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
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasArea;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasMultiUses;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasPower;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;

import java.util.stream.Collectors;

public class GrowthSpell extends Spell implements IHasPower, IHasArea, IHasAdjustableElements {

	public GrowthSpell() {
		super(new ResourceLocation(References.MODID,"growth"), 1, 0, generateSchoolMap(), generateElementMap(),
				Lists.newArrayList(MagicSchoolRegistry.ABJURATION), Lists.newArrayList(MagicElementRegistry.ANIMANCY),
				Lists.newArrayList());
	}

	public GrowthSpell(CompoundNBT nbt){
		this.deserializeNBT(nbt);
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {
		BlockPos pos = context.getClickedPos();
		BlockState state = context.getLevel().getBlockState(pos);
		SpellEvent.Power power = new SpellEvent.Power(this);
		MinecraftForge.EVENT_BUS.post(power);
		SpellEvent.Area area = new SpellEvent.Area(this);
		area.setAddition(((float)this.currentSpellChargeLevel)/2f);
		MinecraftForge.EVENT_BUS.post(area);
		if(state.getBlock() instanceof IGrowable) {
			IGrowable growable = (IGrowable)state.getBlock();
			if(growable.isValidBonemealTarget(context.getLevel(), pos, state, context.getLevel().isClientSide) && this.castSpell(context.getPlayer())) {
				int radius = Math.round(area.getAddition());
				for(int i = 0; i <= Math.round(power.getAddition()); ++ i) {
					for(BlockPos adPos : BlockPos.betweenClosed(pos.offset(-radius, 0, -radius), pos.offset(radius, 0, radius))) {
						/*BlockState adState = context.getLevel().getBlockState(adPos);
						if(adState.getBlock() instanceof IGrowable) {
							if(((IGrowable)adState.getBlock()).isValidBonemealTarget(context.getLevel(), adPos, adState, context.getLevel().isClientSide)) {
								if (context.getLevel() instanceof ServerWorld)
									((IGrowable)adState.getBlock()).performBonemeal((ServerWorld) context.getLevel(), context.getPlayer().getRandom(), pos, state);
								else if(!context.getLevel().isClientSide) {
									context.getLevel().levelEvent(2005, adPos, 0);
								}
							}
						}*/
						BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL), context.getLevel(), adPos, context.getPlayer());
					}
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
