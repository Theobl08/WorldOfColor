package net.theobl.worldofcolor.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.fluids.ModFluids;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagsProvider extends FluidTagsProvider {
    public ModFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, WorldOfColor.MODID);
    }

    @Override
    protected IntrinsicHolderTagAppender<Fluid> tag(TagKey<Fluid> tag) {
        return new IntrinsicHolderTagAppender<>(super.tag(tag));
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        this.tag(FluidTags.WATER).add(ModFluids.DYED_WATER, ModFluids.FLOWING_DYED_WATER);
    }
}
