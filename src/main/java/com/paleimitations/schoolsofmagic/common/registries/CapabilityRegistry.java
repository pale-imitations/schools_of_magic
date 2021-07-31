package com.paleimitations.schoolsofmagic.common.registries;


import com.paleimitations.schoolsofmagic.common.data.capabilities.conjured_data.ConjuredDataProvider;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.MagicDataProvider;
import com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data.QuestDataProvider;

public class CapabilityRegistry {

    public static void init() {
        MagicDataProvider.register();
        QuestDataProvider.register();
        ConjuredDataProvider.register();
    }
}
