package com.paleimitations.schoolsofmagic.common.spells.spells;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.registries.MagicElementRegistry;
import com.paleimitations.schoolsofmagic.common.registries.MagicSchoolRegistry;
import com.paleimitations.schoolsofmagic.common.spells.events.SpellEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.util.Random;

public class HealingSpell extends MultiUseSpell {

    public HealingSpell() {
        super();
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(References.MODID,"healing");
    }

    @Override
    public void init() {
        super.init();
        this.associations.add(MagicSchoolRegistry.ABJURATION);
        this.associations.add(MagicElementRegistry.HERBALMANCY);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand, ItemStack stack) {
        if(player.getHealth() < player.getMaxHealth() && this.castSpell(player)) {
            player.heal(1f);
            Random random = player.getRandom();
            if(world.isClientSide) {
                for(int i = 0; i < 10; ++i) {
                    double d2 = random.nextGaussian() * 0.02D;
                    double d3 = random.nextGaussian() * 0.02D;
                    double d4 = random.nextGaussian() * 0.02D;
                    double d0 = 0.5D;
                    double d5 = 0.5D;
                    double d6 = player.getX() + d5 + random.nextDouble() * d0 * 2.0D;
                    double d7 = player.getY() + d5 + random.nextDouble() * d0 * 2.0D;
                    double d8 = player.getZ() + d5 + random.nextDouble() * d0 * 2.0D;
                    if (!world.getBlockState((new BlockPos(d6, d7, d8)).below()).isAir()) {
                        world.addParticle(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, d2, d3, d4);
                    }
                    player.level.playSound(player, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0F, player.getRandom().nextFloat() * 0.4F + 0.8F);

                }
            }
        }
        return ActionResult.success(stack);
    }

    @Override
    public int getUsesPerCharge(int chargeLevel) {
        SpellEvent.UsesPerCharge event = new SpellEvent.UsesPerCharge(this, chargeLevel, 3 + (chargeLevel-getMinimumSpellChargeLevel())*2);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getUses();
    }
}
