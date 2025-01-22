package com.jahirtrap.kuromaterials.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.jahirtrap.kuromaterials.KuroMaterialsMod.MODID;

public class ModTab {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> TAB_KURO_MATERIALS = TABS.register("tab_kuromaterials", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModContent.ZURITE_INGOT.get()))
            .displayItems((features, event) -> {
                for (RegistryObject<Item> item : ModContent.ITEMS.getEntries())
                    event.accept(item.get());
            })
            .title(Component.translatable("itemGroup.kuromaterials.tab_kuromaterials"))
            .build());

    public static void init(IEventBus bus) {
        TABS.register(bus);
    }
}
