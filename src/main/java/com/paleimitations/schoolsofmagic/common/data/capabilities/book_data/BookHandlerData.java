package com.paleimitations.schoolsofmagic.common.data.capabilities.book_data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.storage.WorldSavedData;

public class BookHandlerData extends WorldSavedData {

    public int bookIds;

    public BookHandlerData(String s) {
        super(s);
    }

    public int getNewBookId() {
        this.bookIds++;
        return this.bookIds;
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.putInt("bookIds", this.bookIds);
        return nbt;
    }

    @Override
    public void load(CompoundNBT nbt) {
        this.bookIds = nbt.getInt("bookIds");
    }
}
