package com.paleimitations.schoolsofmagic.common.spells.spells;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.data.Utils;
import com.paleimitations.schoolsofmagic.common.registries.MagicElementRegistry;
import com.paleimitations.schoolsofmagic.common.registries.MagicSchoolRegistry;
import com.paleimitations.schoolsofmagic.common.registries.SoundRegistry;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import com.paleimitations.schoolsofmagic.common.spells.events.SpellEvent;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasArea;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasPower;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class RumorSpell extends Spell implements IHasArea, IHasPower {

    public LivingEntity enchantedEntity;
    public int timeLimit;

    @Override
    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(References.MODID,"rumor");
    }

    @Override
    public void init() {
        super.init();
        this.associations.add(MagicSchoolRegistry.ILLUSION);
        this.associations.add(MagicElementRegistry.CHAOTIMANCY);
    }

    public boolean enchantable(LivingEntity living, int chargeLevel, float powerAdjuster) {
        if(!(living instanceof IMob || living instanceof IAngerable || living instanceof IRangedAttackMob))
            return false;
        switch(chargeLevel) {
            case 0: return living.getHealth() < 8f * powerAdjuster;
            case 1: return living.getHealth() < 16f * powerAdjuster;
            case 2: return living.getHealth() < 32f * powerAdjuster;
            case 3: return living.getHealth() < 48f * powerAdjuster;
            case 4: return living.getHealth() < 64f * powerAdjuster;
            case 5: return living.getHealth() < 100f * powerAdjuster;
            case 6: return living.getHealth() < 150f * powerAdjuster;
            case 7: return living.getHealth() < 200f * powerAdjuster;
            case 8: return true;
        }
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if(timeLimit>0) {
            timeLimit--;
        }
        if(timeLimit==0 && enchantedEntity!=null)
            enchantedEntity=null;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand, ItemStack stack) {
        ItemStack itemstack = player.getItemInHand(hand);
        SpellEvent.Power event = new SpellEvent.Power(this);
        MinecraftForge.EVENT_BUS.post(event);

        LivingEntity base = Utils.getEntityOnVec(world, player, 20 + 5 * event.getMultiplier() + 5 * currentSpellChargeLevel);
        if(canCast(player) && enchantedEntity == null && base != null && enchantable(base, currentSpellChargeLevel, event.getMultiplier())) {
            this.enchantedEntity = base;
            player.playSound(SoundRegistry.WHISPER, 1f, 1f);
            timeLimit=500;
        }
        else if(enchantedEntity != null && base != enchantedEntity && base != null && castSpell(player)) {
            if(enchantedEntity instanceof IAngerable) {
                //((IAngerable) enchantedEntity).setPersistentAngerTarget(base.getUUID());
                ((IAngerable) enchantedEntity).setTarget(base);
            }
            else if(enchantedEntity instanceof IMob || enchantedEntity instanceof IRangedAttackMob) {
                if(enchantedEntity instanceof MobEntity) {
                    ((MobEntity)enchantedEntity).setTarget(base);
                }
                else {
                    enchantedEntity.setLastHurtByMob(base);
                }
            }
            enchantedEntity=null;
            player.playSound(SoundRegistry.WHISPER, 1f, 1f);
        }

        return ActionResult.pass(itemstack);
    }
}
