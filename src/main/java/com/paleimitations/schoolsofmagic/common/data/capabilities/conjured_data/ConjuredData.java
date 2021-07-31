package com.paleimitations.schoolsofmagic.common.data.capabilities.conjured_data;

import com.paleimitations.schoolsofmagic.common.quests.Quest;
import com.paleimitations.schoolsofmagic.common.quests.QuestHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public class ConjuredData implements IConjuredData, INBTSerializable<CompoundNBT> {
    public boolean isConjured;
    public boolean useCountdown;
    public int countdown;

    public ConjuredData() {
    }

    @Override
    public boolean isConjured() {
        return this.isConjured;
    }

    @Override
    public void setConjured(boolean isConjured) {
        this.isConjured = isConjured;
    }

    @Override
    public int getCountdown() {
        return countdown;
    }

    @Override
    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    @Override
    public boolean useCountdown() {
        return this.useCountdown;
    }

    @Override
    public void setUseCountdown(boolean useCountdown) {
        this.useCountdown = useCountdown;
    }

    @Override
    public void setConjured(boolean isConjured, int countdown, boolean useCountdown) {
        this.useCountdown = useCountdown;
        this.countdown = countdown;
        this.isConjured = isConjured;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("countdown", countdown);
        nbt.putBoolean("isConjured", isConjured);
        nbt.putBoolean("useCountdown", useCountdown);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        countdown = nbt.getInt("countdown");
        isConjured = nbt.getBoolean("isConjured");
        useCountdown = nbt.getBoolean("useCountdown");
    }
}
