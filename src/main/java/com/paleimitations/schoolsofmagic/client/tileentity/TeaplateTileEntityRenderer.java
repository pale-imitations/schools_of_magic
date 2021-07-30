package com.paleimitations.schoolsofmagic.client.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.TeaBrewResult;
import com.paleimitations.schoolsofmagic.common.blocks.PodiumBlock;
import com.paleimitations.schoolsofmagic.common.blocks.TeaplateBlock;
import com.paleimitations.schoolsofmagic.common.data.TeaUtils;
import com.paleimitations.schoolsofmagic.common.items.TeacupItem;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import com.paleimitations.schoolsofmagic.common.tileentities.PodiumTileEntity;
import com.paleimitations.schoolsofmagic.common.tileentities.TeaplateTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.obj.OBJLoader;

import java.awt.*;

public class TeaplateTileEntityRenderer extends TileEntityRenderer<TeaplateTileEntity> {

    public TeaplateTileEntityRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(TeaplateTileEntity tte, float partial, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
        Direction direction = tte.getBlockState().getValue(TeaplateBlock.HORIZONTAL_FACING);
        int stackHeight = tte.getBlockState().getValue(TeaplateBlock.COUNT);
        ItemStack item = tte.getItem();
        if(item.getItem() instanceof TeacupItem) {
            TeacupModel model = new TeacupModel();
            RenderType renderType = ((TeacupItem)item.getItem()).filled? RenderType.itemEntityTranslucentCull(new ResourceLocation(item.getItem().getRegistryName().getNamespace(),
                    "textures/block/" + item.getItem().getRegistryName().getPath().substring(7) + ".png")) : RenderType.itemEntityTranslucentCull(new ResourceLocation(item.getItem().getRegistryName().getNamespace(),
                    "textures/block/" + item.getItem().getRegistryName().getPath() + ".png"));
            IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);
            matrix.pushPose();
            matrix.translate(0.5D, 25D/16D + (stackHeight-1) * 1.25D/16D, 0.5D);
            float f = direction.toYRot();
            matrix.mulPose(Vector3f.YN.rotationDegrees(f));
            matrix.scale(1.0F, -1.0F, -1.0F);
            model.renderToBuffer(matrix, vertexBuilder, combinedLightIn, combinedOverlayIn, 1f,1f,1f,1f);
            if(((TeacupItem)item.getItem()).filled) {
                Color color = new Color(TeaUtils.getColor(item));
                TeaBrewResult brew = TeaUtils.getTea(item);
                model.renderToBuffer(matrix, buffer.getBuffer(RenderType.entityTranslucentCull(new ResourceLocation(item.getItem().getRegistryName().getNamespace(),
                        "textures/block/teacup_overlay.png"))), combinedLightIn, combinedOverlayIn,
                        (float)color.getRed()/255f, (float)color.getGreen()/255f, (float)color.getBlue()/255f, brew.isMilk? 1f : 0.85f);

            }
            matrix.popPose();
        }
        else if(item.getItem() == ItemRegistry.POPPY_SEED_MUFFIN.get()) {
            MuffinModel model = new MuffinModel();
            RenderType renderType = RenderType.itemEntityTranslucentCull(new ResourceLocation(References.MODID,"textures/block/poppy_seed_muffin.png"));
            IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);
            matrix.pushPose();
            matrix.translate(0.5D, 25D/16D + (stackHeight-1) * 1.25D/16D, 0.5D);
            float f = direction.toYRot();
            matrix.mulPose(Vector3f.YN.rotationDegrees(f));
            matrix.scale(1.0F, -1.0F, -1.0F);
            model.renderToBuffer(matrix, vertexBuilder, combinedLightIn, combinedOverlayIn, 1f,1f,1f,1f);
            matrix.popPose();
        }
        else if(item.getItem() == Items.PUMPKIN_PIE){
            PieModel model = new PieModel();
            RenderType renderType = RenderType.itemEntityTranslucentCull(new ResourceLocation(References.MODID,"textures/block/pumpkin_pie.png"));
            IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);
            matrix.pushPose();
            matrix.translate(0.5D, 25D/16D + (stackHeight-1) * 1.25D/16D, 0.5D);
            float f = direction.toYRot();
            matrix.mulPose(Vector3f.YN.rotationDegrees(f));
            matrix.scale(1.0F, -1.0F, -1.0F);
            model.renderToBuffer(matrix, vertexBuilder, combinedLightIn, combinedOverlayIn, 1f,1f,1f,1f);
            matrix.popPose();
        }
        else if(item.getItem() == Items.BREAD){
            BreadModel model = new BreadModel();
            RenderType renderType = RenderType.itemEntityTranslucentCull(new ResourceLocation(References.MODID,"textures/block/bread.png"));
            IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);
            matrix.pushPose();
            matrix.translate(0.5D, 25D/16D + (stackHeight-1) * 1.25D/16D, 0.5D);
            float f = direction.toYRot();
            matrix.mulPose(Vector3f.YN.rotationDegrees(f));
            matrix.scale(1.0F, -1.0F, -1.0F);
            model.renderToBuffer(matrix, vertexBuilder, combinedLightIn, combinedOverlayIn, 1f,1f,1f,1f);
            matrix.popPose();
        }
        else if(item.getItem() == Items.BAKED_POTATO){
            BakedPotatoModel model = new BakedPotatoModel();
            RenderType renderType = RenderType.itemEntityTranslucentCull(new ResourceLocation(References.MODID,"textures/block/baked_potato.png"));
            IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);
            matrix.pushPose();
            matrix.translate(0.5D, 25D/16D + (stackHeight-1) * 1.25D/16D, 0.5D);
            float f = direction.toYRot();
            matrix.mulPose(Vector3f.YN.rotationDegrees(f));
            matrix.scale(1.0F, -1.0F, -1.0F);
            model.renderToBuffer(matrix, vertexBuilder, combinedLightIn, combinedOverlayIn, 1f,1f,1f,1f);
            matrix.popPose();
        }
        else if(item.getItem() == Items.APPLE || item.getItem() == Items.GOLDEN_APPLE || item.getItem() == Items.ENCHANTED_GOLDEN_APPLE){
            AppleModel model = new AppleModel();
            RenderType renderType = RenderType.itemEntityTranslucentCull(new ResourceLocation(References.MODID,"textures/block/"+item.getItem().getRegistryName().getPath()+".png"));
            IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);
            matrix.pushPose();
            matrix.translate(0.5D, 25D/16D + (stackHeight-1) * 1.25D/16D, 0.5D);
            float f = direction.toYRot();
            matrix.mulPose(Vector3f.YN.rotationDegrees(f));
            matrix.scale(1.0F, -1.0F, -1.0F);
            model.renderToBuffer(matrix, vertexBuilder, combinedLightIn, combinedOverlayIn, 1f,1f,1f,1f);
            matrix.popPose();
        }
        else if(item.getItem() == Items.COOKED_CHICKEN || item.getItem() == Items.CHICKEN){
            ChickenModel model = new ChickenModel();
            RenderType renderType = RenderType.itemEntityTranslucentCull(new ResourceLocation(References.MODID,"textures/block/"+item.getItem().getRegistryName().getPath()+".png"));
            IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);
            matrix.pushPose();
            matrix.translate(0.5D, 25D/16D + (stackHeight-1) * 1.25D/16D, 0.5D);
            float f = direction.toYRot();
            matrix.mulPose(Vector3f.YN.rotationDegrees(f));
            matrix.scale(1.0F, -1.0F, -1.0F);
            model.renderToBuffer(matrix, vertexBuilder, combinedLightIn, combinedOverlayIn, 1f,1f,1f,1f);
            matrix.popPose();
        }
        else if(item.getItem() == Items.BOWL){
            BowlModel model = new BowlModel();
            String s = tte.getBlockState().getBlock().getRegistryName().getPath();
            String s1 = s.substring(0, s.length()-8) + "bowl";
            RenderType renderType = RenderType.itemEntityTranslucentCull(new ResourceLocation(References.MODID,"textures/block/"+s1+".png"));
            IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);
            matrix.pushPose();
            matrix.translate(0.5D, 25D/16D + (stackHeight-1) * 1.25D/16D, 0.5D);
            float f = direction.toYRot();
            matrix.mulPose(Vector3f.YN.rotationDegrees(f));
            matrix.scale(1.0F, -1.0F, -1.0F);
            model.renderToBuffer(matrix, vertexBuilder, combinedLightIn, combinedOverlayIn, 1f,1f,1f,1f);
            matrix.popPose();
        }
        else if(item.getItem() == Items.BEETROOT_SOUP){
            BowlModel model = new BowlModel();
            String s = tte.getBlockState().getBlock().getRegistryName().getPath();
            String s1 = s.substring(0, s.length()-8) + "bowl";
            RenderType renderType = RenderType.itemEntityTranslucentCull(new ResourceLocation(References.MODID,"textures/block/"+s1+".png"));
            RenderType renderType1 = RenderType.itemEntityTranslucentCull(new ResourceLocation(References.MODID,"textures/block/beetroot_soup.png"));
            IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);
            matrix.pushPose();
            matrix.translate(0.5D, 25D/16D + (stackHeight-1) * 1.25D/16D, 0.5D);
            float f = direction.toYRot();
            matrix.mulPose(Vector3f.YN.rotationDegrees(f));
            matrix.scale(1.0F, -1.0F, -1.0F);
            model.renderToBuffer(matrix, vertexBuilder, combinedLightIn, combinedOverlayIn, 1f,1f,1f,1f);
            model.renderToBuffer(matrix, buffer.getBuffer(renderType1), combinedLightIn, combinedOverlayIn, 1f,1f,1f,1f);
            matrix.popPose();
        }
        else if(item.getItem() == Items.MUSHROOM_STEW){
            BowlModel model = new BowlModel();
            String s = tte.getBlockState().getBlock().getRegistryName().getPath();
            String s1 = s.substring(0, s.length()-8) + "bowl";
            RenderType renderType = RenderType.itemEntityTranslucentCull(new ResourceLocation(References.MODID,"textures/block/"+s1+".png"));
            RenderType renderType1 = RenderType.itemEntityTranslucentCull(new ResourceLocation(References.MODID,"textures/block/mushroom_stew.png"));
            IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);
            matrix.pushPose();
            matrix.translate(0.5D, 25D/16D + (stackHeight-1) * 1.25D/16D, 0.5D);
            float f = direction.toYRot();
            matrix.mulPose(Vector3f.YN.rotationDegrees(f));
            matrix.scale(1.0F, -1.0F, -1.0F);
            model.renderToBuffer(matrix, vertexBuilder, combinedLightIn, combinedOverlayIn, 1f,1f,1f,1f);
            model.renderToBuffer(matrix, buffer.getBuffer(renderType1), combinedLightIn, combinedOverlayIn, 1f,1f,1f,1f);
            matrix.popPose();
        }
        else if(item.getItem() == Items.RABBIT_STEW){
            BowlModel model = new BowlModel();
            String s = tte.getBlockState().getBlock().getRegistryName().getPath();
            String s1 = s.substring(0, s.length()-8) + "bowl";
            RenderType renderType = RenderType.itemEntityTranslucentCull(new ResourceLocation(References.MODID,"textures/block/"+s1+".png"));
            RenderType renderType1 = RenderType.itemEntityTranslucentCull(new ResourceLocation(References.MODID,"textures/block/rabbit_stew.png"));
            IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);
            matrix.pushPose();
            matrix.translate(0.5D, 25D/16D + (stackHeight-1) * 1.25D/16D, 0.5D);
            float f = direction.toYRot();
            matrix.mulPose(Vector3f.YN.rotationDegrees(f));
            matrix.scale(1.0F, -1.0F, -1.0F);
            model.renderToBuffer(matrix, vertexBuilder, combinedLightIn, combinedOverlayIn, 1f,1f,1f,1f);
            model.renderToBuffer(matrix, buffer.getBuffer(renderType1), combinedLightIn, combinedOverlayIn, 1f,1f,1f,1f);
            matrix.popPose();
        }
        else if (item != ItemStack.EMPTY) {
            matrix.pushPose();
            matrix.translate(0.5D, 1D/16D + (stackHeight-1) * 1.25D/16D, 0.5D);
            float f = direction.toYRot();
            switch(Math.round(f)){
                case 0:
                    matrix.mulPose(Vector3f.XP.rotationDegrees(-90));
                    break;
                case 90:
                    matrix.mulPose(Vector3f.ZP.rotationDegrees(-90));
                    break;
                case -90: case 270:
                    matrix.mulPose(Vector3f.ZP.rotationDegrees(90));
                    break;
                case 180:
                    matrix.mulPose(Vector3f.XP.rotationDegrees(90));
                    break;
            }
            matrix.mulPose(Vector3f.YN.rotationDegrees(f+180f));
            matrix.scale(0.65F, 0.65F, 0.65F);
            Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrix, buffer);
            matrix.popPose();
        }
    }
}
