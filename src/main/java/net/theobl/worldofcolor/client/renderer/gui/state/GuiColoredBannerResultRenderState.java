package net.theobl.worldofcolor.client.renderer.gui.state;

import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.model.object.banner.BannerFlagModel;
import net.minecraft.client.renderer.state.gui.pip.PictureInPictureRenderState;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.jspecify.annotations.Nullable;

public record GuiColoredBannerResultRenderState(
        BannerFlagModel flag,
        DyeColor baseColor,
        BannerPatternLayers resultBannerPatterns,
        int x0,
        int y0,
        int x1,
        int y1,
        @Nullable ScreenRectangle scissorArea,
        @Nullable ScreenRectangle bounds
) implements net.minecraft.client.renderer.state.gui.pip.PictureInPictureRenderState {
    public GuiColoredBannerResultRenderState(
            BannerFlagModel flag,
            DyeColor baseColor,
            BannerPatternLayers resultBannerPatterns,
            int x0,
            int y0,
            int x1,
            int y1,
            @Nullable ScreenRectangle scissorArea
    ) {
        this(
                flag,
                baseColor,
                resultBannerPatterns,
                x0,
                y0,
                x1,
                y1,
                scissorArea,
                PictureInPictureRenderState.getBounds(x0, y0, x1, y1, scissorArea)
        );
    }

    @Override
    public float scale() {
        return 16.0F;
    }
}
