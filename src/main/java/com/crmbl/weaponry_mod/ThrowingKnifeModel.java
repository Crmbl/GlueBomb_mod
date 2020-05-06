package com.crmbl.weaponry_mod;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.client.model.obj.OBJModel;

@OnlyIn(Dist.CLIENT)
public class ThrowingKnifeModel extends Model {
    private final ModelRenderer modelRenderer = new ModelRenderer(32, 32, 0, 0);
    private OBJModel objModel = OBJLoader.INSTANCE.loadModel(new OBJModel.ModelSettings(
        new ResourceLocation("weaponry_mod:models/item/throwing_knife_model.obj"),
            true,
            true,
            false,
            true,
            null)
        );

    public ThrowingKnifeModel() {
        super(RenderType::getEntitySolid);

        /*ModelRenderer pommelRenderer = new ModelRenderer(32, 32, 0, 0);
        //pommelRenderer.setRotationPoint(0.65F, 0.65F, 0.65F);
        //pommelRenderer.rotateAngleZ = 45.0F;
        pommelRenderer.addBox(0, 6, 0, 1.3F, 1.3F, 1.3F);

        ModelRenderer handleRenderer = new ModelRenderer(32, 32, 1, 0);
        handleRenderer.addBox(0.15F, 4.3F, 0.15F, 1, 2.2F, 1);

        ModelRenderer hiltRenderer1 = new ModelRenderer(32, 32, 2, 0);
        //hiltRenderer1.setRotationPoint(-0.35F, 4.15F, 0.65F);
        //hiltRenderer1.rotateAngleZ = 45.0F;
        hiltRenderer1.addBox(-1, 3.45F, 0, 1.3F, 1.3F, 1.3F);

        ModelRenderer hiltRenderer2 = new ModelRenderer(32, 32, 3, 0);
        //hiltRenderer2.setRotationPoint(0.65F, 4.15F, 0.65F);
        //hiltRenderer2.rotateAngleZ = 45.0F;
        hiltRenderer2.addBox(0, 3.45F, 0, 1.3F, 1.3F, 1.3F);

        ModelRenderer hiltRenderer3 = new ModelRenderer(32, 32, 0, 0);
        //hiltRenderer3.setRotationPoint(1.65F, 4.15F, 0.65F);
        //hiltRenderer3.rotateAngleZ = 45.0F;
        hiltRenderer3.addBox(1, 3.45F, 0, 1.3F, 1.3F, 1.3F);

        ModelRenderer bladeRenderer1 = new ModelRenderer(32, 32, 0, 0);
        bladeRenderer1.addBox(-0.25F, 2.7F, 0.15F, 1.8F, 1.2F, 1);

        ModelRenderer bladeRenderer2 = new ModelRenderer(32, 32, 0, 0);
        bladeRenderer2.addBox(0.15F, 0.8F, 0.15F, 1, 2.1F, 1);

        ModelRenderer bladeRenderer3 = new ModelRenderer(32, 32, 0, 0);
        //bladeRenderer3.setRotationPoint(0.65F, 0.8F, 0.65F);
        //bladeRenderer3.rotateAngleZ = 45.0F;
        bladeRenderer3.addBox(0.3F, 0.45F, 0.15F, 0.7F, 0.7F, 1);

        this.modelRenderer.addChild(pommelRenderer);
        this.modelRenderer.addChild(handleRenderer);
        this.modelRenderer.addChild(hiltRenderer1);
        this.modelRenderer.addChild(hiltRenderer2);
        this.modelRenderer.addChild(hiltRenderer3);
        this.modelRenderer.addChild(bladeRenderer1);
        this.modelRenderer.addChild(bladeRenderer2);
        this.modelRenderer.addChild(bladeRenderer3);*/
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}