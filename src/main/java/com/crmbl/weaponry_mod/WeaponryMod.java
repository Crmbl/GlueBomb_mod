package com.crmbl.weaponry_mod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("weaponry_mod")
public class WeaponryMod
{
    public static final String MOD_ID = "weaponry_mod";

    public WeaponryMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        MinecraftForge.EVENT_BUS.register(this);
        WeaponryModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        WeaponryModEntityType.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(WeaponryModEntityType.THROWING_KNIFE.get(), ThrowingKnifeRenderer::new);
    }
}