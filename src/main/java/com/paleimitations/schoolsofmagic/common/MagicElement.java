package com.paleimitations.schoolsofmagic.common;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.registries.MagicElementRegistry;
import net.minecraft.util.ResourceLocation;

public class MagicElement implements IMagicType {

	private final String name;
	private final int categoryId;
	private final int color;
	
	public MagicElement(String name, int categoryId, int color) {
		this.name = name;
		this.categoryId = categoryId;
		this.color = color;

		MagicElementRegistry.ELEMENTS.add(this);
	}
	
	public int getId() {
		return categoryId;
	}
	
	public int getColor() {
		return color;
	}
	
	public String getName() {
		return name;
	}

	public String getFormattedName() {
		return "element." + name + ".name";
	}
	
	public ResourceLocation getIcon() {
		return new ResourceLocation(References.MODID, "textures/gui/magic_categories/"+name+".png");
	}
}
