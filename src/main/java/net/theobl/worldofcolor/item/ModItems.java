package net.theobl.worldofcolor.item;

import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.entity.ModBoat;

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

    private static List<DeferredItem<Item>> registerColoredSign(boolean hanging) {
        List<DeferredItem<Item>> signs = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredItem<Item> item;
            if(!hanging) {
                item = ITEMS.register(color.getName() + "_sign",
                        () -> new SignItem(new Item.Properties().stacksTo(16),
                                ModBlocks.COLORED_SIGNS.get(index).get(), ModBlocks.COLORED_WALL_SIGNS.get(index).get()));
            }
            else {
                item = ITEMS.register(color.getName() + "_hanging_sign",
                        () -> new HangingSignItem(ModBlocks.COLORED_HANGING_SIGNS.get(index).get(), ModBlocks.COLORED_WALL_HANGING_SIGNS.get(index).get(),
                                new Item.Properties().stacksTo(16)));
            }
            signs.add(item);
        }
        return signs;
    }

    private static List<DeferredItem<Item>> registerColoredBoats(boolean hasChest) {
        List<DeferredItem<Item>> boat = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredItem<Item> item = ITEMS.register(color.getName() + (hasChest ? "_chest_boat" : "_boat"),
                    () -> new ModBoatItem(hasChest, ModBoat.Type.byId(index), new Item.Properties().stacksTo(1)));
            boat.add(item);
        }
        return boat;
    }

    private static List<DeferredItem<Item>> registerColoredCauldron() {
        List<DeferredItem<Item>> cauldron = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredItem<Item> item = ITEMS.register(color.getName() + "_cauldron", key ->
                    new BlockItem(ModBlocks.COLORED_CAULDRONS.get(index).get(), new Item.Properties()) {
                        public void registerBlocks(java.util.Map<Block, Item> map, Item self) {
                            super.registerBlocks(map, self);
                            map.put(ModBlocks.COLORED_WATER_CAULDRONS.get(index).get(), self);
                            map.put(ModBlocks.COLORED_LAVA_CAULDRONS.get(index).get(), self);
                            map.put(ModBlocks.COLORED_POWDER_SNOW_CAULDRONS.get(index).get(), self);
                        }
                    });
            cauldron.add(item);
        }
        return cauldron;
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
