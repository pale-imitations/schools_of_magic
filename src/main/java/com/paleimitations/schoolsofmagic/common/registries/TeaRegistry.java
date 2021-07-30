package com.paleimitations.schoolsofmagic.common.registries;

import com.google.common.collect.Lists;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import java.util.Arrays;
import java.util.List;

public class TeaRegistry {

    public static List<Tea> TEAS = Lists.newArrayList();

    public static void register() {
        TEAS.add(new Tea("allium", 8550019, new EffectInstance(Effects.DIG_SPEED, 600), new EffectInstance(Effects.FIRE_RESISTANCE, 600)));
        TEAS.add(new Tea("blue_orchid", 7508378, new EffectInstance(Effects.CONFUSION, 400), new EffectInstance(Effects.SATURATION)));
        TEAS.add(new Tea("cornflower", 6197382, new EffectInstance(Effects.HUNGER, 400), new EffectInstance(Effects.JUMP, 600)));
        TEAS.add(new Tea("dandelion", 13676379, new EffectInstance(Effects.DIG_SPEED, 600), new EffectInstance(Effects.SATURATION)));
        TEAS.add(new Tea("lily_of_the_valley", 12567946, new EffectInstance(Effects.FIRE_RESISTANCE, 600), new EffectInstance(Effects.POISON, 400)));
        TEAS.add(new Tea("poppy_seed", 10122850, new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 400), new EffectInstance(Effects.NIGHT_VISION, 600)));
        TEAS.add(new Tea("cocoa_bean", 6440734, new EffectInstance(Effects.MOVEMENT_SPEED, 600), new EffectInstance(Effects.NIGHT_VISION, 600)));
        TEAS.add(new Tea("sugar", -1, new EffectInstance(Effects.MOVEMENT_SPEED, 600), new EffectInstance(Effects.DIG_SPEED, 600)));
    }

    public static Tea getTea(String name) {
        if(name==null)
            return null;
        for(Tea tea : TEAS) {
            if (tea.name.equalsIgnoreCase(name))
                return tea;
        }
        return null;
    }

    public static class Tea {
        public final String name;
        public final int color;
        public final List<EffectInstance> effects = Lists.newArrayList();

        public Tea(String name, int color, EffectInstance... effects) {
            this.name = name;
            this.color = color;
            this.effects.addAll(Arrays.asList(effects));
        }

        public List<EffectInstance> getEffects() {
            return effects;
        }
    }
}
