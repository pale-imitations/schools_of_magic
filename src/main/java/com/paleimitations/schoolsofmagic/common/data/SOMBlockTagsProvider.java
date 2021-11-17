package com.paleimitations.schoolsofmagic.common.data;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.blocks.HerbalTwineBlock;
import com.paleimitations.schoolsofmagic.common.blocks.MortarBlock;
import com.paleimitations.schoolsofmagic.common.blocks.PodiumBlock;
import com.paleimitations.schoolsofmagic.common.blocks.TeapotBlock;
import com.paleimitations.schoolsofmagic.common.registries.BlockRegistry;
import com.paleimitations.schoolsofmagic.common.registries.TagRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SOMBlockTagsProvider extends BlockTagsProvider {
    private static final Predicate<Block> SOM_BLOCK = b -> References.MODID.equals(Registry.BLOCK.getKey(b).getNamespace());

    public SOMBlockTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, References.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.SLABS).add(getModBlocks(b -> b instanceof SlabBlock));
        tag(BlockTags.STAIRS).add(getModBlocks(b -> b instanceof StairsBlock));
        tag(BlockTags.BUTTONS).add(getModBlocks(b -> b instanceof AbstractButtonBlock));
        tag(BlockTags.WOODEN_BUTTONS).add(getModBlocks(b -> b instanceof WoodButtonBlock));
        tag(BlockTags.DOORS).add(getModBlocks(b -> b instanceof DoorBlock));
        tag(BlockTags.TRAPDOORS).add(getModBlocks(b -> b instanceof TrapDoorBlock));
        tag(BlockTags.FENCES).add(getModBlocks(b -> b instanceof FenceBlock));
        tag(BlockTags.FENCE_GATES).add(getModBlocks(b -> b instanceof FenceGateBlock));
        tag(BlockTags.PRESSURE_PLATES).add(getModBlocks(b -> b instanceof PressurePlateBlock));
        tag(BlockTags.SIGNS).add(getModBlocks(b -> b instanceof AbstractSignBlock));
        tag(BlockTags.WALL_SIGNS).add(getModBlocks(b -> b instanceof WallSignBlock));
        tag(BlockTags.STANDING_SIGNS).add(getModBlocks(b -> b instanceof StandingSignBlock));
        tag(BlockTags.WOODEN_SLABS).add(getModBlocks(b -> b instanceof SlabBlock && b.defaultBlockState().getMaterial() == Material.WOOD));
        tag(BlockTags.WOODEN_STAIRS).add(getModBlocks(b -> b instanceof StairsBlock && b.defaultBlockState().getMaterial() == Material.WOOD));
        tag(BlockTags.WOODEN_DOORS).add(getModBlocks(b -> b instanceof DoorBlock && b.defaultBlockState().getMaterial() == Material.WOOD));
        tag(BlockTags.WOODEN_TRAPDOORS).add(getModBlocks(b -> b instanceof TrapDoorBlock && b.defaultBlockState().getMaterial() == Material.WOOD));
        tag(BlockTags.WOODEN_FENCES).add(getModBlocks(b -> b instanceof FenceBlock && b.defaultBlockState().getMaterial() == Material.WOOD));
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(getModBlocks(b -> b instanceof PressurePlateBlock && b.defaultBlockState().getMaterial() == Material.WOOD));
        tag(BlockTags.PLANKS).add(getModBlocks(b -> b.getRegistryName().getPath().contains("_planks")));
        tag(BlockTags.LOGS).add(getModBlocks(b -> b instanceof RotatedPillarBlock && (b.getRegistryName().getPath().contains("_log") ||
                b.getRegistryName().getPath().contains("_wood"))));
        tag(TagRegistry.Blocks.PODIUMS).add(getModBlocks(b -> b instanceof PodiumBlock));
        tag(TagRegistry.Blocks.MORTARS).add(getModBlocks(b -> b instanceof MortarBlock));
        tag(TagRegistry.Blocks.TWINES).add(getModBlocks(b -> b instanceof HerbalTwineBlock));
        tag(TagRegistry.Blocks.TEAPOTS).add(getModBlocks(b -> b instanceof TeapotBlock));
        tag(TagRegistry.Blocks.ACOLYTE_LOGS).add(BlockRegistry.ACOLYTE_LOG.get(), BlockRegistry.STRIPPED_ACOLYTE_LOG.get(), BlockRegistry.ACOLYTE_WOOD.get(),
                BlockRegistry.STRIPPED_ACOLYTE_WOOD.get());
        tag(TagRegistry.Blocks.BASTION_LOGS).add(BlockRegistry.BASTION_LOG.get(), BlockRegistry.STRIPPED_BASTION_LOG.get(), BlockRegistry.BASTION_WOOD.get(),
                BlockRegistry.STRIPPED_BASTION_WOOD.get());
        tag(TagRegistry.Blocks.VERMILION_LOGS).add(BlockRegistry.VERMILION_LOG.get(), BlockRegistry.STRIPPED_VERMILION_LOG.get(), BlockRegistry.VERMILION_WOOD.get(),
                BlockRegistry.STRIPPED_VERMILION_WOOD.get());
        tag(TagRegistry.Blocks.WARTWOOD_LOGS).add(BlockRegistry.WARTWOOD_LOG.get(), BlockRegistry.STRIPPED_WARTWOOD_LOG.get(), BlockRegistry.WARTWOOD_WOOD.get(),
                BlockRegistry.STRIPPED_WARTWOOD_WOOD.get());
        tag(TagRegistry.Blocks.JUBILEE_LOGS).add(BlockRegistry.JUBILEE_LOG.get(), BlockRegistry.STRIPPED_JUBILEE_LOG.get(), BlockRegistry.JUBILEE_WOOD.get(),
                BlockRegistry.STRIPPED_JUBILEE_WOOD.get());
        tag(TagRegistry.Blocks.EVERMORE_LOGS).add(BlockRegistry.EVERMORE_LOG.get(), BlockRegistry.STRIPPED_EVERMORE_LOG.get(), BlockRegistry.EVERMORE_WOOD.get(),
                BlockRegistry.STRIPPED_EVERMORE_WOOD.get());
    }

    @Nonnull
    private Block[] getModBlocks(Predicate<Block> predicate) {
        return registry.stream().filter(SOM_BLOCK)
                .filter(predicate)
                .sorted(Comparator.comparing(Registry.BLOCK::getKey))
                .toArray(Block[]::new);
    }
}
