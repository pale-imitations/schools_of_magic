package com.paleimitations.schoolsofmagic.common.crafting;

import com.google.gson.JsonObject;
import com.paleimitations.schoolsofmagic.common.registries.RecipeRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class MortarRecipeBuilder {
    private final int crush;
    private final Ingredient ingredient;
    private final Ingredient ingredientSecondary;
    private final Item result;
    private final int resultCount;
    private final Item resultSecondary;
    private final int resultSecondaryCount;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private final IRecipeSerializer<?> type;

    public MortarRecipeBuilder(IRecipeSerializer<?> serializer, int crush, Ingredient ingredient, Ingredient ingredientSecondary, Item result, int resultCount,
                               Item resultSecondary, int resultSecondaryCount) {
        this.type = serializer;
        this.crush = crush;
        this.ingredient = ingredient;
        this.ingredientSecondary = ingredientSecondary;
        this.result = result;
        this.resultCount = resultCount;
        this.resultSecondary = resultSecondary;
        this.resultSecondaryCount = resultSecondaryCount;
    }

    public static MortarRecipeBuilder crushing(int crush, Ingredient ingredient, Ingredient ingredientSecondary, Item result, int count, Item resultSecondary, int countSecondary) {
        return new MortarRecipeBuilder(RecipeRegistry.MORTAR_SERIALIZER.get(), crush, ingredient, ingredientSecondary, result, count, resultSecondary, countSecondary);
    }

    public static MortarRecipeBuilder crushing(int crush, Ingredient ingredient, Item result) {
        return new MortarRecipeBuilder(RecipeRegistry.MORTAR_SERIALIZER.get(), crush, ingredient, Ingredient.of(ItemStack.EMPTY), result, 1, Items.AIR, 0);
    }

    public static MortarRecipeBuilder crushing(int crush, Ingredient ingredient, Item result, int count) {
        return new MortarRecipeBuilder(RecipeRegistry.MORTAR_SERIALIZER.get(), crush, ingredient, Ingredient.of(ItemStack.EMPTY), result, count, Items.AIR, 0);
    }

    public MortarRecipeBuilder unlocks(String p_240503_1_, ICriterionInstance p_240503_2_) {
        this.advancement.addCriterion(p_240503_1_, p_240503_2_);
        return this;
    }

    public void save(Consumer<IFinishedRecipe> p_240504_1_, String p_240504_2_) {
        this.save(p_240504_1_, new ResourceLocation(p_240504_2_));
    }

    public void save(Consumer<IFinishedRecipe> p_240505_1_, ResourceLocation p_240505_2_) {
        this.ensureValid(p_240505_2_);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_240505_2_)).rewards(AdvancementRewards.Builder.recipe(p_240505_2_)).requirements(IRequirementsStrategy.OR);
        p_240505_1_.accept(new MortarRecipeBuilder.Result(p_240505_2_, this.type, this.crush, this.ingredient, this.ingredientSecondary, this.result, this.resultCount,
                this.resultSecondary, this.resultSecondaryCount, this.advancement, new ResourceLocation(p_240505_2_.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + p_240505_2_.getPath())));
    }

    private void ensureValid(ResourceLocation p_240506_1_) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_240506_1_);
        }
    }

    public static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final int crush;
        private final Ingredient ingredient;
        private final Ingredient ingredientSecondary;
        private final Item result;
        private final int resultCount;
        private final Item resultSecondary;
        private final int resultSecondaryCount;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final IRecipeSerializer<?> type;

        public Result(ResourceLocation id, IRecipeSerializer<?> type, int crush, Ingredient ingredient, Ingredient ingredientSecondary, Item result, int resultCount,
                      Item resultSecondary, int resultSecondaryCount, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.crush = crush;
            this.type = type;
            this.ingredient = ingredient;
            this.ingredientSecondary = ingredientSecondary;
            this.result = result;
            this.resultCount = resultCount;
            this.resultSecondary = resultSecondary;
            this.resultSecondaryCount = resultSecondaryCount;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        public void serializeRecipeData(JsonObject json) {

            json.addProperty("crush", this.crush);

            json.add("ingredient", this.ingredient.toJson());
            if(!this.ingredientSecondary.isEmpty())
                json.add("ingredientSecondary", this.ingredientSecondary.toJson());

            if (this.resultCount >= 1) {
                JsonObject jsonobject = new JsonObject();
                jsonobject.addProperty("item", Registry.ITEM.getKey(this.result).toString());
                jsonobject.addProperty("count", this.resultCount);
                json.add("result", jsonobject);
            }

            if (this.resultSecondaryCount >= 1) {
                JsonObject jsonobject1 = new JsonObject();
                jsonobject1.addProperty("item", Registry.ITEM.getKey(this.resultSecondary).toString());
                jsonobject1.addProperty("count", this.resultSecondaryCount);
                json.add("resultSecondary", jsonobject1);
            }
        }

        public ResourceLocation getId() {
            return this.id;
        }

        public IRecipeSerializer<?> getType() {
            return this.type;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}