package com.paleimitations.schoolsofmagic.common.spells.modifiers;

import com.paleimitations.schoolsofmagic.common.registries.SpellRegistry;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import net.minecraft.util.ResourceLocation;

import java.util.function.Predicate;

public class Modifier {

    public final ResourceLocation location;
    public final Predicate<Spell> applicableSpells;
    public final boolean isBeneficial;

    public Modifier(ResourceLocation location, Predicate<Spell> applicableSpells, boolean isBeneficial) {
        this.location = location;
        this.applicableSpells = applicableSpells;
        this.isBeneficial = isBeneficial;
        SpellRegistry.MODIFIERS.add(this);
    }

    public ResourceLocation getLocation() {
        return location;
    }

    public boolean canApply(Spell spell) {
        return applicableSpells.test(spell);
    }
}
