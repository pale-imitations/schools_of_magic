package com.paleimitations.schoolsofmagic.common.items;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.TeaBrewResult;
import com.paleimitations.schoolsofmagic.common.data.TeaUtils;
import com.paleimitations.schoolsofmagic.common.quests.Quest;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import com.paleimitations.schoolsofmagic.common.registries.QuestRegistry;
import com.paleimitations.schoolsofmagic.common.registries.TeaRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class TeacupItem extends Item {
    public final boolean filled;
    public final Item drinkResult;

    public TeacupItem(Properties props, boolean filled, Item drinkResult) {
        super(props);
        this.filled = filled;
        this.drinkResult = drinkResult;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag tooltipFlag) {
        if(filled) {
            TeaBrewResult brew = TeaUtils.getTea(stack);
            if(brew!=null && !brew.isMilk && brew.successful) {
                if (Screen.hasShiftDown())
                    TeaUtils.addTooltip(stack, list, 1.0F);
                else
                    list.add(new TranslationTextComponent(References.MODID + ".hold_shift").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
            }
        }
        else {
            super.appendHoverText(stack, world, list, tooltipFlag);
        }
    }

    public String getDescriptionId(ItemStack stack) {
        return filled? TeaUtils.getName(stack, "item.schoolsofmagic.filled_teacup") : super.getDescriptionId(stack);
    }

    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if (this == ItemRegistry.FILLED_WHITE_TERRACOTTA_TEACUP.get()) {
            if (this.allowdedIn(group)) {
                for(TeaRegistry.Tea tea : TeaRegistry.TEAS) {
                    ItemStack stack = new ItemStack(this);
                    TeaUtils.setTea(stack, tea, tea, tea, false);
                    items.add(stack);
                }
            }
        }
        else if (this.allowdedIn(group)) {
            items.add(new ItemStack(this));
        }


    }

    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity living) {
        PlayerEntity playerentity = living instanceof PlayerEntity ? (PlayerEntity)living : null;
        if (playerentity instanceof ServerPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)playerentity, stack);
        }

        if (!world.isClientSide) {
            TeaBrewResult brew = TeaUtils.getTea(stack);
            Random rand = living.getRandom();
            if(brew.successful) {
                if (brew.isMilk) {
                    living.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
                }
                else {
                    if (!brew.tier1Pool.isEmpty()) {
                        EffectInstance effectinstance = brew.tier1Pool.get(rand.nextInt(brew.tier1Pool.size()));
                        if (effectinstance.getEffect().isInstantenous()) {
                            effectinstance.getEffect().applyInstantenousEffect(playerentity, playerentity, living, effectinstance.getAmplifier(), 1.0D);
                        } else {
                            living.addEffect(new EffectInstance(effectinstance));
                        }
                    }
                    if (!brew.tier2Pool.isEmpty()) {
                        EffectInstance effectinstance = brew.tier2Pool.get(rand.nextInt(brew.tier2Pool.size()));
                        if (effectinstance.getEffect().isInstantenous()) {
                            effectinstance.getEffect().applyInstantenousEffect(playerentity, playerentity, living, effectinstance.getAmplifier(), 1.0D);
                        } else {
                            living.addEffect(new EffectInstance(effectinstance));
                        }
                    }
                    if (!brew.tier3Pool.isEmpty()) {
                        EffectInstance effectinstance = brew.tier3Pool.get(rand.nextInt(brew.tier3Pool.size()));
                        if (effectinstance.getEffect().isInstantenous()) {
                            effectinstance.getEffect().applyInstantenousEffect(playerentity, playerentity, living, effectinstance.getAmplifier(), 1.0D);
                        } else {
                            living.addEffect(new EffectInstance(effectinstance));
                        }
                    }
                }
            }
        }

        if (playerentity != null) {
            playerentity.awardStat(Stats.ITEM_USED.get(this));
            if (!playerentity.abilities.instabuild) {
                stack.shrink(1);
            }
        }

        if (playerentity == null || !playerentity.abilities.instabuild) {
            if (stack.isEmpty()) {
                return new ItemStack(drinkResult);
            }

            if (playerentity != null) {
                playerentity.inventory.add(new ItemStack(drinkResult));
            }
        }

        return stack;
    }

    public int getUseDuration(ItemStack stack) {
        return filled? 32 : 0;
    }

    public UseAction getUseAnimation(ItemStack stack) {
        return filled? UseAction.DRINK : UseAction.NONE;
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        return filled? DrinkHelper.useDrink(world, player, hand) : ActionResult.pass(player.getItemInHand(hand));
    }
}
