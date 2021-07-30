package com.paleimitations.schoolsofmagic.common.compat.jei;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.crafting.MortarRecipe;
import com.paleimitations.schoolsofmagic.common.registries.BlockRegistry;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class MortarRecipeCategory implements IRecipeCategory<MortarRecipe> {

    public static final ResourceLocation ID = new ResourceLocation(References.MODID, ".mortar");
    protected static final ResourceLocation TEXTURES = new ResourceLocation(References.MODID +":textures/gui/mortar_jei.png");
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableStatic cooldown_icon;
    private final IDrawableAnimated cooldown;

    public MortarRecipeCategory(IGuiHelper helper) {
        this.cooldown_icon = helper.createDrawable(TEXTURES, 108, 0, 13, 42);
        this.cooldown = helper.createAnimatedDrawable(cooldown_icon, 100, IDrawableAnimated.StartDirection.TOP, false);
        this.background = helper.createDrawable(TEXTURES, 0, 0, 99, 79);
        this.icon = helper.createDrawableIngredient(new ItemStack(BlockRegistry.ANDESITE_MORTAR.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }

    @Override
    public Class<? extends MortarRecipe> getRecipeClass() {
        return MortarRecipe.class;
    }

    @Override
    public String getTitle() {
        return new TranslationTextComponent("category."+References.MODID+".mortar_recipe").getString();
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(MortarRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        this.cooldown.draw(matrixStack, 56, 25);
    }

    @Override
    public void setIngredients(MortarRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        List<ItemStack> list = Lists.newArrayList();
        if(!recipe.getResultItem().isEmpty())
            list.add(recipe.getResultItem());
        if(!recipe.getSecondaryResultItem().isEmpty())
            list.add(recipe.getSecondaryResultItem());
        ingredients.setOutputs(VanillaTypes.ITEM, list);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MortarRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        stacks.init(0, true, 7, 46);
        stacks.init(1, true, 27, 46);
        stacks.init(2, false, 77, 26);
        stacks.init(3, false, 77, 46);
        stacks.set(ingredients);
    }
}
