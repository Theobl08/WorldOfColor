package net.theobl.worldofcolor.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.theobl.worldofcolor.WorldOfColor;

import java.util.ArrayList;
import java.util.List;

import static net.theobl.worldofcolor.util.ModUtil.*;

public class ModWoodType {
    public static final List<WoodType> COLORED_WOODS = registerColoredWoodType();

    public static List<WoodType> registerColoredWoodType() {
        List<WoodType> woodTypes = new ArrayList<>();
        for (DyeColor color : COLORS) {
            WoodType type = WoodType.register(new WoodType(WorldOfColor.MODID + ":" + color.getName(), BlockSetType.OAK));
            woodTypes.add(type);
        }
        return woodTypes;
    }
}
