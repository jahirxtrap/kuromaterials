package com.jahirtrap.kuromaterials;

import com.jahirtrap.configlib.TXFConfig;
import com.jahirtrap.kuromaterials.init.ModComponents;
import com.jahirtrap.kuromaterials.init.ModConfig;
import com.jahirtrap.kuromaterials.init.ModContent;
import com.jahirtrap.kuromaterials.init.ModTab;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(KuroMaterialsMod.MODID)
public class KuroMaterialsMod {

    public static final String MODID = "kuromaterials";

    public KuroMaterialsMod(FMLJavaModLoadingContext context) {
        BusGroup bus = context.getModBusGroup();

        TXFConfig.init(MODID, ModConfig.class);
        ModComponents.init(bus);
        ModContent.init(bus);
        ModTab.init(bus);
    }
}
