package net.theobl.worldofcolor.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.item.ModItems;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.theobl.worldofcolor.util.ModUtil.*;

public class ModBlocks {
    // Create a Deferred Register to hold Blocks which will all be registered under the "worldofcolor" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(WorldOfColor.MODID);

    public static final List<DeferredBlock<Block>> CLASSIC_WOOLS = registerClassicWools();
    public static final List<DeferredBlock<Block>> CLASSIC_CARPETS = registerClassicCarpets();
    public static final List<DeferredBlock<Block>> SIMPLE_COLORED_BLOCKS = registerColoredBlocks("block", BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final List<DeferredBlock<Block>> QUILTED_CONCRETES = registerColoredBlocks("quilted_concrete", BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final List<DeferredBlock<Block>> GLAZED_CONCRETES = registerGlazedConcrete();
    public static final List<DeferredBlock<Block>> COLORED_LEAVES = registerColoredLeaves();
    public static final List<DeferredBlock<Block>> COLORED_LOGS = registerColoredLogs("log", false);
    public static final List<DeferredBlock<Block>> COLORED_WOODS = registerColoredLogs("wood", false);
    public static final List<DeferredBlock<Block>> COLORED_STRIPPED_LOGS = registerColoredLogs("log", true);
    public static final List<DeferredBlock<Block>> COLORED_STRIPPED_WOODS = registerColoredLogs("wood", true);
    public static final List<DeferredBlock<Block>> COLORED_PLANKS = registerColoredBlocks("planks", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final List<DeferredBlock<Block>> COLORED_STAIRS = registerColoredStairs();
    public static final List<DeferredBlock<Block>> COLORED_SLABS = registerColoredSlabs();
    public static final List<DeferredBlock<Block>> COLORED_FENCES = registerColoredFences();
    public static final List<DeferredBlock<Block>> COLORED_FENCE_GATES = registerColoredFenceGates();
    public static final List<DeferredBlock<Block>> COLORED_DOORS = registerColoredDoors();
    public static final List<DeferredBlock<Block>> COLORED_TRAPDOORS = registerColoredTrapdoors();
    public static final List<DeferredBlock<Block>> COLORED_PRESSURE_PLATES = registerColoredPressurePlates();
    public static final List<DeferredBlock<Block>> COLORED_BUTTONS = registerColoredButtons();
    public static final List<DeferredBlock<Block>> COLORED_SIGNS = registerColoredSigns();
    public static final List<DeferredBlock<Block>> COLORED_WALL_SIGNS = registerColoredWallSign();
    public static final List<DeferredBlock<Block>> COLORED_HANGING_SIGNS = registerColoredHangingSign();
    public static final List<DeferredBlock<Block>> COLORED_WALL_HANGING_SIGNS = registerColoredWallHangingSign();
    public static final List<DeferredBlock<Block>> COLORED_COPPER_BLOCKS = registerColoredWeatheringBlocks("copper_block");
    public static final List<DeferredBlock<Block>> COLORED_CHISELED_COPPER = registerColoredWeatheringBlocks("chiseled_copper");
    public static final List<DeferredBlock<Block>> COLORED_COPPER_GRATES = registerColoredWeatheringCopperGrateBlocks();
    public static final List<DeferredBlock<Block>> COLORED_CUT_COPPER = registerColoredWeatheringBlocks("cut_copper");
    public static final List<DeferredBlock<Block>> COLORED_CUT_COPPER_STAIRS = registerColoredWeatheringStairs();
    public static final List<DeferredBlock<Block>> COLORED_CUT_COPPER_SLABS = registerColoredWeatheringSlabs();
    public static final List<DeferredBlock<Block>> COLORED_COPPER_DOORS = registerColoredWeatheringDoors();
    public static final List<DeferredBlock<Block>> COLORED_COPPER_TRAPDOORS = registerColoredWeatheringTrapDoors();
    public static final List<DeferredBlock<Block>> COLORED_COPPER_BULBS = registerColoredWeatheringCopperBulbs();
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_BLOCKS = registerColoredWaxedBlocks("copper_block");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_CHISELED_COPPER = registerColoredWaxedBlocks("chiseled_copper");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_GRATES = registerColoredCopperGrateBlocks();
    public static final List<DeferredBlock<Block>> COLORED_WAXED_CUT_COPPER = registerColoredWaxedBlocks("cut_copper");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_CUT_COPPER_STAIRS = registerColoredCopperStairs();
    public static final List<DeferredBlock<Block>> COLORED_WAXED_CUT_COPPER_SLABS = registerColoredCopperSlabs();
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_DOORS = registerColoredCopperDoors();
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_TRAPDOORS = registerColoredCopperTrapdoors();
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_BULBS = registerColoredCopperBulbs();

    private static List<DeferredBlock<Block>> registerColoredBlocks(String key, BlockBehaviour.Properties properties) {
        List<DeferredBlock<Block>> blocks = new ArrayList<>();
        COLORS.forEach( color -> blocks.add(registerBlock(color.getName() + "_" + key, () -> new Block(properties.mapColor(color)))));
        return blocks;
    }
    private static List<DeferredBlock<Block>> registerColoredWaxedBlocks(String key) {
        List<DeferredBlock<Block>> blocks = new ArrayList<>();
        COLORS.forEach( color -> blocks.add(registerBlock("waxed_" + color.getName() + "_" + key,
                () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(color)))));
        return blocks;
    }

    private static List<DeferredBlock<Block>> registerColoredWeatheringBlocks(String key) {
        List<DeferredBlock<Block>> blocks = new ArrayList<>();
        COLORS.forEach( color -> blocks.add(registerBlock(color.getName() + "_" + key,
                () -> new WeatheringCopperFullBlock(WeatheringCopper.WeatherState.UNAFFECTED, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(color)))));
        return blocks;
    }

    private static List<DeferredBlock<Block>> registerColoredWeatheringStairs() {
        List<DeferredBlock<Block>> blocks = new ArrayList<>();
        COLORS.forEach( color -> blocks.add(registerBlock(color.getName() + "_cut_copper_stairs",
                () -> new WeatheringCopperStairBlock(WeatheringCopper.WeatherState.UNAFFECTED, ModBlocks.COLORED_CUT_COPPER.get(COLORS.indexOf(color)).get().defaultBlockState(),
                        BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(color)))));
        return blocks;
    }

    private static List<DeferredBlock<Block>> registerColoredWeatheringSlabs() {
        List<DeferredBlock<Block>> blocks = new ArrayList<>();
        COLORS.forEach( color -> blocks.add(registerBlock(color.getName() + "_cut_copper_slab",
                () -> new WeatheringCopperSlabBlock(WeatheringCopper.WeatherState.UNAFFECTED,
                        BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(color)))));
        return blocks;
    }

    private static List<DeferredBlock<Block>> registerColoredWeatheringDoors() {
        List<DeferredBlock<Block>> blocks = new ArrayList<>();
        COLORS.forEach( color -> blocks.add(registerBlock(color.getName() + "_copper_door",
                () -> new WeatheringCopperDoorBlock(BlockSetType.COPPER, WeatheringCopper.WeatherState.UNAFFECTED,
                        BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_DOOR).mapColor(color)))));
        return blocks;
    }

    private static List<DeferredBlock<Block>> registerColoredWeatheringTrapDoors() {
        List<DeferredBlock<Block>> blocks = new ArrayList<>();
        COLORS.forEach( color -> blocks.add(registerBlock(color.getName() + "_copper_trapdoor",
                () -> new WeatheringCopperTrapDoorBlock(BlockSetType.COPPER, WeatheringCopper.WeatherState.UNAFFECTED,
                        BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_TRAPDOOR).mapColor(color)))));
        return blocks;
    }

    private static List<DeferredBlock<Block>> registerColoredWeatheringCopperGrateBlocks() {
        List<DeferredBlock<Block>> blocks = new ArrayList<>();
        COLORS.forEach( color -> blocks.add(registerBlock(color.getName() + "_copper_grate",
                () -> new WeatheringCopperGrateBlock(WeatheringCopper.WeatherState.UNAFFECTED, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).mapColor(color)))));
        return blocks;
    }

    private static List<DeferredBlock<Block>> registerColoredCopperGrateBlocks() {
        List<DeferredBlock<Block>> blocks = new ArrayList<>();
        COLORS.forEach( color -> blocks.add(registerBlock("waxed_" + color.getName() + "_copper_grate",
                () -> new WaterloggedTransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).mapColor(color)))));
        return blocks;
    }

    private static List<DeferredBlock<Block>> registerColoredWeatheringCopperBulbs() {
        List<DeferredBlock<Block>> blocks = new ArrayList<>();
        COLORS.forEach( color -> blocks.add(registerBlock(color.getName() + "_copper_bulb",
                () -> new WeatheringCopperBulbBlock(WeatheringCopper.WeatherState.UNAFFECTED, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BULB).mapColor(color)))));
        return blocks;
    }

    private static List<DeferredBlock<Block>> registerColoredCopperBulbs() {
        List<DeferredBlock<Block>> blocks = new ArrayList<>();
        COLORS.forEach( color -> blocks.add(registerBlock("waxed_" + color.getName() + "_copper_bulb",
                () -> new CopperBulbBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BULB).mapColor(color)))));
        return blocks;
    }

    private static List<DeferredBlock<Block>> registerColoredCopperStairs() {
        List<DeferredBlock<Block>> stairs = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredBlock<Block> block = registerBlock("waxed_" + color.getName() + "_cut_copper_stairs",
                    () -> new StairBlock(ModBlocks.COLORED_WAXED_CUT_COPPER.get(index).get().defaultBlockState(),
                            BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_STAIRS).mapColor(color)));
            stairs.add(block);
        }
        return stairs;
    }

    private static List<DeferredBlock<Block>> registerColoredCopperSlabs(){
        List<DeferredBlock<Block>> slabs = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock("waxed_" + color.getName() + "_cut_copper_slab",
                    () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_SLAB).mapColor(color)));
            slabs.add(block);
        }
        return slabs;
    }

    private static List<DeferredBlock<Block>> registerColoredCopperDoors(){
        List<DeferredBlock<Block>> doors = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( "waxed_" + color.getName() + "_copper_door",
                    () -> new DoorBlock(BlockSetType.COPPER, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_DOOR).mapColor(color)));
            doors.add(block);
        }
        return doors;
    }

    private static List<DeferredBlock<Block>> registerColoredCopperTrapdoors(){
        List<DeferredBlock<Block>> trapdoors = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( "waxed_" + color.getName() + "_copper_trapdoor",
                    () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(color).isValidSpawn(Blocks::never)));
            trapdoors.add(block);
        }
        return trapdoors;
    }

    private static List<DeferredBlock<Block>> registerClassicWools() {
        List<DeferredBlock<Block>> wools = new ArrayList<>();
        for (String color : CLASSIC_COLORS) {
            DeferredBlock<Block> block = registerBlock(color + "_wool",
                    () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL)));
            wools.add(block);
        }
        return wools;
    }

    private static List<DeferredBlock<Block>> registerClassicCarpets() {
        List<DeferredBlock<Block>> wools = new ArrayList<>();
        for (String color : CLASSIC_COLORS) {
            DeferredBlock<Block> block = registerBlock(color + "_carpet",
                    () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CARPET)));
            wools.add(block);
        }
        return wools;
    }

    private static List<DeferredBlock<Block>> registerGlazedConcrete() {
        List<DeferredBlock<Block>> glazedConcrete = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_glazed_concrete",
                    () -> new GlazedTerracottaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_GLAZED_TERRACOTTA).mapColor(color)));
            glazedConcrete.add(block);
        }
        return glazedConcrete;
    }

    private static List<DeferredBlock<Block>> registerColoredLeaves(){
        List<DeferredBlock<Block>> leaves = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock(color.getName() + "_leaves",
                    () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(color)
                            .isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never).isRedstoneConductor(ModBlocks::never)));
            leaves.add(block);
        }
        return leaves;
    }

    private static List<DeferredBlock<Block>> registerColoredLogs(String key, boolean isStripped){
        List<DeferredBlock<Block>> log = new ArrayList<>();
        for (DyeColor color : COLORS) {
            String name = isStripped ? "stripped_" + color.getName() + "_" + key : color.getName() + "_" + key;
            DeferredBlock<Block> block = registerBlock(name,
                    () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(color)));
            log.add(block);
        }
        return log;
    }

    private static List<DeferredBlock<Block>> registerColoredStairs(){
        List<DeferredBlock<Block>> stairs = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredBlock<Block> block = registerBlock( color.getName() + "_stairs",
                    () -> new StairBlock(COLORED_PLANKS.get(index).get().defaultBlockState(),
                            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).mapColor(color)));
            stairs.add(block);
        }
        return stairs;
    }

    private static List<DeferredBlock<Block>> registerColoredSlabs(){
        List<DeferredBlock<Block>> slabs = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_slab",
                    () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(color)));
            slabs.add(block);
        }
        return slabs;
    }

    private static List<DeferredBlock<Block>> registerColoredFences(){
        List<DeferredBlock<Block>> fence = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_fence",
                    () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(color)));
            fence.add(block);
        }
        return fence;
    }

    private static List<DeferredBlock<Block>> registerColoredFenceGates(){
        List<DeferredBlock<Block>> fenceGate = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_fence_gate",
                    () -> new FenceGateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(color),
                            SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE));
            fenceGate.add(block);
        }
        return fenceGate;
    }

    private static List<DeferredBlock<Block>> registerColoredDoors(){
        List<DeferredBlock<Block>> doors = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_door",
                    () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(color)));
            doors.add(block);
        }
        return doors;
    }

    private static List<DeferredBlock<Block>> registerColoredTrapdoors(){
        List<DeferredBlock<Block>> trapdoors = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_trapdoor",
                    () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(color).isValidSpawn(Blocks::never)));
            trapdoors.add(block);
        }
        return trapdoors;
    }

    private static List<DeferredBlock<Block>> registerColoredPressurePlates(){
        List<DeferredBlock<Block>> pressurePlates = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_pressure_plate",
                    () -> new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(color)));
            pressurePlates.add(block);
        }
        return pressurePlates;
    }

    private static List<DeferredBlock<Block>> registerColoredButtons(){
        List<DeferredBlock<Block>> button = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_button",
                    () -> new ButtonBlock(BlockSetType.OAK, 30,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).mapColor(color)));
            button.add(block);
        }
        return button;
    }

    private static List<DeferredBlock<Block>> registerColoredSigns() {
        List<DeferredBlock<Block>> signs = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredBlock<Block> block = BLOCKS.register( color.getName() + "_sign",
                    () -> new StandingSignBlock(ModWoodType.COLORED_WOODS.get(index), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(color)));
            signs.add(block);
        }
        return signs;
    }

    private static List<DeferredBlock<Block>> registerColoredWallSign() {
        List<DeferredBlock<Block>> wallSign = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredBlock<Block> block = BLOCKS.register( color.getName() + "_wall_sign",
                    () -> new WallSignBlock(ModWoodType.COLORED_WOODS.get(index), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(color)));
            wallSign.add(block);
        }
        return wallSign;
    }

    private static List<DeferredBlock<Block>> registerColoredHangingSign() {
        List<DeferredBlock<Block>> hangingSign = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredBlock<Block> block = BLOCKS.register( color.getName() + "_hanging_sign",
                    () -> new CeilingHangingSignBlock(ModWoodType.COLORED_WOODS.get(index), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(color)));
            hangingSign.add(block);
        }
        return hangingSign;
    }

    private static List<DeferredBlock<Block>> registerColoredWallHangingSign(){
        List<DeferredBlock<Block>> hangingSign = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredBlock<Block> block = BLOCKS.register( color.getName() + "_wall_hanging_sign",
                    () -> new WallHangingSignBlock(ModWoodType.COLORED_WOODS.get(index), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(color)));
            hangingSign.add(block);
        }
        return hangingSign;
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> deferredBlock = BLOCKS.register(name, block);
        registerBlockItem(name, deferredBlock);
        return deferredBlock;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
//        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        ModItems.ITEMS.registerSimpleBlockItem(name, block, new Item.Properties());
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
