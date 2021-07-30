package com.paleimitations.schoolsofmagic.client.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class BowlModel extends EntityModel<Entity> {
	private final ModelRenderer bb_main;
	private final ModelRenderer east_r1;
	private final ModelRenderer south_r1;

	public BowlModel() {
		texWidth = 32;
		texHeight = 32;

		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		bb_main.texOffs(16, 26).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
		bb_main.texOffs(0, 0).addBox(-4.0F, -1.5F, -4.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);
		bb_main.texOffs(11, 18).addBox(-4.0F, -5.5F, -5.0F, 8.0F, 4.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(0, 17).addBox(-5.0F, -5.5F, -4.0F, 1.0F, 4.0F, 8.0F, 0.0F, false);
		bb_main.texOffs(0, 9).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 0.0F, 8.0F, 0.0F, false);

		east_r1 = new ModelRenderer(this);
		east_r1.setPos(4.5F, -3.5F, 0.0F);
		bb_main.addChild(east_r1);
		setRotationAngle(east_r1, 0.0F, 3.1416F, 0.0F);
		east_r1.texOffs(0, 17).addBox(-0.5F, -2.0F, -4.0F, 1.0F, 4.0F, 8.0F, 0.0F, false);

		south_r1 = new ModelRenderer(this);
		south_r1.setPos(0.0F, -3.5F, 4.5F);
		bb_main.addChild(south_r1);
		setRotationAngle(south_r1, 0.0F, 3.1416F, 0.0F);
		south_r1.texOffs(11, 18).addBox(-4.0F, -2.0F, -0.5F, 8.0F, 4.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(Entity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}