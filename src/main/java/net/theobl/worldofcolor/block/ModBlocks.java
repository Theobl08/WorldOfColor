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
    public static final List<DeferredBlock<Block>> SIMPLE_COLORED_BLOCKS = registerSimpleColoredBlock();
    public static final List<DeferredBlock<Block>> QUILTED_CONCRETES = registerQuiltedConcrete();
    public static final List<DeferredBlock<Block>> GLAZED_CONCRETES = registerGlazedConcrete();
    public static final List<DeferredBlock<Block>> COLORED_LEAVES = registerColoredLeaves();
    public static final List<DeferredBlock<Block>> COLORED_LOGS = registerColoredLogs(false);
    public static final List<DeferredBlock<Block>> COLORED_WOODS = registerColoredWoods(false);
    public static final List<DeferredBlock<Block>> COLORED_STRIPPED_LOGS = registerColoredLogs(true);
    public static final List<DeferredBlock<Block>> COLORED_STRIPPED_WOODS = registerColoredWoods(true);
    public static final List<DeferredBlock<Block>> COLORED_PLANKS = registerColoredPlanks();
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

    private static List<DeferredBlock<Block>> registerSimpleColoredBlock() {
        List<DeferredBlock<Block>> quiltedConcrete = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_block",
                    () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE).mapColor(color.getMapColor())));
            quiltedConcrete.add(block);
        }
        return quiltedConcrete;
    }

    private static List<DeferredBlock<Block>> registerQuiltedConcrete() {
        List<DeferredBlock<Block>> quiltedConcrete = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_quilted_concrete",
                    () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE).mapColor(color.getMapColor())));
            quiltedConcrete.add(block);
        }
        return quiltedConcrete;
    }

    private static List<DeferredBlock<Block>> registerGlazedConcrete() {
        List<DeferredBlock<Block>> glazedConcrete = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_glazed_concrete",
                    () -> new GlazedTerracottaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_GLAZED_TERRACOTTA).mapColor(color.getMapColor())));
            glazedConcrete.add(block);
        }
        return glazedConcrete;
    }

    private static List<DeferredBlock<Block>> registerColoredLeaves(){
        List<DeferredBlock<Block>> leaves = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock(color.getName() + "_leaves",
                    () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(color.getMapColor())
                            .isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never).isRedstoneConductor(ModBlocks::never)));
            leaves.add(block);
        }
        return leaves;
    }

    private static List<DeferredBlock<Block>> registerColoredLogs(boolean isStripped){
        List<DeferredBlock<Block>> log = new ArrayList<>();
        for (DyeColor color : COLORS) {
            String name = isStripped ? "stripped_" + color.getName() + "_log" : color.getName() + "_log";
            DeferredBlock<Block> block = registerBlock(name,
                    () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(color.getMapColor())));
            log.add(block);
        }
        return log;
    }

    private static List<DeferredBlock<Block>> registerColoredWoods(boolean isStripped){
        List<DeferredBlock<Block>> log = new ArrayList<>();
        for (DyeColor color : COLORS) {
            String name = isStripped ? "stripped_" + color.getName() + "_wood" : color.getName() + "_wood";
            DeferredBlock<Block> block = registerBlock(name,
                    () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(color.getMapColor())));
            log.add(block);
        }
        return log;
    }

    private static List<DeferredBlock<Block>> registerColoredPlanks(){
        List<DeferredBlock<Block>> planks = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_planks",
                    () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(color.getMapColor())));
            planks.add(block);
        }
        return planks;
    }

    private static List<DeferredBlock<Block>> registerColoredStairs(){
        List<DeferredBlock<Block>> stairs = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredBlock<Block> block = registerBlock( color.getName() + "_stairs",
                    () -> new StairBlock(COLORED_PLANKS.get(index).get().defaultBlockState(),
                            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).mapColor(color.getMapColor())));
            stairs.add(block);
        }
        return stairs;
    }

    private static List<DeferredBlock<Block>> registerColoredSlabs(){
        List<DeferredBlock<Block>> slabs = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_slab",
                    () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(color.getMapColor())));
            slabs.add(block);
        }
        return slabs;
    }

    private static List<DeferredBlock<Block>> registerColoredFences(){
        List<DeferredBlock<Block>> fence = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_fence",
                    () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(color.getMapColor())));
            fence.add(block);
        }
        return fence;
    }

    private static List<DeferredBlock<Block>> registerColoredFenceGates(){
        List<DeferredBlock<Block>> fenceGate = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_fence_gate",
                    () -> new FenceGateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).mapColor(color.getMapColor()),
                            SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE));
            fenceGate.add(block);
        }
        return fenceGate;
    }

    private static List<DeferredBlock<Block>> registerColoredDoors(){
        List<DeferredBlock<Block>> doors = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_door",
                    () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(color.getMapColor())));
            doors.add(block);
        }
        return doors;
    }

    private static List<DeferredBlock<Block>> registerColoredTrapdoors(){
        List<DeferredBlock<Block>> trapdoors = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_trapdoor",
                    () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(color.getMapColor()).isValidSpawn(Blocks::never)));
            trapdoors.add(block);
        }
        return trapdoors;
    }

    private static List<DeferredBlock<Block>> registerColoredPressurePlates(){
        List<DeferredBlock<Block>> pressurePlates = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_pressure_plate",
                    () -> new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(color.getMapColor())));
            pressurePlates.add(block);
        }
        return pressurePlates;
    }

    private static List<DeferredBlock<Block>> registerColoredButtons(){
        List<DeferredBlock<Block>> button = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock( color.getName() + "_button",
                    () -> new ButtonBlock(BlockSetType.OAK, 30,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).mapColor(color.getMapColor())));
            button.add(block);
        }
        return button;
    }

    private static List<DeferredBlock<Block>> registerColoredSigns() {
        List<DeferredBlock<Block>> signs = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredBlock<Block> block = BLOCKS.register( color.getName() + "_sign",
                    () -> new StandingSignBlock(ModWoodType.COLORED_WOODS.get(index), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)));
            signs.add(block);
        }
        return signs;
    }

    private static List<DeferredBlock<Block>> registerColoredWallSign() {
        List<DeferredBlock<Block>> wallSign = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredBlock<Block> block = BLOCKS.register( color.getName() + "_wall_sign",
                    () -> new WallSignBlock(ModWoodType.COLORED_WOODS.get(index), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)));
            wallSign.add(block);
        }
        return wallSign;
    }

    private static List<DeferredBlock<Block>> registerColoredHangingSign() {
        List<DeferredBlock<Block>> hangingSign = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredBlock<Block> block = BLOCKS.register( color.getName() + "_hanging_sign",
                    () -> new CeilingHangingSignBlock(ModWoodType.COLORED_WOODS.get(index), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)));
            hangingSign.add(block);
        }
        return hangingSign;
    }

    private static List<DeferredBlock<Block>> registerColoredWallHangingSign(){
        List<DeferredBlock<Block>> hangingSign = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredBlock<Block> block = BLOCKS.register( color.getName() + "_wall_hanging_sign",
                    () -> new WallHangingSignBlock(ModWoodType.COLORED_WOODS.get(index), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)));
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
