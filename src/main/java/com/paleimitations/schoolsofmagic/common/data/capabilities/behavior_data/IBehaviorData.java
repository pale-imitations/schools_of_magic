package com.paleimitations.schoolsofmagic.common.data.capabilities.behavior_data;

import net.minecraft.nbt.CompoundNBT;

import java.util.List;

public interface IBehaviorData {

    List<BehaviorModifier> getBehaviorModifiers();
    boolean hasBehavior(BehaviorModifier.Behavior behavior);
    List<BehaviorModifier> getBehaviorModifiersOf(BehaviorModifier.Behavior behavior);
    void updateBehaviors();
    void addBehaviorModifier(BehaviorModifier modifier);

    CompoundNBT serializeNBT();
    void deserializeNBT(CompoundNBT nbt);

}
