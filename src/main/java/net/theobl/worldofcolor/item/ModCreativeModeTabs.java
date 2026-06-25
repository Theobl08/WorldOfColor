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
                    .icon(() -> ModBlocks.QUILTED_CONCRETES.pick(DyeColor.WHITE).asItem().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        for (DyeColor color : ModUtil.COLORS) {
                            int index = ModUtil.COLORS.indexOf(color);
                            output.accept(ModBlocks.COLORED_LEAVES.pick(color));
                            output.accept(ModBlocks.COLORED_LOGS.pick(color));
                            output.accept(ModBlocks.COLORED_WOODS.pick(color));
                            output.accept(ModBlocks.COLORED_STRIPPED_LOGS.pick(color));
                            output.accept(ModBlocks.COLORED_STRIPPED_WOODS.pick(color));
                            output.accept(ModBlocks.COLORED_PLANKS.pick(color));
                            output.accept(ModBlocks.COLORED_STAIRS.pick(color));
                            output.accept(ModBlocks.COLORED_SLABS.pick(color));
                            output.accept(ModBlocks.COLORED_FENCES.pick(color));
                            output.accept(ModBlocks.COLORED_FENCE_GATES.pick(color));
                            output.accept(ModBlocks.COLORED_DOORS.pick(color));
                            output.accept(ModBlocks.COLORED_TRAPDOORS.pick(color));
                            output.accept(ModBlocks.COLORED_PRESSURE_PLATES.pick(color));
                            output.accept(ModBlocks.COLORED_BUTTONS.pick(color));
                            output.accept(ModBlocks.COLORED_SIGNS.pick(color));
                            output.accept(ModBlocks.COLORED_HANGING_SIGNS.pick(color));
                            output.accept(ModItems.COLORED_BOATS.pick(color));
                            output.accept(ModItems.COLORED_CHEST_BOATS.pick(color));
                        }
                        for (DyeColor color : ModUtil.COLORS) {
                            int index = ModUtil.COLORS.indexOf(color);
                            output.accept(ModBlocks.COLORED_COPPER_BLOCKS.pick(color));
                            output.accept(ModBlocks.COLORED_CHISELED_COPPER.pick(color));
                            output.accept(ModBlocks.COLORED_COPPER_GRATES.pick(color));
                            output.accept(ModBlocks.COLORED_CUT_COPPER.pick(color));
                            output.accept(ModBlocks.COLORED_CUT_COPPER_STAIRS.pick(color));
                            output.accept(ModBlocks.COLORED_CUT_COPPER_SLABS.pick(color));
                            output.accept(ModBlocks.COLORED_COPPER_DOORS.pick(color));
                            output.accept(ModBlocks.COLORED_COPPER_TRAPDOORS.pick(color));
                            output.accept(ModBlocks.COLORED_COPPER_BULBS.pick(color));
                        }
                        for (DyeColor color : ModUtil.COLORS) {
                            int index = ModUtil.COLORS.indexOf(color);
                            output.accept(ModBlocks.COLORED_WAXED_COPPER_BLOCKS.pick(color));
                            output.accept(ModBlocks.COLORED_WAXED_CHISELED_COPPER.pick(color));
                            output.accept(ModBlocks.COLORED_WAXED_COPPER_GRATES.pick(color));
                            output.accept(ModBlocks.COLORED_WAXED_CUT_COPPER.pick(color));
                            output.accept(ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS.pick(color));
                            output.accept(ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS.pick(color));
                            output.accept(ModBlocks.COLORED_WAXED_COPPER_DOORS.pick(color));
                            output.accept(ModBlocks.COLORED_WAXED_COPPER_TRAPDOORS.pick(color));
                            output.accept(ModBlocks.COLORED_WAXED_COPPER_BULBS.pick(color));
                        }
                        for (DyeColor color : ModUtil.COLORS) {
                            int index = ModUtil.COLORS.indexOf(color);
                            output.accept(ModBlocks.COLORED_BRICKS.pick(color));
                            output.accept(ModBlocks.COLORED_BRICK_STAIRS.pick(color));
                            output.accept(ModBlocks.COLORED_BRICK_SLABS.pick(color));
                            output.accept(ModBlocks.COLORED_BRICK_WALLS.pick(color));
                        }
                        for (DeferredHolder<Block, ? extends Block> block : ModBlocks.BLOCKS.getEntries()) {
                            if(block.get().asItem() != Blocks.AIR.asItem() && block.get().asItem() != Items.CAULDRON)
                                output.accept(block.get());
                        }
                        output.accept(ModItems.RGB_BUNDLE);
                        output.accept(ModItems.RGB_HARNESS);
                        output.accept(ModItems.RGB_DYE);
                        ModItems.ITEMS.getEntries().stream().map(DeferredHolder::get).forEach(output::accept);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
