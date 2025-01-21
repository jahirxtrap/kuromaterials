package com.jahirtrap.kuromaterials.init.mixin;

import com.jahirtrap.kuromaterials.item.BaseArmorItem;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Locale;
import java.util.Map;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin {

    @Final
    @Shadow
    private static Map<String, ResourceLocation> ARMOR_LOCATION_CACHE;

    @Inject(method = "getArmorLocation", at = @At("HEAD"), cancellable = true)
    private void getArmorLocation(ArmorItem armorItem, boolean bl, String string, CallbackInfoReturnable<ResourceLocation> cir) {
        if (armorItem instanceof BaseArmorItem baseArmorItem) {
            String name = baseArmorItem.getMaterial().getName();
            int idx = name.indexOf(ResourceLocation.NAMESPACE_SEPARATOR);

            if (idx != -1) {
                String namespace = name.substring(0, idx);
                String path = name.substring(idx + 1);
                String texture = String.format(Locale.ROOT, "%s:textures/models/armor/%s_layer_%d%s.png", namespace, path, (bl ? 2 : 1), string == null ? "" : "_" + string);

                cir.setReturnValue(ARMOR_LOCATION_CACHE.computeIfAbsent(texture, ResourceLocation::new));
            }
        }
    }
}
