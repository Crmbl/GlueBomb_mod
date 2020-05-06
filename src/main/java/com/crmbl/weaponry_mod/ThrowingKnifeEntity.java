package com.crmbl.weaponry_mod;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class ThrowingKnifeEntity extends AbstractArrowEntity {
    private ItemStack thrownStack = new ItemStack(WeaponryModItems.THROWING_KNIFE.get());
    private int ticksIn;
    private final int maxTicks = 400;

    public ThrowingKnifeEntity(EntityType<? extends ThrowingKnifeEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public ThrowingKnifeEntity(World worldIn, LivingEntity thrower, ItemStack thrownStackIn) {
        super(WeaponryModEntityType.THROWING_KNIFE.get(), thrower, worldIn);
        this.thrownStack = thrownStackIn.copy();
    }

    @OnlyIn(Dist.CLIENT)
    public ThrowingKnifeEntity(World worldIn, double x, double y, double z) {
        super(WeaponryModEntityType.THROWING_KNIFE.get(), x, y, z, worldIn);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        this.ticksIn = 0;
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = result.getEntity();
            Entity shooter = this.getShooter();

            float amount = 8.0F;
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                amount += EnchantmentHelper.getModifierForCreature(this.thrownStack, livingentity.getCreatureAttribute());
            }

            DamageSource damageSource = DamageSource.causeThrownDamage(this, (shooter == null ? this : shooter));
            if (entity.attackEntityFrom(damageSource, amount)) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity)entity;
                    if (shooter instanceof LivingEntity)
                        EnchantmentHelper.applyThornEnchantments(livingentity, shooter);

                    this.arrowHit(livingentity);
                    this.playSound(getHitEntitySound(), 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                }
            }

            this.remove();
            //TODO improve damage behavior ?
            //TODO stuck in entity like on block
            //TODO improve trajectory of projectile, too much curve down
        }
        else {
            this.setMotion(this.getMotion().mul(-0.01D, -0.1D, -0.01D));
            this.playSound(getHitEntitySound(), 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isInRangeToRender3d(double x, double y, double z) {
        return true;
    }

    @Override
    protected ItemStack getArrowStack() {
        return this.thrownStack.copy();
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void tick() {
        ++this.ticksIn;
        if (this.ticksIn >= this.maxTicks && !this.world.isRemote)
            this.remove();
        super.tick();
    }

    protected SoundEvent getHitEntitySound() {
        return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
    }
}