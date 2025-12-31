package net.theobl.worldofcolor.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.worldofcolor.block.entity.ColoredBannerBlockEntity;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ColoredBannerBlock extends BannerBlock {
    public ColoredBannerBlock(Properties properties) {
        super(DyeColor.WHITE, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ColoredBannerBlockEntity(pos, state);
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        if (level.getBlockEntity(pos) instanceof ColoredBannerBlockEntity bannerBlockEntity) {
            return bannerBlockEntity.getItem(this);
        } else {
            return new ItemStack(this);
        }
    }
}
