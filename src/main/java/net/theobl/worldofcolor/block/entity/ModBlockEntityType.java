package net.theobl.worldofcolor.block.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.Arrays;
import java.util.function.Supplier;

public class ModBlockEntityType {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, WorldOfColor.MODID);

    public static final Supplier<BlockEntityType<ColoredDecoratedPotBlockEntity>> COLORED_DECORATED_POT =
            BLOCK_ENTITY_TYPES.register("colored_decorated_pot",
                    () -> new BlockEntityType<>(ColoredDecoratedPotBlockEntity::new, ModUtil.asVarArgs(ModBlocks.COLORED_DECORATED_POTS.values().stream().toList())));

    public static final Supplier<BlockEntityType<ColoredBannerBlockEntity>> RGB_BANNER =
            BLOCK_ENTITY_TYPES.register("rgb_banner",
                    () -> new BlockEntityType<>(ColoredBannerBlockEntity::new, ModBlocks.RGB_BANNER.get(), ModBlocks.RGB_WALL_BANNER.get()));

    public static final Supplier<BlockEntityType<DyedWaterCauldronBlockEntity>> DYED_WATER_CAULDRON =
            register("dyed_water_cauldron",
            DyedWaterCauldronBlockEntity::new,
            ModBlocks.DYED_WATER_CAULDRON,
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(DyeColor.WHITE),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(DyeColor.LIGHT_GRAY),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(DyeColor.GRAY),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(DyeColor.BLACK),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(DyeColor.BROWN),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(DyeColor.RED),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(DyeColor.ORANGE),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(DyeColor.YELLOW),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(DyeColor.LIME),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(DyeColor.GREEN),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(DyeColor.CYAN),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(DyeColor.LIGHT_BLUE),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(DyeColor.BLUE),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(DyeColor.PURPLE),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(DyeColor.MAGENTA),
            ModBlocks.COLORED_DYED_WATER_CAULDRONS.get(DyeColor.PINK)
        );

    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, DeferredBlock<?>... validBlocks) {
        return BLOCK_ENTITY_TYPES.register(name,
                () -> new BlockEntityType<>(factory, Arrays.stream(validBlocks).map(DeferredBlock::get).toArray(Block[]::new)));
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
