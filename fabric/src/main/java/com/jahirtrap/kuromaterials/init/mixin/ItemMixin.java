package com.jahirtrap.kuromaterials.init.mixin;

import com.jahirtrap.kuromaterials.init.ModConfig;
import com.jahirtrap.kuromaterials.init.ModTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Inject(method = "inventoryTick", at = @At("HEAD"))
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int i, boolean bl, CallbackInfo ci) {
        if (!level.isClientSide() && stack.is(ModTags.Items.AUTOREPAIRABLE_ITEMS) && stack.isDamaged()) {
            if (!(ModConfig.itemAutorepairInterval <= 0) && level.getGameTime() % ModConfig.itemAutorepairInterval == 0) {
                if (entity instanceof Player player)
                    if ((player.getUseItem() == stack) || (player.swinging && player.getItemInHand(player.swingingArm) == stack))
                        return;
                stack.setDamageValue(stack.getDamageValue() - ModConfig.itemAutorepairAmount);
            }
        }
    }
}
