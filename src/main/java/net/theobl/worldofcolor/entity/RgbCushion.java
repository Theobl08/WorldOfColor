package net.theobl.worldofcolor.entity;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Cushion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.item.ModItems;

public class RgbCushion extends AbstractCushion {
    public RgbCushion(EntityType<Cushion> type, Level level) {
        super(type, level);
    }

    @Override
    protected Holder<Block> getAssociatedWoolBlock() {
        return ModBlocks.RGB_WOOL;
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(ModItems.RGB_CUSHION.get());
    }
}
