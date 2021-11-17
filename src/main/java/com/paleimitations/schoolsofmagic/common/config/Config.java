package com.paleimitations.schoolsofmagic.common.config;

import com.paleimitations.schoolsofmagic.References;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class Config {

    public static final class Common {
        static final ForgeConfigSpec spec;
        public static final ForgeConfigSpec.BooleanValue SHOW_PACKET_MESSAGES;
        public static final ForgeConfigSpec.IntValue MAX_LEVEL, MAX_SPELL_STORAGE;
        public static final ForgeConfigSpec.IntValue SPELL_CHARGE_1_RELOAD, SPELL_CHARGE_2_RELOAD, SPELL_CHARGE_3_RELOAD, SPELL_CHARGE_4_RELOAD, SPELL_CHARGE_5_RELOAD,
                SPELL_CHARGE_6_RELOAD, SPELL_CHARGE_7_RELOAD, SPELL_CHARGE_8_RELOAD, SPELL_CHARGE_9_RELOAD;

        static {
            ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
            {
                builder.comment("Magic system");
                builder.push("maxLevel");
                MAX_LEVEL = builder
                        .comment("Maximum magician level")
                        .defineInRange("max_level", 36, 1, 50);
                builder.pop();
            }
            {
                builder.push("maxSpellStorage");
                MAX_SPELL_STORAGE = builder
                        .comment("Maximum number of spells a magician can store")
                        .defineInRange("max_spell_storage", 20, 5, 20);
                builder.pop();
            }
            {
                builder.push("spellCharge1Reload");
                SPELL_CHARGE_1_RELOAD = builder
                        .comment("The time it takes for a first level spell charge to reload in ticks")
                        .defineInRange("spell_charge_1_reload", 600, 20, 28800);
                builder.pop();
            }
            {
                builder.push("spellCharge2Reload");
                SPELL_CHARGE_2_RELOAD = builder
                        .comment("The time it takes for a second level spell charge to reload in ticks")
                        .defineInRange("spell_charge_2_reload", 1200, 20, 28800);
                builder.pop();
            }
            {
                builder.push("spellCharge3Reload");
                SPELL_CHARGE_3_RELOAD = builder
                        .comment("The time it takes for a third level spell charge to reload in ticks")
                        .defineInRange("spell_charge_3_reload", 1800, 20, 28800);
                builder.pop();
            }
            {
                builder.push("spellCharge4Reload");
                SPELL_CHARGE_4_RELOAD = builder
                        .comment("The time it takes for a fourth level spell charge to reload in ticks")
                        .defineInRange("spell_charge_4_reload", 2400, 20, 28800);
                builder.pop();
            }
            {
                builder.push("spellCharge5Reload");
                SPELL_CHARGE_5_RELOAD = builder
                        .comment("The time it takes for a fifth level spell charge to reload in ticks")
                        .defineInRange("spell_charge_5_reload", 3600, 20, 28800);
                builder.pop();
            }
            {
                builder.push("spellCharge6Reload");
                SPELL_CHARGE_6_RELOAD = builder
                        .comment("The time it takes for a sixth level spell charge to reload in ticks")
                        .defineInRange("spell_charge_6_reload", 4800, 20, 28800);
                builder.pop();
            }
            {
                builder.push("spellCharge7Reload");
                SPELL_CHARGE_7_RELOAD = builder
                        .comment("The time it takes for a seventh level spell charge to reload in ticks")
                        .defineInRange("spell_charge_7_reload", 6000, 20, 28800);
                builder.pop();
            }
            {
                builder.push("spellCharge8Reload");
                SPELL_CHARGE_8_RELOAD = builder
                        .comment("The time it takes for a eighth level spell charge to reload in ticks")
                        .defineInRange("spell_charge_8_reload", 7200, 20, 28800);
                builder.pop();
            }
            {
                builder.push("spellCharge9Reload");
                SPELL_CHARGE_9_RELOAD = builder
                        .comment("The time it takes for a ninth level spell charge to reload in ticks")
                        .defineInRange("spell_charge_9_reload", 8400, 20, 28800);
                builder.pop();
            }
            {
                builder.comment("Debug");
                builder.push("showPacketMessages");
                SHOW_PACKET_MESSAGES = builder
                        .comment("Used to display server/client messages in logs for debug purposes, default true, switching to false will hide these messages.")
                        .define("show_packet_messages", true);
                builder.pop();
            }

            spec = builder.build();
        }

        private Common() {}
    }

    public static final class Client {
        static final ForgeConfigSpec spec;
        public static final ForgeConfigSpec.EnumValue<SpellGuiPosition> SPELL_GUI_POSITION;
        public static final ForgeConfigSpec.DoubleValue BOOK_FONT_SCALE;
        public static final ForgeConfigSpec.BooleanValue SHOW_PACKET_MESSAGES;

        static {
            ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
            {
                builder.comment("Position of the Spell casting gui");
                builder.push("spellGuiPosition");
                SPELL_GUI_POSITION = builder.defineEnum("spell_gui_position", SpellGuiPosition.BOTTOM_LEFT);
                builder.pop();

                builder.comment("Scale of the font in schools of magic books");
                builder.push("bookFontScale");
                BOOK_FONT_SCALE = builder.defineInRange("book_font_scale", 0.75d, 0.05d, 5d);
                builder.pop();
            }
            {
                builder.comment("Debug");
                builder.push("showPacketMessages");
                SHOW_PACKET_MESSAGES = builder
                        .comment("Used to display server/client messages in logs for debug purposes, default true, switching to false will hide these messages.")
                        .define("show_packet_messages", true);
                builder.pop();
            }

            spec = builder.build();
        }

        private Client() {}
    }

    public static SpellGuiPosition getSpellGuiPostion() {
        return Client.SPELL_GUI_POSITION.get();
    }

    private static boolean isResourceLocation(Object o) {
        return o instanceof String && ResourceLocation.tryParse((String) o) != null;
    }

    private Config() {}

    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Common.spec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Client.spec);
    }

    public static void sync() {
    }

    @SubscribeEvent
    public static void sync(ModConfig.Loading event) {
        sync();
    }

    @SubscribeEvent
    public static void sync(ModConfig.Reloading event) {
        sync();
    }

    public enum SpellGuiPosition {
        BOTTOM_LEFT, BOTTOM_RIGHT, LEFT, RIGHT, TOP_LEFT, TOP_RIGHT;
    }
}
