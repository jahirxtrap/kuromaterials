package com.jahirtrap.kuromaterials.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.level.Level;

import java.util.Map;

import static com.jahirtrap.kuromaterials.KuroMaterialsMod.MODID;

public class TrimOverlay {
    private static final Map<EquipmentSlot, String> SLOTS = Map.of(
            EquipmentSlot.HEAD, "helmet",
            EquipmentSlot.CHEST, "chestplate",
            EquipmentSlot.LEGS, "leggings",
            EquipmentSlot.FEET, "boots"
    );

    public static void append(ItemStackRenderState output, ItemStack item, ItemModelResolver resolver, ItemDisplayContext displayContext, Level level, ItemOwner owner, int seed) {
        ArmorTrim trim = item.get(DataComponents.TRIM);
        Equippable equippable = item.get(DataComponents.EQUIPPABLE);
        if (trim == null || equippable == null) return;

        String slot = SLOTS.get(equippable.slot());
        Identifier material = trim.material().unwrapKey().map(ResourceKey::identifier).orElse(null);
        if (slot == null || material == null || !material.getNamespace().equals(MODID)) return;

        Identifier overlay = Identifier.fromNamespaceAndPath(MODID, "trim/" + slot + "_" + material.getPath());
        Minecraft.getInstance().getModelManager().getItemModel(overlay)
                .update(output, item, resolver, displayContext, level instanceof ClientLevel clientLevel ? clientLevel : null, owner, seed);
    }
}
