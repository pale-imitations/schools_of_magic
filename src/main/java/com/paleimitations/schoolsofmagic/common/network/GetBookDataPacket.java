package com.paleimitations.schoolsofmagic.common.network;

import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookDataProvider;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.IMagicData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.MagicDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class GetBookDataPacket<MSG> {

    private String bookID;
    private int entityID;

    public GetBookDataPacket(int entityID, String bookID) {
        this.entityID = entityID;
        this.bookID = bookID;
    }

    public static GetBookDataPacket decode(PacketBuffer buf) {
        return new GetBookDataPacket(buf.readInt(), buf.readUtf(32767));
    }

    public static void encode(GetBookDataPacket pkt, PacketBuffer buf) {
        buf.writeInt(pkt.entityID);
        buf.writeUtf(pkt.bookID);
    }

    public static void handle(final GetBookDataPacket pkt, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            BookData data = BookDataProvider.getBook(ctx.getSender().level, pkt.bookID);
            Entity entity = ctx.getSender().level.getEntity(pkt.entityID);
            if(entity instanceof ServerPlayerEntity)
                PacketHandler.sendTo(new ReturnBookDataPacket(pkt.bookID, data.save(new CompoundNBT())), (ServerPlayerEntity)entity);
        });
        ctx.setPacketHandled(true);
    }
}