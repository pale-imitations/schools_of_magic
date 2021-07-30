package com.paleimitations.schoolsofmagic.common.network;

import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.IMagicData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.MagicDataProvider;
import com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data.IQuestData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data.QuestDataProvider;
import com.paleimitations.schoolsofmagic.common.items.BookBaseItem;
import com.paleimitations.schoolsofmagic.common.items.QuestNoteItem;
import com.paleimitations.schoolsofmagic.common.quests.Quest;
import com.paleimitations.schoolsofmagic.common.quests.QuestHelper;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class QuestNotePacket {

    private int entityID;
    private Hand hand;
    private int eventType;

    public QuestNotePacket(int entityID, Hand hand, int eventType) {
        this.entityID = entityID;
        this.hand = hand;
        this.eventType = eventType;
    }

    public static QuestNotePacket decode(PacketBuffer buf) {
        return new QuestNotePacket(buf.readInt(), Hand.values()[buf.readInt()], buf.readInt());
    }

    public static void encode(QuestNotePacket pkt, PacketBuffer buf) {
        buf.writeInt(pkt.entityID);
        buf.writeInt(pkt.hand.ordinal());
        buf.writeInt(pkt.eventType);
    }

    public static void handle(final QuestNotePacket pkt, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            Entity entity = ctx.getSender().level.getEntity(pkt.entityID);
            if(entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                ItemStack stack = player.getItemInHand(pkt.hand);
                CompoundNBT nbt = stack.getOrCreateTag();
                if(stack.getItem() == ItemRegistry.QUEST_NOTE.get() && nbt.hasUUID("quest_giver") && nbt.contains("quest")) {
                    IQuestData data = QuestDataProvider.getData(player);
                    switch (pkt.eventType) {
                        case 0: {
                            System.out.println("Receiving Quest Note Clear");
                            Quest q = data.getQuestbyQuestGiver(nbt.getUUID("quest_giver"));
                            if(q!=null) {
                                q.dead = true;
                                stack.shrink(1);
                            }
                            else
                                System.out.println("Error Null Quest");
                            break;
                        }
                        case 1: {
                            System.out.println("Receiving Quest Note Claim");
                            Quest q = data.getQuestbyQuestGiver(nbt.getUUID("quest_giver"));
                            if(q!=null) {
                                q.claim(player);
                                stack.shrink(1);
                            }
                            else
                                System.out.println("Error Null Quest");
                            break;
                        }
                        case 2: {
                            System.out.println("Receiving Quest Note Start");
                            if(!data.hasQuest(nbt.getUUID("quest_giver"))) {
                                Quest q = QuestHelper.getNewInstance(new ResourceLocation(nbt.getString("quest")));
                                q.setQuestGiver(nbt.getUUID("quest_giver"));
                                data.addQuest(q);
                            }
                            else
                                System.out.println("Error Already Has This Quest");
                            break;
                        }
                    }
                    if(pkt.eventType>2){
                        System.out.println("Receiving Quest Note Task Start");
                        int i = pkt.eventType-3;
                        Quest q = data.getQuestbyQuestGiver(nbt.getUUID("quest_giver"));
                        if(q!=null && i<q.tasks.size() && q.tasks.get(i)!=null && q.tasks.get(i).canStart(q)) {
                            q.tasks.get(i).setStarted(true);
                        }
                    }
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
