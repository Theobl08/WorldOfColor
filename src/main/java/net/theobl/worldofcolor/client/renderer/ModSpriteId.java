package net.theobl.worldofcolor.client.renderer;

import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.world.level.block.ColorCollection;
import net.theobl.worldofcolor.WorldOfColor;

import static net.minecraft.client.renderer.Sheets.*;

public class ModSpriteId {
    public static final ColorCollection<SpriteId> COPPER_CHEST_LOCATION = ColorCollection.NAMES
            .map(color -> CHEST_MAPPER.apply(WorldOfColor.asResource("copper_" + color)));
    public static final ColorCollection<SpriteId> COPPER_CHEST_LOCATION_LEFT = ColorCollection.NAMES
            .map(color -> CHEST_MAPPER.apply(WorldOfColor.asResource("copper_" + color + "_left")));
    public static final ColorCollection<SpriteId> COPPER_CHEST_LOCATION_RIGHT = ColorCollection.NAMES
            .map(color -> CHEST_MAPPER.apply(WorldOfColor.asResource("copper_" + color + "_right")));
    public static final SpriteId RGB_SHULKER_LOCATION = SHULKER_MAPPER.apply(WorldOfColor.asResource("shulker_rgb"));
    public static final SpriteId BANNER_RGB = BANNER_MAPPER.apply(WorldOfColor.asResource("rgb"));

    public static void bootstrap() {

    }
}
