package com.jahirtrap.kuromaterials.init;

import com.jahirtrap.kuromaterials.item.BaseArmorItem;
import com.jahirtrap.kuromaterials.item.BaseSmithingTemplateItem;
import com.jahirtrap.kuromaterials.item.LinkRelicItem;
import com.jahirtrap.kuromaterials.item.TravelerRelicItem;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;
import java.util.List;

import static com.jahirtrap.kuromaterials.KuroMaterialsMod.MODID;

public class ModContent {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static final Item ZURITE_INGOT = registerItem("zurite_ingot", new Item(new Item.Properties().fireResistant()));
    public static final Block ZURITE_BLOCK = registerBlock("zurite_block", new Block(BlockBehaviour.Properties.ofLegacyCopy(Blocks.NETHERITE_BLOCK).mapColor(MapColor.COLOR_GRAY)), new Item.Properties().fireResistant());
    public static final Item ZURITE_UPGRADE_SMITHING_TEMPLATE = registerItem("zurite_upgrade_smithing_template", BaseSmithingTemplateItem.createUpgradeTemplate("zurite"));
    public static final List<Item> ZURITE_TOOLS = registerTools("zurite", ModTiers.ZURITE, new float[]{5f, -3f, -4f, 0f}, new Item.Properties().fireResistant());
    public static final List<Item> ZURITE_ARMOR = registerArmor(ModMaterials.ZURITE, 31, new Item.Properties().fireResistant());
    public static final Item TRAVELER_RELIC_FRAGMENT = registerItem("traveler_relic_fragment", new TravelerRelicItem(true, new Item.Properties().fireResistant().rarity(Rarity.RARE)));
    public static final Item TRAVELER_RELIC = registerItem("traveler_relic", new TravelerRelicItem(false, new Item.Properties().fireResistant().rarity(Rarity.RARE)));
    public static final Item LINK_RELIC = registerItem("link_relic", new LinkRelicItem(new Item.Properties().fireResistant().rarity(Rarity.RARE)));
    public static final Block ANCHOR_STONE = registerBlock("anchor_stone", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(5f, 6f).sound(SoundType.LODESTONE).requiresCorrectToolForDrops()), new Item.Properties().fireResistant());

    private static Block registerBlock(String name, Block block, Item.Properties itemProp) {
        registerItem(name, new BlockItem(block, itemProp));
        return registerBlock(name, block);
    }

    private static Block registerBlock(String name, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(MODID, name), block);
    }

    private static Item registerItem(String name, Item item) {
        var itemReg = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MODID, name), item);
        ITEMS.add(itemReg);
        return itemReg;
    }

    private static List<Item> registerTools(String name, Tier tier, float[] attr, Item.Properties itemProp) {
        return List.of(
                registerItem(name + "_sword", new SwordItem(tier, itemProp.attributes(SwordItem.createAttributes(tier, 3, -2.4f)))),
                registerItem(name + "_pickaxe", new PickaxeItem(tier, itemProp.attributes(PickaxeItem.createAttributes(tier, 1f, -2.8f)))),
                registerItem(name + "_axe", new AxeItem(tier, itemProp.attributes(AxeItem.createAttributes(tier, attr[0], attr[1])))),
                registerItem(name + "_shovel", new ShovelItem(tier, itemProp.attributes(ShovelItem.createAttributes(tier, 1.5f, -3f)))),
                registerItem(name + "_hoe", new HoeItem(tier, itemProp.attributes(HoeItem.createAttributes(tier, attr[2], attr[3]))))
        );
    }

    private static List<Item> registerArmor(Holder<ArmorMaterial> material, int durabilityMultiplier, Item.Properties itemProp) {
        String name = material.getRegisteredName().substring(material.getRegisteredName().indexOf(ResourceLocation.NAMESPACE_SEPARATOR) + 1);
        return List.of(
                registerItem(name + "_helmet", new BaseArmorItem(material, Type.HELMET, durabilityMultiplier, itemProp)),
                registerItem(name + "_chestplate", new BaseArmorItem(material, Type.CHESTPLATE, durabilityMultiplier, itemProp)),
                registerItem(name + "_leggings", new BaseArmorItem(material, Type.LEGGINGS, durabilityMultiplier, itemProp)),
                registerItem(name + "_boots", new BaseArmorItem(material, Type.BOOTS, durabilityMultiplier, itemProp))
        );
    }

    public static void init() {
    }
}
