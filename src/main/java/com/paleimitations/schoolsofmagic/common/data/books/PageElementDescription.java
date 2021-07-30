package com.paleimitations.schoolsofmagic.common.data.books;

import net.minecraft.util.text.TextFormatting;

public class PageElementDescription extends PageElement {

    public final String description;
    public final TextFormatting formatting;

    public PageElementDescription(String description) {
        super(0, 0);
        this.description = description;
        this.formatting = TextFormatting.GRAY;
    }

    public PageElementDescription(String description, TextFormatting formatting) {
        super(0, 0);
        this.description = description;
        this.formatting = formatting;
    }
}
