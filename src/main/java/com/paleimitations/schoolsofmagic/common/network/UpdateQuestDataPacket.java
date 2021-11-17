package com.paleimitations.schoolsofmagic.common.network;

import com.paleimitations.schoolsofmagic.common.config.Config;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.IMagicData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.MagicDataProvider;
import com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data.IQuestData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data.QuestDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateQuestDataPacket<MSG> {

    private int entityID;
    private CompoundNBT data;

    public UpdateQuestDataPacket(int entityID, CompoundNBT data) {
        this.entityID = entityID;
        this.data = data;
    }

    public static UpdateQuestDataPacket decode(PacketBuffer buf) {
        return new UpdateQuestDataPacket(buf.readInt(), buf.readNbt());
    }

    public static void encode(UpdateQuestDataPacket pkt, PacketBuffer buf) {
        buf.writeInt(pkt.entityID);
        buf.writeNbt(pkt.data);
    }

    public static void handle(final UpdateQuestDataPacket pkt, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            if(ctx.getDirection().getReceptionSide().isClient()) {
                Entity entity = Minecraft.getInstance().level.getEntity(pkt.entityID);
                if (entity instanceof PlayerEntity) {
                    if(Config.Client.SHOW_PACKET_MESSAGES.get())
                        System.out.println("Quest Data recieved for: " + ((PlayerEntity)entity).getGameProfile().getName());
                    IQuestData data = entity.getCapability(QuestDataProvider.QUEST_DATA_CAPABILITY, null).orElseThrow(IllegalStateException::new);
                    data.deserializeNBT(pkt.data);
                }
                else {
                    if(Config.Client.SHOW_PACKET_MESSAGES.get())
                        System.out.println("Broken Packet");
                }
            }
            else {
                System.out.println("Packet Sent to Wrong Side");
            }
        });
        ctx.setPacketHandled(true);
    }
}
