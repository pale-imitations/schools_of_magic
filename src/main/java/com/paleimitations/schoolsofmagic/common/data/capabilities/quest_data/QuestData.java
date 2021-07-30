package com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.common.quests.Quest;
import com.paleimitations.schoolsofmagic.common.quests.QuestHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;
import java.util.UUID;

public class QuestData implements IQuestData, INBTSerializable<CompoundNBT> {

    public List<Quest> quests = Lists.newArrayList();

    @Override
    public List<Quest> getQuests() {
        return quests;
    }

    @Override
    public void addQuest(Quest quest) {
        this.quests.add(quest);
    }

    @Override
    public void updateQuests(PlayerEntity player) {
        List<Quest> reset = Lists.newArrayList();
        for(Quest quest : quests) {
            quest.update(player);
            if (!quest.dead)
                reset.add(quest);
        }
        this.quests = reset;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("NumberOfQuests", quests.size());
        for(int i = 0; i < quests.size(); ++i) {
            nbt.putString("QuestLocation"+i, quests.get(i).getResourceLocation().toString());
            nbt.put("QuestData"+i, quests.get(i).serializeNBT());
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        quests.clear();
        for(int i = 0; i < nbt.getInt("NumberOfQuests"); ++i) {
            if(nbt.contains("QuestLocation"+i) && nbt.contains("QuestData"+i)) {
                Quest q = QuestHelper.getQuestInstance(new ResourceLocation(nbt.getString("QuestLocation" + i)), nbt.getCompound("QuestData" + i));
                if(q!=null) {
                    quests.add(q);
                }
            }
        }
    }

    @Override
    public boolean hasQuest(UUID questGiver) {
        for(Quest quest : quests)
            if(quest.getQuestGiver()!=null && quest.getQuestGiver().equals(questGiver))
                return true;
        return false;
    }

    @Override
    public Quest getQuestbyQuestGiver(UUID questGiver) {
        for(Quest quest : quests)
            if(quest.getQuestGiver()!=null && quest.getQuestGiver().equals(questGiver))
                return quest;
        return null;
    }
}
