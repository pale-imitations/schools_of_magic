package com.paleimitations.schoolsofmagic.common.registries;

import com.paleimitations.schoolsofmagic.common.containers.MortarContainer;
import com.paleimitations.schoolsofmagic.common.containers.MortarContainerProvider;
import com.paleimitations.schoolsofmagic.common.containers.TeapotContainer;
import com.paleimitations.schoolsofmagic.common.containers.TeapotContainerProvider;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;

public class ContainerRegistry {

    public static final RegistryObject<ContainerType<MortarContainer>> MORTAR_CONTAINER = Registry.CONTAINER_TYPES.register("mortar_container",
            ()->new ContainerType<>(new MortarContainerProvider.Factory()));
    public static final RegistryObject<ContainerType<TeapotContainer>> TEAPOT_CONTAINER = Registry.CONTAINER_TYPES.register("teapot_container",
            ()->new ContainerType<>(new TeapotContainerProvider.Factory()));

    public static void register() {
    }
}
