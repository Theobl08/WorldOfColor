package net.theobl.worldofcolor.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.worldofcolor.util.ModUtil;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ColoredFlowerPotBlock extends FlowerPotBlock {
    private final DyeColor color;

    public ColoredFlowerPotBlock(@Nullable Supplier<FlowerPotBlock> emptyPot, Supplier<? extends Block> flower, DyeColor color, Properties properties) {
        super(emptyPot, flower, properties);
        this.color = color;
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        int index = ModUtil.COLORS.indexOf(this.color);
        return state.is(ModBlocks.COLORED_POTTED_PLANTS.get(ModUtil.OPEN_EYEBLOSSOM).get(index))
                || state.is(ModBlocks.COLORED_POTTED_PLANTS.get(ModUtil.CLOSED_EYEBLOSSOM).get(index));
    }

    public BlockState opposite(BlockState state) {
        int index = ModUtil.COLORS.indexOf(this.color);
        if (state.is(ModBlocks.COLORED_POTTED_PLANTS.get(ModUtil.OPEN_EYEBLOSSOM).get(index))) {
            return ModBlocks.COLORED_POTTED_PLANTS.get(ModUtil.CLOSED_EYEBLOSSOM).get(index).get().defaultBlockState();
        } else {
            return state.is(ModBlocks.COLORED_POTTED_PLANTS.get(ModUtil.CLOSED_EYEBLOSSOM).get(index))
                    ? ModBlocks.COLORED_POTTED_PLANTS.get(ModUtil.OPEN_EYEBLOSSOM).get(index).get().defaultBlockState()
                    : state;
        }
    }
}
