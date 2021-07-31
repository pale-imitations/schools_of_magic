package com.paleimitations.schoolsofmagic.common.data.books;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.MagicElement;
import com.paleimitations.schoolsofmagic.common.MagicSchool;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasMaterialComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.Map;

public class PageElementSpellInfo extends PageElement {

	public final Spell spell;
	public static final ResourceLocation SPELL_ICONS = new ResourceLocation(References.MODID, "textures/gui/books/magic_icons.png");
	public static final ResourceLocation SPELL_ICONS2 = new ResourceLocation(References.MODID, "textures/gui/spell_charge_icons.png");

	public PageElementSpellInfo(Spell spell) {
		super(0, 0);
		this.spell = spell;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void drawElement(MatrixStack matrixStack, float mouseX, float mouseY, int xIn, int yIn, float zLevel, boolean isGUI, int target, int light) {
		Color color = new Color(BookPageSpell.getColorFromElementList(spell.getElements()));

		matrixStack.pushPose();

		/*Minecraft.getInstance().getTextureManager().bind(SPELL_ICONS);
		RenderSystem.color4f((float)color.getRed()/255f, (float)color.getGreen()/255f, (float)color.getBlue()/255f, 1f);
		this.drawTexturedModalRect(23+xIn, 51+yIn, 0, 139+(spell.requiresSpark()?44:0), 95, 26, zLevel);*/
		Minecraft.getInstance().getTextureManager().bind(spell.getSpellIcon());
		RenderSystem.color4f(1f, 1f, 1f, 1f);
		matrixStack.pushPose();
		float sSpell = 26f/32f;
		matrixStack.scale(sSpell, sSpell, sSpell);
		this.blit(matrixStack,Math.round((24+xIn)/sSpell), Math.round((50+yIn)/sSpell), zLevel+(isGUI?1f:-1f), 0, 0, 32, 32, 32, 32, light);
		matrixStack.popPose();

		drawStandardText(matrixStack, I18n.get("spell."+spell.getName()+".name"), 68, 18, 51+34+xIn, 54+10f+yIn, 0, true, false, isGUI, light);

		Minecraft.getInstance().getTextureManager().bind(SPELL_ICONS);
		RenderSystem.color4f((float)color.getRed()/255f, (float)color.getGreen()/255f, (float)color.getBlue()/255f, 1f);
		this.drawTexturedModalRect(matrixStack,96+xIn, 75+yIn, 96, 120, 21, 110, zLevel, light);

		int boxWidth = 95;
		int boxHeight = 22;
		int boxX = 24;
		int boxY = 77;
		int minSpell = spell.getMinimumSpellChargeLevel();
		int maxSpell = 8;
		int totalWidth = (maxSpell + 1 - minSpell) * 33;
		float scaleHeight = (float)boxHeight / 32f;
		float scaleWidth = (float)boxWidth / (float)totalWidth;
		float scaleSpellSlots = Math.min(scaleHeight, scaleWidth);
		matrixStack.pushPose();
		matrixStack.scale(scaleSpellSlots, scaleSpellSlots, scaleSpellSlots);
		float startX = (float)xIn + boxX + (float)boxWidth / 2f - (float)totalWidth * scaleSpellSlots / 2f;
		float startY = (float)yIn + boxY + (float)boxHeight / 2f - 16f * scaleSpellSlots;
		Minecraft.getInstance().getTextureManager().bind(SPELL_ICONS2);
		RenderSystem.color4f(1f, 1f, 1f, 1f);
		for(int i = 0; i <= maxSpell - minSpell; ++ i) {
			int icon = minSpell + i;
			this.drawTexturedModalRect(matrixStack, Math.round((startX + i * 33f * scaleSpellSlots)/scaleSpellSlots), Math.round(startY/scaleSpellSlots), (icon%3) * 32, (icon/3) * 32, 32, 32, zLevel, light);
		}
		matrixStack.popPose();

		/*Minecraft.getInstance().getTextureManager().bind(SPELL_ICONS);
		RenderSystem.color4f((float)color.getRed()/255f, (float)color.getGreen()/255f, (float)color.getBlue()/255f, 1f);
		float f = spell.getCost()/(float)ManaData.getMaxMana(spell.getMinimumMagicianLevel()+1);
		this.drawTexturedModalRect(matrixStack,104+xIn, 96+yIn+Math.round(75f*(1f-f)), 117, 120+Math.round(75f*(1f-f)), 5, Math.round(75f*f), + (isGUI?1f:-1f));
		drawStandardText(String.valueOf(spell.getMinimumMagicianLevel()+1), 11, 11, 107+xIn, 86+yIn, Color.WHITE.getRGB(), true, false, isGUI);
		drawStandardText(String.valueOf(spell.getCost() * (spell.isPerSecond()? 20f : 1f)), 9, 6, 106.5f+xIn, 177.5f+yIn, Color.WHITE.getRGB(), true, false, isGUI);*/

		RenderSystem.color4f((float)color.getRed()/255f, (float)color.getGreen()/255f, (float)color.getBlue()/255f, 1f);
		int i = this.drawMaterialComponentTab(matrixStack, 24+xIn, 100+yIn, zLevel, isGUI, light);
		RenderSystem.color4f((float)color.getRed()/255f, (float)color.getGreen()/255f, (float)color.getBlue()/255f, 1f);
		i = this.drawSchoolComponent(matrixStack, 24+xIn, i, zLevel, isGUI, light);
		RenderSystem.color4f((float)color.getRed()/255f, (float)color.getGreen()/255f, (float)color.getBlue()/255f, 1f);
		i = this.drawElementComponent(matrixStack, 24+xIn, i, zLevel, isGUI, light);
		/*RenderSystem.color4f((float)color.getRed()/255f, (float)color.getGreen()/255f, (float)color.getBlue()/255f, 1f);
		i = this.drawModifierComponent(matrixStack, 22+xIn, i, zLevel, isGUI, 0);
		if(this.getModifierMap().entrySet().size()>1 && i<170) {
			RenderSystem.color4f((float) color.getRed() / 255f, (float) color.getGreen() / 255f, (float) color.getBlue() / 255f, 1f);
			i = this.drawModifierComponent(22 + xIn, i, zLevel, isGUI, 1);
		}
		if(this.getModifierMap().entrySet().size()>2 && i<170) {
			RenderSystem.color4f((float) color.getRed() / 255f, (float) color.getGreen() / 255f, (float) color.getBlue() / 255f, 1f);
			i = this.drawModifierComponent(22 + xIn, i, zLevel, isGUI, 2);
		}
		if(this.getModifierMap().entrySet().size()>3 && i<170) {
			RenderSystem.color4f((float) color.getRed() / 255f, (float) color.getGreen() / 255f, (float) color.getBlue() / 255f, 1f);
			i = this.drawModifierComponent(22 + xIn, i, zLevel, isGUI, 3);
		}
		if(this.getModifierMap().entrySet().size()>4 && i<170) {
			RenderSystem.color4f((float) color.getRed() / 255f, (float) color.getGreen() / 255f, (float) color.getBlue() / 255f, 1f);
			i = this.drawModifierComponent(22 + xIn, i, zLevel, isGUI, 4);
		}*/


		matrixStack.popPose();
        
	}

	public static void drawStandardText(MatrixStack matrixStack, String s, int width, int height, float x, float y, int color, boolean centered, boolean dropShadow, boolean gui, int light) {
		Minecraft mc = Minecraft.getInstance();
		FontRenderer font = mc.font;

		int textWidth = mc.font.width(s);
		int textHeight = mc.font.lineHeight;
		float scalerWidth = width / Float.valueOf(textWidth);
		float scalerHeight = height / Float.valueOf(textHeight);
		float scaler = Math.min(scalerWidth, scalerHeight);
		float drawX = x;
		float drawY = y;

		if(centered) {
			drawX = x - ((float)textWidth*scaler / 2f);
			drawY = y - ((float)textHeight*scaler / 2f);
		}

		matrixStack.pushPose();
		matrixStack.scale(scaler, scaler, scaler);
		matrixStack.translate(0f,0f, gui? 0.1f : -0.1f);
		if(dropShadow) {
			drawString(font, s, drawX / scaler + 0.5f, drawY / scaler + 0.5f, 0, matrixStack, light);
			matrixStack.translate(0f,0f, gui? 0.1f : -0.1f);
		}
		drawString(font, s, drawX/scaler, drawY/scaler, color, matrixStack, light);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		matrixStack.popPose();

	}

	public int drawMaterialComponentTab(MatrixStack matrixStack, int x, int y, float zLevel, boolean gui, int light){
		/*if(spell instanceof IHasMaterialComponents && !((IHasMaterialComponents) spell).getMaterialComponents().isEmpty()) {
			matrixStack.pushPose();
			Minecraft mc = Minecraft.getInstance();
			int j = (mc.player.tickCount/60) % ((IHasMaterialComponents) spell).getMaterialComponents().size();
			ItemStack stack = ((IHasMaterialComponents) spell).getMaterialComponents().get(j);
			mc.getTextureManager().bind(SPELL_ICONS);
			this.drawTexturedModalRect(matrixStack, x, y, 0, 165, 73, 18, zLevel, light);
			this.drawItemStack(matrixStack, stack, x+1, y+1, gui);
			drawStandardText(matrixStack, stack.getDisplayName().getContents(), 51, 8, x+45.5f, y+10f, 0, true, false, gui, light);
			matrixStack.popPose();
			return y + 18;
		}*/
		return y;
	}

	public int drawSchoolComponent(MatrixStack matrixStack, int x, int y, float zLevel, boolean gui, int light){
		if(!spell.getSchools().isEmpty()) {
			matrixStack.pushPose();
			Minecraft mc = Minecraft.getInstance();
			int j = (mc.player.tickCount/60) % spell.getSchools().size();
			MagicSchool school = spell.getSchools().get(j);
			mc.getTextureManager().bind(SPELL_ICONS);
			this.drawTexturedModalRect(matrixStack, x, y, 0, 120, 73, 18, zLevel, light);

			matrixStack.pushPose();
			matrixStack.translate(x+2f,y+2f,zLevel+(gui?1f:-1f));
			matrixStack.scale(14f/30f,14f/30f,1f);
			RenderSystem.color4f(1f,1f,1f,1f);
			this.drawTexturedModalRect(matrixStack,0, 0, school.getId()*30, 60, 30, 30, zLevel, light);
			matrixStack.popPose();

			/*boolean flag = spell.getMinimumSchoolLevels()[school.getId()]>0;
			if(flag) {
				drawStandardText(matrixStack, I18n.get("school." + school.getName() + ".name"), 53, 6, x + 18, y + 2, 0, false, false, gui, light);
				drawStandardText(matrixStack, I18n.get("page.level.element") + ": "+ (spell.getMinimumSchoolLevels()[school.getId()]+1), 53, 6, x + 18, y + 9, 0, false, false, gui, light);
			}
			else*/
				drawStandardText(matrixStack, I18n.get("school."+school.getName()+".name"), 53,8, x+44, y+10, 0, true, false, gui, light);

			matrixStack.popPose();
			return y + 18;
		}
		return y;
	}

	public int drawElementComponent(MatrixStack matrixStack, int x, int y, float zLevel, boolean gui, int light){
		if(!spell.getElements().isEmpty()) {
			matrixStack.pushPose();
			Minecraft mc = Minecraft.getInstance();
			int j = (mc.player.tickCount/60) % spell.getElements().size();
			MagicElement element = spell.getElements().get(j);
			mc.getTextureManager().bind(SPELL_ICONS);
			this.drawTexturedModalRect(matrixStack, x, y, 0, 120, 73, 18, zLevel, light);

			matrixStack.pushPose();
			matrixStack.translate(x+2f,y+2f,zLevel+(gui?1f:-1f));
			matrixStack.scale(14f/30f,14f/30f,1f);
			RenderSystem.color4f(1f,1f,1f,1f);
			this.drawTexturedModalRect(matrixStack, 0, 0, (element.getId()%8)*30, element.getId()/8*30, 30, 30, zLevel, light);
			matrixStack.popPose();

			/*boolean flag = spell.getMinimumElementLevels()[element.getId()]>0;
			if(flag) {
				drawStandardText(matrixStack, I18n.get("element." + element.getName() + ".name"), 53, 6, x + 18, y + 2, 0, false, false, gui, light);
				drawStandardText(matrixStack, I18n.get("page.level.element") + ": "+ (spell.getMinimumElementLevels()[element.getId()]+1), 53, 6, x + 18, y + 9, 0, false, false, gui, light);
			}
			else*/
				drawStandardText(matrixStack, I18n.get("element."+element.getName()+".name"), 53,8, x+44, y+10, 0, true, false, gui, light);

			matrixStack.popPose();
			return y + 18;
		}
		return y;
	}

	public int drawModifierComponent(MatrixStack matrixStack, int x, int y, float zLevel, boolean gui, int offset){
		/*if(!this.getModifierMap().isEmpty()) {
			GlStateManager.pushMatrix();
			int j = (int)((Minecraft.getSystemTime()/3000) + offset) % this.getModifierMap().entrySet().size();
			Map.Entry<Spell.EnumSpellModifier, Object> entry = (Map.Entry<Spell.EnumSpellModifier, Object>) this.getModifierMap().entrySet().toArray()[j];
			Minecraft.getMinecraft().getTextureManager().bindTexture(SPELL_ICONS);
			this.drawTexturedModalRect(matrixStack,x, y, 0, 120, 73, 18, zLevel);

			GlStateManager.pushMatrix();
			GlStateManager.translate(x+2f,y+2f,zLevel+(gui?1f:-1f));
			GlStateManager.scale(14f/30f,14f/30f,1f);
			RenderSystem.color4f(1f,1f,1f,1f);
			this.drawTexturedModalRect(matrixStack,0, 0, 122+(entry.getKey().id%4)*30, 90+entry.getKey().id/4*30, 30, 30, zLevel);
			GlStateManager.popMatrix();

			/*boolean flag = !spell.getMinimumElementLevels().isEmpty() && spell.getMinimumElementLevels().keySet().contains(element) && spell.getMinimumElementLevels().get(element)>0;
			if(flag) {
				drawStandardText(I18n.format("element." + element.getName() + ".name"), 53, 6, x + 18, y + 2, Color.WHITE.getRGB(), false, true, gui);
				drawStandardText(I18n.format("page.level.element") + ": "+ (spell.getMinimumElementLevels().get(element)+1), 53, 6, x + 18, y + 9, Color.WHITE.getRGB(), false, true, gui);
			}
			else*/
				/*drawStandardText(matrixStack,I18n.get("modifier."+entry.getKey().getName().toLowerCase()+".name"), 53,8, x+44, y+10, Color.WHITE.getRGB(), true, true, gui);

			GlStateManager.popMatrix();
			return y + 18;
		}*/
		return y;
	}

	/*public Map<Spell.EnumSpellModifier, Object> getModifierMap(){
		if(map.isEmpty() && !spell.getModifiers().isEmpty()) {
			for (Spell.EnumSpellModifier mod : spell.getModifiers().keySet()) {
				boolean flag = false;
				for (Spell.EnumSpellModifier mod1 : spell.getModifiers().keySet()) {
					if (mod.id == mod1.id && mod1.level > mod.level) {
						flag = true;
						break;
					}
				}
				if (!flag)
					map.put(mod, spell.getModifiers().get(mod));
			}
		}
		return map;
	}*/

}
