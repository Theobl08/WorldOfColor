package net.theobl.worldofcolor.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.theobl.worldofcolor.client.renderer.blockentity.ColoredDecoratedPotRenderer;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Objects;
import java.util.Set;

public class ColoredDecoratedPotSpecialRenderer implements SpecialModelRenderer<PotDecorations> {
    private final ColoredDecoratedPotRenderer decoratedPotRenderer;
    private final DyeColor color;

    public ColoredDecoratedPotSpecialRenderer(ColoredDecoratedPotRenderer decoratedPotRenderer, DyeColor color) {
        this.decoratedPotRenderer = decoratedPotRenderer;
        this.color = color;
    }

    @Nullable
    public PotDecorations extractArgument(ItemStack stack) {
        return stack.get(DataComponents.POT_DECORATIONS);
    }

    @Override
    public void submit(@Nullable PotDecorations argument, ItemDisplayContext displayContext, PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, int packedOverlay, boolean hasFoil, int outlineColor) {
        this.decoratedPotRenderer.color = this.color;
        this.decoratedPotRenderer.submit(poseStack, nodeCollector, packedLight, packedOverlay, Objects.requireNonNullElse(argument, PotDecorations.EMPTY), outlineColor);
    }

    @Override
    public void getExtents(Set<Vector3f> output) {
        decoratedPotRenderer.getExtents(output);
    }

    public record Unbaked(DyeColor color) implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<ColoredDecoratedPotSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(DyeColor.CODEC.fieldOf("color").forGetter(ColoredDecoratedPotSpecialRenderer.Unbaked::color))
                        .apply(instance, ColoredDecoratedPotSpecialRenderer.Unbaked::new)
        );

        @Override
        public MapCodec<ColoredDecoratedPotSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(SpecialModelRenderer.BakingContext context) {
            return new ColoredDecoratedPotSpecialRenderer(new ColoredDecoratedPotRenderer(context), color());
        }
    }
}
