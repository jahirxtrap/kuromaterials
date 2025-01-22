package com.jahirtrap.kuromaterials;

import com.jahirtrap.configlib.TXFConfig;
import com.jahirtrap.kuromaterials.init.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(KuroMaterialsMod.MODID)
public class KuroMaterialsMod {

    public static final String MODID = "kuromaterials";

    public KuroMaterialsMod(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();

        TXFConfig.init(MODID, ModConfig.class);
        ModComponents.init(bus);
        ModMaterials.init(bus);
        ModContent.init(bus);
        ModTab.init(bus);
    }
}
