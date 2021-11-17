package com.paleimitations.schoolsofmagic.client.data;

import com.paleimitations.schoolsofmagic.References;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SOMItemModelProvider extends ItemModelProvider {

    public SOMItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, References.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //withExistingParent("silver_block", modLoc("block/silver_block"));
        //withExistingParent("silver_ore", modLoc("block/silver_ore"));

        withExistingParent("acolyte_planks", modLoc("block/acolyte_planks"));
        withExistingParent("vermilion_planks", modLoc("block/vermilion_planks"));
        withExistingParent("bastion_planks", modLoc("block/bastion_planks"));
        withExistingParent("evermore_planks", modLoc("block/evermore_planks"));
        withExistingParent("wartwood_planks", modLoc("block/wartwood_planks"));
        withExistingParent("jubilee_planks", modLoc("block/jubilee_planks"));

        withExistingParent("acolyte_stairs", modLoc("block/acolyte_stairs"));
        withExistingParent("vermilion_stairs", modLoc("block/vermilion_stairs"));
        withExistingParent("bastion_stairs", modLoc("block/bastion_stairs"));
        withExistingParent("evermore_stairs", modLoc("block/evermore_stairs"));
        withExistingParent("wartwood_stairs", modLoc("block/wartwood_stairs"));
        withExistingParent("jubilee_stairs", modLoc("block/jubilee_stairs"));

        withExistingParent("acolyte_slab", modLoc("block/acolyte_slab"));
        withExistingParent("vermilion_slab", modLoc("block/vermilion_slab"));
        withExistingParent("bastion_slab", modLoc("block/bastion_slab"));
        withExistingParent("evermore_slab", modLoc("block/evermore_slab"));
        withExistingParent("wartwood_slab", modLoc("block/wartwood_slab"));
        withExistingParent("jubilee_slab", modLoc("block/jubilee_slab"));

        withExistingParent("acolyte_fence", modLoc("block/acolyte_fence_inventory"));
        withExistingParent("vermilion_fence", modLoc("block/vermilion_fence_inventory"));
        withExistingParent("bastion_fence", modLoc("block/bastion_fence_inventory"));
        withExistingParent("evermore_fence", modLoc("block/evermore_fence_inventory"));
        withExistingParent("wartwood_fence", modLoc("block/wartwood_fence_inventory"));
        withExistingParent("jubilee_fence", modLoc("block/jubilee_fence_inventory"));

        withExistingParent("acolyte_fence_gate", modLoc("block/acolyte_fence_gate"));
        withExistingParent("vermilion_fence_gate", modLoc("block/vermilion_fence_gate"));
        withExistingParent("bastion_fence_gate", modLoc("block/bastion_fence_gate"));
        withExistingParent("evermore_fence_gate", modLoc("block/evermore_fence_gate"));
        withExistingParent("wartwood_fence_gate", modLoc("block/wartwood_fence_gate"));
        withExistingParent("jubilee_fence_gate", modLoc("block/jubilee_fence_gate"));

        withExistingParent("acolyte_trapdoor", modLoc("block/acolyte_trapdoor_bottom"));
        withExistingParent("vermilion_trapdoor", modLoc("block/vermilion_trapdoor_bottom"));
        withExistingParent("bastion_trapdoor", modLoc("block/bastion_trapdoor_bottom"));
        withExistingParent("evermore_trapdoor", modLoc("block/evermore_trapdoor_bottom"));
        withExistingParent("wartwood_trapdoor", modLoc("block/wartwood_trapdoor_bottom"));
        withExistingParent("jubilee_trapdoor", modLoc("block/jubilee_trapdoor_bottom"));

        withExistingParent("acolyte_pressure_plate", modLoc("block/acolyte_pressure_plate"));
        withExistingParent("vermilion_pressure_plate", modLoc("block/vermilion_pressure_plate"));
        withExistingParent("bastion_pressure_plate", modLoc("block/bastion_pressure_plate"));
        withExistingParent("evermore_pressure_plate", modLoc("block/evermore_pressure_plate"));
        withExistingParent("wartwood_pressure_plate", modLoc("block/wartwood_pressure_plate"));
        withExistingParent("jubilee_pressure_plate", modLoc("block/jubilee_pressure_plate"));

        withExistingParent("acolyte_planks", modLoc("block/acolyte_planks"));
        withExistingParent("vermilion_planks", modLoc("block/vermilion_planks"));
        withExistingParent("bastion_planks", modLoc("block/bastion_planks"));
        withExistingParent("evermore_planks", modLoc("block/evermore_planks"));
        withExistingParent("wartwood_planks", modLoc("block/wartwood_planks"));
        withExistingParent("jubilee_planks", modLoc("block/jubilee_planks"));

        withExistingParent("acolyte_log", modLoc("block/acolyte_log"));
        withExistingParent("stripped_acolyte_log", modLoc("block/stripped_acolyte_log"));
        withExistingParent("acolyte_wood", modLoc("block/acolyte_wood"));
        withExistingParent("stripped_acolyte_wood", modLoc("block/stripped_acolyte_wood"));

        withExistingParent("bastion_log", modLoc("block/bastion_log"));
        withExistingParent("stripped_bastion_log", modLoc("block/stripped_bastion_log"));
        withExistingParent("bastion_wood", modLoc("block/bastion_wood"));
        withExistingParent("stripped_bastion_wood", modLoc("block/stripped_bastion_wood"));

        withExistingParent("vermilion_log", modLoc("block/vermilion_log"));
        withExistingParent("stripped_vermilion_log", modLoc("block/stripped_vermilion_log"));
        withExistingParent("vermilion_wood", modLoc("block/vermilion_wood"));
        withExistingParent("stripped_vermilion_wood", modLoc("block/stripped_vermilion_wood"));

        withExistingParent("evermore_log", modLoc("block/evermore_log"));
        withExistingParent("stripped_evermore_log", modLoc("block/stripped_evermore_log"));
        withExistingParent("evermore_wood", modLoc("block/evermore_wood"));
        withExistingParent("stripped_evermore_wood", modLoc("block/stripped_evermore_wood"));

        withExistingParent("wartwood_log", modLoc("block/wartwood_log"));
        withExistingParent("stripped_wartwood_log", modLoc("block/stripped_wartwood_log"));
        withExistingParent("wartwood_wood", modLoc("block/wartwood_wood"));
        withExistingParent("stripped_wartwood_wood", modLoc("block/stripped_wartwood_wood"));

        withExistingParent("jubilee_log", modLoc("block/jubilee_log"));
        withExistingParent("stripped_jubilee_log", modLoc("block/stripped_jubilee_log"));
        withExistingParent("jubilee_wood", modLoc("block/jubilee_wood"));
        withExistingParent("stripped_jubilee_wood", modLoc("block/stripped_jubilee_wood"));

        withExistingParent("red_sandstone_podium", modLoc("block/red_sandstone_podium"));
        withExistingParent("sandstone_podium", modLoc("block/sandstone_podium"));
        withExistingParent("basalt_podium", modLoc("block/basalt_podium"));
        withExistingParent("blackstone_podium", modLoc("block/blackstone_podium"));
        withExistingParent("endstone_podium", modLoc("block/endstone_podium"));
        withExistingParent("purpur_podium", modLoc("block/purpur_podium"));
        withExistingParent("stone_podium", modLoc("block/stone_podium"));
        withExistingParent("granite_podium", modLoc("block/granite_podium"));
        withExistingParent("andesite_podium", modLoc("block/andesite_podium"));
        withExistingParent("diorite_podium", modLoc("block/diorite_podium"));
        withExistingParent("prismarine_podium", modLoc("block/prismarine_podium"));
        withExistingParent("obsidian_podium", modLoc("block/obsidian_podium"));
        withExistingParent("netherbrick_podium", modLoc("block/netherbrick_podium"));
        withExistingParent("quartz_podium", modLoc("block/quartz_podium"));

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        ModelFile itemHandheld = getExistingFile(mcLoc("item/handheld"));
        builder(itemGenerated, "quest_note");
        builder(itemGenerated, "letter_ccw");
        builder(itemGenerated,"andesite_mortar");
        builder(itemGenerated,"diorite_mortar");
        builder(itemGenerated,"granite_mortar");
        builder(itemGenerated,"herbal_twine");
        builder(itemGenerated,"poppy_seeds");
        builder(itemGenerated,"dried_poppy");
        builder(itemGenerated,"poppy_seed_muffin");
        builder(itemGenerated,"dried_cornflower");
        builder(itemGenerated,"crushed_cornflower");
        builder(itemGenerated,"dried_allium");
        builder(itemGenerated,"crushed_allium");
        builder(itemGenerated,"dried_dandelion");
        builder(itemGenerated,"crushed_dandelion");
        builder(itemGenerated,"dried_lily_of_the_valley");
        builder(itemGenerated,"crushed_lily_of_the_valley");
        builder(itemGenerated,"dried_blue_orchid");
        builder(itemGenerated,"crushed_blue_orchid");
        builder(itemGenerated,"red_clay_powder");
        builder(itemGenerated,"orange_clay_powder");
        builder(itemGenerated,"yellow_clay_powder");
        builder(itemGenerated,"lime_clay_powder");
        builder(itemGenerated,"green_clay_powder");
        builder(itemGenerated,"cyan_clay_powder");
        builder(itemGenerated,"light_blue_clay_powder");
        builder(itemGenerated,"blue_clay_powder");
        builder(itemGenerated,"purple_clay_powder");
        builder(itemGenerated,"magenta_clay_powder");
        builder(itemGenerated,"pink_clay_powder");
        builder(itemGenerated,"white_clay_powder");
        builder(itemGenerated,"light_gray_clay_powder");
        builder(itemGenerated,"gray_clay_powder");
        builder(itemGenerated,"black_clay_powder");
        builder(itemGenerated,"brown_clay_powder");
        builder(itemGenerated,"terracotta_clay_powder");
        builder(itemGenerated,"red_clay");
        builder(itemGenerated,"orange_clay");
        builder(itemGenerated,"yellow_clay");
        builder(itemGenerated,"lime_clay");
        builder(itemGenerated,"green_clay");
        builder(itemGenerated,"cyan_clay");
        builder(itemGenerated,"light_blue_clay");
        builder(itemGenerated,"blue_clay");
        builder(itemGenerated,"purple_clay");
        builder(itemGenerated,"magenta_clay");
        builder(itemGenerated,"pink_clay");
        builder(itemGenerated,"white_clay");
        builder(itemGenerated,"light_gray_clay");
        builder(itemGenerated,"gray_clay");
        builder(itemGenerated,"black_clay");
        builder(itemGenerated,"brown_clay");
        builder(itemGenerated,"terracotta_clay");
        builder(itemGenerated,"red_clay_teapot");
        builder(itemGenerated,"red_terracotta_teapot");
        builder(itemGenerated,"red_teapot");
        builder(itemGenerated,"orange_clay_teapot");
        builder(itemGenerated,"orange_terracotta_teapot");
        builder(itemGenerated,"orange_teapot");
        builder(itemGenerated,"yellow_clay_teapot");
        builder(itemGenerated,"yellow_terracotta_teapot");
        builder(itemGenerated,"yellow_teapot");
        builder(itemGenerated,"lime_clay_teapot");
        builder(itemGenerated,"lime_terracotta_teapot");
        builder(itemGenerated,"lime_teapot");
        builder(itemGenerated,"white_clay_teapot");
        builder(itemGenerated,"white_terracotta_teapot");
        builder(itemGenerated,"white_teapot");
        builder(itemGenerated,"white_clay_teaplate");
        builder(itemGenerated,"white_terracotta_teaplate");
        builder(itemGenerated,"white_teaplate");
        builder(itemGenerated,"white_clay_teacup");
        builder(itemGenerated,"white_terracotta_teacup");
        builder(itemGenerated,"white_teacup");
        builder(itemGenerated, "acolyte_door");
        builder(itemGenerated, "vermilion_door");
        builder(itemGenerated, "evermore_door");
        builder(itemGenerated, "bastion_door");
        builder(itemGenerated, "wartwood_door");
        builder(itemGenerated, "jubilee_door");
        builder(itemGenerated, "acolyte_sign");
        builder(itemGenerated, "vermilion_sign");
        builder(itemGenerated, "evermore_sign");
        builder(itemGenerated, "bastion_sign");
        builder(itemGenerated, "wartwood_sign");
        builder(itemGenerated, "jubilee_sign");
        builderMultiLayerSetOverlay(itemGenerated,"filled_white_terracotta_teacup", "filled_teacup_overlay");
        builderMultiLayerSetOverlay(itemGenerated,"filled_white_teacup", "filled_teacup_overlay");
        //builder(itemGenerated,"white_clay_teapot");
        //builder(itemGenerated, "silver_ingot");
        //builder(itemGenerated, "copper_ingot");
        //builder(itemGenerated, "brass_ingot");
        //builder(itemGenerated, "bronze_ingot");
        //builder(itemGenerated, "allorite_ingot");
        //builder(itemGenerated, "tenebrium_ingot");
        for(int i = 1; i <= 4; ++i)
            builder(itemHandheld, "apprentice_wand_"+i);
    }

    private void builder(ModelFile itemGenerated, String name) {
        getBuilder(name).parent(itemGenerated).texture("layer0","item/"+name);
    }

    private void builderMultiLayer(ModelFile itemGenerated, String name) {
        getBuilder(name).parent(itemGenerated).texture("layer0", "item/"+name+"_overlay").texture("layer1","item/"+name);
    }

    private void builderMultiLayerSetOverlay(ModelFile itemGenerated, String name, String overlay) {
        getBuilder(name).parent(itemGenerated).texture("layer0", "item/"+overlay).texture("layer1","item/"+name);
    }
}
