package com.paleimitations.schoolsofmagic.common.spells.spells;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.MagicData;
import com.paleimitations.schoolsofmagic.common.registries.MagicElementRegistry;
import com.paleimitations.schoolsofmagic.common.registries.MagicSchoolRegistry;
import com.paleimitations.schoolsofmagic.common.registries.SoundRegistry;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import com.paleimitations.schoolsofmagic.common.spells.events.SpellEvent;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasPower;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.MinecraftForge;

public class FurnaceFuelSpell extends Spell implements IHasPower {

    @Override
    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(References.MODID,"furnace_fuel");
    }

    @Override
    public void init() {
        super.init();
        this.associations.add(MagicSchoolRegistry.CONJURATION);
        this.associations.add(MagicElementRegistry.PYROMANCY);
    }

    public int burnTimeForCharge(int chargeLevel) {
        switch (chargeLevel) {
            case 0: return 600;
            case 1: return 1000;
            case 2: return 1600;
            case 3: return 2400;
            case 4: return 5000;
            case 5: return 10000;
            case 6: return 16000;
            case 7: return 20000;
            case 8: return 30000;
        }
        return 20000;
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        TileEntity tileEntity = context.getLevel().getBlockEntity(context.getClickedPos());
        if(tileEntity instanceof AbstractFurnaceTileEntity && castSpell(context.getPlayer())) {
            CompoundNBT nbt = tileEntity.save(new CompoundNBT());
            SpellEvent.Power event = new SpellEvent.Power(this);
            MinecraftForge.EVENT_BUS.post(event);
            int burnTime = nbt.getInt("BurnTime") + Math.round((float)burnTimeForCharge(currentSpellChargeLevel) * event.getMultiplier());
            nbt.putInt("BurnTime", burnTime);
            BlockState state = context.getLevel().getBlockState(context.getClickedPos());
            if(state.getBlock() instanceof AbstractFurnaceBlock) {
                state = state.setValue(AbstractFurnaceBlock.LIT, true);
            }
            context.getLevel().playSound(null, context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(),
                    SoundEvents.FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1f, context.getPlayer().getRandom().nextFloat() * 0.4F + 0.8F);
            tileEntity.load(state, nbt);
            tileEntity.setChanged();
            context.getLevel().setBlockAndUpdate(context.getClickedPos(), state);
            return ActionResultType.SUCCESS;
        }
        return super.useOn(context);
    }
}
