package com.paleimitations.schoolsofmagic.common.data.books;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.paleimitations.schoolsofmagic.common.registries.BookPageRegistry;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class BookPage {

	public final String name;
	public final List<PageElement> elements;
	
	public BookPage(String name, List<PageElement> elements) {
		this.name = name;
		this.elements = elements;
		this.addPageToRegistry();
	}

	public void addPageToRegistry(){
		BookPageRegistry.PAGES.add(this);
	}

	@OnlyIn(Dist.CLIENT)
	public void drawPage(MatrixStack matrix, float mouseX, float mouseY, int x, int y, float zLevel, boolean isGUI, int subpage, int light, IRenderTypeBuffer buffer) {
		for(PageElement element : elements) {
			if(element.isTarget(subpage)) {
				RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
				element.drawElement(matrix, mouseX, mouseY, x, y, zLevel, isGUI, subpage, light, buffer);
				RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isSubPageBlank(int subpage) {
		for(PageElement element : elements)
			if(element.hasSubpage(subpage))
				return false;
		return true;
	}

	public int getSubPages() {
		int i = 0;
		for(PageElement element : elements)
			if(element.subpage>i)
				i = element.subpage;
		return i + 1;
	}
	
	public String getName() {
		return name;
	}
	
	public BookPage addToList(List<BookPage> pages) {
		pages.add(this);
		return this;
	}
	
	public BookPage addElement(PageElement element) {
		elements.add(element);
		return this;
	}

	public BookPage addElements(List<PageElement> element) {
		elements.addAll(element);
		return this;
	}
	
}
