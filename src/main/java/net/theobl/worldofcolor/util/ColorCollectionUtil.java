package net.theobl.worldofcolor.util;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColorCollection;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.BiConsumer;
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

    public static <T> void progressMapping(ColorCollection<T> collection, BiConsumer<T, T> consumer) {
        consumer.accept(collection.white(), collection.lightGray());
        consumer.accept(collection.lightGray(), collection.gray());
        consumer.accept(collection.gray(), collection.black());
        consumer.accept(collection.black(), collection.brown());
        consumer.accept(collection.brown(), collection.red());
        consumer.accept(collection.red(), collection.orange());
        consumer.accept(collection.orange(), collection.yellow());
        consumer.accept(collection.yellow(), collection.lime());
        consumer.accept(collection.lime(), collection.green());
        consumer.accept(collection.green(), collection.cyan());
        consumer.accept(collection.cyan(), collection.lightBlue());
        consumer.accept(collection.lightBlue(), collection.blue());
        consumer.accept(collection.blue(), collection.purple());
        consumer.accept(collection.purple(), collection.magenta());
        consumer.accept(collection.magenta(), collection.pink());
    }
}
