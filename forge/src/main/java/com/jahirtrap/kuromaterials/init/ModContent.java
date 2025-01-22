package com.jahirtrap.kuromaterials.init;

import com.jahirtrap.kuromaterials.item.BaseSmithingTemplateItem;
import com.jahirtrap.kuromaterials.item.LinkRelicItem;
import com.jahirtrap.kuromaterials.item.TravelerRelicItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Function;

import static com.jahirtrap.kuromaterials.KuroMaterialsMod.MODID;

public class ModContent {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MODID);

    public static final RegistryObject<Item> ZURITE_INGOT = registerItem("zurite_ingot", Item::new, new Item.Properties().fireResistant());
    public static final RegistryObject<Block> ZURITE_BLOCK = registerBlock("zurite_block", Block::new, BlockBehaviour.Properties.ofLegacyCopy(Blocks.NETHERITE_BLOCK), new Item.Properties().fireResistant());
    public static final RegistryObject<Item> ZURITE_UPGRADE_SMITHING_TEMPLATE = registerItem("zurite_upgrade_smithing_template", (p) -> BaseSmithingTemplateItem.createUpgradeTemplate("zurite", p), new Item.Properties());
    public static final List<RegistryObject<Item>> ZURITE_TOOLS = registerTools("zurite", ModMaterials.Tool.ZURITE, new float[]{5f, -3f, -4f, 0f}, new Item.Properties().fireResistant());
    public static final List<RegistryObject<Item>> ZURITE_ARMOR = registerArmor(ModMaterials.Armor.ZURITE, new Item.Properties().fireResistant());
    public static final RegistryObject<Item> TRAVELER_RELIC_FRAGMENT = registerItem("traveler_relic_fragment", (p) -> new TravelerRelicItem(true, p), new Item.Properties().fireResistant().rarity(Rarity.RARE));
    public static final RegistryObject<Item> TRAVELER_RELIC = registerItem("traveler_relic", (p) -> new TravelerRelicItem(false, p), new Item.Properties().fireResistant().rarity(Rarity.RARE));
    public static final RegistryObject<Item> LINK_RELIC = registerItem("link_relic", LinkRelicItem::new, new Item.Properties().fireResistant().rarity(Rarity.RARE));
    public static final RegistryObject<Block> ANCHOR_STONE = registerBlock("anchor_stone", Block::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(5f, 6f).sound(SoundType.LODESTONE).requiresCorrectToolForDrops(), new Item.Properties().fireResistant());

    private static RegistryObject<Block> registerBlock(String name, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties blockProp, Item.Properties itemProp) {
        var blockReg = registerBlock(name, function, blockProp);
        registerItem(name, (p) -> new BlockItem(blockReg.get(), p), itemProp.useBlockDescriptionPrefix());
        return blockReg;
    }

    private static RegistryObject<Block> registerBlock(String name, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties blockProp) {
        return BLOCKS.register(name, () -> function.apply(blockProp.setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MODID, name)))));
    }

    private static RegistryObject<Item> registerItem(String name, Function<Item.Properties, Item> function, Item.Properties itemProp) {
        return ITEMS.register(name, () -> function.apply(itemProp.setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, name)))));
    }

    private static List<RegistryObject<Item>> registerTools(String name, ToolMaterial material, float[] attr, Item.Properties itemProp) {
        return List.of(
                registerItem(name + "_sword", (p) -> new SwordItem(material, 3f, -2.4f, p), itemProp),
                registerItem(name + "_pickaxe", (p) -> new PickaxeItem(material, 1f, -2.8f, p), itemProp),
                registerItem(name + "_axe", (p) -> new AxeItem(material, attr[0], attr[1], p), itemProp),
                registerItem(name + "_shovel", (p) -> new ShovelItem(material, 1.5f, -3f, p), itemProp),
                registerItem(name + "_hoe", (p) -> new HoeItem(material, attr[2], attr[3], p), itemProp)
        );
    }

    private static List<RegistryObject<Item>> registerArmor(ArmorMaterial material, Item.Properties itemProp) {
        String name = material.modelId().getPath();
        return List.of(
                registerItem(name + "_helmet", (p) -> new ArmorItem(material, ArmorType.HELMET, p), itemProp),
                registerItem(name + "_chestplate", (p) -> new ArmorItem(material, ArmorType.CHESTPLATE, p), itemProp),
                registerItem(name + "_leggings", (p) -> new ArmorItem(material, ArmorType.LEGGINGS, p), itemProp),
                registerItem(name + "_boots", (p) -> new ArmorItem(material, ArmorType.BOOTS, p), itemProp)
        );
    }

    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }
}
