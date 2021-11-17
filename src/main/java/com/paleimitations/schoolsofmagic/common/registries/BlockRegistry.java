package com.paleimitations.schoolsofmagic.common.registries;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.blocks.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class BlockRegistry {

    public static final List<HerbalTwineEntry> HERBAL_TWINE_ENTRIES = Lists.newArrayList();
    public static final WoodType ACOLYTE = WoodType.create(References.MODID+":acolyte");
    public static final WoodType BASTION = WoodType.create(References.MODID+":bastion");
    public static final WoodType VERMILION = WoodType.create(References.MODID+":vermilion");
    public static final WoodType WARTWOOD = WoodType.create(References.MODID+":wartwood");
    public static final WoodType JUBILEE = WoodType.create(References.MODID+":jubilee");
    public static final WoodType EVERMORE = WoodType.create(References.MODID+":evermore");
    /* public static final RegistryObject<Block> SILVER_ORE = register("silver_ore", () -> new OreBlock(AbstractBlock.Properties.of(Material.STONE)
            .requiresCorrectToolForDrops().strength(1f, 1f).harvestLevel(2).sound(SoundType.STONE)));
    public static final RegistryObject<Block> SILVER_BLOCK = register("silver_block", () ->
            new Block(AbstractBlock.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(1f, 1f).sound(SoundType.METAL)));*/
    public static final RegistryObject<Block> ACOLYTE_PLANKS = register("acolyte_planks", () -> new Block(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> VERMILION_PLANKS = register("vermilion_planks", () -> new Block(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> WARTWOOD_PLANKS = register("wartwood_planks", () -> new Block(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> JUBILEE_PLANKS = register("jubilee_planks", () -> new Block(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> BASTION_PLANKS = register("bastion_planks", () -> new Block(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> EVERMORE_PLANKS = register("evermore_planks", () -> new Block(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> ACOLYTE_LOG = register("acolyte_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> BASTION_LOG = register("bastion_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> VERMILION_LOG = register("vermilion_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> WARTWOOD_LOG = register("wartwood_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> JUBILEE_LOG = register("jubilee_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> EVERMORE_LOG = register("evermore_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_ACOLYTE_LOG = register("stripped_acolyte_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_BASTION_LOG = register("stripped_bastion_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_VERMILION_LOG = register("stripped_vermilion_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_WARTWOOD_LOG = register("stripped_wartwood_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_JUBILEE_LOG = register("stripped_jubilee_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_EVERMORE_LOG = register("stripped_evermore_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> ACOLYTE_WOOD = register("acolyte_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> BASTION_WOOD = register("bastion_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> VERMILION_WOOD = register("vermilion_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> WARTWOOD_WOOD = register("wartwood_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> JUBILEE_WOOD = register("jubilee_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> EVERMORE_WOOD = register("evermore_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_ACOLYTE_WOOD = register("stripped_acolyte_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_BASTION_WOOD = register("stripped_bastion_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_VERMILION_WOOD = register("stripped_vermilion_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_WARTWOOD_WOOD = register("stripped_wartwood_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_JUBILEE_WOOD = register("stripped_jubilee_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_EVERMORE_WOOD = register("stripped_evermore_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<DoorBlock> ACOLYTE_DOOR = register("acolyte_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistryObject<DoorBlock> BASTION_DOOR = register("bastion_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(3.5F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistryObject<DoorBlock> VERMILION_DOOR = register("vermilion_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistryObject<DoorBlock> WARTWOOD_DOOR = register("wartwood_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistryObject<DoorBlock> JUBILEE_DOOR = register("jubilee_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistryObject<DoorBlock> EVERMORE_DOOR = register("evermore_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistryObject<TrapDoorBlock> ACOLYTE_TRAPDOOR = register("acolyte_trapdoor", () -> new TrapDoorBlock(AbstractBlock.
            Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(BlockRegistry::never)));
    public static final RegistryObject<TrapDoorBlock> BASTION_TRAPDOOR = register("bastion_trapdoor", () -> new TrapDoorBlock(AbstractBlock.
            Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.5F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(BlockRegistry::never)));
    public static final RegistryObject<TrapDoorBlock> VERMILION_TRAPDOOR = register("vermilion_trapdoor", () -> new TrapDoorBlock(AbstractBlock.
            Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(BlockRegistry::never)));
    public static final RegistryObject<TrapDoorBlock> WARTWOOD_TRAPDOOR = register("wartwood_trapdoor", () -> new TrapDoorBlock(AbstractBlock.
            Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(BlockRegistry::never)));
    public static final RegistryObject<TrapDoorBlock> JUBILEE_TRAPDOOR = register("jubilee_trapdoor", () -> new TrapDoorBlock(AbstractBlock.
            Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(BlockRegistry::never)));
    public static final RegistryObject<TrapDoorBlock> EVERMORE_TRAPDOOR = register("evermore_trapdoor", () -> new TrapDoorBlock(AbstractBlock.
            Properties.of(Material.WOOD, MaterialColor.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(BlockRegistry::never)));
    public static final RegistryObject<Block> ACOLYTE_BUTTON = register("acolyte_button", () -> new WoodButtonBlock(AbstractBlock.
            Properties.of(Material.WOOD).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> BASTION_BUTTON = register("bastion_button", () -> new WoodButtonBlock(AbstractBlock.
            Properties.of(Material.WOOD).noCollission().strength(1F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> VERMILION_BUTTON = register("vermilion_button", () -> new WoodButtonBlock(AbstractBlock.
            Properties.of(Material.WOOD).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> WARTWOOD_BUTTON = register("wartwood_button", () -> new WoodButtonBlock(AbstractBlock.
            Properties.of(Material.WOOD).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> JUBILEE_BUTTON = register("jubilee_button", () -> new WoodButtonBlock(AbstractBlock.
            Properties.of(Material.WOOD).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> EVERMORE_BUTTON = register("evermore_button", () -> new WoodButtonBlock(AbstractBlock.
            Properties.of(Material.WOOD).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ACOLYTE_PRESSURE_PLATE = register("acolyte_pressure_plate", () -> new PressurePlateBlock(
            PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).noCollission().strength(0.5F)
            .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> BASTION_PRESSURE_PLATE = register("bastion_pressure_plate", () -> new PressurePlateBlock(
            PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).noCollission().strength(1F)
            .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> VERMILION_PRESSURE_PLATE = register("vermilion_pressure_plate", () -> new PressurePlateBlock(
            PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).noCollission().strength(0.5F)
            .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> WARTWOOD_PRESSURE_PLATE = register("wartwood_pressure_plate", () -> new PressurePlateBlock(
            PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).noCollission().strength(0.5F)
            .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> JUBILEE_PRESSURE_PLATE = register("jubilee_pressure_plate", () -> new PressurePlateBlock(
            PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).noCollission().strength(0.5F)
            .sound(SoundType.WOOD)));
    public static final RegistryObject<Block> EVERMORE_PRESSURE_PLATE = register("evermore_pressure_plate", () -> new PressurePlateBlock(
            PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).noCollission().strength(0.5F)
            .sound(SoundType.WOOD)));
    public static final RegistryObject<FenceBlock> ACOLYTE_FENCE = register("acolyte_fence", () -> new FenceBlock(AbstractBlock.Properties.of(
            Material.WOOD, ACOLYTE_PLANKS.get().defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceBlock> BASTION_FENCE = register("bastion_fence", () -> new FenceBlock(AbstractBlock.Properties.of(
            Material.WOOD, BASTION_PLANKS.get().defaultMaterialColor()).strength(2.5F, 3.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceBlock> VERMILION_FENCE = register("vermilion_fence", () -> new FenceBlock(AbstractBlock.Properties.of(
            Material.WOOD, VERMILION_PLANKS.get().defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceBlock> WARTWOOD_FENCE = register("wartwood_fence", () -> new FenceBlock(AbstractBlock.Properties.of(
            Material.WOOD, WARTWOOD_PLANKS.get().defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceBlock> JUBILEE_FENCE = register("jubilee_fence", () -> new FenceBlock(AbstractBlock.Properties.of(
            Material.WOOD, JUBILEE_PLANKS.get().defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceBlock> EVERMORE_FENCE = register("evermore_fence", () -> new FenceBlock(AbstractBlock.Properties.of(
            Material.WOOD, EVERMORE_PLANKS.get().defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceGateBlock> ACOLYTE_FENCE_GATE = register("acolyte_fence_gate", () -> new FenceGateBlock(AbstractBlock.
            Properties.of(Material.WOOD, BASTION_PLANKS.get().defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceGateBlock> BASTION_FENCE_GATE = register("bastion_fence_gate", () -> new FenceGateBlock(AbstractBlock.
            Properties.of(Material.WOOD, ACOLYTE_PLANKS.get().defaultMaterialColor()).strength(2.5F, 3.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceGateBlock> VERMILION_FENCE_GATE = register("vermilion_fence_gate", () -> new FenceGateBlock(AbstractBlock.
            Properties.of(Material.WOOD, VERMILION_PLANKS.get().defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceGateBlock> WARTWOOD_FENCE_GATE = register("wartwood_fence_gate", () -> new FenceGateBlock(AbstractBlock.
            Properties.of(Material.WOOD, WARTWOOD_PLANKS.get().defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceGateBlock> JUBILEE_FENCE_GATE = register("jubilee_fence_gate", () -> new FenceGateBlock(AbstractBlock.
            Properties.of(Material.WOOD, JUBILEE_PLANKS.get().defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceGateBlock> EVERMORE_FENCE_GATE = register("evermore_fence_gate", () -> new FenceGateBlock(AbstractBlock.
            Properties.of(Material.WOOD, EVERMORE_PLANKS.get().defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<StairsBlock> ACOLYTE_STAIRS = register("acolyte_stairs", () -> new StairsBlock(
            ()->ACOLYTE_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(ACOLYTE_PLANKS.get())));
    public static final RegistryObject<StairsBlock> BASTION_STAIRS = register("bastion_stairs", () -> new StairsBlock(
            ()->BASTION_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(BASTION_PLANKS.get())));
    public static final RegistryObject<StairsBlock> VERMILION_STAIRS = register("vermilion_stairs", () -> new StairsBlock(
            ()->VERMILION_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(VERMILION_PLANKS.get())));
    public static final RegistryObject<StairsBlock> WARTWOOD_STAIRS = register("wartwood_stairs", () -> new StairsBlock(
            ()->WARTWOOD_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(WARTWOOD_PLANKS.get())));
    public static final RegistryObject<StairsBlock> JUBILEE_STAIRS = register("jubilee_stairs", () -> new StairsBlock(
            ()->JUBILEE_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(JUBILEE_PLANKS.get())));
    public static final RegistryObject<StairsBlock> EVERMORE_STAIRS = register("evermore_stairs", () -> new StairsBlock(
            ()->EVERMORE_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(EVERMORE_PLANKS.get())));
    public static final RegistryObject<SlabBlock> ACOLYTE_SLAB = register("acolyte_slab", () -> new SlabBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<SlabBlock> BASTION_SLAB = register("bastion_slab", () -> new SlabBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.5F, 3.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<SlabBlock> VERMILION_SLAB = register("vermilion_slab", () -> new SlabBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<SlabBlock> WARTWOOD_SLAB = register("wartwood_slab", () -> new SlabBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<SlabBlock> JUBILEE_SLAB = register("jubilee_slab", () -> new SlabBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<SlabBlock> EVERMORE_SLAB = register("evermore_slab", () -> new SlabBlock(AbstractBlock.Properties.of(Material.WOOD,
            MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ACOLYTE_SIGN = registerNoItem("acolyte_sign", () -> new StandingSignBlock(AbstractBlock.Properties.of(
            Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), ACOLYTE));
    public static final RegistryObject<Block> BASTION_SIGN = registerNoItem("bastion_sign", () -> new StandingSignBlock(AbstractBlock.Properties.of(
            Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), BASTION));
    public static final RegistryObject<Block> VERMILION_SIGN = registerNoItem("vermilion_sign", () -> new StandingSignBlock(AbstractBlock.Properties.of(
            Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), VERMILION));
    public static final RegistryObject<Block> WARTWOOD_SIGN = registerNoItem("wartwood_sign", () -> new StandingSignBlock(AbstractBlock.Properties.of(
            Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), WARTWOOD));
    public static final RegistryObject<Block> JUBILEE_SIGN = registerNoItem("jubilee_sign", () -> new StandingSignBlock(AbstractBlock.Properties.of(
            Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), JUBILEE));
    public static final RegistryObject<Block> EVERMORE_SIGN = registerNoItem("evermore_sign", () -> new StandingSignBlock(AbstractBlock.Properties.of(
            Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), EVERMORE));
    public static final RegistryObject<Block> ACOLYTE_WALL_SIGN = registerNoItem("acolyte_wall_sign", () -> new WallSignBlock(AbstractBlock.Properties.of(
            Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(ACOLYTE_SIGN.get()), ACOLYTE));
    public static final RegistryObject<Block> BASTION_WALL_SIGN = registerNoItem("bastion_wall_sign", () -> new WallSignBlock(AbstractBlock.Properties.of(
            Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(BASTION_SIGN.get()), BASTION));
    public static final RegistryObject<Block> VERMILION_WALL_SIGN = registerNoItem("vermilion_wall_sign", () -> new WallSignBlock(AbstractBlock.Properties.of(
            Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(VERMILION_SIGN.get()), VERMILION));
    public static final RegistryObject<Block> WARTWOOD_WALL_SIGN = registerNoItem("wartwood_wall_sign", () -> new WallSignBlock(AbstractBlock.Properties.of(
            Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(WARTWOOD_SIGN.get()), WARTWOOD));
    public static final RegistryObject<Block> JUBILEE_WALL_SIGN = registerNoItem("jubilee_wall_sign", () -> new WallSignBlock(AbstractBlock.Properties.of(
            Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(JUBILEE_SIGN.get()), JUBILEE));
    public static final RegistryObject<Block> EVERMORE_WALL_SIGN = registerNoItem("evermore_wall_sign", () -> new WallSignBlock(AbstractBlock.Properties.of(
            Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(EVERMORE_SIGN.get()), EVERMORE));
    /*public static final RegistryObject<Block> ACOLYTE_LEAVES = register("acolyte_leaves", () -> leaves());
    public static final RegistryObject<Block> BASTION_LEAVES = register("bastion_leaves", () -> leaves());
    public static final RegistryObject<Block> VERMILION_LEAVES = register("vermilion_leaves", () -> leaves());
    public static final RegistryObject<Block> WARTWOOD_LEAVES = register("wartwood_leaves", () -> leaves());
    public static final RegistryObject<Block> JUBILEE_LEAVES = register("jubilee_leaves", () -> leaves());
    public static final RegistryObject<Block> EVERMORE_LEAVES = register("evermore_leaves", () -> leaves());*/
    public static final RegistryObject<Block> ANDESITE_MORTAR = register("andesite_mortar", () -> new MortarBlock(AbstractBlock.Properties.of(Material.STONE)
           .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> GRANITE_MORTAR = register("granite_mortar", () -> new MortarBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> DIORITE_MORTAR = register("diorite_mortar", () -> new MortarBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> STONE_PODIUM = register("stone_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ANDESITE_PODIUM = register("andesite_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> GRANITE_PODIUM = register("granite_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> DIORITE_PODIUM = register("diorite_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> SANDSTONE_PODIUM = register("sandstone_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> RED_SANDSTONE_PODIUM = register("red_sandstone_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> PRISMARINE_PODIUM = register("prismarine_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> BLACKSTONE_PODIUM = register("blackstone_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.GILDED_BLACKSTONE)));
    public static final RegistryObject<Block> BASALT_PODIUM = register("basalt_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.BASALT)));
    public static final RegistryObject<Block> QUARTZ_PODIUM = register("quartz_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> NETHERBRICK_PODIUM = register("netherbrick_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.NETHER_BRICKS)));
    public static final RegistryObject<Block> ENDSTONE_PODIUM = register("endstone_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> PURPUR_PODIUM = register("purpur_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> OBSIDIAN_PODIUM = register("obsidian_podium", () -> new PodiumBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> HERBAL_TWINE = register("herbal_twine", () -> new HerbalTwineBlock(AbstractBlock.Properties.of(Material.WOOL)
            .strength(1f, 1f).sound(SoundType.GRASS).noCollission().instabreak()));
    public static final RegistryObject<Block> HERBAL_TWINE_POPPY = registerNoItem("herbal_twine_poppy", () -> new HerbalTwineBlock(AbstractBlock.Properties.of(Material.PLANT)
            .strength(1f, 1f).sound(SoundType.GRASS).noCollission().instabreak()));
    public static final RegistryObject<Block> HERBAL_TWINE_CORNFLOWER = registerNoItem("herbal_twine_cornflower", () -> new HerbalTwineBlock(AbstractBlock.Properties.of(Material.PLANT)
            .strength(1f, 1f).sound(SoundType.GRASS).noCollission().instabreak()));
    public static final RegistryObject<Block> HERBAL_TWINE_ALLIUM = registerNoItem("herbal_twine_allium", () -> new HerbalTwineBlock(AbstractBlock.Properties.of(Material.PLANT)
            .strength(1f, 1f).sound(SoundType.GRASS).noCollission().instabreak()));
    public static final RegistryObject<Block> HERBAL_TWINE_DANDELION = registerNoItem("herbal_twine_dandelion", () -> new HerbalTwineBlock(AbstractBlock.Properties.of(Material.PLANT)
            .strength(1f, 1f).sound(SoundType.GRASS).noCollission().instabreak()));
    public static final RegistryObject<Block> HERBAL_TWINE_LILY_OF_THE_VALLEY = registerNoItem("herbal_twine_lily_of_the_valley", () -> new HerbalTwineBlock(AbstractBlock.Properties.of(Material.PLANT)
            .strength(1f, 1f).sound(SoundType.GRASS).noCollission().instabreak()));
    public static final RegistryObject<Block> HERBAL_TWINE_BLUE_ORCHID = registerNoItem("herbal_twine_blue_orchid", () -> new HerbalTwineBlock(AbstractBlock.Properties.of(Material.PLANT)
            .strength(1f, 1f).sound(SoundType.GRASS).noCollission().instabreak()));
    public static final RegistryObject<Block> RED_TERRACOTTA_TEAPOT = register("red_terracotta_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ORANGE_TERRACOTTA_TEAPOT = register("orange_terracotta_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> YELLOW_TERRACOTTA_TEAPOT = register("yellow_terracotta_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> LIME_TERRACOTTA_TEAPOT = register("lime_terracotta_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> WHITE_TERRACOTTA_TEAPOT = register("white_terracotta_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> RED_TEAPOT = register("red_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> ORANGE_TEAPOT = register("orange_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> YELLOW_TEAPOT = register("yellow_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> LIME_TEAPOT = register("lime_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> WHITE_TEAPOT = register("white_teapot", () -> new TeapotBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> WHITE_TERRACOTTA_TEAPLATE = register("white_terracotta_teaplate", () -> new TeaplateBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> WHITE_TEAPLATE = register("white_teaplate", () -> new TeaplateBlock(AbstractBlock.Properties.of(Material.STONE)
            .strength(1f, 1f).sound(SoundType.GLASS)));

    public static void register() {
        WoodType.register(ACOLYTE);
        WoodType.register(BASTION);
        WoodType.register(EVERMORE);
        WoodType.register(JUBILEE);
        WoodType.register(VERMILION);
        WoodType.register(WARTWOOD);
    }

    private static Boolean never(BlockState p_235427_0_, IBlockReader p_235427_1_, BlockPos p_235427_2_, EntityType<?> p_235427_3_) {
        return false;
    }

    private static boolean never(BlockState p_235436_0_, IBlockReader p_235436_1_, BlockPos p_235436_2_) {
        return false;
    }

    private static Boolean ocelotOrParrot(BlockState p_235441_0_, IBlockReader p_235441_1_, BlockPos p_235441_2_, EntityType<?> p_235441_3_) {
        return p_235441_3_ == EntityType.OCELOT || p_235441_3_ == EntityType.PARROT;
    }

    private static LeavesBlock leaves() {
        return new LeavesBlock(AbstractBlock.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(BlockRegistry::ocelotOrParrot).isSuffocating(BlockRegistry::never).isViewBlocking(BlockRegistry::never));
    }

    public static void init() {
        HERBAL_TWINE_ENTRIES.add(new HerbalTwineEntry((HerbalTwineBlock)HERBAL_TWINE_POPPY.get(), new ItemStack(Items.POPPY), new ItemStack(ItemRegistry.DRIED_POPPY.get())));
        HERBAL_TWINE_ENTRIES.add(new HerbalTwineEntry((HerbalTwineBlock)HERBAL_TWINE_CORNFLOWER.get(), new ItemStack(Items.CORNFLOWER), new ItemStack(ItemRegistry.DRIED_CORNFLOWER.get())));
        HERBAL_TWINE_ENTRIES.add(new HerbalTwineEntry((HerbalTwineBlock)HERBAL_TWINE_ALLIUM.get(), new ItemStack(Items.ALLIUM), new ItemStack(ItemRegistry.DRIED_ALLIUM.get())));
        HERBAL_TWINE_ENTRIES.add(new HerbalTwineEntry((HerbalTwineBlock)HERBAL_TWINE_DANDELION.get(), new ItemStack(Items.DANDELION), new ItemStack(ItemRegistry.DRIED_DANDELION.get())));
        HERBAL_TWINE_ENTRIES.add(new HerbalTwineEntry((HerbalTwineBlock)HERBAL_TWINE_LILY_OF_THE_VALLEY.get(), new ItemStack(Items.LILY_OF_THE_VALLEY), new ItemStack(ItemRegistry.DRIED_LILY_OF_THE_VALLEY.get())));
        HERBAL_TWINE_ENTRIES.add(new HerbalTwineEntry((HerbalTwineBlock)HERBAL_TWINE_BLUE_ORCHID.get(), new ItemStack(Items.BLUE_ORCHID), new ItemStack(ItemRegistry.DRIED_BLUE_ORCHID.get())));

    }

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return Registry.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerNoItem(name, block);
        Registry.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().tab(Registry.EQUIPMENT_TAB)));
        return ret;
    }

}
