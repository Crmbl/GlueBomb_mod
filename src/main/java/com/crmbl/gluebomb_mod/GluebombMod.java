package com.crmbl.gluebomb_mod;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;

@Mod("gluebomb_mod")
public class GluebombMod
{
    public static final String MOD_ID = "gluebomb_mod";

    public GluebombMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new GluebombModEventHandler());
        GluebombModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        GluebombEntityTypes.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            //LogManager.getLogger().info("HELLO from Register Block");
        }
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(GluebombEntityTypes.GLUEBOMB.get(), manager ->
                new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer()));
    }
}