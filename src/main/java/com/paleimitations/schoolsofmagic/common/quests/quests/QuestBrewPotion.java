package com.paleimitations.schoolsofmagic.common.quests.quests;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.items.BookBaseItem;
import com.paleimitations.schoolsofmagic.common.quests.Quest;
import com.paleimitations.schoolsofmagic.common.quests.Task;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.brewing.PlayerBrewedPotionEvent;

public class QuestBrewPotion extends Quest {

    public QuestBrewPotion() {
        super(new ResourceLocation(References.MODID, "brew_potion"));
        this.initialize();
        //System.out.println("Created Brew Potion Quest");
    }

    public QuestBrewPotion(ResourceLocation location) {
        this();
    }

    public QuestBrewPotion(CompoundNBT nbt) {
        super(nbt);
    }

    @Override
    public void initialize() {
        System.out.println("Init Brew Potion Quest");
        this.tasks.clear();
        Task task = new Task(Task.EnumTaskType.POTION_BREW) {

            @Override
            public void onCompletion(PlayerEntity player) {
                QuestBrewPotion.this.markDirty();
            }

            @Override
            public boolean check(PlayerEntity player, Object object) {
                return object instanceof PlayerBrewedPotionEvent;
            }
        };
        task.setStarted(true);
        this.tasks.add(task);
        this.rewards.add(BookBaseItem.initialize(new ItemStack(ItemRegistry.BASIC_ARCANA.get())));
        ItemStack stack = new ItemStack(ItemRegistry.QUEST_NOTE.get());
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("quest", References.MODID+":intermediate_arcana");
        stack.setTag(nbt);
        //this.rewards.add(stack);
        this.icon = new ItemStack(Items.BREWING_STAND);
    }
}
