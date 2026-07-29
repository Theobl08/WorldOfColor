package net.theobl.worldofcolor.util;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColorCollection;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public final class ColorCollectionUtil {

    public static <B extends Block, Id> ColorCollection<DeferredBlock<B>> registerBlocks(
            ColorCollection<Id> ids,
            TriFunction<Id, Function<BlockBehaviour.Properties, B>, Supplier<BlockBehaviour.Properties>, DeferredBlock<B>> register,
            BiFunction<DyeColor, BlockBehaviour.Properties, B> colorBlockFactory,
            Function<DyeColor, BlockBehaviour.Properties> propertiesSupplier
    ) {
        return ColorCollection.zipMap(ColorCollection.VALUES, ids, (color, id) -> register.apply(id, p -> colorBlockFactory.apply(color, p), () -> propertiesSupplier.apply(color)));
    }

    public static <H extends DeferredHolder<?, ?>, Id> ColorCollection<H> register(ColorCollection<Id> ids, BiFunction<Id, DyeColor, H> factory) {
        return ColorCollection.zipMap(ColorCollection.VALUES, ids, (color, id) -> factory.apply(id, color));
    }
}
