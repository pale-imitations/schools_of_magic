package com.paleimitations.schoolsofmagic.common.quests.quests;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.quests.Quest;
import com.paleimitations.schoolsofmagic.common.quests.Task;
import com.paleimitations.schoolsofmagic.common.registries.BlockRegistry;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;

import java.util.List;

public class QuestAdvancedArcana extends Quest {

    public QuestAdvancedArcana() {
        super(new ResourceLocation(References.MODID, "advanced_arcana"));
        this.initialize();
        System.out.println("Created Advanced Arcana Quest");
    }

    public QuestAdvancedArcana(ResourceLocation location) {
        this();
    }

    public QuestAdvancedArcana(CompoundNBT nbt) {
        super(nbt);
    }

    @Override
    public void initialize() {
        this.tasks.clear();
        /*Task task1 = new Task(Task.EnumTaskType.OTHER) {
            @Override
            public boolean check(PlayerEntity player, Object object) {
                return true;
            }
        };
        task1.setIcon(new ItemStack(Items.SPIDER_EYE));
        task1.setTimed(true);
        task1.setCountdown(14400);
        task1.setName("breed_frogs");
        this.tasks.add(task1);
        Task task2 = new Task(Task.EnumTaskType.BASIN) {
            @Override
            public boolean check(PlayerEntity player, Object object) {
                return object == RecipeRegistry.getCatalystRecipe(new ItemStack(ItemRegistry.ingot, 1, EnumMetal.BRASS.getIndex()));
            }
        };
        task2.setIcon(new ItemStack(ItemRegistry.ingot, 1, EnumMetal.BRASS.getIndex()));
        task2.setName("create_brass");
        this.tasks.add(task2);
        Task task3 = new Task(Task.EnumTaskType.RITUAL_RECIPE) {
            @Override
            public boolean check(EntityPlayer player, Object object) {
                return object instanceof RecipeRitualCrafting && ((RecipeRitualCrafting)object).getOutput().isItemEqual(new ItemStack(BlockRegistry.divination_crystal));
            }
        };
        task3.setName("create_crystal_ball");
        task3.setPrerequisite(quest -> (quest.tasks.get(1).completed));
        task3.setIcon(new ItemStack(BlockRegistry.divination_crystal));
        this.tasks.add(task3);
        Task task4 = new Task(Task.EnumTaskType.SPELL) {
            List<String> spells = Lists.newArrayList();

            @Override
            public boolean check(PlayerEntity player, Object object) {
                System.out.println("Spell checked");
                if(object instanceof Spell) {
                    Spell spell = (Spell) object;
                    if(!spells.contains(spell.getName()))
                        spells.add(spell.getName());
                    progress = new Tuple<>(spells.size(), 12);
                }
                return spells.size() >= 12;
            }

            @Override
            public CompoundNBT serializeNBT() {
                CompoundNBT nbt = super.serializeNBT();
                nbt.setInteger("num_spells", spells.size());
                for(int i = 0; i < spells.size(); ++i){
                    nbt.setString("spell"+i, spells.get(i));
                }
                return nbt;
            }

            @Override
            public void deserializeNBT(CompoundNBT nbt) {
                super.deserializeNBT(nbt);
                spells.clear();
                for(int i = 0; i<nbt.getInteger("num_spells"); ++i){
                    spells.add(nbt.getString("spell"+i));
                }
            }
        };
        task4.progress = new Tuple<>(0, 12);
        task4.setPrerequisite(quest -> (quest.tasks.get(2).completed));
        ItemStack wand = new ItemStack(ItemRegistry.wand_advanced);
        if (wand.hasCapability(CapabilityWandData.WAND_DATA_CAPABILITY, null)) {
            IWandData data = wand.getCapability(CapabilityWandData.WAND_DATA_CAPABILITY, null);
            data.setCoreType(IWandData.EnumCoreType.ASH);
            data.setHandleType(IWandData.EnumHandleType.SILVER);
            data.setGemType(IWandData.EnumGemType.AMETHYST);
        }
        task4.setIcon(wand);
        task4.setName("spellcast12");
        this.tasks.add(task4);
        this.rewards.add(ItemBookBase.initializeBook(new ItemStack(ItemRegistry.advanced_spellbook)));
        ItemStack stack = new ItemStack(ItemRegistry.spell_modifier_scroll);
        if(stack.hasCapability(CapabilitySpellModifier.SPELL_MODIFIER_CAPABILITY, null)) {
            ISpellModifier data = stack.getCapability(CapabilitySpellModifier.SPELL_MODIFIER_CAPABILITY, null);
            data.setSpellModifier(Spell.EnumSpellModifier.POWER5, Spell.EnumSpellModifier.POWER5.defaultObj);
        }
        this.rewards.add(stack);
        this.icon = new ItemStack(ItemRegistry.advanced_spellbook);*/
    }
}
