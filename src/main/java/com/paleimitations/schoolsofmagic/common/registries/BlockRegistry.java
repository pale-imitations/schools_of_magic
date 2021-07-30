package com.paleimitations.schoolsofmagic.common.registries;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.common.blocks.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class BlockRegistry {

    public static final List<HerbalTwineEntry> HERBAL_TWINE_ENTRIES = Lists.newArrayList();
   /* public static final RegistryObject<Block> SILVER_ORE = register("silver_ore", () -> new OreBlock(AbstractBlock.Properties.of(Material.STONE)
            .requiresCorrectToolForDrops().strength(1f, 1f).harvestLevel(2).sound(SoundType.STONE)));
    public static final RegistryObject<Block> SILVER_BLOCK = register("silver_block", () ->
            new Block(AbstractBlock.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1f, 1f).sound(SoundType.METAL)));*/
   public static final RegistryObject<Block> ANDESITE_MORTAR = register("andesite_mortar", () -> new MortarBlock(AbstractBlock.Properties.of(Material.STONE)
           .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> GRANITE_MORTAR = register("granite_mortar", () -> new MortarBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> DIORITE_MORTAR = register("diorite_mortar", () -> new MortarBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> STONE_PODIUM = register("stone_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ANDESITE_PODIUM = register("andesite_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> GRANITE_PODIUM = register("granite_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> DIORITE_PODIUM = register("diorite_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> SANDSTONE_PODIUM = register("sandstone_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> RED_SANDSTONE_PODIUM = register("red_sandstone_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> PRISMARINE_PODIUM = register("prismarine_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> BLACKSTONE_PODIUM = register("blackstone_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.GILDED_BLACKSTONE)));
    public static final RegistryObject<Block> BASALT_PODIUM = register("basalt_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.BASALT)));
    public static final RegistryObject<Block> QUARTZ_PODIUM = register("quartz_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> NETHERBRICK_PODIUM = register("netherbrick_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.NETHER_BRICKS)));
    public static final RegistryObject<Block> ENDSTONE_PODIUM = register("endstone_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> PURPUR_PODIUM = register("purpur_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> OBSIDIAN_PODIUM = register("obsidian_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> HERBAL_TWINE = register("herbal_twine", () -> new HerbalTwineBlock(AbstractBlock.Properties.of(Material.WOOL)
            .strength(1f, 1f).sound(SoundType.GRASS).noCollission().instabreak()));
    public static final RegistryObject<Block> HERBAL_TWINE_POPPY = registerNoItem("herbal_twine_poppy", () -> new HerbalTwineBlock(AbstractBlock.Properties.of(Material.PLANT)
            .strength(1f, 1f).sound(SoundType.GRASS).noCollission().instabreak()));
    public static final RegistryObject<Block> HERBAL_TWINE_CORNFLOWER = registerNoItem("herbal_twine_cornflower", () -> new HerbalTwineBlock(AbstractBlock.Properties.of(Material.PLANT)
            .strength(1f, 1f).sound(SoundType.GRASS).noCollission().instabreak()));
    public static final RegistryObject<Block> HERBAL_TWINE_ALLIUM = registerNoItem("herbal_twine_allium", () -> new HerbalTwineBlock(AbstractBlock.Properties.of(Material.PLANT)
            .strength(1f, 1f).sound(SoundType.GRASS).noCollission().instabreak()));
    public static final RegistryObject<Block> HERBAL_TWINE_DANDELION = registerNoItem("herbal_twine_dandelion", () -> new HerbalTwineBlock(AbstractBlock.Properties.of(Material.PLANT)
            .strength(1f, 1f).sound(SoundType.GRASS).noCollission().instabreak()));
    public static final RegistryObject<Block> HERBAL_TWINE_LILY_OF_THE_VALLEY = registerNoItem("herbal_twine_lily_of_the_valley", () -> new HerbalTwineBlock(AbstractBlock.Properties.of(Material.PLANT)
            .strength(1f, 1f).sound(SoundType.GRASS).noCollission().instabreak()));
    public static final RegistryObject<Block> HERBAL_TWINE_BLUE_ORCHID = registerNoItem("herbal_twine_blue_orchid", () -> new HerbalTwineBlock(AbstractBlock.Properties.of(Material.PLANT)
            .strength(1f, 1f).sound(SoundType.GRASS).noCollission().instabreak()));
    public static final RegistryObject<Block> RED_TERRACOTTA_TEAPOT = register("red_terracotta_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ORANGE_TERRACOTTA_TEAPOT = register("orange_terracotta_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> YELLOW_TERRACOTTA_TEAPOT = register("yellow_terracotta_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> LIME_TERRACOTTA_TEAPOT = register("lime_terracotta_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> WHITE_TERRACOTTA_TEAPOT = register("white_terracotta_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> RED_TEAPOT = register("red_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> ORANGE_TEAPOT = register("orange_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> YELLOW_TEAPOT = register("yellow_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> LIME_TEAPOT = register("lime_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> WHITE_TEAPOT = register("white_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> WHITE_TERRACOTTA_TEAPLATE = register("white_terracotta_teaplate", () -> new TeaplateBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> WHITE_TEAPLATE = register("white_teaplate", () -> new TeaplateBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.GLASS)));

    public static void register() {
    }

    public static void init() {
        HERBAL_TWINE_ENTRIES.add(new HerbalTwineEntry((HerbalTwineBlock)HERBAL_TWINE_POPPY.get(), new ItemStack(Items.POPPY), new ItemStack(ItemRegistry.DRIED_POPPY.get())));
        HERBAL_TWINE_ENTRIES.add(new HerbalTwineEntry((HerbalTwineBlock)HERBAL_TWINE_CORNFLOWER.get(), new ItemStack(Items.CORNFLOWER), new ItemStack(ItemRegistry.DRIED_CORNFLOWER.get())));
        HERBAL_TWINE_ENTRIES.add(new HerbalTwineEntry((HerbalTwineBlock)HERBAL_TWINE_ALLIUM.get(), new ItemStack(Items.ALLIUM), new ItemStack(ItemRegistry.DRIED_ALLIUM.get())));
        HERBAL_TWINE_ENTRIES.add(new HerbalTwineEntry((HerbalTwineBlock)HERBAL_TWINE_DANDELION.get(), new ItemStack(Items.DANDELION), new ItemStack(ItemRegistry.DRIED_DANDELION.get())));
        HERBAL_TWINE_ENTRIES.add(new HerbalTwineEntry((HerbalTwineBlock)HERBAL_TWINE_LILY_OF_THE_VALLEY.get(), new ItemStack(Items.LILY_OF_THE_VALLEY), new ItemStack(ItemRegistry.DRIED_LILY_OF_THE_VALLEY.get())));
        HERBAL_TWINE_ENTRIES.add(new HerbalTwineEntry((HerbalTwineBlock)HERBAL_TWINE_BLUE_ORCHID.get(), new ItemStack(Items.BLUE_ORCHID), new ItemStack(ItemRegistry.DRIED_BLUE_ORCHID.get())));
    }

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return Registry.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerNoItem(name, block);
        Registry.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
        return ret;
    }

}
