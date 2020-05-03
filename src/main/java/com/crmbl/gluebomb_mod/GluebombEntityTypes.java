package com.crmbl.gluebomb_mod;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GluebombEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, GluebombMod.MOD_ID);
    public static final RegistryObject<EntityType<GluebombEntity>> GLUEBOMB = ENTITY_TYPES.register("gluebomb_item", () ->
            EntityType.Builder.<GluebombEntity>create(GluebombEntity::new, EntityClassification.MISC).build("gluebomb_item"));
}