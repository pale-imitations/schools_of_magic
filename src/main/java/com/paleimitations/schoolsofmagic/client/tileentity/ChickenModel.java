package com.paleimitations.schoolsofmagic.client.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ChickenModel extends EntityModel<Entity> {
	private final ModelRenderer bb_main;

	public ChickenModel() {
		texWidth = 32;
		texHeight = 32;

		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		bb_main.texOffs(0, 0).addBox(-4.0F, -5.0F, -2.5F, 8.0F, 5.0F, 6.0F, 0.0F, false);
		bb_main.texOffs(8, 11).addBox(0.0F, -3.0F, -3.5F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(0, 3).addBox(3.0F, -3.0F, -3.0F, 2.0F, 3.0F, 0.0F, 0.0F, false);
		bb_main.texOffs(0, 11).addBox(0.0F, -3.0F, 3.5F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(0, 0).addBox(3.0F, -3.0F, 4.0F, 2.0F, 3.0F, 0.0F, 0.0F, false);
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