package com.paleimitations.schoolsofmagic.common.network;

import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookDataProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ReturnBookDataPacket<MSG> {

    private String bookID;
    private CompoundNBT bookData;

    public ReturnBookDataPacket(String bookId, CompoundNBT bookData) {
        this.bookData = bookData;
        this.bookID = bookId;
    }

    public static ReturnBookDataPacket decode(PacketBuffer buf) {
        return new ReturnBookDataPacket(buf.readUtf(32767), buf.readNbt());
    }

    public static void encode(ReturnBookDataPacket pkt, PacketBuffer buf) {
        buf.writeUtf(pkt.bookID);
        buf.writeNbt(pkt.bookData);
    }

    public static void handle(final ReturnBookDataPacket pkt, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            BookData data = new BookData(pkt.bookID);
            data.load(pkt.bookData);
            BookDataProvider.addBookData(pkt.bookID, data);
        });
        ctx.setPacketHandled(true);
    }
}