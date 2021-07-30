package com.paleimitations.schoolsofmagic.client.data;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.blocks.HerbalTwineBlock;
import com.paleimitations.schoolsofmagic.common.blocks.TeaplateBlock;
import com.paleimitations.schoolsofmagic.common.registries.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class SOMBlockStateProvider extends BlockStateProvider {

    private ExistingFileHelper existingFileHelper;

    public SOMBlockStateProvider(DataGenerator dg, ExistingFileHelper fh) {
        super(dg, References.MODID, fh);
        this.existingFileHelper = fh;
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile andesite_mortar = new ModelFile.ExistingModelFile(new ResourceLocation(References.MODID, "block/andesite_mortar"), this.existingFileHelper);
        ModelFile diorite_mortar = new ModelFile.ExistingModelFile(new ResourceLocation(References.MODID, "block/diorite_mortar"), this.existingFileHelper);
        ModelFile granite_mortar = new ModelFile.ExistingModelFile(new ResourceLocation(References.MODID, "block/granite_mortar"), this.existingFileHelper);
        ModelFile herbal_twine = new ModelFile.ExistingModelFile(new ResourceLocation(References.MODID, "block/herbal_twine"), this.existingFileHelper);
        simpleBlock(BlockRegistry.ANDESITE_MORTAR.get(), andesite_mortar);
        simpleBlock(BlockRegistry.DIORITE_MORTAR.get(), diorite_mortar);
        simpleBlock(BlockRegistry.GRANITE_MORTAR.get(), granite_mortar);
        horizontalBlock(BlockRegistry.RED_TEAPOT.get(), new ModelFile.ExistingModelFile(new ResourceLocation(References.MODID, "block/red_teapot"), this.existingFileHelper));
        horizontalBlock(BlockRegistry.ORANGE_TEAPOT.get(), new ModelFile.ExistingModelFile(new ResourceLocation(References.MODID, "block/orange_teapot"), this.existingFileHelper));
        horizontalBlock(BlockRegistry.YELLOW_TEAPOT.get(), new ModelFile.ExistingModelFile(new ResourceLocation(References.MODID, "block/yellow_teapot"), this.existingFileHelper));
        horizontalBlock(BlockRegistry.LIME_TEAPOT.get(), new ModelFile.ExistingModelFile(new ResourceLocation(References.MODID, "block/lime_teapot"), this.existingFileHelper));
        horizontalBlock(BlockRegistry.WHITE_TEAPOT.get(), new ModelFile.ExistingModelFile(new ResourceLocation(References.MODID, "block/white_teapot"), this.existingFileHelper));
        horizontalBlock(BlockRegistry.RED_TERRACOTTA_TEAPOT.get(), new ModelFile.ExistingModelFile(new ResourceLocation(References.MODID, "block/red_terracotta_teapot"), this.existingFileHelper));
        horizontalBlock(BlockRegistry.ORANGE_TERRACOTTA_TEAPOT.get(), new ModelFile.ExistingModelFile(new ResourceLocation(References.MODID, "block/orange_terracotta_teapot"), this.existingFileHelper));
        horizontalBlock(BlockRegistry.YELLOW_TERRACOTTA_TEAPOT.get(), new ModelFile.ExistingModelFile(new ResourceLocation(References.MODID, "block/yellow_terracotta_teapot"), this.existingFileHelper));
        horizontalBlock(BlockRegistry.LIME_TERRACOTTA_TEAPOT.get(), new ModelFile.ExistingModelFile(new ResourceLocation(References.MODID, "block/lime_terracotta_teapot"), this.existingFileHelper));
        horizontalBlock(BlockRegistry.WHITE_TERRACOTTA_TEAPOT.get(), new ModelFile.ExistingModelFile(new ResourceLocation(References.MODID, "block/white_terracotta_teapot"), this.existingFileHelper));
        simpleBlock(BlockRegistry.HERBAL_TWINE.get(), herbal_twine);
        herbalTwine((HerbalTwineBlock) BlockRegistry.HERBAL_TWINE_POPPY.get(), herbal_twine,
                new ModelFile.ExistingModelFile(new ResourceLocation("block/poppy"), this.existingFileHelper),
                models().cross("dried_poppy", new ResourceLocation(References.MODID,"block/dried_poppy")));
        herbalTwine((HerbalTwineBlock) BlockRegistry.HERBAL_TWINE_CORNFLOWER.get(), herbal_twine,
                new ModelFile.ExistingModelFile(new ResourceLocation("block/cornflower"), this.existingFileHelper),
                models().cross("dried_cornflower", new ResourceLocation(References.MODID,"block/dried_cornflower")));
        herbalTwine((HerbalTwineBlock) BlockRegistry.HERBAL_TWINE_ALLIUM.get(), herbal_twine,
                new ModelFile.ExistingModelFile(new ResourceLocation("block/allium"), this.existingFileHelper),
                models().cross("dried_allium", new ResourceLocation(References.MODID,"block/dried_allium")));
        herbalTwine((HerbalTwineBlock) BlockRegistry.HERBAL_TWINE_DANDELION.get(), herbal_twine,
                new ModelFile.ExistingModelFile(new ResourceLocation("block/dandelion"), this.existingFileHelper),
                models().cross("dried_dandelion", new ResourceLocation(References.MODID,"block/dried_dandelion")));
        herbalTwine((HerbalTwineBlock) BlockRegistry.HERBAL_TWINE_LILY_OF_THE_VALLEY.get(), herbal_twine,
                new ModelFile.ExistingModelFile(new ResourceLocation("block/lily_of_the_valley"), this.existingFileHelper),
                models().cross("dried_lily_of_the_valley", new ResourceLocation(References.MODID,"block/dried_lily_of_the_valley")));
        herbalTwine((HerbalTwineBlock) BlockRegistry.HERBAL_TWINE_BLUE_ORCHID.get(), herbal_twine,
                new ModelFile.ExistingModelFile(new ResourceLocation("block/blue_orchid"), this.existingFileHelper),
                models().cross("dried_blue_orchid", new ResourceLocation(References.MODID,"block/dried_blue_orchid")));
        countBlock((TeaplateBlock) BlockRegistry.WHITE_TEAPLATE.get(), "white_teaplate");
        countBlock((TeaplateBlock) BlockRegistry.WHITE_TERRACOTTA_TEAPLATE.get(), "white_terracotta_teaplate");
        //simpleBlock(BlockRegistry.SILVER_BLOCK.get());
        //simpleBlock(BlockRegistry.SILVER_ORE.get());
        /*ModelFile sandstone_podium = new ModelFile.ExistingModelFile(new ResourceLocation(References.MODID, "block/sandstone_podium"), this.existingFileHelper);
        horizontalBlock(BlockRegistry.SANDSTONE_PODIUM.get(), sandstone_podium);
        ModelFile red_sandstone_podium = new ModelFile.ExistingModelFile(new ResourceLocation(References.MODID, "block/red_sandstone_podium"), this.existingFileHelper);
        horizontalBlock(BlockRegistry.RED_SANDSTONE_PODIUM.get(), red_sandstone_podium);*/

    }

    public void herbalTwine(HerbalTwineBlock block, ModelFile twine, ModelFile undried, ModelFile dried) {
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block).part().modelFile(twine).addModel().end();
        builder.part().modelFile(undried).rotationX(180).addModel().condition(HerbalTwineBlock.AGE, 0,1,2,3,4);
        builder.part().modelFile(dried).rotationX(180).addModel().condition(HerbalTwineBlock.AGE, 5);
    }

    public void countBlock(TeaplateBlock block, String name) {
        getVariantBuilder(block)
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(new ModelFile.ExistingModelFile(new ResourceLocation(References.MODID,"block/"+name+"_"+state.getValue(TeaplateBlock.COUNT)),
                                this.existingFileHelper))
                        .build()
                );
    }
}
