package com.paleimitations.schoolsofmagic.common.spells.spells;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.registries.MagicElementRegistry;
import com.paleimitations.schoolsofmagic.common.registries.MagicSchoolRegistry;
import com.paleimitations.schoolsofmagic.common.spells.events.SpellEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class SunDrySpell extends MultiUseSpell {

    @Override
    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(References.MODID, "sun_dry");
    }

    @Override
    public void init() {
        super.init();
        this.associations.add(MagicSchoolRegistry.TRANSFIGURATION);
        this.associations.add(MagicElementRegistry.HELIOMANCY);
    }

    @Override
    public int getUsesPerCharge(int chargeLevel) {
        SpellEvent.UsesPerCharge event = new SpellEvent.UsesPerCharge(this, chargeLevel,(chargeLevel + 1) * 3);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getUses();
    }
}
