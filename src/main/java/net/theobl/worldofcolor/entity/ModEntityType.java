package net.theobl.worldofcolor.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModEntityType {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, WorldOfColor.MODID);

    public static final List<Supplier<EntityType<Boat>>> COLORED_BOATS = registerColoredBoats();
    public static final List<Supplier<EntityType<ChestBoat>>> COLORED_CHEST_BOATS = registerColoredChestBoats();

    public static List<Supplier<EntityType<Boat>>> registerColoredBoats() {
        List<Supplier<EntityType<Boat>>> boats = new ArrayList<>();
        for (DyeColor color : ModUtil.COLORS) {
            int index = ModUtil.COLORS.indexOf(color);
            Supplier<EntityType<Boat>> entity = registerEntityType(color.getName() + "_boat", EntityType.Builder.of(boatFactory(ModItems.COLORED_BOATS.get(index)), MobCategory.MISC)
                    .noLootTable()
                    .sized(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .clientTrackingRange(10));
            boats.add(entity);
        }
        return boats;
    }

    public static List<Supplier<EntityType<ChestBoat>>> registerColoredChestBoats() {
        List<Supplier<EntityType<ChestBoat>>> boats = new ArrayList<>();
        for (DyeColor color : ModUtil.COLORS) {
            int index = ModUtil.COLORS.indexOf(color);
            Supplier<EntityType<ChestBoat>> entity = registerEntityType(color.getName() + "_chest_boat", EntityType.Builder.of(chestBoatFactory(ModItems.COLORED_CHEST_BOATS.get(index)), MobCategory.MISC)
                    .noLootTable()
                    .sized(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .clientTrackingRange(10));
            boats.add(entity);
        }
        return boats;
    }
    private static <T extends Entity> Supplier<EntityType<T>> registerEntityType(String key, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(key, () -> builder.build(coloredEntityId(key)));
    }

    private static ResourceKey<EntityType<?>> coloredEntityId(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(WorldOfColor.MODID, name));
    }

    private static EntityType.EntityFactory<Boat> boatFactory(Supplier<Item> boatItemGetter) {
        return (boat, level) -> new Boat(boat, level, boatItemGetter);
    }

    private static EntityType.EntityFactory<ChestBoat> chestBoatFactory(Supplier<Item> boatItemGetter) {
        return (boat, level) -> new ChestBoat(boat, level, boatItemGetter);
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}