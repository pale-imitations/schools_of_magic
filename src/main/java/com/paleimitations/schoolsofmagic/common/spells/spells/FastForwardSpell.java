package com.paleimitations.schoolsofmagic.common.spells.spells;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.data.Utils;
import com.paleimitations.schoolsofmagic.common.registries.MagicElementRegistry;
import com.paleimitations.schoolsofmagic.common.registries.MagicSchoolRegistry;
import com.paleimitations.schoolsofmagic.common.registries.SoundRegistry;
import com.paleimitations.schoolsofmagic.common.spells.events.SpellEvent;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;

import java.util.Random;

public class FastForwardSpell extends MultiUseSpell {

    @Override
    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(References.MODID,"fast_forward");
    }

    @Override
    public int getUsesPerCharge(int chargeLevel) {
        SpellEvent.UsesPerCharge event = new SpellEvent.UsesPerCharge(this, chargeLevel,((chargeLevel + 1)/2 + 1) * 100);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getUses();
    }

    @Override
    public void init() {
        super.init();
        this.associations.add(MagicSchoolRegistry.TRANSFIGURATION);
        this.associations.add(MagicElementRegistry.CHRONOMANCY);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand, ItemStack stack) {
        player.startUsingItem(hand);
        return ActionResult.success(stack);
    }

    @Override
    public int getUseLength() {
        return this.maxUses == 0 ? this.getUsesPerCharge(currentSpellChargeLevel) : this.maxUses;
    }

    @Override
    public void onUseTick(World world, LivingEntity living, ItemStack stack, int count) {
        if (living instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) living;
            Random rand = player.getRandom();
            SpellEvent.Power event = new SpellEvent.Power(this);
            MinecraftForge.EVENT_BUS.post(event);
            double distance = 10d + 4d * event.getMultiplier();
            LivingEntity base = Utils.getEntityOnVec(world, player, distance);
            if(base==null) {
                BlockRayTraceResult result = Utils.getPlayerPOVHitResult(player.level, player, RayTraceContext.FluidMode.NONE, distance);
                BlockState state = player.level.getBlockState(result.getBlockPos());
                if((state.isRandomlyTicking() || player.level.getBlockEntity(result.getBlockPos()) instanceof ITickableTileEntity) && castSpell(player)) {
                    if(state.isRandomlyTicking() && player.level instanceof ServerWorld) {
                        for(int i = 0; i <= lastSpellChargeLevel/2 + 1; ++i)
                            state.randomTick((ServerWorld) player.level, result.getBlockPos(), player.getRandom());
                    }
                    if(player.level.getBlockEntity(result.getBlockPos()) instanceof ITickableTileEntity) {
                        for(int i = 0; i <= lastSpellChargeLevel/2 + 1; ++i)
                            ((ITickableTileEntity)player.level.getBlockEntity(result.getBlockPos())).tick();
                    }
                    if(count%40==0 && player.getRandom().nextBoolean())
                        player.playSound(SoundRegistry.CLICKS, 1f, rand.nextFloat() * 0.4F + 0.8F);
                    if(rand.nextBoolean())
                        world.addParticle(ParticleTypes.ASH, result.getBlockPos().getX() + rand.nextDouble()*1.25d - 0.125d,
                                result.getBlockPos().getY() + rand.nextDouble()*1.25d - 0.125d, result.getBlockPos().getZ() + rand.nextDouble()*1.25d - 0.125d,
                                (rand.nextDouble() - rand.nextDouble())*0.1d, (rand.nextDouble() - rand.nextDouble())*0.1d,
                                (rand.nextDouble() - rand.nextDouble())*0.1d);
                }
            }
            else if(castSpell(player)) {
                for(int i = 0; i <= lastSpellChargeLevel/2 + 1; ++i)
                    base.tick();
                if(count%40==0 && player.getRandom().nextBoolean())
                    player.playSound(SoundRegistry.CLICKS, 1f, rand.nextFloat() * 0.4F + 0.8F);
                if(rand.nextBoolean())
                    world.addParticle(ParticleTypes.ASH, base.getX() + (rand.nextDouble() - rand.nextDouble())*base.getBbWidth(),
                        base.getY() + (rand.nextDouble() - rand.nextDouble())*base.getBbHeight(),
                        base.getZ() + (rand.nextDouble() - rand.nextDouble())*base.getBbWidth(), (rand.nextDouble() - rand.nextDouble())*0.1d,
                        (rand.nextDouble() - rand.nextDouble())*0.1d,(rand.nextDouble() - rand.nextDouble())*0.1d);
            }
            if(remainingUses==0) {
                player.stopUsingItem();
            }
        }
    }
}
