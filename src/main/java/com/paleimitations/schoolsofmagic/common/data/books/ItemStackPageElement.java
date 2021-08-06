package com.paleimitations.schoolsofmagic.common.data.books;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemStackPageElement extends PageElement {

	public final ItemStack stack;
	
	public ItemStackPageElement(ItemStack stack, int x, int y) {
		super(x, y);
		this.stack = stack;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void drawElement(MatrixStack matrixStack, float mouseX, float mouseY, int xIn, int yIn, float zLevel, boolean isGUI, int target, int light) {
        this.drawItemStack(matrixStack, stack, x+xIn, y+yIn, isGUI);
	}

}
