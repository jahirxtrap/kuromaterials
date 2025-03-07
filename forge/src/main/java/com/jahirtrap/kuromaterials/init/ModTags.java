package com.jahirtrap.kuromaterials.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static com.jahirtrap.kuromaterials.KuroMaterialsMod.MODID;

public class ModTags {
    public interface Items {
        TagKey<Item> AUTOREPAIRABLE_ITEMS = create(ResourceLocation.fromNamespaceAndPath(MODID, "autorepairable_items"));
        TagKey<Item> ZURITE_INGOTS = create(ResourceLocation.parse("c:ingots/zurite"));

        private static TagKey<Item> create(ResourceLocation name) {
            return TagKey.create(Registries.ITEM, name);
        }
    }

    public interface Blocks {
        TagKey<Block> ANCHOR_BLOCKS = create(ResourceLocation.fromNamespaceAndPath(MODID, "anchor_blocks"));

        private static TagKey<Block> create(ResourceLocation name) {
            return TagKey.create(Registries.BLOCK, name);
        }
    }
}
