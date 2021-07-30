package com.paleimitations.schoolsofmagic.common.quests;

import com.google.common.collect.Maps;
import com.paleimitations.schoolsofmagic.common.registries.QuestRegistry;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.function.Function;

public class QuestHelper {
    private static final Map<ResourceLocation, Function<CompoundNBT, ? extends Quest>> QUEST_HELPERS = Maps.newHashMap();
    private static final Map<ResourceLocation, Function<ResourceLocation, ? extends Quest>> QUEST_HELPERS2 = Maps.newHashMap();

    public static void registerQuestHelpers(Quest quest){
        ResourceLocation location = quest.getResourceLocation();
        Class questClass = quest.getClass();
        QUEST_HELPERS.put(location, new NBTConstructorFactory(questClass));
        QUEST_HELPERS2.put(location, new ConstructorFactory(questClass));
        QuestRegistry.QUESTS.add(quest);
    }

    static class NBTConstructorFactory<R extends Quest> implements Function<CompoundNBT, R>
    {
        private final Constructor<? extends R> constructor;

        NBTConstructorFactory(final Class<? extends R> quest)
        {
            this.constructor = ObfuscationReflectionHelper.findConstructor(quest, CompoundNBT.class);
        }

        @Override
        public R apply(CompoundNBT nbt)
        {
            try
            {
                return this.constructor.newInstance(nbt);
            }
            catch (final IllegalAccessException | InstantiationException | InvocationTargetException e)
            {
                return null;
            }
        }
    }

    static class ConstructorFactory<R extends Quest> implements Function<ResourceLocation, R>
    {
        private final Constructor<? extends R> constructor;

        ConstructorFactory(final Class<? extends R> spell)
        {
            this.constructor = ObfuscationReflectionHelper.findConstructor(spell);
        }

        @Override
        public R apply(ResourceLocation resourceLocation)
        {
            try
            {
                return this.constructor.newInstance();
            }
            catch (final IllegalAccessException | InstantiationException | InvocationTargetException e)
            {
                return null;
            }
        }
    }

    public static Quest getQuestInstance(ResourceLocation location, CompoundNBT nbt) {
        return QUEST_HELPERS.get(location).apply(nbt);
    }

    public static Quest getNewInstance(ResourceLocation location) {
        return QUEST_HELPERS2.get(location).apply(location);
    }
}
