package com.paleimitations.schoolsofmagic.client.tileentity;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.blocks.PodiumBlock;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookData;
import com.paleimitations.schoolsofmagic.common.registries.ItemRegistry;
import com.paleimitations.schoolsofmagic.common.tileentities.PodiumTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

import java.util.Map;

public class PodiumTileEntityRenderer extends TileEntityRenderer<PodiumTileEntity> {

    public PodiumTileEntityRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(PodiumTileEntity pte, float partial, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
        Direction direction = pte.getBlockState().getValue(PodiumBlock.HORIZONTAL_FACING);
        ItemStack item = pte.getItem();
        if (item != ItemStack.EMPTY) {
            if(pte.hasBook()) {
                matrix.pushPose();
                float f = direction.toYRot();
                float xD = 0f;
                float zD = 0f;
                float yD;
                float angle = 0f;

                float progress = (float)pte.animationTick/(float)pte.bookState.getAnimationLength();
                switch(pte.bookState){
                    case CLOSED:
                        yD = 0.02f;
                        if(direction==Direction.NORTH) {
                            xD = 5f/16f;
                            zD = -7f/16f;
                        }else if(direction==Direction.SOUTH) {
                            xD = -5f/16f;
                            zD = 7f/16f;
                        }else if(direction==Direction.EAST) {
                            xD = 7f/16f;
                            zD = 5f/16f;
                        }else if(direction==Direction.WEST) {
                            xD = -7f/16f;
                            zD = -5f/16f;
                        }
                        break;
                    case OPEN_BOOK:
                        angle = -45.0F * progress;
                        yD = 0.02f - 0.02f * progress;
                        if(direction==Direction.NORTH) {
                            xD = 5f/16f - 5f/16f * progress;
                            zD = -7f/16f + 7f/16f * progress;
                        }else if(direction==Direction.SOUTH) {
                            xD = -5f/16f + 5f/16f * progress;
                            zD = 7f/16f - 7f/16f * progress;
                        }else if(direction==Direction.EAST) {
                            xD = 7f/16f - 7f/16f * progress;
                            zD = 5f/16f - 5f/16f * progress;
                        }else if(direction==Direction.WEST) {
                            xD = -7f/16f + 7f/16f * progress;
                            zD = -5f/16f + 5f/16f * progress;
                        }
                        break;
                    case CLOSE_BOOK:
                        angle = -45.0F + 45.0F * progress;
                        yD = 0.02f * progress;
                        if(direction==Direction.NORTH) {
                            xD = 5f/16f * progress;
                            zD = -7f/16f * progress;
                        }else if(direction==Direction.SOUTH) {
                            xD = -5f/16f * progress;
                            zD = 7f/16f * progress;
                        }else if(direction==Direction.EAST) {
                            xD = 7f/16f * progress;
                            zD = 5f/16f * progress;
                        }else if(direction==Direction.WEST) {
                            xD = -7f/16f * progress;
                            zD = -5f/16f * progress;
                        }
                        break;
                    default:
                        angle = -45.0F;
                        yD = (float)Math.sin(pte.getLevel().getGameTime() / 20f) * 0.05f + 0.05f;
                        break;
                }
                matrix.translate(0.5D + xD, 1.2f + yD, 0.5D + zD);
                matrix.mulPose(Vector3f.YN.rotationDegrees(f));
                matrix.mulPose(Vector3f.XP.rotationDegrees(angle));
                matrix.scale(1.0F, -1.0F, -1.0F);
                GrimoireModel model = new GrimoireModel();
                CompoundNBT nbt = pte.getItem().getOrCreateTag();
                {
                    RenderType renderType = RenderType.itemEntityTranslucentCull(new ResourceLocation(References.MODID, "textures/entity/book/" + (
                            nbt.contains("dye") ? DyeColor.values()[nbt.getInt("dye")].getName() : "tan"
                    ) + ".png"));
                    IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);
                    model.render(pte, pte.animationTick, matrix, vertexBuilder, combinedLightIn, combinedOverlayIn, 1f, 1f, 1f, 1f);
                }

                if(nbt.contains("binding")) {
                    RenderType renderType = RenderType.entityTranslucent(new ResourceLocation(References.MODID, "textures/entity/book/binding_"+
                            nbt.getString("binding")+".png"));
                    IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);
                    model.render(pte, pte.animationTick, matrix, vertexBuilder, combinedLightIn, combinedOverlayIn, 1f, 1f, 1f, 1f);
                }

                if(nbt.contains("decor")) {
                    RenderType renderType = RenderType.entityTranslucent(new ResourceLocation(References.MODID, "textures/entity/book/decor_"+
                            nbt.getString("decor")+".png"));
                    IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);
                    model.render(pte, pte.animationTick, matrix, vertexBuilder, combinedLightIn, combinedOverlayIn, 1f, 1f, 1f, 1f);
                }

                if(nbt.contains("decor_sec")) {
                    RenderType renderType = RenderType.entityTranslucent(new ResourceLocation(References.MODID, "textures/entity/book/decor_"+
                            nbt.getString("decor_sec")+".png"));
                    IVertexBuilder vertexBuilder = buffer.getBuffer(renderType);
                    model.render(pte, pte.animationTick, matrix, vertexBuilder, combinedLightIn, combinedOverlayIn, 1f, 1f, 1f, 1f);
                }
                matrix.popPose();
            }
            else {
                matrix.pushPose();
                float i = Minecraft.getInstance().player.tickCount + partial;
                matrix.translate(0.5D, 1.35D + Math.sin(i / 20f) * 0.075D, 0.5D);
                float f = direction.toYRot();
                matrix.mulPose(Vector3f.YN.rotationDegrees(f));
                matrix.scale(0.65F, 0.65F, 0.65F);
                Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrix, buffer);
                matrix.popPose();
            }
        }
    }
}
