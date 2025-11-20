package net.theobl.worldofcolor.datagen;

import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.AtlasIds;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.data.SpriteSourceProvider;
import net.neoforged.neoforge.client.textures.NamespacedDirectoryLister;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.concurrent.CompletableFuture;

public class ModSpriteSourceProvider extends SpriteSourceProvider {
    public ModSpriteSourceProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, WorldOfColor.MODID);
    }

    @Override
    protected void gather() {
        //atlas(BLOCKS_ATLAS).addSource(new NamespacedDirectoryLister(WorldOfColor.MODID, "entity/decorated_pot/side", "entity/decorated_pot/side/"));
        ModUtil.COLORS.forEach(color -> atlas(AtlasIds.BLOCKS).addSource(new SingleFile(WorldOfColor.asResource("entity/decorated_pot/decorated_pot_side_" + color.getName()))));
        atlas(AtlasIds.BLOCKS).addSource(new SingleFile(WorldOfColor.asResource("entity/shulker/shulker_rgb")));
        atlas(AtlasIds.BLOCKS).addSource(new SingleFile(WorldOfColor.asResource("entity/bed/rgb")));
    }
}
