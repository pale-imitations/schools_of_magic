package com.paleimitations.schoolsofmagic.common.spells.events;

import com.paleimitations.schoolsofmagic.common.spells.Spell;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

/**
 * SpellEvent is fired when an event involving any Spell occurs.<br>
 * If a method utilizes this {@link com.paleimitations.schoolsofmagic.common.spells.Spell} as its parameter, the method will
 * receive every child event of this class.<br>
 * <br>
 * {@link #spell} contains the spell that caused this event to occur.<br>
 * <br>
 * All children of this event are fired on the {@link MinecraftForge#EVENT_BUS}.<br>
 **/
public class SpellEvent extends Event {
    private final Spell spell;

    public SpellEvent(Spell spell) {
        this.spell = spell;
    }

    public Spell getSpell() {
        return spell;
    }

    /**
     * Cast is fired when a Spell is cast. <br>
     * <br>
     * This event is {@link net.minecraftforge.eventbus.api.Cancelable}, causing the spell to fail it's casting.<br>
     * <br>
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.<br>
     **/
    public static class Cast extends SpellEvent {

        public Cast(Spell spell) {
            super(spell);
        }
    }

    /**
     * Uses is fired when a Spell's number of uses per spell charge is calculated. <br>
     * {@link #spellChargeLevel} the level of the spell charge being used to cast the spell.<br>
     * {@link #uses} the number of times the spell can be used before the charge runs out (this can be in ticks or clicks depending on the spell).<br>
     * {@link #defaultUses} the default number of times the spell can be used before the charge runs out (this can be in ticks or clicks depending on the spell).<br>
     * <br>
     * This event is not {@link net.minecraftforge.eventbus.api.Cancelable}.<br>
     * <br>
     * This event does not have a result. {@link HasResult}<br>
     * <br>
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.<br>
     **/
    public static class UsesPerCharge extends SpellEvent {

        private final int defaultUses;
        private final int spellChargeLevel;
        private int uses;

        public UsesPerCharge(Spell spell, int spellChargeLevel, int defaultUses) {
            super(spell);
            this.spellChargeLevel = spellChargeLevel;
            this.defaultUses = defaultUses;
            this.uses = defaultUses;
        }

        public int getSpellChargeLevel() {
            return spellChargeLevel;
        }

        public int getDefaultUses() {
            return defaultUses;
        }

        public int getUses() {
            return uses;
        }

        public void setUses(int uses) {
            this.uses = uses;
        }
    }

    /**
     * Power is fired when a Spell's power is being calculated. <br>
     * Power can be calculated as additive or multiplicative depending on the context of the spell, so both should be adjusted when subscribed to. <br>
     * <br>
     * This event is not {@link net.minecraftforge.eventbus.api.Cancelable}.<br>
     * <br>
     * This event does not have a result. {@link HasResult}<br>
     * <br>
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.<br>
     **/
    public static class Power extends SpellEvent {

        private float multiplier = 1f;
        private float addition = 0f;

        public Power(Spell spell) {
            super(spell);
        }

        public float getAddition() {
            return addition;
        }

        public float getMultiplier() {
            return multiplier;
        }

        public void setAddition(float addition) {
            this.addition = addition;
        }

        public void setMultiplier(float multiplier) {
            this.multiplier = multiplier;
        }
    }

    /**
     * Area is fired when a Spell's area of effect is being calculated, depending on whether the spell has a duration element. <br>
     * <br>
     * This event is not {@link net.minecraftforge.eventbus.api.Cancelable}.<br>
     * <br>
     * This event does not have a result. {@link HasResult}<br>
     * <br>
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.<br>
     **/
    public static class Area extends SpellEvent {

        private float multiplier = 1f;
        private float addition = 0f;

        public Area(Spell spell) {
            super(spell);
        }

        public float getMultiplier() {
            return multiplier;
        }

        public float getAddition() {
            return addition;
        }

        public void setMultiplier(float multiplier) {
            this.multiplier = multiplier;
        }

        public void setAddition(float addition) {
            this.addition = addition;
        }
    }

    /**
     * Duration is fired when a Spell's duration is being calculated, depending on whether the spell has a duration element. <br>
     * <br>
     * This event is not {@link net.minecraftforge.eventbus.api.Cancelable}.<br>
     * <br>
     * This event does not have a result. {@link HasResult}<br>
     * <br>
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.<br>
     **/
    public static class Duration extends SpellEvent {

        private float multiplier = 1f;

        public Duration(Spell spell) {
            super(spell);
        }

        public float getMultiplier() {
            return multiplier;
        }

        public void setMultiplier(float multiplier) {
            this.multiplier = multiplier;
        }
    }

    /**
     * MaterialCost is fired when a Spell has a material cost. <br>
     * MaterialCost calculates the chance that the material cost is discounted. <br>
     * <br>
     * This event is not {@link net.minecraftforge.eventbus.api.Cancelable}.<br>
     * <br>
     * This event does not have a result. {@link HasResult}<br>
     * <br>
     * This event is fired on the {@link MinecraftForge#EVENT_BUS}.<br>
     **/
    public static class MaterialCost extends SpellEvent {

        private float discountChance = 0f;

        public MaterialCost(Spell spell) {
            super(spell);
        }

        public float getDiscountChance() {
            return discountChance;
        }

        public void setDiscountChance(float discountChance) {
            this.discountChance = discountChance;
        }
    }
}
