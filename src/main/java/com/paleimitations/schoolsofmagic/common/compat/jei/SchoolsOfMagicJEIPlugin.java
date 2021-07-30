package com.paleimitations.schoolsofmagic.common.compat.jei;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.registries.RecipeRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.stream.Collectors;

@JeiPlugin
public class SchoolsOfMagicJEIPlugin implements IModPlugin {

    private static final ResourceLocation PLUGIN_ID = new ResourceLocation(References.MODID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        registration.addRecipes(getRecipes(manager, RecipeRegistry.MORTAR_TYPE), MortarRecipeCategory.ID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new MortarRecipeCategory(helper));
    }

    private static Collection<?> getRecipes(RecipeManager manager, IRecipeType<?> type) {
        return manager.getRecipes().parallelStream().filter(iRecipe -> iRecipe.getType()==type).collect(Collectors.toList());
    }
}
