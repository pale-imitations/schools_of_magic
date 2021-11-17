package com.paleimitations.schoolsofmagic.common.data;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.items.TeacupItem;
import com.paleimitations.schoolsofmagic.common.items.WandBaseItem;
import com.paleimitations.schoolsofmagic.common.registries.BlockRegistry;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import com.paleimitations.schoolsofmagic.common.registries.TagRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.function.Predicate;

public class SOMItemTagsProvider extends ItemTagsProvider {
    private static final Predicate<Item> SOM_ITEM = b -> References.MODID.equals(Registry.ITEM.getKey(b).getNamespace());

    public SOMItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagsProvider, References.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        copy(TagRegistry.Blocks.PODIUMS,TagRegistry.Items.PODIUMS);
        copy(TagRegistry.Blocks.MORTARS,TagRegistry.Items.MORTARS);
        copy(TagRegistry.Blocks.TWINES,TagRegistry.Items.TWINES);
        copy(TagRegistry.Blocks.TEAPOTS,TagRegistry.Items.TEAPOTS);
        copy(TagRegistry.Blocks.ACOLYTE_LOGS,TagRegistry.Items.ACOLYTE_LOGS);
        copy(TagRegistry.Blocks.BASTION_LOGS,TagRegistry.Items.BASTION_LOGS);
        copy(TagRegistry.Blocks.EVERMORE_LOGS,TagRegistry.Items.EVERMORE_LOGS);
        copy(TagRegistry.Blocks.JUBILEE_LOGS,TagRegistry.Items.JUBILEE_LOGS);
        copy(TagRegistry.Blocks.VERMILION_LOGS,TagRegistry.Items.VERMILION_LOGS);
        copy(TagRegistry.Blocks.WARTWOOD_LOGS,TagRegistry.Items.WARTWOOD_LOGS);
        tag(ItemTags.SIGNS).add(getModItems(b -> b instanceof SignItem));

        /*tag(ItemTags.SLABS).add(getModItems(b -> b instanceof SlabBlock));
        tag(ItemTags.STAIRS).add(getModItems(b -> b instanceof StairsBlock));
        tag(ItemTags.BUTTONS).add(getModItems(b -> b instanceof AbstractButtonBlock));
        tag(ItemTags.WOODEN_BUTTONS).add(getModItems(b -> b instanceof WoodButtonBlock));
        tag(ItemTags.DOORS).add(getModItems(b -> b instanceof DoorBlock));
        tag(ItemTags.TRAPDOORS).add(getModItems(b -> b instanceof TrapDoorBlock));
        tag(ItemTags.FENCES).add(getModItems(b -> b instanceof FenceBlock));
        tag(ItemTags.WOODEN_SLABS).add(getModItems(b -> b instanceof SlabBlock && b.defaultBlockState().getMaterial() == Material.WOOD));
        tag(ItemTags.WOODEN_STAIRS).add(getModItems(b -> b instanceof StairsBlock && b.defaultBlockState().getMaterial() == Material.WOOD));
        tag(ItemTags.WOODEN_DOORS).add(getModItems(b -> b instanceof DoorBlock && b.defaultBlockState().getMaterial() == Material.WOOD));
        tag(ItemTags.WOODEN_TRAPDOORS).add(getModItems(b -> b instanceof TrapDoorBlock && b.defaultBlockState().getMaterial() == Material.WOOD));
        tag(ItemTags.WOODEN_FENCES).add(getModItems(b -> b instanceof FenceBlock && b.defaultBlockState().getMaterial() == Material.WOOD));
        tag(ItemTags.PLANKS).add(getModItems(b -> b.getRegistryName().getPath().contains("_planks")));
        tag(ItemTags.LOGS).add(getModItems(b -> b instanceof RotatedPillarBlock && (b.getRegistryName().getPath().contains("_log") ||
                b.getRegistryName().getPath().contains("_wood"))));*/

        tag(TagRegistry.Items.WANDS).add(getModItems(i -> i instanceof WandBaseItem));
        tag(TagRegistry.Items.UNCOOKED_TEAPOTS).add(getModItems(i -> i.getRegistryName().getPath().contains("_clay_teapot")));
        tag(TagRegistry.Items.CLAY_POWDERS).add(getModItems(i -> i.getRegistryName().getPath().contains("_clay_powder")));
        tag(TagRegistry.Items.CLAYS).add(getModItems(i -> i.getRegistryName().getPath().endsWith("_clay")));
        tag(TagRegistry.Items.FILLED_CUPS).add(getModItems(i -> i instanceof TeacupItem && i.getRegistryName().getPath().contains("filled_")));
    }

    @Nonnull
    private Item[] getModItems(Predicate<Item> predicate) {
        return registry.stream().filter(SOM_ITEM)
                .filter(predicate)
                .sorted(Comparator.comparing(Registry.ITEM::getKey))
                .toArray(Item[]::new);
    }
}
