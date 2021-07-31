package com.paleimitations.schoolsofmagic.common.spells.modifiers;

public interface IHasDuration {

    int getDurationForCharge(int chargeLevel);
    int getDuration();
    int getMaxDuration();

}
