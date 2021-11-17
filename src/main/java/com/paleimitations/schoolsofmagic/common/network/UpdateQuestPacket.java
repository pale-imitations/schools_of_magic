package com.paleimitations.schoolsofmagic.common.network;

import com.paleimitations.schoolsofmagic.common.config.Config;
import com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data.IQuestData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data.QuestDataProvider;
import com.paleimitations.schoolsofmagic.common.quests.Quest;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class UpdateQuestPacket<MSG> {

    private int entityID;
    private UUID questGiver;
    private CompoundNBT data;

    public UpdateQuestPacket(int entityID, UUID questGiver, CompoundNBT data) {
        this.entityID = entityID;
        this.questGiver = questGiver;
        this.data = data;
    }

    public static UpdateQuestPacket decode(PacketBuffer buf) {
        return new UpdateQuestPacket(buf.readInt(), buf.readUUID(), buf.readNbt());
    }

    public static void encode(UpdateQuestPacket pkt, PacketBuffer buf) {
        buf.writeInt(pkt.entityID);
        buf.writeUUID(pkt.questGiver);
        buf.writeNbt(pkt.data);
    }

    public static void handle(final UpdateQuestPacket pkt, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            if(ctx.getDirection().getReceptionSide().isClient()) {
                Entity entity = Minecraft.getInstance().level.getEntity(pkt.entityID);
                if (entity instanceof PlayerEntity) {
                    if(Config.Client.SHOW_PACKET_MESSAGES.get())
                        System.out.println("Quest recieved for: " + ((PlayerEntity)entity).getGameProfile().getName());
                    IQuestData data = entity.getCapability(QuestDataProvider.QUEST_DATA_CAPABILITY, null).orElseThrow(IllegalStateException::new);
                    Quest quest = data.getQuestbyQuestGiver(pkt.questGiver);
                    if(quest!=null) {
                        quest.deserializeNBT(pkt.data);
                    }
                    else {
                        if(Config.Client.SHOW_PACKET_MESSAGES.get())
                            System.out.println("ERROR: Missing Quest");
                    }
                }
                else {
                    if(Config.Client.SHOW_PACKET_MESSAGES.get())
                        System.out.println("ERROR: Broken Packet");
                }
            }
            else {
                System.out.println("ERROR: Packet Sent to Wrong Side");
            }
        });
        ctx.setPacketHandled(true);
    }
}
