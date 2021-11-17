package com.paleimitations.schoolsofmagic.common.data.loottables;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.data.BindingType;
import com.paleimitations.schoolsofmagic.common.registries.BlockRegistry;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.item.DyeColor;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.loot.functions.SetNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SOMLootTableProvider extends LootTableProvider {

    public SOMLootTableProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "Schools of Magic - Loot Tables";
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(Pair.of(SOMBlockLootTables::new, LootParameterSets.BLOCK),
                                Pair.of(SOMEntityLootTables::new, LootParameterSets.ENTITY));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((id, table) -> LootTableManager.validate(validationtracker, id, table));
    }

    public static class SOMBlockLootTables extends BlockLootTables {

        @Override
        protected void addTables() {
            super.addTables();
            this.dropSelf(BlockRegistry.HERBAL_TWINE.get());
            this.dropOther(BlockRegistry.HERBAL_TWINE_POPPY.get(), BlockRegistry.HERBAL_TWINE.get());
            this.dropOther(BlockRegistry.HERBAL_TWINE_CORNFLOWER.get(), BlockRegistry.HERBAL_TWINE.get());
            this.dropOther(BlockRegistry.HERBAL_TWINE_ALLIUM.get(), BlockRegistry.HERBAL_TWINE.get());
            this.dropOther(BlockRegistry.HERBAL_TWINE_DANDELION.get(), BlockRegistry.HERBAL_TWINE.get());
            this.dropOther(BlockRegistry.HERBAL_TWINE_LILY_OF_THE_VALLEY.get(), BlockRegistry.HERBAL_TWINE.get());
            this.dropOther(BlockRegistry.HERBAL_TWINE_BLUE_ORCHID.get(), BlockRegistry.HERBAL_TWINE.get());
            this.dropSelf(BlockRegistry.ANDESITE_MORTAR.get());
            this.dropSelf(BlockRegistry.DIORITE_MORTAR.get());
            this.dropSelf(BlockRegistry.GRANITE_MORTAR.get());
            this.dropSelf(BlockRegistry.LIME_TEAPOT.get());
            this.dropSelf(BlockRegistry.YELLOW_TEAPOT.get());
            this.dropSelf(BlockRegistry.ORANGE_TEAPOT.get());
            this.dropSelf(BlockRegistry.RED_TEAPOT.get());
            this.dropSelf(BlockRegistry.WHITE_TEAPOT.get());
            this.dropSelf(BlockRegistry.LIME_TERRACOTTA_TEAPOT.get());
            this.dropSelf(BlockRegistry.YELLOW_TERRACOTTA_TEAPOT.get());
            this.dropSelf(BlockRegistry.ORANGE_TERRACOTTA_TEAPOT.get());
            this.dropSelf(BlockRegistry.RED_TERRACOTTA_TEAPOT.get());
            this.dropSelf(BlockRegistry.WHITE_TERRACOTTA_TEAPOT.get());
            this.dropSelf(BlockRegistry.WHITE_TERRACOTTA_TEAPLATE.get());
            this.dropSelf(BlockRegistry.WHITE_TEAPLATE.get());

            this.dropSelf(BlockRegistry.ANDESITE_PODIUM.get());
            this.dropSelf(BlockRegistry.RED_SANDSTONE_PODIUM.get());
            this.dropSelf(BlockRegistry.DIORITE_PODIUM.get());
            this.dropSelf(BlockRegistry.NETHERBRICK_PODIUM.get());
            this.dropSelf(BlockRegistry.STONE_PODIUM.get());
            this.dropSelf(BlockRegistry.PRISMARINE_PODIUM.get());
            this.dropSelf(BlockRegistry.GRANITE_PODIUM.get());
            this.dropSelf(BlockRegistry.QUARTZ_PODIUM.get());
            this.dropSelf(BlockRegistry.PURPUR_PODIUM.get());
            this.dropSelf(BlockRegistry.BASALT_PODIUM.get());
            this.dropSelf(BlockRegistry.ENDSTONE_PODIUM.get());
            this.dropSelf(BlockRegistry.BLACKSTONE_PODIUM.get());
            this.dropSelf(BlockRegistry.OBSIDIAN_PODIUM.get());
            this.dropSelf(BlockRegistry.SANDSTONE_PODIUM.get());

            this.add(BlockRegistry.ACOLYTE_DOOR.get(), BlockLootTables::createDoorTable);
            this.dropSelf(BlockRegistry.ACOLYTE_FENCE_GATE.get());
            this.dropSelf(BlockRegistry.ACOLYTE_FENCE.get());
            this.dropSelf(BlockRegistry.ACOLYTE_LOG.get());
            this.dropSelf(BlockRegistry.ACOLYTE_BUTTON.get());
            this.dropSelf(BlockRegistry.ACOLYTE_PLANKS.get());
            this.dropSelf(BlockRegistry.ACOLYTE_PRESSURE_PLATE.get());
            this.add(BlockRegistry.ACOLYTE_SLAB.get(), BlockLootTables::createSlabItemTable);
            this.dropSelf(BlockRegistry.ACOLYTE_STAIRS.get());
            this.dropSelf(BlockRegistry.ACOLYTE_TRAPDOOR.get());
            this.dropSelf(BlockRegistry.ACOLYTE_WOOD.get());
            this.dropSelf(BlockRegistry.STRIPPED_ACOLYTE_LOG.get());
            this.dropSelf(BlockRegistry.STRIPPED_ACOLYTE_WOOD.get());
            this.dropOther(BlockRegistry.ACOLYTE_SIGN.get(), ItemRegistry.ACOLYTE_SIGN.get());
            this.dropOther(BlockRegistry.ACOLYTE_WALL_SIGN.get(), ItemRegistry.ACOLYTE_SIGN.get());

            this.add(BlockRegistry.VERMILION_DOOR.get(), BlockLootTables::createDoorTable);
            this.dropSelf(BlockRegistry.VERMILION_FENCE_GATE.get());
            this.dropSelf(BlockRegistry.VERMILION_FENCE.get());
            this.dropSelf(BlockRegistry.VERMILION_LOG.get());
            this.dropSelf(BlockRegistry.VERMILION_BUTTON.get());
            this.dropSelf(BlockRegistry.VERMILION_PLANKS.get());
            this.dropSelf(BlockRegistry.VERMILION_PRESSURE_PLATE.get());
            this.add(BlockRegistry.VERMILION_SLAB.get(), BlockLootTables::createSlabItemTable);
            this.dropSelf(BlockRegistry.VERMILION_STAIRS.get());
            this.dropSelf(BlockRegistry.VERMILION_TRAPDOOR.get());
            this.dropSelf(BlockRegistry.VERMILION_WOOD.get());
            this.dropSelf(BlockRegistry.STRIPPED_VERMILION_LOG.get());
            this.dropSelf(BlockRegistry.STRIPPED_VERMILION_WOOD.get());
            this.dropOther(BlockRegistry.VERMILION_SIGN.get(), ItemRegistry.VERMILION_SIGN.get());
            this.dropOther(BlockRegistry.VERMILION_WALL_SIGN.get(), ItemRegistry.VERMILION_SIGN.get());

            this.add(BlockRegistry.WARTWOOD_DOOR.get(), BlockLootTables::createDoorTable);
            this.dropSelf(BlockRegistry.WARTWOOD_FENCE_GATE.get());
            this.dropSelf(BlockRegistry.WARTWOOD_FENCE.get());
            this.dropSelf(BlockRegistry.WARTWOOD_LOG.get());
            this.dropSelf(BlockRegistry.WARTWOOD_BUTTON.get());
            this.dropSelf(BlockRegistry.WARTWOOD_PLANKS.get());
            this.dropSelf(BlockRegistry.WARTWOOD_PRESSURE_PLATE.get());
            this.add(BlockRegistry.WARTWOOD_SLAB.get(), BlockLootTables::createSlabItemTable);
            this.dropSelf(BlockRegistry.WARTWOOD_STAIRS.get());
            this.dropSelf(BlockRegistry.WARTWOOD_TRAPDOOR.get());
            this.dropSelf(BlockRegistry.WARTWOOD_WOOD.get());
            this.dropSelf(BlockRegistry.STRIPPED_WARTWOOD_LOG.get());
            this.dropSelf(BlockRegistry.STRIPPED_WARTWOOD_WOOD.get());
            this.dropOther(BlockRegistry.WARTWOOD_SIGN.get(), ItemRegistry.WARTWOOD_SIGN.get());
            this.dropOther(BlockRegistry.WARTWOOD_WALL_SIGN.get(), ItemRegistry.WARTWOOD_SIGN.get());

            this.add(BlockRegistry.EVERMORE_DOOR.get(), BlockLootTables::createDoorTable);
            this.dropSelf(BlockRegistry.EVERMORE_FENCE_GATE.get());
            this.dropSelf(BlockRegistry.EVERMORE_FENCE.get());
            this.dropSelf(BlockRegistry.EVERMORE_LOG.get());
            this.dropSelf(BlockRegistry.EVERMORE_BUTTON.get());
            this.dropSelf(BlockRegistry.EVERMORE_PLANKS.get());
            this.dropSelf(BlockRegistry.EVERMORE_PRESSURE_PLATE.get());
            this.add(BlockRegistry.EVERMORE_SLAB.get(), BlockLootTables::createSlabItemTable);
            this.dropSelf(BlockRegistry.EVERMORE_STAIRS.get());
            this.dropSelf(BlockRegistry.EVERMORE_TRAPDOOR.get());
            this.dropSelf(BlockRegistry.EVERMORE_WOOD.get());
            this.dropSelf(BlockRegistry.STRIPPED_EVERMORE_LOG.get());
            this.dropSelf(BlockRegistry.STRIPPED_EVERMORE_WOOD.get());
            this.dropOther(BlockRegistry.EVERMORE_SIGN.get(), ItemRegistry.EVERMORE_SIGN.get());
            this.dropOther(BlockRegistry.EVERMORE_WALL_SIGN.get(), ItemRegistry.EVERMORE_SIGN.get());

            this.add(BlockRegistry.JUBILEE_DOOR.get(), BlockLootTables::createDoorTable);
            this.dropSelf(BlockRegistry.JUBILEE_FENCE_GATE.get());
            this.dropSelf(BlockRegistry.JUBILEE_FENCE.get());
            this.dropSelf(BlockRegistry.JUBILEE_LOG.get());
            this.dropSelf(BlockRegistry.JUBILEE_BUTTON.get());
            this.dropSelf(BlockRegistry.JUBILEE_PLANKS.get());
            this.dropSelf(BlockRegistry.JUBILEE_PRESSURE_PLATE.get());
            this.add(BlockRegistry.JUBILEE_SLAB.get(), BlockLootTables::createSlabItemTable);
            this.dropSelf(BlockRegistry.JUBILEE_STAIRS.get());
            this.dropSelf(BlockRegistry.JUBILEE_TRAPDOOR.get());
            this.dropSelf(BlockRegistry.JUBILEE_WOOD.get());
            this.dropSelf(BlockRegistry.STRIPPED_JUBILEE_LOG.get());
            this.dropSelf(BlockRegistry.STRIPPED_JUBILEE_WOOD.get());
            this.dropOther(BlockRegistry.JUBILEE_SIGN.get(), ItemRegistry.JUBILEE_SIGN.get());
            this.dropOther(BlockRegistry.JUBILEE_WALL_SIGN.get(), ItemRegistry.JUBILEE_SIGN.get());

            this.add(BlockRegistry.BASTION_DOOR.get(), BlockLootTables::createDoorTable);
            this.dropSelf(BlockRegistry.BASTION_FENCE_GATE.get());
            this.dropSelf(BlockRegistry.BASTION_FENCE.get());
            this.dropSelf(BlockRegistry.BASTION_LOG.get());
            this.dropSelf(BlockRegistry.BASTION_BUTTON.get());
            this.dropSelf(BlockRegistry.BASTION_PLANKS.get());
            this.dropSelf(BlockRegistry.BASTION_PRESSURE_PLATE.get());
            this.add(BlockRegistry.BASTION_SLAB.get(), BlockLootTables::createSlabItemTable);
            this.dropSelf(BlockRegistry.BASTION_STAIRS.get());
            this.dropSelf(BlockRegistry.BASTION_TRAPDOOR.get());
            this.dropSelf(BlockRegistry.BASTION_WOOD.get());
            this.dropSelf(BlockRegistry.STRIPPED_BASTION_LOG.get());
            this.dropSelf(BlockRegistry.STRIPPED_BASTION_WOOD.get());
            this.dropOther(BlockRegistry.BASTION_SIGN.get(), ItemRegistry.BASTION_SIGN.get());
            this.dropOther(BlockRegistry.BASTION_WALL_SIGN.get(), ItemRegistry.BASTION_SIGN.get());
        }
    }

    public static class SOMEntityLootTables extends EntityLootTables {

        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            consumer.accept(LootInjecter.Tables.ENTITIES_WITCH, addArcana(0.95f, 0f));
            consumer.accept(LootInjecter.Tables.ENTITIES_EVOKER, addArcana(1f, 0f));
            consumer.accept(LootInjecter.Tables.ENTITIES_ILLUSIONER, addArcana(1f, 0f));
        }

        private static LootTable.Builder addArcana(float baseChance, float lootingBonus) {
            //CompoundNBT nbt = new CompoundNBT();
            //nbt.putInt("dye", DyeColor.BLACK.getId());
            //nbt.putString("book_data_s", References.MODID + "_book_basic_arcana");
            //nbt.putString("binding", BindingType.BRONZE.getSerializedName());
            return LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantRange.exactly(1))
                            //.add(ItemLootEntry.lootTableItem(ItemRegistry.BASIC_ARCANA.get())
                            .add(ItemLootEntry.lootTableItem(ItemRegistry.LETTER_CCW.get())
                                    //.apply(SetNBT.setTag(nbt))
                                    .when(KilledByPlayer.killedByPlayer())
                            )
                    );
        }
    }
}
