package com.paleimitations.schoolsofmagic.common.spells;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.IMagicType;
import com.paleimitations.schoolsofmagic.common.MagicElement;
import com.paleimitations.schoolsofmagic.common.MagicSchool;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.IMagicData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.MagicDataProvider;
import com.paleimitations.schoolsofmagic.common.registries.SpellRegistry;
import com.paleimitations.schoolsofmagic.common.spells.events.SpellEvent;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasMaterialComponents;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasMultiUses;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.Modifier;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public class Spell implements INBTSerializable<CompoundNBT> {

    public int currentSpellChargeLevel;
    public int lastSpellChargeLevel;
    public List<SpellRequirement> requirements = Lists.newArrayList();
    public List<IMagicType> associations = Lists.newArrayList();
    public List<Modifier> modifiers = Lists.newArrayList();

    public Spell() {
        this.currentSpellChargeLevel = this.getMinimumSpellChargeLevel();
        this.lastSpellChargeLevel = this.getMinimumSpellChargeLevel();
        this.init();
    }

    public void init() {
    }

    public int getMinimumSpellChargeLevel() {
        return 0;
    }

    public int getMaximumSpellChargeLevel() {
        return 8;
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(References.MODID, "none");
    }

    public List<MagicSchool> getSchools() {
        List<MagicSchool> schoolList = Lists.newArrayList();
        for(IMagicType type : this.associations)
            if(type instanceof MagicSchool)
                schoolList.add((MagicSchool) type);
        return schoolList;
    }

    public List<MagicElement> getElements() {
        List<MagicElement> elementList = Lists.newArrayList();
        for(IMagicType type : this.associations)
            if(type instanceof MagicElement)
                elementList.add((MagicElement) type);
        return elementList;
    }

    public String getName() {
        return this.getResourceLocation().getPath();
    }

    public ResourceLocation getSpellIcon() {
        return new ResourceLocation(this.getResourceLocation().getNamespace(), "textures/gui/spell_icons/"+getName()+".png");
    }

    public IMagicData getMagicData(PlayerEntity player) {
        return player.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY, null).orElseThrow(IllegalStateException::new);
    }

    public boolean canCast(PlayerEntity player) {
        IMagicData data = this.getMagicData(player);
        final boolean cancelled = MinecraftForge.EVENT_BUS.post(new SpellEvent.Cast(this));
        if(this.isDisabled() || cancelled)
            return false;
        if(player.isCreative())
            return true;
        if(data.hasChargeLevel(currentSpellChargeLevel) || (this instanceof IHasMultiUses && ((IHasMultiUses)this).getUses()>0)) {
            for(SpellRequirement requirement : requirements) {
                if(!requirement.test(player, data))
                    return false;
            }
            if(this instanceof IHasMaterialComponents) {
                if(!((IHasMaterialComponents) this).hasComponents(player, data))
                    return false;
            }
            return true;
        }
        return false;
    }

    public boolean castSpell(PlayerEntity player) {
        if(canCast(player)) {
            IMagicData data = this.getMagicData(player);
            /*if (!materialComponents.isEmpty()) {
                SpellEvent.MaterialCost costEvent = new SpellEvent.MaterialCost(this);
                MinecraftForge.EVENT_BUS.post(costEvent);
                Random rand = new Random();
                for (ItemStack stack : materialComponents) {
                    for (ItemStack inventoryStack : player.inventory.items) {
                        if (stack.sameItem(inventoryStack) && (!inventoryStack.isDamageableItem() || inventoryStack.getDamageValue() + stack.getCount() < inventoryStack.getMaxDamage())) {
                            if(rand.nextFloat() >= costEvent.getDiscountChance()) {
                                if (inventoryStack.isDamageableItem())
                                    inventoryStack.setDamageValue(inventoryStack.getDamageValue() + stack.getCount());
                                else
                                    inventoryStack.shrink(stack.getCount());
                            }
                            break;
                        }
                    }
                }
            }*/
            if(this instanceof IHasMaterialComponents)
                ((IHasMaterialComponents) this).useMaterialComponent();
            if(this instanceof IHasMultiUses) {
                ((IHasMultiUses) this).castMultiUseSpell(player, data);
            }
            else {
                if(!player.isCreative() || data.hasChargeLevel(this.currentSpellChargeLevel))
                    data.useCharge(this.currentSpellChargeLevel, this.getElements(), this.getSchools(), IMagicData.EnumMagicTool.SPELL);
                this.lastSpellChargeLevel = this.currentSpellChargeLevel;
            }
            return true;
        }
        return false;
    }

    public boolean isDisabled() {
        return false;
    }

    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity living, Hand hand) {
        return ActionResultType.PASS;
    }

    public void releaseUsing(ItemStack stack, World world, LivingEntity living, int count) {
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    }

    public void onUsingTick(ItemStack stack, LivingEntity living, int count) {
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand, ItemStack stack) {
        return ActionResult.pass(stack);
    }

    public boolean useOnRelease() {
        return false;
    }

    public void onUseTick(World world, LivingEntity living, ItemStack stack, int count) {

    }

    public ActionResultType useOn(ItemUseContext context) {
        return ActionResultType.PASS;
    }

    public boolean onPlayerSwing(ItemStack stack, PlayerEntity player) {
        return false;
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity living, LivingEntity attacker) {
        return false;
    }

    public boolean mineBlock(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity living) {
        return false;
    }

    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, PlayerEntity player) {
        return false;
    }

    public UseAction getAction() {
        return UseAction.NONE;
    }

    public int getUseLength() {
        return 0;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("resourceLocation", this.getResourceLocation().toString());
        nbt.putInt("currentSpellChargeLevel", this.currentSpellChargeLevel);
        nbt.putInt("lastSpellChargeLevel", this.lastSpellChargeLevel);
        if(!modifiers.isEmpty()) {
            ListNBT list = new ListNBT();
            for(int i = 0; i < modifiers.size(); i ++)
                list.add(i, StringNBT.valueOf(modifiers.get(i).getLocation().toString()));
            nbt.put("modifiers", list);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.currentSpellChargeLevel = nbt.getInt("currentSpellChargeLevel");
        this.lastSpellChargeLevel = nbt.getInt("lastSpellChargeLevel");
        this.modifiers.clear();
        if(nbt.contains("modifiers")) {
            ListNBT list = (ListNBT) nbt.get("modifiers");
            list.forEach(inbt -> {
                Modifier mod = SpellRegistry.getModifier(inbt.getAsString());
                if(mod!=null)
                    modifiers.add(mod);
            });
        }
    }
}
