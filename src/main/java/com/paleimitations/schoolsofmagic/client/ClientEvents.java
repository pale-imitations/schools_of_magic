package com.paleimitations.schoolsofmagic.client;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.client.items.models.BookModel;
import com.paleimitations.schoolsofmagic.client.tileentity.PodiumTileEntityRenderer;
import com.paleimitations.schoolsofmagic.common.data.BindingType;
import com.paleimitations.schoolsofmagic.common.data.TeaUtils;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookData;
import com.paleimitations.schoolsofmagic.common.items.TeacupItem;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import com.paleimitations.schoolsofmagic.common.registries.TileEntityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    private static final Logger LOGGER = LogManager.getLogger();

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onModelBakeEvent(ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(BookModel.RESOURCE_LOCATION, BookModel.Loader.INSTANCE);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void stitcherEventPre(TextureStitchEvent.Pre event) {
        if (event.getMap().location() == PlayerContainer.BLOCK_ATLAS) {
            event.addSprite(new ResourceLocation(References.MODID + ":item/book/cover"));
            for(DyeColor color : DyeColor.values()) {
                event.addSprite(new ResourceLocation(References.MODID + ":item/book/cover_"+color.getName()));
            }
            for(BindingType type : BindingType.values()) {
                event.addSprite(new ResourceLocation(References.MODID + ":item/book/binding_"+type.getSerializedName()));
            }
            for(BookData.DecorationType type : BookData.DecorationType.values()) {
                event.addSprite(new ResourceLocation(References.MODID + ":item/book/decor_"+type.getSerializedName()));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerColors(ColorHandlerEvent.Item event) {
        ItemColors itemcolors = event.getItemColors();
        itemcolors.register((itemStack, tint) -> tint > 0 ? -1 : TeaUtils.getColor(itemStack),
                ItemRegistry.FILLED_WHITE_TEACUP.get(), ItemRegistry.FILLED_WHITE_TERRACOTTA_TEACUP.get());
    }
}
