package net.theobl.worldofcolor.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.entity.ModEntityType;

import java.util.ArrayList;
import java.util.List;

import static net.theobl.worldofcolor.util.ModUtil.*;

public class ModItems {
    // Create a Deferred Register to hold Items which will all be registered under the "worldofcolor" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(WorldOfColor.MODID);

    public static final List<DeferredItem<Item>> COLORED_SIGNS = registerColoredSign(false);
    public static final List<DeferredItem<Item>> COLORED_HANGING_SIGNS = registerColoredSign(true);
    public static final List<DeferredItem<Item>> COLORED_BOATS = registerColoredBoats(false);
    public static final List<DeferredItem<Item>> COLORED_CHEST_BOATS = registerColoredBoats(true);
    public static final List<DeferredItem<Item>> COLORED_CAULDRONS = registerColoredCauldron();
    public static final List<DeferredItem<Item>> COLORED_DECORATED_POTS = registerColoredDecoratedPots();
    public static final List<DeferredItem<Item>> COLORED_ITEM_FRAMES = registerColoredItemFrames();

    private static List<DeferredItem<Item>> registerColoredSign(boolean hanging) {
        List<DeferredItem<Item>> signs = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredItem<Item> item;
            if(!hanging) {
                item = ITEMS.registerItem(color.getName() + "_sign",
                        p -> new SignItem(ModBlocks.COLORED_SIGNS.get(index).get(), ModBlocks.COLORED_WALL_SIGNS.get(index).get(), p),
                                new Item.Properties().stacksTo(16).useBlockDescriptionPrefix());
            }
            else {
                item = ITEMS.registerItem(color.getName() + "_hanging_sign",
                        p -> new HangingSignItem(ModBlocks.COLORED_HANGING_SIGNS.get(index).get(), ModBlocks.COLORED_WALL_HANGING_SIGNS.get(index).get(), p),
                                new Item.Properties().stacksTo(16).useBlockDescriptionPrefix());
            }
            signs.add(item);
        }
        return signs;
    }

    private static List<DeferredItem<Item>> registerColoredBoats(boolean hasChest) {
        List<DeferredItem<Item>> boat = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredItem<Item> item;
            if(hasChest) {
                item = ITEMS.registerItem(color.getName() + "_chest_boat",
                        p -> new BoatItem(ModEntityType.COLORED_CHEST_BOATS.get(index).get(), p), new Item.Properties().stacksTo(1));
            }
            else {
                item = ITEMS.registerItem(color.getName() + "_boat",
                        p -> new BoatItem(ModEntityType.COLORED_BOATS.get(index).get(), p), new Item.Properties().stacksTo(1));
            }
            boat.add(item);
        }
        return boat;
    }

    private static List<DeferredItem<Item>> registerColoredCauldron() {
        List<DeferredItem<Item>> cauldron = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredItem<Item> item = ITEMS.registerItem(color.getName() + "_cauldron", p ->
                    new BlockItem(ModBlocks.COLORED_CAULDRONS.get(index).get(), p) {
                        public void registerBlocks(java.util.Map<Block, Item> map, Item self) {
                            super.registerBlocks(map, self);
                            map.put(ModBlocks.COLORED_WATER_CAULDRONS.get(index).get(), self);
                            map.put(ModBlocks.COLORED_LAVA_CAULDRONS.get(index).get(), self);
                            map.put(ModBlocks.COLORED_POWDER_SNOW_CAULDRONS.get(index).get(), self);
                        }
                    },
                    new Item.Properties().useBlockDescriptionPrefix());
            cauldron.add(item);
        }
        return cauldron;
    }

    private static List<DeferredItem<Item>> registerColoredDecoratedPots() {
        List<DeferredItem<Item>> items = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredItem<Item> item = ITEMS.registerItem(color.getName() + "_decorated_pot",
                    p -> new BlockItem(ModBlocks.COLORED_DECORATED_POTS.get(index).get(), p),
                    new Item.Properties().component(DataComponents.POT_DECORATIONS, PotDecorations.EMPTY).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY).useBlockDescriptionPrefix());
            items.add(item);
        }
        return items;
    }

    private static List<DeferredItem<Item>> registerColoredItemFrames() {
        List<DeferredItem<Item>> items = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredItem<Item> item = ITEMS.registerItem(color.getName() + "_item_frame",
                    p -> new ItemFrameItem(ModEntityType.COLORED_ITEM_FRAMES.get(index).get(), p));
            items.add(item);
        }
        return items;
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
