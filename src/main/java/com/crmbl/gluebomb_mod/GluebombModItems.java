package com.crmbl.gluebomb_mod;

import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GluebombModItems {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, GluebombMod.MOD_ID);
    public static final RegistryObject<Item> GLUEBOMB_ITEM = ITEMS.register("gluebomb_item", () ->
            new GluebombItem(new TridentItem.Properties()
                    .maxStackSize(24)
                    //.maxDamage(30)
                    .group(ItemGroup.COMBAT)
            )
    );
}
