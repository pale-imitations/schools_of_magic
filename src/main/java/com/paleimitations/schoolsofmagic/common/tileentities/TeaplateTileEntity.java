package com.paleimitations.schoolsofmagic.common.tileentities;

import com.paleimitations.schoolsofmagic.common.registries.TileEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class TeaplateTileEntity extends TileEntity implements IInventory, ITickableTileEntity {

    private ItemStack item = ItemStack.EMPTY;

    public TeaplateTileEntity() {
        super(TileEntityRegistry.TEAPLATE_TILE_ENTITY.get());
    }

    @Override
    public void setChanged() {
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.level.getBlockState(this.worldPosition), 3);
            this.clearCache();
            this.level.blockEntityChanged(this.worldPosition, this);
            if (!this.getBlockState().isAir(this.level, this.worldPosition)) {
                this.level.updateNeighbourForOutputSignal(this.worldPosition, this.getBlockState().getBlock());
            }
        }
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.item.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return slot == 0 ? this.item : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int count) {
        if (slot == 0) {
            this.setChanged();
            return this.item.split(count);
        } else {
            return ItemStack.EMPTY;
        }
    }

    public void update() {
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        if (slot == 0) {
            ItemStack itemstack = this.item;
            this.item = ItemStack.EMPTY;
            return itemstack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if(slot == 0) {
            this.item = stack;
            this.setChanged();
        }
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D,
                    (double)this.worldPosition.getY() + 0.5D,
                    (double)this.worldPosition.getZ() + 0.5D) > 64.0D ? false : !this.getItem().isEmpty();
        }
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return slot==0 && this.getItem().isEmpty();
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void setItem(ItemStack stack) {
        this.item = stack;
        this.setChanged();
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        if (nbt.contains("Item", 10)) {
            this.item = ItemStack.of(nbt.getCompound("Item"));
        } else {
            this.item = ItemStack.EMPTY;
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        if (!this.getItem().isEmpty()) {
            nbt.put("Item", this.getItem().save(new CompoundNBT()));
        }

        return nbt;
    }

    @Override
    public void clearContent() {
        this.setItem(ItemStack.EMPTY);
        this.setChanged();
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.worldPosition, 13, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.save(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt){
        CompoundNBT tag = pkt.getTag();
        this.load(this.getBlockState(), tag);
    }

    @Override
    public void tick() {
        this.update();
    }
}
