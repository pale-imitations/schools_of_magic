package com.paleimitations.schoolsofmagic.common.network;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data.IQuestData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data.QuestDataProvider;
import com.paleimitations.schoolsofmagic.common.quests.Quest;
import com.paleimitations.schoolsofmagic.common.quests.QuestHelper;
import com.paleimitations.schoolsofmagic.common.quests.quests.QuestEnchantItem;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class LetterPacket {

    private int entityID;
    private Hand hand;
    private int eventType;

    public LetterPacket(int entityID, Hand hand, int eventType) {
        this.entityID = entityID;
        this.hand = hand;
        this.eventType = eventType;
    }

    public static LetterPacket decode(PacketBuffer buf) {
        return new LetterPacket(buf.readInt(), Hand.values()[buf.readInt()], buf.readInt());
    }

    public static void encode(LetterPacket pkt, PacketBuffer buf) {
        buf.writeInt(pkt.entityID);
        buf.writeInt(pkt.hand.ordinal());
        buf.writeInt(pkt.eventType);
    }

    public static void handle(final LetterPacket pkt, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            Entity entity = ctx.getSender().level.getEntity(pkt.entityID);
            if(entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                ItemStack stack = player.getItemInHand(pkt.hand);
                CompoundNBT nbt = stack.getOrCreateTag();
                if(stack.getItem() == ItemRegistry.LETTER_CCW.get()) {
                    if(pkt.eventType < 2) {
                        nbt.putBoolean("opened", pkt.eventType == 0);
                        if(pkt.eventType==0) {
                            player.playSound(SoundEvents.CHICKEN_EGG, 0.1f, 1f);
                        }
                        else {
                            player.playSound(SoundEvents.BOOK_PAGE_TURN, 0.1f, 1f);
                        }
                    }
                    if(pkt.eventType == 0 && !nbt.contains("quest"))
                        nbt.putBoolean("quest", true);
                    if(pkt.eventType == 2 && nbt.contains("quest") && nbt.getBoolean("quest")) {
                        nbt.putBoolean("quest", false);
                        ItemStack questNote = new ItemStack(ItemRegistry.QUEST_NOTE.get());
                        CompoundNBT nbtQ = questNote.getOrCreateTag();
                        nbtQ.putString("quest", getRandomQuest(player.getRandom()).toString());
                        questNote.setTag(nbtQ);
                        player.level.addFreshEntity(new ItemEntity(player.level, player.getX(), player.getY() + 1, player.getZ(), questNote));
                        player.playSound(SoundEvents.BOOK_PAGE_TURN, 0.1f, 1f);
                    }
                    stack.setTag(nbt);
                }
            }
        });
        ctx.setPacketHandled(true);
    }

    private static ResourceLocation getRandomQuest(Random rand) {
        switch(rand.nextInt(3)) {
            case 0: return new ResourceLocation(References.MODID, "brew_potion");
            case 1: return new ResourceLocation(References.MODID, "enchant_item");
            case 2: return  new ResourceLocation(References.MODID, "build_golem");
        }
        return new QuestEnchantItem().getResourceLocation();
    }
}
