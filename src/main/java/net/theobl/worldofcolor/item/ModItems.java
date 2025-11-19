package net.theobl.worldofcolor.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CopperGolemStatueBlock;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.entity.ModEntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.theobl.worldofcolor.util.ModUtil.*;

public class ModItems {
    // Create a Deferred Register to hold Items which will all be registered under the "worldofcolor" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(WorldOfColor.MODID);

    public static final List<DeferredItem<Item>> COLORED_SIGNS = registerColored("sign");
    public static final List<DeferredItem<Item>> COLORED_HANGING_SIGNS = registerColored("hanging_sign");
    public static final List<DeferredItem<Item>> COLORED_BOATS = registerColored("boat");
    public static final List<DeferredItem<Item>> COLORED_CHEST_BOATS = registerColored("chest_boat");
    public static final List<DeferredItem<Item>> COLORED_COPPER_GOLEM_STATUES = registerColored("copper_golem_statue");
    public static final List<DeferredItem<Item>> COLORED_WAXED_COPPER_GOLEM_STATUES = registerColored("waxed_copper_golem_statue");
    public static final List<DeferredItem<Item>> COLORED_CAULDRONS = registerColored("cauldron");
    public static final List<DeferredItem<Item>> COLORED_DECORATED_POTS = registerColored("decorated_pot");
    public static final List<DeferredItem<Item>> COLORED_ITEM_FRAMES = registerColored("item_frame");
    public static final DeferredItem<BlockItem> RGB_SHULKER_BOX = ITEMS.registerItem(
            "rgb_shulker_box",
            p -> new BlockItem(ModBlocks.RGB_SHULKER_BOX.get(), p),
            p -> p.stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).useBlockDescriptionPrefix());
    public static final DeferredItem<Item> RGB_DYE = ITEMS.registerSimpleItem("rgb_dye");

    private static List<DeferredItem<Item>> registerColored(String key) {
        List<DeferredItem<Item>> items = new ArrayList<>();
        for(DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            String name = color.getName() + "_" + key;
            DeferredItem<Item> item = switch (key) {
                case "sign" -> ITEMS.registerItem(color.getName() + "_sign",
                        p -> new SignItem(ModBlocks.COLORED_SIGNS.get(index).get(), ModBlocks.COLORED_WALL_SIGNS.get(index).get(), p),
                        p -> p.stacksTo(16).useBlockDescriptionPrefix());

                case "hanging_sign" -> ITEMS.registerItem(color.getName() + "_hanging_sign",
                        p -> new HangingSignItem(ModBlocks.COLORED_HANGING_SIGNS.get(index).get(), ModBlocks.COLORED_WALL_HANGING_SIGNS.get(index).get(), p),
                        p -> p.stacksTo(16).useBlockDescriptionPrefix());

                case "boat" -> ITEMS.registerItem(color.getName() + "_boat",
                        p -> new BoatItem(ModEntityType.COLORED_BOATS.get(index).get(), p),
                        p -> p.stacksTo(1));

                case "chest_boat" -> ITEMS.registerItem(color.getName() + "_chest_boat",
                        p -> new BoatItem(ModEntityType.COLORED_CHEST_BOATS.get(index).get(), p),
                        p -> p.stacksTo(1));

                case "copper_golem_statue" -> ITEMS.registerItem(color.getName() + "_copper_golem_statue",
                        p -> new BlockItem(ModBlocks.COLORED_COPPER_GOLEM_STATUES.get(index).get(), p),
                        p -> p.component(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(CopperGolemStatueBlock.POSE, CopperGolemStatueBlock.Pose.STANDING)).useBlockDescriptionPrefix());

                case "waxed_copper_golem_statue" -> ITEMS.registerItem("waxed_" + color.getName() + "_copper_golem_statue",
                        p -> new BlockItem(ModBlocks.COLORED_WAXED_COPPER_GOLEM_STATUES.get(index).get(), p),
                        p -> p.component(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(CopperGolemStatueBlock.POSE, CopperGolemStatueBlock.Pose.STANDING)).useBlockDescriptionPrefix());

                case "cauldron" -> ITEMS.registerItem(color.getName() + "_cauldron",
                        p -> new BlockItem(ModBlocks.COLORED_CAULDRONS.get(index).get(), p) {
                                    public void registerBlocks(Map<Block, Item> map, Item self) {
                                        super.registerBlocks(map, self);
                                        map.put(ModBlocks.COLORED_WATER_CAULDRONS.get(index).get(), self);
                                        map.put(ModBlocks.COLORED_LAVA_CAULDRONS.get(index).get(), self);
                                        map.put(ModBlocks.COLORED_POWDER_SNOW_CAULDRONS.get(index).get(), self);
                                    }
                                }, Item.Properties::useBlockDescriptionPrefix);

                case "decorated_pot" -> ITEMS.registerItem(color.getName() + "_decorated_pot",
                        p -> new BlockItem(ModBlocks.COLORED_DECORATED_POTS.get(index).get(), p),
                        p -> p.component(DataComponents.POT_DECORATIONS, PotDecorations.EMPTY).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).useBlockDescriptionPrefix());

                case "item_frame" -> ITEMS.registerItem(color.getName() + "_item_frame",
                        p -> new ItemFrameItem(ModEntityType.COLORED_ITEM_FRAMES.get(index).get(), p));

                default -> ITEMS.registerSimpleItem(name);
            };
            items.add(item);
        }
        return items;
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
