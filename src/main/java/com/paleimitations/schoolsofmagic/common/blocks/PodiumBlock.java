package com.paleimitations.schoolsofmagic.common.blocks;

import com.paleimitations.schoolsofmagic.common.data.BookUtils;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookDataProvider;
import com.paleimitations.schoolsofmagic.common.registries.TileEntityRegistry;
import com.paleimitations.schoolsofmagic.common.tileentities.PodiumTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.BarrelTileEntity;
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

public class PodiumBlock extends ContainerBlock {
    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape SHAPE_BASE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
    public static final VoxelShape SHAPE_POST = Block.box(1.0D, 3.0D, 1.0D, 15.0D, 6.0D, 15.0D);
    public static final VoxelShape SHAPE_TOP = Block.box(0.0D, 6.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    public static final VoxelShape SHAPE_COLLISION = VoxelShapes.or(SHAPE_BASE, SHAPE_POST, SHAPE_TOP);

    public PodiumBlock(Properties p_i48446_1_) {
        super(p_i48446_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        double hitX = result.getLocation().x - pos.getX();
        double hitY = result.getLocation().y - pos.getY();
        double hitZ = result.getLocation().z - pos.getZ();
        TileEntity tileentity = world.getBlockEntity(pos);
        if (tileentity instanceof PodiumTileEntity) {
            PodiumTileEntity podium = (PodiumTileEntity)tileentity;
            if(podium.getItem().isEmpty() && !player.getItemInHand(hand).isEmpty()) {
                podium.setItem(player.getItemInHand(hand).split(1));
                return ActionResultType.SUCCESS;
            }
            else if(player.isShiftKeyDown() && player.getItemInHand(hand).isEmpty() && !podium.getItem().isEmpty()) {
                player.setItemInHand(hand, podium.getItem().split(1));
                PiglinTasks.angerNearbyPiglins(player, true);
                return ActionResultType.SUCCESS;
            }
            else if(podium.hasBook() && hitY>0.5d && world.isClientSide) {
                BookData data = BookDataProvider.getBook(world, podium.getItem());
                ActionResult<ItemStack> action;
                switch(blockState.getValue(HORIZONTAL_FACING)) {
                    case NORTH:
                        if(hitX > 0.5d) {
                            action = BookUtils.turnPage(data, podium.getItem(), false, pos);
                        }
                        else{
                            action = BookUtils.turnPage(data, podium.getItem(), true, pos);
                        }
                        if(action.getResult() == ActionResultType.SUCCESS)
                            podium.setItem(action.getObject());
                        return ActionResultType.SUCCESS;
                    case SOUTH:
                        if(hitX > 0.5d) {
                            action = BookUtils.turnPage(data, podium.getItem(), true, pos);
                        }
                        else{
                            action = BookUtils.turnPage(data, podium.getItem(), false, pos);
                        }
                        if(action.getResult() == ActionResultType.SUCCESS)
                            podium.setItem(action.getObject());
                        return ActionResultType.SUCCESS;
                    case EAST:
                        if(hitZ > 0.5d) {
                            action = BookUtils.turnPage(data, podium.getItem(), false, pos);
                        }
                        else{
                            action = BookUtils.turnPage(data, podium.getItem(), true, pos);
                        }
                        if(action.getResult() == ActionResultType.SUCCESS)
                            podium.setItem(action.getObject());
                        return ActionResultType.SUCCESS;
                    case WEST:
                        if(hitZ > 0.5d) {
                            action = BookUtils.turnPage(data, podium.getItem(), true, pos);
                        }
                        else{
                            action = BookUtils.turnPage(data, podium.getItem(), false, pos);
                        }
                        if(action.getResult() == ActionResultType.SUCCESS)
                            podium.setItem(action.getObject());
                }
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState p_196247_1_, IBlockReader p_196247_2_, BlockPos p_196247_3_) {
        return SHAPE_COLLISION;
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
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return TileEntityRegistry.PODIUM_TILE_ENTITY.get().create();
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
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, p_196258_1_.getHorizontalDirection().getOpposite());
    }
}
