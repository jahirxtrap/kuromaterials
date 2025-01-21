package com.jahirtrap.kuromaterials.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

import static com.jahirtrap.kuromaterials.init.ModTab.TAB_KURO_MATERIALS;

public class BaseArmorItem extends ArmorItem {
    public BaseArmorItem(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
        super(material, slot, properties.durability(material.getDurabilityForSlot(slot)).tab(TAB_KURO_MATERIALS));
    }
}
