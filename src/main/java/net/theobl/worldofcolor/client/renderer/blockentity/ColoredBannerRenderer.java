package net.theobl.worldofcolor.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
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
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.util.Unit;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.theobl.worldofcolor.ModMaterial;
import org.jspecify.annotations.Nullable;

public class ColoredBannerRenderer extends BannerRenderer {
    private static final float SIZE = 0.6666667F;
    private final MaterialSet materials;
    private final BannerModel standingModel;
    private final BannerModel wallModel;
    private final BannerFlagModel standingFlagModel;
    private final BannerFlagModel wallFlagModel;
    public ColoredBannerRenderer(BlockEntityRendererProvider.Context context) {
        this(context.entityModelSet(), context.materials());
    }

    public ColoredBannerRenderer(SpecialModelRenderer.BakingContext context) {
        this(context.entityModelSet(), context.materials());
    }

    public ColoredBannerRenderer(EntityModelSet modelSet, MaterialSet materials) {
        super(modelSet, materials);
        this.materials = materials;
        this.standingModel = new BannerModel(modelSet.bakeLayer(ModelLayers.STANDING_BANNER));
        this.wallModel = new BannerModel(modelSet.bakeLayer(ModelLayers.WALL_BANNER));
        this.standingFlagModel = new BannerFlagModel(modelSet.bakeLayer(ModelLayers.STANDING_BANNER_FLAG));
        this.wallFlagModel = new BannerFlagModel(modelSet.bakeLayer(ModelLayers.WALL_BANNER_FLAG));
    }

    @Override
    public void submit(BannerRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        BannerModel bannermodel;
        BannerFlagModel bannerflagmodel;
        if (renderState.standing) {
            bannermodel = this.standingModel;
            bannerflagmodel = this.standingFlagModel;
        } else {
            bannermodel = this.wallModel;
            bannerflagmodel = this.wallFlagModel;
        }

        submitBanner(
                this.materials,
                poseStack,
                nodeCollector,
                renderState.lightCoords,
                OverlayTexture.NO_OVERLAY,
                renderState.angle,
                bannermodel,
                bannerflagmodel,
                renderState.phase,
                renderState.baseColor,
                renderState.patterns,
                renderState.breakProgress,
                0
        );
    }

    public void submitSpecial(
            PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, int packedOverlay, DyeColor baseColor, BannerPatternLayers patterns, int outlineColor
    ) {
        submitBanner(
                this.materials,
                poseStack,
                nodeCollector,
                packedLight,
                packedOverlay,
                0.0F,
                this.standingModel,
                this.standingFlagModel,
                0.0F,
                baseColor,
                patterns,
                null,
                outlineColor
        );
    }

    private static void submitBanner(
            MaterialSet materialSet,
            PoseStack poseStack,
            SubmitNodeCollector nodeCollector,
            int p_440110_,
            int p_439479_,
            float p_439639_,
            BannerModel bannerModel,
            BannerFlagModel bannerFlagModel,
            float p_440294_,
            DyeColor color,
            BannerPatternLayers patternLayers,
            ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay,
            int p_451702_
    ) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.0F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(p_439639_));
        poseStack.scale(SIZE, -SIZE, -SIZE);
        Material material = ModelBakery.BANNER_BASE;
        nodeCollector.submitModel(
                bannerModel,
                Unit.INSTANCE,
                poseStack,
                material.renderType(RenderTypes::entitySolid),
                p_440110_,
                p_439479_,
                -1,
                materialSet.get(material),
                p_451702_,
                crumblingOverlay
        );
        submitPatterns(
                materialSet, poseStack, nodeCollector, p_440110_, p_439479_, bannerFlagModel, p_440294_, material, true, color, patternLayers, false, crumblingOverlay, p_451702_
        );
        poseStack.popPose();
    }

    public static <S> void submitPatterns(
            MaterialSet materials,
            PoseStack poseStack,
            SubmitNodeCollector nodeCollector,
            int packedLight,
            int packedOverlay,
            Model<S> flag,
            S renderState,
            Material p_material,
            boolean banner,
            DyeColor baseColor,
            BannerPatternLayers patterns,
            boolean withGlint,
            ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay,
            int outlineColor
    ) {
        nodeCollector.submitModel(
                flag,
                renderState,
                poseStack,
                p_material.renderType(RenderTypes::entitySolid),
                packedLight,
                packedOverlay,
                -1,
                materials.get(p_material),
                outlineColor,
                crumblingOverlay
        );
        if (withGlint) {
            nodeCollector.submitModel(flag, renderState, poseStack, RenderTypes.entityGlint(), packedLight, packedOverlay, -1, materials.get(p_material), 0, crumblingOverlay);
        }

        submitPatternLayer(
                materials,
                poseStack,
                nodeCollector,
                packedLight,
                packedOverlay,
                flag,
                renderState,
                banner ? ModMaterial.BANNER_RGB : Sheets.SHIELD_BASE,
                baseColor,
                crumblingOverlay
        );

        for (int i = 0; i < 16 && i < patterns.layers().size(); i++) {
            BannerPatternLayers.Layer bannerpatternlayers$layer = patterns.layers().get(i);
            Material material = banner
                    ? Sheets.getBannerMaterial(bannerpatternlayers$layer.pattern())
                    : Sheets.getShieldMaterial(bannerpatternlayers$layer.pattern());
            submitPatternLayer(materials, poseStack, nodeCollector, packedLight, packedOverlay, flag, renderState, material, bannerpatternlayers$layer.color(), null);
        }
    }

    private static <S> void submitPatternLayer(
            MaterialSet materials,
            PoseStack poseStack,
            SubmitNodeCollector nodeCollector,
            int packedLight,
            int packedOverlay,
            Model<S> flagModel,
            S sway,
            Material material,
            DyeColor color,
            ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay
    ) {
        int i = color.getTextureDiffuseColor();
        nodeCollector.submitModel(
                flagModel,
                sway,
                poseStack,
                material.renderType(RenderTypes::entityNoOutline),
                packedLight,
                packedOverlay,
                i,
                materials.get(material),
                0,
                crumblingOverlay
        );
    }
}
