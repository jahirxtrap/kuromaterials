package com.jahirtrap.kuromaterials.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTab {
    public static final CreativeModeTab TAB_KURO_MATERIALS = new CreativeModeTab("kuromaterials.tab_kuromaterials") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModContent.ZURITE_INGOT.get());
        }
    };
}
