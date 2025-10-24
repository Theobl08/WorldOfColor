package net.theobl.worldofcolor.block.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.function.Supplier;

public class ModBlockEntityType {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, WorldOfColor.MODID);

    public static final Supplier<BlockEntityType<ColoredDecoratedPotBlockEntity>> COLORED_DECORATED_POT =
            BLOCK_ENTITY_TYPES.register("colored_decorated_pot",
                    () -> new BlockEntityType<>(ColoredDecoratedPotBlockEntity::new, ModUtil.asVarArgs(ModBlocks.COLORED_DECORATED_POTS)));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
