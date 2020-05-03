package com.crmbl.gluebomb_mod;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SnowballItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class GluebombModEventHandler {

    /*@SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            PlayerEntity player = event.player;
            if (player != null && !player.abilities.isCreativeMode) {
                boolean isInWater = player.isInWater();
                boolean isInLava = player.isInLava();
                ItemStack stack = player.inventory.armorInventory.get(2);
                boolean isFlyingModItem = stack.getItem() instanceof FlyingModItem;
                if (event.side == LogicalSide.SERVER) {
                    if ((player.abilities.allowFlying && !isFlyingModItem) || isInWater || isInLava) {
                        player.abilities.allowFlying = false;
                        player.abilities.isFlying = false;
                        player.sendPlayerAbilities();
                        FlyingModPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> player), new FlyingModPacket(player.getEntityId(), player.abilities.isFlying));
                    }
                    if (!player.abilities.allowFlying && isFlyingModItem && !isInWater && !isInLava) {
                        player.abilities.allowFlying = true;
                        player.sendPlayerAbilities();
                        FlyingModPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> player), new FlyingModPacket(player.getEntityId(), player.abilities.isFlying));
                    }

                    if (isFlyingModItem) {
                        CompoundNBT tag = stack.getOrCreateTag();
                        boolean isFlying = tag.getBoolean("flying_mod_flying_item");
                        if (isFlying != player.abilities.isFlying) {
                            tag.putBoolean("flying_mod_flying_item", player.abilities.isFlying);
                            stack.setTag(tag);
                            FlyingModPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> player), new FlyingModPacket(event.player.getEntityId(), player.abilities.isFlying));
                        }
                    }
                }

                if (event.side == LogicalSide.CLIENT) {
                    if (player.abilities.allowFlying && player.abilities.getFlySpeed() != 0.02f) {
                        player.abilities.setFlySpeed(0.04f);
                        player.sendPlayerAbilities();
                    }
                    if (!player.abilities.allowFlying && player.abilities.getFlySpeed() != 0.05f) {
                        player.abilities.setFlySpeed(0.05f);
                        player.sendPlayerAbilities();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = ((PlayerEntity) event.getEntity());

            if (player != null) {
                ItemStack itemStack = player.getItemStackFromSlot(EquipmentSlotType.CHEST);
                if (itemStack.getItem() instanceof FlyingModItem)
                    itemStack.damageItem(10, player, playerEntity -> {  });
            }
        }
    }

    @SubscribeEvent
    public void onLivingDead(LivingDeathEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = ((PlayerEntity) event.getEntity());
            if (player != null) {
                ItemStack itemStack = player.getItemStackFromSlot(EquipmentSlotType.CHEST);
                if (itemStack.getItem() instanceof FlyingModItem)
                    itemStack.damageItem(itemStack.getMaxDamage(), player, playerEntity -> {});
            }
        }
    }*/

    /*@SubscribeEvent
    public void onClientSetup (FMLClientSetupEvent event) {
        //ModelLoader.addSpecialModel(GluebombModItems.GLUEBOMB_ITEM.get().getModelResourceLocation());
    }*/

}
