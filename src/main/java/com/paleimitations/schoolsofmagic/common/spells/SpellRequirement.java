package com.paleimitations.schoolsofmagic.common.spells;

import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.IMagicData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;

import java.util.function.Predicate;

public class SpellRequirement {
    public final Predicate<IMagicData> requirement;
    public final StringTextComponent failMessage;

    public SpellRequirement(Predicate<IMagicData> requirement, StringTextComponent failMessage) {
        this.requirement = requirement;
        this.failMessage = failMessage;
    }

    public boolean test(PlayerEntity player, IMagicData data) {
        if(requirement.test(data))
            return true;
        else {
            player.sendMessage(failMessage, Util.NIL_UUID);
            return false;
        }
    }
}
