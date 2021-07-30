package com.paleimitations.schoolsofmagic.common.blocks;

import com.paleimitations.schoolsofmagic.common.registries.BlockRegistry;
import com.paleimitations.schoolsofmagic.common.registries.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class HerbalTwineBlock extends Block {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);

    public HerbalTwineBlock(Properties prop) {
        super(prop);
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        ItemStack inHand = player.getItemInHand(hand);
        if (state.getBlock() == BlockRegistry.HERBAL_TWINE.get()) {
            for (HerbalTwineEntry entry : BlockRegistry.HERBAL_TWINE_ENTRIES) {
                if (entry.undried.sameItem(inHand)) {
                    world.setBlockAndUpdate(pos, entry.block.defaultBlockState());
                    if(!player.isCreative())
                        inHand.shrink(1);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        else {
            HerbalTwineEntry entry = HerbalTwineEntry.getEntryFromBlock(this);
            if(state.getValue(AGE) < 5) {
                if(!player.addItem(entry.undried.copy())) {
                    if (world.isClientSide) {
                        world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, entry.undried.copy()));
                    }
                }
                world.setBlockAndUpdate(pos, BlockRegistry.HERBAL_TWINE.get().defaultBlockState());
                return ActionResultType.SUCCESS;
            }
            else {
                if(!player.addItem(entry.dried.copy())) {
                    if (world.isClientSide) {
                        world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, entry.dried.copy()));
                    }
                }
                world.setBlockAndUpdate(pos, BlockRegistry.HERBAL_TWINE.get().defaultBlockState());
                return ActionResultType.SUCCESS;
            }
        }

        return super.use(state, world, pos, player, hand, result);
    }

    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 5;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        if (state.getBlock() != BlockRegistry.HERBAL_TWINE.get()) {
            int i = state.getValue(AGE);
            if (i < 5 && rand.nextFloat() > 0.45f)
                world.setBlock(pos, state.setValue(AGE, i + 1), 2);
        }
    }

    public void onRemove(BlockState state, World world, BlockPos pos, BlockState p_196243_4_, boolean p_196243_5_) {
        if (!state.is(p_196243_4_.getBlock())) {
            if(state.getBlock() != BlockRegistry.HERBAL_TWINE.get()) {
                HerbalTwineEntry entry = HerbalTwineEntry.getEntryFromBlock(this);
                if (world.isClientSide) {
                    world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, state.getValue(AGE) < 5 ? entry.undried.copy() : entry.dried.copy()));
                }

            }
            super.onRemove(state, world, pos, p_196243_4_, p_196243_5_);
        }
    }

    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return p_200123_1_.getFluidState().isEmpty();
    }

    public boolean isPathfindable(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        return p_196266_4_ == PathType.AIR && !this.hasCollision ? true : super.isPathfindable(p_196266_1_, p_196266_2_, p_196266_3_, p_196266_4_);
    }

    public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
        BlockState block = world.getBlockState(pos.relative(Direction.UP));
        return block.isFaceSturdy(world, pos, Direction.DOWN);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState block = context.getLevel().getBlockState(context.getClickedPos());
        return context.getClickedFace() == Direction.DOWN? this.defaultBlockState() : null;
    }

    public BlockState updateShape(BlockState blockstate, Direction direction, BlockState p_196271_3_, IWorld world, BlockPos pos, BlockPos p_196271_6_) {
        return direction == Direction.UP && !blockstate.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockstate, direction, p_196271_3_, world, pos, p_196271_6_);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(AGE);
    }
}
