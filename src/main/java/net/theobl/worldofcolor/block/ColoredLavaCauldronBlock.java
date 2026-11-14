package net.theobl.worldofcolor.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.LavaCauldronBlock;

public class ColoredLavaCauldronBlock extends LavaCauldronBlock implements ColoredBlock {
    private final DyeColor color;
    public ColoredLavaCauldronBlock( DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    @Override
    public DyeColor getColor() {
        return color;
    }
}
