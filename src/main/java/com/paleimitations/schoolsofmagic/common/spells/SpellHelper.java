package com.paleimitations.schoolsofmagic.common.spells;

import com.google.common.collect.Maps;
import com.paleimitations.schoolsofmagic.SchoolsOfMagicMod;
import com.paleimitations.schoolsofmagic.common.data.books.BookPageSpell;
import com.paleimitations.schoolsofmagic.common.registries.BookPageRegistry;
import com.paleimitations.schoolsofmagic.common.registries.SpellRegistry;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.function.Function;

public class SpellHelper {
    private static final Map<ResourceLocation, Function<CompoundNBT, ? extends Spell>> SPELL_HELPERS = Maps.newHashMap();
    private static final Map<ResourceLocation, Function<ResourceLocation, ? extends Spell>> SPELL_HELPERS_2 = Maps.newHashMap();

    public static void registerSpellHelpers(Spell spell){
        ResourceLocation location = spell.getResourceLocation();
        Class spellClass = spell.getClass();
        SPELL_HELPERS.put(location, new NBTConstructorFactory(spellClass));
        SPELL_HELPERS_2.put(location, new ConstructorFactory(spellClass));
        SpellRegistry.SPELLS.add(spell);
        BookPageRegistry.PAGES.add(new BookPageSpell(spell));
        //spell.generateBuyable();
    }

    static class NBTConstructorFactory<R extends Spell> implements Function<CompoundNBT, R>
    {
        private final Constructor<? extends R> constructor;

        NBTConstructorFactory(final Class<? extends R> spell)
        {
            this.constructor = ObfuscationReflectionHelper.findConstructor(spell, CompoundNBT.class);
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
                SchoolsOfMagicMod.LOGGER.error("Encountered an exception while constructing spell '{}'", e);
                return null;
            }
        }
    }

    static class ConstructorFactory<R extends Spell> implements Function<ResourceLocation, R>
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
                return this.constructor.newInstance(resourceLocation);
            }
            catch (final IllegalAccessException | InstantiationException | InvocationTargetException e) {
                SchoolsOfMagicMod.LOGGER.error("Encountered an exception while constructing ritual '{}'", e);
                return null;
            }
        }
    }

    public static Spell getSpellInstance(ResourceLocation location, CompoundNBT nbt) {
        return SPELL_HELPERS.get(location).apply(nbt);
    }

    public static Spell getBaseSpellInstance(ResourceLocation location) {
        return SPELL_HELPERS_2.get(location).apply(location);
    }
}
