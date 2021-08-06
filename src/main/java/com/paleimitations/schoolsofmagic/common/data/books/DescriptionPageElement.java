package com.paleimitations.schoolsofmagic.common.data.books;

import net.minecraft.util.text.TextFormatting;

public class DescriptionPageElement extends PageElement {

    public final String description;
    public final TextFormatting formatting;

    public DescriptionPageElement(String description) {
        super(0, 0);
        this.description = description;
        this.formatting = TextFormatting.GRAY;
    }

    public DescriptionPageElement(String description, TextFormatting formatting) {
        super(0, 0);
        this.description = description;
        this.formatting = formatting;
    }
}
