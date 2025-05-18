package net.theobl.worldofcolor.item;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
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

    public static final List<DeferredItem<Item>> COLORED_SIGNS = registerColoredSign();
    private static List<DeferredItem<Item>> registerColoredSign(){
        List<DeferredItem<Item>> signs = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredItem<Item> item = ITEMS.register(color.getName() + "_sign",
                    () -> new SignItem(new Item.Properties().stacksTo(16),
                            ModBlocks.COLORED_SIGNS.get(index).get(), ModBlocks.COLORED_WALL_SIGNS.get(index).get()));
            signs.add(item);
        }
        return signs;
    }

    public static final List<DeferredItem<Item>> COLORED_HANGING_SIGNS = registerColoredHangingSign();
    private static List<DeferredItem<Item>> registerColoredHangingSign(){
        List<DeferredItem<Item>> hangingSign = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredItem<Item> item = ITEMS.register(color.getName() + "_hanging_sign",
                    () -> new HangingSignItem(ModBlocks.COLORED_HANGING_SIGNS.get(index).get(), ModBlocks.COLORED_WALL_HANGING_SIGNS.get(index).get(),
                            new Item.Properties().stacksTo(16)));
            hangingSign.add(item);
        }
        return hangingSign;
    }

    public static final List<DeferredItem<Item>> COLORED_BOATS = registerColoredBoats(false);
    public static final List<DeferredItem<Item>> COLORED_CHEST_BOATS = registerColoredBoats(true);
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

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
