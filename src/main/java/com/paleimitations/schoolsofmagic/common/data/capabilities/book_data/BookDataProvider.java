package com.paleimitations.schoolsofmagic.common.data.capabilities.book_data;

import com.google.common.collect.Maps;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.network.GetBookDataPacket;
import com.paleimitations.schoolsofmagic.common.network.PacketHandler;
import com.paleimitations.schoolsofmagic.common.registries.BookPageRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

public class BookDataProvider {

    private static final Map<String, BookData> CLIENT_BOOK_DATA = Maps.newHashMap();

    public static void addBookData(String id, BookData data) {
        CLIENT_BOOK_DATA.put(id, data);
    }

    public static int getNextAvailableBookId(World world) {
        return world.getServer().overworld().getDataStorage().computeIfAbsent(() -> new BookHandlerData("book_handler"), "book_handler").getNewBookId();
    }

    public static BookData getBook(World world, ItemStack stack) {
        if(stack.getTag()!=null) {
            CompoundNBT nbt = stack.getTag();
            if(nbt.contains("book_data_s"))
                return getBook(world, nbt.getString("book_data_s"));
            if(nbt.contains("book_data_i"))
                return getBook(world, nbt.getInt("book_data_i"));
        }
        return null;
    }

    public static BookData getBook(World world, int id) {
        String s = References.MODID + "_book_" + id;
        if(world.isClientSide) {
            if(CLIENT_BOOK_DATA.containsKey(s))
                return CLIENT_BOOK_DATA.get(s);
            else {
                PacketHandler.INSTANCE.sendToServer(new GetBookDataPacket(Minecraft.getInstance().player.getId(), s));
                return null;
            }
        }
        return world.getServer().overworld().getDataStorage().computeIfAbsent(() -> new BookData(s), s);
    }

    public static BookData getBook(World world, String id) {
        if(world.isClientSide) {
            if(CLIENT_BOOK_DATA.containsKey(id))
                return CLIENT_BOOK_DATA.get(id);
            else {
                PacketHandler.INSTANCE.sendToServer(new GetBookDataPacket(Minecraft.getInstance().player.getId(), id));
                return null;
            }
        }
        return world.getServer().overworld().getDataStorage().computeIfAbsent(() -> new BookData(id), id);
    }

    @Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class Events {

        @SubscribeEvent
        public static void loadWorld(WorldEvent.Load event) {
            if(event.getWorld() instanceof World) {
                World world = (World) event.getWorld();
                if(!world.isClientSide()) {
                    BookData data = BookDataProvider.getBook(world, References.MODID + "_book_basic_arcana");
                    if(data.getBookPages() != BookPageRegistry.BASIC_ARCANA) {
                        data.getBookPages().clear();
                        data.addBookPages(BookPageRegistry.BASIC_ARCANA);
                    }
                }
            }
        }
    }
}
