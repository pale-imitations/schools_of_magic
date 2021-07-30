package com.paleimitations.schoolsofmagic.common.network;

import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.IMagicData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.MagicDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateMagicDataPacket<MSG> {

    private int entityID;
    private CompoundNBT data;

    public UpdateMagicDataPacket(int entityID, CompoundNBT data) {
        this.entityID = entityID;
        this.data = data;
    }

    public static UpdateMagicDataPacket decode(PacketBuffer buf) {
        return new UpdateMagicDataPacket(buf.readInt(), buf.readNbt());
    }

    public static void encode(UpdateMagicDataPacket pkt, PacketBuffer buf) {
        buf.writeInt(pkt.entityID);
        buf.writeNbt(pkt.data);
    }

    public static void handle(final UpdateMagicDataPacket pkt, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            if(ctx.getDirection().getReceptionSide().isClient()) {
                Entity entity = Minecraft.getInstance().level.getEntity(pkt.entityID);
                if (entity instanceof PlayerEntity) {
                    System.out.println("Magic Data recieved for: " + ((PlayerEntity)entity).getGameProfile().getName());
                    IMagicData data = entity.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY, null).orElseThrow(IllegalStateException::new);
                    data.deserializeNBT(pkt.data);
                }
                else {
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
