package net.theobl.worldofcolor;

import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.DyeColor;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.client.renderer.Sheets.*;

public class ModMaterial {
    public static final List<Material> COPPER_CHEST_LOCATION = new ArrayList<>();
    public static final List<Material> COPPER_CHEST_LOCATION_LEFT = new ArrayList<>();
    public static final List<Material> COPPER_CHEST_LOCATION_RIGHT = new ArrayList<>();
    public static final Material RGB_SHULKER_LOCATION = SHULKER_MAPPER.apply(WorldOfColor.asResource("shulker_rgb"));
    public static final Material RGB_BED_TEXTURE = BED_MAPPER.apply(WorldOfColor.asResource("rgb"));

    public static void bootstrap() {
        for (DyeColor color : ModUtil.COLORS) {
            COPPER_CHEST_LOCATION.add(CHEST_MAPPER.apply(WorldOfColor.asResource("copper_" + color.getName())));
            COPPER_CHEST_LOCATION_LEFT.add(CHEST_MAPPER.apply(WorldOfColor.asResource("copper_" + color.getName() + "_left")));
            COPPER_CHEST_LOCATION_RIGHT.add(CHEST_MAPPER.apply(WorldOfColor.asResource("copper_" + color.getName() + "_right")));
        }
    }
}
