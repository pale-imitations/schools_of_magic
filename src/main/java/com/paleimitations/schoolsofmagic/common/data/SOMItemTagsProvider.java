package com.paleimitations.schoolsofmagic.common.data;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import com.paleimitations.schoolsofmagic.common.registries.TagRegistry;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class SOMItemTagsProvider extends ItemTagsProvider {
    public SOMItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagsProvider, References.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        copy(TagRegistry.Blocks.PODIUMS,TagRegistry.Items.PODIUMS);
        copy(TagRegistry.Blocks.MORTARS,TagRegistry.Items.MORTARS);
        copy(TagRegistry.Blocks.TWINES,TagRegistry.Items.TWINES);
        copy(TagRegistry.Blocks.TEAPOTS,TagRegistry.Items.TEAPOTS);
        tag(TagRegistry.Items.WANDS).add(ItemRegistry.APPRENTICE_WAND_1.get(), ItemRegistry.APPRENTICE_WAND_2.get(), ItemRegistry.APPRENTICE_WAND_3.get(),
                ItemRegistry.APPRENTICE_WAND_4.get());
        tag(TagRegistry.Items.UNCOOKED_TEAPOTS).add(ItemRegistry.RED_CLAY_TEAPOT.get(), ItemRegistry.ORANGE_CLAY_TEAPOT.get(), ItemRegistry.YELLOW_CLAY_TEAPOT.get(),
                ItemRegistry.LIME_CLAY_TEAPOT.get(), ItemRegistry.WHITE_CLAY_TEAPOT.get());
        tag(TagRegistry.Items.CLAY_POWDERS).add(ItemRegistry.RED_CLAY_POWDER.get(), ItemRegistry.ORANGE_CLAY_POWDER.get(), ItemRegistry.YELLOW_CLAY_POWDER.get(),
                ItemRegistry.LIME_CLAY_POWDER.get(), ItemRegistry.GREEN_CLAY_POWDER.get(), ItemRegistry.CYAN_CLAY_POWDER.get(), ItemRegistry.LIGHT_BLUE_CLAY_POWDER.get(),
                ItemRegistry.BLUE_CLAY_POWDER.get(), ItemRegistry.PURPLE_CLAY_POWDER.get(), ItemRegistry.MAGENTA_CLAY_POWDER.get(), ItemRegistry.PINK_CLAY_POWDER.get(),
                ItemRegistry.WHITE_CLAY_POWDER.get(), ItemRegistry.LIGHT_GRAY_CLAY_POWDER.get(), ItemRegistry.GRAY_CLAY_POWDER.get(), ItemRegistry.BLACK_CLAY_POWDER.get(),
                ItemRegistry.BROWN_CLAY_POWDER.get(), ItemRegistry.TERRACOTTA_CLAY_POWDER.get());
        tag(TagRegistry.Items.CLAYS).add(ItemRegistry.RED_CLAY.get(), ItemRegistry.ORANGE_CLAY.get(), ItemRegistry.YELLOW_CLAY.get(),
                ItemRegistry.LIME_CLAY.get(), ItemRegistry.GREEN_CLAY.get(), ItemRegistry.CYAN_CLAY.get(), ItemRegistry.LIGHT_BLUE_CLAY.get(),
                ItemRegistry.BLUE_CLAY.get(), ItemRegistry.PURPLE_CLAY.get(), ItemRegistry.MAGENTA_CLAY.get(), ItemRegistry.PINK_CLAY.get(),
                ItemRegistry.WHITE_CLAY.get(), ItemRegistry.LIGHT_GRAY_CLAY.get(), ItemRegistry.GRAY_CLAY.get(), ItemRegistry.BLACK_CLAY.get(),
                ItemRegistry.BROWN_CLAY.get(), ItemRegistry.TERRACOTTA_CLAY.get());
        tag(TagRegistry.Items.FILLED_CUPS).add(ItemRegistry.FILLED_WHITE_TERRACOTTA_TEACUP.get(), ItemRegistry.FILLED_WHITE_TEACUP.get());
    }
}
