package net.theobl.worldofcolor.entity.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.item.DyeColor;
import net.theobl.worldofcolor.WorldOfColor;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.theobl.worldofcolor.util.ModUtil.*;

public class ModModelLayers {
    public static final Map<DyeColor, ModelLayerLocation> COLORED_BOATS = registerColoredBoatLayers(false);
    public static final Map<DyeColor, ModelLayerLocation> COLORED_CHEST_BOATS = registerColoredBoatLayers(true);

    private static Map<DyeColor, ModelLayerLocation> registerColoredBoatLayers(boolean hasChest) {
        Map<DyeColor, ModelLayerLocation> boat = new LinkedHashMap<>();
        for (DyeColor color : COLORS) {
            ModelLayerLocation layerLocation = new ModelLayerLocation(
                    WorldOfColor.asResource((hasChest ? "chest_boat/" : "boat/") + color.getName()), "main");
            boat.put(color, layerLocation);
        }
        return boat;
    }
}
