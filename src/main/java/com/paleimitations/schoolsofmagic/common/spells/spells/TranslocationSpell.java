package com.paleimitations.schoolsofmagic.common.spells.spells;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.data.Utils;
import com.paleimitations.schoolsofmagic.common.registries.MagicElementRegistry;
import com.paleimitations.schoolsofmagic.common.registries.MagicSchoolRegistry;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import com.paleimitations.schoolsofmagic.common.spells.events.SpellEvent;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasMultiUses;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasPower;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class TranslocationSpell extends Spell implements IHasPower, IHasMultiUses {

    public TranslocationSpell() {
        super(new ResourceLocation(References.MODID,"translocation"), 3, 0, generateSchoolMap(), generateElementMap(),
                Lists.newArrayList(MagicSchoolRegistry.CONJURATION), Lists.newArrayList(MagicElementRegistry.HIEROMANCY),
                Lists.newArrayList());
    }

    public TranslocationSpell(CompoundNBT nbt){
        this.deserializeNBT(nbt);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand, ItemStack stack) {
        ItemStack itemstack = player.getItemInHand(hand);
        SpellEvent.Power event = new SpellEvent.Power(this);
        MinecraftForge.EVENT_BUS.post(event);

        LivingEntity base = Utils.getEntityOnVec(world, player, 20 + event.getAddition() + (lastSpellChargeLevel - minSpellChargeLevel) * 5);
        if(base != null && this.castSpell(player)) {
            double plX = player.getX();
            double plY = player.getY();
            double plZ = player.getZ();
            float plFallDistance = player.fallDistance;
            float plXRot = player.xRot;
            float plYRot = player.yRot;

            double bX = base.getX();
            double bY = base.getY();
            double bZ = base.getZ();
            float bFallDistance = base.fallDistance;
            float bXRot = base.xRot;
            float bYRot = base.yRot;

            player.moveTo(bX, bY, bZ, bXRot, bYRot);
            player.fallDistance = bFallDistance;

            base.moveTo(plX, plY, plZ, plXRot, plYRot);
            base.fallDistance = plFallDistance;

            player.lookAt(EntityAnchorArgument.Type.EYES, new Vector3d(plX, plY, plZ));

            world.playSound(player, bX, bY, bZ, SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
            world.playSound(player, plX, plY, plZ, SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);

            return ActionResult.success(itemstack);
        }

        return ActionResult.pass(itemstack);
    }

    @Override
    public int getUsesPerCharge(int chargeLevel) {
        SpellEvent.UsesPerCharge event = new SpellEvent.UsesPerCharge(this, chargeLevel, 1 + (chargeLevel - 3) * 2);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getUses();
    }

    @Override
    public int getMaxUses(int chargeLevel) {
        return this.maxUses;
    }

    @Override
    public int getUses() {
        return this.remainingUses;
    }
}
