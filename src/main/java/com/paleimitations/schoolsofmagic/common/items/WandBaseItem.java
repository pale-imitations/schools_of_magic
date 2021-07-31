package com.paleimitations.schoolsofmagic.common.items;

import com.paleimitations.schoolsofmagic.common.data.books.BookPage;
import com.paleimitations.schoolsofmagic.common.data.books.BookPageSpell;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookDataProvider;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.IMagicData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.MagicDataProvider;
import com.paleimitations.schoolsofmagic.common.network.PacketHandler;
import com.paleimitations.schoolsofmagic.common.network.SetSpellPacket;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import com.paleimitations.schoolsofmagic.common.registries.SpellRegistry;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import com.paleimitations.schoolsofmagic.common.tileentities.PodiumTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WandBaseItem extends Item {

    private final int slotLimit;

    public WandBaseItem(Properties properties, int slotLimit) {
        super(properties.stacksTo(1));
        this.slotLimit = slotLimit;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        /*if(stack.getTag()!=null && stack.getTag().contains("slotLimit") && entity instanceof PlayerEntity) {
            IMagicData data = this.getMagicData((PlayerEntity) entity);
            if(data.getCurrentSpellSlot() > stack.getTag().getInt("slotLimit"))
                data.setCurrentSpellSlot(stack.getTag().getInt("slotLimit"));
        }
        else if(slotLimit > 0 && entity instanceof PlayerEntity) {
            IMagicData data = this.getMagicData((PlayerEntity) entity);
            if(data.getCurrentSpellSlot() > slotLimit)
                data.setCurrentSpellSlot(slotLimit);
        }*/
        if(entity.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY, null).isPresent()) {
            Spell spell = this.getCurrentSpell(entity);
            if (spell != null) {
                spell.inventoryTick(stack, world, entity, slot, selected);
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity livingEntity, Hand hand) {
        Spell spell = this.getCurrentSpell(player);
        if(spell!=null) {
            return spell.interactLivingEntity(stack, player, livingEntity, hand);
        }
        return super.interactLivingEntity(stack, player, livingEntity, hand);
    }

    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            if(this == ItemRegistry.APPRENTICE_WAND_1.get()) {
                ItemStack stack = new ItemStack(this);
                addSlotLimit(stack, 1);
                items.add(stack);
            }
            else if(this == ItemRegistry.APPRENTICE_WAND_2.get()) {
                ItemStack stack = new ItemStack(this);
                addSlotLimit(stack, 2);
                items.add(stack);
            }
            else if(this == ItemRegistry.APPRENTICE_WAND_3.get()) {
                ItemStack stack = new ItemStack(this);
                addSlotLimit(stack, 3);
                items.add(stack);
            }
            else if(this == ItemRegistry.APPRENTICE_WAND_4.get()) {
                ItemStack stack = new ItemStack(this);
                addSlotLimit(stack, 4);
                items.add(stack);
            }
            else {
                items.add(new ItemStack(this));
            }
        }
    }

    @Override
    public void onCraftedBy(ItemStack stack1, World world, PlayerEntity player) {
        if(stack1.getItem() == ItemRegistry.APPRENTICE_WAND_1.get()) {
            addSlotLimit(stack1, 1);
        }
        else if(stack1.getItem() == ItemRegistry.APPRENTICE_WAND_2.get()) {
            addSlotLimit(stack1, 2);
        }
        else if(stack1.getItem() == ItemRegistry.APPRENTICE_WAND_3.get()) {
            addSlotLimit(stack1, 3);
        }
        else if(stack1.getItem() == ItemRegistry.APPRENTICE_WAND_4.get()) {
            addSlotLimit(stack1, 4);
        }
    }

    public static void addSlotLimit(ItemStack stack, int slotLimit) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putInt("slotLimit", slotLimit);
        stack.setTag(nbt);
    }

    public IMagicData getMagicData(PlayerEntity player) {
        return player.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY, null).orElseThrow(IllegalStateException::new);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        Spell spell = this.getCurrentSpell(player);
        ItemStack stack = this.setUseStackSpell(player, player.getItemInHand(hand));
        if(spell!=null) {
            spell.use(world, player, hand, stack);
        }
        return ActionResult.pass(stack);
    }

    @Override
    public void onUseTick(World world, LivingEntity living, ItemStack stack, int count) {
        Spell spell = this.getCurrentSpell(living);

        if(spell!=null) {
            spell.onUseTick(world, living, stack, count);
        }
    }

    @Override
    public boolean useOnRelease(ItemStack stack) {
        if(stack.hasTag() && stack.getTag().contains("spell_release")){
            return stack.getTag().getBoolean("spell_release");
        }
        return false;
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        Spell spell = this.getCurrentSpell(context.getPlayer());
        PlayerEntity player = context.getPlayer();
        TileEntity te = player.level.getBlockEntity(context.getClickedPos());
        if(te!=null && te instanceof PodiumTileEntity) {
            PodiumTileEntity podium = (PodiumTileEntity) te;
            if(podium.hasBook() && player.level.isClientSide) {
                BookData data = BookDataProvider.getBook(player.level, podium.getItem());
                IMagicData magic = getMagicData(player);
                CompoundNBT nbt = podium.getItem().getOrCreateTag();
                int page = nbt.contains("page")? nbt.getInt("page") : 0;
                BookPage bookPage = data.getBookPage(page);
                if(bookPage instanceof BookPageSpell && ((BookPageSpell) bookPage).spell!=null) {
                    Spell spell1 = SpellRegistry.getSpell(((BookPageSpell) bookPage).spell.getResourceLocation().toString());
                    spell1.deserializeNBT(((BookPageSpell) bookPage).spell.serializeNBT());
                    magic.setCurrentSpell(spell1);
                    PacketHandler.INSTANCE.sendToServer(new SetSpellPacket(spell1.getResourceLocation(), spell1.serializeNBT(), Minecraft.getInstance().player.getId(), magic.getCurrentSpellSlot()));
                }
            }
            return ActionResultType.SUCCESS;
        }
        if(spell!=null) {
            return spell.useOn(context);
        }
        return ActionResultType.PASS;
    }

    @Override
    public void releaseUsing(ItemStack stack, World world, LivingEntity living, int count) {
        Spell spell = this.getCurrentSpell(living);
        if(spell!=null) {
            spell.releaseUsing(stack, world, living, count);
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity living, int count) {
        if(living.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY, null).isPresent()) {
            Spell spell = this.getCurrentSpell(living);
            if (spell != null) {
                spell.onUsingTick(stack, living, count);
            }
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity living, LivingEntity attacker) {
        Spell spell = this.getCurrentSpell(living);
        if(spell!=null) {
            return spell.hurtEnemy(stack, living, attacker);
        }
        return false;
    }

    @Override
    public boolean mineBlock(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity living) {
        Spell spell = this.getCurrentSpell(living);
        if(spell!=null) {
            return spell.mineBlock(stack, world, state, pos, living);
        }
        return false;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, PlayerEntity player) {
        Spell spell = this.getCurrentSpell(player);
        if(spell!=null) {
            return spell.onBlockStartBreak(stack, pos, player);
        }
        return false;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        Spell spell = this.getCurrentSpell(entity);
        if(spell!=null) {
            return spell.onPlayerSwing(stack, (PlayerEntity) entity);
        }
        return false;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        if(stack.hasTag() && stack.getTag().contains("spell_use_length")){
            return stack.getTag().getInt("spell_use_length");
        }
        return 0;
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        if(stack.hasTag() && stack.getTag().contains("spell_action")){
            UseAction action = UseAction.values()[stack.getTag().getInt("spell_action")];
            if(action!=null)
                return action;
        }
        return UseAction.BOW;
    }

    public ItemStack setUseStackSpell(Entity entity, ItemStack stack) {
        if(entity instanceof PlayerEntity) {
            IMagicData data = entity.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY, null).orElseThrow(IllegalStateException::new);
            CompoundNBT nbt = stack.getOrCreateTag();
            if(data.getCurrentSpell()!=null) {
                nbt.putInt("spell_action", data.getCurrentSpell().getAction().ordinal());
                nbt.putInt("spell_use_length", data.getCurrentSpell().getUseLength());
                nbt.putBoolean("spell_release", data.getCurrentSpell().useOnRelease());
                stack.setTag(nbt);
            }
            else if(nbt.contains("spell_action")) {
                nbt.remove("spell_action");
                nbt.remove("spell_use_length");
                nbt.remove("spell_release");
                stack.setTag(nbt);
            }
        }
        return stack;
    }

    public Spell getCurrentSpell(Entity entity) {
        if(entity instanceof PlayerEntity && !((PlayerEntity) entity).isDeadOrDying()) {
            IMagicData data = entity.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY, null).orElseThrow(IllegalStateException::new);
            if(data.getCurrentSpell()!=null) {
                return data.getCurrentSpell();
            }
        }
        return null;
    }
}
