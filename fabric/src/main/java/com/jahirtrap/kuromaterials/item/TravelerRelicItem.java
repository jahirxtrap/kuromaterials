package com.jahirtrap.kuromaterials.item;

import com.jahirtrap.kuromaterials.init.ModTags;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.jahirtrap.kuromaterials.util.CommonUtils.coloredTextComponent;
import static com.jahirtrap.kuromaterials.util.CommonUtils.snakeToTitleCase;

public class TravelerRelicItem extends BaseItem {
    private static final String DIM_KEY = "RelicDim";
    private static final String POS_KEY = "RelicPos";
    private final boolean fragment;

    public TravelerRelicItem(boolean fragment, Properties properties) {
        super(properties.stacksTo(16));
        this.fragment = fragment;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BlockPos pos = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE).getBlockPos();
        boolean anchor = level.getBlockState(pos).is(ModTags.Blocks.ANCHOR_BLOCKS);

        if (!level.isClientSide() && (anchor || player.isShiftKeyDown()) && (anchor || !fragment)) {
            var targetPos = anchor ? pos.above() : player.blockPosition();

            setGlobalPos(stack, level.dimension(), targetPos, player, hand);
            level.playSound(null, anchor ? targetPos.below() : targetPos, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1, 1);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
        } else if (!level.isClientSide() && getGlobalPos(stack) != null) player.startUsingItem(hand);

        return super.use(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide() && entity instanceof ServerPlayer serverPlayer) {
            var targetDimension = getGlobalPos(stack).dimension();
            var targetPos = getGlobalPos(stack).pos();
            var serverLevel = level.getServer() != null ? level.getServer().getLevel(targetDimension) : null;

            if (serverLevel != null && (!fragment || serverLevel.getBlockState(targetPos.below()).is(ModTags.Blocks.ANCHOR_BLOCKS))) {
                if (level.dimension() != targetDimension)
                    serverPlayer.teleportTo(level.getServer().getLevel(targetDimension), targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, serverPlayer.getYRot(), serverPlayer.getXRot());
                serverPlayer.teleportTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);

                serverLevel.playSound(null, serverPlayer.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1, 1);
            } else
                level.playSound(null, serverPlayer.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1, 1);

            serverPlayer.getCooldowns().addCooldown(stack.getItem(), 30);
        }

        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        if (stack.getOrCreateTag().contains(DIM_KEY) && stack.getOrCreateTag().contains(POS_KEY)) {
            var targetPos = getGlobalPos(stack).pos();
            tooltip.add(coloredTextComponent((snakeToTitleCase(getGlobalPos(stack).dimension().location().getPath()) + " [" + targetPos.getX() + ", " + targetPos.getY() + ", " + targetPos.getZ() + "]"), ChatFormatting.GRAY));
        }
    }

    private GlobalPos getGlobalPos(ItemStack stack) {
        List<ResourceKey<Level>> dimension = new ArrayList<>();
        Level.RESOURCE_KEY_CODEC.decode(NbtOps.INSTANCE, stack.getOrCreateTag().get(DIM_KEY)).resultOrPartial(LogUtils.getLogger()::error).ifPresent((dim) -> dimension.add(dim.getFirst()));
        return GlobalPos.of(dimension.get(0), NbtUtils.readBlockPos(stack.getOrCreateTag().getCompound(POS_KEY)));
    }

    private void setGlobalPos(ItemStack stack, ResourceKey<Level> targetDimension, BlockPos targetPos, Player player, InteractionHand hand) {
        ItemStack result = stack.copy();
        result.setCount(1);
        Level.RESOURCE_KEY_CODEC.encodeStart(NbtOps.INSTANCE, targetDimension).resultOrPartial(LogUtils.getLogger()::error).ifPresent((dim) -> result.getOrCreateTag().put(DIM_KEY, dim));
        result.getOrCreateTag().put(POS_KEY, NbtUtils.writeBlockPos(targetPos));

        if (!player.getAbilities().instabuild && stack.getCount() == 1) {
            stack.setTag(result.getTag());
        } else {
            if (!player.getAbilities().instabuild) stack.shrink(1);
            if (!player.getInventory().add(result)) player.drop(result, false);
        }

        player.displayClientMessage(coloredTextComponent(snakeToTitleCase(targetDimension.location().getPath()) + " [" + targetPos.getX() + ", " + targetPos.getY() + ", " + targetPos.getZ() + "]", ChatFormatting.GOLD), true);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 30;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.getOrCreateTag().contains(DIM_KEY) && stack.getOrCreateTag().contains(POS_KEY);
    }
}
