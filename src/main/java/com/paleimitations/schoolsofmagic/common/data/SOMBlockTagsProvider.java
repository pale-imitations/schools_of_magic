package com.paleimitations.schoolsofmagic.common.data;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.registries.BlockRegistry;
import com.paleimitations.schoolsofmagic.common.registries.TagRegistry;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class SOMBlockTagsProvider extends BlockTagsProvider {

    public SOMBlockTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, References.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(TagRegistry.Blocks.PODIUMS).add(BlockRegistry.SANDSTONE_PODIUM.get(), BlockRegistry.RED_SANDSTONE_PODIUM.get(), BlockRegistry.BLACKSTONE_PODIUM.get(),
                BlockRegistry.BASALT_PODIUM.get(), BlockRegistry.ENDSTONE_PODIUM.get(), BlockRegistry.PURPUR_PODIUM.get(), BlockRegistry.OBSIDIAN_PODIUM.get(),
                BlockRegistry.QUARTZ_PODIUM.get(), BlockRegistry.ANDESITE_PODIUM.get(), BlockRegistry.GRANITE_PODIUM.get(), BlockRegistry.DIORITE_PODIUM.get(),
                BlockRegistry.PRISMARINE_PODIUM.get(), BlockRegistry.NETHERBRICK_PODIUM.get(), BlockRegistry.STONE_PODIUM.get());
        tag(TagRegistry.Blocks.MORTARS).add(BlockRegistry.ANDESITE_MORTAR.get(), BlockRegistry.GRANITE_MORTAR.get(), BlockRegistry.DIORITE_MORTAR.get());
        tag(TagRegistry.Blocks.TWINES).add(BlockRegistry.HERBAL_TWINE_POPPY.get(),BlockRegistry.HERBAL_TWINE_CORNFLOWER.get(),BlockRegistry.HERBAL_TWINE_ALLIUM.get(),
                BlockRegistry.HERBAL_TWINE_DANDELION.get(), BlockRegistry.HERBAL_TWINE_LILY_OF_THE_VALLEY.get(), BlockRegistry.HERBAL_TWINE_BLUE_ORCHID.get());
        tag(TagRegistry.Blocks.TEAPOTS).add(BlockRegistry.RED_TEAPOT.get(),BlockRegistry.ORANGE_TEAPOT.get(), BlockRegistry.YELLOW_TEAPOT.get(),
                BlockRegistry.LIME_TEAPOT.get(), BlockRegistry.WHITE_TEAPOT.get(),
                BlockRegistry.RED_TERRACOTTA_TEAPOT.get(),BlockRegistry.ORANGE_TERRACOTTA_TEAPOT.get(), BlockRegistry.YELLOW_TERRACOTTA_TEAPOT.get(),
                BlockRegistry.LIME_TERRACOTTA_TEAPOT.get(), BlockRegistry.WHITE_TERRACOTTA_TEAPOT.get());

    }
}
