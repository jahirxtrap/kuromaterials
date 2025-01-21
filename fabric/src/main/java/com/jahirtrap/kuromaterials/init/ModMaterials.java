package com.jahirtrap.kuromaterials.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

import static com.jahirtrap.kuromaterials.KuroMaterialsMod.MODID;

public enum ModMaterials implements ArmorMaterial {
    ZURITE("zurite", 31, new int[]{3, 6, 8, 3},
            16, SoundEvents.ARMOR_EQUIP_NETHERITE, 2.5f, 0.1f, () -> Ingredient.of(ModTags.Items.ZURITE_INGOTS));

    private static final int[] durability = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] defense;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> ingredient;

    ModMaterials(String name, int durabilityMultiplier, int[] defense, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> ingredient) {
        this.name = new ResourceLocation(MODID, name).toString();
        this.durabilityMultiplier = durabilityMultiplier;
        this.defense = defense;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.ingredient = ingredient;
    }

    public int getDurabilityForSlot(EquipmentSlot slot) {
        return durability[slot.getIndex()] * durabilityMultiplier;
    }

    public int getDefenseForSlot(EquipmentSlot slot) {
        return defense[slot.getIndex()];
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
}
