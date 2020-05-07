package com.crmbl.weaponry_mod;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExplosiveArrowEntity extends AbstractArrowEntity {

    //TODO explosion ! Michael bayy
    //TODO Need custom renderer ?
    //TODO make design
    //TODO add smoke effect ?
    //TODO this.remove onHit()

    protected ExplosiveArrowEntity(EntityType<? extends AbstractArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public ExplosiveArrowEntity(World worldIn, LivingEntity shooter) {
        super(WeaponryModEntityType.EXPLOSIVE_ARROW.get(), shooter, worldIn);
    }

    @OnlyIn(Dist.CLIENT)
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
            this.world.addParticle(ParticleTypes.INSTANT_EFFECT, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    protected void arrowHit(LivingEntity living) {
        super.arrowHit(living);

        //TODO BOOM
        EffectInstance effectinstance = new EffectInstance(Effects.GLOWING, 10, 0);
        living.addPotionEffect(effectinstance);
    }
}
