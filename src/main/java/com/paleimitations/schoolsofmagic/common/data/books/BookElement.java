package com.paleimitations.schoolsofmagic.common.data.books;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BookElement {

    public int page, subpage;
    public float x, y;

    public BookElement(float x, float y, int page, int subpage) {
        this.page = page;
        this.subpage = subpage;
        this.x = x;
        this.y = y;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldDraw(float mouseX, float mouseY, int x, int y, boolean isGUI, int subpage, int page) {
        return subpage == this.subpage && page == this.page;
    }

    @OnlyIn(Dist.CLIENT)
    public void drawElement(float mouseX, float mouseY, int x, int y, float zLevel, boolean isGUI, int subpage, int page) {
    }

    @OnlyIn(Dist.CLIENT)
    public void drawElement(float mouseX, float mouseY, int x, int y, float zLevel, boolean isGUI, int subpage, int page, IRenderTypeBuffer buffer) {
        this.drawElement(mouseX, mouseY, x, y, zLevel, isGUI, subpage, page);
    }

    @OnlyIn(Dist.CLIENT)
    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, float zLevel) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.vertex(x + 0, y + height, zLevel).uv((float)(textureX + 0) * 0.00390625F, (float)(textureY + height) * 0.00390625F).endVertex();
        bufferbuilder.vertex(x + width, y + height, zLevel).uv((float)(textureX + width) * 0.00390625F, (float)(textureY + height) * 0.00390625F).endVertex();
        bufferbuilder.vertex(x + width, y + 0, zLevel).uv((float)(textureX + width) * 0.00390625F, (float)(textureY + 0) * 0.00390625F).endVertex();
        bufferbuilder.vertex(x + 0, y + 0, zLevel).uv((float)(textureX + 0) * 0.00390625F, (float)(textureY + 0) * 0.00390625F).endVertex();
        tessellator.end();
    }
}
