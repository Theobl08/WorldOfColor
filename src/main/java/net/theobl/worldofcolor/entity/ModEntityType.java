package net.theobl.worldofcolor.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.ColorCollection;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.item.ModItems;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ModEntityType {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, WorldOfColor.MODID);

    public static final ColorCollection<DeferredHolder<EntityType<?>, EntityType<Boat>>> COLORED_BOATS = registerEntityTypes(
            createSimpleColored("boat"),
            (name, color) -> registerEntityType(name,
                    EntityType.Builder.of(boatFactory(ModItems.COLORED_BOATS.pick(color)), MobCategory.MISC)
                    .noLootTable()
                    .sized(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .clientTrackingRange(10))
    );
    public static final ColorCollection<DeferredHolder<EntityType<?>, EntityType<ChestBoat>>> COLORED_CHEST_BOATS = registerEntityTypes(
            createSimpleColored("chest_boat"),
            (name, color) -> registerEntityType(name,
                    EntityType.Builder.of(chestBoatFactory(ModItems.COLORED_CHEST_BOATS.pick(color)), MobCategory.MISC)
                    .noLootTable()
                    .sized(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .clientTrackingRange(10))
    );
    public static final ColorCollection<DeferredHolder<EntityType<?>, EntityType<ColoredItemFrame>>> COLORED_ITEM_FRAMES = registerEntityTypes(
            createSimpleColored("item_frame"),
            (name, color) -> registerEntityType(name,
                    EntityType.Builder.of(itemFrameFactory(color), MobCategory.MISC)
                    .noLootTable()
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.0F)
                    .clientTrackingRange(10)
                    .updateInterval(Integer.MAX_VALUE))
    );

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> registerEntityType(String key, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(key, () -> builder.build(coloredEntityId(key)));
    }

    private static ResourceKey<EntityType<?>> coloredEntityId(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, WorldOfColor.asResource(name));
    }

    private static EntityType.EntityFactory<Boat> boatFactory(Supplier<Item> boatItemGetter) {
        return (boat, level) -> new Boat(boat, level, boatItemGetter);
    }

    private static EntityType.EntityFactory<ChestBoat> chestBoatFactory(Supplier<Item> boatItemGetter) {
        return (boat, level) -> new ChestBoat(boat, level, boatItemGetter);
    }

    private static EntityType.EntityFactory<ColoredItemFrame> itemFrameFactory(DyeColor color) {
        return (itemFrame, level) -> new ColoredItemFrame(itemFrame, level, color);
    }

    public static <E extends Entity, Id> ColorCollection<DeferredHolder<EntityType<?>, EntityType<E>>> registerEntityTypes(
            ColorCollection<Id> ids,
            BiFunction<Id, DyeColor, DeferredHolder<EntityType<?>, EntityType<E>>> entityTypeFactory
    ) {
        return ColorCollection.zipMap(ColorCollection.VALUES, ids, (color, id) -> entityTypeFactory.apply(id, color));
    }

    private static ColorCollection<String> createSimpleColored(String baseName) {
        return ColorCollection.prefixWithColor(ColorCollection.create(baseName));
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}