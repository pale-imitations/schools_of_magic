package com.paleimitations.schoolsofmagic;

import com.paleimitations.schoolsofmagic.common.data.books.BookPage;
import com.paleimitations.schoolsofmagic.common.quests.Quest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class CommonProxy {

    public void preInit() {
    }

    public void postInit() {
    }

    public void loadBookPageText(BookPage page) {
    }

    public void openBook(PlayerEntity player, ItemStack stack, Hand hand) {
    }

    public void openQuest(PlayerEntity player, ItemStack stack, Quest quest, Hand hand) {
    }

    public void openLetter(PlayerEntity player, ItemStack stack, Hand hand) {
    }
}
