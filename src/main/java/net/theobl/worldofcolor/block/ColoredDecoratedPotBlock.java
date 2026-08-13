package net.theobl.worldofcolor.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.worldofcolor.block.entity.ColoredDecoratedPotBlockEntity;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class ColoredDecoratedPotBlock extends DecoratedPotBlock {
    private final DyeColor color;
    public ColoredDecoratedPotBlock(DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    public DyeColor getColor() {
        return color;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ColoredDecoratedPotBlockEntity(pos, state);
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        if (level.getBlockEntity(pos) instanceof ColoredDecoratedPotBlockEntity blockEntity) {
            PotDecorations potdecorations = blockEntity.getDecorations();
            return ColoredDecoratedPotBlockEntity.createDecoratedPotInstance(potdecorations, this.color);
        } else {
            return super.getCloneItemStack(level, pos, state, includeData);
        }
    }
}
