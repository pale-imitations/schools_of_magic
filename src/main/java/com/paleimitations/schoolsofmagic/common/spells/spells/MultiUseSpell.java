package com.paleimitations.schoolsofmagic.common.spells.spells;

import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.IMagicData;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasMultiUses;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public abstract class MultiUseSpell extends Spell implements IHasMultiUses {
    public int remainingUses;
    public int maxUses;

    public MultiUseSpell() {
        super();
        this.remainingUses = 0;
        this.maxUses = this.getUsesPerCharge(this.getMinimumSpellChargeLevel());
    }

    @Override
    public int getUsesPerCharge(int chargeLevel) {
        return 0;
    }

    @Override
    public int getMaxUses(int chargeLevel) {
        return maxUses;
    }

    @Override
    public int getUses() {
        return this.remainingUses;
    }

    @Override
    public void castMultiUseSpell(PlayerEntity player, IMagicData data) {
        if(this.remainingUses==0) {
            if(!player.isCreative() || data.hasChargeLevel(this.currentSpellChargeLevel))
                data.useCharge(this.currentSpellChargeLevel, this.getElements(), this.getSchools(), IMagicData.EnumMagicTool.SPELL);
            this.remainingUses = this.getUsesPerCharge(this.currentSpellChargeLevel);
            this.maxUses = remainingUses;
            this.lastSpellChargeLevel = this.currentSpellChargeLevel;
        }
        else
            this.remainingUses--;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = super.serializeNBT();
        nbt.putInt("remainingUses", this.remainingUses);
        nbt.putInt("maxUses", this.maxUses);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt);
        this.remainingUses = nbt.getInt("remainingUses");
        this.maxUses = nbt.getInt("maxUses");
    }
}
