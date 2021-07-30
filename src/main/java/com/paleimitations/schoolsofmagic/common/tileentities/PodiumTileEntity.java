package com.paleimitations.schoolsofmagic.common.tileentities;

import com.paleimitations.schoolsofmagic.common.blocks.PodiumBlock;
import com.paleimitations.schoolsofmagic.common.data.Utils;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookDataProvider;
import com.paleimitations.schoolsofmagic.common.items.BookBaseItem;
import com.paleimitations.schoolsofmagic.common.registries.SoundRegistry;
import com.paleimitations.schoolsofmagic.common.registries.TileEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;

import javax.annotation.Nullable;

public class PodiumTileEntity extends TileEntity implements IInventory, ITickableTileEntity {

    private ItemStack item = ItemStack.EMPTY;
    public BookState bookState = BookState.CLOSED;
    public int prevPage;
    public int prevSubPage;
    public int animationTick;

    public PodiumTileEntity() {
        super(TileEntityRegistry.PODIUM_TILE_ENTITY.get());
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
        if(hasBook() && BookDataProvider.getBook(level, item)!=null) {
            BookData data = BookDataProvider.getBook(level, item);
            CompoundNBT nbt = item.getOrCreateTag();
            int page = nbt.contains("page")? nbt.getInt("page") : 0;
            int subpage = nbt.contains("subpage")? nbt.getInt("subpage") : 0;
            if(!nbt.contains("page") || !nbt.contains("subpage")){
                nbt.putInt("page", page);
                nbt.putInt("subpage", page);
                item.setTag(nbt);
            }

            if(bookState.getAnimationLength()>0){
                ++animationTick;
                if(animationTick>=bookState.getAnimationLength()){
                    animationTick=0;
                    if(bookState== BookState.CLOSE_BOOK)
                        bookState= BookState.CLOSED;
                    else {
                        if(bookState != BookState.OPEN_BOOK){
                            this.prevPage=page;
                            this.prevSubPage=subpage;
                        }
                        bookState = BookState.OPEN;
                    }
                }
            }

            float xD = 0.5f;
            float zD = 0.5f;
            Direction facing = this.level.getBlockState(worldPosition).getValue(PodiumBlock.HORIZONTAL_FACING);
            float facingAngle;
            float targetRotation;

            if(facing==Direction.NORTH) {
                facingAngle=0f;
                xD=1.0f;
            }else if(facing==Direction.SOUTH) {
                facingAngle=180f;
                xD=0f;
            }else if(facing==Direction.EAST) {
                facingAngle=-90f;
                zD=1.0f;
            }else{
                facingAngle=90f;
                zD=0f;
            }
            PlayerEntity player = level.getNearestPlayer(worldPosition.getX()+xD,worldPosition.getY(),worldPosition.getZ()+zD,30,false);
            if(bookState== BookState.OPEN && (player==null || !this.shouldBeOpen(player,worldPosition.getX()+xD,worldPosition.getY(),worldPosition.getZ()+zD))){
                bookState= BookState.CLOSE_BOOK;
                level.playSound(null, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), SoundRegistry.BOOK_CLOSE, SoundCategory.BLOCKS, 1f,1f);
                animationTick=0;
            }
            if(bookState== BookState.CLOSED && player!=null && this.shouldBeOpen(player,worldPosition.getX()+xD,worldPosition.getY(),worldPosition.getZ()+zD)){
                bookState= BookState.OPEN_BOOK;
                level.playSound(null, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), SoundRegistry.BOOK_OPEN, SoundCategory.BLOCKS, 0.8f,1f);
                animationTick=0;
            }
            if((bookState== BookState.OPEN) && (this.prevPage<page || (this.prevSubPage<subpage && this.prevPage==page))) {
                bookState = BookState.TURN_PAGE_FORWARD;
                level.playSound(null, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), SoundRegistry.PAGE_FLIP, SoundCategory.BLOCKS, 1f,1f);
                animationTick=0;
            }
            else if((bookState== BookState.OPEN) && (this.prevPage>page || (this.prevSubPage>subpage && this.prevPage==page))) {
                bookState = BookState.TURN_PAGE_BACK;
                level.playSound(null, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), SoundRegistry.PAGE_FLIP, SoundCategory.BLOCKS, 1f,1f);
                animationTick=0;
            }
        }
    }

    private boolean shouldBeOpen(PlayerEntity player, double x, double y, double z){
        return Utils.getDistanceDouble(player.getX(), player.getY(), player.getZ(), x, y, z) < 5;
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

    public boolean hasBook() {
        Item item = this.item.getItem();
        return item instanceof BookBaseItem;
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

    /*public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new LecternContainer(p_createMenu_1_, this.bookAccess, this.dataAccess);
    }*/

    public enum BookState {
        OPEN_BOOK(30),
        OPEN(0),
        TURN_PAGE_FORWARD(12),
        TURN_PAGE_BACK(12),
        CLOSE_BOOK(30),
        CLOSED(0);
        private final int animationLength;

        BookState(int animationLength){
            this.animationLength = animationLength;
        }

        public int getAnimationLength() {
            return animationLength;
        }
    }
}
