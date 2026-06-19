package net.theobl.worldofcolor.client.renderer.gui;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.theobl.worldofcolor.client.renderer.blockentity.ColoredBannerRenderer;
import net.theobl.worldofcolor.client.renderer.gui.state.GuiColoredBannerResultRenderState;

public class GuiColoredBannerResultRenderer extends PictureInPictureRenderer<GuiColoredBannerResultRenderState> {
    private final SpriteGetter sprites;

    public GuiColoredBannerResultRenderer(SpriteGetter materials) {
        this.sprites = materials;
    }

    @Override
    public Class<GuiColoredBannerResultRenderState> getRenderStateClass() {
        return GuiColoredBannerResultRenderState.class;
    }

    @Override
    protected void renderToTexture(GuiColoredBannerResultRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
        Minecraft.getInstance().gameRenderer.lighting().setupFor(Lighting.Entry.ITEMS_FLAT);
        poseStack.translate(0.0F, 0.25F, 0.0F);
        submitNodeCollector.submitModel(renderState.flag(), 0.0F, poseStack, 15728880, OverlayTexture.NO_OVERLAY, -1, Sheets.BANNER_BASE, this.sprites, 0, null);
        ColoredBannerRenderer.submitPatterns(
                this.sprites,
                poseStack,
                submitNodeCollector,
                15728880,
                OverlayTexture.NO_OVERLAY,
                renderState.flag(),
                0.0F,
                true,
                renderState.baseColor(),
                renderState.resultBannerPatterns(),
                null
        );
    }

    @Override
    protected String getTextureLabel() {
        return "banner result";
    }
}
