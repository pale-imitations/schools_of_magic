package com.paleimitations.schoolsofmagic.common.registries;

import com.paleimitations.schoolsofmagic.References;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundRegistry {

    public final static SoundEvent PAGE_FLIP = createSoundEvent("blocks.page_flip");
    public final static SoundEvent BOOK_OPEN = createSoundEvent("blocks.book_open");
    public final static SoundEvent BOOK_CLOSE = createSoundEvent("blocks.book_close");
    public final static SoundEvent HEART_AMBIENT = createSoundEvent("blocks.heart");
    public final static SoundEvent SNEEZE = createSoundEvent("entity.sneeze");
    public final static SoundEvent THORN_RING = createSoundEvent("spells.thorn_ring");
    public final static SoundEvent WHISPER = createSoundEvent("spells.whisper");
    public final static SoundEvent WITHER_BLIGHT = createSoundEvent("spells.wither_blight");
    public final static SoundEvent BLAZE = createSoundEvent("spells.blaze");
    public final static SoundEvent INVISIBILITY = createSoundEvent("spells.invisibility");
    public final static SoundEvent ELECTROCUTE = createSoundEvent("spells.lightning");
    public final static SoundEvent CONJURE_THORNS = createSoundEvent("spells.conjure_thorns");
    public final static SoundEvent ENERGIZE = createSoundEvent("spells.energize");
    public final static SoundEvent FURNACE_FUEL = createSoundEvent("spells.furnace_fuel");
    public final static SoundEvent FIERY_BLESSING = createSoundEvent("spells.fiery_blessing");
    public final static SoundEvent PHANTOM_FIRE = createSoundEvent("spells.phantom_fire");
    public final static SoundEvent SPECTRAL_HAND = createSoundEvent("spells.spectral_hand");
    public final static SoundEvent CLICKS = createSoundEvent("spells.clicks");

    /*public static SoundEvent PAGE_FLIP, BOOK_OPEN, BOOK_CLOSE;
    public static SoundEvent HEART_AMBIENT, SNEEZE, THORN_RING, WHISPER;
    public static SoundEvent WITHER_BLIGHT, BLAZE, INVISIBILITY, ELECTROCUTE, CONJURE_THORNS, ENERGIZE, FURNACE_FUEL, FIERY_BLESSING, PHANTOM_FIRE, SPECTRAL_HAND;

    public static void init(RegistryEvent.Register<SoundEvent> event) {
        PAGE_FLIP = register("blocks.page_flip", event);
        BOOK_OPEN = register("blocks.book_open", event);
        BOOK_CLOSE = register("blocks.book_close", event);
        WHISPER = register("spells.whisper", event);
        HEART_AMBIENT = register("blocks.heart", event);
        SNEEZE = register("entity.sneeze", event);
        THORN_RING = register("spells.thorn_ring", event);
        WITHER_BLIGHT = register("spells.wither_blight", event);
        BLAZE = register("spells.blaze", event);
        INVISIBILITY = register("spells.invisibility", event);
        ELECTROCUTE = register("spells.lightning", event);
        CONJURE_THORNS = register("spells.conjure_thorns", event);
        ENERGIZE = register("spells.energize", event);
        SPECTRAL_HAND = register("spells.spectral_hand", event);
        FIERY_BLESSING = register("spells.fiery_blessing", event);
        PHANTOM_FIRE = register("spells.phantom_fire", event);
        FURNACE_FUEL = register("spells.furnace_fuel", event);
    }

    public static SoundEvent register(String name, RegistryEvent.Register<SoundEvent> registry) {
        ResourceLocation location = new ResourceLocation(References.MODID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        registry.getRegistry().register(event);
        System.out.println("Registered SoundEvent: "+name);
        return event;
    }*/

    private static SoundEvent createSoundEvent(final String soundName) {
        final ResourceLocation soundID = new ResourceLocation(References.MODID, soundName);
        return new SoundEvent(soundID).setRegistryName(soundID);
    }
}
