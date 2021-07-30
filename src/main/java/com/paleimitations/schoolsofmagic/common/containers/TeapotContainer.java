package com.paleimitations.schoolsofmagic.common.containers;

import com.paleimitations.schoolsofmagic.common.registries.ContainerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TeapotContainer extends Container {
    private final IInventory teapot;
    private final IIntArray teapotData;
    protected final World level;

    public TeapotContainer(int containerID, PlayerInventory playerInv) {
        this(containerID, playerInv, new Inventory(5), new IntArray(4));
    }

    public TeapotContainer(int containerID, PlayerInventory playerInv, IInventory teapotInv, IIntArray dataAccess) {
        super(ContainerRegistry.TEAPOT_CONTAINER.get(), containerID);
        checkContainerSize(teapotInv, 5);
        checkContainerDataCount(dataAccess, 4);
        this.teapot = teapotInv;
        this.teapotData  = dataAccess;
        this.level = playerInv.player.level;
        this.addSlot(new TeapotSlot(teapotInv, 0, 80, 15));
        this.addSlot(new TeapotSlot(teapotInv, 1, 80, 34));
        this.addSlot(new TeapotSlot(teapotInv, 2, 80, 53));
        this.addSlot(new Slot(teapotInv, 3, 134, 25));
        this.addSlot(new OutputSlot(teapotInv, 4, 134, 44));
        this.addDataSlots(dataAccess);

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInv, k, 8 + k * 18, 142));
        }

    }

    public ItemStack quickMoveStack(PlayerEntity player, int slotID) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotID);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (slotID < 0 || slotID > 3) {
                if (!this.moveItemStackTo(itemstack1, 0, 3, false)) {
                    return ItemStack.EMPTY;
                }
                else if (slotID >= this.teapot.getContainerSize() && slotID < this.teapot.getContainerSize() + 27) {
                    if (!this.moveItemStackTo(itemstack1, this.teapot.getContainerSize() + 27, this.teapot.getContainerSize() + 36, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (slotID >= this.teapot.getContainerSize() + 27 && slotID < this.teapot.getContainerSize() + 36) {
                    if (!this.moveItemStackTo(itemstack1, this.teapot.getContainerSize(), this.teapot.getContainerSize() + 27, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (!this.moveItemStackTo(itemstack1, this.teapot.getContainerSize(), this.teapot.getContainerSize() + 36, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else {
                if (!this.moveItemStackTo(itemstack1, this.teapot.getContainerSize(), this.teapot.getContainerSize() + 36, true)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            }
            else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    public boolean hasHeat() {
        return teapotData.get(0) == 1;
    }

    public int liquidLevel() {
        return teapotData.get(1);
    }

    public int liquidColor() {
        return teapotData.get(2);
    }

    public int tickHeight() {
        return Math.round(16f * (100f-(float)teapotData.get(3))/100f);
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return teapot.stillValid(player);
    }

    static class TeapotSlot extends Slot {
        public TeapotSlot(IInventory inv, int id, int x, int y) {
            super(inv, id, x, y);
        }

        public int getMaxStackSize() {
            return 1;
        }
    }

    static class OutputSlot extends Slot {
        public OutputSlot(IInventory inv, int id, int x, int y) {
            super(inv, id, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack p_75214_1_) {
            return false;
        }
    }

}
