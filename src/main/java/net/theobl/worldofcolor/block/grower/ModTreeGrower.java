package net.theobl.worldofcolor.block.grower;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.util.ModUtil;
import net.theobl.worldofcolor.worldgen.ModTreeFeatures;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModTreeGrower {
    public static final List<TreeGrower> COLORED_TREES = registerColoredTrees();

    private static List<TreeGrower> registerColoredTrees() {
        List<TreeGrower> treeGrowers = new ArrayList<>();
        for(DyeColor color : ModUtil.COLORS) {
            int index = ModUtil.COLORS.indexOf(color);
            TreeGrower grower = new TreeGrower(WorldOfColor.MODID + ":" + color.getName(), Optional.empty(), Optional.of(ModTreeFeatures.COLORED_TREES.get(index)), Optional.empty());
            treeGrowers.add(grower);
        }
        return treeGrowers;
    }
}
