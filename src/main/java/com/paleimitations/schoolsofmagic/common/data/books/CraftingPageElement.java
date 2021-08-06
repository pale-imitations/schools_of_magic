package com.paleimitations.schoolsofmagic.common.data.books;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.paleimitations.schoolsofmagic.References;
import it.unimi.dsi.fastutil.objects.Reference2ShortArrayMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.util.List;
import java.util.Random;

public class CraftingPageElement extends PageElement {

    public final ICraftingRecipe recipe;

    public CraftingPageElement(ICraftingRecipe recipe, int x, int y) {
        super(x, y);
        this.recipe = recipe;
    }

    public CraftingPageElement(ICraftingRecipe recipe, int x, int y, int target) {
        super(x, y, target);
        this.recipe = recipe;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawElement(MatrixStack matrixStack, float mouseX, float mouseY, int x, int y, float zLevel, boolean isGUI, int subpage, int light) {
        this.drawElement(matrixStack, mouseX, mouseY, x, y, zLevel, isGUI, subpage, light, null);
    }

    @Override
    public void drawElement(MatrixStack matrixStack, float mouseX, float mouseY, int xIn, int yIn, float zLevel, boolean isGUI, int subpage, int light, IRenderTypeBuffer buffer) {
        Minecraft.getInstance().getTextureManager().bind(new ResourceLocation(References.MODID, "textures/gui/books/crafting_recipe.png"));
        //Random rand = Minecraft.getInstance().player.getRandom();
        PlayerEntity player =  Minecraft.getInstance().player;
        this.drawTexturedModalRect(matrixStack, x+xIn, y+yIn, 0, 0, 54, 76, zLevel, light);
        for(int i = 0; i < recipe.getIngredients().size(); ++i) {
            Ingredient ing = recipe.getIngredients().get(i);
            if(ing!=null && !ing.isEmpty()) this.drawItemStack(matrixStack, ing.getItems()[player.tickCount / 60 % ing.getItems().length], x+xIn+1+((i%3)*18), y+yIn+1+((i/3)*18), isGUI, light, buffer);
        }
        this.drawItemStack(matrixStack, recipe.getResultItem(), x+xIn+19, y+yIn+59, isGUI, light, buffer);
        /*if(!inputs.isEmpty() && !inputs.get(0).isEmpty())this.drawItemStack(matrixStack, inputs.get(0), x+xIn+1, y+yIn+1, isGUI);
        if(inputs.size()>1 && !inputs.get(1).isEmpty())this.drawItemStack(matrixStack, inputs.get(1), x+xIn+19, y+yIn+1, isGUI);
        if(inputs.size()>2 && !inputs.get(2).isEmpty())this.drawItemStack(matrixStack, inputs.get(2), x+xIn+37, y+yIn+1, isGUI);
        if(inputs.size()>3 && !inputs.get(3).isEmpty())this.drawItemStack(matrixStack, inputs.get(3), x+xIn+1, y+yIn+19, isGUI);
        if(inputs.size()>4 && !inputs.get(4).isEmpty())this.drawItemStack(matrixStack, inputs.get(4), x+xIn+19, y+yIn+19, isGUI);
        if(inputs.size()>5 && !inputs.get(5).isEmpty())this.drawItemStack(matrixStack, inputs.get(5), x+xIn+37, y+yIn+19, isGUI);
        if(inputs.size()>6 && !inputs.get(6).isEmpty())this.drawItemStack(matrixStack, inputs.get(6), x+xIn+1, y+yIn+37, isGUI);
        if(inputs.size()>7 && !inputs.get(7).isEmpty())this.drawItemStack(matrixStack, inputs.get(7), x+xIn+19, y+yIn+37, isGUI);
        if(inputs.size()>8 && !inputs.get(8).isEmpty())this.drawItemStack(matrixStack, inputs.get(8), x+xIn+37, y+yIn+37, isGUI);
        this.drawItemStack(matrixStack, output, x+xIn+19, y+yIn+59, isGUI);*/
    }

    /*public final List<ItemStack> inputs;
    public final ItemStack output;

    public CraftingPageElement(List<ItemStack> inputs, ItemStack output, int x, int y) {
        super(x, y);
        this.inputs = inputs;
        this.output = output;
    }

    public CraftingPageElement(List<ItemStack> inputs, ItemStack output, int x, int y, int target) {
        super(x, y, target);
        this.inputs = inputs;
        this.output = output;
    }

    @Override
    public void drawElement(MatrixStack matrixStack, float mouseX, float mouseY, int xIn, int yIn, float zLevel, boolean isGUI, int subpage, int light) {
        Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(IRecipeType.CRAFTING);
        Minecraft.getInstance().getTextureManager().bind(new ResourceLocation(References.MODID, "textures/gui/books/crafting_recipe.png"));
        this.drawTexturedModalRect(matrixStack, x+xIn, y+yIn, 0, 0, 54, 76, zLevel, light);
        if(!inputs.isEmpty() && !inputs.get(0).isEmpty())this.drawItemStack(matrixStack, inputs.get(0), x+xIn+1, y+yIn+1, isGUI);
        if(inputs.size()>1 && !inputs.get(1).isEmpty())this.drawItemStack(matrixStack, inputs.get(1), x+xIn+19, y+yIn+1, isGUI);
        if(inputs.size()>2 && !inputs.get(2).isEmpty())this.drawItemStack(matrixStack, inputs.get(2), x+xIn+37, y+yIn+1, isGUI);
        if(inputs.size()>3 && !inputs.get(3).isEmpty())this.drawItemStack(matrixStack, inputs.get(3), x+xIn+1, y+yIn+19, isGUI);
        if(inputs.size()>4 && !inputs.get(4).isEmpty())this.drawItemStack(matrixStack, inputs.get(4), x+xIn+19, y+yIn+19, isGUI);
        if(inputs.size()>5 && !inputs.get(5).isEmpty())this.drawItemStack(matrixStack, inputs.get(5), x+xIn+37, y+yIn+19, isGUI);
        if(inputs.size()>6 && !inputs.get(6).isEmpty())this.drawItemStack(matrixStack, inputs.get(6), x+xIn+1, y+yIn+37, isGUI);
        if(inputs.size()>7 && !inputs.get(7).isEmpty())this.drawItemStack(matrixStack, inputs.get(7), x+xIn+19, y+yIn+37, isGUI);
        if(inputs.size()>8 && !inputs.get(8).isEmpty())this.drawItemStack(matrixStack, inputs.get(8), x+xIn+37, y+yIn+37, isGUI);
        this.drawItemStack(matrixStack, output, x+xIn+19, y+yIn+59, isGUI);
    }*/
}
