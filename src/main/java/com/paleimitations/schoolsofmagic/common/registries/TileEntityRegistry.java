package com.paleimitations.schoolsofmagic.common.registries;

import com.paleimitations.schoolsofmagic.common.tileentities.MortarTileEntity;
import com.paleimitations.schoolsofmagic.common.tileentities.PodiumTileEntity;
import com.paleimitations.schoolsofmagic.common.tileentities.TeaplateTileEntity;
import com.paleimitations.schoolsofmagic.common.tileentities.TeapotTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class TileEntityRegistry {

    public static final RegistryObject<TileEntityType<PodiumTileEntity>> PODIUM_TILE_ENTITY =
            Registry.TILE_ENTITY_TYPES.register("magic_podium", () -> TileEntityType.Builder.of(PodiumTileEntity::new, BlockRegistry.SANDSTONE_PODIUM.get(),
                    BlockRegistry.RED_SANDSTONE_PODIUM.get(), BlockRegistry.BLACKSTONE_PODIUM.get(), BlockRegistry.BASALT_PODIUM.get(),
                    BlockRegistry.ENDSTONE_PODIUM.get(), BlockRegistry.PURPUR_PODIUM.get(), BlockRegistry.OBSIDIAN_PODIUM.get(), BlockRegistry.QUARTZ_PODIUM.get(),
                    BlockRegistry.ANDESITE_PODIUM.get(), BlockRegistry.GRANITE_PODIUM.get(), BlockRegistry.DIORITE_PODIUM.get(), BlockRegistry.PRISMARINE_PODIUM.get(),
                    BlockRegistry.NETHERBRICK_PODIUM.get(), BlockRegistry.STONE_PODIUM.get()
            ).build(null));
    public static final RegistryObject<TileEntityType<MortarTileEntity>> MORTAR_TILE_ENTITY =
            Registry.TILE_ENTITY_TYPES.register("magic_mortar", () -> TileEntityType.Builder.of(MortarTileEntity::new, BlockRegistry.ANDESITE_MORTAR.get(),
                    BlockRegistry.DIORITE_MORTAR.get(), BlockRegistry.GRANITE_MORTAR.get()
            ).build(null));
    public static final RegistryObject<TileEntityType<TeapotTileEntity>> TEAPOT_TILE_ENTITY =
            Registry.TILE_ENTITY_TYPES.register("magic_teapot", () -> TileEntityType.Builder.of(TeapotTileEntity::new, BlockRegistry.RED_TEAPOT.get(),
                    BlockRegistry.ORANGE_TEAPOT.get(), BlockRegistry.YELLOW_TEAPOT.get(), BlockRegistry.LIME_TEAPOT.get(), BlockRegistry.WHITE_TEAPOT.get(),
                    BlockRegistry.RED_TERRACOTTA_TEAPOT.get(),BlockRegistry.ORANGE_TERRACOTTA_TEAPOT.get(), BlockRegistry.YELLOW_TERRACOTTA_TEAPOT.get(),
                    BlockRegistry.LIME_TERRACOTTA_TEAPOT.get(), BlockRegistry.WHITE_TERRACOTTA_TEAPOT.get()
            ).build(null));
    public static final RegistryObject<TileEntityType<TeaplateTileEntity>> TEAPLATE_TILE_ENTITY =
            Registry.TILE_ENTITY_TYPES.register("magic_teaplate", () -> TileEntityType.Builder.of(TeaplateTileEntity::new, BlockRegistry.WHITE_TERRACOTTA_TEAPLATE.get(),
                    BlockRegistry.WHITE_TEAPLATE.get()
            ).build(null));

    public static void register() {

    }
}
