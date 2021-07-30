package com.paleimitations.schoolsofmagic.common.quests.quests;

import com.google.common.base.Predicate;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.items.BookBaseItem;
import com.paleimitations.schoolsofmagic.common.quests.Quest;
import com.paleimitations.schoolsofmagic.common.quests.Task;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.pattern.BlockMaterialMatcher;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.BlockEvent;

public class QuestBuildGolem extends Quest {

    public QuestBuildGolem() {
        super(new ResourceLocation(References.MODID, "build_golem"));
        this.initialize();
        //System.out.println("Created Build Golem Quest");
    }

    public QuestBuildGolem(ResourceLocation location) {
        this();
    }

    public QuestBuildGolem(CompoundNBT nbt) {
        super(nbt);
    }

    @Override
    public void initialize() {
        System.out.println("Init Build Golem Quest");
        this.tasks.clear();
        Task task = new Task(Task.EnumTaskType.BUILD) {
            private BlockPattern snowmanPattern;
            private BlockPattern golemPattern;
            private BlockPattern witherPattern;
            private final Predicate<BlockState> IS_PUMPKIN = p_apply_1_ -> p_apply_1_ != null && (p_apply_1_.getBlock() == Blocks.JACK_O_LANTERN
                    || p_apply_1_.getBlock() == Blocks.CARVED_PUMPKIN);
            private final Predicate<BlockState> IS_WITHER_SKELETON = p_apply_1_ -> p_apply_1_ != null && (p_apply_1_.getBlock() == Blocks.WITHER_SKELETON_SKULL
                    || p_apply_1_.getBlock() == Blocks.WITHER_SKELETON_WALL_SKULL);

            @Override
            public boolean check(PlayerEntity player, Object object) {
                if(object instanceof BlockEvent.EntityPlaceEvent) {
                    BlockEvent.EntityPlaceEvent event = (BlockEvent.EntityPlaceEvent) object;
                    if(IS_PUMPKIN.test(event.getPlacedBlock()))
                        return this.getSnowmanPattern().find(event.getWorld(), event.getPos()) != null || this.getGolemPattern().find(event.getWorld(), event.getPos()) != null;
                    else if(IS_WITHER_SKELETON.test(event.getPlacedBlock()))
                        return this.getWitherPattern().find(event.getWorld(), event.getPos()) != null;
                    else
                        return false;
                }
                else
                    return false;
            }

            @Override
            public void onCompletion(PlayerEntity player) {
                QuestBuildGolem.this.markDirty();
            }

            protected BlockPattern getSnowmanPattern() {
                if (this.snowmanPattern == null) {
                    this.snowmanPattern = BlockPatternBuilder.start().aisle("^", "#", "#").where('^', CachedBlockInfo.hasState(IS_PUMPKIN)).where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.SNOW_BLOCK))).build();
                }

                return this.snowmanPattern;
            }

            protected BlockPattern getGolemPattern() {
                if (this.golemPattern == null) {
                    this.golemPattern = BlockPatternBuilder.start().aisle("~^~", "###", "~#~").where('^', CachedBlockInfo.hasState(IS_PUMPKIN)).where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.IRON_BLOCK))).where('~', CachedBlockInfo.hasState(BlockMaterialMatcher.forMaterial(Material.AIR))).build();
                }

                return this.golemPattern;
            }

            private BlockPattern getWitherPattern() {
                if (witherPattern == null) {
                    witherPattern = BlockPatternBuilder.start().aisle("^^^", "###", "~#~").where('#', (p_235639_0_) -> p_235639_0_.getState().is(BlockTags.WITHER_SUMMON_BASE_BLOCKS)).where('^', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.WITHER_SKELETON_SKULL).or(BlockStateMatcher.forBlock(Blocks.WITHER_SKELETON_WALL_SKULL)))).where('~', CachedBlockInfo.hasState(BlockMaterialMatcher.forMaterial(Material.AIR))).build();
                }
                return witherPattern;
            }
        };
        task.setStarted(true);
        this.tasks.add(task);
        this.rewards.add(BookBaseItem.initialize(new ItemStack(ItemRegistry.BASIC_ARCANA.get())));
        ItemStack stack = new ItemStack(ItemRegistry.QUEST_NOTE.get());
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("quest", References.MODID+":intermediate_arcana");
        stack.setTag(nbt);
        //this.rewards.add(stack);
        this.icon = new ItemStack(Blocks.CARVED_PUMPKIN);
    }
}
