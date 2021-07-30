package com.paleimitations.schoolsofmagic.common.data;

import net.minecraft.util.IStringSerializable;
import net.minecraftforge.common.IExtensibleEnum;

public enum BindingType implements IStringSerializable, IExtensibleEnum {
    COPPER,
    BRONZE,
    BRASS,
    GOLD,
    SILVER,
    IRON,
    ALLORITE,
    NETHERITE,
    TENEBRIUM;

    @Override
    public String getSerializedName() {
        return name().toLowerCase();
    }

    public static BindingType create(String enumName) {
        throw new IllegalStateException("Enum not extended");
    }

    public static BindingType fromName(String name) {
        for(BindingType type : values()) {
            if (type.getSerializedName().equalsIgnoreCase(name))
                return type;
        }
        return COPPER;
    }

    public static BindingType fromIndex(int i) {
        for(BindingType type : values()) {
            if (type.ordinal() == i)
                return type;
        }
        return COPPER;
    }
}
