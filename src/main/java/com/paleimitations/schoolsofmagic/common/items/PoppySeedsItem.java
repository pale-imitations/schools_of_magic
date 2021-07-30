package com.paleimitations.schoolsofmagic.common.items;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;

public class PoppySeedsItem extends Item {
    public PoppySeedsItem(Properties props) {
        super(props);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        BlockState poppy = Blocks.POPPY.defaultBlockState();
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        if(Blocks.POPPY.canSurvive(poppy, context.getLevel(), pos)) {
            int a = random.nextInt(5);
            context.getLevel().setBlockAndUpdate(pos, poppy);
            for(int i = 0; i < a; ++ i) {
                BlockPos randPos = context.getClickedPos().relative(context.getClickedFace()).offset(random.nextInt(3)-random.nextInt(3),
                        random.nextInt(3)-random.nextInt(3),random.nextInt(3)-random.nextInt(3));
                if(Blocks.POPPY.canSurvive(poppy, context.getLevel(), randPos) && context.getLevel().getBlockState(randPos).isAir()) {
                    context.getLevel().setBlockAndUpdate(randPos, poppy);
                }
            }
            return ActionResultType.SUCCESS;
        }
        return super.useOn(context);
    }
}
