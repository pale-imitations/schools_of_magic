package com.paleimitations.schoolsofmagic.client.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class TeacupModel extends EntityModel<Entity> {
	private final ModelRenderer bb_main;
	private final ModelRenderer handle_bot_r1;
	private final ModelRenderer base_r1;

	public TeacupModel() {
		texWidth = 32;
		texHeight = 32;

		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		bb_main.texOffs(0, 6).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, true);
		bb_main.texOffs(10, 6).addBox(-2.5F, -2.0F, -2.5F, 5.0F, 1.0F, 5.0F, 0.0F, false);
		bb_main.texOffs(20, 26).addBox(-2.5F, -7.0F, -3.5F, 5.0F, 5.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(7, 26).addBox(-2.5F, -7.0F, 2.5F, 5.0F, 5.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(20, 15).addBox(2.5F, -7.0F, -2.5F, 1.0F, 5.0F, 5.0F, 0.0F, false);
		bb_main.texOffs(7, 15).addBox(-3.5F, -7.0F, -2.5F, 1.0F, 5.0F, 5.0F, 0.0F, false);
		bb_main.texOffs(0, 10).addBox(-0.5F, -6.0F, -5.5F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		bb_main.texOffs(5, 15).addBox(-0.5F, -5.0F, -5.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(12, 0).addBox(-2.5F, -6.0F, -2.5F, 5.0F, 0.0F, 5.0F, 0.0F, false);

		handle_bot_r1 = new ModelRenderer(this);
		handle_bot_r1.setPos(0.0F, -3.0F, -5.5F);
		bb_main.addChild(handle_bot_r1);
		setRotationAngle(handle_bot_r1, 0.9163F, 0.0F, 0.0F);
		handle_bot_r1.texOffs(0, 14).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);

		base_r1 = new ModelRenderer(this);
		base_r1.setPos(0.0F, 0.0F, 0.0F);
		bb_main.addChild(base_r1);
		setRotationAngle(base_r1, 0.0F, -0.7854F, 0.0F);
		base_r1.texOffs(0, 0).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(Entity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}