package net.theobl.worldofcolor.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.theobl.worldofcolor.util.ModUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class ColoredBlockList<T extends Block> implements Iterable<DeferredBlock<T>> {
    private static final int COLOR_AMOUNT = DyeColor.values().length;

    private final DeferredBlock<?>[] values = new DeferredBlock<?>[COLOR_AMOUNT];

    public ColoredBlockList(Function<DyeColor, DeferredBlock<? extends Block>> filler) {
        for (DyeColor color : DyeColor.values()) {
            values[ModUtil.COLORS.indexOf(color)] = filler.apply(color);
        }
    }

    @SuppressWarnings("unchecked")
    public DeferredBlock<T> get(DyeColor color) {
        return (DeferredBlock<T>) values[ModUtil.COLORS.indexOf(color)];
    }

    public Block getBlock(DyeColor color) {
        return get(color).get();
    }

    @SuppressWarnings("unchecked")
    public DeferredBlock<T> get(int index) {
        return (DeferredBlock<T>) values[index];
    }

    public boolean contains(Block block) {
        for (DeferredBlock<?> entry : values) {
            if (entry.get() == block) {
                return true;
            }
        }
        return false;
    }

    public int indexOf(DeferredBlock<T> block) {
        return toList().indexOf(block);
    }

    @SuppressWarnings("unchecked")
    public DeferredBlock<T>[] toArray() {
        return (DeferredBlock<T>[]) Arrays.copyOf(values, values.length);
    }

    public List<DeferredBlock<T>> toList() {
        return Arrays.stream(toArray()).toList();
    }

    @Override
    public @NotNull Iterator<DeferredBlock<T>> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < values.length;
            }

            @SuppressWarnings("unchecked")
            @Override
            public DeferredBlock<T> next() {
                if(!hasNext())
                    throw new NoSuchElementException();
                return (DeferredBlock<T>) values[index++];
            }
        };
    }
}
