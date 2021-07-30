package com.paleimitations.schoolsofmagic.common.data;

import com.paleimitations.schoolsofmagic.common.data.books.BookPage;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookDataProvider;
import com.paleimitations.schoolsofmagic.common.network.PacketHandler;
import com.paleimitations.schoolsofmagic.common.network.TurnPageEntityPacket;
import com.paleimitations.schoolsofmagic.common.network.TurnPageTileEntityPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BookUtils {

    @OnlyIn(Dist.CLIENT)
    public static ActionResult<ItemStack> turnPage(BookData data, ItemStack stack, int i, Hand hand) {
        CompoundNBT nbt = stack.getOrCreateTag();
        int page = nbt.getInt("page");
        int subpage = nbt.getInt("subpage");
        if(data==null || data.getBookPages().isEmpty() || stack.isEmpty())
            return ActionResult.fail(stack);
        if(i == page && subpage == 0)
            return ActionResult.fail(stack);
        if(i>=data.getBookPages().size() || i<0)
            return ActionResult.fail(stack);
        nbt.putInt("page",i);
        nbt.putInt("subpage",0);
        page = i;
        subpage = 0;
        stack.setTag(nbt);
        PacketHandler.INSTANCE.sendToServer(new TurnPageEntityPacket(page, subpage, Minecraft.getInstance().player.getId(), hand));
        return ActionResult.success(stack);
    }

    @OnlyIn(Dist.CLIENT)
    public static ActionResult<ItemStack> turnPage(BookData data, ItemStack stack, boolean forward, BlockPos pos) {
        CompoundNBT nbt = stack.getOrCreateTag();
        int page = nbt.getInt("page");
        int subpage = nbt.getInt("subpage");
        BookPage bookPage = data.getBookPage(page);
        if(data.getBookPages().isEmpty() || stack.isEmpty() || bookPage==null)
            return ActionResult.fail(stack);
        boolean flag = false;
        if (forward) {
            for (int i = subpage + 1; i < bookPage.getSubPages(); ++i) {
                if (!bookPage.isSubPageBlank(i)) {
                    flag = true;
                    nbt.putInt("subpage", i);
                    subpage = i;
                    break;
                }
            }
            if (!flag && data.getBookPages().size() > page + 1) {
                nbt.putInt("page",page + 1);
                nbt.putInt("subpage",0);
                subpage = 0;
                page ++;
            }
        }
        else {
            if (subpage > 0) {
                for (int i = subpage - 1; i >= 0; --i) {
                    if (!bookPage.isSubPageBlank(i)) {
                        flag = true;
                        nbt.putInt("subpage", i);
                        subpage = i;
                        break;
                    }
                }
            }
            if (!flag && page > 0) {
                nbt.putInt("page",page - 1);
                int j = 0;
                for (int i = 0; i < data.getBookPage(page-1).getSubPages(); ++i)
                    if (!data.getBookPage(page-1).isSubPageBlank(i) && i > j)
                        j = i;
                nbt.putInt("subpage", j);
                subpage = j;
                page --;
            }
        }
        stack.setTag(nbt);
        PacketHandler.INSTANCE.sendToServer(new TurnPageTileEntityPacket(page, subpage, Minecraft.getInstance().player.getId(), pos));
        return ActionResult.success(stack);
    }

    @OnlyIn(Dist.CLIENT)
    public static ActionResult<ItemStack> turnPage(BookData data, ItemStack stack, boolean forward, Hand hand) {
        CompoundNBT nbt = stack.getOrCreateTag();
        int page = nbt.getInt("page");
        int subpage = nbt.getInt("subpage");
        BookPage bookPage = data.getBookPage(page);
        if(data.getBookPages().isEmpty() || stack.isEmpty() || bookPage==null)
            return ActionResult.fail(stack);
        boolean flag = false;
        if (forward) {
            for (int i = subpage + 1; i < bookPage.getSubPages(); ++i) {
                if (!bookPage.isSubPageBlank(i)) {
                    flag = true;
                    nbt.putInt("subpage", i);
                    subpage = i;
                    break;
                }
            }
            if (!flag && data.getBookPages().size() > page + 1) {
                nbt.putInt("page",page + 1);
                nbt.putInt("subpage",0);
                subpage = 0;
                page ++;
            }
        }
        else {
            if (subpage > 0) {
                for (int i = subpage - 1; i >= 0; --i) {
                    if (!bookPage.isSubPageBlank(i)) {
                        flag = true;
                        nbt.putInt("subpage", i);
                        subpage = i;
                        break;
                    }
                }
            }
            if (!flag && page > 0) {
                nbt.putInt("page",page - 1);
                int j = 0;
                for (int i = 0; i < data.getBookPage(page-1).getSubPages(); ++i)
                    if (!data.getBookPage(page-1).isSubPageBlank(i) && i > j)
                        j = i;
                nbt.putInt("subpage", j);
                subpage = j;
                page --;
            }
        }
        stack.setTag(nbt);
        PacketHandler.INSTANCE.sendToServer(new TurnPageEntityPacket(page, subpage, Minecraft.getInstance().player.getId(), hand));
        return ActionResult.success(stack);
    }
}
