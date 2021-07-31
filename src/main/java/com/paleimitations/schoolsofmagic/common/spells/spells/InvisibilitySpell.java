package com.paleimitations.schoolsofmagic.common.spells.spells;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.MagicElement;
import com.paleimitations.schoolsofmagic.common.registries.MagicElementRegistry;
import com.paleimitations.schoolsofmagic.common.registries.MagicSchoolRegistry;
import com.paleimitations.schoolsofmagic.common.registries.SoundRegistry;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import com.paleimitations.schoolsofmagic.common.spells.events.SpellEvent;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasAdjustableElements;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasDuration;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasMultiUses;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class InvisibilitySpell extends Spell implements IHasDuration, IHasAdjustableElements {

    private int maxDuration = 400;
    private int duration = 0;

    public InvisibilitySpell() {
        super();
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(References.MODID,"invisibility");
    }

    @Override
    public int getMinimumSpellChargeLevel() {
        return 2;
    }

    @Override
    public void init() {
        super.init();
        this.associations.add(MagicSchoolRegistry.ILLUSION);
        this.associations.add(MagicElementRegistry.UMBRAMANCY);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand, ItemStack stack) {
        player.startUsingItem(hand);
        return ActionResult.success(stack);
    }

    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity living, Hand hand) {
        if (this.castSpell(player) && !living.hasEffect(Effects.INVISIBILITY)) {
            SpellEvent.Duration event = new SpellEvent.Duration(this);
            event.setMultiplier(1f + 0.5f * (this.currentSpellChargeLevel-getMinimumSpellChargeLevel()));
            MinecraftForge.EVENT_BUS.post(event);
            living.addEffect(new EffectInstance(Effects.INVISIBILITY, Math.round(400f*event.getMultiplier())));
            player.playSound(SoundRegistry.INVISIBILITY, 0.5f, player.getRandom().nextFloat() * 0.4F + 0.8F);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if(player.hasEffect(Effects.INVISIBILITY)) {
                EffectInstance inst = player.getEffect(Effects.INVISIBILITY);
                this.duration = Math.min(inst.getDuration(), maxDuration);
            }
        }
    }

    @Override
    public int getUseLength() {
        return 45;
    }

    @Override
    public void releaseUsing(ItemStack stack, World world, LivingEntity living, int count) {
        if (living instanceof PlayerEntity && this.castSpell((PlayerEntity) living) && !living.hasEffect(Effects.INVISIBILITY)) {
            PlayerEntity player = (PlayerEntity) living;
            SpellEvent.Duration event = new SpellEvent.Duration(this);
            event.setMultiplier(1f + 0.5f * (this.currentSpellChargeLevel-getMinimumSpellChargeLevel()));
            MinecraftForge.EVENT_BUS.post(event);
            this.maxDuration = Math.round(400f*event.getMultiplier());
            this.duration = maxDuration;
            player.addEffect(new EffectInstance(Effects.INVISIBILITY, this.maxDuration));
            player.playSound(SoundRegistry.INVISIBILITY, 0.5f, player.getRandom().nextFloat() * 0.4F + 0.8F);
        }
    }

    @Override
    public int getDefaultDuration(int chargeLevel) {
        return maxDuration;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = super.serializeNBT();
        nbt.putInt("duration", this.duration);
        nbt.putInt("maxDuration", this.maxDuration);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt);
        this.maxDuration = nbt.getInt("maxDuration");
        this.duration = nbt.getInt("duration");
    }

    @Override
    public boolean isAcceptableElement(MagicElement element) {
        return element == MagicElementRegistry.HIEROMANCY;
    }
}
