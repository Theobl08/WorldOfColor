package net.theobl.worldofcolor.block;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.theobl.worldofcolor.WorldOfColor;

import java.util.*;

import static net.theobl.worldofcolor.util.ModUtil.*;

public class ModWoodType {
    public static final Map<DyeColor, WoodType> COLORED_WOODS = registerColoredWoodType();

    public static Map<DyeColor, WoodType> registerColoredWoodType() {
        Map<DyeColor, WoodType> woodTypes = new LinkedHashMap<>();
        for (DyeColor color : COLORS) {
            WoodType type = WoodType.register(new WoodType(WorldOfColor.MODID + ":" + color.getName(), BlockSetType.OAK));
            woodTypes.put(color, type);
        }
        return woodTypes;
    }
}
