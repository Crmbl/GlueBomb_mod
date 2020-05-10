package com.crmbl.weaponry_mod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExplosiveArrowEntity extends AbstractArrowEntity {

    protected ExplosiveArrowEntity(EntityType<? extends AbstractArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public ExplosiveArrowEntity(World worldIn, LivingEntity shooter) {
        super(WeaponryModEntityType.EXPLOSIVE_ARROW.get(), shooter, worldIn);
    }

    public ExplosiveArrowEntity(World worldIn, double x, double y, double z) {
        super(WeaponryModEntityType.EXPLOSIVE_ARROW.get(), x, y, z, worldIn);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(WeaponryModItems.EXPLOSIVE_ARROW.get());
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void tick() {
        super.tick();
        if (this.world.isRemote && !this.inGround) {
            this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void shoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy) {
        super.shoot(shooter, pitch, yaw, p_184547_4_, velocity, inaccuracy);
        this.world.playMovingSound(null, shooter, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    @Override
    protected void onHit(RayTraceResult result) {
        super.onHit(result);
        this.world.createExplosion(this, this.getPosX(), this.getPosYHeight(0.0625D), this.getPosZ(), 0.8F, Explosion.Mode.BREAK);
        this.remove();
    }
}
