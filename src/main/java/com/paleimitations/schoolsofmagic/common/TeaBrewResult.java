package com.paleimitations.schoolsofmagic.common;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.common.data.TeaUtils;
import com.paleimitations.schoolsofmagic.common.registries.TeaIngredientRegistry;
import com.paleimitations.schoolsofmagic.common.registries.TeaRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;

import java.awt.*;
import java.util.List;

public class TeaBrewResult {
    public boolean successful;
    public TeaRegistry.Tea tea1;
    public TeaRegistry.Tea tea2;
    public TeaRegistry.Tea tea3;
    public List<EffectInstance> tier1Pool, tier2Pool, tier3Pool;
    public int color;
    public String overrideName;
    public boolean isMilk = false;

    public TeaBrewResult(String tea1, String tea2, String tea3, boolean isMilk) {
        this(TeaRegistry.getTea(tea1), TeaRegistry.getTea(tea2), TeaRegistry.getTea(tea3), isMilk);
    }

    public TeaBrewResult(TeaRegistry.Tea tea1, TeaRegistry.Tea tea2, TeaRegistry.Tea tea3, boolean isMilk) {
        this.tea1 = tea1;
        this.tea2 = tea2;
        this.tea3 = tea3;
        this.isMilk = isMilk;
        refresh();
    }

    public void refresh() {
        this.successful = this.tea1!=null && this.tea2!=null && this.tea3!=null;
        this.tier1Pool = Lists.newArrayList();
        this.tier2Pool = Lists.newArrayList();
        this.tier3Pool = Lists.newArrayList();
        if(successful) {
            this.populatePools();
            this.color = TeaUtils.mixColors(this.isMilk, this.tea1, this.tea2, this.tea3);
        }
        else {
            this.color = this.isMilk? Color.WHITE.getRGB() : 6854629;
        }
    }

    public TeaBrewResult(CompoundNBT nbt) {
        this.deserialize(nbt);
        refresh();
    }

    public CompoundNBT serialize() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("tea1", tea1.name);
        nbt.putString("tea2", tea2.name);
        nbt.putString("tea3", tea3.name);
        nbt.putBoolean("successful", successful);
        nbt.putBoolean("isMilk", isMilk);
        nbt.putInt("color", color);
        if(overrideName!=null && !overrideName.isEmpty())
            nbt.putString("overrideName", overrideName);
        return  nbt;
    }

    public void deserialize(CompoundNBT nbt) {
        this.tea1 = TeaRegistry.getTea(nbt.getString("tea1"));
        this.tea2 = TeaRegistry.getTea(nbt.getString("tea2"));
        this.tea3 = TeaRegistry.getTea(nbt.getString("tea3"));
        this.successful = nbt.getBoolean("successful");
        this.isMilk = nbt.getBoolean("isMilk");
        this.color = nbt.getInt("color");
        if(nbt.contains("overrideName")) {
            this.overrideName = nbt.getString("overrideName");
        }
    }

    public TeaBrewResult setOverrideName(String name) {
        this.overrideName = name;
        return this;
    }

    public boolean hasCustomName() {
        return !this.overrideName.isEmpty();
    }

    public String getOverrideName() {
        return overrideName;
    }

    public int getColor() {
        return color;
    }

    public void populatePools() {
        this.tea1.effects.forEach(effectInstance -> {
            if(effectInstance!=null) {
                int a = 1;
                if (this.tea2.effects.indexOf(effectInstance) >= 0)
                    a++;
                if (this.tea3.effects.indexOf(effectInstance) >= 0)
                    a++;
                switch (a) {
                    case 1:
                        tier1Pool.add(effectInstance);
                        break;
                    case 2:
                        tier2Pool.add(effectInstance);
                        break;
                    case 3:
                        tier3Pool.add(effectInstance);
                        break;
                }
            }
        });
        this.tea2.effects.forEach(effectInstance -> {
            if(effectInstance!=null) {
                int a = 1;
                if (this.tea1.effects.indexOf(effectInstance) >= 0)
                    a++;
                if (this.tea3.effects.indexOf(effectInstance) >= 0)
                    a++;
                switch (a) {
                    case 1:
                        if (!tier1Pool.contains(effectInstance)) tier1Pool.add(effectInstance);
                        break;
                    case 2:
                        if (!tier2Pool.contains(effectInstance)) tier2Pool.add(effectInstance);
                        break;
                    case 3:
                        if (!tier3Pool.contains(effectInstance)) tier3Pool.add(effectInstance);
                        break;
                }
            }
        });
        this.tea3.effects.forEach(effectInstance -> {
            if(effectInstance!=null) {
                int a = 1;
                if (this.tea1.effects.indexOf(effectInstance) >= 0)
                    a++;
                if (this.tea2.effects.indexOf(effectInstance) >= 0)
                    a++;
                switch (a) {
                    case 1:
                        if (!tier1Pool.contains(effectInstance)) tier1Pool.add(effectInstance);
                        break;
                    case 2:
                        if (!tier2Pool.contains(effectInstance)) tier2Pool.add(effectInstance);
                        break;
                    case 3:
                        if (!tier3Pool.contains(effectInstance)) tier3Pool.add(effectInstance);
                        break;
                }
            }
        });
    }
}
