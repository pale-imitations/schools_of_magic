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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;

import java.util.List;

public class QuestIntermediateArcana extends Quest {

    public QuestIntermediateArcana() {
        super(new ResourceLocation(References.MODID, "intermediate_arcana"));
        this.initialize();
        System.out.println("Created Intermediate Arcana Quest");
    }

    public QuestIntermediateArcana(ResourceLocation location) {
        this();
    }

    public QuestIntermediateArcana(CompoundNBT nbt) {
        super(nbt);
    }

    @Override
    public void initialize() {
        this.tasks.clear();
        /*Task task1 = new Task(Task.EnumTaskType.MORTAR) {
            @Override
            public boolean check(PlayerEntity player, Object object) {
                return object instanceof RecipeMortNPest;
            }
        };
        task1.setIcon(new ItemStack(BlockRegistry.mortnpest));
        this.tasks.add(task1);
        task1.setName("mortnpest");
        Task task2 = new Task(Task.EnumTaskType.LIGHT_BRAZIER) {
            @Override
            public boolean check(PlayerEntity player, Object object) {
                return object == BlockRegistry.brazier;
            }
        };
        task2.setName("brazier");
        task2.setPrerequisite(quest -> (quest.tasks.get(0).completed));
        task2.setIcon(new ItemStack(BlockRegistry.brazier));
        this.tasks.add(task2);
        Task task3 = new Task(Task.EnumTaskType.SPELL) {
            List<String> spells = Lists.newArrayList();

            @Override
            public boolean check(EntityPlayer player, Object object) {
                System.out.println("Spell checked");
                if(object instanceof Spell) {
                    Spell spell = (Spell) object;
                    if(!spells.contains(spell.getName()))
                        spells.add(spell.getName());
                    progress = new Tuple<>(spells.size(), 5);
                }
                return spells.size() > 4;
            }

            @Override
            public CompoundNBT serializeNBT() {
                CompoundNBT nbt = super.serializeNBT();
                nbt.putInt("num_spells", spells.size());
                for(int i = 0; i < spells.size(); ++i){
                    nbt.putString("spell"+i, spells.get(i));
                }
                return nbt;
            }

            @Override
            public void deserializeNBT(CompoundNBT nbt) {
                super.deserializeNBT(nbt);
                spells.clear();
                for(int i = 0; i<nbt.getInt("num_spells"); ++i){
                    spells.add(nbt.getString("spell"+i));
                }
            }
        };
        task3.progress = new Tuple<>(0, 5);
        task3.setPrerequisite(quest -> (quest.tasks.get(1).completed));
        task3.setIcon(new ItemStack(ItemRegistry.APPRENTICE_WAND_1.get()));
        task3.setName("spellcast5");
        this.tasks.add(task3);
        this.rewards.add(ItemBookBase.initializeBook(new ItemStack(ItemRegistry.INTERMEDIATE_ARCANA.get())));
        ItemStack stack = new ItemStack(ItemRegistry.spell_modifier_scroll);
        if(stack.hasCapability(CapabilitySpellModifier.SPELL_MODIFIER_CAPABILITY, null)) {
            ISpellModifier data = stack.getCapability(CapabilitySpellModifier.SPELL_MODIFIER_CAPABILITY, null);
            data.setSpellModifier(Spell.EnumSpellModifier.COST3, Spell.EnumSpellModifier.COST3.defaultObj);
        }
        this.rewards.add(stack);
        ItemStack stack2 = new ItemStack(ItemRegistry.QUEST_NOTE.get());
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("quest", References.MODID+":advanced_arcana");
        stack2.setTag(nbt);
        this.rewards.add(stack2);
        this.icon = new ItemStack(ItemRegistry.INTERMEDIATE_ARCANA.get());*/
    }
}
