package com.paleimitations.schoolsofmagic.common.data.books;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class PageElement {

	public final int x, y, subpage;

	public PageElement() {
		this(0,0,0);
	}

	public PageElement(int x, int y) {
		this(x,y,0);
	}

	public PageElement(int x, int y, int subpage) {
		this.x = x;
		this.y = y;
		this.subpage = subpage;
	}

	public int getSubpage() {
		return subpage;
	}

	public boolean isTarget(int i) {
		return i == subpage;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean hasSubpage(int subpage) {
		return this.subpage == subpage;
	}

	@OnlyIn(Dist.CLIENT)
	public void drawElement(MatrixStack matrixStack, float mouseX, float mouseY, int x, int y, float zLevel, boolean isGUI, int subpage, int light) {
	}

	@OnlyIn(Dist.CLIENT)
	public void drawElement(MatrixStack matrixStack, float mouseX, float mouseY, int x, int y, float zLevel, boolean isGUI, int subpage, int light, IRenderTypeBuffer buffer) {
		this.drawElement(matrixStack, mouseX, mouseY, x, y, zLevel, isGUI, subpage, light);
	}

	@OnlyIn(Dist.CLIENT)
	public static void drawString(FontRenderer font, String s, float x, float y, int color, MatrixStack matrix, int light) {
		IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.immediate(Tessellator.getInstance().getBuilder());
		font.drawInBatch(s, x, y, color, false, matrix.last().pose(), irendertypebuffer$impl,false, 0, light);
		irendertypebuffer$impl.endBatch();
	}

	public static void blit(MatrixStack matrix, int x, int y, float z, int p_238470_4_, int p_238470_5_, TextureAtlasSprite p_238470_6_, int light) {
		innerBlit(matrix.last().pose(), x, x + p_238470_4_, y, y + p_238470_5_, z, p_238470_6_.getU0(), p_238470_6_.getU1(), p_238470_6_.getV0(), p_238470_6_.getV1(), light);
	}

	public void blit(MatrixStack matrix, int x, int y, int u, int v, int width, int height, int light) {
		blit(matrix, x, y, 0f, (float)u, (float)v, width, height, 256, 256, light);
	}

	public static void blit(MatrixStack matrix, int x, int y, float z, float u, float v, int width, int height, int imageWidth, int imageHeight, int light) {
		innerBlit(matrix, x, x + width, y, y + height, z, width, height, u, v, imageHeight, imageWidth, light);
	}

	public static void blit(MatrixStack matrix, int x, int y, int width, int height, float p_238466_5_, float p_238466_6_, int p_238466_7_, int p_238466_8_, int p_238466_9_, int p_238466_10_, int light) {
		innerBlit(matrix, x, x + width, y, y + height, 0f, p_238466_7_, p_238466_8_, p_238466_5_, p_238466_6_, p_238466_9_, p_238466_10_, light);
	}

	public static void blit(MatrixStack matrix, int p_238463_1_, int p_238463_2_, float p_238463_3_, float p_238463_4_, int p_238463_5_, int p_238463_6_, int p_238463_7_, int p_238463_8_, int light) {
		blit(matrix, p_238463_1_, p_238463_2_, p_238463_5_, p_238463_6_, p_238463_3_, p_238463_4_, p_238463_5_, p_238463_6_, p_238463_7_, p_238463_8_, light);
	}

	private static void innerBlit(MatrixStack matrix, int x1, int x2, int y1, int y2, float z, int p_238469_6_, int p_238469_7_, float p_238469_8_, float p_238469_9_, int imageU, int imageV, int light) {
		innerBlit(matrix.last().pose(), x1, x2, y1, y2, z, (p_238469_8_ + 0.0F) / (float)imageU, (p_238469_8_ + (float)p_238469_6_) / (float)imageU, (p_238469_9_ + 0.0F) / (float)imageV, (p_238469_9_ + (float)p_238469_7_) / (float)imageV, light);
	}

	private static void innerBlit(Matrix4f matrix, int x1, int x2, int y1, int y2, float z, float u1, float u2, float v1, float v2, int light) {
		BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
		//bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer();
		RenderSystem.enableAlphaTest();
		RenderSystem.defaultAlphaFunc();
		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(true);
		bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP);
		bufferbuilder.vertex(matrix, (float)x1, (float)y2, z).color(1f,1f,1f,1f).uv(u1, v2).uv2(light).endVertex();
		bufferbuilder.vertex(matrix, (float)x2, (float)y2, z).color(1f,1f,1f,1f).uv(u2, v2).uv2(light).endVertex();
		bufferbuilder.vertex(matrix, (float)x2, (float)y1, z).color(1f,1f,1f,1f).uv(u2, v1).uv2(light).endVertex();
		bufferbuilder.vertex(matrix, (float)x1, (float)y1, z).color(1f,1f,1f,1f).uv(u1, v1).uv2(light).endVertex();
		//bufferbuilder.end();
		Tessellator.getInstance().end();
		RenderSystem.depthFunc(515);
		RenderSystem.disableBlend();
		RenderSystem.defaultAlphaFunc();
		//WorldVertexBufferUploader.end(bufferbuilder);
		Minecraft.getInstance().gameRenderer.lightTexture().turnOffLightLayer();
	}

	@OnlyIn(Dist.CLIENT)
	public static void drawTexturedModalRect(MatrixStack matrixStack, int x, int y, int u, int v, int width, int height, float zLevel, int light) {
		final float uScale = 1f / 0x100;
		final float vScale = 1f / 0x100;

		Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer();
		RenderSystem.enableAlphaTest();
		RenderSystem.defaultAlphaFunc();
		RenderSystem.enableDepthTest();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder wr = tessellator.getBuilder();
		wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP);
		Matrix4f matrix = matrixStack.last().pose();
		wr.vertex(matrix, x, y + height, zLevel).color(1f,1f,1f,1f).uv( u * uScale, (v + height) * vScale).uv2(light).endVertex();
		wr.vertex(matrix, x + width, y + height, zLevel).color(1f,1f,1f,1f).uv((u + width) * uScale, (v + height) * vScale).uv2(light).endVertex();
		wr.vertex(matrix, x + width, y, zLevel).color(1f,1f,1f,1f).uv((u + width) * uScale, v * vScale).uv2(light).endVertex();
		wr.vertex(matrix, x, y, zLevel).color(1f,1f,1f,1f).uv( u * uScale, v * vScale).uv2(light).endVertex();
		tessellator.end();
		RenderSystem.depthMask(true);
		RenderSystem.depthFunc(515);
		RenderSystem.disableBlend();
		RenderSystem.defaultAlphaFunc();
		Minecraft.getInstance().gameRenderer.lightTexture().turnOffLightLayer();
	}

	@OnlyIn(Dist.CLIENT)
	public void drawItemStack(MatrixStack matrix, ItemStack stack, int x, int y, boolean isGUI, int light, IRenderTypeBuffer buffer) {
		Minecraft minecraft = Minecraft.getInstance();
		ItemRenderer itemRender = minecraft.getItemRenderer();
		if(stack!=null) {
			if (isGUI) {
				itemRender.blitOffset = 100.0F;
				itemRender.renderAndDecorateItem(stack, x, y);
				itemRender.blitOffset = 0.0F;
			} else {
				matrix.pushPose();
				matrix.translate((float) x, (float) y, 0);
				matrix.translate(8.0F, 8.0F, 0.0F);
				matrix.scale(16F, -16F, -16F);
				matrix.mulPose(Vector3f.YP.rotationDegrees(180.0F));
				itemRender.renderStatic(stack, ItemCameraTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY, matrix, buffer);
				matrix.popPose();

				//stack.getItem().getItemStackTileEntityRenderer().renderByItem(stack, ItemCameraTransforms.TransformType.FIXED, matrix, renderType, combinedLight, OverlayTexture.NO_OVERLAY);
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	protected void renderTooltip(MatrixStack p_230457_1_, ItemStack p_230457_2_, int p_230457_3_, int p_230457_4_) {
		Minecraft minecraft = Minecraft.getInstance();
		FontRenderer font = p_230457_2_.getItem().getFontRenderer(p_230457_2_);
		net.minecraftforge.fml.client.gui.GuiUtils.preItemToolTip(p_230457_2_);
		this.renderWrappedToolTip(p_230457_1_, this.getTooltipFromItem(p_230457_2_), p_230457_3_, p_230457_4_, (font == null ? minecraft.font : font));
		net.minecraftforge.fml.client.gui.GuiUtils.postItemToolTip();
	}

	@OnlyIn(Dist.CLIENT)
	public List<ITextComponent> getTooltipFromItem(ItemStack p_231151_1_) {
		Minecraft minecraft = Minecraft.getInstance();
		return p_231151_1_.getTooltipLines(minecraft.player, minecraft.options.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);
	}

	@OnlyIn(Dist.CLIENT)
	public void renderTooltip(MatrixStack p_238652_1_, ITextComponent p_238652_2_, int p_238652_3_, int p_238652_4_) {
		this.renderComponentTooltip(p_238652_1_, Arrays.asList(p_238652_2_), p_238652_3_, p_238652_4_);
	}

	@OnlyIn(Dist.CLIENT)
	public void renderComponentTooltip(MatrixStack p_243308_1_, List<ITextComponent> p_243308_2_, int p_243308_3_, int p_243308_4_) {
		Minecraft minecraft = Minecraft.getInstance();
		this.renderWrappedToolTip(p_243308_1_, p_243308_2_, p_243308_3_, p_243308_4_, minecraft.font);
	}

	@OnlyIn(Dist.CLIENT)
	public void renderWrappedToolTip(MatrixStack matrixStack, List<? extends net.minecraft.util.text.ITextProperties> tooltips, int mouseX, int mouseY, FontRenderer font) {
		Minecraft minecraft = Minecraft.getInstance();
		net.minecraftforge.fml.client.gui.GuiUtils.drawHoveringText(matrixStack, tooltips, mouseX, mouseY, minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight(), -1, font);
	}

	@OnlyIn(Dist.CLIENT)
	public void renderTooltip(MatrixStack p_238654_1_, List<? extends IReorderingProcessor> p_238654_2_, int p_238654_3_, int p_238654_4_) {
		Minecraft minecraft = Minecraft.getInstance();
		this.renderToolTip(p_238654_1_, p_238654_2_, p_238654_3_, p_238654_4_, minecraft.font);
	}

	@OnlyIn(Dist.CLIENT)
	public void renderToolTip(MatrixStack p_238654_1_, List<? extends IReorderingProcessor> p_238654_2_, int p_238654_3_, int p_238654_4_, FontRenderer font) {
		Minecraft minecraft = Minecraft.getInstance();
		if (!p_238654_2_.isEmpty()) {
			int i = 0;

			for(IReorderingProcessor ireorderingprocessor : p_238654_2_) {
				int j = font.width(ireorderingprocessor);
				if (j > i) {
					i = j;
				}
			}

			int i2 = p_238654_3_ + 12;
			int j2 = p_238654_4_ - 12;
			int k = 8;
			if (p_238654_2_.size() > 1) {
				k += 2 + (p_238654_2_.size() - 1) * 10;
			}

			if (i2 + i > minecraft.getWindow().getWidth()) {
				i2 -= 28 + i;
			}

			if (j2 + k + 6 > minecraft.getWindow().getHeight()) {
				j2 = minecraft.getWindow().getHeight() - k - 6;
			}

			p_238654_1_.pushPose();
			int l = -267386864;
			int i1 = 1347420415;
			int j1 = 1344798847;
			int k1 = 400;
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuilder();
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
			Matrix4f matrix4f = p_238654_1_.last().pose();
			fillGradient(matrix4f, bufferbuilder, i2 - 3, j2 - 4, i2 + i + 3, j2 - 3, 400, -267386864, -267386864);
			fillGradient(matrix4f, bufferbuilder, i2 - 3, j2 + k + 3, i2 + i + 3, j2 + k + 4, 400, -267386864, -267386864);
			fillGradient(matrix4f, bufferbuilder, i2 - 3, j2 - 3, i2 + i + 3, j2 + k + 3, 400, -267386864, -267386864);
			fillGradient(matrix4f, bufferbuilder, i2 - 4, j2 - 3, i2 - 3, j2 + k + 3, 400, -267386864, -267386864);
			fillGradient(matrix4f, bufferbuilder, i2 + i + 3, j2 - 3, i2 + i + 4, j2 + k + 3, 400, -267386864, -267386864);
			fillGradient(matrix4f, bufferbuilder, i2 - 3, j2 - 3 + 1, i2 - 3 + 1, j2 + k + 3 - 1, 400, 1347420415, 1344798847);
			fillGradient(matrix4f, bufferbuilder, i2 + i + 2, j2 - 3 + 1, i2 + i + 3, j2 + k + 3 - 1, 400, 1347420415, 1344798847);
			fillGradient(matrix4f, bufferbuilder, i2 - 3, j2 - 3, i2 + i + 3, j2 - 3 + 1, 400, 1347420415, 1347420415);
			fillGradient(matrix4f, bufferbuilder, i2 - 3, j2 + k + 2, i2 + i + 3, j2 + k + 3, 400, 1344798847, 1344798847);
			RenderSystem.enableDepthTest();
			RenderSystem.disableTexture();
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.shadeModel(7425);
			bufferbuilder.end();
			WorldVertexBufferUploader.end(bufferbuilder);
			RenderSystem.shadeModel(7424);
			RenderSystem.disableBlend();
			RenderSystem.enableTexture();
			IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.immediate(Tessellator.getInstance().getBuilder());
			p_238654_1_.translate(0.0D, 0.0D, 400.0D);

			for(int l1 = 0; l1 < p_238654_2_.size(); ++l1) {
				IReorderingProcessor ireorderingprocessor1 = p_238654_2_.get(l1);
				if (ireorderingprocessor1 != null) {
					minecraft.font.drawInBatch(ireorderingprocessor1, (float)i2, (float)j2, -1, true, matrix4f, irendertypebuffer$impl, false, 0, 15728880);
				}

				if (l1 == 0) {
					j2 += 2;
				}

				j2 += 10;
			}

			irendertypebuffer$impl.endBatch();
			p_238654_1_.popPose();
		}
	}

	@OnlyIn(Dist.CLIENT)
	protected static void fillGradient(Matrix4f p_238462_0_, BufferBuilder p_238462_1_, int p_238462_2_, int p_238462_3_, int p_238462_4_, int p_238462_5_, int p_238462_6_, int p_238462_7_, int p_238462_8_) {
		float f = (float)(p_238462_7_ >> 24 & 255) / 255.0F;
		float f1 = (float)(p_238462_7_ >> 16 & 255) / 255.0F;
		float f2 = (float)(p_238462_7_ >> 8 & 255) / 255.0F;
		float f3 = (float)(p_238462_7_ & 255) / 255.0F;
		float f4 = (float)(p_238462_8_ >> 24 & 255) / 255.0F;
		float f5 = (float)(p_238462_8_ >> 16 & 255) / 255.0F;
		float f6 = (float)(p_238462_8_ >> 8 & 255) / 255.0F;
		float f7 = (float)(p_238462_8_ & 255) / 255.0F;
		p_238462_1_.vertex(p_238462_0_, (float)p_238462_4_, (float)p_238462_3_, (float)p_238462_6_).color(f1, f2, f3, f).endVertex();
		p_238462_1_.vertex(p_238462_0_, (float)p_238462_2_, (float)p_238462_3_, (float)p_238462_6_).color(f1, f2, f3, f).endVertex();
		p_238462_1_.vertex(p_238462_0_, (float)p_238462_2_, (float)p_238462_5_, (float)p_238462_6_).color(f5, f6, f7, f4).endVertex();
		p_238462_1_.vertex(p_238462_0_, (float)p_238462_4_, (float)p_238462_5_, (float)p_238462_6_).color(f5, f6, f7, f4).endVertex();
	}

	@OnlyIn(Dist.CLIENT)
	protected void renderComponentHoverEffect(MatrixStack p_238653_1_, @Nullable Style p_238653_2_, int p_238653_3_, int p_238653_4_) {
		Minecraft minecraft = Minecraft.getInstance();
		if (p_238653_2_ != null && p_238653_2_.getHoverEvent() != null) {
			HoverEvent hoverevent = p_238653_2_.getHoverEvent();
			HoverEvent.ItemHover hoverevent$itemhover = hoverevent.getValue(HoverEvent.Action.SHOW_ITEM);
			if (hoverevent$itemhover != null) {
				this.renderTooltip(p_238653_1_, hoverevent$itemhover.getItemStack(), p_238653_3_, p_238653_4_);
			} else {
				HoverEvent.EntityHover hoverevent$entityhover = hoverevent.getValue(HoverEvent.Action.SHOW_ENTITY);
				if (hoverevent$entityhover != null) {
					if (minecraft.options.advancedItemTooltips) {
						this.renderComponentTooltip(p_238653_1_, hoverevent$entityhover.getTooltipLines(), p_238653_3_, p_238653_4_);
					}
				} else {
					ITextComponent itextcomponent = hoverevent.getValue(HoverEvent.Action.SHOW_TEXT);
					if (itextcomponent != null) {
						this.renderTooltip(p_238653_1_, minecraft.font.split(itextcomponent, Math.max(minecraft.getWindow().getWidth() / 2, 200)), p_238653_3_, p_238653_4_);
					}
				}
			}

		}
	}
	
}
