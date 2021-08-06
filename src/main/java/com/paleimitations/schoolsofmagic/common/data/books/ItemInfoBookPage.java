package com.paleimitations.schoolsofmagic.common.data.books;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;

public class ItemInfoBookPage extends BookPage {
    public final ItemStack stack;

    public ItemInfoBookPage(String name, ItemStack stack) {
        super(name, Lists.newArrayList(
        ));
        this.stack = stack;
    }
}
