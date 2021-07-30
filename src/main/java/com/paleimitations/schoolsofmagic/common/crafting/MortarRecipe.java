package com.paleimitations.schoolsofmagic.common.crafting;

import com.google.gson.JsonObject;
import com.paleimitations.schoolsofmagic.common.registries.BlockRegistry;
import com.paleimitations.schoolsofmagic.common.registries.RecipeRegistry;
import com.paleimitations.schoolsofmagic.common.tileentities.MortarTileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class MortarRecipe implements IRecipe<IInventory> {
    protected final int crush;
    protected final Ingredient ingredient;
    protected final Ingredient ingredientSecondary;
    protected final ItemStack result;
    protected final ItemStack resultSecondary;
    protected final ResourceLocation id;

    public MortarRecipe(ResourceLocation id, int crush, Ingredient ingredient, Ingredient ingredientSecondary, ItemStack result, ItemStack resultSecondary) {
        this.crush = crush;
        this.id = id;
        this.ingredient = ingredient;
        this.ingredientSecondary = ingredientSecondary;
        this.result = result;
        this.resultSecondary = resultSecondary;
    }

    @Override
    public boolean matches(IInventory inv, World world) {
        return (this.ingredient.test(inv.getItem(0)) && this.ingredientSecondary.test(inv.getItem(1)))
                || (this.ingredient.test(inv.getItem(1)) && this.ingredientSecondary.test(inv.getItem(0)))
                || (this.ingredient.test(inv.getItem(1)) && (this.ingredientSecondary.isEmpty() && inv.getItem(0).isEmpty()))
                || (this.ingredient.test(inv.getItem(0)) && (this.ingredientSecondary.isEmpty() && inv.getItem(1).isEmpty()));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(ingredient);
        if(!ingredientSecondary.isEmpty())
            list.add(ingredientSecondary);
        return list;
    }

    @Override
    public ItemStack assemble(IInventory inv) {
        return this.result.copy();
    }

    public ItemStack assembleSecondary(IInventory inv) {
        return this.resultSecondary.copy();
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return result;
    }

    public ItemStack getSecondaryResultItem() {
        return resultSecondary;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeRegistry.MORTAR_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return RecipeRegistry.MORTAR_TYPE;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(BlockRegistry.GRANITE_MORTAR.get());
    }

    public int getCrush() {
        return crush;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MortarRecipe> {

        public MortarRecipe fromJson(ResourceLocation location, JsonObject json) {
            int crush = JSONUtils.getAsInt(json, "crush");
            Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "ingredient"));
            Ingredient ingredientSecondary = JSONUtils.isValidNode(json, "ingredientSecondary") ? Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "ingredientSecondary")) : Ingredient.EMPTY;
            ItemStack itemstack = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
            ItemStack itemstackSecondary = JSONUtils.isValidNode(json, "resultSecondary") ? ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "resultSecondary")) : ItemStack.EMPTY;
            System.out.println("Registered Mortar Recipe:" + location.toString());
            return new MortarRecipe(location, crush, ingredient, ingredientSecondary, itemstack, itemstackSecondary);
        }

        public MortarRecipe fromNetwork(ResourceLocation location, PacketBuffer buffer) {
            int crush = buffer.readInt();
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            Ingredient ingredientSecondary = Ingredient.fromNetwork(buffer);
            ItemStack itemstack = buffer.readItem();
            ItemStack itemstackSecondary = buffer.readItem();
            return new MortarRecipe(location, crush, ingredient, ingredientSecondary, itemstack, itemstackSecondary);
        }

        public void toNetwork(PacketBuffer buffer, MortarRecipe recipe) {
            buffer.writeInt(recipe.crush);
            recipe.ingredient.toNetwork(buffer);
            recipe.ingredientSecondary.toNetwork(buffer);
            buffer.writeItem(recipe.result);
            buffer.writeItem(recipe.resultSecondary);
        }
    }
}
