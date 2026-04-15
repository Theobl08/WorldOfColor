package net.theobl.worldofcolor.block.grower;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.util.ModUtil;
import net.theobl.worldofcolor.worldgen.ModTreeFeatures;

import java.util.*;

public class ModTreeGrower {
    public static final Map<DyeColor, TreeGrower> COLORED_TREES = registerColoredTrees();

    private static Map<DyeColor, TreeGrower> registerColoredTrees() {
        Map<DyeColor, TreeGrower> treeGrowers = new LinkedHashMap<>();
        for(DyeColor color : ModUtil.COLORS) {
            int index = ModUtil.COLORS.indexOf(color);
            TreeGrower grower = new TreeGrower(WorldOfColor.MODID + ":" + color.getName(), Optional.empty(), Optional.of(ModTreeFeatures.COLORED_TREES.get(color)), Optional.empty());
            treeGrowers.put(color, grower);
        }
        return treeGrowers;
    }
}
