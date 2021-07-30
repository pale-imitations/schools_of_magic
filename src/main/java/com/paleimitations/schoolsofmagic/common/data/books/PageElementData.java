package com.paleimitations.schoolsofmagic.common.data.books;

import net.minecraft.item.ItemStack;

import java.util.List;

public class PageElementData extends PageElement {

    public final float podiumGameScore;
    public final List<ItemStack> craftingInputs;

    public PageElementData(float podiumGameScore, List<ItemStack> craftingInputs) {
        super(0, 0);
        this.podiumGameScore = podiumGameScore;
        this.craftingInputs = craftingInputs;
    }
}
