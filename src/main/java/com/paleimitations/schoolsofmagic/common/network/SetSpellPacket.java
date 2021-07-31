package com.paleimitations.schoolsofmagic.common.network;

import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.IMagicData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.MagicDataProvider;
import com.paleimitations.schoolsofmagic.common.registries.SpellRegistry;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SetSpellPacket<MSG> {

    private ResourceLocation spellLocation;
    private CompoundNBT spellData;
    private int entityID, spellSlot;

    public SetSpellPacket(ResourceLocation spellLocation, CompoundNBT spellData, int entityID, int spellSlot) {
        this.spellLocation = spellLocation;
        this.spellData = spellData;
        this.entityID = entityID;
        this.spellSlot = spellSlot;
    }

    public static SetSpellPacket decode(PacketBuffer buf) {
        return new SetSpellPacket(new ResourceLocation(buf.readUtf(32767)), buf.readNbt(), buf.readInt(), buf.readInt());
    }

    public static void encode(SetSpellPacket pkt, PacketBuffer buf) {
        buf.writeUtf(pkt.spellLocation.toString());
        buf.writeNbt(pkt.spellData);
        buf.writeInt(pkt.entityID);
        buf.writeInt(pkt.spellSlot);
    }

    public static void handle(final SetSpellPacket pkt, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            Entity entity = ctx.getSender().level.getEntity(pkt.entityID);
            if(entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                IMagicData data = player.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY, null).orElseThrow(IllegalStateException::new);
                Spell spell = SpellRegistry.getSpell(pkt.spellLocation.toString());
                if(spell!=null) {
                    spell.deserializeNBT(pkt.spellData);
                    data.setSpell(pkt.spellSlot, spell);
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
