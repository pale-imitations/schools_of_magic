package com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data;

import com.paleimitations.schoolsofmagic.common.MagicElement;
import com.paleimitations.schoolsofmagic.common.MagicSchool;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Tuple;

import javax.annotation.Nullable;
import java.util.List;

public interface IMagicData {

    void markDirty();
    int getCurrentSpellSlot();
    void setCurrentSpellSlot(int spellSlot);
    Spell getCurrentSpell();
    void setCurrentSpell(Spell spell);
    Spell getSpell(int index);
    void setSpell(int index, Spell spell);
    List<Spell> getSpells();
    int getSpellSlots(ItemStack wand);

    float getMagicianXP();
    void setMagicianXP(float manaXP);
    void addMagicianXP(float manaXP);
    void removeMagicianXP(float manaXP);
    Tuple<Float, Float> getMagicianXPToNextLevel();

    int getLevel();
    void update(PlayerEntity player);
    boolean hasChargeLevel(int chargeLevel);
    int getLargestChargeLevel();
    boolean canAddCharge(int chargeLevel);
    int[] getCharges();
    int getMaxCharges(int chargeLevel, int level);
    int[] getCountdowns();

    float[] getElementXPs();
    void setElementXPs(float[] xps);
    float getElementXP(MagicElement element);
    void setElementXP(MagicElement element, float elementXP);
    void addElementXP(MagicElement element, float elementXP);
    void removeElementXP(MagicElement element, float elementXP);
    int getElementLevel(MagicElement element);
    Tuple<Float, Float> getElementXPToNextLevel(MagicElement element);

    float[] getSchoolXPs();
    void setSchoolXPs(float[] xps);
    float getSchoolXP(MagicSchool school);
    void setSchoolXP(MagicSchool school, float schoolXP);
    void addSchoolXP(MagicSchool school, float schoolXP);
    void removeSchoolXP(MagicSchool school, float schoolXP);
    int getSchoolLevel(MagicSchool school);
    Tuple<Float, Float> getSchoolXPToNextLevel(MagicSchool school);

    float getPotionXP();
    void setPotionXP(float potionXP);
    void addPotionXP(float potionXP);
    void removePotionXP(float potionXP);
    int getPotionLevel();
    Tuple<Float, Float> getPotionXPToNextLevel();

    float getSpellXP();
    void setSpellXP(float spellXP);
    void addSpellXP(float spellXP);
    void removeSpellXP(float spellXP);
    int getSpellLevel();
    Tuple<Float, Float> getSpellXPToNextLevel();

    float getRitualXP();
    void setRitualXP(float ritualXP);
    void addRitualXP(float ritualXP);
    void removeRitualXP(float ritualXP);
    int getRitualLevel();
    Tuple<Float, Float> getRitualXPToNextLevel();

    CompoundNBT serializeNBT();
    void deserializeNBT(CompoundNBT nbt);

    void useCharge(int chargeLevel, List<MagicElement> elements, List<MagicSchool> schools, @Nullable EnumMagicTool tool);

    enum EnumMagicTool implements IStringSerializable {
        SPELL,
        RITUAL,
        POTION;

        public static EnumMagicTool fromName(String name){
            for(EnumMagicTool tool : values()){
                if(tool.getSerializedName().equalsIgnoreCase(name))
                    return tool;
            }
            return null;
        }

        @Override
        public String getSerializedName() {
            return name().toLowerCase();
        }
    }

}
