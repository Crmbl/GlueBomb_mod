package com.crmbl.weaponry_mod;

import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WeaponryModItems {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, WeaponryMod.MOD_ID);
    public static final RegistryObject<ThrowingKnifeItem> THROWING_KNIFE = ITEMS.register("throwing_knife_item", () ->
            new ThrowingKnifeItem(new Item.Properties()
                    .maxStackSize(64)
                    .group(ItemGroup.COMBAT)
            )
    );
    public static final RegistryObject<ThrowingKnifeItem> EXPLOSIVE_ARROW = ITEMS.register("explosive_arrow_item", () ->
            new ThrowingKnifeItem(new Item.Properties()
                    .maxStackSize(64)
                    .group(ItemGroup.COMBAT)
            )
    );
}
