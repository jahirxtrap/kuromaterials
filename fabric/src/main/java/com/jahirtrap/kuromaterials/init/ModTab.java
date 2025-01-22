package com.jahirtrap.kuromaterials.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static com.jahirtrap.kuromaterials.KuroMaterialsMod.MODID;

public class ModTab {
    public static void init() {
        CreativeModeTab TAB_KURO_MATERIALS = FabricItemGroup.builder(new ResourceLocation(MODID, "tab_kuromaterials"))
                .icon(() -> new ItemStack(ModContent.ZURITE_INGOT))
                .displayItems((features, event) -> {
                    for (Item item : ModContent.ITEMS)
                        event.accept(item);
                })
                .title(Component.translatable("itemGroup.kuromaterials.tab_kuromaterials"))
                .build();
    }
}
