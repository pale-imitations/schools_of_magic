package com.paleimitations.schoolsofmagic.common.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.items.BookBaseItem;
import com.paleimitations.schoolsofmagic.common.registries.BlockRegistry;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import com.paleimitations.schoolsofmagic.common.registries.TagRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class SOMAdvancementProvider implements IDataProvider {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;

    public SOMAdvancementProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(DirectoryCache cache) {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        //noinspection OverlyLongLambda
        Consumer<Advancement> consumer = (p_204017_3_) -> {
            if (!set.add(p_204017_3_.getId())) {
                throw new IllegalStateException("Duplicate advancement " + p_204017_3_.getId());
            } else {
                Path path1 = getPath(path, p_204017_3_);

                try {
                    IDataProvider.save(GSON, cache, p_204017_3_.deconstruct().serializeToJson(), path1);
                } catch (IOException ioexception) {
                }

            }
        };

        new Advancements().accept(consumer);
    }

    @Override
    public String getName() {
        return "Schools of Magic Advancements";
    }

    private static Path getPath(Path pathIn, Advancement advancementIn) {
        return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    }

    private static class Advancements implements Consumer<Consumer<Advancement>> {

        @Override
        public void accept(Consumer<Advancement> consumer) {
            Advancement root = Advancement.Builder.advancement()
                    .display(Items.STICK, title("root"), description("root"), new ResourceLocation("minecraft:textures/gui/advancements/backgrounds/adventure.png"),
                            FrameType.TASK, false, false, false)
                    .requirements(IRequirementsStrategy.OR)
                    .addCriterion("kill_witch", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityType.WITCH)))
                    .addCriterion("kill_evoker", KilledTrigger.Instance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityType.EVOKER)))
                    .save(consumer, References.MODID + ":" + References.MODID + "/root");
            Advancement youreAWizard = Advancement.Builder.advancement().parent(root)
                    .display(ItemRegistry.LETTER_CCW.get(), title("youre_a_wizard"), description("youre_a_wizard"), null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("get_item", getItem(ItemRegistry.LETTER_CCW.get()))
                    .save(consumer, References.MODID + ":" + References.MODID + "/youre_a_wizard");
            Advancement basicArcana = Advancement.Builder.advancement().parent(youreAWizard)
                    .display(BookBaseItem.initialize(new ItemStack(ItemRegistry.BASIC_ARCANA.get())), title("basic_arcana"), description("basic_arcana"), null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("get_item", getItem(ItemRegistry.BASIC_ARCANA.get()))
                    .save(consumer, References.MODID + ":" + References.MODID + "/basic_arcana");
            Advancement apprenticeWand = Advancement.Builder.advancement().parent(basicArcana)
                    .display(ItemRegistry.APPRENTICE_WAND_1.get(), title("apprentice_wand"), description("apprentice_wand"), null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("get_item", getItem(ItemRegistry.APPRENTICE_WAND_1.get()))
                    .save(consumer, References.MODID + ":" + References.MODID + "/apprentice_wand");
            Advancement podium = Advancement.Builder.advancement().parent(basicArcana)
                    .display(BlockRegistry.ANDESITE_PODIUM.get(), title("podium"), description("podium"), null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("get_item", InventoryChangeTrigger.Instance.hasItems(ItemPredicate.Builder.item().of(TagRegistry.Items.PODIUMS).build()))
                    .save(consumer, References.MODID + ":" + References.MODID + "/podium");
            Advancement make_tea = Advancement.Builder.advancement().parent(root)
                    .display(BlockRegistry.WHITE_TERRACOTTA_TEAPOT.get(), title("make_tea"), description("make_tea"), null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("get_item", InventoryChangeTrigger.Instance.hasItems(ItemPredicate.Builder.item().of(TagRegistry.Items.FILLED_CUPS).build()))
                    .save(consumer, References.MODID + ":" + References.MODID + "/make_tea");

        }

        private static ICriterionInstance getItem(IItemProvider... items) {
            return InventoryChangeTrigger.Instance.hasItems(items);
        }

        private static ITextComponent title(String key) {
            return new TranslationTextComponent("advancements." + References.MODID + "." + key + ".title");
        }

        private static ITextComponent description(String key) {
            return new TranslationTextComponent("advancements." + References.MODID + "." + key + ".description");
        }
    }
}
