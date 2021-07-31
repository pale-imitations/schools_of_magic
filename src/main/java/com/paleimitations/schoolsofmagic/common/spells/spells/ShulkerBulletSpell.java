package com.paleimitations.schoolsofmagic.common.spells.spells;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.data.Utils;
import com.paleimitations.schoolsofmagic.common.registries.MagicElementRegistry;
import com.paleimitations.schoolsofmagic.common.registries.MagicSchoolRegistry;
import com.paleimitations.schoolsofmagic.common.spells.events.SpellEvent;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasPower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ShulkerBulletSpell extends MultiUseSpell implements IHasPower {

    public ShulkerBulletSpell() {
        super();
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(References.MODID,"shulker_bullet");
    }

    @Override
    public int getMinimumSpellChargeLevel() {
        return 1;
    }

    @Override
    public void init() {
        super.init();
        this.associations.add(MagicSchoolRegistry.EVOCATION);
        this.associations.add(MagicElementRegistry.CHAOTIMANCY);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand, ItemStack stack) {
        ItemStack itemstack = player.getItemInHand(hand);
        SpellEvent.Power event = new SpellEvent.Power(this);
        MinecraftForge.EVENT_BUS.post(event);

        LivingEntity base = Utils.getEntityOnVec(world, player, 10 + 4 * event.getMultiplier());
        if(base != null && this.castSpell(player)) {

            ShulkerBulletEntity shulkerBullet = new ShulkerBulletEntity(world, player, base, player.getDirection().getAxis());
            if(!world.isClientSide)world.addFreshEntity(shulkerBullet);

            player.playSound(SoundEvents.SHULKER_SHOOT, 1f, 1f);

            return ActionResult.success(itemstack);
        }

        return ActionResult.pass(itemstack);
    }

    @Override
    public int getUsesPerCharge(int chargeLevel) {
        SpellEvent.UsesPerCharge event = new SpellEvent.UsesPerCharge(this, chargeLevel, 1 + (chargeLevel - getMinimumSpellChargeLevel()) * 2);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getUses();
    }
}
