package net.theobl.worldofcolor.block;

import net.minecraft.data.BlockFamily;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColorCollection;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeatheringCopperCollection;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.*;

// When ColorCollection meets WeatheringCopperCollection

/**
 * A union between {@link ColorCollection} and {@link WeatheringCopperCollection}
 * @param coloring A {@link ColorCollection} which change color over time (like copper oxidizing over time)
 * @param waxed A {@link ColorCollection} which keep its color (like waxed copper keeping its oxidization state)
 * @param <T>
 */
public record ColoringColorCollection<T>(ColorCollection<T> coloring, ColorCollection<T> waxed) {
    public static final ColoringColorCollection<String> PREFIXES = new ColoringColorCollection<>(
            ColorCollection.prefixWithColor(ColorCollection.create("")),
            ColorCollection.prefixWithColor(ColorCollection.create("")).map(name -> "waxed_" + name)
    );

    public static ColoringColorCollection<String> prefixWithState(ColoringColorCollection<String> ids) {
        return zipMap(PREFIXES, ids, (state, id) -> state + id);
    }

    public static ColoringColorCollection<String> create(String name) {
        return same(ColorCollection.create(name));
    }

    public static ColoringColorCollection<String> same(ColorCollection<String> byState) {
        return new ColoringColorCollection<>(byState, byState);
    }

    // Because "oxidizable" datamap only works for blocks implementing WeatheringCopper, we keep it in the generic
    public static <WaxedBlock extends Block, ColoringBlock extends Block & WeatheringCopper, Id> ColoringColorCollection<DeferredBlock<Block>> registerBlocks(
            ColoringColorCollection<Id> ids,
            TriFunction<Id, Function<BlockBehaviour.Properties, Block>, Supplier<BlockBehaviour.Properties>, DeferredBlock<Block>> register,
            BiFunction<DyeColor, BlockBehaviour.Properties, WaxedBlock> waxedBlockFactory,
            BiFunction<DyeColor, BlockBehaviour.Properties, ColoringBlock> coloringFactory,
            Function<DyeColor, BlockBehaviour.Properties> propertiesSupplier
    ) {
        return ids.apply(
                coloringIds -> ColorCollection.zipMap(
                        ColorCollection.VALUES, coloringIds, (color, id) -> register.apply(id, p -> coloringFactory.apply(color, p), () -> propertiesSupplier.apply(color))
                ),
                waxedIds -> ColorCollection.zipMap(ColorCollection.VALUES, waxedIds, (color, id) -> register.apply(id, p -> waxedBlockFactory.apply(color, p), () -> propertiesSupplier.apply(color)))
        );
    }

    public static ColoringColorCollection<BlockFamily> createFamily(
            BiFunction<String, DyeColor, BlockFamily> waxedProvider,
            BiFunction<String, DyeColor, BlockFamily> coloringProvider
    ) {
        return PREFIXES.apply(
                coloringPrefixes -> ColorCollection.zipMap(coloringPrefixes, ColorCollection.VALUES, coloringProvider),
                waxedPrefixes -> ColorCollection.zipMap(waxedPrefixes, ColorCollection.VALUES, waxedProvider)
        );
    }

    public void forEach(Consumer<T> consumer) {
        this.coloring.forEach(consumer);
        this.waxed.forEach(consumer);
    }

    public <U> ColoringColorCollection<U> map(Function<T, U> mapper) {
        return new ColoringColorCollection<>(this.coloring.map(mapper), this.waxed.map(mapper));
    }

    public <U> ColoringColorCollection<U> apply(
            Function<ColorCollection<T>, ColorCollection<U>> weatheringMapper,
            Function<ColorCollection<T>, ColorCollection<U>> waxedMapper
    ) {
        return new ColoringColorCollection<>(weatheringMapper.apply(this.coloring), waxedMapper.apply(this.waxed));
    }

    public static <T, U> void zipApply(ColoringColorCollection<T> first, ColoringColorCollection<U> second, BiConsumer<T, U> consumer) {
        ColorCollection.zipApply(first.coloring, second.coloring, consumer);
        ColorCollection.zipApply(first.waxed, second.waxed, consumer);
    }

    public static <T, U, R> ColoringColorCollection<R> zipMap(
            ColoringColorCollection<T> first, ColoringColorCollection<U> second, BiFunction<T, U, R> operation
    ) {
        return new ColoringColorCollection<>(
                ColorCollection.zipMap(first.coloring, second.coloring, operation),
                ColorCollection.zipMap(first.waxed, second.waxed, operation)
        );
    }

    public void zipUnwaxedWaxed(BiConsumer<T, T> consumer) {
        ColorCollection.zipApply(this.coloring, this.waxed, consumer);
    }
}
