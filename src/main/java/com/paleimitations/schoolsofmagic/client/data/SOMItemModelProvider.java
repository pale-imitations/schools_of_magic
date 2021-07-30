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
