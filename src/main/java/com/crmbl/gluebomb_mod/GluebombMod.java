package com.crmbl.gluebomb_mod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("gluebomb_mod")
public class GluebombMod
{
    public static final String MOD_ID = "gluebomb_mod";

    public GluebombMod() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new GluebombModEventHandler());
        GluebombModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}