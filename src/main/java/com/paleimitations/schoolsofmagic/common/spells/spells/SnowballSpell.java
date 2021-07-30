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
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class SnowballSpell extends Spell implements IHasPower, IHasMultiUses {

	public SnowballSpell() {
		super(new ResourceLocation(References.MODID,"snowball"), 0, 0, generateSchoolMap(), generateElementMap(),
				Lists.newArrayList(MagicSchoolRegistry.EVOCATION), Lists.newArrayList(MagicElementRegistry.CRYOMANCY),
				Lists.newArrayList());
	}

	public SnowballSpell(CompoundNBT nbt){
		this.deserializeNBT(nbt);
	}

	@Override
	public int getUsesPerCharge(int chargeLevel) {
		SpellEvent.UsesPerCharge event = new SpellEvent.UsesPerCharge(this, chargeLevel, 9 + 10 * chargeLevel);
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
			SnowballEntity snowball = new SnowballEntity(world,player);
			snowball.shoot(vec.x, vec.y, vec.z, 1.5F * event.getMultiplier(), 1F);
			player.level.playSound(player, player.position().x, player.position().y, player.position().z, SoundEvents.SNOWBALL_THROW, SoundCategory.PLAYERS, 1.0F, player.getRandom().nextFloat() * 0.4F + 0.8F);
			snowball.setOwner(player);
			if(!world.isClientSide) world.addFreshEntity(snowball);
			return ActionResult.success(stack);
		}

		return ActionResult.pass(stack);
	}
}
