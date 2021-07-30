package com.paleimitations.schoolsofmagic.common.containers;

import com.paleimitations.schoolsofmagic.common.registries.ContainerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
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

public class MortarContainer extends RecipeBookContainer<IInventory> {
    private final IInventory mortar;
    private final IIntArray mortarData;
    protected final World level;

    public MortarContainer(int containerID, PlayerInventory playerInv) {
        this(containerID, playerInv, new Inventory(2), new IntArray(2));
    }

    public MortarContainer(int containerID, PlayerInventory playerInv, IInventory mortarInv, IIntArray dataAccess) {
        super(ContainerRegistry.MORTAR_CONTAINER.get(), containerID);
        checkContainerSize(mortarInv, 2);
        checkContainerDataCount(dataAccess, 2);
        this.mortar = mortarInv;
        this.mortarData  = dataAccess;
        this.level = playerInv.player.level;
        this.addSlot(new MortarContainer.MortarSlot(mortarInv, 0, 70, 47));
        this.addSlot(new MortarContainer.MortarSlot(mortarInv, 1, 90, 47));
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
            if (slotID < 0 || slotID > 1) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
                else if (slotID >= this.mortar.getContainerSize() && slotID < this.mortar.getContainerSize() + 27) {
                    if (!this.moveItemStackTo(itemstack1, this.mortar.getContainerSize() + 27, this.mortar.getContainerSize() + 36, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (slotID >= this.mortar.getContainerSize() + 27 && slotID < this.mortar.getContainerSize() + 36) {
                    if (!this.moveItemStackTo(itemstack1, this.mortar.getContainerSize(), this.mortar.getContainerSize() + 27, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (!this.moveItemStackTo(itemstack1, this.mortar.getContainerSize(), this.mortar.getContainerSize() + 36, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else {
                if (!this.moveItemStackTo(itemstack1, this.mortar.getContainerSize(), this.mortar.getContainerSize() + 36, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
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

    @OnlyIn(Dist.CLIENT)
    public int getCrush(){
        return this.mortarData.get(0);
    }

    @OnlyIn(Dist.CLIENT)
    public int getMaxCrush(){
        return this.mortarData.get(1);
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return mortar.stillValid(player);
    }

    @OnlyIn(Dist.CLIENT)
    public void addCrush() {
        this.mortarData.set(0, this.getCrush() + 1);
    }

    @Override
    public void fillCraftSlotsStackedContents(RecipeItemHelper p_201771_1_) {
    }

    @Override
    public void clearCraftingContent() {
        this.mortar.clearContent();
    }

    @Override
    public boolean recipeMatches(IRecipe<? super IInventory> p_201769_1_) {
        return p_201769_1_.matches(mortar, level);
    }

    @Override
    public int getResultSlotIndex() {
        return 0;
    }

    @Override
    public int getGridWidth() {
        return 0;
    }

    @Override
    public int getGridHeight() {
        return 0;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public RecipeBookCategory getRecipeBookType() {
        return null;
    }

    static class MortarSlot extends Slot {
        public MortarSlot(IInventory inv, int id, int x, int y) {
            super(inv, id, x, y);
        }

        public int getMaxStackSize() {
            return 1;
        }
    }

}
