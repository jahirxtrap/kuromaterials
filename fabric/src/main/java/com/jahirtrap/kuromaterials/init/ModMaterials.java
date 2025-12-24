package com.jahirtrap.kuromaterials.init;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;

import static com.jahirtrap.kuromaterials.KuroMaterialsMod.MODID;

public class ModMaterials {
    public interface Armor {
        ArmorMaterial ZURITE = new ArmorMaterial(31, createMap(new int[]{3, 6, 8, 3, 11}),
                16, SoundEvents.ARMOR_EQUIP_NETHERITE, 2.5f, 0.1f, ModTags.Items.ZURITE_INGOTS, createAsset("zurite"));

        private static EnumMap<ArmorType, Integer> createMap(int[] values) {
            EnumMap<ArmorType, Integer> enumMap = new EnumMap<>(ArmorType.class);
            for (int i = 0; i < values.length; i++) enumMap.put(ArmorType.values()[i], values[i]);
            return enumMap;
        }

        private static ResourceKey<EquipmentAsset> createAsset(String name) {
            return ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(MODID, name));
        }
    }

    public interface Tool {
        ToolMaterial ZURITE = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 1320, 8.5f, 4f, 16, ModTags.Items.ZURITE_INGOTS);
    }
}
