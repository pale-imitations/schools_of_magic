package com.paleimitations.schoolsofmagic.common.data;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.paleimitations.schoolsofmagic.common.TeaBrewResult;
import com.paleimitations.schoolsofmagic.common.registries.TeaIngredientRegistry;
import com.paleimitations.schoolsofmagic.common.registries.TeaRegistry;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class TeaUtils {

    public static ItemStack setTea(ItemStack stack, TeaBrewResult teaBrewResult) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.put("teaBrewResult", teaBrewResult.serialize());
        stack.setTag(nbt);
        return stack;
    }

    public static ItemStack setTea(ItemStack stack, TeaRegistry.Tea tea1, TeaRegistry.Tea tea2, TeaRegistry.Tea tea3, boolean isMilk) {
        return setTea(stack, tea1.name, tea2.name, tea3.name, isMilk);
    }

    public static ItemStack setTea(ItemStack stack, String tea1, String tea2, String tea3, boolean isMilk) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putString("tea1", tea1);
        nbt.putString("tea2", tea2);
        nbt.putString("tea3", tea3);
        nbt.putBoolean("isMilk", isMilk);
        stack.setTag(nbt);
        return stack;
    }

    public static TeaBrewResult getTea(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateTag();
        if(nbt.contains("teaBrewResult"))
            return new TeaBrewResult(nbt.getCompound("teaBrewResult"));
        else if(nbt.contains("tea1") && nbt.contains("tea2") && nbt.contains("tea3") && nbt.contains("isMilk")) {
            return new TeaBrewResult(nbt.getString("tea1"), nbt.getString("tea2"), nbt.getString("tea3"), nbt.getBoolean("isMilk"));
        }
        else
            return null;
    }

    public static int getColor(ItemStack itemStack) {
        if(itemStack.getOrCreateTag().contains("tea1")) {
            CompoundNBT nbt = itemStack.getOrCreateTag();
            return mixColors(nbt.getBoolean("isMilk"), TeaRegistry.getTea(nbt.getString("tea1")), TeaRegistry.getTea(nbt.getString("tea2")), TeaRegistry.getTea(nbt.getString("tea3")));
        }
        if(itemStack.getOrCreateTag().contains("teaBrewResult")) {
            return new TeaBrewResult(itemStack.getOrCreateTag().getCompound("teaBrewResult")).getColor();
        }
        return 4676607;
    }

    public static int mixColors(boolean isMilk, TeaRegistry.Tea... teas) {
        float f = 0.0F;
        float f1 = 0.0F;
        float f2 = 0.0F;
        int j = 0;

        for(TeaRegistry.Tea tea : teas) {
            if (tea!=null && tea.color >= 0) {
                int k = tea.color;
                int l = 1;
                f += (float)(l * (k >> 16 & 255)) / 255.0F;
                f1 += (float)(l * (k >> 8 & 255)) / 255.0F;
                f2 += (float)(l * (k >> 0 & 255)) / 255.0F;
                j += l;
            }
        }

        if(isMilk) {
            int k = Color.WHITE.getRGB();
            int l = 1;
            f += (float)(l * (k >> 16 & 255)) / 255.0F;
            f1 += (float)(l * (k >> 8 & 255)) / 255.0F;
            f2 += (float)(l * (k >> 0 & 255)) / 255.0F;
            j += l;
        }

        if (j == 0) {
            return 4676607;
        } else {
            f = f / (float)j * 255.0F;
            f1 = f1 / (float)j * 255.0F;
            f2 = f2 / (float)j * 255.0F;
            return (int)f << 16 | (int)f1 << 8 | (int)f2;
        }
    }

    public static String getName(ItemStack stack, String s) {
        TeaBrewResult brew = getTea(stack);
        if(brew!=null && brew.tea1!=null) {
            return s + (brew.isMilk? ".milk_tea." : ".tea.") + brew.tea1.name;
        }
        return s;
    }

    public static void addTooltip(ItemStack stack, List<ITextComponent> list, float v) {
        TeaBrewResult brew = getTea(stack);
        if(brew!=null) {
            List<EffectInstance> pool1 = brew.tier1Pool;
            List<EffectInstance> pool2 = brew.tier2Pool;
            List<EffectInstance> pool3 = brew.tier3Pool;
            List<Pair<Attribute, AttributeModifier>> list1 = Lists.newArrayList();
            if(!pool1.isEmpty()) {
                IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent("tea_pool_1.name");
                list.add(iformattabletextcomponent.withStyle(TextFormatting.GOLD));
                addTooltipForEffects(list, pool1, list1);
            }
            if(!pool2.isEmpty()) {
                IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent("tea_pool_2.name");
                list.add(iformattabletextcomponent.withStyle(TextFormatting.GOLD));
                addTooltipForEffects(list, pool2, list1);
            }
            if(!pool3.isEmpty()) {
                IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent("tea_pool_3.name");
                list.add(iformattabletextcomponent.withStyle(TextFormatting.GOLD));
                addTooltipForEffects(list, pool3, list1);
            }
        }
    }

    public static void addTooltipForEffects(List<ITextComponent> list, List<EffectInstance> effectInstances, List<Pair<Attribute, AttributeModifier>> list1) {
        for(EffectInstance effectinstance : effectInstances) {
            IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(effectinstance.getDescriptionId());
            Effect effect = effectinstance.getEffect();
            Map<Attribute, AttributeModifier> map = effect.getAttributeModifiers();
            if (!map.isEmpty()) {
                for(Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
                    AttributeModifier attributemodifier = entry.getValue();
                    AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), effect.getAttributeModifierValue(effectinstance.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                    list1.add(new Pair<>(entry.getKey(), attributemodifier1));
                }
            }

            if (effectinstance.getAmplifier() > 0) {
                iformattabletextcomponent = new TranslationTextComponent("potion.withAmplifier", iformattabletextcomponent, new TranslationTextComponent("potion.potency." + effectinstance.getAmplifier()));
            }

            if (effectinstance.getDuration() > 20) {
                iformattabletextcomponent = new TranslationTextComponent("potion.withDuration", iformattabletextcomponent, EffectUtils.formatDuration(effectinstance, 1f));
            }

            list.add(iformattabletextcomponent.withStyle(effect.getCategory().getTooltipFormatting()));
        }
    }
}
