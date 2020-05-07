package com.crmbl.weaponry_mod;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WeaponryModEntityType {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, WeaponryMod.MOD_ID);
    public static final RegistryObject<EntityType<ThrowingKnifeEntity>> THROWING_KNIFE = ENTITY_TYPES.register("throwing_knife_item", () ->
            EntityType.Builder.<ThrowingKnifeEntity>create(ThrowingKnifeEntity::new, EntityClassification.MISC).build("throwing_knife_item"));
    public static final RegistryObject<EntityType<ExplosiveArrowEntity>> EXPLOSIVE_ARROW = ENTITY_TYPES.register("explosive_arrow_item", () ->
            EntityType.Builder.<ExplosiveArrowEntity>create(ExplosiveArrowEntity::new, EntityClassification.MISC).build("explosive_arrow_item"));
}