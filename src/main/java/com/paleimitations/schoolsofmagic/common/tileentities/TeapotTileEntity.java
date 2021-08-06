package com.paleimitations.schoolsofmagic.common.tileentities;

import com.paleimitations.schoolsofmagic.common.TeaBrewResult;
import com.paleimitations.schoolsofmagic.common.containers.MortarContainer;
import com.paleimitations.schoolsofmagic.common.containers.TeapotContainer;
import com.paleimitations.schoolsofmagic.common.data.TeaUtils;
import com.paleimitations.schoolsofmagic.common.items.TeacupItem;
import com.paleimitations.schoolsofmagic.common.network.ClientTileEntityPacket;
import com.paleimitations.schoolsofmagic.common.network.PacketHandler;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import com.paleimitations.schoolsofmagic.common.registries.TeaIngredientRegistry;
import com.paleimitations.schoolsofmagic.common.registries.TileEntityRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

public class TeapotTileEntity extends LockableTileEntity implements ITickableTileEntity {

    private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
    private LiquidType liquidType = LiquidType.EMPTY;
    private int liquidLevel = 0;
    private TeaBrewResult brew = null;
    private int countdown = 100;
    public final IIntArray dataAccess = new IIntArray() {
        public int get(int id) {
            switch(id) {
                case 0:
                    return TeapotTileEntity.this.heated()? 1 : 0;
                case 1:
                    return TeapotTileEntity.this.liquidLevel;
                case 2:
                    if(TeapotTileEntity.this.brew!=null){
                        return TeapotTileEntity.this.brew.getColor();
                    }
                    return TeapotTileEntity.this.liquidType == LiquidType.WATER ? 6854629 : Color.WHITE.getRGB();
                case 3:
                    return TeapotTileEntity.this.countdown;
                default:
                    return 0;
            }
        }

        public void set(int id, int value) {
        }

        public int getCount() {
            return 4;
        }
    };

    public TeapotTileEntity() {
        super(TileEntityRegistry.TEAPOT_TILE_ENTITY.get());
    }

    private void sendServerUpdate() {
        if(this.getLevel().isClientSide) {
            PacketHandler.INSTANCE.sendToServer(new ClientTileEntityPacket(this.worldPosition, this.save(new CompoundNBT())));
        }
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.schoolsofmagic.teapot");
    }

    @Override
    protected Container createMenu(int containerID, PlayerInventory playerInv) {
        return new TeapotContainer(containerID, playerInv, this, dataAccess);
    }

    public void load(BlockState state, CompoundNBT nbt) {
        if(nbt.contains("brew"))
            this.brew = new TeaBrewResult(nbt.getCompound("brew"));
        this.liquidType = LiquidType.values()[nbt.getInt("liquidType")];
        this.liquidLevel = nbt.getInt("liquidLevel");
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.items);
        super.load(state, nbt);
    }

    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        ItemStackHelper.saveAllItems(nbt, this.items);
        if(this.brew!=null)
            nbt.put("brew", this.brew.serialize());
        nbt.putInt("liquidLevel", this.liquidLevel);
        nbt.putInt("liquidType", this.liquidType.ordinal());
        return nbt;
    }

    @Override
    public int getContainerSize() {
        return 5;
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
    public void tick() {
        ItemStack input = this.items.get(3);
        ItemStack output = this.items.get(4);
        if(this.liquidType == LiquidType.EMPTY || liquidType == LiquidType.WATER) {
            if(liquidLevel == 0 && input.getItem() instanceof BucketItem && ((BucketItem) input.getItem()).getFluid() == Fluids.WATER) {
                boolean proceed = !input.hasContainerItem();
                if(input.hasContainerItem()) {
                    ItemStack containerItem = input.getContainerItem();
                    if (output.isEmpty()) {
                        proceed = true;
                        this.setItem(4, containerItem);
                    } else if (this.equals(output, containerItem, false) && output.getCount() < output.getItem().getMaxStackSize()) {
                        proceed = true;
                        output.grow(1);
                    }
                }
                if(proceed) {
                    input.shrink(1);
                    this.liquidLevel = 3;
                    this.liquidType = LiquidType.WATER;
                    this.setChanged();
                }
            }
            else if(liquidLevel == 0 && ((input.getItem() instanceof BucketItem && Tags.Fluids.MILK.contains(((BucketItem) input.getItem()).getFluid())) || input.getItem() instanceof MilkBucketItem)) {
                boolean proceed = !input.hasContainerItem();
                if(input.hasContainerItem()) {
                    ItemStack containerItem = input.getContainerItem();
                    if (output.isEmpty()) {
                        this.setItem(4, containerItem);
                        proceed = true;
                    } else if (this.equals(output, containerItem, false) && output.getCount() < output.getItem().getMaxStackSize()) {
                        output.grow(1);
                        proceed = true;
                    }
                }
                if(proceed) {
                    input.shrink(1);
                    this.liquidLevel = 3;
                    this.liquidType = LiquidType.MILK;
                    this.setChanged();
                }
            }
            else if(liquidLevel < 3 && input.getItem() instanceof PotionItem && PotionUtils.getPotion(input) == Potions.WATER) {
                boolean proceed = false;
                ItemStack containerItem = new ItemStack(Items.GLASS_BOTTLE);
                if(output.isEmpty()) {
                    this.setItem(4, containerItem);
                    proceed = true;
                }
                else if(this.equals(output, containerItem, false) && output.getCount() < output.getItem().getMaxStackSize()) {
                    output.grow(1);
                    proceed = true;
                }
                if(proceed) {
                    input.shrink(1);
                    ++this.liquidLevel;
                    this.liquidType = LiquidType.WATER;
                    this.setChanged();
                }
            }
        }
        else if(liquidType == LiquidType.TEA && liquidLevel > 0 && brew!=null && ItemRegistry.TEACUP_ENTRIES.containsKey(input.getItem())) {
            ItemStack newTea = new ItemStack(ItemRegistry.TEACUP_ENTRIES.get(input.getItem()));
            TeaUtils.setTea(newTea, this.brew);
            boolean proceed = false;
            if(output.isEmpty()) {
                this.setItem(4, newTea);
                proceed = true;
            }
            else if(this.equals(output, newTea, false) && output.getCount() < output.getItem().getMaxStackSize()) {
                output.grow(1);
                proceed = true;
            }
            if(proceed) {
                input.shrink(1);
                --this.liquidLevel;
                this.setChanged();
            }
        }

        if(heated() && (liquidType == LiquidType.MILK || liquidType == LiquidType.WATER) && liquidLevel > 0) {
            TeaIngredientRegistry.TeaIngredient ingred1 = TeaIngredientRegistry.getIngredient(this.items.get(0));
            TeaIngredientRegistry.TeaIngredient ingred2 = TeaIngredientRegistry.getIngredient(this.items.get(1));
            TeaIngredientRegistry.TeaIngredient ingred3 = TeaIngredientRegistry.getIngredient(this.items.get(2));
            TeaBrewResult test = new TeaBrewResult(ingred1!=null? ingred1.tea : null, ingred2!=null? ingred2.tea : null, ingred3!=null? ingred3.tea : null, liquidType == LiquidType.MILK);
            if(test.successful) {
                this.countdown--;
                if(countdown == 0) {
                    this.liquidType = LiquidType.TEA;
                    this.brew = test;
                    this.items.set(0, ItemStack.EMPTY);
                    this.items.set(1, ItemStack.EMPTY);
                    this.items.set(2, ItemStack.EMPTY);
                    this.setChanged();
                }
            }
            else {
                this.countdown = 100;
            }
        }
        else {
            this.countdown = 100;
        }

        if(liquidLevel==0) {
            liquidType = LiquidType.EMPTY;
            brew = null;
            this.setChanged();
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

    public boolean equals(ItemStack stack, ItemStack other, boolean limitTags) {
        if (stack.isEmpty())
            return other.isEmpty();
        else
            return !other.isEmpty() && stack.getItem() == other.getItem() && (limitTags ? stack.areShareTagsEqual(other) : ItemStack.tagMatches(stack, other));
    }

    public boolean heated() {
        return isHeatSource(this.level.getBlockState(this.worldPosition.below(1))) || isHeatSource(this.level.getBlockState(this.worldPosition.below(2)));
    }

    public boolean isHeatSource(BlockState state) {
        return (state.getBlock() instanceof AbstractFurnaceBlock && state.getValue(AbstractFurnaceBlock.LIT)) || (state.getBlock() instanceof CampfireBlock && state.getValue(CampfireBlock.LIT)) || state.getBlock() instanceof AbstractFireBlock || state.getBlock() instanceof TorchBlock ||
                state.getBlock() instanceof MagmaBlock || state.getMaterial() == Material.LAVA;
    }

    enum LiquidType {
        EMPTY,
        WATER,
        MILK,
        TEA;
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
