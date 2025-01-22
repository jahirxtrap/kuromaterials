package com.jahirtrap.kuromaterials.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.function.Supplier;

import static com.jahirtrap.kuromaterials.KuroMaterialsMod.MODID;

public enum ModMaterials implements StringRepresentable, ArmorMaterial {
    ZURITE("zurite", 31, createMap(new int[]{3, 6, 8, 3}),
            16, SoundEvents.ARMOR_EQUIP_NETHERITE, 2.5f, 0.1f, () -> Ingredient.of(ModTags.Items.ZURITE_INGOTS));

    private static EnumMap<Type, Integer> createMap(int[] values) {
        EnumMap<Type, Integer> enumMap = new EnumMap<>(Type.class);
        for (int i = 0; i < values.length; i++) enumMap.put(Type.values()[i], values[i]);
        return enumMap;
    }

    public static final StringRepresentable.EnumCodec<ModMaterials> CODEC = StringRepresentable.fromEnum(ModMaterials::values);
    private static final EnumMap<Type, Integer> durability = createMap(new int[]{13, 15, 16, 11});
    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<Type, Integer> defense;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> ingredient;

    ModMaterials(String name, int durabilityMultiplier, EnumMap<Type, Integer> defense, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> ingredient) {
        this.name = new ResourceLocation(MODID, name).toString();
        this.durabilityMultiplier = durabilityMultiplier;
        this.defense = defense;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.ingredient = ingredient;
    }

    public int getDurabilityForType(Type type) {
        return durability.get(type) * durabilityMultiplier;
    }

    public int getDefenseForType(Type type) {
        return defense.get(type);
    }

    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    public SoundEvent getEquipSound() {
        return sound;
    }

    public Ingredient getRepairIngredient() {
        return ingredient.get();
    }

    public String getName() {
        return name;
    }

    public float getToughness() {
        return toughness;
    }

    public float getKnockbackResistance() {
        return knockbackResistance;
    }

    public String getSerializedName() {
        return name;
    }
}
