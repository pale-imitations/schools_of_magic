package com.paleimitations.schoolsofmagic.common.network;

import com.paleimitations.schoolsofmagic.common.items.BookBaseItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TurnPageEntityPacket<MSG> {

    private int page, subpage, entityID;
    private Hand hand;

    public TurnPageEntityPacket(int page, int subpage, int entityID, Hand hand) {
        this.page = page;
        this.subpage = subpage;
        this.entityID = entityID;
        this.hand = hand;
    }

    public static TurnPageEntityPacket decode(PacketBuffer buf) {
        return new TurnPageEntityPacket(buf.readInt(), buf.readInt(), buf.readInt(), Hand.values()[buf.readInt()]);
    }

    public static void encode(TurnPageEntityPacket pkt, PacketBuffer buf) {
        buf.writeInt(pkt.page);
        buf.writeInt(pkt.subpage);
        buf.writeInt(pkt.entityID);
        buf.writeInt(pkt.hand.ordinal());
    }

    public static void handle(final TurnPageEntityPacket pkt, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            Entity entity = ctx.getSender().level.getEntity(pkt.entityID);
            if(entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                ItemStack stack = player.getItemInHand(pkt.hand);
                if(stack.getItem() instanceof BookBaseItem) {
                    CompoundNBT nbt = stack.getOrCreateTag();
                    nbt.putInt("page", pkt.page);
                    nbt.putInt("subpage", pkt.subpage);
                    stack.setTag(nbt);
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
