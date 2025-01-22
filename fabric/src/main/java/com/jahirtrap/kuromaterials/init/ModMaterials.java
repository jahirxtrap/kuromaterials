package com.jahirtrap.kuromaterials.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

import static com.jahirtrap.kuromaterials.KuroMaterialsMod.MODID;

public class ModMaterials {
    public static final Holder<ArmorMaterial> ZURITE = register("zurite", createMap(new int[]{3, 6, 8, 3, 11}),
            16, SoundEvents.ARMOR_EQUIP_NETHERITE, 2.5f, 0.1f, () -> Ingredient.of(ModTags.Items.ZURITE_INGOTS));

    private static EnumMap<Type, Integer> createMap(int[] values) {
        EnumMap<Type, Integer> enumMap = new EnumMap<>(Type.class);
        for (int i = 0; i < values.length; i++) enumMap.put(Type.values()[i], values[i]);
        return enumMap;
    }

    private static Holder<ArmorMaterial> register(String name, EnumMap<Type, Integer> defense, int i, Holder<SoundEvent> holder, float f, float g, Supplier<Ingredient> supplier) {
        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, new ResourceLocation(MODID, name), new ArmorMaterial(defense, i, holder, supplier, List.of(new ArmorMaterial.Layer(new ResourceLocation(MODID, name))), f, g));
    }

    public static void init() {
    }
}
