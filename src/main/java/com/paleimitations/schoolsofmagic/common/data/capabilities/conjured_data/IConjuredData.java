package com.paleimitations.schoolsofmagic.common.data.capabilities.conjured_data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

public interface IConjuredData {
    boolean isConjured();
    void setConjured(boolean isConjured);
    int getCountdown();
    void setCountdown(int countdown);
    boolean useCountdown();
    void setUseCountdown(boolean useCountdown);

    void setConjured(boolean isConjured, int countdown, boolean useCountdown);

    CompoundNBT serializeNBT();
    void deserializeNBT(CompoundNBT nbt);
}
