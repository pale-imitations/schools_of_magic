package com.paleimitations.schoolsofmagic.common.registries;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.common.quests.Quest;
import com.paleimitations.schoolsofmagic.common.quests.QuestHelper;
import com.paleimitations.schoolsofmagic.common.quests.quests.*;

import java.util.List;

public class QuestRegistry {

    public static final List<Quest> QUESTS = Lists.newArrayList();

    public static void init() {
        QuestHelper.registerQuestHelpers(new QuestBrewPotion());
        QuestHelper.registerQuestHelpers(new QuestBuildGolem());
        QuestHelper.registerQuestHelpers(new QuestEnchantItem());
        //QuestHelper.registerQuestHelpers(new QuestIntermediateArcana());
        //QuestHelper.registerQuestHelpers(new QuestAdvancedArcana());
    }

    public static Quest getQuest(String name) {
        for(Quest quest : QUESTS) {
            if(quest.getResourceLocation().toString().equalsIgnoreCase(name)){
                return quest;
            }
        }
        return null;
    }

}
