package com.jahirtrap.kuromaterials.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static com.jahirtrap.kuromaterials.KuroMaterialsMod.MODID;

public class ModTab {
    public static final CreativeModeTab TAB_KURO_MATERIALS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModContent.ZURITE_INGOT))
            .displayItems((features, event) -> {
                for (Item item : ModContent.ITEMS)
                    event.accept(item);
            })
            .title(Component.translatable("itemGroup.kuromaterials.tab_kuromaterials"))
            .build();

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(MODID, "tab_kuromaterials"), TAB_KURO_MATERIALS);
    }
}
