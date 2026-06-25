package net.theobl.worldofcolor.client.model.geom;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.ColorCollection;
import net.theobl.worldofcolor.WorldOfColor;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.theobl.worldofcolor.util.ModUtil.*;

public class ModModelLayers {
    public static final ColorCollection<ModelLayerLocation> COLORED_BOATS = ColorCollection.NAMES
            .map(color -> new ModelLayerLocation(WorldOfColor.asResource("boat/" + color), "main"));
    public static final ColorCollection<ModelLayerLocation> COLORED_CHEST_BOATS = ColorCollection.NAMES
            .map(color -> new ModelLayerLocation(WorldOfColor.asResource("chest_boat/" + color), "main"));
}
