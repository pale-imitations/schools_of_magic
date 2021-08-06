package com.paleimitations.schoolsofmagic.common.data.books;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ColorizedImagePageElement extends ImagePageElement {

	public final float red, green, blue, alpha;
	
	public ColorizedImagePageElement(ResourceLocation imageLocation, int x, int y, int xUV, int yUV, int width, int height, float scale, int color) {
		super(imageLocation, x, y, xUV, yUV, width, height, scale, false);
        if ((color & -67108864) == 0)
        {
            color |= -16777216;
        }
        this.red = (float)(color >> 16 & 255) / 255.0F;
        this.green = (float)(color >> 8 & 255) / 255.0F;
        this.blue = (float)(color & 255) / 255.0F;
        this.alpha = (float)(color >> 24 & 255) / 255.0F;
	}

	@Override
    @OnlyIn(Dist.CLIENT)
	public void drawElement(MatrixStack matrixStack,  float mouseX, float mouseY, int xIn, int yIn, float zLevel, boolean isGUI, int target, int light) {
		//Minecraft.getMinecraft().getTextureManager().bindTexture(imageLocation);
        matrixStack.pushPose();
        RenderSystem.color4f(red, green, blue, alpha);
        super.drawElement(matrixStack, mouseX, mouseY, xIn, yIn, zLevel, isGUI, target, light);
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        matrixStack.popPose();
	}

}
