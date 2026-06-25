package net.theobl.worldofcolor.block;

import net.minecraft.world.level.block.ColorCollection;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.theobl.worldofcolor.WorldOfColor;

public class ModWoodType {
    public static final ColorCollection<WoodType> COLORED_WOODS = ColorCollection.NAMES.map(color -> WoodType.register(new WoodType(WorldOfColor.MODID + ":" + color, BlockSetType.OAK)));
}
