package com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data;

import com.paleimitations.schoolsofmagic.common.quests.Quest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import java.util.List;
import java.util.UUID;

public interface IQuestData {
    List<Quest> getQuests();
    void addQuest(Quest quest);
    void updateQuests(PlayerEntity player);
    CompoundNBT serializeNBT();
    void deserializeNBT(CompoundNBT nbt);
    boolean hasQuest(UUID questGiver);
    Quest getQuestbyQuestGiver(UUID questGiver);
}
