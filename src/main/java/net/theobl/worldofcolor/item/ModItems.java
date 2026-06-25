package net.theobl.worldofcolor.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColorCollection;
import net.minecraft.world.level.block.CopperGolemStatueBlock;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.entity.ModEntityType;
import net.theobl.worldofcolor.item.equipement.ModEquipmentAssets;

import java.util.*;
import java.util.function.BiFunction;

public class ModItems {
    // Create a Deferred Register to hold Items which will all be registered under the "worldofcolor" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(WorldOfColor.MODID);

    public static final ColorCollection<DeferredItem<Item>> COLORED_SIGNS = registerItems(
            createSimpleColored("sign"),
            (name, color) -> ITEMS.registerItem(name,
                    p -> new SignItem(ModBlocks.COLORED_SIGNS.pick(color).get(), ModBlocks.COLORED_WALL_SIGNS.pick(color).get(), p),
                    p -> p.stacksTo(16).useBlockDescriptionPrefix())
    );
    public static final ColorCollection<DeferredItem<Item>> COLORED_HANGING_SIGNS = registerItems(
            createSimpleColored("hanging_sign"),
            (name, color) -> ITEMS.registerItem(name,
                    p -> new HangingSignItem(ModBlocks.COLORED_HANGING_SIGNS.pick(color).get(), ModBlocks.COLORED_WALL_HANGING_SIGNS.pick(color).get(), p),
                    p -> p.stacksTo(16).useBlockDescriptionPrefix())
    );
    public static final ColorCollection<DeferredItem<Item>> COLORED_BOATS = registerItems(
            createSimpleColored("boat"),
            (name, color) -> ITEMS.registerItem(name,
                    p -> new BoatItem(ModEntityType.COLORED_BOATS.pick(color).get(), p),
                    p -> p.stacksTo(1))
    );
    public static final ColorCollection<DeferredItem<Item>> COLORED_CHEST_BOATS = registerItems(
            createSimpleColored("chest_boat"),
            (name, color) -> ITEMS.registerItem(name,
                    p -> new BoatItem(ModEntityType.COLORED_CHEST_BOATS.pick(color).get(), p),
                    p -> p.stacksTo(1))
    );
    public static final ColorCollection<DeferredItem<Item>> COLORED_COPPER_GOLEM_STATUES = registerItems(
            createSimpleColored("copper_golem_statue"),
            (name, color) -> ITEMS.registerItem(name,
                    p -> new BlockItem(ModBlocks.COLORED_COPPER_GOLEM_STATUES.pick(color).get(), p),
                    p -> p.component(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(CopperGolemStatueBlock.POSE, CopperGolemStatueBlock.Pose.STANDING)).useBlockDescriptionPrefix())
    );
    public static final ColorCollection<DeferredItem<Item>> COLORED_WAXED_COPPER_GOLEM_STATUES = registerItems(
            createSimpleColored("copper_golem_statue").map(((name) -> "waxed_" + name)),
            (name, color) -> ITEMS.registerItem(name,
                    p -> new BlockItem(ModBlocks.COLORED_WAXED_COPPER_GOLEM_STATUES.pick(color).get(), p),
                    p -> p.component(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(CopperGolemStatueBlock.POSE, CopperGolemStatueBlock.Pose.STANDING)).useBlockDescriptionPrefix())
    );
    public static final ColorCollection<DeferredItem<Item>> COLORED_CAULDRONS = registerItems(
            createSimpleColored("cauldron"),
            (name, color) -> ITEMS.registerItem(name,
                    p -> new BlockItem(ModBlocks.COLORED_CAULDRONS.pick(color).get(), p) {
                        public void registerBlocks(Map<Block, Item> map, Item self) {
                            super.registerBlocks(map, self);
                            map.put(ModBlocks.COLORED_WATER_CAULDRONS.pick(color).get(), self);
                            map.put(ModBlocks.COLORED_LAVA_CAULDRONS.pick(color).get(), self);
                            map.put(ModBlocks.COLORED_POWDER_SNOW_CAULDRONS.pick(color).get(), self);
                            map.put(ModBlocks.COLORED_DYED_WATER_CAULDRONS.pick(color).get(), self);
                        }
                    }, Item.Properties::useBlockDescriptionPrefix)
    );
    public static final ColorCollection<DeferredItem<Item>> COLORED_DECORATED_POTS = registerItems(
            createSimpleColored("decorated_pot"),
            (name, color) -> ITEMS.registerItem(name,
                    p -> new BlockItem(ModBlocks.COLORED_DECORATED_POTS.pick(color).get(), p),
                    p -> p.component(DataComponents.POT_DECORATIONS, PotDecorations.EMPTY).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).useBlockDescriptionPrefix())
    );
    public static final ColorCollection<DeferredItem<Item>> COLORED_ITEM_FRAMES = registerItems(
            createSimpleColored("item_frame"),
            (name, color) -> ITEMS.registerItem(name,p -> new ItemFrameItem(ModEntityType.COLORED_ITEM_FRAMES.pick(color).get(), p))
    );
    public static final ColorCollection<DeferredItem<Item>> COLORED_POTATO_PEELS = registerItems(
            createSimpleColored("potato_peels"),
            (name, color) -> ITEMS.registerSimpleItem(name, p -> p.food(Foods.POTATO))
    );
    public static final DeferredItem<BlockItem> RGB_SHULKER_BOX = ITEMS.registerItem(
            "rgb_shulker_box",
            p -> new BlockItem(ModBlocks.RGB_SHULKER_BOX.get(), p),
            p -> p.stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).useBlockDescriptionPrefix());
    public static final DeferredItem<Item> RGB_DYE = ITEMS.registerItem("rgb_dye", RgbDyeItem::new);
    public static final DeferredItem<Item> RGB_BUNDLE = ITEMS.registerItem(
            "rgb_bundle", BundleItem::new, p -> p.stacksTo(1).component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY));
    public static final DeferredItem<Item> RGB_BANNER = ITEMS.registerItem("rgb_banner",
            p -> new BannerItem(ModBlocks.RGB_BANNER.get(), ModBlocks.RGB_WALL_BANNER.get(), p),
            p -> p.stacksTo(16).component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY).useBlockDescriptionPrefix());
    public static final DeferredItem<Item> RGB_HARNESS = ITEMS.registerSimpleItem("rgb_harness",
            p -> p.stacksTo(1)
                    .component(DataComponents.EQUIPPABLE,
                            Equippable.builder(EquipmentSlot.BODY)
                                    .setEquipSound(SoundEvents.HARNESS_EQUIP)
                                    .setAsset(ModEquipmentAssets.RGB_HARNESS)
                                    .setAllowedEntities(BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.ENTITY_TYPE).getOrThrow(EntityTypeTags.CAN_EQUIP_HARNESS))
                                    .setEquipOnInteract(true)
                                    .setCanBeSheared(true)
                                    .setShearingSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.HARNESS_UNEQUIP))
                                    .build())
    );

    public static <Id> ColorCollection<DeferredItem<Item>> registerItems(ColorCollection<Id> ids, BiFunction<Id, DyeColor, DeferredItem<Item>> itemFactory) {
        return ColorCollection.zipMap(ColorCollection.VALUES, ids, (color, id) -> itemFactory.apply(id, color));
    }

    private static ColorCollection<String> createSimpleColored(String baseName) {
        return ColorCollection.prefixWithColor(ColorCollection.create(baseName));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
