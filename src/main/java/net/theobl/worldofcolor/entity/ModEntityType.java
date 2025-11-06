package net.theobl.worldofcolor.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.decoration.ItemFrame;
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
    public static final List<Supplier<EntityType<ColoredItemFrame>>> COLORED_ITEM_FRAMES = registerColoredItemFrames();

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

    @SuppressWarnings("unchecked")
    public static <T extends ItemFrame> List<Supplier<EntityType<T>>> registerColoredItemFrames() {
        List<Supplier<EntityType<T>>> boats = new ArrayList<>();
        for (DyeColor color : ModUtil.COLORS) {
            Supplier<EntityType<T>> entity = registerEntityType(color.getName() + "_item_frame", (EntityType.Builder<T>) EntityType.Builder.of(itemFrameFactory(color), MobCategory.MISC)
                    .noLootTable()
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.0F)
                    .clientTrackingRange(10)
                    .updateInterval(Integer.MAX_VALUE));
            boats.add(entity);
        }
        return boats;
    }

    private static <T extends Entity> Supplier<EntityType<T>> registerEntityType(String key, EntityType.Builder<T> builder) {
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

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}