package net.theobl.worldofcolor;

import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.world.item.DyeColor;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.minecraft.client.renderer.Sheets.*;

public class ModMaterial {
    public static final Map<DyeColor, SpriteId> COPPER_CHEST_LOCATION = new LinkedHashMap<>();
    public static final Map<DyeColor, SpriteId> COPPER_CHEST_LOCATION_LEFT = new LinkedHashMap<>();
    public static final Map<DyeColor, SpriteId> COPPER_CHEST_LOCATION_RIGHT = new LinkedHashMap<>();
    public static final SpriteId RGB_SHULKER_LOCATION = SHULKER_MAPPER.apply(WorldOfColor.asResource("shulker_rgb"));
    public static final SpriteId RGB_BED_TEXTURE = BED_MAPPER.apply(WorldOfColor.asResource("rgb"));
    public static final SpriteId BANNER_RGB = BANNER_MAPPER.apply(WorldOfColor.asResource("rgb"));

    public static void bootstrap() {
        for (DyeColor color : ModUtil.COLORS) {
            COPPER_CHEST_LOCATION.put(color, CHEST_MAPPER.apply(WorldOfColor.asResource("copper_" + color.getName())));
            COPPER_CHEST_LOCATION_LEFT.put(color, CHEST_MAPPER.apply(WorldOfColor.asResource("copper_" + color.getName() + "_left")));
            COPPER_CHEST_LOCATION_RIGHT.put(color, CHEST_MAPPER.apply(WorldOfColor.asResource("copper_" + color.getName() + "_right")));
        }
    }
}
