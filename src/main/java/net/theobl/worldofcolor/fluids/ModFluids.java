package net.theobl.worldofcolor.fluids;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.item.ModItems;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, WorldOfColor.MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, WorldOfColor.MODID);

    public static final DeferredHolder<FluidType, FluidType> DYED_WATER_TYPE = FLUID_TYPES.register(
            "dyed_water",
            () -> new FluidType(
                    FluidType.Properties.create()
                            .descriptionId(ModBlocks.DYED_WATER.get().getDescriptionId())
                            .fallDistanceModifier(0F)
                            .canExtinguish(true)
                            .canConvertToSource(true)
                            .supportsBoating(true)
                            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                            .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
                            .canHydrate(true)
                            .isWaterLike(true)
            )
    );
    public static final DeferredHolder<Fluid, DyedWaterFluid.Source> DYED_WATER = FLUIDS.register(
            "dyed_water", () -> new DyedWaterFluid.Source(ModFluids.DYED_WATER_PROPERTIES)
    );
    public static final DeferredHolder<Fluid, DyedWaterFluid.Flowing> FLOWING_DYED_WATER = FLUIDS.register(
            "flowing_dyed_water", () -> new DyedWaterFluid.Flowing(ModFluids.DYED_WATER_PROPERTIES)
    );
    public static final BaseFlowingFluid.Properties DYED_WATER_PROPERTIES =
            new BaseFlowingFluid.Properties(DYED_WATER_TYPE, DYED_WATER, FLOWING_DYED_WATER)
                    .block(ModBlocks.DYED_WATER)
                    .bucket(ModItems.DYED_WATER_BUCKET);
    public static final DeferredHolder<FluidType, FluidType> RGB_WATER_TYPE = FLUID_TYPES.register(
            "rgb_water",
            () -> new FluidType(
                    FluidType.Properties.create()
                            .descriptionId(ModBlocks.RGB_WATER.get().getDescriptionId())
                            .fallDistanceModifier(0F)
                            .canExtinguish(true)
                            .canConvertToSource(true)
                            .supportsBoating(true)
                            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                            .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
                            .canHydrate(true)
                            .isWaterLike(true)
            )
    );
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Source> RGB_WATER = FLUIDS.register(
            "rgb_water", () -> new BaseFlowingFluid.Source(ModFluids.RGB_WATER_PROPERTIES)
    );
    public static final DeferredHolder<Fluid, BaseFlowingFluid.Flowing> FLOWING_RGB_WATER = FLUIDS.register(
            "flowing_rgb_water", () -> new BaseFlowingFluid.Flowing(ModFluids.RGB_WATER_PROPERTIES)
    );
    public static final BaseFlowingFluid.Properties RGB_WATER_PROPERTIES =
            new BaseFlowingFluid.Properties(RGB_WATER_TYPE, RGB_WATER, FLOWING_RGB_WATER)
                    .block(ModBlocks.RGB_WATER)
                    .bucket(ModItems.RGB_WATER_BUCKET);

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }
}
