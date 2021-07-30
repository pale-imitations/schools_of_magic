package com.paleimitations.schoolsofmagic.common.registries;

import com.paleimitations.schoolsofmagic.common.crafting.MortarRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;

public class RecipeRegistry {
    public static final RegistryObject<IRecipeSerializer<MortarRecipe>> MORTAR_SERIALIZER = Registry.RECIPE_SERIALIZERS.register("mortar", () -> new MortarRecipe.Serializer());
    public static final IRecipeType<MortarRecipe> MORTAR_TYPE = IRecipeType.register("mortar");

    public static void register() {
    }

}
