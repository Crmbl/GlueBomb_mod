package com.crmbl.gluebomb_mod;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class GluebombEntity extends ProjectileItemEntity {
    private LivingEntity gluebombThrower;
    @Nullable
    private BlockState inBlockState;
    protected boolean inGround;
    protected int timeInEntity;
    private int ticksInAir;
    private int ticksOnEntity;
    private List<Entity> hitEntities;
    private SoundEvent hitSound = SoundEvents.ENTITY_ARROW_HIT;
    private double damage = 0.0D;

    public GluebombEntity(EntityType<? extends GluebombEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public GluebombEntity(World worldIn, LivingEntity throwerIn) {
        super(GluebombEntityTypes.GLUEBOMB.get(), throwerIn, worldIn);
        this.gluebombThrower = throwerIn;
    }

    public GluebombEntity(FMLPlayMessages.SpawnEntity packet, World worldIn) {
        super(GluebombEntityTypes.GLUEBOMB.get(), worldIn);
    }

    @OnlyIn(Dist.CLIENT)
    public GluebombEntity(World worldIn, double x, double y, double z) {
        super(EntityType.ENDER_PEARL, x, y, z, worldIn);
    }

    @Override
    protected Item getDefaultItem() {
        return GluebombModItems.GLUEBOMB_ITEM.get();
    }

    public void tick() {
        super.tick();
        Vec3d vec3d = this.getMotion();
        BlockPos blockpos = new BlockPos(this);
        BlockState blockstate = this.world.getBlockState(blockpos);
        if (!blockstate.isAir(this.world, blockpos)) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.world, blockpos);
            if (!voxelshape.isEmpty()) {
                Vec3d vec3d1 = this.getPositionVec();

                for(AxisAlignedBB axisalignedbb : voxelshape.toBoundingBoxList()) {
                    if (axisalignedbb.offset(blockpos).contains(vec3d1)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.inGround) {
            if (this.inBlockState != blockstate && this.world.func_226664_a_(this.getBoundingBox().grow(0.06D))) {
                this.inGround = false;
                this.setMotion(vec3d.mul((double)(this.rand.nextFloat() * 0.2F), (double)(this.rand.nextFloat() * 0.2F), (double)(this.rand.nextFloat() * 0.2F)));
                this.ticksOnEntity = 0;
                this.ticksInAir = 0;
            } else if (!this.world.isRemote) {
                this.func_225516_i_();
            }

            ++this.timeInEntity;
        } else {
            this.timeInEntity = 0;
            ++this.ticksInAir;
            Vec3d vec3d2 = this.getPositionVec();
            Vec3d vec3d3 = vec3d2.add(vec3d);
            RayTraceResult raytraceresult = this.world.rayTraceBlocks(new RayTraceContext(vec3d2, vec3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
            if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
                vec3d3 = raytraceresult.getHitVec();
            }

            while(!this.removed) {
                EntityRayTraceResult entityraytraceresult = this.rayTraceEntities(vec3d2, vec3d3);
                if (entityraytraceresult != null) {
                    raytraceresult = entityraytraceresult;
                }

                if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
                    Entity entity = ((EntityRayTraceResult)raytraceresult).getEntity();
                    Entity entity1 = this.getShooter();
                    if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity)entity1).canAttackPlayer((PlayerEntity)entity)) {
                        raytraceresult = null;
                        entityraytraceresult = null;
                    }
                }

                if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                    this.onImpact(raytraceresult);
                    this.isAirBorne = true;
                }

                if (entityraytraceresult == null) {
                    break;
                }

                raytraceresult = null;
            }

            vec3d = this.getMotion();
            double d3 = vec3d.x;
            double d4 = vec3d.y;
            double d0 = vec3d.z;
            /*if (this.getIsCritical()) {
                for(int i = 0; i < 4; ++i) {
                    this.world.addParticle(ParticleTypes.CRIT, this.getPosX() + d3 * (double)i / 4.0D, this.getPosY() + d4 * (double)i / 4.0D, this.getPosZ() + d0 * (double)i / 4.0D, -d3, -d4 + 0.2D, -d0);
                }
            }*/

            double d5 = this.getPosX() + d3;
            double d1 = this.getPosY() + d4;
            double d2 = this.getPosZ() + d0;
            float f2 = 0.99F;
            if (this.isInWater()) {
                for(int j = 0; j < 4; ++j)
                    this.world.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
                f2 = 0.6F;
            }

            this.setMotion(vec3d.scale(f2));
            if (!this.hasNoGravity()) {
                Vec3d vec3d4 = this.getMotion();
                this.setMotion(vec3d4.x, vec3d4.y - (double)0.05F, vec3d4.z);
            }

            this.setPosition(d5, d1, d2);
            this.doBlockCollisions();
        }
    }

    protected void func_225516_i_() {
        ++this.ticksOnEntity;
        if (this.ticksOnEntity >= 800) {
            LogManager.getLogger().info("BOOM");
            this.remove();
        }
    }

    @Nullable
    public Entity getShooter() {
        return this.gluebombThrower != null && this.world instanceof ServerWorld ? ((ServerWorld)this.world).getEntityByUuid(this.gluebombThrower.getUniqueID()) : null;
    }

    /**
     * Gets the EntityRayTraceResult representing the entity hit
     */
    @Nullable
    protected EntityRayTraceResult rayTraceEntities(Vec3d startVec, Vec3d endVec) {
        return ProjectileHelper.rayTraceEntities(this.world, this, startVec, endVec, this.getBoundingBox().expand(this.getMotion()).grow(1.0D), (p_213871_1_) ->
                !p_213871_1_.isSpectator() && p_213871_1_.isAlive() && p_213871_1_.canBeCollidedWith() && (p_213871_1_ != this.getShooter() || this.ticksInAir >= 5));
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        LivingEntity livingentity = this.getThrower();
        if (result.getType() == RayTraceResult.Type.ENTITY)
            this.onEntityHit((EntityRayTraceResult)result);
        else if (result.getType() == RayTraceResult.Type.BLOCK) {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)result;
            BlockState blockstate = this.world.getBlockState(blockraytraceresult.getPos());
            this.inBlockState = blockstate;
            Vec3d vec3d = blockraytraceresult.getHitVec().subtract(this.getPosX(), this.getPosY(), this.getPosZ());
            this.setMotion(vec3d);
            Vec3d vec3d1 = vec3d.normalize().scale((double)0.05F);
            this.setRawPosition(this.getPosX() - vec3d1.x, this.getPosY() - vec3d1.y, this.getPosZ() - vec3d1.z);
            //this.playSound(this.getHitGroundSound(), 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            this.inGround = true;
            this.setHitSound(SoundEvents.ENTITY_ARROW_HIT);
            this.func_213870_w();
            blockstate.onProjectileCollision(this.world, blockstate, blockraytraceresult, this);
        }
        /*for(int i = 0; i < 32; ++i) {
            this.world.addParticle(ParticleTypes.PORTAL, this.getPosX(), this.getPosY() + this.rand.nextDouble() * 2.0D, this.getPosZ(), this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());
        }*/
    }

    private void func_213870_w() {
        if (this.hitEntities != null) {
            this.hitEntities.clear();
        }
    }

    protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
        Entity entity = p_213868_1_.getEntity();
        float f = (float)this.getMotion().length();
        int i = MathHelper.ceil(Math.max((double)f * this.damage, 0.0D));

        Entity entity1 = this.getShooter();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = DamageSource.causeThrownDamage(this, this);
        } else {
            damagesource = DamageSource.causeThrownDamage(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity)entity1).setLastAttackedEntity(entity);
            }
        }

        int j = entity.getFireTimer();
        if (entity.attackEntityFrom(damagesource, (float)i)) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                if (!this.world.isRemote)
                    livingentity.setArrowCountInEntity(livingentity.getArrowCountInEntity() + 1);
                if (livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity)
                    ((ServerPlayerEntity)entity1).connection.sendPacket(new SChangeGameStatePacket(6, 0.0F));
                if (!entity.isAlive() && this.hitEntities != null)
                    this.hitEntities.add(livingentity);
            }

            this.playSound(this.hitSound, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            this.remove();
        } else {
            entity.setFireTimer(j);
            this.setMotion(this.getMotion().scale(-0.1D));
            this.ticksInAir = 0;
            if (!this.world.isRemote && this.getMotion().lengthSquared() < 1.0E-7D)
                this.remove();
        }
    }

    @Override
    public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
        super.shoot(entityThrower, rotationPitchIn, rotationYawIn, pitchOffset, velocity, inaccuracy);
        this.ticksOnEntity = 0;
    }

    public void setHitSound(SoundEvent soundIn) {
        this.hitSound = soundIn;
    }
}
