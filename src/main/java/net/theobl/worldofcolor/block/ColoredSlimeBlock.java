package net.theobl.worldofcolor.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ColoredSlimeBlock extends SlimeBlock {
    private final DyeColor color;

    public ColoredSlimeBlock(DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    @Override
    public boolean isSlimeBlock(BlockState state) {
        return true;
    }

    @Override
    public boolean isStickyBlock(BlockState state) {
        return true;
    }

    @Override
    public boolean canStickTo(BlockState state, BlockState other) {
        if(other.is(Blocks.HONEY_BLOCK)) {
            return false;
        }
        else if(other.getBlock() instanceof ColoredSlimeBlock slimeBlock && slimeBlock.color != this.color)
            return false;
        return super.canStickTo(state, other);
    }
}
