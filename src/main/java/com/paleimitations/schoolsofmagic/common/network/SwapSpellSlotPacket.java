package com.paleimitations.schoolsofmagic.common.network;

import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.IMagicData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.MagicDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SwapSpellSlotPacket {

    private int entityID, spellSlot;

    public SwapSpellSlotPacket(int entityID, int spellSlot) {
        this.entityID = entityID;
        this.spellSlot = spellSlot;
    }

    public static SwapSpellSlotPacket decode(PacketBuffer buf) {
        return new SwapSpellSlotPacket(buf.readInt(), buf.readInt());
    }

    public static void encode(SwapSpellSlotPacket pkt, PacketBuffer buf) {
        buf.writeInt(pkt.entityID);
        buf.writeInt(pkt.spellSlot);
    }

    public static void handle(final SwapSpellSlotPacket pkt, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            Entity entity = ctx.getSender().level.getEntity(pkt.entityID);
            if(entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                IMagicData data = player.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY, null).orElseThrow(IllegalStateException::new);
                data.setCurrentSpellSlot(pkt.spellSlot);
            }
        });
        ctx.setPacketHandled(true);
    }
}
