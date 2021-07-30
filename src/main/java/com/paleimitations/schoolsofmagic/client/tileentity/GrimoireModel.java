package com.paleimitations.schoolsofmagic.client.tileentity;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookDataProvider;
import com.paleimitations.schoolsofmagic.common.tileentities.PodiumTileEntity;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;


public class GrimoireModel extends EntityModel<Entity> {
	private final ModelRenderer book_bone;
	private final ModelRenderer front_cover_bone;
	private final ModelRenderer back_cover_bone;
	private final ModelRenderer page_bone;

	public GrimoireModel() {
		texWidth = 320;
		texHeight = 320;

		book_bone = new ModelRenderer(this);
		book_bone.setPos(8.0F, 24.0F, 0.0F);
		book_bone.texOffs(0, 0).addBox(-14.0F, -86.0F, 0.0F, 12.0F, 86.0F, 8.0F, 0.0F, false);

		front_cover_bone = new ModelRenderer(this);
		front_cover_bone.setPos(-11.0F, 0.0F, 3.0F);
		book_bone.addChild(front_cover_bone);
		front_cover_bone.texOffs(188, 0).addBox(-59.0F, -85.5F, 0.9F, 62.0F, 85.0F, 2.0F, 0.0F, false);
		front_cover_bone.texOffs(55, 0).addBox(-52.0F, -83.6F, -7.0F, 55.0F, 81.0F, 8.0F, 0.0F, false);

		back_cover_bone = new ModelRenderer(this);
		back_cover_bone.setPos(-5.0F, 0.0F, 3.0F);
		book_bone.addChild(back_cover_bone);
		back_cover_bone.texOffs(188, 100).addBox(-3.0F, -85.5F, 0.9F, 62.0F, 85.0F, 2.0F, 0.0F, false);
		back_cover_bone.texOffs(55, 100).addBox(-3.0F, -83.5F, -7.0F, 55.0F, 81.0F, 8.0F, 0.0F, false);

		page_bone = new ModelRenderer(this);
		page_bone.setPos(-3.0F, 0.0F, -7.0F);
		back_cover_bone.addChild(page_bone);
		setRotationAngle(page_bone, 0.0F, 1.6144F, 0.0F);
		page_bone.texOffs(0, 200).addBox(0.0F, -83.5F, 0.0F, 55.0F, 81.0F, 0.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(Entity p_225597_1_, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	public void render(PodiumTileEntity te, int tick, MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		float progress = 0;
		this.page_bone.visible = false;
		PodiumTileEntity.BookState bookstate = te.bookState;
		switch(te.bookState){
			case CLOSED:
				this.back_cover_bone.yRot = (float)Math.toRadians(90d);
				this.front_cover_bone.yRot = (float)Math.toRadians(-90d);
				this.book_bone.zRot = (float)Math.toRadians(90d);
				this.book_bone.xRot = (float)Math.toRadians(-90d);
				break;
			case OPEN_BOOK:
				progress = ((float)tick/(float)bookstate.getAnimationLength());
				this.back_cover_bone.yRot = (float)Math.toRadians(90d-90d*progress);
				this.front_cover_bone.yRot = (float)Math.toRadians(-90d+90d*progress);
				this.book_bone.zRot = (float)Math.toRadians(90d-90d*progress);
				this.book_bone.xRot = (float)Math.toRadians(-90d+90d*progress);
				break;
			case CLOSE_BOOK:
				progress = ((float)tick/(float)bookstate.getAnimationLength());
				this.back_cover_bone.yRot = (float)Math.toRadians(90d*progress);
				this.front_cover_bone.yRot = (float)Math.toRadians(-90d*progress);
				this.book_bone.zRot = (float)Math.toRadians(90d*progress);
				this.book_bone.xRot = (float)Math.toRadians(-90d*progress);
				break;
			case TURN_PAGE_BACK:
				progress = ((float)tick/(float)bookstate.getAnimationLength());
				this.page_bone.visible = true;
				this.page_bone.yRot = (float)Math.toRadians(180d-180d*progress);
				break;
			case TURN_PAGE_FORWARD:
				progress = ((float)tick/(float)bookstate.getAnimationLength());
				this.page_bone.visible = true;
				this.page_bone.yRot = (float)Math.toRadians(180d*progress);
				break;
		}
		matrixStack.pushPose();
		matrixStack.scale(0.16f, 0.16f, 0.16f);
		book_bone.render(matrixStack, buffer, packedLight, packedOverlay);
		matrixStack.popPose();

		if(bookstate == PodiumTileEntity.BookState.OPEN){
			matrixStack.pushPose();
			matrixStack.translate(-0.632f, -0.8f, -0.0505f);
			matrixStack.scale(0.00493F, 0.00493F, 0.00493F);
			if(te.hasBook()) {
				BookData book = BookDataProvider.getBook(te.getLevel(), te.getItem());
				CompoundNBT nbt = te.getItem().getOrCreateTag();
				int page = nbt.contains("page")? nbt.getInt("page") : 0;
				int subpage = nbt.contains("subpage")? nbt.getInt("subpage") : 0;
				if(book!=null && book.getBookPage(page)!=null) {
					book.getBookPage(page).drawPage(matrixStack, 0, 0, 0, 0, 0f, false, subpage, this.getLightColor(te));
				}
			}
			matrixStack.popPose();
		}
	}

	protected int getLightColor(TileEntity te) {
		return te.getLevel().hasChunkAt(te.getBlockPos()) ? WorldRenderer.getLightColor(te.getLevel(), te.getBlockPos().above()) : 0;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		book_bone.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}