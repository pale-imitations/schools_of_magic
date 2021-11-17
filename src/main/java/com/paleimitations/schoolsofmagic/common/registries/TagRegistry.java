package com.paleimitations.schoolsofmagic.common.registries;

import com.paleimitations.schoolsofmagic.References;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class TagRegistry {
    public static final class Blocks {
        public static final ITag.INamedTag<Block> PODIUMS = mod("podiums");
        public static final ITag.INamedTag<Block> MORTARS = mod("mortars");
        public static final ITag.INamedTag<Block> TWINES = mod("twines");
        public static final ITag.INamedTag<Block> TEAPOTS = mod("teapots");
        public static final ITag.INamedTag<Block> ACOLYTE_LOGS = mod("acolyte_logs");
        public static final ITag.INamedTag<Block> BASTION_LOGS = mod("bastion_logs");
        public static final ITag.INamedTag<Block> VERMILION_LOGS = mod("vermilion_logs");
        public static final ITag.INamedTag<Block> WARTWOOD_LOGS = mod("wartwood_logs");
        public static final ITag.INamedTag<Block> JUBILEE_LOGS = mod("jubilee_logs");
        public static final ITag.INamedTag<Block> EVERMORE_LOGS = mod("evermore_logs");

        private static ITag.INamedTag<Block> forge(String path) {
            return BlockTags.createOptional(new ResourceLocation("forge", path));
        }

        private static ITag.INamedTag<Block> mod(String path) {
            return BlockTags.bind(new ResourceLocation(References.MODID, path).toString());
        }
    }

    public static final class Items {
        public static final ITag.INamedTag<Item> WANDS = mod("wands");
        public static final ITag.INamedTag<Item> PODIUMS = mod("podiums");
        public static final ITag.INamedTag<Item> MORTARS = mod("mortars");
        public static final ITag.INamedTag<Item> TWINES = mod("twines");
        public static final ITag.INamedTag<Item> TEAPOTS = mod("teapots");
        public static final ITag.INamedTag<Item> ACOLYTE_LOGS = mod("acolyte_logs");
        public static final ITag.INamedTag<Item> BASTION_LOGS = mod("bastion_logs");
        public static final ITag.INamedTag<Item> VERMILION_LOGS = mod("vermilion_logs");
        public static final ITag.INamedTag<Item> WARTWOOD_LOGS = mod("wartwood_logs");
        public static final ITag.INamedTag<Item> JUBILEE_LOGS = mod("jubilee_logs");
        public static final ITag.INamedTag<Item> EVERMORE_LOGS = mod("evermore_logs");
        public static final ITag.INamedTag<Item> UNCOOKED_TEAPOTS = mod("uncooked_teapots");
        public static final ITag.INamedTag<Item> CLAY_POWDERS = mod("clay_powders");
        public static final ITag.INamedTag<Item> CLAYS = mod("clays");
        public static final ITag.INamedTag<Item> FILLED_CUPS = mod("filled_cups");

        private static ITag.INamedTag<Item> forge(String path) {
            return ItemTags.createOptional(new ResourceLocation("forge", path));
        }

        private static ITag.INamedTag<Item> mod(String path) {
            return ItemTags.bind(new ResourceLocation(References.MODID, path).toString());
        }
    }
}
