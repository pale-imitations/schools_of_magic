package com.paleimitations.schoolsofmagic.common.spells;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.MagicElement;
import com.paleimitations.schoolsofmagic.common.MagicSchool;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.IMagicData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.MagicDataProvider;
import com.paleimitations.schoolsofmagic.common.registries.MagicElementRegistry;
import com.paleimitations.schoolsofmagic.common.registries.MagicSchoolRegistry;
import com.paleimitations.schoolsofmagic.common.spells.events.SpellEvent;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Spell implements INBTSerializable<CompoundNBT> {

    private ResourceLocation resourceLocation;
    public int currentSpellChargeLevel;
    public int lastSpellChargeLevel;
    public int remainingUses;
    public int maxUses;
    public int minSpellChargeLevel;
    public int minSpellLevel;
    public int[] minSchoolLevels = new int[MagicSchoolRegistry.SCHOOLS.size()];
    public int[] minElementLevels = new int[MagicElementRegistry.ELEMENTS.size()];
    public boolean[] schools = new boolean[MagicSchoolRegistry.SCHOOLS.size()];
    public boolean[] elements = new boolean[MagicElementRegistry.ELEMENTS.size()];
    public List<ItemStack> materialComponents;

    public Spell(ResourceLocation resourceLocationIn, int minSpellChargeLevelIn, int minSpellLevelIn, Map<MagicSchool, Integer> minSchoolLevelsIn,
                 Map<MagicElement, Integer> minElementLevelsIn, List<MagicSchool> schoolsIn, List<MagicElement> elementsIn, List<ItemStack> materialComponentsIn) {
        this.resourceLocation = resourceLocationIn;
        this.minSpellChargeLevel = minSpellChargeLevelIn;
        this.currentSpellChargeLevel = minSpellChargeLevelIn;
        this.lastSpellChargeLevel = minSpellChargeLevelIn;
        this.maxUses = this.getUsesPerCharge(this.lastSpellChargeLevel);
        this.remainingUses = 0;
        this.minSpellLevel = minSpellLevelIn;
        for(MagicSchool school : minSchoolLevelsIn.keySet())
            minSchoolLevels[school.getId()] = minSchoolLevelsIn.get(school);
        for(MagicElement element : minElementLevelsIn.keySet())
            minElementLevels[element.getId()] = minElementLevelsIn.get(element);
        for(int i = 0; i < MagicSchoolRegistry.SCHOOLS.size(); ++i)
            schools[i] = schoolsIn.contains(MagicSchoolRegistry.getSchoolFromId(i));
        for(int i = 0; i < MagicElementRegistry.ELEMENTS.size(); ++i)
            elements[i] = elementsIn.contains(MagicElementRegistry.getElementFromId(i));
        this.materialComponents = materialComponentsIn==null? Lists.newArrayList() : materialComponentsIn;
    }

    public Spell(CompoundNBT nbt) {
        this.deserializeNBT(nbt);
    }

    public Spell(){
        this(new ResourceLocation(References.MODID, "none"), 0, 0, generateSchoolMap(), generateElementMap(), Lists.newArrayList(), Lists.newArrayList(), Lists.newArrayList());
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public static Map<MagicSchool, Integer> generateSchoolMap(Map.Entry<MagicSchool, Integer>... entries){
        Map<MagicSchool, Integer> map = Maps.newHashMap();
        for(MagicSchool school : MagicSchoolRegistry.SCHOOLS)
            map.put(school, 0);
        for(Map.Entry<MagicSchool, Integer> entry : entries)
            map.put(entry.getKey(), entry.getValue());
        return map;
    }

    public static Map<MagicElement, Integer> generateElementMap(Map.Entry<MagicElement, Integer>... entries){
        Map<MagicElement, Integer> map = Maps.newHashMap();
        for(MagicElement element : MagicElementRegistry.ELEMENTS)
            map.put(element, 0);
        for(Map.Entry<MagicElement, Integer> entry : entries)
            map.put(entry.getKey(), entry.getValue());
        return map;
    }

    public List<MagicSchool> getSchools() {
        List<MagicSchool> schoolList = Lists.newArrayList();
        for(int i = 0; i<MagicSchoolRegistry.SCHOOLS.size(); ++i)
            if(schools[i])
                schoolList.add(MagicSchoolRegistry.getSchoolFromId(i));
        return schoolList;
    }

    public List<MagicElement> getElements() {
        List<MagicElement> elementList = Lists.newArrayList();
        for(int i = 0; i<MagicElementRegistry.ELEMENTS.size(); ++i)
            if(elements[i])
                elementList.add(MagicElementRegistry.getElementFromId(i));
        return elementList;
    }

    public String getName() {
        return resourceLocation.getPath();
    }

    public ResourceLocation getSpellIcon() {
        return new ResourceLocation(resourceLocation.getNamespace(), "textures/gui/spell_icons/"+getName()+".png");
    }

    public IMagicData getMagicData(PlayerEntity player) {
        return player.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY, null).orElseThrow(IllegalStateException::new);
    }

    public int getUsesPerCharge(int chargeLevel) {
        return 0;
    }

    public boolean canCast(PlayerEntity player) {
        IMagicData data = this.getMagicData(player);
        final boolean cancelled = MinecraftForge.EVENT_BUS.post(new SpellEvent.Cast(this));
        if(this.isDisabled() || cancelled)
            return false;
        if(player.isCreative())
            return true;
        if(this.remainingUses>0 || data.hasChargeLevel(currentSpellChargeLevel)) {
            for(int i = 0; i < MagicElementRegistry.ELEMENTS.size(); i ++)
                if(data.getElementLevel(MagicElementRegistry.getElementFromId(i))<this.minElementLevels[i]) {
                    if(!player.getCommandSenderWorld().isClientSide)
                        player.sendMessage(new StringTextComponent("You aren't high enough level to use this spell."), Util.NIL_UUID);
                    return false;
                }
            for(int i = 0; i < MagicSchoolRegistry.SCHOOLS.size(); i ++)
                if(data.getSchoolLevel(MagicSchoolRegistry.getSchoolFromId(i))<this.minSchoolLevels[i]) {
                    if(!player.getCommandSenderWorld().isClientSide)
                        player.sendMessage(new StringTextComponent("You aren't high enough level to use this spell."), Util.NIL_UUID);
                    return false;
                }
            if(!materialComponents.isEmpty()) {
                for(ItemStack stack : materialComponents){
                    if(!player.inventory.contains(stack)) {
                        if(!player.getCommandSenderWorld().isClientSide)
                            player.sendMessage(new StringTextComponent("You're missing a material component."), Util.NIL_UUID);
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean castSpell(PlayerEntity player) {
        if(canCast(player)) {
            IMagicData data = this.getMagicData(player);
            if (!materialComponents.isEmpty()) {
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
            }
            if(this.remainingUses==0) {
                if(!player.isCreative() || data.hasChargeLevel(this.currentSpellChargeLevel))
                    data.useCharge(this.currentSpellChargeLevel, this.getElements(), this.getSchools(), IMagicData.EnumMagicTool.SPELL);
                this.remainingUses = this.getUsesPerCharge(this.currentSpellChargeLevel);
                this.maxUses = this.remainingUses;
                this.lastSpellChargeLevel = this.currentSpellChargeLevel;
            }
            else
                this.remainingUses--;
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

    public int[] getMinimumSchoolLevels() {
        return minSchoolLevels;
    }

    public int[] getMinimumElementLevels() {
        return minElementLevels;
    }

    public List<ItemStack> getMaterialComponents() {
        return materialComponents;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("resourceLocation", this.resourceLocation.toString());

        nbt.putInt("remainingUses", this.remainingUses);
        nbt.putInt("maxUses", this.maxUses);
        nbt.putInt("minSpellChargeLevel", this.minSpellChargeLevel);
        nbt.putInt("currentSpellChargeLevel", this.currentSpellChargeLevel);
        nbt.putInt("lastSpellChargeLevel", this.lastSpellChargeLevel);
        nbt.putInt("minSpellLevel", this.minSpellLevel);
        nbt.putIntArray("minSchoolLevels", this.minSchoolLevels);
        nbt.putIntArray("minElementLevels", this.minElementLevels);
        for(int i = 0; i < MagicSchoolRegistry.SCHOOLS.size(); ++i)
            nbt.putBoolean("school"+i, schools[i]);
        for(int i = 0; i < MagicElementRegistry.ELEMENTS.size(); ++i)
            nbt.putBoolean("element"+i, elements[i]);

        nbt.putInt("materialComponents_size",materialComponents.size());
        int m = 0;
        for(ItemStack stack : materialComponents){
            nbt.put("materialComponent"+m, stack.serializeNBT());
            ++m;
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.resourceLocation = new ResourceLocation(nbt.getString("resourceLocation"));
        this.remainingUses = nbt.getInt("remainingUses");
        this.maxUses = nbt.getInt("maxUses");
        this.minSpellChargeLevel = nbt.getInt("minSpellChargeLevel");
        this.currentSpellChargeLevel = nbt.getInt("currentSpellChargeLevel");
        this.lastSpellChargeLevel = nbt.getInt("lastSpellChargeLevel");
        this.minSpellLevel = nbt.getInt("minSpellLevel");
        this.minSchoolLevels = nbt.getIntArray("minSchoolLevels");
        this.minElementLevels = nbt.getIntArray("minElementLevels");
        for(int i = 0; i < MagicSchoolRegistry.SCHOOLS.size(); ++ i)
            schools[i] = nbt.getBoolean("school"+i);
        for(int i = 0; i < MagicElementRegistry.ELEMENTS.size(); ++ i)
            elements[i] = nbt.getBoolean("element"+i);
        List<ItemStack> materialComponentsIn = Lists.newArrayList();

        for(int i = 0; i < nbt.getInt("materialComponents_size"); ++ i)
            materialComponentsIn.add(ItemStack.of(nbt.getCompound("materialComponent"+i)));
        this.materialComponents = materialComponentsIn;
    }

    enum SpellType {

    }
}
