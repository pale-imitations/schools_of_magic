package com.paleimitations.schoolsofmagic.common.data.loottables;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.SchoolsOfMagicMod;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = References.MODID)
public final class LootInjecter {
    public static final class Tables {
        private static final Map<ResourceLocation, ResourceLocation> MAP = new HashMap<>();

        public static final ResourceLocation ENTITIES_WITCH = inject(new ResourceLocation("entities/witch"));
        public static final ResourceLocation ENTITIES_EVOKER = inject(new ResourceLocation("entities/evoker"));
        public static final ResourceLocation ENTITIES_ILLUSIONER = inject(new ResourceLocation("entities/illusioner"));

        private Tables() {}

        public static Collection<ResourceLocation> getValues() {
            return MAP.values();
        }

        public static Optional<ResourceLocation> get(ResourceLocation lootTable) {
            return Optional.ofNullable(MAP.get(lootTable));
        }

        private static ResourceLocation inject(ResourceLocation lootTable) {
            ResourceLocation ret = new ResourceLocation(References.MODID,"inject/" + lootTable.getPath());
            MAP.put(lootTable, ret);
            return ret;
        }
    }

    public LootInjecter() {}

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        Tables.get(event.getName()).ifPresent(injectorName -> {
            event.getTable().addPool(
                    LootPool.lootPool()
                            .name("schoolsofmagic_injected")
                            .add(TableLootEntry.lootTableReference(injectorName))
                            .build()
            );
        });
    }
}
