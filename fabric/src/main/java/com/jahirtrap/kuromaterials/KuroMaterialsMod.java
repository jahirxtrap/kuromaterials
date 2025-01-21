package com.jahirtrap.kuromaterials;

import com.jahirtrap.configlib.TXFConfig;
import com.jahirtrap.kuromaterials.init.ModConfig;
import com.jahirtrap.kuromaterials.init.ModContent;
import net.fabricmc.api.ModInitializer;

public class KuroMaterialsMod implements ModInitializer {

    public static final String MODID = "kuromaterials";

    @Override
    public void onInitialize() {
        TXFConfig.init(MODID, ModConfig.class);
        ModContent.init();
    }
}
