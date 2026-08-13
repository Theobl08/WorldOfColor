package net.theobl.worldofcolor.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.DecoratedPotSpecialRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.theobl.worldofcolor.client.renderer.blockentity.ColoredDecoratedPotRenderer;
import org.jspecify.annotations.Nullable;
import org.joml.Vector3fc;

import java.util.Objects;
import java.util.function.Consumer;

public class ColoredDecoratedPotSpecialRenderer extends DecoratedPotSpecialRenderer {
    private final ColoredDecoratedPotRenderer decoratedPotRenderer;
    private final DyeColor color;

    public ColoredDecoratedPotSpecialRenderer(ColoredDecoratedPotRenderer decoratedPotRenderer, DyeColor color) {
        super(decoratedPotRenderer);
        this.decoratedPotRenderer = decoratedPotRenderer;
        this.color = color;
    }

    @Override
    public void submit(@Nullable PotDecorations argument, PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, int packedOverlay, boolean hasFoil, int outlineColor) {
        this.decoratedPotRenderer.color = this.color;
        super.submit(argument, poseStack, nodeCollector, packedLight, packedOverlay, hasFoil, outlineColor);
    }

    public record Unbaked(DyeColor color) implements SpecialModelRenderer.Unbaked<PotDecorations> {
        public static final MapCodec<ColoredDecoratedPotSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(DyeColor.CODEC.fieldOf("color").forGetter(ColoredDecoratedPotSpecialRenderer.Unbaked::color))
                        .apply(instance, ColoredDecoratedPotSpecialRenderer.Unbaked::new)
        );

        @Override
        public MapCodec<ColoredDecoratedPotSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public ColoredDecoratedPotSpecialRenderer bake(SpecialModelRenderer.BakingContext context) {
            return new ColoredDecoratedPotSpecialRenderer(new ColoredDecoratedPotRenderer(context), color());
        }
    }
}
