package net.theobl.worldofcolor.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.util.ModUtil;

public class ModCreativeModeTabs {
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "worldofcolor" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WorldOfColor.MODID);

    // Creates a creative tab with the id "worldofcolor:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab",
            () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.worldofcolor"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(() -> ModBlocks.QUILTED_CONCRETES.get(DyeColor.WHITE).asItem().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        for (DyeColor color : ModUtil.COLORS) {
                            int index = ModUtil.COLORS.indexOf(color);
                            output.accept(ModBlocks.COLORED_LEAVES.get(color));
                            output.accept(ModBlocks.COLORED_LOGS.get(color));
                            output.accept(ModBlocks.COLORED_WOODS.get(color));
                            output.accept(ModBlocks.COLORED_STRIPPED_LOGS.get(color));
                            output.accept(ModBlocks.COLORED_STRIPPED_WOODS.get(color));
                            output.accept(ModBlocks.COLORED_PLANKS.get(color));
                            output.accept(ModBlocks.COLORED_STAIRS.get(color));
                            output.accept(ModBlocks.COLORED_SLABS.get(color));
                            output.accept(ModBlocks.COLORED_FENCES.get(color));
                            output.accept(ModBlocks.COLORED_FENCE_GATES.get(color));
                            output.accept(ModBlocks.COLORED_DOORS.get(color));
                            output.accept(ModBlocks.COLORED_TRAPDOORS.get(color));
                            output.accept(ModBlocks.COLORED_PRESSURE_PLATES.get(color));
                            output.accept(ModBlocks.COLORED_BUTTONS.get(color));
                            output.accept(ModBlocks.COLORED_SIGNS.get(color));
                            output.accept(ModBlocks.COLORED_HANGING_SIGNS.get(color));
                            output.accept(ModItems.COLORED_BOATS.get(color));
                            output.accept(ModItems.COLORED_CHEST_BOATS.get(color));
                        }
                        for (DyeColor color : ModUtil.COLORS) {
                            int index = ModUtil.COLORS.indexOf(color);
                            output.accept(ModBlocks.COLORED_COPPER_BLOCKS.get(color));
                            output.accept(ModBlocks.COLORED_CHISELED_COPPER.get(color));
                            output.accept(ModBlocks.COLORED_COPPER_GRATES.get(color));
                            output.accept(ModBlocks.COLORED_CUT_COPPER.get(color));
                            output.accept(ModBlocks.COLORED_CUT_COPPER_STAIRS.get(color));
                            output.accept(ModBlocks.COLORED_CUT_COPPER_SLABS.get(color));
                            output.accept(ModBlocks.COLORED_COPPER_DOORS.get(color));
                            output.accept(ModBlocks.COLORED_COPPER_TRAPDOORS.get(color));
                            output.accept(ModBlocks.COLORED_COPPER_BULBS.get(color));
                        }
                        for (DyeColor color : ModUtil.COLORS) {
                            int index = ModUtil.COLORS.indexOf(color);
                            output.accept(ModBlocks.COLORED_WAXED_COPPER_BLOCKS.get(color));
                            output.accept(ModBlocks.COLORED_WAXED_CHISELED_COPPER.get(color));
                            output.accept(ModBlocks.COLORED_WAXED_COPPER_GRATES.get(color));
                            output.accept(ModBlocks.COLORED_WAXED_CUT_COPPER.get(color));
                            output.accept(ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS.get(color));
                            output.accept(ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS.get(color));
                            output.accept(ModBlocks.COLORED_WAXED_COPPER_DOORS.get(color));
                            output.accept(ModBlocks.COLORED_WAXED_COPPER_TRAPDOORS.get(color));
                            output.accept(ModBlocks.COLORED_WAXED_COPPER_BULBS.get(color));
                        }
                        for (DyeColor color : ModUtil.COLORS) {
                            int index = ModUtil.COLORS.indexOf(color);
                            output.accept(ModBlocks.COLORED_BRICKS.get(color));
                            output.accept(ModBlocks.COLORED_BRICK_STAIRS.get(color));
                            output.accept(ModBlocks.COLORED_BRICK_SLABS.get(color));
                            output.accept(ModBlocks.COLORED_BRICK_WALLS.get(color));
                        }
                        for (DeferredHolder<Block, ? extends Block> block : ModBlocks.BLOCKS.getEntries()) {
                            if(block.get().asItem() != Blocks.AIR.asItem() && block.get().asItem() != Items.CAULDRON)
                                output.accept(block.get());
                        }
                        output.accept(ModItems.RGB_BUNDLE);
                        output.accept(ModItems.RGB_DYE);
                        ModItems.ITEMS.getEntries().stream().map(DeferredHolder::get).forEach(output::accept);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
