package com.paleimitations.schoolsofmagic.common.spells.modifiers;

import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.IMagicData;
import net.minecraft.entity.player.PlayerEntity;

public interface IHasMultiUses {
    int getUsesPerCharge(int chargeLevel);
    int getMaxUses(int chargeLevel);
    int getUses();
    void castMultiUseSpell(PlayerEntity player, IMagicData data);
}
