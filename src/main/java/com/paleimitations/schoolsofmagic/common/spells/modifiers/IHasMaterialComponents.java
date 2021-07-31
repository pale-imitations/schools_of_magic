package com.paleimitations.schoolsofmagic.common.spells.modifiers;

import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.IMagicData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IHasMaterialComponents {

    List<ItemStack> getMaterialComponents();
    boolean hasComponents(PlayerEntity player, IMagicData data);
    void useMaterialComponent();
}
