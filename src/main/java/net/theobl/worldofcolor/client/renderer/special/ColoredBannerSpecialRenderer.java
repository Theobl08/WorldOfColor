package net.theobl.worldofcolor.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.BannerSpecialRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.theobl.worldofcolor.client.renderer.blockentity.ColoredBannerRenderer;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class ColoredBannerSpecialRenderer implements SpecialModelRenderer<BannerPatternLayers> {
    private final ColoredBannerRenderer bannerRenderer;

    public ColoredBannerSpecialRenderer(ColoredBannerRenderer bannerRenderer) {
        this.bannerRenderer = bannerRenderer;
    }

    public @Nullable BannerPatternLayers extractArgument(ItemStack stack) {
        return stack.get(DataComponents.BANNER_PATTERNS);
    }

    public void submit(
            @Nullable BannerPatternLayers patternLayers,
            ItemDisplayContext context,
            PoseStack poseStack,
            SubmitNodeCollector nodeCollector,
            int p_439962_,
            int p_439234_,
            boolean p_440507_,
            int p_451701_
    ) {
        this.bannerRenderer
                .submitSpecial(
                        poseStack, nodeCollector, p_439962_, p_439234_, DyeColor.WHITE, Objects.requireNonNullElse(patternLayers, BannerPatternLayers.EMPTY), p_451701_
                );
    }

    @Override
    public void getExtents(Consumer<Vector3fc> consumer) {
        this.bannerRenderer.getExtents(consumer);
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<ColoredBannerSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new ColoredBannerSpecialRenderer.Unbaked());

        @Override
        public MapCodec<ColoredBannerSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(SpecialModelRenderer.BakingContext context) {
            return new ColoredBannerSpecialRenderer(new ColoredBannerRenderer(context));
        }
    }
}
