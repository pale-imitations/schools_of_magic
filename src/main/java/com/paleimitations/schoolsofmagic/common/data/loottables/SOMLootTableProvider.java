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
