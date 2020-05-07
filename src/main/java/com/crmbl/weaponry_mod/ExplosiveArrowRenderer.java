package com.crmbl.weaponry_mod;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExplosiveArrowRenderer extends ArrowRenderer<ExplosiveArrowEntity> {
    public static final ResourceLocation RES_EXPLOSIVE_ARROW = new ResourceLocation("weaponry_mod:textures/entity/projectiles/explosive_arrow_item.png");

    public ExplosiveArrowRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(ExplosiveArrowEntity entity) {
        return RES_EXPLOSIVE_ARROW;
    }
}
