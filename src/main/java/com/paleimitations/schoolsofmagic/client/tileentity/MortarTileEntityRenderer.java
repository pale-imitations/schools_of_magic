package com.paleimitations.schoolsofmagic.client.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.paleimitations.schoolsofmagic.common.tileentities.MortarTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.vector.Vector3f;

public class MortarTileEntityRenderer extends TileEntityRenderer<MortarTileEntity> {
    private final ItemStack pestle;
    private boolean animated = false;
    private int tick = 0;
    private int crush = 0;

    public MortarTileEntityRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
        pestle = new ItemStack(Items.STICK);
    }

    @Override
    public void render(MortarTileEntity pte, float partial, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
        ItemStack item1 = pte.getItem(0);
        ItemStack item2 = pte.getItem(1);
        double i = 0.125D;
        if (item1 != ItemStack.EMPTY) {
            matrix.pushPose();
            matrix.translate(0.5D, i, 0.5D);
            matrix.mulPose(Vector3f.XP.rotationDegrees(90));
            matrix.scale(0.3F, 0.3F, 0.3F);
            Minecraft.getInstance().getItemRenderer().renderStatic(item1, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrix, buffer);
            matrix.popPose();
            i += 0.03125D;
        }
        if (item2 != ItemStack.EMPTY) {
            matrix.pushPose();
            matrix.translate(0.5D, i, 0.5D);
            matrix.mulPose(Vector3f.XP.rotationDegrees(90));
            matrix.mulPose(Vector3f.ZP.rotationDegrees(45));
            matrix.scale(0.3F, 0.3F, 0.3F);
            Minecraft.getInstance().getItemRenderer().renderStatic(item2, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrix, buffer);
            matrix.popPose();
        }
        if(this.crush!=pte.getCrush()) {
            this.crush=pte.getCrush();
            if(!this.animated)
                this.animated = true;
        }
        if(this.animated) {
            tick++;
        }
        if(tick%20==0)
            this.animated=false;

        matrix.pushPose();
        matrix.translate(0.5D, 0.45D, 0.5D);
        matrix.scale(0.65F, 0.65F, 0.65F);
        Minecraft.getInstance().getItemRenderer().renderStatic(pestle, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrix, buffer);
        matrix.popPose();
    }
}
