package com.paleimitations.schoolsofmagic.common.registries;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.MagicElement;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import com.paleimitations.schoolsofmagic.common.spells.SpellHelper;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.*;
import com.paleimitations.schoolsofmagic.common.spells.spells.*;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class SpellRegistry {

    public static final List<Spell> SPELLS = Lists.newArrayList();
    public static final List<Modifier> MODIFIERS = Lists.newArrayList();

    public static void init() {
        SpellHelper.registerSpellHelpers(new BlazeSpell());
        SpellHelper.registerSpellHelpers(new ZephyrSpell());
        SpellHelper.registerSpellHelpers(new GrowthSpell());
        SpellHelper.registerSpellHelpers(new SnowballSpell());
        SpellHelper.registerSpellHelpers(new ShulkerBulletSpell());
        SpellHelper.registerSpellHelpers(new TranslocationSpell());
        SpellHelper.registerSpellHelpers(new FangMangleSpell());
        SpellHelper.registerSpellHelpers(new InvisibilitySpell());
        SpellHelper.registerSpellHelpers(new HealingSpell());
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

    /**
     * Returns the registry instance of a spell. THIS SHOULD ONLY BE USED FOR REFERENCING STATIC VALUES WITHIN A SPELL AND SHOULD NOT BE MODIFIED!
     * @param name
     */
    public static Spell getDefaultSpell(String name) {
        for(Spell spell : SPELLS) {
            if(spell.getResourceLocation().toString().equals(name))
                return spell;
        }
        return null;
    }
}
