package com.paleimitations.schoolsofmagic;

import com.paleimitations.schoolsofmagic.client.ClientProxy;
import com.paleimitations.schoolsofmagic.common.registries.Registry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Mod(References.MODID)
public class SchoolsOfMagicMod {
    public static final Logger LOGGER = LogManager.getLogger();
    public static SchoolsOfMagicMod instance;
    private static ModContainer modContainer;
    private final CommonProxy proxy;

    public SchoolsOfMagicMod() {
        instance = this;
        Registry.register();
        modContainer = ModList.get().getModContainerById(References.MODID).get();
        this.proxy = DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        //test 4

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static SchoolsOfMagicMod getInstance() {
        return instance;
    }

    public static ModContainer getModContainer() {
        return modContainer;
    }

    public static CommonProxy getProxy() {
        return getInstance().proxy;
    }

    private void setup(final FMLCommonSetupEvent event) {
        Registry.init();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ClientProxy.register(event);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
}
