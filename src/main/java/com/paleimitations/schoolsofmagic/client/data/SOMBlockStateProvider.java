package com.paleimitations.schoolsofmagic.client.data;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.blocks.HerbalTwineBlock;
import com.paleimitations.schoolsofmagic.common.blocks.TeaplateBlock;
import com.paleimitations.schoolsofmagic.common.registries.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

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
        //simpleBlock(BlockRegistry.ACOLYTE_LEAVES.get(), models().cubeAll("acolyte_leaves", new ResourceLocation(References.MODID, "block/acolyte_leaves")));

        planks(BlockRegistry.ACOLYTE_PLANKS.get(), BlockRegistry.ACOLYTE_STAIRS.get(), BlockRegistry.ACOLYTE_SLAB.get(), BlockRegistry.ACOLYTE_FENCE.get(), BlockRegistry.ACOLYTE_FENCE_GATE.get());
        planks(BlockRegistry.BASTION_PLANKS.get(), BlockRegistry.BASTION_STAIRS.get(), BlockRegistry.BASTION_SLAB.get(), BlockRegistry.BASTION_FENCE.get(), BlockRegistry.BASTION_FENCE_GATE.get());
        planks(BlockRegistry.VERMILION_PLANKS.get(), BlockRegistry.VERMILION_STAIRS.get(), BlockRegistry.VERMILION_SLAB.get(), BlockRegistry.VERMILION_FENCE.get(), BlockRegistry.VERMILION_FENCE_GATE.get());
        planks(BlockRegistry.WARTWOOD_PLANKS.get(), BlockRegistry.WARTWOOD_STAIRS.get(), BlockRegistry.WARTWOOD_SLAB.get(), BlockRegistry.WARTWOOD_FENCE.get(), BlockRegistry.WARTWOOD_FENCE_GATE.get());
        planks(BlockRegistry.EVERMORE_PLANKS.get(), BlockRegistry.EVERMORE_STAIRS.get(), BlockRegistry.EVERMORE_SLAB.get(), BlockRegistry.EVERMORE_FENCE.get(), BlockRegistry.EVERMORE_FENCE_GATE.get());
        planks(BlockRegistry.JUBILEE_PLANKS.get(), BlockRegistry.JUBILEE_STAIRS.get(), BlockRegistry.JUBILEE_SLAB.get(), BlockRegistry.JUBILEE_FENCE.get(), BlockRegistry.JUBILEE_FENCE_GATE.get());

        wood(BlockRegistry.ACOLYTE_LOG.get(), BlockRegistry.STRIPPED_ACOLYTE_LOG.get(), BlockRegistry.ACOLYTE_WOOD.get(), BlockRegistry.STRIPPED_ACOLYTE_WOOD.get());
        wood(BlockRegistry.BASTION_LOG.get(), BlockRegistry.STRIPPED_BASTION_LOG.get(), BlockRegistry.BASTION_WOOD.get(), BlockRegistry.STRIPPED_BASTION_WOOD.get());
        wood(BlockRegistry.VERMILION_LOG.get(), BlockRegistry.STRIPPED_VERMILION_LOG.get(), BlockRegistry.VERMILION_WOOD.get(), BlockRegistry.STRIPPED_VERMILION_WOOD.get());
        wood(BlockRegistry.WARTWOOD_LOG.get(), BlockRegistry.STRIPPED_WARTWOOD_LOG.get(), BlockRegistry.WARTWOOD_WOOD.get(), BlockRegistry.STRIPPED_WARTWOOD_WOOD.get());
        wood(BlockRegistry.JUBILEE_LOG.get(), BlockRegistry.STRIPPED_JUBILEE_LOG.get(), BlockRegistry.JUBILEE_WOOD.get(), BlockRegistry.STRIPPED_JUBILEE_WOOD.get());
        wood(BlockRegistry.EVERMORE_LOG.get(), BlockRegistry.STRIPPED_EVERMORE_LOG.get(), BlockRegistry.EVERMORE_WOOD.get(), BlockRegistry.STRIPPED_EVERMORE_WOOD.get());

        door(BlockRegistry.ACOLYTE_DOOR.get(), BlockRegistry.ACOLYTE_TRAPDOOR.get(), true);
        door(BlockRegistry.BASTION_DOOR.get(), BlockRegistry.BASTION_TRAPDOOR.get(), true);
        door(BlockRegistry.VERMILION_DOOR.get(), BlockRegistry.VERMILION_TRAPDOOR.get(), true);
        door(BlockRegistry.JUBILEE_DOOR.get(), BlockRegistry.JUBILEE_TRAPDOOR.get(), true);
        door(BlockRegistry.WARTWOOD_DOOR.get(), BlockRegistry.WARTWOOD_TRAPDOOR.get(), true);
        door(BlockRegistry.EVERMORE_DOOR.get(), BlockRegistry.EVERMORE_TRAPDOOR.get(), true);

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

    public void planks(Block planks, StairsBlock stairs, SlabBlock slab, FenceBlock fence, FenceGateBlock gate) {
        String planksName = planks.getRegistryName().getPath();
        simpleBlock(planks, models().cubeAll(planks.getRegistryName().getPath(), new ResourceLocation(References.MODID,"block/"+planksName)));
        stairsBlock(stairs, new ResourceLocation(References.MODID,"block/"+planksName));
        slabBlock(slab, new ResourceLocation(References.MODID,"block/"+planksName), new ResourceLocation(References.MODID,"block/"+planksName));
        fence(fence, gate, planksName);
    }

    public void wood(RotatedPillarBlock log, RotatedPillarBlock strippedLog, RotatedPillarBlock wood, RotatedPillarBlock strippedWood) {
        logBlock(log);
        logBlock(strippedLog);
        woodBlock(wood, log.getRegistryName().getPath());
        woodBlock(strippedWood, strippedLog.getRegistryName().getPath());
    }

    public void door(DoorBlock door, TrapDoorBlock trapdoor, boolean orientable) {
        String doorName = door.getRegistryName().getPath();
        doorBlock(door, new ResourceLocation(References.MODID,"block/"+doorName+"_bottom"),
                new ResourceLocation(References.MODID,"block/"+doorName+"_top"));
        trapdoorBlock(trapdoor, new ResourceLocation(References.MODID,"block/"+trapdoor.getRegistryName().getPath()), orientable);
    }

    public void fence(FenceBlock fence, FenceGateBlock gate, String plank) {
        models().fenceInventory(fence.getRegistryName().getPath()+"_inventory", new ResourceLocation(References.MODID,"block/"+plank));
        fenceBlock(fence, new ResourceLocation(References.MODID,"block/"+plank));
        fenceGateBlock(gate, new ResourceLocation(References.MODID,"block/"+plank));
    }

    public void herbalTwine(HerbalTwineBlock block, ModelFile twine, ModelFile undried, ModelFile dried) {
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block).part().modelFile(twine).addModel().end();
        builder.part().modelFile(undried).rotationX(180).addModel().condition(HerbalTwineBlock.AGE, 0,1,2,3,4);
        builder.part().modelFile(dried).rotationX(180).addModel().condition(HerbalTwineBlock.AGE, 5);
    }

    public void woodBlock(RotatedPillarBlock block, String name) {
        axisBlock(block, new ResourceLocation(References.MODID,"block/"+name), new ResourceLocation(References.MODID,"block/"+name));
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
