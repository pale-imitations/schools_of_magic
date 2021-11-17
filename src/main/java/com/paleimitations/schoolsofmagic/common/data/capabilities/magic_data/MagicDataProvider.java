package com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.config.Config;
import com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data.IQuestData;
import com.paleimitations.schoolsofmagic.common.network.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MagicDataProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(IMagicData.class)
    public static Capability<IMagicData> MAGIC_DATA_CAPABILITY = null;

    private final LazyOptional<IMagicData> instance = LazyOptional.of(MAGIC_DATA_CAPABILITY::getDefaultInstance);

    public static final ResourceLocation ID = new ResourceLocation(References.MODID, "magic_data");

    public static final Direction DIRECTION = null;

    public static void register() {
        CapabilityManager.INSTANCE
                .register(IMagicData.class, new Capability.IStorage<IMagicData>(){
            @Nullable
            @Override
            public INBT writeNBT(Capability<IMagicData> capability, IMagicData instance, Direction side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IMagicData> capability, IMagicData instance, Direction side, INBT nbt) {
                instance.deserializeNBT((CompoundNBT)nbt);
            }
        }, MagicData::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == MAGIC_DATA_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return MAGIC_DATA_CAPABILITY.getStorage().writeNBT(MAGIC_DATA_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), DIRECTION);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        MAGIC_DATA_CAPABILITY.getStorage().readNBT(MAGIC_DATA_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), DIRECTION, nbt);
    }

    @Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class Events {

        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof PlayerEntity) {
                event.addCapability(ID, new MagicDataProvider());
            }
        }

        @SubscribeEvent
        public static void onClone(PlayerEvent.Clone event) {
            IMagicData original = event.getOriginal().getCapability(MAGIC_DATA_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
            IMagicData clone = event.getPlayer().getCapability(MAGIC_DATA_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));

            clone.deserializeNBT(original.serializeNBT());
        }

        @SubscribeEvent
        public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
            if(event.getPlayer() instanceof ServerPlayerEntity) {
                if(Config.Common.SHOW_PACKET_MESSAGES.get())
                    System.out.println("Magic Data sent For: " + event.getPlayer().getGameProfile().getName() + ", To: all tracking, Reason: respawn");
                IMagicData data = event.getPlayer().getCapability(MAGIC_DATA_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
                PacketHandler.sendToTracking(new UpdateMagicDataPacket(event.getPlayer().getId(), data.serializeNBT()), (ServerPlayerEntity) event.getPlayer());
            }
        }

        @SubscribeEvent
        public static void joinGame(PlayerEvent.PlayerLoggedInEvent event) {
            if(event.getPlayer() instanceof ServerPlayerEntity) {
                if(Config.Common.SHOW_PACKET_MESSAGES.get())
                    System.out.println("Magic Data sent For: " + event.getPlayer().getGameProfile().getName()+", To: all tracking, Reason: join game");
                IMagicData data = event.getPlayer().getCapability(MAGIC_DATA_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
                PacketHandler.sendToTracking(new UpdateMagicDataPacket(event.getPlayer().getId(), data.serializeNBT()), (ServerPlayerEntity) event.getPlayer());
            }
        }

        @SubscribeEvent
        public static void changeDimEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
            if(event.getPlayer() instanceof ServerPlayerEntity) {
                if(Config.Common.SHOW_PACKET_MESSAGES.get())
                    System.out.println("Magic Data sent For: " + event.getPlayer().getGameProfile().getName() + ", To: all tracking, Reason: dimension change");
                IMagicData data = event.getPlayer().getCapability(MAGIC_DATA_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
                PacketHandler.sendToTracking(new UpdateMagicDataPacket(event.getPlayer().getId(), data.serializeNBT()), (ServerPlayerEntity) event.getPlayer());
            }
        }

        @SubscribeEvent
        public static void startTracking(PlayerEvent.StartTracking event) {
            if(event.getPlayer() instanceof ServerPlayerEntity) {
                if(event.getTarget() instanceof PlayerEntity) {
                    PlayerEntity target = (PlayerEntity) event.getTarget();
                    if(Config.Common.SHOW_PACKET_MESSAGES.get())
                        System.out.println("Magic Data sent For: " + target.getGameProfile().getName() + ", To: "+event.getPlayer().getGameProfile().getName()+", Reason: start tracking");
                    IMagicData data = event.getPlayer().getCapability(MAGIC_DATA_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
                    PacketHandler.sendTo(new UpdateMagicDataPacket(target.getId(), data.serializeNBT()), (ServerPlayerEntity) event.getPlayer());
                }
            }
        }

        @SubscribeEvent
        public static void onUpdate(LivingEvent.LivingUpdateEvent event) {
            LivingEntity entity = event.getEntityLiving();
            if(entity instanceof PlayerEntity) {
                LazyOptional<IMagicData> lazyOptional = entity.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY);
                IMagicData data = lazyOptional.orElseThrow(IllegalStateException::new);
                data.update((PlayerEntity) entity);
            }
        }
    }
}
