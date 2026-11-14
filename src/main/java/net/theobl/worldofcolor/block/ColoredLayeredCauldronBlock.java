package net.theobl.worldofcolor.block;

import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.LayeredCauldronBlock;

public class ColoredLayeredCauldronBlock extends LayeredCauldronBlock implements ColoredBlock {
    private final DyeColor color;
    public ColoredLayeredCauldronBlock(Biome.Precipitation precipitationType, CauldronInteraction.Dispatcher interactionMap, DyeColor color, Properties properties) {
        super(precipitationType, interactionMap, properties);
        this.color = color;
    }

    @Override
    public DyeColor getColor() {
        return color;
    }
}
