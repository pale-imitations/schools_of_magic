package com.paleimitations.schoolsofmagic.common.data;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.client.data.SOMBlockStateProvider;
import com.paleimitations.schoolsofmagic.client.data.SOMItemModelProvider;
import com.paleimitations.schoolsofmagic.common.data.loottables.SOMLootTableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper fh = event.getExistingFileHelper();

        gen.addProvider(new SOMBlockStateProvider(gen, fh));
        gen.addProvider(new SOMItemModelProvider(gen, fh));

        SOMBlockTagsProvider blocktags = new SOMBlockTagsProvider(gen, fh);
        gen.addProvider(blocktags);
        gen.addProvider(new SOMItemTagsProvider(gen, blocktags, fh));

        gen.addProvider(new SOMRecipeProvider(gen));
        gen.addProvider(new SOMLootTableProvider(gen));
        gen.addProvider(new SOMAdvancementProvider(gen));
    }
}
