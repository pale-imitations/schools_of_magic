package com.paleimitations.schoolsofmagic.common.data.books;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.paleimitations.schoolsofmagic.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

public class CraftingResourceLocationPageElement extends PageElement {

    public final ResourceLocation recipeLocation;

    public CraftingResourceLocationPageElement(ResourceLocation recipeLocation, int x, int y) {
        super(x, y);
        this.recipeLocation = recipeLocation;
    }

    public CraftingResourceLocationPageElement(ResourceLocation recipeLocation, int x, int y, int target) {
        super(x, y, target);
        this.recipeLocation = recipeLocation;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawElement(MatrixStack matrixStack, float mouseX, float mouseY, int x, int y, float zLevel, boolean isGUI, int subpage, int light) {
        this.drawElement(matrixStack, mouseX, mouseY, x, y, zLevel, isGUI, subpage, light, null);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawElement(MatrixStack matrixStack, float mouseX, float mouseY, int xIn, int yIn, float zLevel, boolean isGUI, int subpage, int light, IRenderTypeBuffer buffer) {

        Optional<? extends IRecipe<?>> optionalIRecipe = Minecraft.getInstance().level.getRecipeManager().byKey(recipeLocation);
        if(optionalIRecipe.isPresent()) {
            IRecipe recipe = optionalIRecipe.get();
            if(recipe instanceof ICraftingRecipe) {
                Minecraft.getInstance().getTextureManager().bind(new ResourceLocation(References.MODID, "textures/gui/books/crafting_recipe.png"));
                //Random rand = Minecraft.getInstance().player.getRandom();
                PlayerEntity player = Minecraft.getInstance().player;
                this.drawTexturedModalRect(matrixStack, x + xIn, y + yIn, 0, 0, 54, 76, zLevel, light);
                for (int i = 0; i < ((ICraftingRecipe)recipe).getIngredients().size(); ++i) {
                    Ingredient ing = ((ICraftingRecipe)recipe).getIngredients().get(i);
                    if (ing != null && !ing.isEmpty())
                        this.drawItemStack(matrixStack, ing.getItems()[player.tickCount / 60 % ing.getItems().length], x + xIn + 1 + ((i % 3) * 18), y + yIn + 1 + ((i / 3) * 18), isGUI, light, buffer);
                }
                this.drawItemStack(matrixStack, recipe.getResultItem(), x + xIn + 19, y + yIn + 59, isGUI, light, buffer);
            }
        }
    }
}
