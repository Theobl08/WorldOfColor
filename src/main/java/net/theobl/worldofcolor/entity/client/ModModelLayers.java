package net.theobl.worldofcolor.entity.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.entity.ModBoat;

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
                    WorldOfColor.asResource((hasChest ? "chest_boat/" : "boat/") + color.getName()), "main");
            boat.add(layerLocation);
        }
        return boat;
    }

    private static ModelLayerLocation createLocation(String pPath, String pModel) {
        return new ModelLayerLocation(WorldOfColor.asResource(pPath), pModel);
    }

    public static ModelLayerLocation createRaftModelName(ModBoat.Type pType) {
        return createLocation("raft/" + pType.getName(), "main");
    }

    public static ModelLayerLocation createChestRaftModelName(ModBoat.Type pType) {
        return createLocation("chest_raft/" + pType.getName(), "main");
    }

    public static ModelLayerLocation createBoatModelName(ModBoat.Type pType) {
        return createLocation("boat/" + pType.getName(), "main");
    }

    public static ModelLayerLocation createChestBoatModelName(ModBoat.Type pType) {
        return createLocation("chest_boat/" + pType.getName(), "main");
    }
}
