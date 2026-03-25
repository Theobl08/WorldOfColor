package net.theobl.worldofcolor.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.theobl.worldofcolor.client.renderer.blockentity.ColoredBannerRenderer;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class ColoredBannerSpecialRenderer implements SpecialModelRenderer<BannerPatternLayers> {
    private final ColoredBannerRenderer bannerRenderer;
    private final BannerBlock.AttachmentType attachment;

    public ColoredBannerSpecialRenderer(ColoredBannerRenderer bannerRenderer, BannerBlock.AttachmentType attachment) {
        this.bannerRenderer = bannerRenderer;
        this.attachment = attachment;
    }

    public @Nullable BannerPatternLayers extractArgument(ItemStack stack) {
        return stack.get(DataComponents.BANNER_PATTERNS);
    }

    public void submit(
            @Nullable BannerPatternLayers patternLayers,
            PoseStack poseStack,
            SubmitNodeCollector nodeCollector,
            int lightCoords,
            int overlayCoords,
            boolean hasFoil,
            int outlineColor
    ) {
        this.bannerRenderer
                .submitSpecial(this.attachment,
                        poseStack, nodeCollector, lightCoords, overlayCoords, DyeColor.WHITE, Objects.requireNonNullElse(patternLayers, BannerPatternLayers.EMPTY), outlineColor
                );
    }

    @Override
    public void getExtents(Consumer<Vector3fc> consumer) {
        this.bannerRenderer.getExtents(consumer);
    }

    public record Unbaked(BannerBlock.AttachmentType attachment) implements SpecialModelRenderer.Unbaked<BannerPatternLayers> {
        public static final MapCodec<ColoredBannerSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
                i -> i.group(
                        BannerBlock.AttachmentType.CODEC.optionalFieldOf("attachment", BannerBlock.AttachmentType.GROUND)
                                .forGetter(ColoredBannerSpecialRenderer.Unbaked::attachment)
                        )
                        .apply(i,  ColoredBannerSpecialRenderer.Unbaked::new)
        );

        @Override
        public MapCodec<ColoredBannerSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public ColoredBannerSpecialRenderer bake(SpecialModelRenderer.BakingContext context) {
            return new ColoredBannerSpecialRenderer(new ColoredBannerRenderer(context), this.attachment);
        }
    }
}
