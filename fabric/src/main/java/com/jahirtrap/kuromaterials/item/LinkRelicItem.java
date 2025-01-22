package com.jahirtrap.kuromaterials.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.jahirtrap.kuromaterials.util.CommonUtils.coloredTextComponent;

public class LinkRelicItem extends Item {
    private static final String PLAYER_NAME_KEY = "PlayerName";

    public LinkRelicItem(Properties properties) {
        super(properties.stacksTo(16));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide() && getPlayerName(player.getItemInHand(hand)) != null) player.startUsingItem(hand);

        return super.use(level, player, hand);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!player.getCooldowns().isOnCooldown(stack.getItem()) && target instanceof Player targetPlayer) {
            setPlayerName(player.getItemInHand(hand), targetPlayer.getName().getString(), player);
            targetPlayer.level().playSound(null, targetPlayer.blockPosition(), SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS);
            return InteractionResult.SUCCESS;
        }

        return super.interactLivingEntity(stack, player, target, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide() && entity instanceof ServerPlayer serverPlayer) {
            var targetPlayer = level.getServer().getPlayerList().getPlayerByName(getPlayerName(stack));

            if (targetPlayer != null && targetPlayer != serverPlayer) {
                var targetDimension = targetPlayer.level().dimension();
                var targetPos = targetPlayer.blockPosition();
                var serverLevel = level.getServer() != null ? level.getServer().getLevel(targetDimension) : null;

                if (serverLevel != null) {
                    if (level.dimension() != targetDimension)
                        serverPlayer.teleportTo(level.getServer().getLevel(targetDimension), targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, RelativeMovement.ALL, serverPlayer.getYRot(), serverPlayer.getXRot());
                    serverPlayer.teleportTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);

                    serverLevel.playSound(null, serverPlayer.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS);
                }
            } else level.playSound(null, serverPlayer.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS);

            serverPlayer.getCooldowns().addCooldown(stack.getItem(), 30);
        }

        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        if (!getPlayerName(stack).isBlank())
            tooltip.add(coloredTextComponent(getPlayerName(stack) + " " + Component.translatable("kuromaterials.link_relic.linked").getString(), ChatFormatting.GRAY));
    }

    private String getPlayerName(ItemStack stack) {
        return stack.getOrCreateTag().getString(PLAYER_NAME_KEY);
    }

    private void setPlayerName(ItemStack stack, String name, Player player) {
        ItemStack result = stack.copyWithCount(1);
        result.getOrCreateTag().putString(PLAYER_NAME_KEY, name);

        if (!player.getAbilities().instabuild && stack.getCount() == 1) {
            stack.setTag(result.getTag());
        } else {
            if (!player.getAbilities().instabuild) stack.shrink(1);
            if (!player.getInventory().add(result)) player.drop(result, false);
        }

        player.displayClientMessage(coloredTextComponent(name + " " + Component.translatable("kuromaterials.link_relic.linked").getString(), ChatFormatting.GOLD), true);
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
        return stack.getOrCreateTag().contains(PLAYER_NAME_KEY);
    }
}
