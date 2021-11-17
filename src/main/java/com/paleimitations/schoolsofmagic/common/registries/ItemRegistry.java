package com.paleimitations.schoolsofmagic.common.registries;

import com.google.common.collect.Maps;
import com.paleimitations.schoolsofmagic.common.data.loottables.LootInjecter;
import com.paleimitations.schoolsofmagic.common.items.*;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;

import java.util.Map;

public class ItemRegistry {

    public static Map<Item, Item> TEACUP_ENTRIES = Maps.newHashMap();

    //public static final RegistryObject<Item> SILVER_INGOT = Registry.ITEMS.register("silver_ingot", ()-> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    //public static final RegistryObject<Item> BRASS_INGOT = Registry.ITEMS.register("brass_ingot", ()-> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    //public static final RegistryObject<Item> BRONZE_INGOT = Registry.ITEMS.register("bronze_ingot", ()-> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    //public static final RegistryObject<Item> COPPER_INGOT = Registry.ITEMS.register("copper_ingot", ()-> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    //public static final RegistryObject<Item> ALLORITE_INGOT = Registry.ITEMS.register("allorite_ingot", ()-> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    //public static final RegistryObject<Item> TENEBRIUM_INGOT = Registry.ITEMS.register("tenebrium_ingot", ()-> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    public static final RegistryObject<Item> APPRENTICE_WAND_1 = Registry.ITEMS.register("apprentice_wand_1", ()-> new WandBaseItem(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> APPRENTICE_WAND_2 = Registry.ITEMS.register("apprentice_wand_2", ()-> new WandBaseItem(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> APPRENTICE_WAND_3 = Registry.ITEMS.register("apprentice_wand_3", ()-> new WandBaseItem(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> APPRENTICE_WAND_4 = Registry.ITEMS.register("apprentice_wand_4", ()-> new WandBaseItem(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> BASIC_ARCANA = Registry.ITEMS.register("basic_arcana", ()-> new BookBaseItem(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> QUEST_NOTE = Registry.ITEMS.register("quest_note", ()-> new QuestNoteItem(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> LETTER_CCW = Registry.ITEMS.register("letter_ccw", ()-> new LetterItem(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> DRIED_POPPY = Registry.ITEMS.register("dried_poppy", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> POPPY_SEEDS = Registry.ITEMS.register("poppy_seeds", ()-> new PoppySeedsItem(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> POPPY_SEED_MUFFIN = Registry.ITEMS.register("poppy_seed_muffin", () -> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB).food(FoodRegistry.POPPY_SEED_MUFFIN)));
    public static final RegistryObject<Item> DRIED_CORNFLOWER = Registry.ITEMS.register("dried_cornflower", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> CRUSHED_CORNFLOWER = Registry.ITEMS.register("crushed_cornflower", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> DRIED_ALLIUM = Registry.ITEMS.register("dried_allium", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> CRUSHED_ALLIUM = Registry.ITEMS.register("crushed_allium", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> DRIED_DANDELION = Registry.ITEMS.register("dried_dandelion", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> CRUSHED_DANDELION = Registry.ITEMS.register("crushed_dandelion", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> DRIED_LILY_OF_THE_VALLEY = Registry.ITEMS.register("dried_lily_of_the_valley", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> CRUSHED_LILY_OF_THE_VALLY = Registry.ITEMS.register("crushed_lily_of_the_valley", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> DRIED_BLUE_ORCHID = Registry.ITEMS.register("dried_blue_orchid", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> CRUSHED_BLUE_ORCHID = Registry.ITEMS.register("crushed_blue_orchid", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> RED_CLAY_POWDER = Registry.ITEMS.register("red_clay_powder", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> ORANGE_CLAY_POWDER = Registry.ITEMS.register("orange_clay_powder", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> YELLOW_CLAY_POWDER = Registry.ITEMS.register("yellow_clay_powder", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> LIME_CLAY_POWDER = Registry.ITEMS.register("lime_clay_powder", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> GREEN_CLAY_POWDER = Registry.ITEMS.register("green_clay_powder", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> CYAN_CLAY_POWDER = Registry.ITEMS.register("cyan_clay_powder", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> LIGHT_BLUE_CLAY_POWDER = Registry.ITEMS.register("light_blue_clay_powder", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> BLUE_CLAY_POWDER = Registry.ITEMS.register("blue_clay_powder", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> PURPLE_CLAY_POWDER = Registry.ITEMS.register("purple_clay_powder", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> MAGENTA_CLAY_POWDER = Registry.ITEMS.register("magenta_clay_powder", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> PINK_CLAY_POWDER = Registry.ITEMS.register("pink_clay_powder", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> WHITE_CLAY_POWDER = Registry.ITEMS.register("white_clay_powder", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> LIGHT_GRAY_CLAY_POWDER = Registry.ITEMS.register("light_gray_clay_powder", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> GRAY_CLAY_POWDER = Registry.ITEMS.register("gray_clay_powder", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> BLACK_CLAY_POWDER = Registry.ITEMS.register("black_clay_powder", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> BROWN_CLAY_POWDER = Registry.ITEMS.register("brown_clay_powder", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> TERRACOTTA_CLAY_POWDER = Registry.ITEMS.register("terracotta_clay_powder", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> RED_CLAY = Registry.ITEMS.register("red_clay", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> ORANGE_CLAY = Registry.ITEMS.register("orange_clay", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> YELLOW_CLAY = Registry.ITEMS.register("yellow_clay", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> LIME_CLAY = Registry.ITEMS.register("lime_clay", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> GREEN_CLAY = Registry.ITEMS.register("green_clay", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> CYAN_CLAY = Registry.ITEMS.register("cyan_clay", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> LIGHT_BLUE_CLAY = Registry.ITEMS.register("light_blue_clay", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> BLUE_CLAY = Registry.ITEMS.register("blue_clay", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> PURPLE_CLAY = Registry.ITEMS.register("purple_clay", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> MAGENTA_CLAY = Registry.ITEMS.register("magenta_clay", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> PINK_CLAY = Registry.ITEMS.register("pink_clay", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> WHITE_CLAY = Registry.ITEMS.register("white_clay", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> LIGHT_GRAY_CLAY = Registry.ITEMS.register("light_gray_clay", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> GRAY_CLAY = Registry.ITEMS.register("gray_clay", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> BLACK_CLAY = Registry.ITEMS.register("black_clay", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> BROWN_CLAY = Registry.ITEMS.register("brown_clay", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> TERRACOTTA_CLAY = Registry.ITEMS.register("terracotta_clay", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> RED_CLAY_TEAPOT = Registry.ITEMS.register("red_clay_teapot", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> ORANGE_CLAY_TEAPOT = Registry.ITEMS.register("orange_clay_teapot", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> YELLOW_CLAY_TEAPOT = Registry.ITEMS.register("yellow_clay_teapot", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> LIME_CLAY_TEAPOT = Registry.ITEMS.register("lime_clay_teapot", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> WHITE_CLAY_TEAPOT = Registry.ITEMS.register("white_clay_teapot", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> WHITE_CLAY_TEAPLATE = Registry.ITEMS.register("white_clay_teaplate", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> WHITE_CLAY_TEACUP = Registry.ITEMS.register("white_clay_teacup", ()-> new Item(new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
    public static final RegistryObject<Item> WHITE_TERRACOTTA_TEACUP = Registry.ITEMS.register("white_terracotta_teacup", ()-> new TeacupItem(new Item.Properties().tab(Registry.EQUIPMENT_TAB), false, Items.AIR));
    public static final RegistryObject<Item> FILLED_WHITE_TERRACOTTA_TEACUP = Registry.ITEMS.register("filled_white_terracotta_teacup", ()-> new TeacupItem(new Item.Properties().tab(Registry.EQUIPMENT_TAB), true, WHITE_TERRACOTTA_TEACUP.get()));
    public static final RegistryObject<Item> WHITE_TEACUP = Registry.ITEMS.register("white_teacup", ()-> new TeacupItem(new Item.Properties().tab(Registry.EQUIPMENT_TAB), false, Items.AIR));
    public static final RegistryObject<Item> FILLED_WHITE_TEACUP = Registry.ITEMS.register("filled_white_teacup", ()-> new TeacupItem(new Item.Properties().tab(Registry.EQUIPMENT_TAB), true, WHITE_TEACUP.get()));
    public static final RegistryObject<Item> ACOLYTE_SIGN = Registry.ITEMS.register("acolyte_sign", ()-> new SignItem(
            (new Item.Properties()).stacksTo(16).tab(Registry.EQUIPMENT_TAB), BlockRegistry.ACOLYTE_SIGN.get(), BlockRegistry.ACOLYTE_WALL_SIGN.get()));
    public static final RegistryObject<Item> BASTION_SIGN = Registry.ITEMS.register("bastion_sign", ()-> new SignItem(
            (new Item.Properties()).stacksTo(16).tab(Registry.EQUIPMENT_TAB), BlockRegistry.BASTION_SIGN.get(), BlockRegistry.BASTION_WALL_SIGN.get()));
    public static final RegistryObject<Item> VERMILION_SIGN = Registry.ITEMS.register("vermilion_sign", ()-> new SignItem(
            (new Item.Properties()).stacksTo(16).tab(Registry.EQUIPMENT_TAB), BlockRegistry.VERMILION_SIGN.get(), BlockRegistry.VERMILION_WALL_SIGN.get()));
    public static final RegistryObject<Item> WARTWOOD_SIGN = Registry.ITEMS.register("wartwood_sign", ()-> new SignItem(
            (new Item.Properties()).stacksTo(16).tab(Registry.EQUIPMENT_TAB), BlockRegistry.WARTWOOD_SIGN.get(), BlockRegistry.WARTWOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> JUBILEE_SIGN = Registry.ITEMS.register("jubilee_sign", ()-> new SignItem(
            (new Item.Properties()).stacksTo(16).tab(Registry.EQUIPMENT_TAB), BlockRegistry.JUBILEE_SIGN.get(), BlockRegistry.JUBILEE_WALL_SIGN.get()));
    public static final RegistryObject<Item> EVERMORE_SIGN = Registry.ITEMS.register("evermore_sign", ()-> new SignItem(
            (new Item.Properties()).stacksTo(16).tab(Registry.EQUIPMENT_TAB), BlockRegistry.EVERMORE_SIGN.get(), BlockRegistry.EVERMORE_WALL_SIGN.get()));



    public static void register() {

    }

    public static void init() {
        TEACUP_ENTRIES.put(WHITE_TEACUP.get(), FILLED_WHITE_TEACUP.get());
        TEACUP_ENTRIES.put(WHITE_TERRACOTTA_TEACUP.get(), FILLED_WHITE_TERRACOTTA_TEACUP.get());
    }
}
