package com.paleimitations.schoolsofmagic.common.network;

import com.paleimitations.schoolsofmagic.common.tileentities.MortarTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientTileEntityPacket<MSG> {

    private CompoundNBT nbt;
    private BlockPos pos;

    public ClientTileEntityPacket(BlockPos pos, CompoundNBT nbt) {
        this.pos = pos;
        this.nbt = nbt;
    }

    public static ClientTileEntityPacket decode(PacketBuffer buf) {
        return new ClientTileEntityPacket(BlockPos.of(buf.readLong()), buf.readNbt());
    }

    public static void encode(ClientTileEntityPacket pkt, PacketBuffer buf) {
        buf.writeLong(pkt.pos.asLong());
        buf.writeNbt(pkt.nbt);
    }

    public static void handle(final ClientTileEntityPacket pkt, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            TileEntity entity = ctx.getSender().level.getBlockEntity(pkt.pos);
            if(entity != null) {
                entity.load(ctx.getSender().level.getBlockState(pkt.pos), pkt.nbt);
                entity.setChanged();
            }
        });
        ctx.setPacketHandled(true);
    }
}
