package com.jahirtrap.kuromaterials.item;

import net.minecraft.world.item.Item;

import static com.jahirtrap.kuromaterials.init.ModTab.TAB_KURO_MATERIALS;

public class BaseItem extends Item {
    public BaseItem(Properties properties) {
        super(properties.tab(TAB_KURO_MATERIALS));
    }
}
