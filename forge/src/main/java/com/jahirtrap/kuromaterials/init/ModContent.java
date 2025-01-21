package com.jahirtrap.kuromaterials.init;

import com.jahirtrap.kuromaterials.item.BaseArmorItem;
import com.jahirtrap.kuromaterials.item.BaseItem;
import com.jahirtrap.kuromaterials.item.LinkRelicItem;
import com.jahirtrap.kuromaterials.item.TravelerRelicItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

import static com.jahirtrap.kuromaterials.KuroMaterialsMod.MODID;
import static com.jahirtrap.kuromaterials.init.ModTab.TAB_KURO_MATERIALS;

public class ModContent {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registry.BLOCK_REGISTRY, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registry.ITEM_REGISTRY, MODID);

    public static final RegistryObject<Item> ZURITE_INGOT = registerItem("zurite_ingot", () -> new BaseItem(new Item.Properties().fireResistant()));
    public static final RegistryObject<Block> ZURITE_BLOCK = registerBlock("zurite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK)), new Item.Properties().fireResistant());
    public static final List<RegistryObject<Item>> ZURITE_TOOLS = registerTools("zurite", ModTiers.ZURITE, new float[]{5f, -3f, -4f, 0f}, new Item.Properties().fireResistant());
    public static final List<RegistryObject<Item>> ZURITE_ARMOR = registerArmor(ModMaterials.ZURITE, new Item.Properties().fireResistant());
    public static final RegistryObject<Item> TRAVELER_RELIC_FRAGMENT = registerItem("traveler_relic_fragment", () -> new TravelerRelicItem(true, new Item.Properties().fireResistant().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> TRAVELER_RELIC = registerItem("traveler_relic", () -> new TravelerRelicItem(false, new Item.Properties().fireResistant().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> LINK_RELIC = registerItem("link_relic", () -> new LinkRelicItem(new Item.Properties().fireResistant().rarity(Rarity.RARE)));
    public static final RegistryObject<Block> ANCHOR_STONE = registerBlock("anchor_stone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(5f, 6f).sound(SoundType.LODESTONE).requiresCorrectToolForDrops()), new Item.Properties().fireResistant());

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> supplier, Item.Properties itemProp) {
        var blockReg = registerBlock(name, supplier);
        registerItem(name, () -> new BlockItem(blockReg.get(), itemProp.tab(TAB_KURO_MATERIALS)));
        return blockReg;
    }

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> supplier) {
        return BLOCKS.register(name, supplier);
    }

    private static RegistryObject<Item> registerItem(String name, Supplier<Item> supplier) {
        return ITEMS.register(name, supplier);
    }

    private static List<RegistryObject<Item>> registerTools(String name, Tier tier, float[] attr, Item.Properties itemProp) {
        return List.of(
                registerItem(name + "_sword", () -> new SwordItem(tier, 3, -2.4f, itemProp.tab(TAB_KURO_MATERIALS))),
                registerItem(name + "_pickaxe", () -> new PickaxeItem(tier, 1, -2.8f, itemProp.tab(TAB_KURO_MATERIALS))),
                registerItem(name + "_axe", () -> new AxeItem(tier, attr[0], attr[1], itemProp.tab(TAB_KURO_MATERIALS))),
                registerItem(name + "_shovel", () -> new ShovelItem(tier, 1.5f, -3f, itemProp.tab(TAB_KURO_MATERIALS))),
                registerItem(name + "_hoe", () -> new HoeItem(tier, (int) attr[2], attr[3], itemProp.tab(TAB_KURO_MATERIALS)))
        );
    }

    private static List<RegistryObject<Item>> registerArmor(ArmorMaterial material, Item.Properties itemProp) {
        String name = material.getName().substring(material.getName().indexOf(ResourceLocation.NAMESPACE_SEPARATOR) + 1);
        return List.of(
                registerItem(name + "_helmet", () -> new BaseArmorItem(material, EquipmentSlot.HEAD, itemProp)),
                registerItem(name + "_chestplate", () -> new BaseArmorItem(material, EquipmentSlot.CHEST, itemProp)),
                registerItem(name + "_leggings", () -> new BaseArmorItem(material, EquipmentSlot.LEGS, itemProp)),
                registerItem(name + "_boots", () -> new BaseArmorItem(material, EquipmentSlot.FEET, itemProp))
        );
    }

    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }
}
