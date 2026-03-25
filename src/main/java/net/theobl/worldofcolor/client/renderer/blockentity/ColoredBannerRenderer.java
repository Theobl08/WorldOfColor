package net.theobl.worldofcolor.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.banner.BannerFlagModel;
import net.minecraft.client.model.object.banner.BannerModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BannerRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.util.Unit;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.theobl.worldofcolor.ModMaterial;
import org.jspecify.annotations.Nullable;

public class ColoredBannerRenderer extends BannerRenderer {
    private static final float SIZE = 0.6666667F;
    private final SpriteGetter sprites;
    private final BannerModel standingModel;
    private final BannerModel wallModel;
    private final BannerFlagModel standingFlagModel;
    private final BannerFlagModel wallFlagModel;
    public ColoredBannerRenderer(BlockEntityRendererProvider.Context context) {
        this(context.entityModelSet(), context.sprites());
    }

    public ColoredBannerRenderer(SpecialModelRenderer.BakingContext context) {
        this(context.entityModelSet(), context.sprites());
    }

    public ColoredBannerRenderer(EntityModelSet modelSet, SpriteGetter sprites) {
        super(modelSet, sprites);
        this.sprites = sprites;
        this.standingModel = new BannerModel(modelSet.bakeLayer(ModelLayers.STANDING_BANNER));
        this.wallModel = new BannerModel(modelSet.bakeLayer(ModelLayers.WALL_BANNER));
        this.standingFlagModel = new BannerFlagModel(modelSet.bakeLayer(ModelLayers.STANDING_BANNER_FLAG));
        this.wallFlagModel = new BannerFlagModel(modelSet.bakeLayer(ModelLayers.WALL_BANNER_FLAG));
    }

    private BannerModel bannerModel(BannerBlock.AttachmentType type) {
        return switch (type) {
            case WALL -> this.wallModel;
            case GROUND -> this.standingModel;
        };
    }

    private BannerFlagModel flagModel(BannerBlock.AttachmentType type) {
        return switch (type) {
            case WALL -> this.wallFlagModel;
            case GROUND -> this.standingFlagModel;
        };
    }

    @Override
    public void submit(BannerRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.mulPose(renderState.transformation);
        submitBanner(
                this.sprites,
                poseStack,
                nodeCollector,
                renderState.lightCoords,
                OverlayTexture.NO_OVERLAY,
                this.bannerModel(renderState.attachmentType),
                this.flagModel(renderState.attachmentType),
                renderState.phase,
                renderState.baseColor,
                renderState.patterns,
                renderState.breakProgress,
                0
        );
        poseStack.popPose();
    }

    public void submitSpecial(
            BannerBlock.AttachmentType type,
            PoseStack poseStack,
            SubmitNodeCollector nodeCollector,
            int lightCoords,
            int overlayCoords,
            DyeColor baseColor,
            BannerPatternLayers patterns,
            int outlineColor
    ) {
        submitBanner(
                this.sprites,
                poseStack,
                nodeCollector,
                lightCoords,
                overlayCoords,
                this.bannerModel(type),
                this.flagModel(type),
                0.0F,
                baseColor,
                patterns,
                null,
                outlineColor
        );
    }

    private static void submitBanner(
            SpriteGetter sprites,
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            int lightCoords,
            int overlayCoords,
            BannerModel model,
            BannerFlagModel flagModel,
            float phase,
            DyeColor color,
            BannerPatternLayers patternLayers,
            ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress,
            int outlineColor
    ) {
        SpriteId sprite = Sheets.BANNER_BASE;
        submitNodeCollector.submitModel(model, Unit.INSTANCE, poseStack, lightCoords, overlayCoords, -1, sprite, sprites, outlineColor, breakProgress);
        submitNodeCollector.submitModel(flagModel, phase, poseStack, lightCoords, overlayCoords, -1, sprite, sprites, outlineColor, breakProgress);
        submitPatterns(sprites, poseStack, submitNodeCollector, lightCoords, overlayCoords, flagModel, phase, true, color, patternLayers, breakProgress);
    }

    public static <S> void submitPatterns(
            SpriteGetter materials,
            PoseStack poseStack,
            SubmitNodeCollector nodeCollector,
            int lightCoords,
            int overlayCoords,
            Model<S> flag,
            S renderState,
            boolean banner,
            DyeColor baseColor,
            BannerPatternLayers patterns,
            ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay
    ) {
        submitPatternLayer(
                materials,
                poseStack,
                nodeCollector,
                lightCoords,
                overlayCoords,
                flag,
                renderState,
                banner ? ModMaterial.BANNER_RGB : Sheets.SHIELD_PATTERN_BASE,
                baseColor,
                crumblingOverlay
        );

        for (int maskIndex = 0; maskIndex < 16 && maskIndex < patterns.layers().size(); maskIndex++) {
            BannerPatternLayers.Layer layer = patterns.layers().get(maskIndex);
            SpriteId sprite = banner
                    ? Sheets.getBannerSprite(layer.pattern())
                    : Sheets.getShieldSprite(layer.pattern());
            submitPatternLayer(materials, poseStack, nodeCollector, lightCoords, overlayCoords, flag, renderState, sprite, layer.color(), null);
        }
    }

    private static <S> void submitPatternLayer(
            SpriteGetter sprites,
            PoseStack poseStack,
            SubmitNodeCollector nodeCollector,
            int packedLight,
            int packedOverlay,
            Model<S> flagModel,
            S sway,
            SpriteId sprite,
            DyeColor color,
            ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay
    ) {
        int i = color.getTextureDiffuseColor();
        nodeCollector.submitModel(
                flagModel,
                sway,
                poseStack,
                sprite.renderType(RenderTypes::bannerPattern),
                packedLight,
                packedOverlay,
                i,
                sprites.get(sprite),
                0,
                crumblingOverlay
        );
    }
}
