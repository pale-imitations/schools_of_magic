package com.paleimitations.schoolsofmagic.common.blocks;

import com.paleimitations.schoolsofmagic.common.registries.BlockRegistry;
import net.minecraft.item.ItemStack;

public class HerbalTwineEntry {
    public final HerbalTwineBlock block;
    public final ItemStack undried;
    public final ItemStack dried;

    public HerbalTwineEntry(HerbalTwineBlock block, ItemStack undried, ItemStack dried) {
        this.block = block;
        this.undried = undried;
        this.dried = dried;
    }

    public static HerbalTwineEntry getEntryFromBlock(HerbalTwineBlock blockIn) {
        for(HerbalTwineEntry entry : BlockRegistry.HERBAL_TWINE_ENTRIES) {
            if(entry.block == blockIn) {
                return entry;
            }
        }
        return null;
    }
}
