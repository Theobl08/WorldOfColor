package net.theobl.worldofcolor.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.neoforged.neoforge.common.Tags;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.entity.ModEntityType;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public ModEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, WorldOfColor.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        ModEntityType.COLORED_BOATS.forEach(boat -> tag(Tags.EntityTypes.BOATS).add(boat.get()));
        ModEntityType.COLORED_CHEST_BOATS.forEach(boat -> tag(Tags.EntityTypes.BOATS).add(boat.get()));
    }
}
