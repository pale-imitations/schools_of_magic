package com.paleimitations.schoolsofmagic.common.blocks;

import com.paleimitations.schoolsofmagic.common.registries.TileEntityRegistry;
import com.paleimitations.schoolsofmagic.common.tileentities.TeaplateTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
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

public class TeaplateBlock extends ContainerBlock {
    protected static final VoxelShape[] STACKS = new VoxelShape[]{
            Block.box(1D,0.0D,1D,15D,1.75D,15D),
            Block.box(1D,0.0D,1D,15D,3.00D,15D),
            Block.box(1D,0.0D,1D,15D,4.25D,15D),
            Block.box(1D,0.0D,1D,15D,5.50D,15D),
            Block.box(1D,0.0D,1D,15D,6.75D,15D)
    };
    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty COUNT = IntegerProperty.create("count", 1, 5);

    public TeaplateBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(COUNT, 1));
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
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult p_225533_6_) {
        ItemStack stack = player.getItemInHand(hand);
        TeaplateTileEntity tte = (TeaplateTileEntity) world.getBlockEntity(pos);
        if(stack.getItem()==this.asItem() && state.getValue(COUNT)<5) {
            world.setBlockAndUpdate(pos, state.setValue(COUNT, state.getValue(COUNT)+1));
            if(!player.isCreative())
                stack.shrink(1);
            return ActionResultType.SUCCESS;
        }
        else if(player.isShiftKeyDown() && !tte.getItem().isEmpty() && player.canTakeItem(tte.getItem())) {
            player.addItem(tte.getItem().split(1));
            return ActionResultType.SUCCESS;
        }
        else if(!stack.isEmpty() && tte.getItem().isEmpty()) {
            if(!player.isCreative())
                tte.setItem(stack.split(1));
            else
                tte.setItem(stack.copy().split(1));
            return ActionResultType.SUCCESS;
        }
        else if(player.isShiftKeyDown() && state.getValue(COUNT)>1 && player.canTakeItem(new ItemStack(this, 1))) {
            world.setBlockAndUpdate(pos, state.setValue(COUNT, state.getValue(COUNT)-1));
            player.addItem(new ItemStack(this, 1));
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
        return STACKS[state.getValue(COUNT)-1];
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return STACKS[state.getValue(COUNT)-1];
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
        builder.add(HORIZONTAL_FACING, COUNT);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, p_196258_1_.getHorizontalDirection().getOpposite());
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return TileEntityRegistry.TEAPLATE_TILE_ENTITY.get().create();
    }
}
