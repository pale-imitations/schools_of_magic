package com.paleimitations.schoolsofmagic.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data.IQuestData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.quest_data.QuestDataProvider;
import com.paleimitations.schoolsofmagic.common.network.PacketHandler;
import com.paleimitations.schoolsofmagic.common.network.QuestNotePacket;
import com.paleimitations.schoolsofmagic.common.quests.Quest;
import com.paleimitations.schoolsofmagic.common.quests.Task;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;

public class QuestPageScreen extends Screen {

    private final int imageHeight = 166;
    private final int imageWidth = 156;
    private int leftPos;
    private int topPos;
    public static final ResourceLocation QUEST = new ResourceLocation(References.MODID, "textures/gui/quest_paper.png");
    private PlayerEntity player;
    private ItemStack stack;
    private Hand hand;
    private Quest questDisplay;
    private Button startQuestButton, clearQuestButton, claimQuestButton;
    private Button[] taskButtons = new Button[14];

    public QuestPageScreen(ItemStack stack, Quest q, Hand hand) {
        super(NarratorChatListener.NO_TITLE);
        this.player = Minecraft.getInstance().player;
        this.stack = stack;
        this.hand = hand;
        this.questDisplay = q;
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        this.startQuestButton = this.addButton(new Button(leftPos + 43, topPos + 135, 83, 18, StringTextComponent.EMPTY,
                (pressable) -> this.startQuest()) {
            @Override
            public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partial) {
                if(visible) {
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    minecraft.getTextureManager().bind(QUEST);
                    this.blit(matrix, this.x, this.y, this.isHovered()? 83 : 0, 184, 83, 18);

                    String line = I18n.get("gui.start_quest.name");
                    int textWidth = font.width(line);
                    int textHeight = font.lineHeight;

                    float scalerWidth = 77 / Float.valueOf(textWidth);
                    float scalerHeight = 12 / Float.valueOf(textHeight);
                    float scaler = Math.min(scalerWidth, scalerHeight);

                    int drawX = Math.round(this.x + 42 - (textWidth*scaler / 2));
                    int drawY = Math.round(this.y + 10 - (textHeight*scaler / 2));

                    matrix.pushPose();
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    matrix.scale(scaler, scaler, scaler);
                    font.draw(matrix, line, Math.round(drawX/scaler), Math.round(drawY/scaler), 0);
                    matrix.popPose();
                }
            }
        });
        this.clearQuestButton = this.addButton(new Button(leftPos + 43, topPos + 135, 83, 18, StringTextComponent.EMPTY,
                (pressable) -> this.clearQuest()) {
            @Override
            public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partial) {
                if(visible) {
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    minecraft.getTextureManager().bind(QUEST);
                    this.blit(matrix, this.x, this.y, this.isHovered()? 83 : 0, 184, 83, 18);

                    String line = I18n.get("gui.clear_quest.name");
                    int textWidth = font.width(line);
                    int textHeight = font.lineHeight;

                    float scalerWidth = 77 / Float.valueOf(textWidth);
                    float scalerHeight = 12 / Float.valueOf(textHeight);
                    float scaler = Math.min(scalerWidth, scalerHeight);

                    int drawX = Math.round(this.x + 42 - (textWidth*scaler / 2));
                    int drawY = Math.round(this.y + 10 - (textHeight*scaler / 2));

                    matrix.pushPose();
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    matrix.scale(scaler, scaler, scaler);
                    font.draw(matrix, line, Math.round(drawX/scaler), Math.round(drawY/scaler), 0);
                    matrix.popPose();
                }
            }
        });
        this.claimQuestButton = this.addButton(new Button(leftPos + 43, topPos + 135, 83, 18, StringTextComponent.EMPTY,
                (pressable) -> this.claimQuest()) {
            @Override
            public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partial) {
                if(visible) {
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    minecraft.getTextureManager().bind(QUEST);
                    this.blit(matrix, this.x, this.y, this.isHovered()? 83 : 0, 184, 83, 18);

                    String line = I18n.get("gui.claim_quest.name");
                    int textWidth = font.width(line);
                    int textHeight = font.lineHeight;

                    float scalerWidth = 77 / Float.valueOf(textWidth);
                    float scalerHeight = 12 / Float.valueOf(textHeight);
                    float scaler = Math.min(scalerWidth, scalerHeight);

                    int drawX = Math.round(this.x + 42 - (textWidth*scaler / 2));
                    int drawY = Math.round(this.y + 10 - (textHeight*scaler / 2));

                    matrix.pushPose();
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    matrix.scale(scaler, scaler, scaler);
                    font.draw(matrix, line, Math.round(drawX/scaler), Math.round(drawY/scaler), 0);
                    matrix.popPose();
                }
            }
        });
        for(int i = 0; i < 14; i ++) {
            int j = i;
            this.taskButtons[i] = this.addButton(new Button(leftPos +(i/7==0?-25:152), topPos + 5 + 22*(i%7), 29, 22, StringTextComponent.EMPTY,
                    (pressable) -> this.taskClick(j)) {
                @Override
                public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partial) {
                }
            });
        }

        this.updateButtonVisibility();
    }

    private void updateButtonVisibility() {
        IQuestData data = QuestDataProvider.getData(player);
        boolean flag = questDisplay.getQuestGiver()!=null && data.hasQuest(questDisplay.getQuestGiver());
        clearQuestButton.visible = questDisplay!=null && flag && questDisplay.failed;
        claimQuestButton.visible = questDisplay!=null && flag && questDisplay.canClaim();
        startQuestButton.visible = questDisplay!=null && !flag;
        for(int i = 0; i < 14; ++ i) {
            taskButtons[i].visible = questDisplay!=null && questDisplay.tasks!=null && flag && i < questDisplay.tasks.size() && questDisplay.tasks.get(i)!=null &&
                    questDisplay.tasks.get(i).canStart(questDisplay);
        }
    }

    private void clearQuest() {
        System.out.println("Sending Quest Note Clear");
        PacketHandler.INSTANCE.sendToServer(new QuestNotePacket(player.getId(), hand, 0));
        questDisplay.dead = true;
        minecraft.setScreen(null);
    }

    private void claimQuest() {
        System.out.println("Sending Quest Note Claim");
        PacketHandler.INSTANCE.sendToServer(new QuestNotePacket(player.getId(), hand, 1));
        //questDisplay.claim(player);
        minecraft.setScreen(null);
    }

    private void startQuest() {
        System.out.println("Sending Quest Note Start");
        PacketHandler.INSTANCE.sendToServer(new QuestNotePacket(player.getId(), hand, 2));

        CompoundNBT nbt = stack.getOrCreateTag();
        if(nbt.contains("quest_giver")) {
            IQuestData data = QuestDataProvider.getData(player);
            questDisplay.setQuestGiver(nbt.getUUID("quest_giver"));
            data.addQuest(questDisplay);
        }
        this.updateButtonVisibility();
    }

    private void taskClick(int id) {
        System.out.println("Sending Quest Note Task Start");
        PacketHandler.INSTANCE.sendToServer(new QuestNotePacket(player.getId(), hand, 3+id));
        questDisplay.tasks.get(id).setStarted(true);
        this.updateButtonVisibility();
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTick) {
        if(questDisplay!=null &&  stack!=null) {
            int offsetWidth = (width - imageWidth) / 2;
            int offsetHeight = (height - imageHeight) / 2;

            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.minecraft.getTextureManager().bind(QUEST);
            this.blit(matrix, offsetWidth, offsetHeight, 0, 0, imageWidth, imageHeight);

            if(questDisplay.failed) {
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.minecraft.getTextureManager().bind(QUEST);
                this.blit(matrix,offsetWidth+13, offsetHeight+9, 180, 0, 22, 22);
            }
            else if(questDisplay.completed) {
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.minecraft.getTextureManager().bind(QUEST);
                this.blit(matrix,offsetWidth+13, offsetHeight+9, 158, 0, 22, 22);
            }

            String line = I18n.get("quest."+questDisplay.getResourceLocation().toString()+".name");
            int textWidth = font.width(line);
            int textHeight = font.lineHeight;

            float scalerWidth = 126 / Float.valueOf(textWidth);
            float scalerHeight = 17 / Float.valueOf(textHeight);
            float scaler = Math.min(scalerWidth, scalerHeight);

            int drawX = Math.round(78 + offsetWidth - (textWidth*scaler / 2));
            int drawY = Math.round(45 + offsetHeight - (textHeight*scaler / 2));

            matrix.pushPose();
            matrix.scale(scaler, scaler, scaler);
            font.draw(matrix, line, Math.round(drawX/scaler), Math.round(drawY/scaler), 0);
            matrix.popPose();

            font.drawWordWrap(ITextProperties.of(I18n.get("quest."+questDisplay.getResourceLocation().toString()+".desc")), 15 + offsetWidth, 56 + offsetHeight, 126, 0);

            if(!questDisplay.rewards.isEmpty()) {
                ItemStack reward  = questDisplay.rewards.get(player.tickCount/40%questDisplay.rewards.size());
                minecraft.getItemRenderer().renderGuiItem(reward, 16+offsetWidth, 136+offsetHeight);
            }
            if(questDisplay.icon!=null){
                minecraft.getItemRenderer().renderGuiItem(questDisplay.icon, 72+offsetWidth, 12+offsetHeight);
            }

            if(questDisplay.tasks.size()>1 && !startQuestButton.visible) {
                for (int i = 0; i < questDisplay.tasks.size(); ++i)
                    drawTabs(matrix, i, questDisplay, questDisplay.tasks.get(i));
            }

            super.render(matrix,mouseX,mouseY,partialTick);
        }
    }

    public void drawTabs(MatrixStack matrix, int taskNumber, Quest quest, Task task) {
        int offsetWidth = (width - imageWidth) / 2;
        int offsetHeight = (height - imageHeight) / 2;
        if(task.isOngoing()) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.minecraft.getTextureManager().bind(QUEST);
            this.blit(matrix, offsetWidth + (taskNumber/7==0? -70:152), offsetHeight + 5 + (taskNumber%7)*22, 171, (taskNumber/7==0? 78:29), 74, 22);

            if(task.icon!=null){
                this.minecraft.getItemRenderer().renderGuiItem(task.icon, offsetWidth+(taskNumber/7==0? -63:203), offsetHeight + 9 + (taskNumber%7)*22);
            }

            if(task.getName()!=null) {
                String line = I18n.get("task." + task.getName() + ".name");
                float scaler = 0.65f;

                int drawX = Math.round((taskNumber/7==0? -45:155) + offsetWidth);
                int drawY = Math.round(8 + (taskNumber%7)*22 + offsetHeight);

                matrix.pushPose();
                matrix.scale(scaler, scaler, scaler);
                font.drawWordWrap(ITextProperties.of(line), Math.round(drawX / scaler), Math.round(drawY / scaler), Math.round(44 / scaler), 0);
                matrix.popPose();
            }
            if(task.isTimed() || task.progress!=null) {
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.minecraft.getTextureManager().bind(QUEST);
                this.blit(matrix, offsetWidth + (taskNumber/7==0? -107:226), offsetHeight + 10 + (taskNumber%7)*22, 171, (taskNumber/7==0? 142:130)+24, 37, 12);

                int sec = ((task.getCountdown()/20)%60);
                String line = task.isTimed()? task.getCountdown()/1200+":"+(sec<10? "0" + sec : sec) : task.progress.getA()+"/"+task.progress.getB();
                int textWidth = font.width(line);
                int textHeight = font.lineHeight;

                float scalerWidth = 27 / Float.valueOf(textWidth);
                float scalerHeight = 10 / Float.valueOf(textHeight);
                float scaler = Math.min(scalerWidth, scalerHeight);

                int drawX = Math.round((taskNumber/7==0? -99:229) + offsetWidth);
                int drawY = Math.round((12 + (taskNumber%7)*22) + offsetHeight);

                matrix.pushPose();
                matrix.scale(scaler, scaler, scaler);
                font.draw(matrix, line, Math.round(drawX/scaler), Math.round(drawY/scaler), 0);
                matrix.popPose();
            }

        }
        else {
            int a = 48;
            if(task.failed) {
                RenderSystem.color4f(162f/256f,38f/256f,38f/256f, 1.0F);
            }
            else if(task.completed) {
                RenderSystem.color4f(101f/256f,170f/256f,100f/256f, 1.0F);
                a=0;
            }
            else if(task.canStart(quest)){
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                a=24;
            }
            else {
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                a=72;
            }
            this.minecraft.getTextureManager().bind(QUEST);
            this.blit(matrix,offsetWidth + (taskNumber / 7 == 0 ? -25 : 152), offsetHeight + 5 + (taskNumber % 7) * 22, 171, (taskNumber / 7 == 0 ? 100 : 51), 29, 22);

            if(task.icon!=null){
                this.minecraft.getItemRenderer().renderGuiItem(task.icon, offsetWidth+(taskNumber/7==0? -18:158), offsetHeight + 9 + (taskNumber%7)*22);
            }

            if(task.isTimed() || task.progress!=null) {
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.minecraft.getTextureManager().bind(QUEST);
                this.blit(matrix,offsetWidth + (taskNumber/7==0? -62:181), offsetHeight + 10 + (taskNumber%7)*22, 171, (taskNumber/7==0? 142:130)+a, 37, 12);

                String line = task.isTimed()? task.getCountdown()/1200+":"+((task.getCountdown()/20)%60) : task.progress.getA()+"/"+task.progress.getB();
                int textWidth = font.width(line);
                int textHeight = font.lineHeight;

                float scalerWidth = 27 / Float.valueOf(textWidth);
                float scalerHeight = 10 / Float.valueOf(textHeight);
                float scaler = Math.min(scalerWidth, scalerHeight);

                int drawX = Math.round((taskNumber/7==0? -54:184) + offsetWidth);
                int drawY = Math.round((12 + (taskNumber%7)*22) + offsetHeight);

                matrix.pushPose();
                matrix.scale(scaler, scaler, scaler);
                font.draw(matrix, line, Math.round(drawX/scaler), Math.round(drawY/scaler), 0);
                matrix.popPose();
            }
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
