package com.jahirtrap.kuromaterials;

import com.jahirtrap.configlib.TXFConfig;
import com.jahirtrap.kuromaterials.init.ModComponents;
import com.jahirtrap.kuromaterials.init.ModConfig;
import com.jahirtrap.kuromaterials.init.ModContent;
import com.jahirtrap.kuromaterials.init.ModTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(KuroMaterialsMod.MODID)
public class KuroMaterialsMod {

    public static final String MODID = "kuromaterials";

    public KuroMaterialsMod(IEventBus bus) {
        TXFConfig.init(MODID, ModConfig.class);
        ModComponents.init(bus);
        ModContent.init(bus);
        ModTab.init(bus);
    }
}
