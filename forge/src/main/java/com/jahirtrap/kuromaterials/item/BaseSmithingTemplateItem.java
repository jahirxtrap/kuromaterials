package com.jahirtrap.kuromaterials.item;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

import static com.jahirtrap.kuromaterials.KuroMaterialsMod.MODID;

public class BaseSmithingTemplateItem extends SmithingTemplateItem {
    public BaseSmithingTemplateItem(Component appliesTo, Component ingredients, Component baseSlotDescription, Component additionsSlotDescription, List<ResourceLocation> iconList, List<ResourceLocation> materialList, Properties properties) {
        super(appliesTo, ingredients, baseSlotDescription, additionsSlotDescription, iconList, materialList, properties);
    }

    public static BaseSmithingTemplateItem createUpgradeTemplate(String key, Properties properties) {
        Component[] c = createComponents(key);
        return new BaseSmithingTemplateItem(c[0], c[1], c[2], c[3], SmithingTemplateItem.createNetheriteUpgradeIconList(), SmithingTemplateItem.createNetheriteUpgradeMaterialList(), properties);
    }

    private static Component[] createComponents(String key) {
        return new Component[]{
                Component.translatable(Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(MODID, String.format("smithing_template.%s_upgrade.applies_to", key)))).withStyle(SmithingTemplateItem.DESCRIPTION_FORMAT),
                Component.translatable(Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(MODID, String.format("smithing_template.%s_upgrade.ingredients", key)))).withStyle(SmithingTemplateItem.DESCRIPTION_FORMAT),
                Component.translatable(Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(MODID, String.format("smithing_template.%s_upgrade.base_slot_description", key)))),
                Component.translatable(Util.makeDescriptionId("item", ResourceLocation.fromNamespaceAndPath(MODID, String.format("smithing_template.%s_upgrade.additions_slot_description", key))))
        };
    }
}
