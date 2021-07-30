package com.paleimitations.schoolsofmagic.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.containers.MortarContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MortarScreen extends ContainerScreen<MortarContainer> {
    private static final ResourceLocation MORTAR_LOCATION = new ResourceLocation(References.MODID, "textures/gui/mortar_gui.png");
    private int tick = 0;
    private boolean animate = false;
    private Button pestleButton;

    public MortarScreen(MortarContainer container, PlayerInventory inv, ITextComponent textComponent) {
        super(container, inv, textComponent);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.pestleButton = this.addButton(new Button(leftPos + 66, topPos + 10, 44, 27, StringTextComponent.EMPTY,
                (pressable) -> this.addCrush()){
            @Override
            public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partial) {
            }
        });
        this.updateButtons();
    }

    public void addCrush() {
        this.animate = true;
        this.pestleButton.visible = false;
        this.menu.addCrush();
    }

    public void render(MatrixStack matrix, int partial, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(matrix);
        super.render(matrix, partial, p_230430_3_, p_230430_4_);
        this.renderTooltip(matrix, partial, p_230430_3_);
    }

    private void updateButtons() {
        this.pestleButton.visible = this.menu.getMaxCrush()>0 && !animate && this.menu.getCrush()<this.menu.getMaxCrush();
    }

    @Override
    protected void renderBg(MatrixStack matrix, float partial, int p_230450_3_, int p_230450_4_) {
        this.updateButtons();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(MORTAR_LOCATION);
        this.blit(matrix, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);

        if(animate)
            ++tick;
        double wid = 27;
        double heig = 12;
        double x = Math.cos(((float)tick + (this.animate? partial : 0))*Math.PI/20f) * wid / 2d;
        double y = (Math.cos(((float)tick + (this.animate? partial : 0))*Math.PI/5f) + 1d) * heig / 2d;
        if(tick%20==0) {
            this.animate = false;
        }
        matrix.pushPose();
        matrix.translate(x,y,0);
        this.blit(matrix, leftPos + 84, topPos + 15, 176, this.pestleButton.visible? 42:0, 9, 42);
        matrix.popPose();
        this.blit(matrix, leftPos + 66, topPos + 37, 199,0, 44, 41);

        float crush = this.menu.getCrush();
        float max = this.menu.getMaxCrush()-1;
        int height = Math.round(crush / max * 43f);
        if(animate) {
            height = Math.round((crush - 1f + (((float)tick+partial)%20) / 20f) / max * 43f);
        }
        this.blit(matrix, leftPos + 122, topPos + 30, 185,0, 13, height);
    }
}
