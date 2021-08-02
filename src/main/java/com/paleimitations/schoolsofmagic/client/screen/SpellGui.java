package com.paleimitations.schoolsofmagic.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.client.ClientProxy;
import com.paleimitations.schoolsofmagic.common.config.Config;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.IMagicData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.MagicData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.magic_data.MagicDataProvider;
import com.paleimitations.schoolsofmagic.common.items.WandBaseItem;
import com.paleimitations.schoolsofmagic.common.network.PacketHandler;
import com.paleimitations.schoolsofmagic.common.network.SwapSpellChargePacket;
import com.paleimitations.schoolsofmagic.common.network.SwapSpellSlotPacket;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasDuration;
import com.paleimitations.schoolsofmagic.common.spells.modifiers.IHasMultiUses;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SpellGui extends AbstractGui {

    private final int texWidth = 114, texHeight = 114;
    private static final ResourceLocation SPELL_CHARGE_ICONS = new ResourceLocation(References.MODID, "textures/gui/spell_charge_icons.png");
    private static final ResourceLocation MAGICIAN_HUD_ICONS = new ResourceLocation(References.MODID, "textures/gui/magician_level_hud_elements.png");
    private int animationTick;
    private int prevSlot;
    private int animateSlot;
    private Minecraft minecraft;
    private FontRenderer font;

    public SpellGui(Minecraft minecraft) {
        this.minecraft = minecraft;
        this.font = minecraft.font;
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void renderSpellRing(RenderGameOverlayEvent event) {
        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT && minecraft.player!=null)
            this.render(event.getMatrixStack(), event.getPartialTicks());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onMouseEvent(InputEvent.MouseScrollEvent event){
        PlayerEntity player = this.minecraft.player;
        if(ClientProxy.SPELL.isDown() && player.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY).isPresent()) {
            IMagicData data = player.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY).orElseThrow(IllegalStateException::new);
            float dW = (float) event.getScrollDelta();
            if (dW > 0f)
                dW = 1f;
            if (dW < 0f)
                dW = -1f;
            if(dW!=0f) {
                if(player.getMainHandItem().getItem() instanceof WandBaseItem) {
                    if(player.isShiftKeyDown()) {
                        Spell spell = data.getCurrentSpell();
                        if(spell!=null) {
                            int j = spell.currentSpellChargeLevel + (int) dW;
                            int a = data.getLargestChargeLevel();
                            int level = MathHelper.clamp(j, spell.getMinimumSpellChargeLevel(), Math.min(a, spell.getMaximumSpellChargeLevel()));
                            if(level != spell.currentSpellChargeLevel) {
                                PacketHandler.INSTANCE.sendToServer(new SwapSpellChargePacket(player.getId(), data.getCurrentSpellSlot(), level));
                                spell.currentSpellChargeLevel = level;
                            }
                        }
                    }
                    else {
                        int j = data.getCurrentSpellSlot() + (int) dW;
                        int a = data.getSpellSlots(player.getMainHandItem());
                        while (j < 0)
                            j += a;
                        int i = j % a;
                        PacketHandler.INSTANCE.sendToServer(new SwapSpellSlotPacket(player.getId(), i));
                        data.setCurrentSpellSlot(i);
                    }
                    player.inventory.swapPaint(-dW);
                }
            }
        }
    }

    public void render(MatrixStack matrix, float partialTicks) {
        PlayerEntity player = minecraft.player;
        ItemStack stack = player!=null? player.getMainHandItem() : ItemStack.EMPTY;
        if(player.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY).isPresent()) {
            IMagicData data = player.getCapability(MagicDataProvider.MAGIC_DATA_CAPABILITY).orElseThrow(IllegalStateException::new);
            if (stack.getItem() instanceof WandBaseItem) {
                int screenWidth = this.minecraft.getWindow().getGuiScaledWidth();
                int screenHeight = this.minecraft.getWindow().getGuiScaledHeight();
                int xPos = (screenWidth / 2) - (texWidth / 2);
                int yPos = (screenHeight / 2) - (texHeight / 2);

                if (!ClientProxy.SPELL.isDown()) {
                    if (data.getCurrentSpell() != null) {
                        Spell spell = data.getCurrentSpell();
                        int i = spell.currentSpellChargeLevel;
                        boolean usable = i >= spell.getMinimumSpellChargeLevel();
                        float countdown = data.getCountdowns()[i];
                        float maxCountdown = MagicData.MAX_COUNTDOWNS[i];
                        float cooldown = (maxCountdown - countdown) / maxCountdown;

                        //Spell icon
                        minecraft.getTextureManager().bind(getTexture(player.getMainHandItem()));
                        switch(Config.getSpellGuiPostion()) {
                            case BOTTOM_LEFT:
                                this.blit(matrix, 0, screenHeight - 38, 0, 77, 38, 38);
                                this.drawSpellIcon(matrix, 3 + 16, screenHeight - 35 + 16, data.getCurrentSpell(), 1f);
                                break;
                            case BOTTOM_RIGHT:
                                this.blit(matrix, screenWidth - 38, screenHeight - 38, 38, 77, 38, 38);
                                this.drawSpellIcon(matrix, screenWidth - 35 + 16, screenHeight - 35 + 16, data.getCurrentSpell(), 1f);
                                break;
                            case TOP_LEFT:
                                this.blit(matrix, 0, 0, 0, 39, 38, 38);
                                this.drawSpellIcon(matrix, 3 + 16, 3 + 16, data.getCurrentSpell(), 1f);
                                break;
                            case TOP_RIGHT:
                                this.blit(matrix, screenWidth - 38, 0, 38, 39, 38, 38);
                                this.drawSpellIcon(matrix, screenWidth - 35 + 16, 3 + 16, data.getCurrentSpell(), 1f);
                                break;
                            case LEFT:
                                this.blit(matrix, 0, screenHeight/2 - 19, 87, 66, 54, 45);
                                this.drawSpellIcon(matrix, 11 + 16, screenHeight/2 - 19 + 3 + 16, data.getCurrentSpell(), 1f);
                                break;
                            case RIGHT:
                                this.blit(matrix, screenWidth - 54, screenHeight/2 - 19, 87, 66, 54, 45);
                                this.drawSpellIcon(matrix, screenWidth - 54 + 11 + 16, screenHeight/2 - 19 + 3 + 16, data.getCurrentSpell(), 1f);
                                break;
                        }

                        matrix.pushPose();
                        float scale = 0.65f;
                        switch(Config.getSpellGuiPostion()) {
                            case BOTTOM_LEFT:
                                matrix.translate(38f * (1f - scale) / 2f, (float) screenHeight - 38f * (1f + scale), 0);
                                break;
                            case BOTTOM_RIGHT:
                                matrix.translate((float) screenWidth - 38f * (1f + scale) / 2f, (float) screenHeight - 38f * (1f + scale), 0);
                                break;
                            case TOP_LEFT:
                                matrix.translate(38f * (1f - scale) / 2f, 38, 0);
                                break;
                            case TOP_RIGHT:
                                matrix.translate((float) screenWidth - 38f * (1f + scale) / 2f, 38, 0);
                                break;
                            case LEFT:
                                matrix.translate(54f / 2f - 38f * scale / 2f, screenHeight/2 - 19 - 38f * scale, 0);
                                break;
                            case RIGHT:
                                matrix.translate((float) screenWidth - 54f / 2f - 38f * scale / 2f, screenHeight/2 - 19 - 38f * scale, 0);
                                break;
                        }
                        matrix.scale(scale, scale, scale);
                        minecraft.getTextureManager().bind(getTexture(player.getMainHandItem()));
                        this.blit(matrix, 0, 0, 38, 0, 38, 38);
                        minecraft.getTextureManager().bind(SPELL_CHARGE_ICONS);
                        this.blit(matrix, 3, 3, (i % 3) * 32 + 96, (i / 3) * 32, 32, 32);

                        this.blit(matrix, 3, 3 + Math.round(32f * countdown / maxCountdown), (i % 3) * 32,
                                (i / 3) * 32 + (usable ? 0 : 96) + Math.round(32f * countdown / maxCountdown), 32, Math.round(32f * cooldown));

                        int a = data.getMaxCharges(i, data.getLevel());
                        int b = data.getCharges()[i];
                        for (int j = 1; j <= a; j++) {
                            switch(Config.getSpellGuiPostion()) {
                                case BOTTOM_LEFT:
                                case BOTTOM_RIGHT:
                                case LEFT:
                                case RIGHT:
                                    minecraft.getTextureManager().bind(getTexture(player.getMainHandItem()));
                                    this.blit(matrix, 9, -1 - 16 * j, 136, 0, 20, 20);
                                    minecraft.getTextureManager().bind(SPELL_CHARGE_ICONS);
                                    if (j <= b) {
                                        if (usable)
                                            this.blit(matrix, 12, 3 - 16 * j, 14 * i, 192, 14, 14);
                                        else
                                            this.blit(matrix, 12, 3 - 16 * j, 0, 220, 14, 14);
                                    }
                                    break;
                                case TOP_LEFT:
                                case TOP_RIGHT:
                                    minecraft.getTextureManager().bind(getTexture(player.getMainHandItem()));
                                    this.blit(matrix, 9, 19 + 16 * j, 96, 0, 20, 20);
                                    minecraft.getTextureManager().bind(SPELL_CHARGE_ICONS);
                                    if (j <= b) {
                                        if (usable)
                                            this.blit(matrix, 12, 21 + 16 * j, 14 * i, 192, 14, 14);
                                        else
                                            this.blit(matrix, 12, 21 + 16 * j, 0, 220, 14, 14);
                                    }
                                    break;
                            }
                        }
                        matrix.popPose();

                        if ((spell instanceof IHasDuration && ((IHasDuration) spell).getMaxDuration() > 0) || (spell instanceof IHasMultiUses && ((IHasMultiUses)spell).getUsesPerCharge(spell.lastSpellChargeLevel) >= 100)) {
                            int width = spell instanceof IHasDuration ? Math.round((float) ((IHasDuration) spell).getDuration() / (float) ((IHasDuration) spell).getMaxDuration() * 64f) :
                                    spell instanceof IHasMultiUses ? Math.round((float) ((IHasMultiUses) spell).getUses() / (float) ((IHasMultiUses) spell).getMaxUses(spell.lastSpellChargeLevel) * 64f) : 64;

                            matrix.pushPose();
                            float scaleDur = 0.65f;
                            switch(Config.getSpellGuiPostion()) {
                                case BOTTOM_LEFT:
                                    matrix.translate(40f, (float) screenHeight - 9f - (12f * scaleDur) / 2f, 0);
                                    matrix.scale(scaleDur, scaleDur, scaleDur);
                                    minecraft.getTextureManager().bind(getTexture(player.getMainHandItem()));
                                    this.blit(matrix, 0, 0, 101, 49, 72, 12);
                                    minecraft.getTextureManager().bind(SPELL_CHARGE_ICONS);
                                    this.blit(matrix, 4, 4, 101, 151 + 4 * spell.currentSpellChargeLevel, width, 4);
                                    matrix.popPose();
                                    break;
                                case BOTTOM_RIGHT:
                                    matrix.translate((float) screenWidth - 40f - 72f * scaleDur, (float) screenHeight - 9f - (12f * scaleDur) / 2f, 0);
                                    matrix.scale(scaleDur, scaleDur, scaleDur);
                                    minecraft.getTextureManager().bind(getTexture(player.getMainHandItem()));
                                    this.blit(matrix, 0, 0, 101, 49, 72, 12);
                                    minecraft.getTextureManager().bind(SPELL_CHARGE_ICONS);
                                    this.blit(matrix, 4, 4, 101, 151 + 4 * spell.currentSpellChargeLevel, width, 4);
                                    matrix.popPose();
                                    break;
                                case TOP_LEFT:
                                    matrix.translate(40f, 19f - (12f * scaleDur) / 2f, 0);
                                    matrix.scale(scaleDur, scaleDur, scaleDur);
                                    minecraft.getTextureManager().bind(getTexture(player.getMainHandItem()));
                                    this.blit(matrix, 0, 0, 101, 49, 72, 12);
                                    minecraft.getTextureManager().bind(SPELL_CHARGE_ICONS);
                                    this.blit(matrix, 4, 4, 101, 151 + 4 * spell.currentSpellChargeLevel, width, 4);
                                    matrix.popPose();
                                    break;
                                case TOP_RIGHT:
                                    matrix.translate((float) screenWidth - 40f - 72f * scaleDur, 19f - (12f * scaleDur) / 2f, 0);
                                    matrix.scale(scaleDur, scaleDur, scaleDur);
                                    minecraft.getTextureManager().bind(getTexture(player.getMainHandItem()));
                                    this.blit(matrix, 0, 0, 101, 49, 72, 12);
                                    minecraft.getTextureManager().bind(SPELL_CHARGE_ICONS);
                                    this.blit(matrix, 4, 4, 101, 151 + 4 * spell.currentSpellChargeLevel, width, 4);
                                    matrix.popPose();
                                    break;
                                case LEFT:
                                    matrix.translate(27 - (12f * scaleDur) / 2f, screenHeight/2 + 28, 0);
                                    matrix.scale(scaleDur, scaleDur, scaleDur);
                                    minecraft.getTextureManager().bind(getTexture(player.getMainHandItem()));
                                    this.blit(matrix, 0, 0, 189, 21, 12, 72);
                                    minecraft.getTextureManager().bind(SPELL_CHARGE_ICONS);
                                    this.blit(matrix, 4, 4, 128 + 4 * spell.currentSpellChargeLevel, 189, 4, width);
                                    matrix.popPose();
                                    break;
                                case RIGHT:
                                    matrix.translate((float) screenWidth - 27 - (12f * scaleDur) / 2f, screenHeight/2 + 28, 0);
                                    matrix.scale(scaleDur, scaleDur, scaleDur);
                                    minecraft.getTextureManager().bind(getTexture(player.getMainHandItem()));
                                    this.blit(matrix, 0, 0, 189, 21, 12, 72);
                                    minecraft.getTextureManager().bind(SPELL_CHARGE_ICONS);
                                    this.blit(matrix, 4, 4, 128 + 4 * spell.currentSpellChargeLevel, 189, 4, width);
                                    matrix.popPose();
                                    break;
                            }
                        } else if (spell instanceof IHasMultiUses) {
                            String remUses = String.valueOf(((IHasMultiUses) spell).getUses());
                            minecraft.getTextureManager().bind(getTexture(player.getMainHandItem()));
                            float scaleUses = Math.min(11f / (float) this.font.width(remUses), 10f / (float) this.font.lineHeight);
                            switch(Config.getSpellGuiPostion()) {
                                case BOTTOM_LEFT:
                                    this.blit(matrix, 40, screenHeight - 29, 167, 21, 21, 20);
                                    matrix.pushPose();
                                    matrix.translate(51f - (float) this.font.width(remUses) / 2f, (float) screenHeight - 18f - (float) this.font.lineHeight / 2f, 0);
                                    break;
                                case BOTTOM_RIGHT:
                                    this.blit(matrix, screenWidth - 40 - 21, screenHeight - 29, 167, 21, 21, 20);
                                    matrix.pushPose();
                                    matrix.translate(screenWidth - 50f - (float) this.font.width(remUses) / 2f, (float) screenHeight - 18f - (float) this.font.lineHeight / 2f, 0);
                                    break;
                                case LEFT:
                                    this.blit(matrix, 17, screenHeight/2 + 28, 167, 21, 21, 20);
                                    matrix.pushPose();
                                    matrix.translate(28 - (float) this.font.width(remUses) / 2f, (float) screenHeight/2f + 28f + 11f - (float) this.font.lineHeight / 2f, 0);
                                    break;
                                case RIGHT:
                                    this.blit(matrix, screenWidth - 10 - 27, screenHeight/2 + 28, 167, 21, 21, 20);
                                    matrix.pushPose();
                                    matrix.translate(screenWidth - 27 - (float) this.font.width(remUses) / 2f, (float) screenHeight/2f + 28f + 11f - (float) this.font.lineHeight / 2f, 0);
                                    break;
                                case TOP_LEFT:
                                    this.blit(matrix, 40, 9, 167, 21, 21, 20);
                                    matrix.pushPose();
                                    matrix.translate(51f - (float) this.font.width(remUses) / 2f, 20 - (float) this.font.lineHeight / 2f, 0);
                                    break;
                                case TOP_RIGHT:
                                    this.blit(matrix, screenWidth - 40 - 21, 9, 167, 21, 21, 20);
                                    matrix.pushPose();
                                    matrix.translate(screenWidth - 50f - (float) this.font.width(remUses) / 2f, 20 - (float) this.font.lineHeight / 2f, 0);
                                    break;
                            }
                            matrix.scale(scaleUses, scaleUses, scaleUses);
                            this.font.draw(matrix, remUses, 0, 0, Color.BLACK.getRGB());
                            this.font.draw(matrix, remUses, 0, -1, 16777215);
                            matrix.popPose();
                        }
                    }
                } else {
                    int maxSpellCharge = data.getLargestChargeLevel() + 1;
                    int magicianHUDX = screenWidth - 29;
                    int magicianHUDY = 0;
                    minecraft.getTextureManager().bind(MAGICIAN_HUD_ICONS);
                    this.blit(matrix, magicianHUDX, magicianHUDY, (maxSpellCharge - 1) * 24, 128, 24, 128);

                    int magicianLevelHeight = Math.round(Math.min(1f, data.getMagicianXPToNextLevel().getA() / data.getMagicianXPToNextLevel().getB()) * 102f);
                    minecraft.getTextureManager().bind(MAGICIAN_HUD_ICONS);
                    this.blit(matrix, magicianHUDX + 3, magicianHUDY, (maxSpellCharge - 1) * 19 + 71, 0, 18, magicianLevelHeight);

                    int magicianLvl = data.getLevel();
                    minecraft.getTextureManager().bind(MAGICIAN_HUD_ICONS);
                    if (magicianLvl < 10) {
                        this.blit(matrix, magicianHUDX + 9, magicianHUDY + 105, magicianLvl * 7, maxSpellCharge * 10, 7, 9);
                    } else {
                        this.blit(matrix, magicianHUDX + 5, magicianHUDY + 105, (magicianLvl / 10) * 7, maxSpellCharge * 10, 7, 9);
                        this.blit(matrix, magicianHUDX + 12, magicianHUDY + 105, (magicianLvl % 10) * 7, maxSpellCharge * 10, 7, 9);
                    }

                    minecraft.getTextureManager().bind(getTexture(player.getMainHandItem()));
                    this.blit(matrix, xPos, yPos, 0, 115, texWidth, texHeight);

                    float chargeScale = Math.min((((float) screenHeight * 0.8f) / (float) maxSpellCharge) / 38f, 1f);
                    matrix.pushPose();
                    matrix.translate(0, (float) screenHeight / 2f, 0);
                    matrix.scale(chargeScale, chargeScale, chargeScale);
                    matrix.translate(0, -(38f * (float) maxSpellCharge) / 2f, 0);
                    for (int i = 0; i < maxSpellCharge; ++i) {
                        boolean hasSpell = data.getCurrentSpell() != null;
                        boolean isSelected = hasSpell && data.getCurrentSpell().currentSpellChargeLevel == i;
                        boolean usable = hasSpell && i >= data.getCurrentSpell().getMinimumSpellChargeLevel() && i <= data.getCurrentSpell().getMaximumSpellChargeLevel();
                        float countdown = data.getCountdowns()[i];
                        float maxCountdown = MagicData.MAX_COUNTDOWNS[i];
                        float cooldown = (maxCountdown - countdown) / maxCountdown;
                        minecraft.getTextureManager().bind(getTexture(player.getMainHandItem()));
                        this.blit(matrix, 0, 38 * i, isSelected ? 0 : 38, 0, 38, 38);
                        int a = data.getMaxCharges(i, data.getLevel());
                        int b = data.getCharges()[i];
                        for (int j = 1; j <= a; j++) {
                            minecraft.getTextureManager().bind(getTexture(player.getMainHandItem()));
                            this.blit(matrix, 16 * j + 19, 38 * i + 9, 76, 0, 20, 20);
                            minecraft.getTextureManager().bind(SPELL_CHARGE_ICONS);
                            if (j <= b) {
                                if (usable)
                                    this.blit(matrix, 16 * j + 21, 38 * i + 12, 14 * i, 192 + (isSelected ? 0 : 14), 14, 14);
                                else
                                    this.blit(matrix, 16 * j + 21, 38 * i + 12, 0, 220, 14, 14);
                            }
                        }

                        minecraft.getTextureManager().bind(SPELL_CHARGE_ICONS);
                        this.blit(matrix, 3, (38 * i) + 3, (i % 3) * 32 + 96, (i / 3) * 32, 32, 32);

                        this.blit(matrix, 3, (38 * i) + 3 + Math.round(32f * countdown / maxCountdown), (i % 3) * 32,
                                (i / 3) * 32 + (usable ? 0 : 96) + Math.round(32f * countdown / maxCountdown), 32, Math.round(32f * cooldown));
                    }
                    matrix.popPose();

                    String s;
                    if (data.getCurrentSpell() != null) {
                        s = I18n.get("spell." + data.getCurrentSpell().getName() + ".name");
                        this.font.draw(matrix, s, screenWidth / 2 - this.font.width(s) / 2, yPos - 25, Color.BLACK.getRGB());
                        this.font.draw(matrix, s, screenWidth / 2 - this.font.width(s) / 2, yPos - 26, 16777215);
                    }

                    if (animationTick > 0)
                        --animationTick;
                    if (prevSlot != data.getCurrentSpellSlot()) {
                        if (animationTick < 60)
                            animationTick += 30;
                        animateSlot = prevSlot;
                    }
                    if (animationTick == 0)
                        animateSlot = data.getCurrentSpellSlot();
                    prevSlot = data.getCurrentSpellSlot();

                    int spells = data.getSpellSlots(player.getMainHandItem());
                    float difference = animateSlot - data.getCurrentSpellSlot();
                    if (Math.abs(difference) >= spells - 1) {
                        if (difference < 0)
                            difference = animateSlot - data.getCurrentSpellSlot() + spells;
                    }

                    float angle = animateSlot == data.getCurrentSpellSlot() ? (float) data.getCurrentSpellSlot() / (float) spells * 360f : (data.getCurrentSpellSlot() + difference * ((float) animationTick + partialTicks) / 30f) / (float) spells * 360f;
                    if (difference > 0 && Math.abs(difference) >= spells - 1) {
                        difference = animateSlot - data.getCurrentSpellSlot() - spells;
                        angle = (data.getCurrentSpellSlot() + difference * ((float) animationTick + partialTicks) / 30f) / (float) spells * 360f;
                    }

                    for (int i = 0; i < spells; ++i) {
                        int radius = 54;

                        Spell spell = data.getSpell(i);
                        matrix.pushPose();
                        matrix.translate(screenWidth / 2f, screenHeight / 2f - 1.5f, 0f);
                        matrix.mulPose(Vector3f.ZP.rotationDegrees(-angle + (float) i / (float) spells * 360f));
                        float scale = 1f;
                        if (spells == 9) scale = 0.91f;
                        if (spells == 10) scale = 0.85f;
                        if (spells == 11) scale = 0.79f;
                        if (spells == 12) scale = 0.74f;
                        if (spells == 13) scale = 0.69f;
                        if (spells == 14) scale = 0.64f;
                        if (spells == 15) scale = 0.60f;
                        if (spells == 16) scale = 0.56f;
                        if (spells == 17) scale = 0.52f;
                        if (spells == 18) scale = 0.48f;
                        if (spells == 19) scale = 0.46f;
                        if (spells == 20) scale = 0.45f;//spells>9? 1f - 0.05f*(spells-9) : 1f;
                        this.drawSpellSlot(matrix, 0, -radius, player.getMainHandItem(), scale);
                        if (spell != null)
                            this.drawSpellIcon(matrix, 0, -radius, spell, scale);
                        matrix.popPose();
                    }

                    RenderSystem.pushMatrix();
                    RenderSystem.translatef(screenWidth / 2, screenHeight / 2, 0F);
                    RenderSystem.rotatef(-45f, 0f, 0f, 1f);
                    RenderSystem.scalef(3.0F, 3.0F, 3.0F);
                    RenderSystem.translatef(-8.0F, -8.0F, 0.0F);
                    this.drawItemStack(player.getMainHandItem(), 0, 0);
                    RenderSystem.popMatrix();
                }
            }
        }
    }

    public ResourceLocation getTexture(ItemStack wand) {
        /*if(wand==null || wand.getHandleType()==null)
            return new ResourceLocation(References.MODID, "textures/gui/apprentice_wand.png");
        switch(wand.getHandleType()){
            case GOLD:
                return new ResourceLocation(References.MODID, "textures/gui/spell_ring_gold.png");
            case SILVER:
                return new ResourceLocation(References.MODID, "textures/gui/spell_ring_silver.png");
            case VOID:
                return new ResourceLocation(References.MODID, "textures/gui/spell_ring_void.png");
            case COPPER:
                return new ResourceLocation(References.MODID, "textures/gui/spell_ring_copper.png");
            case BRONZE:
                return new ResourceLocation(References.MODID, "textures/gui/spell_ring_bronze.png");
            case BRASS:
                return new ResourceLocation(References.MODID, "textures/gui/spell_ring_brass.png");
            case IRON:
                return new ResourceLocation(References.MODID, "textures/gui/spell_ring_iron.png");
        }*/
        return new ResourceLocation(References.MODID, "textures/gui/hud_elements_gold.png");
    }

    public void drawSpellIcon(MatrixStack matrix, int xPos, int yPos, Spell spell, float scale) {
        if(spell!=null) {
            matrix.pushPose();
            matrix.scale(scale,scale,scale);
            this.minecraft.getTextureManager().bind(spell.getSpellIcon());
            this.blit(matrix, Math.round(xPos/scale) - 16, Math.round(yPos/scale) - 16, 0, 0, 32, 32, 32, 32);
            matrix.popPose();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void drawItemStack(ItemStack stack, int x, int y) {
        ItemRenderer itemRender = minecraft.getItemRenderer();
        if(stack!=null) {
            itemRender.blitOffset = 100.0F;
            itemRender.renderAndDecorateItem(stack, x, y);
            itemRender.blitOffset = 0.0F;
        }
    }

    public void drawSpellSlot(MatrixStack matrix, int xPos, int yPos, ItemStack wand, float scale) {
        this.minecraft.getTextureManager().bind(getTexture(wand));
        matrix.pushPose();
        matrix.scale(scale,scale,scale);
        this.blit(matrix,Math.round(xPos/scale) - 19, Math.round(yPos/scale) - 19, 38, 0, 38, 38);
        matrix.popPose();
    }
}
