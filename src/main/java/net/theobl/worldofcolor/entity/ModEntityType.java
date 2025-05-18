package net.theobl.worldofcolor.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;

import java.util.function.Supplier;

public class ModEntityType {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, WorldOfColor.MODID);

    public static final Supplier<EntityType<ModBoat>> COLORED_BOAT = ENTITY_TYPES.register("colored_boat",
            () -> EntityType.Builder.<ModBoat>of(ModBoat::new, MobCategory.MISC).sized(1.375f, 0.5625f).build("colored_boat"));
    public static final Supplier<EntityType<ModChestBoat>> COLORED_CHEST_BOAT = ENTITY_TYPES.register("colored_chest_boat",
            () -> EntityType.Builder.<ModChestBoat>of(ModChestBoat::new, MobCategory.MISC).sized(1.375f, 0.5625f).build("colored_chest_boat"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}