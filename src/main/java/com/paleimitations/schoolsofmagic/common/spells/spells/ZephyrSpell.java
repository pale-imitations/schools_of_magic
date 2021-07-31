package com.paleimitations.schoolsofmagic.common.spells.spells;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.MagicElement;
import com.paleimitations.schoolsofmagic.common.registries.MagicElementRegistry;
import com.paleimitations.schoolsofmagic.common.registries.MagicSchoolRegistry;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import com.paleimitations.schoolsofmagic.common.spells.events.SpellEvent;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasAdjustableElements;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasMultiUses;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasPower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ZephyrSpell extends MultiUseSpell implements IHasPower, IHasAdjustableElements {

    public ZephyrSpell() {
        super();
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(References.MODID,"zephyr");
    }

    @Override
    public int getMinimumSpellChargeLevel() {
        return 1;
    }

    @Override
    public void init() {
        super.init();
        this.associations.add(MagicSchoolRegistry.CONJURATION);
        this.associations.add(MagicElementRegistry.AEROMANCY);
    }

    @Override
    public int getUsesPerCharge(int chargeLevel) {
        SpellEvent.UsesPerCharge event = new SpellEvent.UsesPerCharge(this, chargeLevel, 200 + (chargeLevel-getMinimumSpellChargeLevel())*200);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getUses();
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand, ItemStack stack) {
        player.startUsingItem(hand);
        return ActionResult.success(stack);
    }

    @Override
    public int getUseLength() {
        return this.remainingUses == 0 ? maxUses : this.remainingUses;
    }

    @Override
    public void onUseTick(World world, LivingEntity living, ItemStack stack, int count) {
        if (living instanceof PlayerEntity && !living.isUnderWater() && this.castSpell((PlayerEntity) living)) {
            PlayerEntity player = (PlayerEntity) living;
            Vector3d vec = player.getLookAngle();
            SpellEvent.Power event = new SpellEvent.Power(this);
            event.setMultiplier(1f + 0.05f * currentSpellChargeLevel);
            MinecraftForge.EVENT_BUS.post(event);
            double vX = vec.x * 0.75d * event.getMultiplier();
            double vY = vec.y * 0.75d * event.getMultiplier();
            double vZ = vec.z * 0.75d * event.getMultiplier();
            //if(player.isShiftKeyDown())
                //player.setDeltaMovement(0,0,0);
            //else
                player.setDeltaMovement(vX, vY, vZ);
            if(vY>0.45d)
                player.fallDistance=0f;
        }
    }

    @Override
    public boolean isAcceptableElement(MagicElement element) {
        return element == MagicElementRegistry.HIEROMANCY;
    }
}
