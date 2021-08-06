package com.paleimitations.schoolsofmagic.common.registries;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import java.util.Arrays;
import java.util.List;

public class TeaIngredientRegistry {

    public static List<TeaIngredient> INGREDIENTS = Lists.newArrayList();

    public static void register() {
        INGREDIENTS.add(new TeaIngredient(TeaRegistry.getTea("allium"), Ingredient.of(ItemRegistry.CRUSHED_ALLIUM.get())));
        INGREDIENTS.add(new TeaIngredient(TeaRegistry.getTea("blue_orchid"), Ingredient.of(ItemRegistry.CRUSHED_BLUE_ORCHID.get())));
        INGREDIENTS.add(new TeaIngredient(TeaRegistry.getTea("cornflower"), Ingredient.of(ItemRegistry.CRUSHED_CORNFLOWER.get())));
        INGREDIENTS.add(new TeaIngredient(TeaRegistry.getTea("dandelion"), Ingredient.of(ItemRegistry.CRUSHED_DANDELION.get())));
        INGREDIENTS.add(new TeaIngredient(TeaRegistry.getTea("lily_of_the_valley"), Ingredient.of(ItemRegistry.CRUSHED_LILY_OF_THE_VALLY.get())));
        INGREDIENTS.add(new TeaIngredient(TeaRegistry.getTea("poppy_seed"), Ingredient.of(ItemRegistry.POPPY_SEEDS.get())));
        INGREDIENTS.add(new TeaIngredient(TeaRegistry.getTea("cocoa_bean"), Ingredient.of(Items.COCOA_BEANS)));
        INGREDIENTS.add(new TeaIngredient(TeaRegistry.getTea("sugar"), Ingredient.of(Items.SUGAR)));
    }

    public static TeaIngredient getIngredient(ItemStack stack) {
        for(TeaIngredient ingredient : INGREDIENTS) {
            if (ingredient.isIngredient(stack))
                return ingredient;
        }
        return null;
    }

    public static TeaIngredient getIngredient(String name) {
        for(TeaIngredient ingredient : INGREDIENTS) {
            if (ingredient.tea.name.equalsIgnoreCase(name))
                return ingredient;
        }
        return null;
    }

    public static class TeaIngredient {
        public final TeaRegistry.Tea tea;
        public final Ingredient ingredient;

        public TeaIngredient(TeaRegistry.Tea tea, Ingredient ingredient) {
            this.tea = tea;
            this.ingredient = ingredient;
        }

        public Ingredient getIngredient() {
            return ingredient;
        }

        public List<EffectInstance> getEffects() {
            return tea.effects;
        }

        public boolean isIngredient(ItemStack stack) {
            return ingredient.test(stack);
        }
    }
}
