package com.jahirtrap.kuromaterials.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.jahirtrap.kuromaterials.KuroMaterialsMod.MODID;

public class ModComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MODID);

    public static final RegistryObject<DataComponentType<GlobalPos>> GLOBAL_POS_KEY = register("global_pos", () -> DataComponentType.<GlobalPos>builder().persistent(GlobalPos.CODEC).networkSynchronized(GlobalPos.STREAM_CODEC).build());
    public static final RegistryObject<DataComponentType<String>> PLAYER_NAME_KEY = register("player_name", () -> DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    private static <T> RegistryObject<DataComponentType<T>> register(String name, Supplier<DataComponentType<T>> supplier) {
        return COMPONENTS.register(name, supplier);
    }

    public static void init(BusGroup bus) {
        COMPONENTS.register(bus);
    }
}
