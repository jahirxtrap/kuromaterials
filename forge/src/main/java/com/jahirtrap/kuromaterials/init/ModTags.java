package com.jahirtrap.kuromaterials.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static com.jahirtrap.kuromaterials.KuroMaterialsMod.MODID;

public class ModTags {
    public interface Items {
        TagKey<Item> AUTOREPAIRABLE_ITEMS = create(Identifier.fromNamespaceAndPath(MODID, "autorepairable_items"));
        TagKey<Item> ZURITE_INGOTS = create(Identifier.parse("c:ingots/zurite"));

        private static TagKey<Item> create(Identifier name) {
            return TagKey.create(Registries.ITEM, name);
        }
    }

    public interface Blocks {
        TagKey<Block> ANCHOR_BLOCKS = create(Identifier.fromNamespaceAndPath(MODID, "anchor_blocks"));

        private static TagKey<Block> create(Identifier name) {
            return TagKey.create(Registries.BLOCK, name);
        }
    }
}
