package net.theobl.worldofcolor.entity;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Cushion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.item.ModItems;

public class MissingnoCushion extends AbstractCushion {
    public MissingnoCushion(EntityType<Cushion> type, Level level) {
        super(type, level);
    }

    @Override
    protected Holder<Block> getAssociatedWoolBlock() {
        return ModBlocks.MISSINGNO_WOOL;
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(ModItems.MISSINGNO_CUSHION.get());
    }
}
