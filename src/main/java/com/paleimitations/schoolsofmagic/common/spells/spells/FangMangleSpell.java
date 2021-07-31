package com.paleimitations.schoolsofmagic.common.spells.spells;

import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.registries.MagicElementRegistry;
import com.paleimitations.schoolsofmagic.common.registries.MagicSchoolRegistry;
import com.paleimitations.schoolsofmagic.common.spells.events.SpellEvent;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasArea;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EvokerFangsEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class FangMangleSpell extends MultiUseSpell implements IHasArea {

    public FangMangleSpell() {
        super();
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(References.MODID,"fang_mangle");
    }

    @Override
    public int getMinimumSpellChargeLevel() {
        return 2;
    }

    @Override
    public void init() {
        super.init();
        this.associations.add(MagicSchoolRegistry.EVOCATION);
        this.associations.add(MagicSchoolRegistry.CONJURATION);
        this.associations.add(MagicElementRegistry.UMBRAMANCY);
        this.associations.add(MagicElementRegistry.CHAOTIMANCY);
    }

    @Override
    public int getUsesPerCharge(int chargeLevel) {
        SpellEvent.UsesPerCharge event = new SpellEvent.UsesPerCharge(this, chargeLevel, 3 + (chargeLevel-getMinimumSpellChargeLevel())*2);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getUses();
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand, ItemStack stack) {
        if (this.castSpell(player)) {
            Vector3d vec = player.getLookAngle();
            float f = (float) MathHelper.atan2(vec.z, vec.x);
            if(player.isCrouching()) {
                for (int i = 0; i < 5; ++i) {
                    float f1 = f + (float)i * (float)Math.PI * 0.4F;
                    this.createSpellEntity(player, player.getX() + (double)MathHelper.cos(f1) * 1.5D, player.getZ() + (double)MathHelper.sin(f1) * 1.5D, player.getY()-1f, player.getY()+1f, f1, 0);
                }

                for (int k = 0; k < 8; ++k) {
                    float f2 = f + (float)k * (float)Math.PI * 2.0F / 8.0F + ((float)Math.PI * 2F / 5F);
                    this.createSpellEntity(player, player.getX() + (double)MathHelper.cos(f2) * 2.5D, player.getZ() + (double)MathHelper.sin(f2) * 2.5D, player.getY()-1f, player.getY()+1f, f2, 3);
                }
            }
            else {
                for (int l = 0; l < 16; ++l) {
                    double d2 = 1.25D * (double)(l + 1);
                    int j = 1 * l;
                    this.createSpellEntity(player, player.getX() + (double)MathHelper.cos(f) * d2, player.getZ() + (double)MathHelper.sin(f) * d2, player.getY()-1f, player.getY()+1f, f-45f+90f*(l%2), j);
                }
            }
            player.level.playSound(player, player.blockPosition(), SoundEvents.EVOKER_FANGS_ATTACK, SoundCategory.PLAYERS, 1.0F, player.getRandom().nextFloat() * 0.4F + 0.8F);
            return ActionResult.success(stack);
        }
        return ActionResult.pass(stack);
    }

    private void createSpellEntity(PlayerEntity player, double x, double z, double minY, double maxY, float rot, int delay) {
        BlockPos blockpos = new BlockPos(x, maxY, z);
        boolean flag = false;
        double d0 = 0.0D;

        do {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = player.level.getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(player.level, blockpos1, Direction.UP)) {
                if (!player.level.isEmptyBlock(blockpos)) {
                    BlockState blockstate1 = player.level.getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(player.level, blockpos);
                    if (!voxelshape.isEmpty()) {
                        d0 = voxelshape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            blockpos = blockpos.below();
        } while(blockpos.getY() >= MathHelper.floor(minY) - 1);

        if (flag && !player.level.isClientSide) {
            player.level.addFreshEntity(new EvokerFangsEntity(player.level, x, (double)blockpos.getY() + d0, z, rot, delay, player));
        }
    }
}
