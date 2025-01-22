package com.jahirtrap.kuromaterials;

import com.jahirtrap.configlib.TXFConfig;
import com.jahirtrap.kuromaterials.init.ModComponents;
import com.jahirtrap.kuromaterials.init.ModConfig;
import com.jahirtrap.kuromaterials.init.ModContent;
import com.jahirtrap.kuromaterials.init.ModTab;
import net.fabricmc.api.ModInitializer;

public class KuroMaterialsMod implements ModInitializer {

    public static final String MODID = "kuromaterials";

    @Override
    public void onInitialize() {
        TXFConfig.init(MODID, ModConfig.class);
        ModComponents.init();
        ModContent.init();
        ModTab.init();
    }
}
