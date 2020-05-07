package com.crmbl.weaponry_mod;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ExplosiveArrowItem extends ArrowItem {
    public ExplosiveArrowItem(Properties properties) {
        super(properties);
    }

    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        return new ExplosiveArrowEntity(worldIn, shooter);
    }
}
