package com.crmbl.weaponry_mod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class HealingArrowEntity extends AbstractArrowEntity {

    protected HealingArrowEntity(EntityType<? extends AbstractArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public HealingArrowEntity(World worldIn, LivingEntity shooter) {
        super(WeaponryModEntityType.HEALING_ARROW.get(), shooter, worldIn);
    }

    public HealingArrowEntity(World worldIn, double x, double y, double z) {
        super(WeaponryModEntityType.HEALING_ARROW.get(), x, y, z, worldIn);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(WeaponryModItems.HEALING_ARROW.get());
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void tick() {
        super.tick();
        if (this.world.isRemote && !this.inGround) {
            this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) {
        RayTraceResult.Type raytraceresult$type = raytraceResultIn.getType();
        if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult)raytraceResultIn).getEntity();
            if (entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity)entity;
                EffectInstance effectinstance = new EffectInstance(Effects.INSTANT_HEALTH, 1);
                living.addPotionEffect(effectinstance);
            }

            this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            this.remove();
        }
        else if (raytraceresult$type == RayTraceResult.Type.BLOCK)
            super.onHit(raytraceResultIn);
    }
}
