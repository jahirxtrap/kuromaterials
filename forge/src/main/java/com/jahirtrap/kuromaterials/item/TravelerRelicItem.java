package com.jahirtrap.kuromaterials.item;

import com.jahirtrap.kuromaterials.init.ModComponents;
import com.jahirtrap.kuromaterials.init.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;

import java.util.List;

import static com.jahirtrap.kuromaterials.util.CommonUtils.coloredTextComponent;
import static com.jahirtrap.kuromaterials.util.CommonUtils.snakeToTitleCase;

public class TravelerRelicItem extends Item {
    private static final DataComponentType<GlobalPos> GLOBAL_POS_KEY = ModComponents.GLOBAL_POS_KEY.get();
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
            level.playSound(null, anchor ? targetPos.below() : targetPos, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS);
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
                    serverPlayer.teleportTo(level.getServer().getLevel(targetDimension), targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, RelativeMovement.ALL, serverPlayer.getYRot(), serverPlayer.getXRot());
                serverPlayer.teleportTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);

                serverLevel.playSound(null, serverPlayer.blockPosition(), SoundEvents.PLAYER_TELEPORT, SoundSource.PLAYERS);
            } else level.playSound(null, serverPlayer.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS);

            serverPlayer.getCooldowns().addCooldown(stack.getItem(), 30);
        }

        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        if (getGlobalPos(stack) != null) {
            var targetPos = getGlobalPos(stack).pos();
            tooltip.add(coloredTextComponent((snakeToTitleCase(getGlobalPos(stack).dimension().location().getPath()) + " [" + targetPos.getX() + ", " + targetPos.getY() + ", " + targetPos.getZ() + "]"), ChatFormatting.GRAY));
        }
    }

    private GlobalPos getGlobalPos(ItemStack stack) {
        return stack.get(GLOBAL_POS_KEY);
    }

    private void setGlobalPos(ItemStack stack, ResourceKey<Level> targetDimension, BlockPos targetPos, Player player, InteractionHand hand) {
        ItemStack result = stack.copyWithCount(1);
        result.set(GLOBAL_POS_KEY, new GlobalPos(targetDimension, targetPos));
        result.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);

        if (!player.hasInfiniteMaterials() && stack.getCount() == 1) {
            stack.applyComponents(result.getComponents());
        } else {
            stack.consume(1, player);
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
}
