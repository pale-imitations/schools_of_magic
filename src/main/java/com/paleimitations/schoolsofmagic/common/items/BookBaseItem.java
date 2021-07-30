package com.paleimitations.schoolsofmagic.common.items;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.SchoolsOfMagicMod;
import com.paleimitations.schoolsofmagic.common.data.BindingType;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookData;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import com.paleimitations.schoolsofmagic.common.registries.MagicElementRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class BookBaseItem extends Item {

    public BookBaseItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            items.add(initialize(new ItemStack(this)));
        }
    }

    public static ItemStack initialize(ItemStack stack) {
        if(stack.getItem()==ItemRegistry.BASIC_ARCANA.get()) {
            CompoundNBT nbt = stack.getOrCreateTag();
            nbt.putInt("dye", DyeColor.BLACK.getId());
            nbt.putString("book_data_s", References.MODID + "_book_basic_arcana");
            nbt.putString("binding", BindingType.BRONZE.getSerializedName());
            stack.setTag(nbt);
            return stack;
        }
        return stack;
    }

    public static DyeColor getCoverColor(ItemStack stack) {
        if(stack.getItem() instanceof BookBaseItem && stack.getOrCreateTag().contains("dye")) {
            return DyeColor.values()[stack.getOrCreateTag().getInt("dye")];
        }
        return null;
    }

    public static ItemStack setCoverColor(ItemStack stack, DyeColor color) {
        if(stack.getItem() instanceof BookBaseItem) {
            CompoundNBT nbt = stack.getOrCreateTag();
            if(color==null && nbt.contains("dye"))
                nbt.remove("dye");
            if(color!=null)
                nbt.putInt("dye", color.getId());
            stack.setTag(nbt);
        }
        return stack;
    }

    public static BindingType getBinding(ItemStack stack) {
        if(stack.getItem() instanceof BookBaseItem && stack.getOrCreateTag().contains("binding")) {
            return BindingType.fromName(stack.getOrCreateTag().getString("binding"));
        }
        return null;
    }

    public static ItemStack setBinding(ItemStack stack, BindingType binding) {
        if(stack.getItem() instanceof BookBaseItem) {
            CompoundNBT nbt = stack.getOrCreateTag();
            if(binding==null && nbt.contains("binding"))
                nbt.remove("binding");
            if(binding!=null)
                nbt.putString("binding", binding.getSerializedName());
            stack.setTag(nbt);
        }
        return stack;
    }

    public static BookData.DecorationType getDecor(ItemStack stack) {
        if(stack.getItem() instanceof BookBaseItem && stack.getOrCreateTag().contains("decor")) {
            return BookData.DecorationType.fromName(stack.getOrCreateTag().getString("decor"));
        }
        return null;
    }

    public static ItemStack setDecor(ItemStack stack, BookData.DecorationType decor) {
        if(stack.getItem() instanceof BookBaseItem) {
            CompoundNBT nbt = stack.getOrCreateTag();
            if(decor==null && nbt.contains("decor"))
                nbt.remove("decor");
            if(decor!=null)
                nbt.putString("decor", decor.getSerializedName());
            stack.setTag(nbt);
        }
        return stack;
    }

    public static BookData.DecorationType getSecondDecor(ItemStack stack) {
        if(stack.getItem() instanceof BookBaseItem && stack.getOrCreateTag().contains("decor_sec")) {
            return BookData.DecorationType.fromName(stack.getOrCreateTag().getString("decor_sec"));
        }
        return null;
    }

    public static ItemStack setSecondDecor(ItemStack stack, BookData.DecorationType decor_sec) {
        if(stack.getItem() instanceof BookBaseItem) {
            CompoundNBT nbt = stack.getOrCreateTag();
            if(decor_sec==null && nbt.contains("decor_sec"))
                nbt.remove("decor_sec");
            if(decor_sec!=null)
                nbt.putString("decor_sec", decor_sec.getSerializedName());
            stack.setTag(nbt);
        }
        return stack;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);

        CompoundNBT nbt = stack.getOrCreateTag();
        if(!nbt.contains("page"))
            nbt.putInt("page",0);
        if(!nbt.contains("subpage"))
            nbt.putInt("subpage",0);
        SchoolsOfMagicMod.getProxy().openBook(player, stack, hand);

        return ActionResult.pass(stack);
    }

    /*@Override
    public void onCraftedBy(ItemStack stack, World world, PlayerEntity player) {
        super.onCraftedBy(stack, world, player);
    }*/
}
