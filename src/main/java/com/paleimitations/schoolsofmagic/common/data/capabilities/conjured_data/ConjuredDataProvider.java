package com.paleimitations.schoolsofmagic.common.data.capabilities.conjured_data;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data.IQuestData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data.QuestDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ConjuredDataProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(IConjuredData.class)
    public static Capability<IConjuredData> CONJURED_DATA_CAPABILITY = null;

    private final LazyOptional<IConjuredData> instance = LazyOptional.of(CONJURED_DATA_CAPABILITY::getDefaultInstance);

    public static final ResourceLocation ID = new ResourceLocation(References.MODID, "conjured_data");

    public static final Direction DIRECTION = null;

    public static void register() {
        CapabilityManager.INSTANCE
                .register(IConjuredData.class, new Capability.IStorage<IConjuredData>(){
                    @Nullable
                    @Override
                    public INBT writeNBT(Capability<IConjuredData> capability, IConjuredData instance, Direction side) {
                        return instance.serializeNBT();
                    }

                    @Override
                    public void readNBT(Capability<IConjuredData> capability, IConjuredData instance, Direction side, INBT nbt) {
                        instance.deserializeNBT((CompoundNBT)nbt);
                    }
                }, ConjuredData::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CONJURED_DATA_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CONJURED_DATA_CAPABILITY.getStorage().writeNBT(CONJURED_DATA_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), DIRECTION);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CONJURED_DATA_CAPABILITY.getStorage().readNBT(CONJURED_DATA_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), DIRECTION, nbt);
    }

    @Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class Events {

        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof LivingEntity && !(event.getObject() instanceof PlayerEntity)) {
                event.addCapability(ID, new ConjuredDataProvider());
            }
        }

        @SubscribeEvent
        public static void onDeathEvent(LivingDeathEvent event) {
            if(event.getEntityLiving().getCapability(ConjuredDataProvider.CONJURED_DATA_CAPABILITY).isPresent()) {
                IConjuredData data = event.getEntityLiving().getCapability(ConjuredDataProvider.CONJURED_DATA_CAPABILITY).orElse(null);
                if (data !=null && data.isConjured()) {
                    event.getEntityLiving().remove();
                }
            }
        }

        @SubscribeEvent
        public static void onUpdate(LivingEvent.LivingUpdateEvent event) {
            if(!(event.getEntityLiving() instanceof PlayerEntity) && event.getEntityLiving().getCapability(ConjuredDataProvider.CONJURED_DATA_CAPABILITY).isPresent()) {
                IConjuredData data = event.getEntityLiving().getCapability(ConjuredDataProvider.CONJURED_DATA_CAPABILITY).orElse(null);
                if (data.isConjured() && data.useCountdown()) {
                    int countdown = data.getCountdown() - 1;
                    data.setCountdown(countdown);
                    if (countdown <= 0) {
                        event.getEntityLiving().remove();
                    }
                }
            }
        }

    }
}
