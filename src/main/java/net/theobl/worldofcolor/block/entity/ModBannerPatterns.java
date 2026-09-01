package net.theobl.worldofcolor.block.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.theobl.worldofcolor.WorldOfColor;

public class ModBannerPatterns {
    public static final ResourceKey<BannerPattern> BOTTOM_LEFT = create("bottom_left");
    public static final ResourceKey<BannerPattern> BOTTOM_RIGHT = create("bottom_right");
    public static final ResourceKey<BannerPattern> TOP_LEFT = create("top_left");
    public static final ResourceKey<BannerPattern> TOP_RIGHT = create("top_right");

    private static ResourceKey<BannerPattern> create(String id) {
        return ResourceKey.create(Registries.BANNER_PATTERN, WorldOfColor.asResource(id));
    }

    public static void bootstrap(BootstrapContext<BannerPattern> context) {
        BannerPatterns.register(context, BOTTOM_LEFT);
        BannerPatterns.register(context, BOTTOM_RIGHT);
        BannerPatterns.register(context, TOP_LEFT);
        BannerPatterns.register(context, TOP_RIGHT);
    }
}
