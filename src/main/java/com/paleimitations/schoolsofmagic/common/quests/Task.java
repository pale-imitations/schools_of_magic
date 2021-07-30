package com.paleimitations.schoolsofmagic.common.quests;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.function.Predicate;

public class Task implements INBTSerializable<CompoundNBT> {

    public final EnumTaskType taskType;
    public boolean started = false;
    public boolean completed = false;
    public boolean failed = false;
    public boolean isTimed = false;
    public boolean dead = false;
    public int countdown = 0;
    public ItemStack icon;
    public String name;
    public Predicate<Quest> prerequisite = null;
    public Tuple<Integer, Integer> progress = null;

    public Task(EnumTaskType taskType) {
        this.taskType = taskType;
    }

    public void onCompletion(PlayerEntity player) {
    }

    public void onClaim(PlayerEntity player) {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean check(PlayerEntity player, Object object) {
        return false;
    }

    public boolean tryStart(Quest quest) {
        if(!this.started && quest!=null && this.canStart(quest)) {
            this.started = true;
            return true;
        }
        return false;
    }

    public void checkEvent(PlayerEntity player, Object object) {
        if(this.isOngoing() && this.check(player, object)) {
            this.completed = true;
            this.onCompletion(player);
            player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1f,1f);
            System.out.println("Task Checked");
        }
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public boolean canClaim() {
        return this.completed && !this.dead;
    }

    public void claim(PlayerEntity player) {
        if(this.canClaim()) {
            this.onClaim(player);
            this.dead = true;
        }
    }

    public boolean isOngoing() {
        return this.started && !this.completed && !this.failed;
    }

    public void update(PlayerEntity player) {
        if(this.isTimed && this.started) {
            if(!this.completed) {
                if(this.countdown>0)
                    --this.countdown;
                else {
                    this.failed = true;
                    this.onFailure(player);
                }
            }
        }
    }

    public void onFailure(PlayerEntity player) {
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public boolean canStart(Quest quest) {
        return !started && (this.prerequisite==null || this.prerequisite.test(quest));
    }

    public void setPrerequisite(Predicate<Quest> prerequisite) {
        this.prerequisite = prerequisite;
    }

    public Predicate<Quest> getPrerequisite() {
        return prerequisite;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public boolean isTimed() {
        return isTimed;
    }

    public void setTimed(boolean timed) {
        isTimed = timed;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("Timed", isTimed);
        nbt.putBoolean("Completed", completed);
        nbt.putBoolean("Failed", failed);
        nbt.putBoolean("Started", started);
        nbt.putInt("Countdown", countdown);
        if(name!=null)
            nbt.putString("Name", name);
        if(icon!=null)
            nbt.put("Icon", this.icon.serializeNBT());
        if(progress!=null) {
            nbt.putInt("ProgressMin", progress.getA());
            nbt.putInt("ProgressMax", progress.getB());
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.isTimed = nbt.getBoolean("Timed");
        this.completed = nbt.getBoolean("Completed");
        this.failed = nbt.getBoolean("Failed");
        this.started = nbt.getBoolean("Started");
        this.countdown = nbt.getInt("Countdown");
        if(nbt.contains("Name"))
            this.name = nbt.getString("Name");
        if(nbt.contains("Icon"))
            this.icon = ItemStack.of(nbt.getCompound("Icon"));
        if(nbt.contains("ProgressMin") && nbt.contains("ProgressMax")) {
            progress = new Tuple<>(nbt.getInt("ProgressMin"),nbt.getInt("ProgressMax"));
        }
    }

    public enum EnumTaskType implements IStringSerializable {
        RETRIEVE,
        BATTLE,
        BEFRIEND,
        BUILD,
        BREAK,
        GO,
        POTION_BREW,
        MORTAR,
        BASIN,
        BREED,
        ENCHANT,
        SPELL,
        LIGHT_BRAZIER,
        RITUAL_RECIPE,
        OTHER;

        EnumTaskType() {
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }

}
