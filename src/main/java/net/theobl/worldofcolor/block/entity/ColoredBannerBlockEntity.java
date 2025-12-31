package net.theobl.worldofcolor.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
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

    public ItemStack getItem(ItemLike itemLike) {
        ItemStack itemstack = new ItemStack(itemLike);
        itemstack.applyComponents(this.collectComponents());
        return itemstack;
    }
}
