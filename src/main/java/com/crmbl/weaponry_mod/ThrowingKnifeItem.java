package com.crmbl.weaponry_mod;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ThrowingKnifeItem extends Item {
    public ThrowingKnifeItem(Item.Properties builder) {
        super(builder);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.getCooldownTracker().setCooldown(this, 5);
        if (!worldIn.isRemote) {
            ThrowingKnifeEntity throwingKnifeEntity = new ThrowingKnifeEntity(worldIn, playerIn, itemstack);
            throwingKnifeEntity.pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;
            throwingKnifeEntity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.4F, 1.0F);
            worldIn.addEntity(throwingKnifeEntity);
            worldIn.playMovingSound(null, throwingKnifeEntity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode)
            itemstack.shrink(1);

        return ActionResult.resultSuccess(itemstack);
    }

    @Override
    public int getDamage(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }
}
