package com.paleimitations.schoolsofmagic.common.spells.spells;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.MagicElement;
import com.paleimitations.schoolsofmagic.common.registries.MagicElementRegistry;
import com.paleimitations.schoolsofmagic.common.registries.MagicSchoolRegistry;
import com.paleimitations.schoolsofmagic.common.spells.events.SpellEvent;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasAdjustableElements;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasPower;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;

public class GrowthSpell extends MultiUseSpell implements IHasPower, IHasAdjustableElements {

	public GrowthSpell() {
		super();
	}

	@Override
	public ResourceLocation getResourceLocation() {
		return new ResourceLocation(References.MODID,"growth");
	}

	@Override
	public int getMinimumSpellChargeLevel() {
		return 0;
	}

	@Override
	public void init() {
		super.init();
		this.associations.add(MagicSchoolRegistry.TRANSFIGURATION);
		this.associations.add(MagicElementRegistry.HERBALMANCY);
	}

	@Override
	public int getUsesPerCharge(int chargeLevel) {
		return 5 + chargeLevel * 5;
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {
		BlockPos pos = context.getClickedPos();
		BlockState state = context.getLevel().getBlockState(pos);
		SpellEvent.Power power = new SpellEvent.Power(this);
		MinecraftForge.EVENT_BUS.post(power);
		if(state.getBlock() instanceof IGrowable) {
			IGrowable growable = (IGrowable)state.getBlock();
			if(growable.isValidBonemealTarget(context.getLevel(), pos, state, context.getLevel().isClientSide) && this.castSpell(context.getPlayer())) {
				for(int i = 0; i <= Math.round(power.getAddition()); ++ i) {
					BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL), context.getLevel(), pos, context.getPlayer());
				}
				return ActionResultType.SUCCESS;
			}
		}
		return super.useOn(context);
	}

	@Override
	public boolean isAcceptableElement(MagicElement element) {
		return element == MagicElementRegistry.HYDROMANCY;
	}
}
