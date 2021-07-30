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
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.util.Random;

public class HealingSpell extends Spell implements IHasMultiUses {

    public HealingSpell() {
        super(new ResourceLocation(References.MODID,"healing"), 0, 0, generateSchoolMap(), generateElementMap(),
                Lists.newArrayList(MagicSchoolRegistry.ABJURATION), Lists.newArrayList(MagicElementRegistry.ANIMANCY),
                Lists.newArrayList());
    }

    public HealingSpell(CompoundNBT nbt){
        this.deserializeNBT(nbt);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand, ItemStack stack) {
        if(player.getHealth() < player.getMaxHealth() && this.castSpell(player)) {
            player.heal(1f);
            Random random = player.getRandom();
            if(world.isClientSide) {
                for(int i = 0; i < 10; ++i) {
                    double d2 = random.nextGaussian() * 0.02D;
                    double d3 = random.nextGaussian() * 0.02D;
                    double d4 = random.nextGaussian() * 0.02D;
                    double d0 = 0.5D;
                    double d5 = 0.5D;
                    double d6 = player.getX() + d5 + random.nextDouble() * d0 * 2.0D;
                    double d7 = player.getY() + d5 + random.nextDouble() * d0 * 2.0D;
                    double d8 = player.getZ() + d5 + random.nextDouble() * d0 * 2.0D;
                    if (!world.getBlockState((new BlockPos(d6, d7, d8)).below()).isAir()) {
                        world.addParticle(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, d2, d3, d4);
                    }
                    player.level.playSound(player, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0F, player.getRandom().nextFloat() * 0.4F + 0.8F);

                }
            }
        }
        return ActionResult.success(stack);
    }

    @Override
    public int getMaxUses(int chargeLevel) {
        return maxUses;
    }

    @Override
    public int getUses() {
        return remainingUses;
    }

    @Override
    public int getUsesPerCharge(int chargeLevel) {
        SpellEvent.UsesPerCharge event = new SpellEvent.UsesPerCharge(this, chargeLevel, 3 + (chargeLevel-minSpellChargeLevel)*2);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getUses();
    }
}
