package com.jahirtrap.kuromaterials;

import com.jahirtrap.configlib.TXFConfig;
import com.jahirtrap.kuromaterials.init.ModConfig;
import com.jahirtrap.kuromaterials.init.ModContent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(KuroMaterialsMod.MODID)
public class KuroMaterialsMod {

    public static final String MODID = "kuromaterials";

    public KuroMaterialsMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        TXFConfig.init(MODID, ModConfig.class);
        ModContent.init(bus);
    }
}
