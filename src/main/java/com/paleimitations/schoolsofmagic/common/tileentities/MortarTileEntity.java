package com.paleimitations.schoolsofmagic.common.tileentities;

import com.paleimitations.schoolsofmagic.common.containers.MortarContainer;
import com.paleimitations.schoolsofmagic.common.crafting.MortarRecipe;
import com.paleimitations.schoolsofmagic.common.network.ClientTileEntityPacket;
import com.paleimitations.schoolsofmagic.common.network.PacketHandler;
import com.paleimitations.schoolsofmagic.common.registries.RecipeRegistry;
import com.paleimitations.schoolsofmagic.common.registries.TileEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class MortarTileEntity extends LockableTileEntity implements ITickableTileEntity {

    private NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    private MortarRecipe recipe;
    private int crush = 0;
    private int maxCrush = 0;

    public final IIntArray dataAccess = new IIntArray() {
        public int get(int id) {
            switch(id) {
                case 0:
                    return MortarTileEntity.this.crush;
                case 1:
                    return MortarTileEntity.this.maxCrush;
                default:
                    return 0;
            }
        }

        public void set(int id, int value) {
            switch(id) {
                case 0:
                    MortarTileEntity.this.crush = value;
                    break;
                case 1:
                    MortarTileEntity.this.maxCrush = value;
                    break;
            }
            MortarTileEntity.this.sendServerUpdate();
        }

        public int getCount() {
            return 2;
        }
    };

    public MortarTileEntity() {
        super(TileEntityRegistry.MORTAR_TILE_ENTITY.get());
    }

    private void sendServerUpdate() {
        if(this.getLevel().isClientSide) {
            PacketHandler.INSTANCE.sendToServer(new ClientTileEntityPacket(this.worldPosition, this.save(new CompoundNBT())));
        }
    }

    public MortarRecipe getRecipe() {
        IRecipe<?> irecipe = this.level.getRecipeManager().getRecipeFor(RecipeRegistry.MORTAR_TYPE, this, this.level).orElse(null);
        return irecipe instanceof MortarRecipe? (MortarRecipe)irecipe : null;
    }

    public boolean hasRecipe() {
        return this.getRecipe() != null;
    }

    public int getCrush() {
        return this.crush;
    }

    public void setCrush(int crush) {
        this.crush = crush;
    }

    @Override
    public void tick() {
        if(this.recipe != this.getRecipe()) {
            this.recipe = this.getRecipe();
            this.crush = 0;
            this.maxCrush = this.recipe!=null? recipe.getCrush() : 0;
            this.setChanged();
        }
        if(this.crush >= this.maxCrush && this.recipe != null) {
            this.crush = 0;
            this.setItem(0, this.recipe.assemble(this));
            this.setItem(1, this.recipe.assembleSecondary(this));
            this.setChanged();
        }
    }

    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.items);
        this.crush = nbt.getInt("crush");
    }

    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        ItemStackHelper.saveAllItems(nbt, this.items);
        nbt.putInt("crush", this.crush);
        return nbt;
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return slot >= 0 && slot < this.items.size() ? this.items.get(slot) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int count) {
        return ItemStackHelper.removeItem(this.items, slot, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ItemStackHelper.takeItem(this.items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (slot >= 0 && slot < this.items.size()) {
            this.items.set(slot, stack);
        }
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
    public boolean stillValid(PlayerEntity player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return !(player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.schoolsofmagic.mortar");
    }

    @Override
    protected Container createMenu(int containerID, PlayerInventory playerInv) {
        return new MortarContainer(containerID, playerInv, this, this.dataAccess);
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
}
