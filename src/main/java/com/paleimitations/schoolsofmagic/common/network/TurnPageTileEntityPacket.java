package com.paleimitations.schoolsofmagic.common.network;

import com.paleimitations.schoolsofmagic.common.items.BookBaseItem;
import com.paleimitations.schoolsofmagic.common.tileentities.PodiumTileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TurnPageTileEntityPacket<MSG> {

    private int page, subpage, entityID;
    private BlockPos pos;

    public TurnPageTileEntityPacket(int page, int subpage, int entityID, BlockPos pos) {
        this.page = page;
        this.subpage = subpage;
        this.entityID = entityID;
        this.pos = pos;
    }

    public static TurnPageTileEntityPacket decode(PacketBuffer buf) {
        return new TurnPageTileEntityPacket(buf.readInt(), buf.readInt(), buf.readInt(), BlockPos.of(buf.readLong()));
    }

    public static void encode(TurnPageTileEntityPacket pkt, PacketBuffer buf) {
        buf.writeInt(pkt.page);
        buf.writeInt(pkt.subpage);
        buf.writeInt(pkt.entityID);
        buf.writeLong(pkt.pos.asLong());
    }

    public static void handle(final TurnPageTileEntityPacket pkt, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            TileEntity entity = ctx.getSender().level.getBlockEntity(pkt.pos);
            if(entity instanceof PodiumTileEntity) {
                PodiumTileEntity podium = (PodiumTileEntity) entity;
                ItemStack stack = podium.getItem();
                if(stack.getItem() instanceof BookBaseItem) {
                    CompoundNBT nbt = stack.getOrCreateTag();
                    nbt.putInt("page", pkt.page);
                    nbt.putInt("subpage", pkt.subpage);
                    stack.setTag(nbt);
                }
                podium.setItem(stack);
            }
        });
        ctx.setPacketHandled(true);
    }
}
