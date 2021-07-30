package com.paleimitations.schoolsofmagic.common.quests;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.common.network.PacketHandler;
import com.paleimitations.schoolsofmagic.common.network.UpdateQuestPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;
import java.util.UUID;

public class Quest implements INBTSerializable<CompoundNBT> {

    private ResourceLocation resourceLocation;
    public List<Task> tasks = Lists.newArrayList();
    public UUID questGiver;
    public boolean completed = false;
    public boolean failed = false;
    public boolean dead = false;
    public boolean isDirty = false;
    public ItemStack icon = null;
    public List<ItemStack> rewards = Lists.newArrayList();

    public Quest(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public Quest(CompoundNBT nbt) {
        this.deserializeNBT(nbt);
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public UUID getQuestGiver() {
        return questGiver;
    }

    public void setQuestGiver(UUID questGiver) {
        this.questGiver = questGiver;
    }

    public void onClaim(PlayerEntity player) {
        if(!player.level.isClientSide) {
            for(ItemStack stack : rewards)
                player.level.addFreshEntity(new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), stack));
        }
    }

    public void markDirty() {
        this.isDirty = true;
    }

    public boolean canClaim() {
        return this.completed && !this.dead;
    }

    public void claim(PlayerEntity player) {
        if(this.canClaim()) {
            System.out.println("Quest Claim");
            this.onClaim(player);
            for(Task task : tasks) {
                if(!task.dead) {
                    task.claim(player);
                }
            }
            this.dead = true;
        }
    }

    public boolean isQuestGiver(Entity entity) {
        return entity.getUUID().equals(questGiver);
    }

    public void update(PlayerEntity player) {
        if(this.isDirty && player instanceof ServerPlayerEntity) {
            PacketHandler.sendToTracking(new UpdateQuestPacket(player.getId(), this.getQuestGiver(), this.serializeNBT()), (ServerPlayerEntity)player);
        }
        if(!this.completed && !this.dead && !this.failed) {
            boolean flag = true;
            boolean flag1 = false;
            for (Task task : tasks) {
                task.update(player);
                if (!task.completed)
                    flag = false;
                if (task.failed)
                    flag1 = true;
            }
            this.completed = flag;
            if(completed)
                this.onCompletion(player);
            this.failed = flag1;
            if(failed)
                this.onFailure(player);
        }
    }

    public void onCompletion(PlayerEntity player) {
        System.out.println("Quest Completed");
    }

    public void onFailure(PlayerEntity player) {
    }

    public void initialize() {
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("resourceLocation", this.resourceLocation.toString());
        for(int i = 0; i < tasks.size(); ++ i) {
            nbt.put("Task"+i,tasks.get(i).serializeNBT());
        }
        nbt.putBoolean("Completed", this.completed);
        nbt.putBoolean("Failed", this.failed);
        nbt.putBoolean("Dead", this.dead);
        if(icon!=null)
            nbt.put("Icon", this.icon.serializeNBT());
        if(questGiver!=null)
            nbt.putUUID("QuestGiver", questGiver);
        nbt.putInt("RewardsSize", rewards.size());
        for(int i = 0; i < rewards.size(); ++i) {
            nbt.put("Reward"+i, this.rewards.get(i).serializeNBT());
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.initialize();
        this.resourceLocation = new ResourceLocation(nbt.getString("resourceLocation"));
        for(int i = 0; i < tasks.size(); ++ i) {
            tasks.get(i).deserializeNBT(nbt.getCompound("Task"+i));
        }
        this.completed = nbt.getBoolean("Completed");
        this.failed = nbt.getBoolean("Failed");
        this.dead = nbt.getBoolean("Dead");
        if(nbt.hasUUID("QuestGiver"))
            this.questGiver = nbt.getUUID("QuestGiver");
        if(nbt.contains("Icon"))
            this.icon = ItemStack.of(nbt.getCompound("Icon"));
        this.rewards.clear();
        for(int i = 0; i < nbt.getInt("RewardsSize"); ++i) {
            this.rewards.add(ItemStack.of(nbt.getCompound("Reward"+i)));
        }
    }
}
