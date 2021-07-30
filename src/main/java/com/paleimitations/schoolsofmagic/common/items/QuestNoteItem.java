package com.paleimitations.schoolsofmagic.common.items;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.SchoolsOfMagicMod;
import com.paleimitations.schoolsofmagic.common.data.BindingType;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data.IQuestData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data.QuestDataProvider;
import com.paleimitations.schoolsofmagic.common.quests.Quest;
import com.paleimitations.schoolsofmagic.common.quests.QuestHelper;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import com.paleimitations.schoolsofmagic.common.registries.QuestRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class QuestNoteItem extends Item {

    public QuestNoteItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            for(Quest quest : QuestRegistry.QUESTS) {
                ItemStack stack = new ItemStack(this);
                CompoundNBT nbt = stack.getOrCreateTag();
                nbt.putString("quest", quest.getResourceLocation().toString());
                stack.setTag(nbt);
                items.add(stack);
            }
        }
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(!player.getItemInHand(hand).getOrCreateTag().hasUUID("quest_giver")){
            ItemStack stack = player.getItemInHand(hand);
            if(!world.isClientSide) {
                CompoundNBT nbt = stack.getOrCreateTag();
                nbt.putUUID("quest_giver", MathHelper.createInsecureUUID(player.getRandom()));
                stack.setTag(nbt);
                return ActionResult.sidedSuccess(stack, false);
            }
            else
                return ActionResult.pass(stack);
        }
        ItemStack stack = player.getItemInHand(hand);
        CompoundNBT nbt = stack.getOrCreateTag();
        player.playSound(SoundEvents.BOOK_PAGE_TURN, 0.1f, 1f);
        if(nbt.hasUUID("quest_giver") && nbt.contains("quest")) {
            IQuestData data = QuestDataProvider.getData(player);
            Quest q;
            if(data.hasQuest(nbt.getUUID("quest_giver"))) {
                q = data.getQuestbyQuestGiver(nbt.getUUID("quest_giver"));
            }
            else
                q = QuestHelper.getNewInstance(new ResourceLocation(nbt.getString("quest")));
            if(q!=null)
                SchoolsOfMagicMod.getProxy().openQuest(player, stack, q, hand);
        }
        return ActionResult.pass(stack);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag tooltipFlag) {
        CompoundNBT nbt = stack.getOrCreateTag();
        if(nbt.contains("quest")) {
            IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent("quest."+nbt.getString("quest")+".name");
            list.add(iformattabletextcomponent.withStyle(TextFormatting.GOLD));
        }
    }
}
