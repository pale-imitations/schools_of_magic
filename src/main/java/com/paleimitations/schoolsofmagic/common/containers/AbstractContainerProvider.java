package com.paleimitations.schoolsofmagic.common.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class AbstractContainerProvider<C extends Container> implements INamedContainerProvider {

    private final ContainerType<C> type;

    public AbstractContainerProvider(ContainerType<C> type) {
        this.type = type;
    }

    @Override
    public ITextComponent getDisplayName() {
        ResourceLocation key = this.type.getRegistryName();
        return new TranslationTextComponent("screen.%s.%s", key.getNamespace(), key.getPath());
    }

    @Override
    public abstract C createMenu(int id, PlayerInventory plInventory, PlayerEntity player);

    protected abstract void writeExtraData(PacketBuffer buf);

    public void openFor(ServerPlayerEntity player) {
        NetworkHooks.openGui(player, this, this::writeExtraData);
    }
}