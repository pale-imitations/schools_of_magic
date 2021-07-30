package com.paleimitations.schoolsofmagic.common.spells.modifiers;

public interface IHasMultiUses {

    int getMaxUses(int chargeLevel);
    int getUses();
}
