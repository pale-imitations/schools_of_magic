package com.paleimitations.schoolsofmagic.common.data.books;

import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookDataProvider;
import com.paleimitations.schoolsofmagic.common.network.PacketHandler;
import com.paleimitations.schoolsofmagic.common.network.TurnPageEntityPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class PageElementPageButton extends PageElement {
    public final int pageNumber, width, height;

    public PageElementPageButton(int pageNumber, int x, int y, int target, int width, int height){
        super(x,y,target);
        this.pageNumber = pageNumber;
        this.width = width;
        this.height = height;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean click(float x, float y, int subpage, ItemStack book, @Nullable BlockPos pos, @Nullable PlayerEntity player, @Nullable Hand hand) {
        if(x>this.x && x<this.x+this.width && y>this.y && y<this.y+this.height && isTarget(subpage)) {
            CompoundNBT nbt = book.getOrCreateTag();
            nbt.putInt("page",pageNumber);
            nbt.putInt("subpage",0);
            book.setTag(nbt);
            if(player!=null && hand!=null) {
                player.setItemInHand(hand, book);
                PacketHandler.INSTANCE.sendToServer(new TurnPageEntityPacket(pageNumber, 0, player.getId(), hand));
                return true;
            }
            else if(pos!=null) {
                //PacketHandler.INSTANCE.sendToServer(new TurnPageTileEntityPacket(pageNumber, 0, pos));
                return true;
            }
            /*book.setPage(pageNumber);
            book.setSubPage(0);
            if(pos!=null) {
                PacketHandler.INSTANCE.sendToServer(new PacketTurnPage(pageNumber, 0, pos));
            }*/
        }
        return false;
    }
}
