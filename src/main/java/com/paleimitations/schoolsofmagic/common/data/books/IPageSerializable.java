package com.paleimitations.schoolsofmagic.common.data.books;

import net.minecraft.nbt.CompoundNBT;

public interface IPageSerializable {

    CompoundNBT serializeNBT();
    void deserializeNBT(CompoundNBT nbt);
}
