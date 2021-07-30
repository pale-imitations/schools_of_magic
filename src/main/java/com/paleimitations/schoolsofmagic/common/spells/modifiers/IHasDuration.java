package com.paleimitations.schoolsofmagic.common.spells.modifiers;

public interface IHasDuration {

    int getDefaultDuration(int chargeLevel);
    int getDuration();

}
