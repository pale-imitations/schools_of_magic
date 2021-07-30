package com.paleimitations.schoolsofmagic.common.network;

import com.paleimitations.schoolsofmagic.References;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(References.MODID,"main"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    private static int ID = 0;

    private static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE.registerMessage(nextID(), UpdateMagicDataPacket.class, UpdateMagicDataPacket::encode, UpdateMagicDataPacket::decode, UpdateMagicDataPacket::handle);
        INSTANCE.registerMessage(nextID(), UpdateQuestDataPacket.class, UpdateQuestDataPacket::encode, UpdateQuestDataPacket::decode, UpdateQuestDataPacket::handle);
        INSTANCE.registerMessage(nextID(), UpdateQuestPacket.class, UpdateQuestPacket::encode, UpdateQuestPacket::decode, UpdateQuestPacket::handle);
        INSTANCE.registerMessage(nextID(), TurnPageEntityPacket.class, TurnPageEntityPacket::encode, TurnPageEntityPacket::decode, TurnPageEntityPacket::handle);
        INSTANCE.registerMessage(nextID(), TurnPageTileEntityPacket.class, TurnPageTileEntityPacket::encode, TurnPageTileEntityPacket::decode, TurnPageTileEntityPacket::handle);
        INSTANCE.registerMessage(nextID(), GetBookDataPacket.class, GetBookDataPacket::encode, GetBookDataPacket::decode, GetBookDataPacket::handle);
        INSTANCE.registerMessage(nextID(), ReturnBookDataPacket.class, ReturnBookDataPacket::encode, ReturnBookDataPacket::decode, ReturnBookDataPacket::handle);
        INSTANCE.registerMessage(nextID(), SetSpellPacket.class, SetSpellPacket::encode, SetSpellPacket::decode, SetSpellPacket::handle);
        INSTANCE.registerMessage(nextID(), SwapSpellSlotPacket.class, SwapSpellSlotPacket::encode, SwapSpellSlotPacket::decode, SwapSpellSlotPacket::handle);
        INSTANCE.registerMessage(nextID(), SwapSpellChargePacket.class, SwapSpellChargePacket::encode, SwapSpellChargePacket::decode, SwapSpellChargePacket::handle);
        INSTANCE.registerMessage(nextID(), QuestNotePacket.class, QuestNotePacket::encode, QuestNotePacket::decode, QuestNotePacket::handle);
        INSTANCE.registerMessage(nextID(), LetterPacket.class, LetterPacket::encode, LetterPacket::decode, LetterPacket::handle);
        INSTANCE.registerMessage(nextID(), ClientTileEntityPacket.class, ClientTileEntityPacket::encode, ClientTileEntityPacket::decode, ClientTileEntityPacket::handle);
    }

    public static void sendTo(Object message, ServerPlayerEntity player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(()->player), message);
        //INSTANCE.sendTo(message, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToAll(Object message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

    public static void sendToTracking(Object message, ServerPlayerEntity player) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), message);
    }

    public static void sendToAllInChunk(Object message, Chunk chunk) {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), message);
    }

    public static void sendToServer(Object message) {
        INSTANCE.sendToServer(message);
    }
}
