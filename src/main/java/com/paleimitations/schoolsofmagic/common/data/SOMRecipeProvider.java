package com.paleimitations.schoolsofmagic.common.data;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.crafting.MortarRecipeBuilder;
import com.paleimitations.schoolsofmagic.common.registries.BlockRegistry;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import com.paleimitations.schoolsofmagic.common.registries.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class SOMRecipeProvider extends RecipeProvider {
    public SOMRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "Schools of Magic Recipes";
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> recipeConsumer) {
        //MORTAR
        MortarRecipeBuilder.crushing(5, Ingredient.of(ItemRegistry.DRIED_POPPY.get()), ItemRegistry.POPPY_SEEDS.get())
                .unlocks("has_dried_poppy", has(ItemRegistry.DRIED_POPPY.get()))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"poppy_seeds_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(ItemRegistry.DRIED_CORNFLOWER.get()), ItemRegistry.CRUSHED_CORNFLOWER.get())
                .unlocks("has_dried_cornflower", has(ItemRegistry.DRIED_CORNFLOWER.get()))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"crushed_cornflower_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(ItemRegistry.DRIED_ALLIUM.get()), ItemRegistry.CRUSHED_ALLIUM.get())
                .unlocks("has_dried_allium", has(ItemRegistry.DRIED_ALLIUM.get()))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"crushed_allium_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(ItemRegistry.DRIED_DANDELION.get()), ItemRegistry.CRUSHED_DANDELION.get())
                .unlocks("has_dried_dandelion", has(ItemRegistry.DRIED_DANDELION.get()))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"crushed_dandelion_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(ItemRegistry.DRIED_LILY_OF_THE_VALLEY.get()), ItemRegistry.CRUSHED_LILY_OF_THE_VALLY.get())
                .unlocks("has_dried_lily_of_the_valley", has(ItemRegistry.DRIED_LILY_OF_THE_VALLEY.get()))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"crushed_lily_of_the_valley_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(ItemRegistry.DRIED_BLUE_ORCHID.get()), ItemRegistry.CRUSHED_BLUE_ORCHID.get())
                .unlocks("has_dried_blue_orchid", has(ItemRegistry.DRIED_BLUE_ORCHID.get()))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"crushed_blue_orchid_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.BLAZE_ROD), Items.BLAZE_POWDER, 3).unlocks("has_blaze_rod", has(Items.BLAZE_ROD))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"blaze_powder_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(Items.DANDELION), Items.YELLOW_DYE, 2).unlocks("has_dandelion", has(Items.DANDELION))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"dandelion_to_yellow_dye_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(Items.POPPY), Items.RED_DYE, 2).unlocks("has_poppy", has(Items.POPPY))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"poppy_to_red_dye_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(Items.BLUE_ORCHID), Items.LIGHT_BLUE_DYE, 2).unlocks("has_blue_orchid", has(Items.BLUE_ORCHID))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"blue_orchid_to_light_blue_dye_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(Items.ALLIUM), Items.MAGENTA_DYE, 2).unlocks("has_allium", has(Items.ALLIUM))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"allium_to_magenta_dye_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(Items.AZURE_BLUET), Items.LIGHT_GRAY_DYE, 2).unlocks("has_azure_bluet", has(Items.AZURE_BLUET))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"azure_bluet_to_light_gray_dye_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(Items.RED_TULIP), Items.RED_DYE, 2).unlocks("has_red_tulip", has(Items.RED_TULIP))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"red_tulip_to_red_dye_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(Items.ORANGE_TULIP), Items.ORANGE_DYE, 2).unlocks("has_orange_tulip", has(Items.ORANGE_TULIP))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"orange_tulip_to_orange_dye_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(Items.PINK_TULIP), Items.PINK_DYE, 2).unlocks("has_pink_tulip", has(Items.PINK_TULIP))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"pink_tulip_to_pink_dye_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(Items.WHITE_TULIP), Items.LIGHT_GRAY_DYE, 2).unlocks("has_white_tulip", has(Items.WHITE_TULIP))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"white_tulip_to_light_gray_dye_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(Items.OXEYE_DAISY), Items.LIGHT_GRAY_DYE, 2).unlocks("has_oxeye_daisy", has(Items.OXEYE_DAISY))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"oxeye_daisy_to_light_gray_dye_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(Items.CORNFLOWER), Items.BLUE_DYE, 2).unlocks("has_cornflower", has(Items.CORNFLOWER))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"cornflower_to_blue_dye_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(Items.LILY_OF_THE_VALLEY), Items.WHITE_DYE, 2).unlocks("has_lily_of_the_valley", has(Items.LILY_OF_THE_VALLEY))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"lily_of_the_valley_to_white_dye_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(Items.WITHER_ROSE), Items.BLACK_DYE, 2).unlocks("has_wither_rose", has(Items.WITHER_ROSE))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"wither_rose_to_black_dye_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(Items.BONE_MEAL), Items.WHITE_DYE, 2).unlocks("has_bone_meal", has(Items.BONE_MEAL))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"bone_meal_to_white_dye_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(Items.INK_SAC), Items.BLACK_DYE, 2).unlocks("has_ink_sac", has(Items.INK_SAC))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"ink_sac_to_black_dye_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(Items.COCOA_BEANS), Items.BROWN_DYE, 2).unlocks("has_cocoa_beans", has(Items.COCOA_BEANS))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"cocoa_beans_to_brown_dye_mortar"));
        MortarRecipeBuilder.crushing(8, Ingredient.of(Items.SUNFLOWER), Items.YELLOW_DYE, 4).unlocks("has_sunflower", has(Items.SUNFLOWER))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"sunflower_to_yellow_dye_mortar"));
        MortarRecipeBuilder.crushing(8, Ingredient.of(Items.LILAC), Items.MAGENTA_DYE, 4).unlocks("has_lilac", has(Items.LILAC))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"lilac_to_magenta_dye_mortar"));
        MortarRecipeBuilder.crushing(8, Ingredient.of(Items.ROSE_BUSH), Items.RED_DYE, 4).unlocks("has_rose_bush", has(Items.ROSE_BUSH))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"rose_bush_to_red_dye_mortar"));
        MortarRecipeBuilder.crushing(8, Ingredient.of(Items.PEONY), Items.PINK_DYE, 4).unlocks("has_peony", has(Items.PEONY))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"peony_to_pink_dye_mortar"));
        MortarRecipeBuilder.crushing(8, Ingredient.of(Items.SUGAR_CANE), Items.SUGAR, 2).unlocks("has_sugar_cane", has(Items.SUGAR_CANE))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"sugar_mortar"));
        MortarRecipeBuilder.crushing(8, Ingredient.of(Items.BONE), Items.BONE_MEAL, 2).unlocks("has_bone", has(Items.BONE))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"bone_meal_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.GLOWSTONE), Items.GLOWSTONE_DUST, 4).unlocks("has_glowstone", has(Items.GLOWSTONE))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"glowstone_dust_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.PUMPKIN), Items.PUMPKIN_SEEDS, 8).unlocks("has_pumpkin", has(Items.PUMPKIN))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"pumpkin_seeds_mortar"));
        MortarRecipeBuilder.crushing(5, Ingredient.of(Items.MELON_SLICE), Items.MELON_SEEDS, 2).unlocks("has_melon_slice", has(Items.MELON_SLICE))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"melon_seeds_mortar"));
        MortarRecipeBuilder.crushing(20, Ingredient.of(Items.MUSIC_DISC_STAL), Items.MUSIC_DISC_11).unlocks("has_music_disc_stal", has(Items.MUSIC_DISC_STAL))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"stal_to_11_mortar"));
        MortarRecipeBuilder.crushing(8, Ingredient.of(Items.HONEYCOMB), Ingredient.of(Items.GLASS_BOTTLE), Items.HONEY_BOTTLE, 1, Items.AIR, 0)
                .unlocks("has_honeycomb", has(Items.HONEYCOMB))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"honey_bottle_mortar"));
        MortarRecipeBuilder.crushing(8, Ingredient.of(Items.BEETROOT), Ingredient.of(Items.BOWL), Items.BEETROOT_SOUP, 1, Items.AIR, 0)
                .unlocks("has_beetroot", has(Items.BEETROOT))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"beetroot_soup_mortar"));
        MortarRecipeBuilder.crushing(8, Ingredient.of(Items.BROWN_MUSHROOM), Ingredient.of(Items.BOWL), Items.MUSHROOM_STEW, 1, Items.AIR, 0)
                .unlocks("has_brown_mushroom", has(Items.BROWN_MUSHROOM))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"brown_mushroom_soup_mortar"));
        MortarRecipeBuilder.crushing(8, Ingredient.of(Items.RED_MUSHROOM), Ingredient.of(Items.BOWL), Items.MUSHROOM_STEW, 1, Items.AIR, 0)
                .unlocks("has_red_mushroom", has(Items.RED_MUSHROOM))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"red_mushroom_soup_mortar"));
        MortarRecipeBuilder.crushing(8, Ingredient.of(Items.GLISTERING_MELON_SLICE), Ingredient.EMPTY, Items.GOLD_NUGGET, 4, Items.MELON_SEEDS, 2)
                .unlocks("has_glistering_melon_slice", has(Items.GLISTERING_MELON_SLICE))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"glistering_melon_slice_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.RED_TERRACOTTA), ItemRegistry.RED_CLAY_POWDER.get(), 4).unlocks("has_red_terracotta", has(Items.RED_TERRACOTTA))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"red_clay_powder_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.ORANGE_TERRACOTTA), ItemRegistry.ORANGE_CLAY_POWDER.get(), 4).unlocks("has_orange_terracotta", has(Items.ORANGE_TERRACOTTA))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"orange_clay_powder_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.YELLOW_TERRACOTTA), ItemRegistry.YELLOW_CLAY_POWDER.get(), 4).unlocks("has_yellow_terracotta", has(Items.YELLOW_TERRACOTTA))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"yellow_clay_powder_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.LIME_TERRACOTTA), ItemRegistry.LIME_CLAY_POWDER.get(), 4).unlocks("has_lime_terracotta", has(Items.LIME_TERRACOTTA))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"lime_clay_powder_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.GREEN_TERRACOTTA), ItemRegistry.GREEN_CLAY_POWDER.get(), 4).unlocks("has_green_terracotta", has(Items.GREEN_TERRACOTTA))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"green_clay_powder_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.CYAN_TERRACOTTA), ItemRegistry.CYAN_CLAY_POWDER.get(), 4).unlocks("has_cyan_terracotta", has(Items.CYAN_TERRACOTTA))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"cyan_clay_powder_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.LIGHT_BLUE_TERRACOTTA), ItemRegistry.LIGHT_BLUE_CLAY_POWDER.get(), 4).unlocks("has_light_blue_terracotta", has(Items.LIGHT_BLUE_TERRACOTTA))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"light_blue_clay_powder_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.BLUE_TERRACOTTA), ItemRegistry.BLUE_CLAY_POWDER.get(), 4).unlocks("has_blue_terracotta", has(Items.BLUE_TERRACOTTA))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"blue_clay_powder_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.PURPLE_TERRACOTTA), ItemRegistry.PURPLE_CLAY_POWDER.get(), 4).unlocks("has_purple_terracotta", has(Items.PURPLE_TERRACOTTA))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"purple_clay_powder_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.MAGENTA_TERRACOTTA), ItemRegistry.MAGENTA_CLAY_POWDER.get(), 4).unlocks("has_magenta_terracotta", has(Items.MAGENTA_TERRACOTTA))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"magenta_clay_powder_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.PINK_TERRACOTTA), ItemRegistry.PINK_CLAY_POWDER.get(), 4).unlocks("has_pink_terracotta", has(Items.PINK_TERRACOTTA))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"pink_clay_powder_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.WHITE_TERRACOTTA), ItemRegistry.WHITE_CLAY_POWDER.get(), 4).unlocks("has_white_terracotta", has(Items.WHITE_TERRACOTTA))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"white_clay_powder_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.LIGHT_GRAY_TERRACOTTA), ItemRegistry.LIGHT_GRAY_CLAY_POWDER.get(), 4).unlocks("has_light_gray_terracotta", has(Items.LIGHT_GRAY_TERRACOTTA))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"light_gray_clay_powder_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.GRAY_TERRACOTTA), ItemRegistry.GRAY_CLAY_POWDER.get(), 4).unlocks("has_gray_terracotta", has(Items.GRAY_TERRACOTTA))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"gray_clay_powder_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.BLACK_TERRACOTTA), ItemRegistry.BLACK_CLAY_POWDER.get(), 4).unlocks("has_black_terracotta", has(Items.BLACK_TERRACOTTA))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"black_clay_powder_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.BROWN_TERRACOTTA), ItemRegistry.BROWN_CLAY_POWDER.get(), 4).unlocks("has_brown_terracotta", has(Items.BROWN_TERRACOTTA))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"brown_clay_powder_mortar"));
        MortarRecipeBuilder.crushing(10, Ingredient.of(Items.TERRACOTTA), ItemRegistry.TERRACOTTA_CLAY_POWDER.get(), 4).unlocks("has_terracotta", has(Items.TERRACOTTA))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"terracotta_clay_powder_mortar"));

        //CRAFTING
        ShapedRecipeBuilder.shaped(ItemRegistry.APPRENTICE_WAND_1.get()).define('S', Items.STICK).define('G', Items.GOLD_INGOT)
                .define('D', Items.DIAMOND).pattern("S  ").pattern(" G ").pattern("  D").unlockedBy("has_diamond", has(Items.DIAMOND))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(BlockRegistry.DIORITE_MORTAR.get()).define('S', Items.STICK).define('D', Items.DIORITE)
                .pattern(" S ").pattern("D D").pattern(" D ").unlockedBy("has_diorite", has(Items.DIORITE))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(BlockRegistry.ANDESITE_MORTAR.get()).define('S', Items.STICK).define('A', Items.ANDESITE)
                .pattern(" S ").pattern("A A").pattern(" A ").unlockedBy("has_andesite", has(Items.ANDESITE))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(BlockRegistry.GRANITE_MORTAR.get()).define('S', Items.STICK).define('G', Items.GRANITE)
                .pattern(" S ").pattern("G G").pattern(" G ").unlockedBy("has_diorite", has(Items.GRANITE))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.RED_CLAY_TEAPOT.get()).define('C', ItemRegistry.RED_CLAY.get())
                .pattern("C C").pattern("C C").pattern(" C ").unlockedBy("has_red_clay", has(ItemRegistry.RED_CLAY.get())).save(recipeConsumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.ORANGE_CLAY_TEAPOT.get()).define('C', ItemRegistry.ORANGE_CLAY.get())
                .pattern("C C").pattern("C C").pattern(" C ").unlockedBy("has_orange_clay", has(ItemRegistry.ORANGE_CLAY.get())).save(recipeConsumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.YELLOW_CLAY_TEAPOT.get()).define('C', ItemRegistry.YELLOW_CLAY.get())
                .pattern("C C").pattern("C C").pattern(" C ").unlockedBy("has_yellow_clay", has(ItemRegistry.YELLOW_CLAY.get())).save(recipeConsumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.LIME_CLAY_TEAPOT.get()).define('C', ItemRegistry.LIME_CLAY.get())
                .pattern("C C").pattern("C C").pattern(" C ").unlockedBy("has_lime_clay", has(ItemRegistry.LIME_CLAY.get())).save(recipeConsumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.WHITE_CLAY_TEAPOT.get()).define('C', ItemRegistry.WHITE_CLAY.get())
                .pattern("C C").pattern("C C").pattern(" C ").unlockedBy("has_white_clay", has(ItemRegistry.WHITE_CLAY.get())).save(recipeConsumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.WHITE_CLAY_TEAPLATE.get()).define('C', ItemRegistry.WHITE_CLAY.get())
                .pattern("CCC").unlockedBy("has_white_clay", has(ItemRegistry.WHITE_CLAY.get())).save(recipeConsumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.WHITE_CLAY_TEACUP.get()).define('C', ItemRegistry.WHITE_CLAY.get())
                .pattern("C C").pattern(" C ").unlockedBy("has_white_clay", has(ItemRegistry.WHITE_CLAY.get())).save(recipeConsumer);
        ShapelessRecipeBuilder.shapeless(BlockRegistry.HERBAL_TWINE.get()).requires(Items.STRING).requires(Items.GRASS)
                .unlockedBy("has_string", has(Items.STRING)).save(recipeConsumer);
        ShapelessRecipeBuilder.shapeless(ItemRegistry.POPPY_SEED_MUFFIN.get()).requires(Items.WHEAT).requires(Items.SUGAR).requires(Items.EGG)
                .requires(ItemRegistry.POPPY_SEEDS.get()).unlockedBy("has_string", has(Items.STRING)).save(recipeConsumer);
        for(int i = 1; i <= 4; ++i) {
            ShapelessRecipeBuilder.shapeless(ItemRegistry.RED_CLAY.get(), i).requires(ItemRegistry.RED_CLAY_POWDER.get(), i).requires(Items.WATER_BUCKET)
                    .unlockedBy("has_red_clay_powder", has(ItemRegistry.RED_CLAY_POWDER.get()))
                    .save(recipeConsumer, new ResourceLocation(References.MODID,"red_clay_"+i));
            ShapelessRecipeBuilder.shapeless(ItemRegistry.ORANGE_CLAY.get(), i).requires(ItemRegistry.ORANGE_CLAY_POWDER.get(), i).requires(Items.WATER_BUCKET)
                    .unlockedBy("has_orange_clay_powder", has(ItemRegistry.ORANGE_CLAY_POWDER.get()))
                    .save(recipeConsumer, new ResourceLocation(References.MODID,"orange_clay_"+i));
            ShapelessRecipeBuilder.shapeless(ItemRegistry.YELLOW_CLAY.get(), i).requires(ItemRegistry.YELLOW_CLAY_POWDER.get(), i).requires(Items.WATER_BUCKET)
                    .unlockedBy("has_yellow_clay_powder", has(ItemRegistry.YELLOW_CLAY_POWDER.get()))
                    .save(recipeConsumer, new ResourceLocation(References.MODID,"yellow_clay_"+i));
            ShapelessRecipeBuilder.shapeless(ItemRegistry.LIME_CLAY.get(), i).requires(ItemRegistry.LIME_CLAY_POWDER.get(), i).requires(Items.WATER_BUCKET)
                    .unlockedBy("has_lime_clay_powder", has(ItemRegistry.LIME_CLAY_POWDER.get()))
                    .save(recipeConsumer, new ResourceLocation(References.MODID,"lime_clay_"+i));
            ShapelessRecipeBuilder.shapeless(ItemRegistry.GREEN_CLAY.get(), i).requires(ItemRegistry.GREEN_CLAY_POWDER.get(), i).requires(Items.WATER_BUCKET)
                    .unlockedBy("has_green_clay_powder", has(ItemRegistry.GREEN_CLAY_POWDER.get()))
                    .save(recipeConsumer, new ResourceLocation(References.MODID,"green_clay_"+i));
            ShapelessRecipeBuilder.shapeless(ItemRegistry.CYAN_CLAY.get(), i).requires(ItemRegistry.CYAN_CLAY_POWDER.get(), i).requires(Items.WATER_BUCKET)
                    .unlockedBy("has_cyan_clay_powder", has(ItemRegistry.CYAN_CLAY_POWDER.get()))
                    .save(recipeConsumer, new ResourceLocation(References.MODID,"cyan_clay_"+i));
            ShapelessRecipeBuilder.shapeless(ItemRegistry.LIGHT_BLUE_CLAY.get(), i).requires(ItemRegistry.LIGHT_BLUE_CLAY_POWDER.get(), i).requires(Items.WATER_BUCKET)
                    .unlockedBy("has_light_blue_clay_powder", has(ItemRegistry.LIGHT_BLUE_CLAY_POWDER.get()))
                    .save(recipeConsumer, new ResourceLocation(References.MODID,"light_blue_clay_"+i));
            ShapelessRecipeBuilder.shapeless(ItemRegistry.BLUE_CLAY.get(), i).requires(ItemRegistry.BLUE_CLAY_POWDER.get(), i).requires(Items.WATER_BUCKET)
                    .unlockedBy("has_blue_clay_powder", has(ItemRegistry.BLUE_CLAY_POWDER.get()))
                    .save(recipeConsumer, new ResourceLocation(References.MODID,"blue_clay_"+i));
            ShapelessRecipeBuilder.shapeless(ItemRegistry.PURPLE_CLAY.get(), i).requires(ItemRegistry.PURPLE_CLAY_POWDER.get(), i).requires(Items.WATER_BUCKET)
                    .unlockedBy("has_purple_clay_powder", has(ItemRegistry.PURPLE_CLAY_POWDER.get()))
                    .save(recipeConsumer, new ResourceLocation(References.MODID,"purple_clay_"+i));
            ShapelessRecipeBuilder.shapeless(ItemRegistry.MAGENTA_CLAY.get(), i).requires(ItemRegistry.MAGENTA_CLAY_POWDER.get(), i).requires(Items.WATER_BUCKET)
                    .unlockedBy("has_magenta_clay_powder", has(ItemRegistry.MAGENTA_CLAY_POWDER.get()))
                    .save(recipeConsumer, new ResourceLocation(References.MODID,"magenta_clay_"+i));
            ShapelessRecipeBuilder.shapeless(ItemRegistry.PINK_CLAY.get(), i).requires(ItemRegistry.PINK_CLAY_POWDER.get(), i).requires(Items.WATER_BUCKET)
                    .unlockedBy("has_pink_clay_powder", has(ItemRegistry.PINK_CLAY_POWDER.get()))
                    .save(recipeConsumer, new ResourceLocation(References.MODID,"pink_clay_"+i));
            ShapelessRecipeBuilder.shapeless(ItemRegistry.WHITE_CLAY.get(), i).requires(ItemRegistry.WHITE_CLAY_POWDER.get(), i).requires(Items.WATER_BUCKET)
                    .unlockedBy("has_white_clay_powder", has(ItemRegistry.WHITE_CLAY_POWDER.get()))
                    .save(recipeConsumer, new ResourceLocation(References.MODID,"white_clay_"+i));
            ShapelessRecipeBuilder.shapeless(ItemRegistry.LIGHT_GRAY_CLAY.get(), i).requires(ItemRegistry.LIGHT_GRAY_CLAY_POWDER.get(), i).requires(Items.WATER_BUCKET)
                    .unlockedBy("has_light_gray_clay_powder", has(ItemRegistry.LIGHT_GRAY_CLAY_POWDER.get()))
                    .save(recipeConsumer, new ResourceLocation(References.MODID,"light_gray_clay_"+i));
            ShapelessRecipeBuilder.shapeless(ItemRegistry.GRAY_CLAY.get(), i).requires(ItemRegistry.GRAY_CLAY_POWDER.get(), i).requires(Items.WATER_BUCKET)
                    .unlockedBy("has_gray_clay_powder", has(ItemRegistry.GRAY_CLAY_POWDER.get()))
                    .save(recipeConsumer, new ResourceLocation(References.MODID,"gray_clay_"+i));
            ShapelessRecipeBuilder.shapeless(ItemRegistry.BLACK_CLAY.get(), i).requires(ItemRegistry.BLACK_CLAY_POWDER.get(), i).requires(Items.WATER_BUCKET)
                    .unlockedBy("has_black_clay_powder", has(ItemRegistry.BLACK_CLAY_POWDER.get()))
                    .save(recipeConsumer, new ResourceLocation(References.MODID,"black_clay_"+i));
            ShapelessRecipeBuilder.shapeless(ItemRegistry.BROWN_CLAY.get(), i).requires(ItemRegistry.BROWN_CLAY_POWDER.get(), i).requires(Items.WATER_BUCKET)
                    .unlockedBy("has_brown_clay_powder", has(ItemRegistry.BROWN_CLAY_POWDER.get()))
                    .save(recipeConsumer, new ResourceLocation(References.MODID,"brown_clay_"+i));
            ShapelessRecipeBuilder.shapeless(ItemRegistry.TERRACOTTA_CLAY.get(), i).requires(ItemRegistry.TERRACOTTA_CLAY_POWDER.get(), i).requires(Items.WATER_BUCKET)
                    .unlockedBy("has_terracotta_clay_powder", has(ItemRegistry.TERRACOTTA_CLAY_POWDER.get()))
                    .save(recipeConsumer, new ResourceLocation(References.MODID,"terracotta_clay_"+i));
        }

        //FURNACE
        CookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.RED_CLAY_TEAPOT.get()), BlockRegistry.RED_TERRACOTTA_TEAPOT.get().asItem(),0.1F,200)
                .unlockedBy("has_red_clay_teapot", has(ItemRegistry.RED_CLAY_TEAPOT.get())).save(recipeConsumer);
        CookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.ORANGE_CLAY_TEAPOT.get()), BlockRegistry.ORANGE_TERRACOTTA_TEAPOT.get().asItem(),0.1F,200)
                .unlockedBy("has_orange_clay_teapot", has(ItemRegistry.ORANGE_CLAY_TEAPOT.get())).save(recipeConsumer);
        CookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.YELLOW_CLAY_TEAPOT.get()), BlockRegistry.YELLOW_TERRACOTTA_TEAPOT.get().asItem(),0.1F,200)
                .unlockedBy("has_yellow_clay_teapot", has(ItemRegistry.YELLOW_CLAY_TEAPOT.get())).save(recipeConsumer);
        CookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.LIME_CLAY_TEAPOT.get()), BlockRegistry.LIME_TERRACOTTA_TEAPOT.get().asItem(),0.1F,200)
                .unlockedBy("has_lime_clay_teapot", has(ItemRegistry.LIME_CLAY_TEAPOT.get())).save(recipeConsumer);
        CookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.WHITE_CLAY_TEAPOT.get()), BlockRegistry.WHITE_TERRACOTTA_TEAPOT.get().asItem(),0.1F,200)
                .unlockedBy("has_white_clay_teapot", has(ItemRegistry.WHITE_CLAY_TEAPOT.get())).save(recipeConsumer);
        CookingRecipeBuilder.smelting(Ingredient.of(BlockRegistry.RED_TERRACOTTA_TEAPOT.get()), BlockRegistry.RED_TEAPOT.get().asItem(),0.1F,200)
                .unlockedBy("has_red_terracotta_teapot", has(BlockRegistry.RED_TERRACOTTA_TEAPOT.get())).save(recipeConsumer);
        CookingRecipeBuilder.smelting(Ingredient.of(BlockRegistry.ORANGE_TERRACOTTA_TEAPOT.get()), BlockRegistry.ORANGE_TEAPOT.get().asItem(),0.1F,200)
                .unlockedBy("has_orange_terracotta_teapot", has(BlockRegistry.ORANGE_TERRACOTTA_TEAPOT.get())).save(recipeConsumer);
        CookingRecipeBuilder.smelting(Ingredient.of(BlockRegistry.YELLOW_TERRACOTTA_TEAPOT.get()), BlockRegistry.YELLOW_TEAPOT.get().asItem(),0.1F,200)
                .unlockedBy("has_yellow_terracotta_teapot", has(BlockRegistry.YELLOW_TERRACOTTA_TEAPOT.get())).save(recipeConsumer);
        CookingRecipeBuilder.smelting(Ingredient.of(BlockRegistry.LIME_TERRACOTTA_TEAPOT.get()), BlockRegistry.LIME_TEAPOT.get().asItem(),0.1F,200)
                .unlockedBy("has_lime_terracotta_teapot", has(BlockRegistry.LIME_TERRACOTTA_TEAPOT.get())).save(recipeConsumer);
        CookingRecipeBuilder.smelting(Ingredient.of(BlockRegistry.WHITE_TERRACOTTA_TEAPOT.get()), BlockRegistry.WHITE_TEAPOT.get().asItem(),0.1F,200)
                .unlockedBy("has_white_terracotta_teapot", has(BlockRegistry.WHITE_TERRACOTTA_TEAPOT.get())).save(recipeConsumer);
        CookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.WHITE_CLAY_TEAPLATE.get()), BlockRegistry.WHITE_TERRACOTTA_TEAPLATE.get().asItem(),0.1F,200)
                .unlockedBy("has_white_clay_teaplate", has(ItemRegistry.WHITE_CLAY_TEAPLATE.get())).save(recipeConsumer);
        CookingRecipeBuilder.smelting(Ingredient.of(BlockRegistry.WHITE_TERRACOTTA_TEAPLATE.get()), BlockRegistry.WHITE_TEAPLATE.get().asItem(),0.1F,200)
                .unlockedBy("has_white_terracotta_teaplate", has(BlockRegistry.WHITE_TERRACOTTA_TEAPOT.get())).save(recipeConsumer);
        CookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.WHITE_CLAY_TEACUP.get()), ItemRegistry.WHITE_TERRACOTTA_TEACUP.get().asItem(),0.1F,200)
                .unlockedBy("has_white_clay_teacup", has(ItemRegistry.WHITE_CLAY_TEACUP.get())).save(recipeConsumer);
        CookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.WHITE_TERRACOTTA_TEACUP.get()), ItemRegistry.WHITE_TEACUP.get().asItem(),0.1F,200)
                .unlockedBy("has_white_terracotta_teacup", has(ItemRegistry.WHITE_TERRACOTTA_TEACUP.get())).save(recipeConsumer);

        //SMITHING
        SmithingRecipeBuilder.smithing(Ingredient.of(ItemRegistry.APPRENTICE_WAND_1.get()), Ingredient.of(Items.DIAMOND), ItemRegistry.APPRENTICE_WAND_2.get())
                .unlocks("has_apprentice_wand_1", has(ItemRegistry.APPRENTICE_WAND_1.get())).save(recipeConsumer,
                new ResourceLocation(References.MODID,"apprentice_wand_2_upgrade_smithing"));
        SmithingRecipeBuilder.smithing(Ingredient.of(ItemRegistry.APPRENTICE_WAND_2.get()), Ingredient.of(Items.DIAMOND), ItemRegistry.APPRENTICE_WAND_3.get())
                .unlocks("has_apprentice_wand_2", has(ItemRegistry.APPRENTICE_WAND_2.get())).save(recipeConsumer,
                new ResourceLocation(References.MODID,"apprentice_wand_3_upgrade_smithing"));
        SmithingRecipeBuilder.smithing(Ingredient.of(ItemRegistry.APPRENTICE_WAND_3.get()), Ingredient.of(Items.DIAMOND), ItemRegistry.APPRENTICE_WAND_4.get())
                .unlocks("has_apprentice_wand_3", has(ItemRegistry.APPRENTICE_WAND_3.get())).save(recipeConsumer,
                new ResourceLocation(References.MODID,"apprentice_wand_4_upgrade_smithing"));

        //STONE_CUTTER
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.SANDSTONE), BlockRegistry.SANDSTONE_PODIUM.get()).unlocks("has_sandstone", has(Blocks.SANDSTONE))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"sandstone_podium_from_sandstone_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.RED_SANDSTONE), BlockRegistry.RED_SANDSTONE_PODIUM.get()).unlocks("has_sandstone", has(Blocks.RED_SANDSTONE))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"red_sandstone_podium_from_red_sandstone_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.STONE), BlockRegistry.STONE_PODIUM.get()).unlocks("has_stone", has(Blocks.STONE))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"stone_podium_from_stone_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.OBSIDIAN), BlockRegistry.OBSIDIAN_PODIUM.get()).unlocks("has_obsidian", has(Blocks.OBSIDIAN))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"obsidian_podium_from_obsidian_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.GRANITE), BlockRegistry.GRANITE_PODIUM.get()).unlocks("has_granite", has(Blocks.GRANITE))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"granite_podium_from_granite_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.ANDESITE), BlockRegistry.ANDESITE_PODIUM.get()).unlocks("has_andesite", has(Blocks.ANDESITE))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"andesite_podium_from_andesite_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.DIORITE), BlockRegistry.DIORITE_PODIUM.get()).unlocks("has_diorite", has(Blocks.DIORITE))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"diorite_podium_from_diorite_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.QUARTZ_BLOCK), BlockRegistry.QUARTZ_PODIUM.get()).unlocks("has_quartz_block", has(Blocks.QUARTZ_BLOCK))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"quartz_podium_from_quartz_block_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.NETHER_BRICKS), BlockRegistry.NETHERBRICK_PODIUM.get()).unlocks("has_nether_bricks", has(Blocks.NETHER_BRICKS))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"netherbrick_podium_from_nether_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.PRISMARINE_BRICKS), BlockRegistry.PRISMARINE_PODIUM.get()).unlocks("has_prismarine_bricks", has(Blocks.PRISMARINE_BRICKS))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"prismarine_podium_from_prismarine_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.PURPUR_BLOCK), BlockRegistry.PURPUR_PODIUM.get()).unlocks("has_purpur_block", has(Blocks.PURPUR_BLOCK))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"purpur_podium_from_purpur_block_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.BASALT), BlockRegistry.BASALT_PODIUM.get()).unlocks("has_basalt", has(Blocks.BASALT))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"basalt_podium_from_basalt_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.BLACKSTONE), BlockRegistry.BLACKSTONE_PODIUM.get()).unlocks("has_blackstone", has(Blocks.BLACKSTONE))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"blackstone_podium_from_blackstone_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.END_STONE), BlockRegistry.ENDSTONE_PODIUM.get()).unlocks("has_end_stone", has(Blocks.END_STONE))
                .save(recipeConsumer, new ResourceLocation(References.MODID,"endstone_podium_from_end_stone_stonecutting"));
        addWoodRecipes(recipeConsumer, TagRegistry.Items.ACOLYTE_LOGS, BlockRegistry.ACOLYTE_LOG.get(), BlockRegistry.ACOLYTE_WOOD.get(),
                BlockRegistry.STRIPPED_ACOLYTE_LOG.get(), BlockRegistry.STRIPPED_ACOLYTE_WOOD.get(), BlockRegistry.ACOLYTE_PLANKS.get(),
                BlockRegistry.ACOLYTE_STAIRS.get(), BlockRegistry.ACOLYTE_SLAB.get(), BlockRegistry.ACOLYTE_DOOR.get(), BlockRegistry.ACOLYTE_TRAPDOOR.get(),
                BlockRegistry.ACOLYTE_FENCE.get(), BlockRegistry.ACOLYTE_FENCE_GATE.get(), BlockRegistry.ACOLYTE_BUTTON.get(),
                BlockRegistry.ACOLYTE_PRESSURE_PLATE.get(), ItemRegistry.ACOLYTE_SIGN.get());
        addWoodRecipes(recipeConsumer, TagRegistry.Items.BASTION_LOGS, BlockRegistry.BASTION_LOG.get(), BlockRegistry.BASTION_WOOD.get(),
                BlockRegistry.STRIPPED_BASTION_LOG.get(), BlockRegistry.STRIPPED_BASTION_WOOD.get(), BlockRegistry.BASTION_PLANKS.get(),
                BlockRegistry.BASTION_STAIRS.get(), BlockRegistry.BASTION_SLAB.get(), BlockRegistry.BASTION_DOOR.get(), BlockRegistry.BASTION_TRAPDOOR.get(),
                BlockRegistry.BASTION_FENCE.get(), BlockRegistry.BASTION_FENCE_GATE.get(), BlockRegistry.BASTION_BUTTON.get(),
                BlockRegistry.BASTION_PRESSURE_PLATE.get(), ItemRegistry.BASTION_SIGN.get());
        addWoodRecipes(recipeConsumer, TagRegistry.Items.VERMILION_LOGS, BlockRegistry.VERMILION_LOG.get(), BlockRegistry.VERMILION_WOOD.get(),
                BlockRegistry.STRIPPED_VERMILION_LOG.get(), BlockRegistry.STRIPPED_VERMILION_WOOD.get(), BlockRegistry.VERMILION_PLANKS.get(),
                BlockRegistry.VERMILION_STAIRS.get(), BlockRegistry.VERMILION_SLAB.get(), BlockRegistry.VERMILION_DOOR.get(),
                BlockRegistry.VERMILION_TRAPDOOR.get(), BlockRegistry.VERMILION_FENCE.get(), BlockRegistry.VERMILION_FENCE_GATE.get(),
                BlockRegistry.VERMILION_BUTTON.get(), BlockRegistry.VERMILION_PRESSURE_PLATE.get(), ItemRegistry.VERMILION_SIGN.get());
        addWoodRecipes(recipeConsumer, TagRegistry.Items.WARTWOOD_LOGS, BlockRegistry.WARTWOOD_LOG.get(), BlockRegistry.WARTWOOD_WOOD.get(),
                BlockRegistry.STRIPPED_WARTWOOD_LOG.get(), BlockRegistry.STRIPPED_WARTWOOD_WOOD.get(), BlockRegistry.WARTWOOD_PLANKS.get(),
                BlockRegistry.WARTWOOD_STAIRS.get(), BlockRegistry.WARTWOOD_SLAB.get(), BlockRegistry.WARTWOOD_DOOR.get(), BlockRegistry.WARTWOOD_TRAPDOOR.get(),
                BlockRegistry.WARTWOOD_FENCE.get(), BlockRegistry.WARTWOOD_FENCE_GATE.get(), BlockRegistry.WARTWOOD_BUTTON.get(),
                BlockRegistry.WARTWOOD_PRESSURE_PLATE.get(), ItemRegistry.WARTWOOD_SIGN.get());
        addWoodRecipes(recipeConsumer, TagRegistry.Items.JUBILEE_LOGS, BlockRegistry.JUBILEE_LOG.get(), BlockRegistry.JUBILEE_WOOD.get(),
                BlockRegistry.STRIPPED_JUBILEE_LOG.get(), BlockRegistry.STRIPPED_JUBILEE_WOOD.get(), BlockRegistry.JUBILEE_PLANKS.get(),
                BlockRegistry.JUBILEE_STAIRS.get(), BlockRegistry.JUBILEE_SLAB.get(), BlockRegistry.JUBILEE_DOOR.get(), BlockRegistry.JUBILEE_TRAPDOOR.get(),
                BlockRegistry.JUBILEE_FENCE.get(), BlockRegistry.JUBILEE_FENCE_GATE.get(), BlockRegistry.JUBILEE_BUTTON.get(),
                BlockRegistry.JUBILEE_PRESSURE_PLATE.get(), ItemRegistry.JUBILEE_SIGN.get());
        addWoodRecipes(recipeConsumer, TagRegistry.Items.EVERMORE_LOGS, BlockRegistry.EVERMORE_LOG.get(), BlockRegistry.EVERMORE_WOOD.get(),
                BlockRegistry.STRIPPED_EVERMORE_LOG.get(), BlockRegistry.STRIPPED_EVERMORE_WOOD.get(),BlockRegistry.EVERMORE_PLANKS.get(),
                BlockRegistry.EVERMORE_STAIRS.get(), BlockRegistry.EVERMORE_SLAB.get(), BlockRegistry.EVERMORE_DOOR.get(),
                BlockRegistry.EVERMORE_TRAPDOOR.get(), BlockRegistry.EVERMORE_FENCE.get(), BlockRegistry.EVERMORE_FENCE_GATE.get(),
                BlockRegistry.EVERMORE_BUTTON.get(), BlockRegistry.EVERMORE_PRESSURE_PLATE.get(), ItemRegistry.EVERMORE_SIGN.get());

    }

    public void addWoodRecipes(Consumer<IFinishedRecipe> recipeConsumer, ITag<Item> logTag, Block log, Block wood, Block strippedLog, Block strippedWood,
                               Block planks, Block stairs, Block slab, Block door, Block trapdoor, Block fence, Block fencegate, Block button, Block plate, Item sign) {
        ShapedRecipeBuilder.shaped(wood, 3).define('L', log).pattern("LL").pattern("LL")
                .unlockedBy("has_"+log.getRegistryName().getPath(), has(log))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(strippedWood, 3).define('L', strippedLog).pattern("LL").pattern("LL")
                .unlockedBy("has_"+strippedLog.getRegistryName().getPath(), has(strippedLog))
                .save(recipeConsumer);
        ShapelessRecipeBuilder.shapeless(planks, 4).requires(Ingredient.of(logTag))
                .unlockedBy("has_"+logTag.toString().replace(References.MODID+":", ""), has(logTag))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(stairs, 4).define('P', planks).pattern("P  ").pattern("PP ").pattern("PPP")
                .unlockedBy("has_"+planks.getRegistryName().getPath(), has(planks))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(slab, 6).define('P', planks).pattern("PPP")
                .unlockedBy("has_"+planks.getRegistryName().getPath(), has(planks))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(plate).define('P', planks).pattern("PP")
                .unlockedBy("has_"+planks.getRegistryName().getPath(), has(planks))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(door, 3).define('P', planks).pattern("PP").pattern("PP").pattern("PP")
                .unlockedBy("has_"+planks.getRegistryName().getPath(), has(planks))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(trapdoor, 2).define('P', planks).pattern("PPP").pattern("PPP")
                .unlockedBy("has_"+planks.getRegistryName().getPath(), has(planks))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(fence, 3).define('P', planks).define('S', Tags.Items.RODS_WOODEN).pattern("PSP").pattern("PSP")
                .unlockedBy("has_"+planks.getRegistryName().getPath(), has(planks))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(fencegate).define('P', planks).define('S', Tags.Items.RODS_WOODEN).pattern("SPS").pattern("SPS")
                .unlockedBy("has_"+planks.getRegistryName().getPath(), has(planks))
                .save(recipeConsumer);
        ShapelessRecipeBuilder.shapeless(button).requires(planks)
                .unlockedBy("has_"+planks.getRegistryName().getPath(), has(planks))
                .save(recipeConsumer);
        ShapedRecipeBuilder.shaped(sign).define('P', planks).define('S', Tags.Items.RODS_WOODEN).pattern("PPP").pattern("PPP").pattern(" S ")
                .unlockedBy("has_"+planks.getRegistryName().getPath(), has(planks))
                .save(recipeConsumer);
    }
}
