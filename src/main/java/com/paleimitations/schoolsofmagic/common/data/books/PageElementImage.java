package com.paleimitations.schoolsofmagic.common.data.books;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PageElementImage extends PageElement {

	public final ResourceLocation imageLocation;
	public final int xUV, yUV, width, height;
	public final float scale;
	public final boolean blend;
	
	public PageElementImage(ResourceLocation imageLocation, int x, int y, int xUV, int yUV, int width, int height, float scale, boolean blend) {
		super(x, y);
		this.imageLocation = imageLocation;
		this.xUV = xUV;
		this.yUV = yUV;
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.blend = blend;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void drawElement(MatrixStack matrixStack, float mouseX, float mouseY, int xIn, int yIn, float zLevel, boolean isGUI, int target, int light) {
        Minecraft.getInstance().getTextureManager().bind(imageLocation);
		matrixStack.pushPose();
        if(blend && !isGUI)
        	GlStateManager._enableBlend();
		matrixStack.scale(scale, scale, scale);
        this.drawTexturedModalRect(matrixStack, Math.round((x+xIn)/scale), Math.round((y+yIn)/scale), xUV, yUV, width, height, zLevel, light);
        if(blend && !isGUI)
        	GlStateManager._disableBlend();
		matrixStack.popPose();
	}

}
