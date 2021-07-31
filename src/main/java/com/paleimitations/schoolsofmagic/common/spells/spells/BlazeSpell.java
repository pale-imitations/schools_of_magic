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
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasMultiUses;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasPower;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class BlazeSpell extends MultiUseSpell implements IHasPower, IHasAdjustableElements {

	public BlazeSpell() {
		super();
	}

	@Override
	public ResourceLocation getResourceLocation() {
		return new ResourceLocation(References.MODID,"blaze");
	}

	@Override
	public void init() {
		super.init();
		this.associations.add(MagicSchoolRegistry.EVOCATION);
		this.associations.add(MagicElementRegistry.PYROMANCY);
	}

	@Override
	public int getUsesPerCharge(int chargeLevel) {
		int chargeUses = 3;
		switch(chargeLevel) {
			case 1: chargeUses = 5; break;
			case 2: chargeUses = 7; break;
			case 3: chargeUses = 9; break;
			case 4: chargeUses = 11; break;
			case 5: chargeUses = 13; break;
			case 6: chargeUses = 15; break;
			case 7: chargeUses = 17; break;
			case 8: chargeUses = 20; break;
		}
		SpellEvent.UsesPerCharge event = new SpellEvent.UsesPerCharge(this, chargeLevel, chargeUses);
		MinecraftForge.EVENT_BUS.post(event);
		return event.getUses();
	}

	@Override
	public int getMaxUses(int chargeLevel) {
		return this.getUsesPerCharge(chargeLevel);
	}

	@Override
	public int getUses() {
		return this.remainingUses;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand, ItemStack stack) {
		if(this.castSpell(player)) {
			Vector3d vec = player.getLookAngle();
			SpellEvent.Power event = new SpellEvent.Power(this);
			MinecraftForge.EVENT_BUS.post(event);
			double vX = vec.x*event.getMultiplier();
			double vY = vec.y*event.getMultiplier();
			double vZ = vec.z*event.getMultiplier();
			/*if(modifiers.keySet().contains(EnumSpellModifier.CHAOTICS) || modifiers.keySet().contains(EnumSpellModifier.INFERNALITY)){
				EntityLargeFireball fireball = new EntityLargeFireball(worldIn, playerIn.posX, playerIn.posY + playerIn.getEyeHeight(), playerIn.posZ, vX, vY, vZ);
				playerIn.playSound(SoundEvents.ENTITY_GHAST_SHOOT, 1.0F, playerIn.getRNG().nextFloat() * 0.4F + 0.8F);
				fireball.explosionPower = 1 + Math.round(0.5f*this.getPowerBonus(playerIn));
				fireball.shootingEntity = playerIn;
				if (!worldIn.isRemote) worldIn.spawnEntity(fireball);
			}
			else {*/
			SmallFireballEntity fireball = new SmallFireballEntity(world, player.getX(), player.getY() + player.getEyeHeight(), player.getZ(), vX, vY, vZ);
			player.level.playSound(player, player.position().x, player.position().y, player.position().z, SoundRegistry.BLAZE, SoundCategory.PLAYERS, 1.0F, player.getRandom().nextFloat() * 0.4F + 0.8F);
			fireball.setOwner(player);
			if(!world.isClientSide) world.addFreshEntity(fireball);
			//}
			return ActionResult.success(stack);
		}

		return ActionResult.pass(stack);
	}

	@Override
	public boolean isAcceptableElement(MagicElement element) {
		return element == MagicElementRegistry.CHAOTICS || element == MagicElementRegistry.INFERNALITY;
	}
}
