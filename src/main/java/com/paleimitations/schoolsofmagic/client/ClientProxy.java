package com.paleimitations.schoolsofmagic.client;

import com.paleimitations.schoolsofmagic.CommonProxy;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.client.data.BookTextManager;
import com.paleimitations.schoolsofmagic.client.screen.*;
import com.paleimitations.schoolsofmagic.client.tileentity.MortarTileEntityRenderer;
import com.paleimitations.schoolsofmagic.client.tileentity.PodiumTileEntityRenderer;
import com.paleimitations.schoolsofmagic.client.tileentity.TeaplateTileEntityRenderer;
import com.paleimitations.schoolsofmagic.common.data.books.BookPage;
import com.paleimitations.schoolsofmagic.common.quests.Quest;
import com.paleimitations.schoolsofmagic.common.registries.BlockRegistry;
import com.paleimitations.schoolsofmagic.common.registries.ContainerRegistry;
import com.paleimitations.schoolsofmagic.common.registries.TileEntityRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.Hand;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy extends CommonProxy {

    private static String CATEGORY = "key.category."+ References.MODID+".general";
    public static KeyBinding SPELL = new KeyBinding("key."+References.MODID+".spell", 89, CATEGORY);

    public static void register(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        MinecraftForge.EVENT_BUS.register(new SpellGui(event.getMinecraftSupplier().get()));
        ((IReloadableResourceManager) event.getMinecraftSupplier().get().getResourceManager()).registerReloadListener(BookTextManager.INSTANCE);
        BookTextManager.INSTANCE.onResourceManagerReload(event.getMinecraftSupplier().get().getResourceManager());
        // do something that can only be done on the client
        RenderTypeLookup.setRenderLayer(BlockRegistry.GRANITE_PODIUM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.PRISMARINE_PODIUM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.HERBAL_TWINE_POPPY.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.HERBAL_TWINE_CORNFLOWER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.HERBAL_TWINE_ALLIUM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.HERBAL_TWINE_DANDELION.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.HERBAL_TWINE_LILY_OF_THE_VALLEY.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.HERBAL_TWINE_BLUE_ORCHID.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.ACOLYTE_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.JUBILEE_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.ACOLYTE_TRAPDOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlockRegistry.JUBILEE_TRAPDOOR.get(), RenderType.cutout());
        /*BlockColors.createDefault().register((p_228061_0_, p_228061_1_, p_228061_2_, p_228061_3_) -> {
            return p_228061_1_ != null && p_228061_2_ != null ? BiomeColors.getAverageFoliageColor(p_228061_1_, p_228061_2_) : FoliageColors.getDefaultColor();
        }, BlockRegistry.ACOLYTE_LEAVES.get());*/
        ClientRegistry.registerKeyBinding(SPELL);
        ScreenManager.register(ContainerRegistry.MORTAR_CONTAINER.get(), MortarScreen::new);
        ScreenManager.register(ContainerRegistry.TEAPOT_CONTAINER.get(), TeapotScreen::new);
        ClientRegistry.bindTileEntityRenderer(TileEntityRegistry.PODIUM_TILE_ENTITY.get(), dispatcher -> new PodiumTileEntityRenderer(dispatcher));
        ClientRegistry.bindTileEntityRenderer(TileEntityRegistry.MORTAR_TILE_ENTITY.get(), dispatcher -> new MortarTileEntityRenderer(dispatcher));
        ClientRegistry.bindTileEntityRenderer(TileEntityRegistry.TEAPLATE_TILE_ENTITY.get(), dispatcher -> new TeaplateTileEntityRenderer(dispatcher));
    }

    @Override
    public void preInit() {
    }

    @Override
    public void postInit() {
    }

    @Override
    public void loadBookPageText(BookPage page) {
        BookTextManager.loadText(page);
    }

    @Override
    public void openBook(PlayerEntity player, ItemStack stack, Hand hand) {
        if(player == Minecraft.getInstance().player)
            Minecraft.getInstance().setScreen(new GrimoireScreen(stack, hand));
    }

    @Override
    public void openQuest(PlayerEntity player, ItemStack stack, Quest quest, Hand hand) {
        if(player == Minecraft.getInstance().player)
            Minecraft.getInstance().setScreen(new QuestPageScreen(stack, quest, hand));
    }

    @Override
    public void openLetter(PlayerEntity player, ItemStack stack, Hand hand) {
        if(player == Minecraft.getInstance().player)
            Minecraft.getInstance().setScreen(new LetterScreen(stack, hand));
    }
}
