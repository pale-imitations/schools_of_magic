package com.paleimitations.schoolsofmagic.common.registries;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.MagicElement;
import com.paleimitations.schoolsofmagic.common.data.books.BookPageSpell;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.*;
import com.paleimitations.schoolsofmagic.common.spells.spells.*;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class SpellRegistry {

    public static final Map<ResourceLocation, Supplier<Spell>> SPELL_SUPPLIERS = Maps.newHashMap();
    public static final List<Modifier> MODIFIERS = Lists.newArrayList();

    public static void init() {
        registerSpell(() -> new BlazeSpell());
        registerSpell(() -> new ZephyrSpell());
        registerSpell(() -> new GrowthSpell());
        registerSpell(() -> new SnowballSpell());
        registerSpell(() -> new ShulkerBulletSpell());
        registerSpell(() -> new TranslocationSpell());
        registerSpell(() -> new FangMangleSpell());
        registerSpell(() -> new InvisibilitySpell());
        registerSpell(() -> new HealingSpell());
        //Increases Power
        new Modifier(new ResourceLocation(References.MODID, "potent_1"), spell -> spell instanceof IHasPower, true); //modest potency
        new Modifier(new ResourceLocation(References.MODID, "potent_2"), spell -> spell instanceof IHasPower, true); //strong potency
        new Modifier(new ResourceLocation(References.MODID, "potent_3"), spell -> spell instanceof IHasPower, true); //greater potency
        new Modifier(new ResourceLocation(References.MODID, "potent_4"), spell -> spell instanceof IHasPower, true); //superior potency
        //Decreases Power
        new Modifier(new ResourceLocation(References.MODID, "diminished_1"), spell -> spell instanceof IHasPower, false); //slight diminishment
        new Modifier(new ResourceLocation(References.MODID, "diminished_2"), spell -> spell instanceof IHasPower, false); //strong diminishment
        new Modifier(new ResourceLocation(References.MODID, "diminished_3"), spell -> spell instanceof IHasPower, false); //awful diminishment
        new Modifier(new ResourceLocation(References.MODID, "diminished_4"), spell -> spell instanceof IHasPower, false); //dreadful diminishment
        //Increases Duration
        new Modifier(new ResourceLocation(References.MODID, "lasting_1"), spell -> spell instanceof IHasDuration, true);
        new Modifier(new ResourceLocation(References.MODID, "lasting_2"), spell -> spell instanceof IHasDuration, true);
        new Modifier(new ResourceLocation(References.MODID, "lasting_3"), spell -> spell instanceof IHasDuration, true);
        new Modifier(new ResourceLocation(References.MODID, "lasting_4"), spell -> spell instanceof IHasDuration, true);
        //Decreases Duration
        new Modifier(new ResourceLocation(References.MODID, "fleeting_1"), spell -> spell instanceof IHasDuration, false);
        new Modifier(new ResourceLocation(References.MODID, "fleeting_2"), spell -> spell instanceof IHasDuration, false);
        new Modifier(new ResourceLocation(References.MODID, "fleeting_3"), spell -> spell instanceof IHasDuration, false);
        new Modifier(new ResourceLocation(References.MODID, "fleeting_4"), spell -> spell instanceof IHasDuration, false);
        //Increases Number of Uses
        new Modifier(new ResourceLocation(References.MODID, "economical_1"), spell -> spell instanceof IHasMultiUses, true);
        new Modifier(new ResourceLocation(References.MODID, "economical_2"), spell -> spell instanceof IHasMultiUses, true);
        new Modifier(new ResourceLocation(References.MODID, "economical_3"), spell -> spell instanceof IHasMultiUses, true);
        new Modifier(new ResourceLocation(References.MODID, "economical_4"), spell -> spell instanceof IHasMultiUses, true);
        //Decreases Number of Uses
        new Modifier(new ResourceLocation(References.MODID, "wasteful_1"), spell -> spell instanceof IHasMultiUses, false);
        new Modifier(new ResourceLocation(References.MODID, "wasteful_2"), spell -> spell instanceof IHasMultiUses, false);
        new Modifier(new ResourceLocation(References.MODID, "wasteful_3"), spell -> spell instanceof IHasMultiUses, false);
        new Modifier(new ResourceLocation(References.MODID, "wasteful_4"), spell -> spell instanceof IHasMultiUses, false);
        //Additional Element
        for(MagicElement element : MagicElementRegistry.ELEMENTS)
            new Modifier(new ResourceLocation(References.MODID, element.getName()),
                    spell -> (spell instanceof IHasAdjustableElements && ((IHasAdjustableElements)spell).isAcceptableElement(element)), true);
    }

    public static void registerSpell(Supplier<Spell> spellSupplier) {
        Spell spell = spellSupplier.get();
        BookPageRegistry.PAGES.add(new BookPageSpell(spell));
        SPELL_SUPPLIERS.put(spell.getResourceLocation(), spellSupplier);
    }

    public static Spell getSpell(String name) {
        Supplier<Spell> supplier = SPELL_SUPPLIERS.get(new ResourceLocation(name));
        return supplier!=null? supplier.get() : null;
    }

    public static Modifier getModifier(String location) {
        for(Modifier mod : MODIFIERS) {
            if(mod.getLocation().toString().equals(location))
                return mod;
        }
        return null;
    }
}
