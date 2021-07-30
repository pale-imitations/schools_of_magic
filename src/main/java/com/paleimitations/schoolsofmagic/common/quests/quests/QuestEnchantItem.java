package com.paleimitations.schoolsofmagic.common.quests.quests;

import com.mojang.brigadier.CommandDispatcher;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.items.BookBaseItem;
import com.paleimitations.schoolsofmagic.common.quests.Quest;
import com.paleimitations.schoolsofmagic.common.quests.Task;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.ComponentArgument;
import net.minecraft.command.arguments.ObjectiveCriteriaArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;

public class QuestEnchantItem extends Quest {

    public QuestEnchantItem() {
        super(new ResourceLocation(References.MODID, "enchant_item"));
        this.initialize();
        //System.out.println("Created Enchant Item Quest");
    }

    public QuestEnchantItem(ResourceLocation location) {
        this();
    }

    public QuestEnchantItem(CompoundNBT nbt) {
        super(nbt);
    }

    @Override
    public void initialize() {
        System.out.println("Init Enchant Item Quest");
        this.tasks.clear();
        Task task = new Task(Task.EnumTaskType.ENCHANT) {
            private int score = -1;

            @Override
            public void onCompletion(PlayerEntity player) {
                QuestEnchantItem.this.markDirty();
            }

            @Override
            public boolean check(PlayerEntity player, Object object) {
                int a = -1;
                if(player instanceof ServerPlayerEntity) {
                    a = ((ServerPlayerEntity) player).getStats().getValue(Stats.CUSTOM.get(Stats.ENCHANT_ITEM));
                }
                else if(player instanceof ClientPlayerEntity) {
                    a = ((ClientPlayerEntity) player).getStats().getValue(Stats.CUSTOM.get(Stats.ENCHANT_ITEM));
                }

                boolean flag = score > -1 && a > score;
                score = a;
                return flag;
            }

            @Override
            public void update(PlayerEntity player) {
                if(player.tickCount%20==0) {
                    this.checkEvent(player, null);
                }
                super.update(player);
            }

            @Override
            public CompoundNBT serializeNBT() {
                CompoundNBT nbt = super.serializeNBT();
                nbt.putInt("Score", this.score);
                return nbt;
            }

            @Override
            public void deserializeNBT(CompoundNBT nbt) {
                super.deserializeNBT(nbt);
                this.score = nbt.getInt("Score");
            }
        };
        task.setStarted(true);
        this.tasks.add(task);
        this.rewards.add(BookBaseItem.initialize(new ItemStack(ItemRegistry.BASIC_ARCANA.get())));
        ItemStack stack = new ItemStack(ItemRegistry.QUEST_NOTE.get());
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("quest", References.MODID+":intermediate_arcana");
        stack.setTag(nbt);
        //this.rewards.add(stack);
        this.icon = new ItemStack(Blocks.ENCHANTING_TABLE);
    }
}
