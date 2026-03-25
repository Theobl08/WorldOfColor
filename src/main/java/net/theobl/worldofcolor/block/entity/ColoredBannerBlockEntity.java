package net.theobl.worldofcolor.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
public class ColoredBannerBlockEntity extends BannerBlockEntity {
    public ColoredBannerBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntityType.RGB_BANNER.get();
    }
}
