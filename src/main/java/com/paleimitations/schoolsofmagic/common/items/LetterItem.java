package com.paleimitations.schoolsofmagic.common.items;

import com.paleimitations.schoolsofmagic.SchoolsOfMagicMod;
import com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data.IQuestData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data.QuestDataProvider;
import com.paleimitations.schoolsofmagic.common.quests.Quest;
import com.paleimitations.schoolsofmagic.common.quests.QuestHelper;
import com.paleimitations.schoolsofmagic.common.registries.QuestRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class LetterItem extends Item {

    public LetterItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        SchoolsOfMagicMod.getProxy().openLetter(player, stack, hand);
        player.playSound(SoundEvents.BOOK_PAGE_TURN, 0.1f, 1f);
        return ActionResult.success(stack);
    }
}
