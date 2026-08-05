package net.theobl.worldofcolor.block.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;

import java.util.Arrays;
import java.util.function.Supplier;

public class ModBlockEntityType {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, WorldOfColor.MODID);

    public static final Supplier<BlockEntityType<ColoredDecoratedPotBlockEntity>> COLORED_DECORATED_POT = register(
            "colored_decorated_pot",
            ColoredDecoratedPotBlockEntity::new,
            ModBlocks.COLORED_DECORATED_POTS.asList().toArray(DeferredBlock[]::new)
    );

    public static final Supplier<BlockEntityType<ColoredBannerBlockEntity>> RGB_BANNER = register(
            "rgb_banner",
            ColoredBannerBlockEntity::new,
            ModBlocks.RGB_BANNER,
            ModBlocks.RGB_WALL_BANNER
    );

    public static final Supplier<BlockEntityType<DyedWaterCauldronBlockEntity>> DYED_WATER_CAULDRON = register(
            "dyed_water_cauldron",
            DyedWaterCauldronBlockEntity::new,
            Util.copyAndAdd(ModBlocks.COLORED_DYED_WATER_CAULDRONS.asList(), ModBlocks.DYED_WATER_CAULDRON).toArray(DeferredBlock[]::new)
        );

    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, DeferredBlock<?>... validBlocks) {
        return BLOCK_ENTITY_TYPES.register(name,
                () -> new BlockEntityType<>(factory, Arrays.stream(validBlocks).map(DeferredBlock::get).toArray(Block[]::new)));
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
