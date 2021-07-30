package com.paleimitations.schoolsofmagic.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.containers.MortarContainer;
import com.paleimitations.schoolsofmagic.common.containers.TeapotContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class TeapotScreen extends ContainerScreen<TeapotContainer> {
    private static final ResourceLocation TEAPOT_LOCATION = new ResourceLocation(References.MODID, "textures/gui/teapot_gui.png");

    public TeapotScreen(TeapotContainer container, PlayerInventory inv, ITextComponent textComponent) {
        super(container, inv, textComponent);
    }

    @Override
    protected void init() {
        super.init();
        this.updateButtons();
        this.inventoryLabelX = 119;
    }

    public void render(MatrixStack matrix, int partial, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(matrix);
        super.render(matrix, partial, p_230430_3_, p_230430_4_);
        this.renderTooltip(matrix, partial, p_230430_3_);
    }

    private void updateButtons() {
    }

    @Override
    protected void renderBg(MatrixStack matrix, float partial, int p_230450_3_, int p_230450_4_) {
        this.updateButtons();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(TEAPOT_LOCATION);
        this.blit(matrix, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);

        if(this.menu.hasHeat())
            this.blit(matrix, leftPos+33, topPos+59, 190, 0, 16, 21);

        if(this.menu.liquidLevel()>0) {
            int height = Math.round(41f * (float)this.menu.liquidLevel()/3f);
            Color color = new Color(menu.liquidColor());
            RenderSystem.color4f(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, 1.0F);
            this.blit(matrix, leftPos+38, topPos+15+41-height, 176, 41-height, 7, height);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.5F);
            this.blit(matrix, leftPos+38, topPos+15+41-height, 220, 41-height, 7, height);
            this.blit(matrix, leftPos+38, topPos+15+41-height, 220, 0, 7, 1);
        }

        if(this.menu.tickHeight() > 0) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.blit(matrix, leftPos+115, topPos+5+16-this.menu.tickHeight(), 183, 16-this.menu.tickHeight(), 7, this.menu.tickHeight());
        }
    }
}
