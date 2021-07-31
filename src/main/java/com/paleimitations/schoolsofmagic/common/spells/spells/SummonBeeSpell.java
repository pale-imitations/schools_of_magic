package com.paleimitations.schoolsofmagic.common.spells.spells;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.data.Utils;
import com.paleimitations.schoolsofmagic.common.data.capabilities.conjured_data.ConjuredDataProvider;
import com.paleimitations.schoolsofmagic.common.data.capabilities.conjured_data.IConjuredData;
import com.paleimitations.schoolsofmagic.common.registries.MagicElementRegistry;
import com.paleimitations.schoolsofmagic.common.registries.MagicSchoolRegistry;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import com.paleimitations.schoolsofmagic.common.spells.events.SpellEvent;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasArea;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasDuration;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.util.Random;

public class SummonBeeSpell extends Spell implements IHasDuration, IHasPower, IHasArea {

    private int maxDuration = 500;
    private int duration = 0;

    @Override
    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(References.MODID,"summon_bee");
    }

    @Override
    public int getMinimumSpellChargeLevel() {
        return 1;
    }

    @Override
    public void init() {
        super.init();
        this.associations.add(MagicSchoolRegistry.CONJURATION);
        this.associations.add(MagicSchoolRegistry.EVOCATION);
        this.associations.add(MagicElementRegistry.ANIMANCY);
    }

    @Override
    public int getDurationForCharge(int chargeLevel) {
        return 500;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public int getMaxDuration() {
        return maxDuration;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand, ItemStack stack) {
        ItemStack itemstack = player.getItemInHand(hand);
        SpellEvent.Power powerEvent = new SpellEvent.Power(this);
        MinecraftForge.EVENT_BUS.post(powerEvent);

        SpellEvent.Area areaEvent = new SpellEvent.Area(this);
        MinecraftForge.EVENT_BUS.post(areaEvent);

        LivingEntity base = Utils.getEntityOnVec(world, player, 20 + areaEvent.getAddition() + (currentSpellChargeLevel - getMinimumSpellChargeLevel()) * 5);
        if(base != null && base!=player && this.castSpell(player)) {
            this.maxDuration = this.getDurationForCharge(currentSpellChargeLevel);
            this.duration = this.maxDuration;
            for(int i = 0; i <= currentSpellChargeLevel + powerEvent.getAddition(); ++ i) {
                BeeEntity bee = new BeeEntity(EntityType.BEE, player.level);
                Random random = player.getRandom();
                double x = player.getX() + (random.nextFloat() - random.nextFloat())*2d;
                double y = player.getY() + (random.nextFloat() - random.nextFloat());
                double z = player.getZ() + (random.nextFloat() - random.nextFloat())*2d;
                BlockPos pos = new BlockPos(x,y,z);
                while(!player.level.getBlockState(pos).isAir()) {
                    x = player.getX() + (random.nextFloat() - random.nextFloat())*2d;
                    y = player.getY() + (random.nextFloat() - random.nextFloat());
                    z = player.getZ() + (random.nextFloat() - random.nextFloat())*2d;
                    pos = new BlockPos(x,y,z);
                }
                bee.setPos(x,y,z);
                bee.setTarget(base);
                IConjuredData data = bee.getCapability(ConjuredDataProvider.CONJURED_DATA_CAPABILITY).orElseThrow(IllegalStateException::new);
                if(!player.level.isClientSide) {
                    player.level.addFreshEntity(bee);
                }
                data.setConjured(true, duration, true);
            }
            return ActionResult.success(itemstack);
        }

        return ActionResult.pass(itemstack);
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
}
