package com.paleimitations.schoolsofmagic.common.registries;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.SchoolsOfMagicMod;
import com.paleimitations.schoolsofmagic.common.command.MagicXPCommand;
import com.paleimitations.schoolsofmagic.common.command.RegisterCommandEvent;
import com.paleimitations.schoolsofmagic.common.config.Config;
import com.paleimitations.schoolsofmagic.common.data.loottables.LootInjecter;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookDataProvider;
import com.paleimitations.schoolsofmagic.common.network.*;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.WoodType;
import net.minecraft.command.CommandSource;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, References.MODID);
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, References.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, References.MODID);

    public static final  ItemGroup EQUIPMENT_TAB = (new ItemGroup("schoolsOfMagicEquipment") {
        @OnlyIn(Dist.CLIENT)
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.APPRENTICE_WAND_1.get());
        }
    }).setRecipeFolderName("schools_of_magic_equipment");

    public static void register() {
        MinecraftForge.EVENT_BUS.register(new Registry());
        MinecraftForge.EVENT_BUS.register(new BookDataProvider.Events());
        MinecraftForge.EVENT_BUS.register(new LootInjecter());
        MinecraftForge.EVENT_BUS.register(RegisterCommandEvent.class);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MagicSchoolRegistry.init();
        MagicElementRegistry.init();
        Config.init();
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        TILE_ENTITY_TYPES.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        CONTAINER_TYPES.register(modEventBus);
        ItemRegistry.register();
        BlockRegistry.register();
        TileEntityRegistry.register();
        RecipeRegistry.register();
        ContainerRegistry.register();
        SpellRegistry.init();
        PacketHandler.registerMessages();
        TeaRegistry.register();
    }

    public static void init() {
        BookPageRegistry.init();
        TeaIngredientRegistry.register();
        CapabilityRegistry.init();
        QuestRegistry.init();
        SchoolsOfMagicMod.getProxy().preInit();
        SchoolsOfMagicMod.getProxy().postInit();
        BlockRegistry.init();
        ItemRegistry.init();
    }

    @SubscribeEvent
    public void onStrip(BlockEvent.BlockToolInteractEvent event) {
        if(event.getToolType() == ToolType.AXE) {
            if(event.getFinalState().getBlock() == BlockRegistry.ACOLYTE_LOG.get())
                event.setFinalState(BlockRegistry.STRIPPED_ACOLYTE_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS,
                        event.getFinalState().getValue(RotatedPillarBlock.AXIS)));
            if(event.getFinalState().getBlock() == BlockRegistry.ACOLYTE_WOOD.get())
                event.setFinalState(BlockRegistry.STRIPPED_ACOLYTE_WOOD.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS,
                        event.getFinalState().getValue(RotatedPillarBlock.AXIS)));
            if(event.getFinalState().getBlock() == BlockRegistry.BASTION_LOG.get())
                event.setFinalState(BlockRegistry.STRIPPED_BASTION_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS,
                        event.getFinalState().getValue(RotatedPillarBlock.AXIS)));
            if(event.getFinalState().getBlock() == BlockRegistry.BASTION_WOOD.get())
                event.setFinalState(BlockRegistry.STRIPPED_BASTION_WOOD.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS,
                        event.getFinalState().getValue(RotatedPillarBlock.AXIS)));
        }
    }

    @SubscribeEvent
    public static void registerTileEntity(final RegistryEvent.Register<SoundEvent> event) {
        List<Block> blockList = Lists.newArrayList(BlockRegistry.ACOLYTE_SIGN.get(), BlockRegistry.ACOLYTE_WALL_SIGN.get(),
                BlockRegistry.BASTION_SIGN.get(), BlockRegistry.BASTION_WALL_SIGN.get(),
                BlockRegistry.EVERMORE_SIGN.get(), BlockRegistry.EVERMORE_WALL_SIGN.get(),
                BlockRegistry.JUBILEE_SIGN.get(), BlockRegistry.JUBILEE_WALL_SIGN.get(),
                BlockRegistry.VERMILION_SIGN.get(), BlockRegistry.VERMILION_WALL_SIGN.get(),
                BlockRegistry.WARTWOOD_SIGN.get(), BlockRegistry.WARTWOOD_WALL_SIGN.get());
        TileEntityType.SIGN.validBlocks.forEach(sign -> blockList.add(sign));
        TileEntityType.SIGN.validBlocks = ImmutableSet.copyOf(blockList);
    }

    @SubscribeEvent
    public static void registerSounds(final RegistryEvent.Register<SoundEvent> event) {
        System.out.println("Registered SoundEvents");
        try {
            for (Field f : SoundRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof SoundEvent) {
                    event.getRegistry().register((SoundEvent) obj);
                } else if (obj instanceof SoundEvent[]) {
                    for (SoundEvent soundEvent : (SoundEvent[]) obj) {
                        event.getRegistry().register(soundEvent);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}