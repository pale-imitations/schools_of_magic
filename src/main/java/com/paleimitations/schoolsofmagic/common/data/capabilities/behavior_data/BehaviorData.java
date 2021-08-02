package com.paleimitations.schoolsofmagic.common.data.capabilities.behavior_data;

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public class BehaviorData implements IBehaviorData, INBTSerializable<CompoundNBT> {

    public List<BehaviorModifier> behaviorModifiers = Lists.newArrayList();

    public BehaviorData() {
    }

    @Override
    public List<BehaviorModifier> getBehaviorModifiers() {
        return behaviorModifiers;
    }

    @Override
    public boolean hasBehavior(BehaviorModifier.Behavior behavior) {
        for(BehaviorModifier mod : behaviorModifiers) {
            if(mod.behavior == behavior)
                return true;
        }
        return false;
    }

    @Override
    public List<BehaviorModifier> getBehaviorModifiersOf(BehaviorModifier.Behavior behavior) {
        List<BehaviorModifier> list = behaviorModifiers;
        list.removeIf(behave -> behave.behavior!=behavior);
        return list;
    }

    @Override
    public void updateBehaviors() {
        behaviorModifiers.forEach(behaviorModifier -> behaviorModifier.update());
        behaviorModifiers.removeIf(behave -> behave.dead);
    }

    @Override
    public void addBehaviorModifier(BehaviorModifier modifier) {
        if(!behaviorModifiers.contains(modifier))
            behaviorModifiers.add(modifier);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        ListNBT list = new ListNBT();
        for(int i = 0; i < behaviorModifiers.size(); ++i) {
            list.add(i, behaviorModifiers.get(i).serializeNBT());
        }
        nbt.put("behaviorModifiers", list);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        ListNBT list = (ListNBT) nbt.get("behaviorModifiers");
        behaviorModifiers.clear();
        list.forEach(inbt -> behaviorModifiers.add(BehaviorModifier.deserializeNBT((CompoundNBT)inbt)));
    }
}
