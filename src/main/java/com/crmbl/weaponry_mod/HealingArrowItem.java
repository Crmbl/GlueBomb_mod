package com.crmbl.weaponry_mod;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.World;

public class HealingArrowItem extends ArrowItem {
    public HealingArrowItem(Properties properties) {
        super(properties);
    }

    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        return new HealingArrowEntity(worldIn, shooter);
    }

    @Override
    public boolean isInfinite(ItemStack stack, ItemStack bow, PlayerEntity player) {
        return false;
    }

    @Override
    public boolean isIn(Tag<Item> tagIn) {
        if (tagIn == ItemTags.ARROWS)
            return true;

        return super.isIn(tagIn);
    }
}
