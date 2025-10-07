package net.theobl.worldofcolor.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.CopperGolemStatueBlock;
import net.minecraft.world.level.block.WeatheringCopper;

public class ColoredCopperGolemStatueBlock extends CopperGolemStatueBlock {
    private final DyeColor color;

    public ColoredCopperGolemStatueBlock(WeatheringCopper.WeatherState weatheringState, DyeColor color, Properties properties) {
        super(weatheringState, properties);
        this.color = color;
    }

    public DyeColor getColor() {
        return color;
    }
}
