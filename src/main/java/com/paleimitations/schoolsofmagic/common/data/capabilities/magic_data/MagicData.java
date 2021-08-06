package com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.common.config.Config;
import com.paleimitations.schoolsofmagic.common.MagicElement;
import com.paleimitations.schoolsofmagic.common.MagicSchool;
import com.paleimitations.schoolsofmagic.common.data.books.BookPageSpell;
import com.paleimitations.schoolsofmagic.common.network.PacketHandler;
import com.paleimitations.schoolsofmagic.common.network.UpdateMagicDataPacket;
import com.paleimitations.schoolsofmagic.common.registries.MagicElementRegistry;
import com.paleimitations.schoolsofmagic.common.registries.MagicSchoolRegistry;
import com.paleimitations.schoolsofmagic.common.registries.SpellRegistry;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.List;

public class MagicData implements IMagicData, INBTSerializable<CompoundNBT> {

    public static final int MAX_CHARGE_LEVEL = 9;
    public static final int MAX_MAGICIAN_LEVEL = Config.Common.MAX_LEVEL.get(); //Default 36, scale accepts up to 50
    public static final int MAX_SPELLS = Config.Common.MAX_SPELL_STORAGE.get();
    public static final int[] MAX_COUNTDOWNS = new int[] {
            Config.Common.SPELL_CHARGE_1_RELOAD.get(), Config.Common.SPELL_CHARGE_2_RELOAD.get(), Config.Common.SPELL_CHARGE_3_RELOAD.get(),
            Config.Common.SPELL_CHARGE_4_RELOAD.get(), Config.Common.SPELL_CHARGE_5_RELOAD.get(), Config.Common.SPELL_CHARGE_6_RELOAD.get(),
            Config.Common.SPELL_CHARGE_7_RELOAD.get(), Config.Common.SPELL_CHARGE_8_RELOAD.get(), Config.Common.SPELL_CHARGE_9_RELOAD.get()
    };

    private Spell[] spells = new Spell[MAX_SPELLS];
    private int spellSlot;
    private int[] charges;
    private float magicianXP;
    private float[] elementXP;
    private float[] schoolXP;
    private float spellXP;
    private float potionXP;
    private float ritualXP;
    private int[] countdowns;
    private boolean dirty = false;

    public MagicData() {
        this.charges = new int[MAX_CHARGE_LEVEL];
        this.countdowns = new int[MAX_CHARGE_LEVEL];
        this.magicianXP=0f;
        this.elementXP=new float[MagicElementRegistry.ELEMENTS.size()];
        this.schoolXP=new float[MagicSchoolRegistry.SCHOOLS.size()];
        this.spellXP=0f;
        this.potionXP=0f;
        this.ritualXP=0f;
    }

    @Override
    public void useCharge(int chargeLevel, List<MagicElement> elements, List<MagicSchool> schools, @Nullable EnumMagicTool tool) {
        this.charges[chargeLevel]--;
        if(this.countdowns[chargeLevel]==0) {
            this.resetCountdown(chargeLevel);
        }
        this.addMagicianXP((chargeLevel+1)*10);
        for(MagicElement element : elements)
            this.addElementXP(element, (chargeLevel+1)*10*(4f/3f)*(1f/(float)elements.size()));
        for(MagicSchool school : schools)
            this.addSchoolXP(school, (chargeLevel+1)*10*1.2f*(1f/(float)elements.size()));
        if(tool!=null)
            switch (tool){
                case SPELL: this.addSpellXP((chargeLevel+1)*10*(2f/3f)); break;
                case RITUAL: this.addRitualXP((chargeLevel+1)*10); break;
                case POTION: this.addPotionXP((chargeLevel+1)*10*(10f/3f)); break;
            }
    }

    public int[] getCountdowns() {
        return countdowns;
    }

    private void resetCountdown(int chargeLevel) {
        this.countdowns[chargeLevel] = MAX_COUNTDOWNS[chargeLevel];
    }

    @Override
    public List<Spell> getSpells() {
        return Lists.newArrayList(spells);
    }

    @Override
    public void markDirty() {
        this.dirty = true;
    }

    @Override
    public int getCurrentSpellSlot() {
        return this.spellSlot;
    }

    @Override
    public void setCurrentSpellSlot(int spellSlot) {
        this.spellSlot = spellSlot;
    }

    @Override
    public Spell getCurrentSpell() {
        if(this.spellSlot<0)
            this.spellSlot=0;
        if(this.spellSlot>=this.spells.length)
            this.spellSlot=this.spells.length-1;
        return this.spells[this.spellSlot];
    }

    @Override
    public void setCurrentSpell(Spell spell) {
        this.spells[this.spellSlot] = spell;
    }

    @Override
    public Spell getSpell(int index) {
        return this.spells[index];
    }

    @Override
    public void setSpell(int index, Spell spell) {
        this.spells[index] = spell;
    }

    @Override
    public void update(PlayerEntity player) {
        if (countdowns.length>0) {
            for (int i = 0; i < MAX_CHARGE_LEVEL; ++i) {
                if (countdowns[i] > 0)
                    countdowns[i]--;
                else if (countdowns[i] == 0 && canAddCharge(i)) {
                    charges[i]++;
                    if (canAddCharge(i)) {
                        resetCountdown(i);
                    }
                }
            }
        }
        if(dirty) {
            if(player instanceof ServerPlayerEntity) {
                PacketHandler.sendToTracking(new UpdateMagicDataPacket(player.getId(), this.serializeNBT()), (ServerPlayerEntity) player);
            }
            dirty = false;
        }
    }

    @Override
    public boolean hasChargeLevel(int chargeLevel) {
        return charges[chargeLevel] > 0;
    }

    @Override
    public boolean canAddCharge(int chargeLevel) {
        return charges[chargeLevel] < this.getMaxCharges(chargeLevel, this.getLevel());
    }

    @Override
    public int getMaxCharges(int chargeLevel, int level) {
        switch(chargeLevel){
            case 0: return 2 + Math.min(6, (level+1)/5);
            case 1: return Math.min(6, level/5);
            case 2: return level < 9? 0 : Math.min(5, (level-4)/5);
            case 3: return level < 13? 0 : Math.min(5, (level-8)/5);
            case 4: return level < 16? 0 : Math.min(5, (level-10)/6);
            case 5: return level < 20? 0 : Math.min(4, (level-11)/8);
            case 6: return level < 25? 0 : Math.min(4, (level-11)/9);
            case 7: return level < 30? 0 : Math.min(3, (level-24)/6);
            case 8: return level < 32? 0 : level < 50 ? 1 : 2;
        }
        return 1;
    }

    @Override
    public int[] getCharges() {
        return charges;
    }

    @Override
    public int getLargestChargeLevel() {
        int level = this.getLevel();
        for(int i = 0; i < 9; ++ i) {
            if(this.getMaxCharges(i, level) == 0)
                return i-1;
        }
        return 8;
    }

    @Override
    public int getSpellSlots(ItemStack wand) {
        CompoundNBT nbt = wand.getOrCreateTag();
        if(nbt.contains("slotLimit"))
            return nbt.getInt("slotLimit");
        else {
            int level = this.getLevel();
            int slots = 3;
            if (level >= 3) slots++;
            if (level >= 7) slots++;
            if (level >= 12) slots++;
            if (level >= 17) slots++;
            if (level >= 21) slots++;
            if (level >= 26) slots++;
            if (level >= 31) slots+= (level-29/2);
            if (level >= 50) slots++;
            return slots;
        }
    }

    @Override
    public float getMagicianXP() {
        return this.magicianXP;
    }

    @Override
    public void setMagicianXP(float magicianXP) {
        this.magicianXP = magicianXP;
    }

    @Override
    public void addMagicianXP (float magicianXP) {
        if(this.getLevel() < MAX_MAGICIAN_LEVEL)
            this.magicianXP += magicianXP;
    }

    @Override
    public void removeMagicianXP (float magicianXP) {
        this.magicianXP -= magicianXP;
        if(this.magicianXP < 0)
            this.magicianXP = 0;
    }

    @Override
    public Tuple<Float, Float> getMagicianXPToNextLevel() {
        int level = 0;
        float magicianXPTemp = magicianXP;
        int nextLevel = 50;
        while(magicianXPTemp>0f){
            nextLevel = 50+(level*10);
            if(magicianXPTemp>nextLevel){
                magicianXPTemp-=nextLevel;
                ++level;
            }
            else
                break;
        }
        return new Tuple<>(magicianXPTemp,(float)nextLevel);
    }

    @Override
    public int getLevel() {
        int level = 0;
        float magicianXPTemp = magicianXP;
        while(magicianXPTemp>0f){
            int nextLevel = 50+(level*10);
            if(magicianXPTemp>nextLevel){
                magicianXPTemp-=nextLevel;
                ++level;
            }
            else
                break;
        }
        return Math.min(level + 1, MAX_MAGICIAN_LEVEL);
    }

    @Override
    public float[] getElementXPs() {
        return this.elementXP;
    }

    @Override
    public void setElementXPs(float[] xps) {
        this.elementXP = xps;
    }

    @Override
    public float getElementXP(MagicElement element) {
        return this.elementXP[element.getId()];
    }

    @Override
    public void setElementXP(MagicElement element, float elementXP) {
        this.elementXP[element.getId()] = elementXP;
    }

    @Override
    public void addElementXP(MagicElement element, float elementXP) {
        this.elementXP[element.getId()] = Math.min(this.elementXP[element.getId()]+elementXP, this.magicianXP);
    }

    @Override
    public void removeElementXP(MagicElement element, float elementXP) {
        this.elementXP[element.getId()] = Math.max(this.elementXP[element.getId()]-elementXP,0f);
    }

    @Override
    public int getElementLevel(MagicElement element) {
        int level = 0;
        float elementXP = this.getElementXP(element);
        while(elementXP>0f){
            int nextLevel = 50+(level*10);
            if(elementXP>nextLevel){
                elementXP-=nextLevel;
                ++level;
            }
            else
                break;
        }
        return level + 1;
    }

    @Override
    public Tuple<Float, Float> getElementXPToNextLevel(MagicElement element) {
        int level = 0;
        float elementXP = this.getElementXP(element);
        int nextLevel = 50;
        while(elementXP>0f){
            nextLevel = 50+(level*10);
            if(elementXP>nextLevel){
                elementXP-=nextLevel;
                ++level;
            }
            else
                break;
        }
        return new Tuple<>(elementXP,(float)nextLevel);
    }

    @Override
    public float[] getSchoolXPs() {
        return this.schoolXP;
    }

    @Override
    public void setSchoolXPs(float[] xps) {
        this.schoolXP = xps;
    }

    @Override
    public float getSchoolXP(MagicSchool school) {
        return this.schoolXP[school.getId()];
    }

    @Override
    public void setSchoolXP(MagicSchool school, float schoolXP) {
        this.schoolXP[school.getId()] = schoolXP;
    }

    @Override
    public void addSchoolXP(MagicSchool school, float schoolXP) {
        this.schoolXP[school.getId()] = Math.min(this.schoolXP[school.getId()]+schoolXP, this.magicianXP);
    }

    @Override
    public void removeSchoolXP(MagicSchool school, float schoolXP) {
        this.schoolXP[school.getId()] = Math.max(this.schoolXP[school.getId()]-schoolXP,0f);
    }

    @Override
    public int getSchoolLevel(MagicSchool school) {
        int level = 0;
        float schoolXP = this.getSchoolXP(school);
        while(schoolXP>0f){
            int nextLevel = 50+(level*10);
            if(schoolXP>nextLevel){
                schoolXP-=nextLevel;
                ++level;
            }
            else
                break;
        }
        return level + 1;
    }

    @Override
    public Tuple<Float, Float> getSchoolXPToNextLevel(MagicSchool school) {
        int level = 0;
        float schoolXP = this.getSchoolXP(school);
        int nextLevel = 50;
        while(schoolXP>0f){
            nextLevel = 50+(level*10);
            if(schoolXP>nextLevel){
                schoolXP-=nextLevel;
                ++level;
            }
            else
                break;
        }
        return new Tuple<>(schoolXP,(float)nextLevel);
    }

    @Override
    public float getSpellXP() {
        return this.spellXP;
    }

    @Override
    public void setSpellXP(float spellXP) {
        this.spellXP = spellXP;
    }

    @Override
    public void addSpellXP (float spellXP) {
        this.spellXP += spellXP;
        if(this.spellXP>this.magicianXP)
            this.spellXP = this.magicianXP;
    }

    @Override
    public void removeSpellXP (float spellXP) {
        this.spellXP -= spellXP;
        if(this.spellXP < 0)
            this.spellXP = 0;
    }

    @Override
    public int getSpellLevel() {
        int level = 0;
        float spellXPTemp = spellXP;
        while(spellXPTemp>0f){
            int nextLevel = 50+(level*10);
            if(spellXPTemp>nextLevel){
                spellXPTemp-=nextLevel;
                ++level;
            }
            else
                break;
        }
        return level + 1;
    }

    @Override
    public Tuple<Float, Float> getSpellXPToNextLevel() {
        int level = 0;
        float spellXPTemp = spellXP;
        int nextLevel = 50;
        while(spellXPTemp>0f){
            nextLevel = 50+(level*10);
            if(spellXPTemp>nextLevel){
                spellXPTemp-=nextLevel;
                ++level;
            }
            else
                break;
        }
        return new Tuple<>(spellXPTemp,(float)nextLevel);
    }

    @Override
    public float getPotionXP() {
        return this.potionXP;
    }

    @Override
    public void setPotionXP(float potionXP) {
        this.potionXP = potionXP;
    }

    @Override
    public void addPotionXP (float potionXP) {
        this.potionXP += potionXP;
        if(this.potionXP>this.magicianXP)
            this.potionXP = this.magicianXP;
    }

    @Override
    public void removePotionXP (float potionXP) {
        this.potionXP -= potionXP;
        if(this.potionXP < 0)
            this.potionXP = 0;
    }

    @Override
    public int getPotionLevel() {
        int level = 0;
        float potionXPTemp = potionXP;
        while(potionXPTemp>0f){
            int nextLevel = 50+(level*10);
            if(potionXPTemp>nextLevel){
                potionXPTemp-=nextLevel;
                ++level;
            }
            else
                break;
        }
        return level + 1;
    }

    @Override
    public Tuple<Float, Float> getPotionXPToNextLevel() {
        int level = 0;
        float potionXPTemp = potionXP;
        int nextLevel = 50;
        while(potionXPTemp>0f){
            nextLevel = 50+(level*10);
            if(potionXPTemp>nextLevel){
                potionXPTemp-=nextLevel;
                ++level;
            }
            else
                break;
        }
        return new Tuple<>(potionXPTemp,(float)nextLevel);
    }

    @Override
    public float getRitualXP() {
        return this.ritualXP;
    }

    @Override
    public void setRitualXP(float ritualXP) {
        this.ritualXP = ritualXP;
    }

    @Override
    public void addRitualXP (float ritualXP) {
        this.ritualXP += ritualXP;
        if(this.ritualXP>this.magicianXP)
            this.ritualXP = this.magicianXP;
    }

    @Override
    public void removeRitualXP (float ritualXP) {
        this.ritualXP -= ritualXP;
        if(this.ritualXP < 0)
            this.ritualXP = 0;
    }

    @Override
    public int getRitualLevel() {
        int level = 0;
        float ritualXPTemp = ritualXP;
        while(ritualXPTemp>0f){
            int nextLevel = 50+(level*10);
            if(ritualXPTemp>nextLevel){
                ritualXPTemp-=nextLevel;
                ++level;
            }
            else
                break;
        }
        return level + 1;
    }

    @Override
    public Tuple<Float, Float> getRitualXPToNextLevel() {
        int level = 0;
        float ritualXPTemp = ritualXP;
        int nextLevel = 50;
        while(ritualXPTemp>0f){
            nextLevel = 50+(level*10);
            if(ritualXPTemp>nextLevel){
                ritualXPTemp-=nextLevel;
                ++level;
            }
            else
                break;
        }
        return new Tuple<>(ritualXPTemp,(float)nextLevel);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("spellSlot", this.spellSlot);
        for(int i=0; i<MAX_SPELLS;++i)
            if(this.spells[i]!=null) {
                nbt.putString("spell" + i, this.spells[i].getResourceLocation().toString());
                nbt.put("spellData" + i, this.spells[i].serializeNBT());
            }
        nbt.putIntArray("charges", this.charges);
        nbt.putIntArray("countdowns", this.countdowns);
        nbt.putFloat("magicianXP", this.magicianXP);
        for(MagicElement element : MagicElementRegistry.ELEMENTS)
            nbt.putFloat(element.getName()+"XP", this.elementXP[element.getId()]);
        for(MagicSchool school : MagicSchoolRegistry.SCHOOLS)
            nbt.putFloat(school.getName()+"XP", this.schoolXP[school.getId()]);
        nbt.putFloat("spellXP", this.spellXP);
        nbt.putFloat("ritualXP", this.ritualXP);
        nbt.putFloat("potionXP", this.potionXP);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.spellSlot = nbt.getInt("spellSlot");
        for(int i=0; i<MAX_SPELLS;++i)
            if(nbt.contains("spell"+i) && nbt.contains("spellData"+i)) {
                Spell spell = SpellRegistry.getSpell(nbt.getString("spell"+i));
                if(spell!=null) {
                    spell.deserializeNBT(nbt.getCompound("spellData"+i));
                    this.spells[i] = spell;
                }
            }
        this.charges = nbt.getIntArray("charges");
        this.countdowns = nbt.getIntArray("countdowns");
        this.magicianXP = nbt.getFloat("magicianXP");
        for(MagicElement element : MagicElementRegistry.ELEMENTS)
            this.elementXP[element.getId()] = nbt.getFloat(element.getName()+"XP");
        for(MagicSchool school : MagicSchoolRegistry.SCHOOLS)
            this.schoolXP[school.getId()] = nbt.getFloat(school.getName()+"XP");
        this.spellXP = nbt.getFloat("spellXP");
        this.ritualXP = nbt.getFloat("ritualXP");
        this.potionXP = nbt.getFloat("potionXP");
    }
}
