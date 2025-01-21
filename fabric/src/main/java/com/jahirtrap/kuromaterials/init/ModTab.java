package com.jahirtrap.kuromaterials.init;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import static com.jahirtrap.kuromaterials.KuroMaterialsMod.MODID;

public class ModTab {
    public static final CreativeModeTab TAB_KURO_MATERIALS = FabricItemGroupBuilder.build(
            new ResourceLocation(MODID, "tab_kuromaterials"), () -> new ItemStack(ModContent.ZURITE_INGOT));
}
