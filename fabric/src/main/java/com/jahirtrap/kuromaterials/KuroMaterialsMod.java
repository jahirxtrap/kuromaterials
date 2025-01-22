package com.jahirtrap.kuromaterials;

import com.jahirtrap.configlib.TXFConfig;
import com.jahirtrap.kuromaterials.init.*;
import net.fabricmc.api.ModInitializer;

public class KuroMaterialsMod implements ModInitializer {

    public static final String MODID = "kuromaterials";

    @Override
    public void onInitialize() {
        TXFConfig.init(MODID, ModConfig.class);
        ModComponents.init();
        ModMaterials.init();
        ModContent.init();
        ModTab.init();
    }
}
