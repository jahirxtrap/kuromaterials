package com.jahirtrap.kuromaterials.item;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

import static com.jahirtrap.kuromaterials.KuroMaterialsMod.MODID;

public class BaseSmithingTemplateItem extends SmithingTemplateItem {
    public BaseSmithingTemplateItem(Component appliesTo, Component ingredients, Component upgrade, Component baseSlotDescription, Component additionsSlotDescription, List<ResourceLocation> iconList, List<ResourceLocation> materialList) {
        super(appliesTo, ingredients, upgrade, baseSlotDescription, additionsSlotDescription, iconList, materialList);
    }

    public static BaseSmithingTemplateItem createUpgradeTemplate(String key) {
        Component[] c = createComponents(key);
        return new BaseSmithingTemplateItem(c[0], c[1], c[2], c[3], c[4], SmithingTemplateItem.createNetheriteUpgradeIconList(), SmithingTemplateItem.createNetheriteUpgradeMaterialList());
    }

    private static Component[] createComponents(String key) {
        return new Component[]{
                Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(MODID, String.format("smithing_template.%s_upgrade.applies_to", key)))).withStyle(SmithingTemplateItem.DESCRIPTION_FORMAT),
                Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(MODID, String.format("smithing_template.%s_upgrade.ingredients", key)))).withStyle(SmithingTemplateItem.DESCRIPTION_FORMAT),
                Component.translatable(Util.makeDescriptionId("upgrade", new ResourceLocation(MODID, String.format("%s_upgrade", key)))).withStyle(SmithingTemplateItem.TITLE_FORMAT),
                Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(MODID, String.format("smithing_template.%s_upgrade.base_slot_description", key)))),
                Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(MODID, String.format("smithing_template.%s_upgrade.additions_slot_description", key))))
        };
    }
}
