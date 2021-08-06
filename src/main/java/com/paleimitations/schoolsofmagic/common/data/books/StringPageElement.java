package com.paleimitations.schoolsofmagic.common.data.books;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StringPageElement extends PageElement {

	public final String[] text;
	public final int fontColor, height, width;
	public final boolean centered;

	public StringPageElement(String text, int x, int y, int width, int height, int fontColor, boolean centered) {
		super(x, y);
		this.text = new String[1];
		this.text[0] = text;
		this.width = width;
		this.height = height;
		this.fontColor = fontColor;
		this.centered = centered;
	}
	
	public StringPageElement(String[] text, int x, int y, int width, int height, int fontColor, boolean centered) {
		super(x, y);
		this.text = text;
		this.width = width;
		this.height = height;
		this.fontColor = fontColor;
		this.centered = centered;
	}

	public StringPageElement(String[] text, int x, int y, int target, int width, int height, int fontColor, boolean centered) {
		super(x, y, target);
		this.text = text;
		this.width = width;
		this.height = height;
		this.fontColor = fontColor;
		this.centered = centered;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void drawElement(MatrixStack matrixStack, float mouseX, float mouseY, int xIn, int yIn, float zLevel, boolean isGUI, int target, int light) {
		Minecraft mc = Minecraft.getInstance();
		FontRenderer font = mc.font;
		matrixStack.pushPose();
		String t = I18n.get(text[0]);
		if(text.length>1)
			for(int i = 1; i < text.length; ++ i)
				if(text[i]!=null)
					t += " " + I18n.get(text[i]);
		float textWidth = mc.font.width(t);
		float textHeight = mc.font.lineHeight;
		float scale = Math.min(width / textWidth, height / textHeight);

		int drawX = x + xIn;
		int drawY = y + yIn;
		if(this.centered) {
			drawX = Math.round(this.x + xIn - (textWidth*scale / 2f));
			drawY = Math.round(this.y + yIn - (textHeight*scale / 2f));
		}

		matrixStack.scale(scale, scale, scale);
        font.draw(matrixStack, t, Math.round(drawX/scale), Math.round(drawY/scale), fontColor);
		matrixStack.popPose();
	}

}
