package com.jahirtrap.kuromaterials.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.jahirtrap.kuromaterials.KuroMaterialsMod.MODID;

public class ModComponents {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(MODID);

    public static final Supplier<DataComponentType<GlobalPos>> GLOBAL_POS_KEY = register("global_pos", () -> DataComponentType.<GlobalPos>builder().persistent(GlobalPos.CODEC).networkSynchronized(GlobalPos.STREAM_CODEC).build());
    public static final Supplier<DataComponentType<String>> PLAYER_NAME_KEY = register("player_name", () -> DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    private static <T> Supplier<DataComponentType<T>> register(String name, Supplier<DataComponentType<T>> supplier) {
        return COMPONENTS.register(name, supplier);
    }

    public static void init(IEventBus bus) {
        COMPONENTS.register(bus);
    }
}
