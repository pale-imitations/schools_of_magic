package com.paleimitations.schoolsofmagic.common.data.books;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class ChapterEntryPageElement extends ButtonPageElement {

	public final String[] text;
	public final String desc;

	public ChapterEntryPageElement(String[] text, String desc, int pageNumber, int x, int y, int target, int width, int height) {
		super(pageNumber, x, y, target, width, height);
		this.text = text;
		this.desc = desc;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void drawElement(MatrixStack matrixStack, float mouseX, float mouseY, int xIn, int yIn, float zLevel, boolean isGUI, int target, int light) {
		Minecraft mc = Minecraft.getInstance();
		FontRenderer font = mc.font;
		matrixStack.pushPose();
		int textHeight = mc.font.lineHeight;
		float scaler = height / Float.valueOf(textHeight);

		String end = "... "+pageNumber;
		int endWidth = font.width(end);
		String t = I18n.get(text[0]);
		if(text.length>1)
			for(int i = 1; i < text.length; ++ i)
				if(text[i]!=null)
					t += " " + I18n.get(text[i]);
		String txt = truncateStringToWidth(t, Math.round(width / scaler) - endWidth) + end;

		int drawX = x + xIn;
		int drawY = y + yIn;

		matrixStack.scale(scaler, scaler, scaler);
		RenderSystem.color4f(1f,1f,1f,1f);
        font.draw(matrixStack, txt, Math.round(drawX/scaler), Math.round(drawY/scaler), 0);
		if(mouseX>this.x && mouseX<this.x+this.width && mouseY>this.y && mouseY<this.y+this.height && isTarget(subpage))
			font.draw(matrixStack, txt, Math.round(drawX/scaler), Math.round(drawY/scaler)-1, Color.WHITE.getRGB());
		RenderSystem.color4f(1f,1f,1f,1f);
		matrixStack.popPose();

        if(!desc.isEmpty()){
			matrixStack.pushPose();
			float descScaler = 6f / Float.valueOf(textHeight);
			String descEnd = "...";
			int descEndWidth = font.width(descEnd);
			String descTxt = truncateStringToWidth(I18n.get(desc), Math.round(width / descScaler) - descEndWidth) + descEnd;

			matrixStack.scale(descScaler, descScaler, descScaler);
			RenderSystem.color4f(1f,1f,1f,1f);
			font.draw(matrixStack, descTxt, Math.round(drawX/descScaler), Math.round(drawY/descScaler)+10, 0);
			RenderSystem.color4f(1f,1f,1f,1f);
			matrixStack.popPose();
		}
	}

	private static String truncateStringToWidth(String str, int wrapWidth) {
		Minecraft mc = Minecraft.getInstance();
		FontRenderer font = mc.font;
		int j = 0;
		int k;

		for (k = 0; k < str.length(); ++k) {
			char c0 = str.charAt(k);
			j += font.width(String.valueOf(c0));
			if (j > wrapWidth)
				break;
		}

		return str.substring(0,k);
	}

}
