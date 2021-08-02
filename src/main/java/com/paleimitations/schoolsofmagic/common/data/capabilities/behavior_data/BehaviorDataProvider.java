package com.paleimitations.schoolsofmagic.common.data.capabilities.behavior_data;

import com.paleimitations.schoolsofmagic.References;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BehaviorDataProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(IBehaviorData.class)
    public static Capability<IBehaviorData> BEHAVIOR_DATA_CAPABILITY = null;

    private final LazyOptional<IBehaviorData> instance = LazyOptional.of(BEHAVIOR_DATA_CAPABILITY::getDefaultInstance);

    public static final ResourceLocation ID = new ResourceLocation(References.MODID, "behavior_data");

    public static final Direction DIRECTION = null;

    public static void register() {
        CapabilityManager.INSTANCE
                .register(IBehaviorData.class, new Capability.IStorage<IBehaviorData>(){
                    @Nullable
                    @Override
                    public INBT writeNBT(Capability<IBehaviorData> capability, IBehaviorData instance, Direction side) {
                        return instance.serializeNBT();
                    }

                    @Override
                    public void readNBT(Capability<IBehaviorData> capability, IBehaviorData instance, Direction side, INBT nbt) {
                        instance.deserializeNBT((CompoundNBT)nbt);
                    }
                }, BehaviorData::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == BEHAVIOR_DATA_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return BEHAVIOR_DATA_CAPABILITY.getStorage().writeNBT(BEHAVIOR_DATA_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), DIRECTION);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        BEHAVIOR_DATA_CAPABILITY.getStorage().readNBT(BEHAVIOR_DATA_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), DIRECTION, nbt);
    }

    @Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class Events {

        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof LivingEntity && !(event.getObject() instanceof PlayerEntity)) {
                event.addCapability(ID, new BehaviorDataProvider());
            }
        }
    }
}
