package com.paleimitations.schoolsofmagic.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.data.books.BookPage;
import com.paleimitations.schoolsofmagic.common.network.LetterPacket;
import com.paleimitations.schoolsofmagic.common.network.PacketHandler;
import com.paleimitations.schoolsofmagic.common.registries.BookPageRegistry;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LetterScreen extends Screen {

    private ItemStack stack;
    private final int imageHeight = 166;
    private final int imageWidth = 156;
    public static final ResourceLocation LETTER = new ResourceLocation(References.MODID, "textures/gui/letter_ccw.png");
    public static final ResourceLocation CLOSED_ENVELOPE = new ResourceLocation(References.MODID, "textures/gui/envelope_closed.png");
    public static final ResourceLocation OPEN_ENVELOPE = new ResourceLocation(References.MODID, "textures/gui/envelope_open.png");
    private int leftPos;
    private int topPos;
    private PlayerEntity player;
    private final BookPage letter;
    private Button sealButton, questButton, envelopeButton;
    private Hand hand;

    private int yOffset = 0;
    private int phase;
    private int tick;

    public LetterScreen(ItemStack stack, Hand hand) {
        super(NarratorChatListener.NO_TITLE);
        this.stack = stack;
        this.player = Minecraft.getInstance().player;
        this.letter = BookPageRegistry.getBookPage("ccw_letter_1");
        this.hand = hand;
    }

    private void updateButtonVisibility() {
        sealButton.visible = (stack!=null && stack.getItem() == ItemRegistry.LETTER_CCW.get() && this.phase == 1 && (!stack.hasTag() || !stack.getTag().getBoolean("opened")));
        questButton.visible = (stack!=null && stack.getItem() == ItemRegistry.LETTER_CCW.get() && stack.getOrCreateTag().getBoolean("opened") &&
                stack.getOrCreateTag().getBoolean("quest") && tick > 40);
        envelopeButton.visible = (stack!=null && stack.getItem() == ItemRegistry.LETTER_CCW.get() && stack.getOrCreateTag().getBoolean("opened") &&
                tick > 40);
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        sealButton = this.addButton(new Button((width - imageWidth) / 2 + 64, (height - imageHeight) / 2 + 87, 24, 24, StringTextComponent.EMPTY,
                (pressable) -> this.openLetter()){
            @Override
            public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partial) {
                if(visible && isHovered()) {
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    minecraft.getTextureManager().bind(CLOSED_ENVELOPE);
                    this.blit(matrix, this.x, this.y, 173, 87, 24, 24);
                }
            }
        });
        questButton = this.addButton(new Button((width - imageWidth) / 2 + 152, (height - imageHeight) / 2 + 32,30,62, StringTextComponent.EMPTY,
                (pressable) -> this.getQuestNote()){
            @Override
            public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partial) {
                if(visible && isHovered()) {
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    minecraft.getTextureManager().bind(LETTER);
                    this.blit(matrix, this.x, this.y, 209, 0, 30, 62);
                }
            }
        });
        envelopeButton = this.addButton(new Button((width - imageWidth) / 2 - 33, (height - imageHeight) / 2 + 28,37,122, StringTextComponent.EMPTY,
                (pressable) -> this.closeLetter()){
            @Override
            public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partial) {
                if(visible && isHovered()) {
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    minecraft.getTextureManager().bind(LETTER);
                    this.blit(matrix, this.x, this.y, 216, 63, 37, 122);
                }
            }
        });

        this.updateButtonVisibility();
    }

    private void closeLetter() {
        PacketHandler.sendToServer(new LetterPacket(player.getId(), hand, 1));
        this.phase = 0;
    }

    private void getQuestNote() {
        PacketHandler.sendToServer(new LetterPacket(player.getId(), hand, 2));
    }

    private void openLetter() {
        this.phase = 2;
        this.tick = 0;
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTick) {
        this.stack = player.getItemInHand(hand);
        this.updateButtonVisibility();

        int offsetWidth = (width - imageWidth) / 2;
        int offsetHeight = (height - imageHeight) / 2;
        ++tick;

        if(stack.getOrCreateTag().getBoolean("opened")) {
            if(this.tick > 40) {
                int xOffset = this.tick < 60? (int) (-30*(-Math.cos(((double)tick-40+partialTick)*Math.PI/20d)-1d)) : 0;
                if(stack.getOrCreateTag().getBoolean("quest")) {
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    this.minecraft.getTextureManager().bind(LETTER);
                    this.blit(matrix,offsetWidth - xOffset + 152, offsetHeight + 32, 179, 0, 30, 62);
                }
                int xOffset2 = this.tick < 60? (int) (37*(-Math.cos(((double)tick-40+partialTick)*Math.PI/20d)-1d)) : 0;
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.minecraft.getTextureManager().bind(LETTER);
                this.blit(matrix,offsetWidth - xOffset2 - 33, offsetHeight + 28, 179, 63, 37, 122);
            }

            if(this.tick<20)
                this.yOffset = (int) (height*(-Math.cos(((double)tick+partialTick)*Math.PI/20d)-1d));
            else
                this.yOffset = 0;

            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.minecraft.getTextureManager().bind(LETTER);
            this.blit(matrix,offsetWidth, offsetHeight - yOffset,0,0, imageWidth, imageHeight);
            if(letter!=null)
                letter.drawPage(matrix, mouseX - offsetWidth, mouseY - offsetHeight - yOffset, offsetWidth, offsetHeight - yOffset, 20f, true, 0, 15728880);
        }
        else {
            if(this.phase == 0) {
                if(this.tick<40)
                    this.yOffset = (int) (height*(-Math.cos(((double)tick+partialTick)*Math.PI/40d)-1d));
                else {
                    this.phase = 1;
                    this.yOffset = 0;
                }
            }
            if(phase == 2) {
                if(this.tick>20&&this.tick<60)
                    this.yOffset = (int) (height*(Math.cos(((double)tick-20d+partialTick)*Math.PI/40d)-1d));
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.minecraft.getTextureManager().bind(OPEN_ENVELOPE);
                this.blit(matrix, offsetWidth, offsetHeight - yOffset, 0, 0, imageWidth, imageHeight);
                if(tick == 60) {
                    PacketHandler.sendToServer(new LetterPacket(player.getId(), hand, 0));
                    tick = 0;
                }
            }
            else {
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.minecraft.getTextureManager().bind(CLOSED_ENVELOPE);
                this.blit(matrix, offsetWidth, offsetHeight - yOffset, 0, 0, imageWidth, imageHeight);
            }
        }

        super.render(matrix, mouseX, mouseY, partialTick);

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
