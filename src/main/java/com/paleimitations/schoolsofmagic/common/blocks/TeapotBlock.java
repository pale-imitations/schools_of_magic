package com.paleimitations.schoolsofmagic.common.blocks;

import com.paleimitations.schoolsofmagic.common.containers.MortarContainerProvider;
import com.paleimitations.schoolsofmagic.common.containers.TeapotContainerProvider;
import com.paleimitations.schoolsofmagic.common.registries.TileEntityRegistry;
import com.paleimitations.schoolsofmagic.common.tileentities.MortarTileEntity;
import com.paleimitations.schoolsofmagic.common.tileentities.TeapotTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TeapotBlock extends ContainerBlock {
    protected static final VoxelShape TOP = Block.box(4D, 15.0D, 4D, 12D, 16.0D, 12D);
    protected static final VoxelShape RIM = Block.box(3.5D, 14.0D, 3.5D, 12.5D, 15.0D, 12.5D);
    protected static final VoxelShape NECK = Block.box(4.5D, 13.0D, 4.5D, 11.5D, 14.0D, 11.5D);
    protected static final VoxelShape SHOULDERS = Block.box(4D, 11.0D, 4D, 12D, 13.0D, 12D);
    protected static final VoxelShape CHEST = Block.box(3.5D, 8.0D, 3.5D, 12.5D, 11.0D, 12.5D);
    protected static final VoxelShape BASE = Block.box(3.0D, 2.0D, 3.0D, 13.0D, 8.0D, 13.0D);
    protected static final VoxelShape FOOT = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 2.0D, 10.0D);
    public static final VoxelShape SHAPE_COLLISION = VoxelShapes.or(TOP, RIM, NECK, SHOULDERS, CHEST, BASE, FOOT);
    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

    public TeapotBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public void onRemove(BlockState p_196243_1_, World p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_, boolean p_196243_5_) {
        if (!p_196243_1_.is(p_196243_4_.getBlock())) {
            TileEntity tileentity = p_196243_2_.getBlockEntity(p_196243_3_);
            if (tileentity instanceof IInventory) {
                InventoryHelper.dropContents(p_196243_2_, p_196243_3_, (IInventory)tileentity);
                p_196243_2_.updateNeighbourForOutputSignal(p_196243_3_, this);
            }

            super.onRemove(p_196243_1_, p_196243_2_, p_196243_3_, p_196243_4_, p_196243_5_);
        }
    }

    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray) {
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileentity = world.getBlockEntity(pos);
            if (tileentity instanceof TeapotTileEntity) {
                TeapotContainerProvider provider = new TeapotContainerProvider((TeapotTileEntity)tileentity);
                if(player instanceof ServerPlayerEntity)
                    provider.openFor((ServerPlayerEntity) player);
                //player.awardStat(Stats.INTERACT_WITH_BREWINGSTAND);
            }

            return ActionResultType.CONSUME;
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
        return SHAPE_COLLISION;
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE_COLLISION;
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        return p_185499_1_.setValue(HORIZONTAL_FACING, p_185499_2_.rotate(p_185499_1_.getValue(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
        return p_185471_1_.rotate(p_185471_2_.getRotation(p_185471_1_.getValue(HORIZONTAL_FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, p_196258_1_.getHorizontalDirection().getOpposite().getClockWise());
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return TileEntityRegistry.TEAPOT_TILE_ENTITY.get().create();
    }
}
