package net.theobl.worldofcolor.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.theobl.worldofcolor.block.entity.DyedWaterLiquidBlockEntity;

public class DyedWaterBucketItem extends BucketItem {
    public DyedWaterBucketItem(Fluid content, Properties properties) {
        super(content, properties);
    }

    @Override
    public void checkExtraContent(LivingEntity user, Level level, ItemStack containerItem, BlockPos pos) {
        DyedItemColor color = containerItem.get(DataComponents.DYED_COLOR);
        if(level.getBlockEntity(pos) instanceof DyedWaterLiquidBlockEntity blockEntity && color != null) {
            blockEntity.setColor(color);
        }
    }
}
