package com.crmbl.weaponry_mod;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HealingArrowRenderer extends ArrowRenderer<HealingArrowEntity> {
    public static final ResourceLocation RES_HEALING_ARROW = new ResourceLocation("weaponry_mod:textures/entity/projectiles/healing_arrow_item.png");

    public HealingArrowRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(HealingArrowEntity entity) {
        return RES_HEALING_ARROW;
    }
}
