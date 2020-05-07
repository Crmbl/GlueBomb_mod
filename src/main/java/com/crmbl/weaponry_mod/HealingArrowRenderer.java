package com.crmbl.weaponry_mod;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HealingArrowRenderer extends EntityRenderer<HealingArrowEntity> {
    public static final ResourceLocation RES_HEALING_ARROW = new ResourceLocation("weaponry_mod:textures/item/healing_arrow_item.png");
    private final net.minecraft.client.renderer.ItemRenderer itemRenderer;

    public HealingArrowRenderer(EntityRendererManager manager) {
        super(manager);
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
        this.shadowSize = 0F;
        this.shadowOpaque = 0.75F;
    }

    @Override
    public ResourceLocation getEntityTexture(HealingArrowEntity entity) {
        return RES_HEALING_ARROW;
    }

    public void render(HealingArrowEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        ItemStack itemstack = entityIn.getArrowStack();
        IBakedModel ibakedModel = this.itemRenderer.getItemModelWithOverrides(itemstack, entityIn.world, null);

        matrixStackIn.push();
        matrixStackIn.scale(1.7F, 1.7F, 1.7F);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) - 90.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch) - 45.0F));
        matrixStackIn.translate(0.0, -0.17, 0.0);

        this.itemRenderer.renderItem(itemstack, ItemCameraTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, ibakedModel);
        matrixStackIn.pop();

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

}
