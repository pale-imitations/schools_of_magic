package com.paleimitations.schoolsofmagic.common.registries;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.data.books.*;
import com.paleimitations.schoolsofmagic.common.spells.spells.*;
import net.minecraft.util.ResourceLocation;

public class BasicArcanaRegistry {

    public static void init() {
        new BookPageTableContent(null).addToList(BookPageRegistry.BASIC_ARCANA);
        new BookPageChapter(null).addToList(BookPageRegistry.BASIC_ARCANA);
        new BookPage("basic_arcana_title", Lists.newArrayList(
                new PageElementImage(new ResourceLocation(References.MODID, "textures/gui/books/images/basic_arcana_title.png"), 0,0,0,0,
                        256,256, 1f, false),
                new PageElementParagraphs("basic_arcana_title", 0.75f, 0, 2, new ParagraphBox(23,95, 0, 99, 95),
                        new ParagraphBox(134,50, 0, 99, 140), new ParagraphBox(23,50, 1, 99, 140),
                        new ParagraphBox(134,50, 1, 99, 140), new ParagraphBox(23,50, 2, 99, 140),
                        new ParagraphBox(134,50, 2, 99, 140), new ParagraphBox(23,50, 3, 99, 140),
                        new ParagraphBox(134,50, 3, 99, 140), new ParagraphBox(23,50, 4, 99, 140),
                        new ParagraphBox(134,50, 4, 99, 140))
        )).addToList(BookPageRegistry.BASIC_ARCANA);
        new BookPageSpell(new BlazeSpell()).addToList(BookPageRegistry.BASIC_ARCANA);
        new BookPageSpell(new FurnaceFuelSpell()).addToList(BookPageRegistry.BASIC_ARCANA);
        new BookPageSpell(new ZephyrSpell()).addToList(BookPageRegistry.BASIC_ARCANA);
        new BookPageSpell(new GrowthSpell()).addToList(BookPageRegistry.BASIC_ARCANA);
        new BookPageSpell(new SnowballSpell()).addToList(BookPageRegistry.BASIC_ARCANA);
        new BookPageSpell(new TranslocationSpell()).addToList(BookPageRegistry.BASIC_ARCANA);
        new BookPageSpell(new ShulkerBulletSpell()).addToList(BookPageRegistry.BASIC_ARCANA);
        new BookPageSpell(new RumorSpell()).addToList(BookPageRegistry.BASIC_ARCANA);
        new BookPageSpell(new HealingSpell()).addToList(BookPageRegistry.BASIC_ARCANA);
        new BookPageSpell(new SummonBeeSpell()).addToList(BookPageRegistry.BASIC_ARCANA);
        new BookPageSpell(new FastForwardSpell()).addToList(BookPageRegistry.BASIC_ARCANA);
        new BookPageSpell(new FangMangleSpell()).addToList(BookPageRegistry.BASIC_ARCANA);
        new BookPageSpell(new InvisibilitySpell()).addToList(BookPageRegistry.BASIC_ARCANA);
    }
}
