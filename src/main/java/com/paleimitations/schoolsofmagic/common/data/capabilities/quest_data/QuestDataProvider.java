package com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.IMagicData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.MagicData;
import com.paleimitations.schoolsofmagic.common.network.PacketHandler;
import com.paleimitations.schoolsofmagic.common.network.UpdateMagicDataPacket;
import com.paleimitations.schoolsofmagic.common.network.UpdateQuestDataPacket;
import com.paleimitations.schoolsofmagic.common.quests.Quest;
import com.paleimitations.schoolsofmagic.common.quests.Task;
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
import net.minecraftforge.event.brewing.PlayerBrewedPotionEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class QuestDataProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(IQuestData.class)
    public static Capability<IQuestData> QUEST_DATA_CAPABILITY = null;

    private final LazyOptional<IQuestData> instance = LazyOptional.of(QUEST_DATA_CAPABILITY::getDefaultInstance);

    public static final ResourceLocation ID = new ResourceLocation(References.MODID, "quest_data");

    public static final Direction DIRECTION = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IQuestData.class, new Capability.IStorage<IQuestData>(){
            @Nullable
            @Override
            public INBT writeNBT(Capability<IQuestData> capability, IQuestData instance, Direction side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IQuestData> capability, IQuestData instance, Direction side, INBT nbt) {
                instance.deserializeNBT((CompoundNBT)nbt);
            }
        }, QuestData::new);
    }

    public static IQuestData getData(PlayerEntity player) {
        return player.getCapability(QuestDataProvider.QUEST_DATA_CAPABILITY).orElseThrow(IllegalStateException::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == QUEST_DATA_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return QUEST_DATA_CAPABILITY.getStorage().writeNBT(QUEST_DATA_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), DIRECTION);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        QUEST_DATA_CAPABILITY.getStorage().readNBT(QUEST_DATA_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), DIRECTION, nbt);
    }

    @Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class Events {

        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof PlayerEntity) {
                event.addCapability(ID, new QuestDataProvider());
            }
        }

        @SubscribeEvent
        public static void onClone(PlayerEvent.Clone event) {
            IQuestData original = event.getOriginal().getCapability(QUEST_DATA_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
            IQuestData clone = event.getPlayer().getCapability(QUEST_DATA_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));

            clone.deserializeNBT(original.serializeNBT());
        }

        @SubscribeEvent
        public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
            if(event.getPlayer() instanceof ServerPlayerEntity) {
                System.out.println("Quest Data sent For: " + event.getPlayer().getGameProfile().getName() + ", To: all tracking, Reason: respawn");
                IQuestData data = event.getPlayer().getCapability(QUEST_DATA_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
                PacketHandler.sendToTracking(new UpdateQuestDataPacket(event.getPlayer().getId(), data.serializeNBT()), (ServerPlayerEntity) event.getPlayer());
            }
        }

        @SubscribeEvent
        public static void joinGame(PlayerEvent.PlayerLoggedInEvent event) {
            if(event.getPlayer() instanceof ServerPlayerEntity) {
                System.out.println("Quest Data sent For: " + event.getPlayer().getGameProfile().getName()+", To: all tracking, Reason: join game");
                IQuestData data = event.getPlayer().getCapability(QUEST_DATA_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
                PacketHandler.sendToTracking(new UpdateQuestDataPacket(event.getPlayer().getId(), data.serializeNBT()), (ServerPlayerEntity) event.getPlayer());
            }
        }

        @SubscribeEvent
        public static void changeDimEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
            if(event.getPlayer() instanceof ServerPlayerEntity) {
                System.out.println("Quest Data sent For: " + event.getPlayer().getGameProfile().getName() + ", To: all tracking, Reason: dimension change");
                IQuestData data = event.getPlayer().getCapability(QUEST_DATA_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
                PacketHandler.sendToTracking(new UpdateQuestDataPacket(event.getPlayer().getId(), data.serializeNBT()), (ServerPlayerEntity) event.getPlayer());
            }
        }

        @SubscribeEvent
        public static void startTracking(PlayerEvent.StartTracking event) {
            if(event.getPlayer() instanceof ServerPlayerEntity) {
                if(event.getTarget() instanceof PlayerEntity) {
                    PlayerEntity target = (PlayerEntity) event.getTarget();
                    System.out.println("Quest Data sent For: " + target.getGameProfile().getName() + ", To: "+event.getPlayer().getGameProfile().getName()+", Reason: start tracking");
                    IQuestData data = event.getPlayer().getCapability(QUEST_DATA_CAPABILITY, null).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
                    PacketHandler.sendTo(new UpdateQuestDataPacket(target.getId(), data.serializeNBT()), (ServerPlayerEntity) event.getPlayer());
                }
            }
        }

        @SubscribeEvent
        public static void onUpdate(LivingEvent.LivingUpdateEvent event) {
            LivingEntity entity = event.getEntityLiving();
            if(entity instanceof PlayerEntity) {
                LazyOptional<IQuestData> lazyOptional = entity.getCapability(QuestDataProvider.QUEST_DATA_CAPABILITY);
                IQuestData data = lazyOptional.orElseThrow(IllegalStateException::new);
                data.updateQuests((PlayerEntity) entity);
            }
        }

        @SubscribeEvent
        public static void potionEvent(PlayerBrewedPotionEvent event) {
            PlayerEntity player = event.getPlayer();
            LazyOptional<IQuestData> lazyOptional = player.getCapability(QuestDataProvider.QUEST_DATA_CAPABILITY);
            IQuestData data = lazyOptional.orElseThrow(IllegalStateException::new);
            for(Quest quest : data.getQuests()) {
                for(Task task : quest.tasks) {
                    if(task.taskType == Task.EnumTaskType.POTION_BREW) {
                        task.checkEvent(player, event);
                    }
                }
            }
        }

        @SubscribeEvent
        public static void buildEvent(BlockEvent.EntityPlaceEvent event) {
            if(event.getEntity() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) event.getEntity();
                LazyOptional<IQuestData> lazyOptional = player.getCapability(QuestDataProvider.QUEST_DATA_CAPABILITY);
                IQuestData data = lazyOptional.orElseThrow(IllegalStateException::new);
                for(Quest quest : data.getQuests()) {
                    for(Task task : quest.tasks) {
                        if(task.taskType == Task.EnumTaskType.BUILD) {
                            task.checkEvent(player, event);
                        }
                    }
                }
            }
        }
    }
}
