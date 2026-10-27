package net.theobl.worldofcolor.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.decoration.Cushion;
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
import net.theobl.worldofcolor.util.ColorCollectionUtil;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ModEntityType {
    public static final DeferredRegister.Entities ENTITY_TYPES = DeferredRegister.createEntities(WorldOfColor.MODID);

    public static final ColorCollection<DeferredHolder<EntityType<?>, EntityType<Boat>>> COLORED_BOATS = registerColored(
            "boat",
            color -> boatFactory(ModItems.COLORED_BOATS.pick(color)),
            MobCategory.MISC,
            b-> b.noLootTable()
                    .sized(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .clientTrackingRange(10)
    );
    public static final ColorCollection<DeferredHolder<EntityType<?>, EntityType<ChestBoat>>> COLORED_CHEST_BOATS = registerColored(
            "chest_boat",
            color -> chestBoatFactory(ModItems.COLORED_CHEST_BOATS.pick(color)),
            MobCategory.MISC,
            b -> b.noLootTable()
                    .sized(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .clientTrackingRange(10)
    );
    public static final ColorCollection<DeferredHolder<EntityType<?>, EntityType<ColoredItemFrame>>> COLORED_ITEM_FRAMES = registerColored(
            "item_frame",
            ModEntityType::itemFrameFactory,
            MobCategory.MISC,
            b -> b.noLootTable()
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.0F)
                    .clientTrackingRange(10)
                    .updateInterval(Integer.MAX_VALUE)
    );

    public static final DeferredHolder<EntityType<?>, EntityType<Cushion>> RGB_CUSHION = ENTITY_TYPES.registerEntityType(
            "rgb_cushion",
            RgbCushion::new,
            MobCategory.MISC,
            b -> b.noLootTable()
                    .sized(1.0F, 0.25F)
                    .clientTrackingRange(10)
                    .updateInterval(Integer.MAX_VALUE)
                    .dontTrackDeltas()
    );
    public static final DeferredHolder<EntityType<?>, EntityType<Cushion>> MISSINGNO_CUSHION = ENTITY_TYPES.registerEntityType(
            "missingno_cushion",
            MissingnoCushion::new,
            MobCategory.MISC,
            b -> b.noLootTable()
                    .sized(1.0F, 0.25F)
                    .clientTrackingRange(10)
                    .updateInterval(Integer.MAX_VALUE)
                    .dontTrackDeltas()
    );

    private static EntityType.EntityFactory<Boat> boatFactory(Supplier<Item> boatItemGetter) {
        return (boat, level) -> new Boat(boat, level, boatItemGetter);
    }

    private static EntityType.EntityFactory<ChestBoat> chestBoatFactory(Supplier<Item> boatItemGetter) {
        return (boat, level) -> new ChestBoat(boat, level, boatItemGetter);
    }

    private static EntityType.EntityFactory<ColoredItemFrame> itemFrameFactory(DyeColor color) {
        return (itemFrame, level) -> new ColoredItemFrame(itemFrame, level, color);
    }

    private static ColorCollection<String> createSimpleColored(String baseName) {
        return ColorCollection.prefixWithColor(ColorCollection.create(baseName));
    }

    @SuppressWarnings("SameParameterValue")
    private static <E extends Entity> ColorCollection<DeferredHolder<EntityType<?>, EntityType<E>>> registerColored(
            String name,
            Function<DyeColor, EntityType.EntityFactory<E>> factory,
            MobCategory category,
            UnaryOperator<EntityType.Builder<E>> builder
    ) {
        return ColorCollectionUtil.register(
                createSimpleColored(name),
                (id, color) -> ENTITY_TYPES.registerEntityType(
                        id,
                        factory.apply(color),
                        category,
                        builder
                )
        );
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}