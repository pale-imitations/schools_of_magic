package com.paleimitations.schoolsofmagic.client.items.models;

import com.google.common.collect.*;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.data.BindingType;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.VanillaResourceType;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class BookModel implements IModelGeometry<BookModel> {

    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(References.MODID, "book");
    public static final ModelResourceLocation LOCATION = new ModelResourceLocation(RESOURCE_LOCATION, "inventory");

    private static final float NORTH_Z_COVER = 7.496f / 16f;
    private static final float SOUTH_Z_COVER = 8.504f / 16f;
    private static final float NORTH_Z_DECOR = 7.48f / 16f;
    private static final float SOUTH_Z_DECOR = 8.51f / 16f;
    private static final float NORTH_Z_DECOR2 = 7.47f / 16f;
    private static final float SOUTH_Z_DECOR2 = 8.52f / 16f;
    @Nullable
    private final ResourceLocation coverLocation;
    @Nullable
    private final ResourceLocation bindingLocation;
    @Nullable
    private final ResourceLocation decorLocation;
    @Nullable
    private final ResourceLocation decorLocation2;

    public BookModel(){
        this(null, null, null, null);
    }

    public BookModel(ResourceLocation coverLocation, ResourceLocation bindingLocation, ResourceLocation decorLocation, ResourceLocation decorLocation2) {
        this.coverLocation = coverLocation;
        this.bindingLocation = bindingLocation;
        this.decorLocation = decorLocation;
        this.decorLocation2 = decorLocation2;
        System.out.println("Basic Arcana Model run");
    }

    @Override
    public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
        RenderMaterial particleLocation = owner.isTexturePresent("particle") ? owner.resolveTexture("particle") : null;
        IModelTransform transformsFromModel = owner.getCombinedTransform();

        TextureAtlasSprite coverSprite = ModelLoader.instance().getSpriteMap().getAtlas(PlayerContainer.BLOCK_ATLAS).getSprite(coverLocation);

        ImmutableMap<ItemCameraTransforms.TransformType, TransformationMatrix> transformMap =
                PerspectiveMapWrapper.getTransforms(new ModelTransformComposition(transformsFromModel, modelTransform));

        TextureAtlasSprite particleSprite = particleLocation != null ? spriteGetter.apply(particleLocation) : null;

        if (particleSprite == null) particleSprite = coverSprite;

        TransformationMatrix transform = modelTransform.getRotation();

        ItemMultiLayerBakedModel.Builder builder = ItemMultiLayerBakedModel.builder(owner, particleSprite, new BookOverrideHandler(overrides, bakery, owner, this), transformMap);

        if (coverSprite != null) {
            builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemLayerModel.getQuadsForSprite(0, coverSprite, transform, false));
            System.out.println("Basic Arcana cover used");
        }

        if (bindingLocation != null) {
            TextureAtlasSprite bindingSprite = ModelLoader.instance().getSpriteMap().getAtlas(AtlasTexture.LOCATION_BLOCKS).getSprite(bindingLocation);
            builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemTextureQuadConverter.genQuad(transform, 0, 0, 16, 16, NORTH_Z_COVER, bindingSprite, Direction.NORTH, 0xFFFFFFFF, 2));
            builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemTextureQuadConverter.genQuad(transform, 0, 0, 16, 16, SOUTH_Z_COVER, bindingSprite, Direction.SOUTH, 0xFFFFFFFF, 2));
            System.out.println("Basic Arcana binding used");
        }

        if (decorLocation != null) {
            TextureAtlasSprite decorSprite = ModelLoader.instance().getSpriteMap().getAtlas(AtlasTexture.LOCATION_BLOCKS).getSprite(decorLocation);
            builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemTextureQuadConverter.genQuad(transform, 0, 0, 16, 16, NORTH_Z_DECOR, decorSprite, Direction.NORTH, 0xFFFFFFFF, 2));
            builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemTextureQuadConverter.genQuad(transform, 0, 0, 16, 16, SOUTH_Z_DECOR, decorSprite, Direction.SOUTH, 0xFFFFFFFF, 2));
            System.out.println("Basic Arcana decor used");
        }

        if (decorLocation2 != null) {
            TextureAtlasSprite decorSprite = ModelLoader.instance().getSpriteMap().getAtlas(AtlasTexture.LOCATION_BLOCKS).getSprite(decorLocation2);
            builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemTextureQuadConverter.genQuad(transform, 0, 0, 16, 16, NORTH_Z_DECOR2, decorSprite, Direction.NORTH, 0xFFFFFFFF, 2));
            builder.addQuads(ItemLayerModel.getLayerRenderType(false), ItemTextureQuadConverter.genQuad(transform, 0, 0, 16, 16, SOUTH_Z_DECOR2, decorSprite, Direction.SOUTH, 0xFFFFFFFF, 2));
            System.out.println("Basic Arcana decor used");
        }

        builder.setParticle(particleSprite);

        return builder.build();
    }

    @Override
    public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
        Set<RenderMaterial> texs = Sets.newHashSet();
        return texs;
    }

    public enum Loader implements IModelLoader<BookModel> {
        INSTANCE;

        @Override
        public IResourceType getResourceType() {
            return VanillaResourceType.MODELS;
        }

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager) {
        }

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        }

        @Override
        public BookModel read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
            return new BookModel();
        }
    }

    private static final class BookOverrideHandler extends ItemOverrideList {
        private final Map<String, IBakedModel> cache = Maps.newHashMap(); // contains all the baked models since they'll never change
        private final ItemOverrideList nested;
        private final ModelBakery bakery;
        private final IModelConfiguration owner;
        private final BookModel parent;

        private BookOverrideHandler(ItemOverrideList nested, ModelBakery bakery, IModelConfiguration owner, BookModel parent) {
            this.nested = nested;
            this.bakery = bakery;
            this.owner = owner;
            this.parent = parent;
        }

        @Override
        public IBakedModel resolve(IBakedModel originalModel, ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
            IBakedModel overriden = nested.resolve(originalModel, stack, world, entity);
            if (overriden != originalModel)
                return overriden;
            CompoundNBT nbt = stack.getOrCreateTag();
            DyeColor color = nbt.contains("dye")? DyeColor.byId(nbt.getInt("dye")) : null;
            BindingType binding = nbt.contains("binding")? BindingType.fromName(nbt.getString("binding")) : null;
            BookData.DecorationType decoration = nbt.contains("decor")? BookData.DecorationType.fromName(nbt.getString("decor")) : null;
            BookData.DecorationType decoration2 = nbt.contains("decor_sec")? BookData.DecorationType.fromName(nbt.getString("decor_sec")) : null;
            String name = "book" + (color!=null? "_" + color.getName() : "") + (binding!=null? "_" + binding.getSerializedName() : "") + (decoration!=null? "_" + decoration.getSerializedName() : "") + (decoration2!=null? "_" + decoration2.getSerializedName() : "");
            if (!cache.containsKey(name)) {
                ResourceLocation coverLoc = new ResourceLocation(References.MODID + ":item/book/cover" + (color!=null? "_"+color.getName():""));
                ResourceLocation bindingLoc = binding!=null? new ResourceLocation(References.MODID + ":item/book/binding_" + binding.getSerializedName()) : null;
                ResourceLocation decorLoc = decoration!=null? new ResourceLocation(References.MODID + ":item/book/decor_" + decoration.getSerializedName()) : null;
                ResourceLocation decorLoc2 = decoration2!=null? new ResourceLocation(References.MODID + ":item/book/decor_" + decoration2.getSerializedName()) : null;
                BookModel unbaked = new BookModel(coverLoc, bindingLoc, decorLoc, decorLoc2);
                IBakedModel bakedModel = unbaked.bake(owner, bakery, ModelLoader.defaultTextureGetter(), ModelRotation.X0_Y0, this, new ResourceLocation(References.MODID,"book"));
                cache.put(name, bakedModel);
                System.out.println("Basic Arcana cached: "+name);
                return bakedModel;
            }
            return cache.get(name);
        }
    }
}
