package net.theobl.worldofcolor.block.grower;

import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.ColorCollection;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.worldgen.ModTreeFeatures;

import java.util.*;

public class ModTreeGrower {
    public static final ColorCollection<TreeGrower> COLORED_TREES = ColorCollection.VALUES
            .map(color -> new TreeGrower(WorldOfColor.MODID + ":" + color.getName(), WeightedList.of(ModTreeFeatures.COLORED_TREES.pick(color)), WeightedList.of(), WeightedList.of(), ModTreeFeatures.COLORED_TREES.pick(color)));
}
