package com.paleimitations.schoolsofmagic.common.network;

import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.IMagicData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.MagicDataProvider;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SwapSpellChargePacket {

    private int entityID, spellSlot, chargeLevel;

    public SwapSpellChargePacket(int entityID, int spellSlot, int chargeLevel) {
        this.entityID = entityID;
        this.spellSlot = spellSlot;
        this.chargeLevel = chargeLevel;
    }

    public static SwapSpellChargePacket decode(PacketBuffer buf) {
        return new SwapSpellChargePacket(buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void encode(SwapSpellChargePacket pkt, PacketBuffer buf) {
        buf.writeInt(pkt.entityID);
        buf.writeInt(pkt.spellSlot);
        buf.writeInt(pkt.chargeLevel);
    }

    public static void handle(final SwapSpellChargePacket pkt, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            Entity entity = ctx.getSender().level.getEntity(pkt.entityID);
            if(entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                IMagicData data = player.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY, null).orElseThrow(IllegalStateException::new);
                Spell spell = data.getSpell(pkt.spellSlot);
                if(spell==null) {
                    throw new NullPointerException("Tried to set spell charge for null spell");
                }
                else {
                    spell.currentSpellChargeLevel = pkt.chargeLevel;
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
