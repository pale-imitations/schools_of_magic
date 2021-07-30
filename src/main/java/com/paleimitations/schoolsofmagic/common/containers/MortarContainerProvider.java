package com.paleimitations.schoolsofmagic.common.containers;

import com.paleimitations.schoolsofmagic.common.registries.ContainerRegistry;
import com.paleimitations.schoolsofmagic.common.tileentities.MortarTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.network.IContainerFactory;

public class MortarContainerProvider extends AbstractContainerProvider<MortarContainer> {

    private final MortarTileEntity mortar;

    public MortarContainerProvider(MortarTileEntity mortar) {
        super(ContainerRegistry.MORTAR_CONTAINER.get());
        this.mortar = mortar;
    }

    @Override
    public ITextComponent getDisplayName() {
        return mortar.getDisplayName();
    }

    @Override
    public MortarContainer createMenu(int id, PlayerInventory plInventory, PlayerEntity player) {
        return (MortarContainer) mortar.createMenu(id, plInventory, player);
    }

    public static MortarContainer createFromPacket(int id, PlayerInventory plInventory, PacketBuffer data) {
        BlockPos pos = BlockPos.of(data.readLong());
        PlayerEntity player = plInventory.player;
        if(player.level.getBlockEntity(pos) instanceof MortarTileEntity) {
            return (MortarContainer) ((MortarTileEntity) player.level.getBlockEntity(pos)).createMenu(id, plInventory, player);
        }
        return null;
    }

    @Override
    protected void writeExtraData(PacketBuffer buf) {
        buf.writeLong(mortar.getBlockPos().asLong());
    }

    public static class Factory implements IContainerFactory<MortarContainer> {

        @Override
        public MortarContainer create(int windowId, PlayerInventory inv, PacketBuffer data) {
            return MortarContainerProvider.createFromPacket(windowId, inv, data);
        }
    }
}
