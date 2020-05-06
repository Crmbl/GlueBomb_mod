package com.crmbl.weaponry_mod;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
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
public class ThrowingKnifeRenderer extends EntityRenderer<ThrowingKnifeEntity> {
    public static final ResourceLocation RES_THROWING_KNIFE = new ResourceLocation("weaponry_mod:textures/item/throwing_knife_item.png");
    private final net.minecraft.client.renderer.ItemRenderer itemRenderer;

    public ThrowingKnifeRenderer(EntityRendererManager manager) {
        super(manager);
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
        this.shadowSize = 0F;
        this.shadowOpaque = 0.75F;
    }

    public ResourceLocation getEntityTexture(ThrowingKnifeEntity entity) {
        return RES_THROWING_KNIFE;
    }

    public void render(ThrowingKnifeEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        ItemStack itemstack = entityIn.getArrowStack();
        IBakedModel ibakedModel = this.itemRenderer.getItemModelWithOverrides(itemstack, entityIn.world, null);

        matrixStackIn.push();
        matrixStackIn.scale(1.6F, 1.6F, 1.6F);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) - 90.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch) - 45.0F));
        matrixStackIn.translate(0.0, -0.17, 0.0);

        this.itemRenderer.renderItem(itemstack, ItemCameraTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, ibakedModel);
        matrixStackIn.pop();

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}
