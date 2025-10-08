package net.theobl.worldofcolor;

import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.client.renderer.Sheets.CHEST_MAPPER;

public class ModMaterial {
    public static final List<Material> COPPER_CHEST_LOCATION = new ArrayList<>();
    public static final List<Material> COPPER_CHEST_LOCATION_LEFT = new ArrayList<>();
    public static final List<Material> COPPER_CHEST_LOCATION_RIGHT = new ArrayList<>();

    public static void bootstrap() {
        for (DyeColor color : ModUtil.COLORS) {
            COPPER_CHEST_LOCATION.add(CHEST_MAPPER.apply(ResourceLocation.fromNamespaceAndPath(WorldOfColor.MODID, "copper_" + color.getName())));
            COPPER_CHEST_LOCATION_LEFT.add(CHEST_MAPPER.apply(ResourceLocation.fromNamespaceAndPath(WorldOfColor.MODID, "copper_" + color.getName() + "_left")));
            COPPER_CHEST_LOCATION_RIGHT.add(CHEST_MAPPER.apply(ResourceLocation.fromNamespaceAndPath(WorldOfColor.MODID,"copper_" + color.getName() + "_right")));
        }
    }
}
