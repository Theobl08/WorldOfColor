package net.theobl.worldofcolor.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.WeatheringCopperGolemStatueBlock;

public class ColoredWeatheringCopperGolemStatueBlock extends WeatheringCopperGolemStatueBlock {
    private final DyeColor color;

    public ColoredWeatheringCopperGolemStatueBlock(WeatherState weatherState, DyeColor color, Properties properties) {
        super(weatherState, properties);
        this.color = color;
    }

    public DyeColor getColor() {
        return color;
    }
}
