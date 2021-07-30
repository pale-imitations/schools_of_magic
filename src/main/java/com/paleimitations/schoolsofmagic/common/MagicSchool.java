package com.paleimitations.schoolsofmagic.common;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.registries.MagicSchoolRegistry;
import net.minecraft.util.ResourceLocation;

public class MagicSchool implements IMagicType {

	private final String name;
	private final int categoryId;
	
	public MagicSchool(String name, int categoryId) {
		this.name = name;
		this.categoryId = categoryId;

		MagicSchoolRegistry.SCHOOLS.add(this);
	}
	
	public int getId() {
		return categoryId;
	}
	
	public String getName() {
		return name;
	}

	public String getFormattedName() {
		return "school." + name + ".name";
	}
	
	public ResourceLocation getIcon() {
		return new ResourceLocation(References.MODID, "textures/gui/spells/school_"+name+".png");
	}
}
