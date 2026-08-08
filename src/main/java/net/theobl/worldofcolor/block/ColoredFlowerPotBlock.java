package net.theobl.worldofcolor.block;

import net.minecraft.references.BlockItemIds;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.theobl.worldofcolor.util.ModUtil;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ColoredFlowerPotBlock extends FlowerPotBlock {
    private final DyeColor color;

    public ColoredFlowerPotBlock(@Nullable Supplier<FlowerPotBlock> emptyPot, DeferredBlock<? extends Block> flower, DyeColor color, Properties properties) {
        super(emptyPot, flower, properties);
        this.color = color;
        if(emptyPot != null) {
            emptyPot.get().addPlant(flower.getId(), () -> this);
        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.is(ModBlocks.COLORED_POTTED_PLANTS.get(BlockItemIds.OPEN_EYEBLOSSOM.block()).pick(this.color))
                || state.is(ModBlocks.COLORED_POTTED_PLANTS.get(BlockItemIds.CLOSED_EYEBLOSSOM.block()).pick(this.color));
    }

    public BlockState opposite(BlockState state) {
        if (state.is(ModBlocks.COLORED_POTTED_PLANTS.get(BlockItemIds.OPEN_EYEBLOSSOM.block()).pick(this.color))) {
            return ModBlocks.COLORED_POTTED_PLANTS.get(BlockItemIds.CLOSED_EYEBLOSSOM.block()).pick(this.color).get().defaultBlockState();
        } else {
            return state.is(ModBlocks.COLORED_POTTED_PLANTS.get(BlockItemIds.CLOSED_EYEBLOSSOM.block()).pick(this.color))
                    ? ModBlocks.COLORED_POTTED_PLANTS.get(BlockItemIds.OPEN_EYEBLOSSOM.block()).pick(this.color).get().defaultBlockState()
                    : state;
        }
    }
}
