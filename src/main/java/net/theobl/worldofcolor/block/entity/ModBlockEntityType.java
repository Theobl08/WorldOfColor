package net.theobl.worldofcolor.block.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

public class ModBlockEntityType {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, WorldOfColor.MODID);

    public static final Supplier<BlockEntityType<ColoredDecoratedPotBlockEntity>> COLORED_DECORATED_POT =
            BLOCK_ENTITY_TYPES.register("colored_decorated_pot",
                    () -> new BlockEntityType<>(ColoredDecoratedPotBlockEntity::new, ModUtil.asVarArgs(ModBlocks.COLORED_DECORATED_POTS)));

    public static final Supplier<BlockEntityType<ColoredBannerBlockEntity>> RGB_BANNER =
            BLOCK_ENTITY_TYPES.register("rgb_banner",
                    () -> new BlockEntityType<>(ColoredBannerBlockEntity::new, ModBlocks.RGB_BANNER.get(), ModBlocks.RGB_WALL_BANNER.get()));

    public static final Supplier<BlockEntityType<DyedWaterCauldronBlockEntity>> DYED_WATER_CAULDRON =
            register("dyed_water_cauldron",
            DyedWaterCauldronBlockEntity::new,
            ModBlocks.DYED_WATER_CAULDRON,
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.getFirst(),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(1),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(2),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(3),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(4),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(5),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(6),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(7),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(8),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(9),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(10),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(11),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(12),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(13),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(14),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(15)
        );

    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, DeferredBlock<?>... validBlocks) {
        return BLOCK_ENTITY_TYPES.register(name,
                () -> new BlockEntityType<>(factory, Arrays.stream(validBlocks).map(DeferredBlock::get).toArray(Block[]::new)));
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
