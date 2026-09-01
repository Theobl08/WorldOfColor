package net.theobl.worldofcolor.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.entity.ModEntityType;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public ModEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, WorldOfColor.MODID);
    }

    @Override
    protected IntrinsicHolderTagAppender<EntityType<?>> tag(TagKey<EntityType<?>> tag) {
        return new IntrinsicHolderTagAppender<>(super.tag(tag));
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(EntityTypeTags.BOAT).addAll(ModEntityType.COLORED_BOATS);
        tag(Tags.EntityTypes.BOATS).addAll(ModEntityType.COLORED_CHEST_BOATS);
        tag(Tags.EntityTypes.ITEM_FRAMES).addAll(ModEntityType.COLORED_ITEM_FRAMES);
    }
}
