package net.theobl.worldofcolor.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;

public class ModPoiTypes {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, WorldOfColor.MODID);

    public static final Holder<PoiType> COLORED_LIGHTNING_RODS = POI_TYPES.register("test",
            () -> new PoiType(ImmutableList.of(ModBlocks.COLORED_LIGHTNING_RODS.getFirst(),
                            ModBlocks.COLORED_LIGHTNING_RODS.get(1),
                            ModBlocks.COLORED_LIGHTNING_RODS.get(2),
                            ModBlocks.COLORED_LIGHTNING_RODS.get(3),
                            ModBlocks.COLORED_LIGHTNING_RODS.get(4),
                            ModBlocks.COLORED_LIGHTNING_RODS.get(5),
                            ModBlocks.COLORED_LIGHTNING_RODS.get(6),
                            ModBlocks.COLORED_LIGHTNING_RODS.get(7),
                            ModBlocks.COLORED_LIGHTNING_RODS.get(8),
                            ModBlocks.COLORED_LIGHTNING_RODS.get(9),
                            ModBlocks.COLORED_LIGHTNING_RODS.get(10),
                            ModBlocks.COLORED_LIGHTNING_RODS.get(11),
                            ModBlocks.COLORED_LIGHTNING_RODS.get(12),
                            ModBlocks.COLORED_LIGHTNING_RODS.get(13),
                            ModBlocks.COLORED_LIGHTNING_RODS.get(14),
                            ModBlocks.COLORED_LIGHTNING_RODS.get(15))
                    .stream()
                    .flatMap(block -> block.get().getStateDefinition().getPossibleStates().stream())
                    .collect(ImmutableSet.toImmutableSet()), 0, 1));

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
    }
}
