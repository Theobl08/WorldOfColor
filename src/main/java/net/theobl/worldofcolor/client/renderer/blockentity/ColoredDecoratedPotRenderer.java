package net.theobl.worldofcolor.client.renderer.blockentity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.client.renderer.blockentity.state.DecoratedPotRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ColoredDecoratedPotBlock;
import org.joml.Vector3fc;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public class ColoredDecoratedPotRenderer extends DecoratedPotRenderer {
    private static final Map<ResourceKey<Item>, SpriteId> DECORATED_POT_SPRITES = Util.make(() -> {
        ImmutableMap.Builder<ResourceKey<Item>, SpriteId> builder = ImmutableMap.builder();
        DecoratedPotPatterns.itemToPatternMappings((itemId, patternId) -> {
            Holder.Reference<DecoratedPotPattern> pattern = BuiltInRegistries.DECORATED_POT_PATTERN.getOrThrow(patternId);
            builder.put(itemId, Sheets.DECORATED_POT_MAPPER.apply(pattern.value().assetId()));
        });
        return builder.buildOrThrow();
    });
    private final SpriteGetter sprites;
    private static final String NECK = "neck";
    private static final String FRONT = "front";
    private static final String BACK = "back";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String TOP = "top";
    private static final String BOTTOM = "bottom";
    private final ModelPart neck;
    private final ModelPart frontSide;
    private final ModelPart backSide;
    private final ModelPart leftSide;
    private final ModelPart rightSide;
    private final ModelPart top;
    private final ModelPart bottom;
    private static final float WOBBLE_AMPLITUDE = 0.125F;
    public DyeColor color;

    public ColoredDecoratedPotRenderer(BlockEntityRendererProvider.Context context) {
        this(context.entityModelSet(), context.sprites());
    }

    public ColoredDecoratedPotRenderer(SpecialModelRenderer.BakingContext context) {
        this(context.entityModelSet(), context.sprites());
    }

    public ColoredDecoratedPotRenderer(EntityModelSet modelSet, SpriteGetter materials) {
        super(modelSet, materials);
        this.sprites = materials;
        ModelPart base = modelSet.bakeLayer(ModelLayers.DECORATED_POT_BASE);
        this.neck = base.getChild(NECK);
        this.top = base.getChild(TOP);
        this.bottom = base.getChild(BOTTOM);
        ModelPart sides = modelSet.bakeLayer(ModelLayers.DECORATED_POT_SIDES);
        this.frontSide = sides.getChild(FRONT);
        this.backSide = sides.getChild(BACK);
        this.leftSide = sides.getChild(LEFT);
        this.rightSide = sides.getChild(RIGHT);
    }

    private SpriteId colorMaterial(SpriteId material) {
        if(color != null) {
            Identifier newTexture = WorldOfColor.asResource(material.texture().getPath() + "_" + color.getName());
            return new SpriteId(material.atlasLocation(), newTexture);
        }
        else {
            return material;
        }
    }

    public void submit(DecoratedPotRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        if(renderState.blockState.getBlock() instanceof ColoredDecoratedPotBlock block) {
            this.color = block.getColor();
        }
        super.submit(renderState, poseStack, nodeCollector, cameraRenderState);
    }

    public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, int packedOverlay, PotDecorations decorations, int outlineColor) {
        RenderType rendertype = Sheets.DECORATED_POT_BASE.renderType(RenderTypes::entitySolid);
        TextureAtlasSprite textureatlassprite = this.sprites.get(colorMaterial(Sheets.DECORATED_POT_BASE));
        nodeCollector.submitModelPart(this.neck, poseStack, rendertype, packedLight, packedOverlay, textureatlassprite, -1, null, outlineColor);
        nodeCollector.submitModelPart(this.top, poseStack, rendertype, packedLight, packedOverlay, textureatlassprite, -1, null, outlineColor);
        nodeCollector.submitModelPart(this.bottom, poseStack, rendertype, packedLight, packedOverlay, textureatlassprite, -1, null, outlineColor);
        SpriteId frontSprite = colorMaterial(getSideSprite(decorations.front()));
        nodeCollector.submitModelPart(
                this.frontSide,
                poseStack,
                frontSprite.renderType(RenderTypes::entitySolid),
                packedLight,
                packedOverlay,
                this.sprites.get(frontSprite),
                -1,
                null,
                outlineColor
        );
        SpriteId backSprite = colorMaterial(getSideSprite(decorations.back()));
        nodeCollector.submitModelPart(
                this.backSide,
                poseStack,
                backSprite.renderType(RenderTypes::entitySolid),
                packedLight,
                packedOverlay,
                this.sprites.get(backSprite),
                -1,
                null,
                outlineColor
        );
        SpriteId leftSprite = colorMaterial(getSideSprite(decorations.left()));
        nodeCollector.submitModelPart(
                this.leftSide,
                poseStack,
                leftSprite.renderType(RenderTypes::entitySolid),
                packedLight,
                packedOverlay,
                this.sprites.get(leftSprite),
                -1,
                null,
                outlineColor
        );
        SpriteId rightSprite = colorMaterial(getSideSprite(decorations.right()));
        nodeCollector.submitModelPart(
                this.rightSide,
                poseStack,
                rightSprite.renderType(RenderTypes::entitySolid),
                packedLight,
                packedOverlay,
                this.sprites.get(rightSprite),
                -1,
                null,
                outlineColor
        );
    }

    public void getExtents(Consumer<Vector3fc> output) {
        PoseStack posestack = new PoseStack();
        this.neck.getExtentsForGui(posestack, output);
        this.top.getExtentsForGui(posestack, output);
        this.bottom.getExtentsForGui(posestack, output);
    }
}
