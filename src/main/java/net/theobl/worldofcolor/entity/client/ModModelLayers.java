package net.theobl.worldofcolor.entity.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.theobl.worldofcolor.WorldOfColor;

import java.util.ArrayList;
import java.util.List;

import static net.theobl.worldofcolor.util.ModUtil.*;

public class ModModelLayers {
    public static final List<ModelLayerLocation> COLORED_BOATS = registerColoredBoatLayers(false);
    public static final List<ModelLayerLocation> COLORED_CHEST_BOATS = registerColoredBoatLayers(true);

    private static List<ModelLayerLocation> registerColoredBoatLayers(boolean hasChest) {
        List<ModelLayerLocation> boat = new ArrayList<>();
        for (DyeColor color : COLORS) {
            ModelLayerLocation layerLocation = new ModelLayerLocation(
                    ResourceLocation.fromNamespaceAndPath(WorldOfColor.MODID,(hasChest ? "chest_boat/" : "boat/") + color.getName()), "main");
            boat.add(layerLocation);
        }
        return boat;
    }
}
