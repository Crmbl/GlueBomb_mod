package com.crmbl.gluebomb_mod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class GluebombItem extends Item {
    public GluebombItem(Item.Properties builder) {
        super(builder);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldownTracker().setCooldown(this, 20);
        if (!worldIn.isRemote) {
            GluebombEntity glueBombEntity = new GluebombEntity(worldIn, playerIn);
            glueBombEntity.setItem(itemstack);
            glueBombEntity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.addEntity(glueBombEntity);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode)
            itemstack.shrink(1);

        return ActionResult.resultSuccess(itemstack);
    }
}
