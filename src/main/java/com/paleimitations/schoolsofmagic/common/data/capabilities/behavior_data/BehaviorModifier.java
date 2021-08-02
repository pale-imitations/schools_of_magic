package com.paleimitations.schoolsofmagic.common.data.capabilities.behavior_data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.UUID;

public class BehaviorModifier {

    public Behavior behavior;
    public UUID target;
    public UUID cause;
    public int timer;
    public boolean useTimer;
    public boolean dead = false;

    public BehaviorModifier(Behavior behavior, UUID target, UUID cause, boolean useTimer, int timer) {
        this.behavior = behavior;
        this.target = target;
        this.cause = cause;
        this.useTimer = useTimer;
        this.timer = timer;
    }

    public void update() {
        if(useTimer && !dead)
            --timer;
        if(timer==0)
            dead=true;
    }

    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("behavior", behavior.getSerializedName());
        if(target!=null)
            nbt.putUUID("target", target);
        if(cause!=null)
            nbt.putUUID("cause", cause);
        nbt.putInt("timer", timer);
        nbt.putBoolean("useTimer", useTimer);
        return nbt;
    }

    public static BehaviorModifier deserializeNBT(CompoundNBT nbt) {
        return new BehaviorModifier(Behavior.fromName(nbt.getString("behavior")), nbt.hasUUID("target")? nbt.getUUID("target") : null,
                nbt.hasUUID("cause")? nbt.getUUID("cause") : null, nbt.getBoolean("useTimer"), nbt.getInt("timer"));
    }

    public enum Behavior implements IStringSerializable {
        ANGER, FEAR, INFATUATION, LOYALTY, PASSIVITY, SLEEPINESS;

        @Override
        public String getSerializedName() {
            return name().toLowerCase();
        }

        public static Behavior fromName(String name) {
            for(Behavior be : values()) {
                if (be.getSerializedName().equalsIgnoreCase(name))
                    return be;
            }
            return null;
        }
    }
}
