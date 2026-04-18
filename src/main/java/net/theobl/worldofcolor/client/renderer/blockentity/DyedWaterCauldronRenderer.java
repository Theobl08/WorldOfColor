package net.theobl.worldofcolor.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ARGB;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.phys.Vec3;
import net.theobl.worldofcolor.block.entity.DyedWaterCauldronBlockEntity;
import net.theobl.worldofcolor.client.renderer.blockentity.state.DyedWaterCauldronRenderState;
import org.joml.Matrix4f;
import org.jspecify.annotations.Nullable;

public class DyedWaterCauldronRenderer implements BlockEntityRenderer<DyedWaterCauldronBlockEntity, DyedWaterCauldronRenderState> {
    private static final double[] HEIGHT_PER_LEVEL = { 0.5625f, 0.75f, 0.9375f };

    public DyedWaterCauldronRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public DyedWaterCauldronRenderState createRenderState() {
        return new DyedWaterCauldronRenderState();
    }

    @Override
    public void extractRenderState(DyedWaterCauldronBlockEntity blockEntity, DyedWaterCauldronRenderState renderState, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);
        renderState.waterColor = blockEntity.getWaterColor();
        renderState.waterLevel = blockEntity.getBlockState().getValue(LayeredCauldronBlock.LEVEL);
    }

    @Override
    public void submit(DyedWaterCauldronRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        TextureAtlasSprite water = Minecraft.getInstance().getAtlasManager().get(Sheets.BLOCKS_MAPPER.defaultNamespaceApply("water_still"));
        poseStack.pushPose();
        poseStack.translate(0, HEIGHT_PER_LEVEL[renderState.waterLevel - 1], 0);

        int red = ARGB.red(renderState.waterColor);
        int green = ARGB.green(renderState.waterColor);
        int blue = ARGB.blue(renderState.waterColor);
        int alpha = 255;

        float sizeFactor = 0.125f; // The inside of cauldron is a square of 14p*14p instead of the full 16p*16p square
        float minU = (water.getU1() - water.getU0()) * (1 - sizeFactor);
        float maxU = (water.getU1() - water.getU0()) * sizeFactor;
        float minV = (water.getV1() - water.getV0()) * (1 - sizeFactor);
        float maxV = (water.getV1() - water.getV0()) * sizeFactor;

        nodeCollector.submitCustomGeometry(poseStack, RenderTypes.entityTranslucent(TextureAtlas.LOCATION_BLOCKS), (pose, consumer) -> {
            Matrix4f matrix = pose.pose();

            consumer.addVertex(matrix, 0.0f + sizeFactor, 0.0f, 1.0f - sizeFactor)
                    .setColor(red, green, blue, alpha)
                    .setUv(water.getU0() + maxU, water.getV0() + minV)
                    .setLight(renderState.lightCoords)
                    .setOverlay(OverlayTexture.NO_OVERLAY)
                    .setNormal(0, 1, 0);

            consumer.addVertex(matrix, 1.0f - sizeFactor, 0.0f, 1.0f - sizeFactor)
                    .setColor(red, green, blue, alpha)
                    .setUv(water.getU0() + minU, water.getV0() + minV)
                    .setLight(renderState.lightCoords)
                    .setOverlay(OverlayTexture.NO_OVERLAY)
                    .setNormal(0, 1, 0);

            consumer.addVertex(matrix, 1.0f - sizeFactor, 0.0f, 0.0f + sizeFactor)
                    .setColor(red, green, blue, alpha)
                    .setUv(water.getU0() + minU, water.getV0() + maxV)
                    .setLight(renderState.lightCoords)
                    .setOverlay(OverlayTexture.NO_OVERLAY)
                    .setNormal(0, 1, 0);

            consumer.addVertex(matrix, 0.0f + sizeFactor, 0.0f, 0.0f + sizeFactor)
                    .setColor(red, green, blue, alpha)
                    .setUv(water.getU0() + maxU, water.getV0() + maxV)
                    .setLight(renderState.lightCoords)
                    .setOverlay(OverlayTexture.NO_OVERLAY)
                    .setNormal(0, 1, 0);
        });
        poseStack.popPose();
    }

    @Override
    public int getViewDistance() {
        return Minecraft.getInstance().options.getEffectiveRenderDistance() * 16;
    }
}
