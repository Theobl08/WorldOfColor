package net.theobl.worldofcolor.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.DecoratedPotRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.phys.Vec3;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ColoredDecoratedPotBlock;
import net.theobl.worldofcolor.block.entity.ColoredDecoratedPotBlockEntity;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;
import java.util.Set;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ColoredDecoratedPotRenderer implements BlockEntityRenderer<ColoredDecoratedPotBlockEntity, DecoratedPotRenderState> {
    private final MaterialSet materials;
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
        this(context.entityModelSet(), context.materials());
    }

    public ColoredDecoratedPotRenderer(SpecialModelRenderer.BakingContext context) {
        this(context.entityModelSet(), context.materials());
    }

    public ColoredDecoratedPotRenderer(EntityModelSet modelSet, MaterialSet materials) {
        this.materials = materials;
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

    private static Material getSideMaterial(Optional<Item> item) {
        if (item.isPresent()) {
            Material material = Sheets.getDecoratedPotMaterial(DecoratedPotPatterns.getPatternFromItem(item.get()));
            if (material != null) {
                return material;
            }
        }

        return Sheets.DECORATED_POT_SIDE;
    }

    private Material colorMaterial(Material material) {
        if(color != null) {
            ResourceLocation newTexture = WorldOfColor.asResource(material.texture().getPath() + "_" + color.getName());
            return new Material(material.atlasLocation(), newTexture);
        }
        else {
            return material;
        }
    }

    public DecoratedPotRenderState createRenderState() {
        return new DecoratedPotRenderState();
    }

    public void extractRenderState(
            ColoredDecoratedPotBlockEntity blockEntity,
            DecoratedPotRenderState renderState,
            float partialTick,
            Vec3 cameraPosition,
            @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress
    ) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);
        renderState.decorations = blockEntity.getDecorations();
        renderState.direction = blockEntity.getDirection();
        DecoratedPotBlockEntity.WobbleStyle decoratedpotblockentity$wobblestyle = blockEntity.lastWobbleStyle;
        if (decoratedpotblockentity$wobblestyle != null && blockEntity.getLevel() != null) {
            renderState.wobbleProgress = ((float)(blockEntity.getLevel().getGameTime() - blockEntity.wobbleStartedAtTick) + partialTick)
                    / decoratedpotblockentity$wobblestyle.duration;
        } else {
            renderState.wobbleProgress = 0.0F;
        }
    }

    public void submit(DecoratedPotRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        if(renderState.blockState.getBlock() instanceof ColoredDecoratedPotBlock block) {
            this.color = block.getColor();
        }
        poseStack.pushPose();
        Direction direction = renderState.direction;
        poseStack.translate(0.5, 0.0, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - direction.toYRot()));
        poseStack.translate(-0.5, 0.0, -0.5);
        if (renderState.wobbleProgress >= 0.0F && renderState.wobbleProgress <= 1.0F) {
            if (renderState.wobbleStyle == DecoratedPotBlockEntity.WobbleStyle.POSITIVE) {
                float amplitude = 0.015625F;
                float deltaTime = renderState.wobbleProgress * (float) (Math.PI * 2);
                float tiltX = -1.5F * (Mth.cos(deltaTime) + 0.5F) * Mth.sin(deltaTime / 2.0F);
                poseStack.rotateAround(Axis.XP.rotation(tiltX * amplitude), 0.5F, 0.0F, 0.5F);
                float tiltZ = Mth.sin(deltaTime);
                poseStack.rotateAround(Axis.ZP.rotation(tiltZ * amplitude), 0.5F, 0.0F, 0.5F);
            } else {
                float turnAngle = Mth.sin(-renderState.wobbleProgress * 3.0F * (float) Math.PI) * WOBBLE_AMPLITUDE;
                float linearDecayFactor = 1.0F - renderState.wobbleProgress;
                poseStack.rotateAround(Axis.YP.rotation(turnAngle * linearDecayFactor), 0.5F, 0.0F, 0.5F);
            }
        }

        this.submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, renderState.decorations, 0);
        poseStack.popPose();
    }

    public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, int packedOverlay, PotDecorations decorations, int outlineColor) {
        RenderType rendertype = Sheets.DECORATED_POT_BASE.renderType(RenderType::entitySolid);
        TextureAtlasSprite textureatlassprite = this.materials.get(colorMaterial(Sheets.DECORATED_POT_BASE));
        nodeCollector.submitModelPart(this.neck, poseStack, rendertype, packedLight, packedOverlay, textureatlassprite, false, false, -1, null, outlineColor);
        nodeCollector.submitModelPart(this.top, poseStack, rendertype, packedLight, packedOverlay, textureatlassprite, false, false, -1, null, outlineColor);
        nodeCollector.submitModelPart(this.bottom, poseStack, rendertype, packedLight, packedOverlay, textureatlassprite, false, false, -1, null, outlineColor);
        Material material = colorMaterial(getSideMaterial(decorations.front()));
        nodeCollector.submitModelPart(
                this.frontSide,
                poseStack,
                material.renderType(RenderType::entitySolid),
                packedLight,
                packedOverlay,
                this.materials.get(material),
                false,
                false,
                -1,
                null,
                outlineColor
        );
        Material material1 = colorMaterial(getSideMaterial(decorations.back()));
        nodeCollector.submitModelPart(
                this.backSide,
                poseStack,
                material1.renderType(RenderType::entitySolid),
                packedLight,
                packedOverlay,
                this.materials.get(material1),
                false,
                false,
                -1,
                null,
                outlineColor
        );
        Material material2 = colorMaterial(getSideMaterial(decorations.left()));
        nodeCollector.submitModelPart(
                this.leftSide,
                poseStack,
                material2.renderType(RenderType::entitySolid),
                packedLight,
                packedOverlay,
                this.materials.get(material2),
                false,
                false,
                -1,
                null,
                outlineColor
        );
        Material material3 = colorMaterial(getSideMaterial(decorations.right()));
        nodeCollector.submitModelPart(
                this.rightSide,
                poseStack,
                material3.renderType(RenderType::entitySolid),
                packedLight,
                packedOverlay,
                this.materials.get(material3),
                false,
                false,
                -1,
                null,
                outlineColor
        );
    }

    public void getExtents(Set<Vector3f> output) {
        PoseStack posestack = new PoseStack();
        this.neck.getExtentsForGui(posestack, output);
        this.top.getExtentsForGui(posestack, output);
        this.bottom.getExtentsForGui(posestack, output);
    }

    @Override
    public net.minecraft.world.phys.AABB getRenderBoundingBox(ColoredDecoratedPotBlockEntity blockEntity) {
        net.minecraft.core.BlockPos pos = blockEntity.getBlockPos();
        return new net.minecraft.world.phys.AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0, pos.getY() + 1.3, pos.getZ() + 1.0);
    }
}
