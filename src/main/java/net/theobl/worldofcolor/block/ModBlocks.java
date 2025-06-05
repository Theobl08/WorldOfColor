package net.theobl.worldofcolor.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.grower.ModTreeGrower;
import net.theobl.worldofcolor.item.ModItems;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.theobl.worldofcolor.util.ModUtil.*;

public class ModBlocks {
    // Create a Deferred Register to hold Blocks which will all be registered under the "worldofcolor" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(WorldOfColor.MODID);

    public static final List<DeferredBlock<Block>> CLASSIC_WOOLS = registerClassic("wool", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL).mapColor(MapColor.WOOL)));
    public static final List<DeferredBlock<Block>> CLASSIC_CARPETS = registerClassic("carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CARPET).mapColor(MapColor.WOOL)));
    public static final List<DeferredBlock<Block>> SIMPLE_COLORED_BLOCKS = registerColored("block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final List<DeferredBlock<Block>> QUILTED_CONCRETES = registerColored("quilted_concrete", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final List<DeferredBlock<Block>> GLAZED_CONCRETES = registerColored("glazed_concrete", GlazedTerracottaBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_GLAZED_TERRACOTTA));
    public static final List<DeferredBlock<Block>> COLORED_SAPLINGS = registerColoredSaplings();
    public static final List<DeferredBlock<Block>> COLORED_LEAVES = registerColored("leaves", LeavesBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final List<DeferredBlock<Block>> COLORED_LOGS = registerColored("log", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG));
    public static final List<DeferredBlock<Block>> COLORED_WOODS = registerColored("wood", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));
    public static final List<DeferredBlock<Block>> COLORED_STRIPPED_LOGS = registerColored("log", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG), "stripped_");
    public static final List<DeferredBlock<Block>> COLORED_STRIPPED_WOODS = registerColored("wood", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD), "stripped_");
    public static final List<DeferredBlock<Block>> COLORED_PLANKS = registerColored("planks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final List<DeferredBlock<Block>> COLORED_STAIRS = registerColoredStairs("stairs", COLORED_PLANKS, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final List<DeferredBlock<Block>> COLORED_SLABS = registerColored("slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final List<DeferredBlock<Block>> COLORED_FENCES = registerColored("fence", FenceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE));
    public static final List<DeferredBlock<Block>> COLORED_FENCE_GATES = registerColored("fence_gate", p -> new FenceGateBlock(p, SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE),
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE));
    public static final List<DeferredBlock<Block>> COLORED_DOORS = registerColored("door", p -> new DoorBlock(BlockSetType.OAK, p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR));
    public static final List<DeferredBlock<Block>> COLORED_TRAPDOORS = registerColored("trapdoor", p -> new TrapDoorBlock(BlockSetType.OAK, p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR));
    public static final List<DeferredBlock<Block>> COLORED_PRESSURE_PLATES = registerColored("pressure_plate", p -> new PressurePlateBlock(BlockSetType.OAK, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE));
    public static final List<DeferredBlock<Block>> COLORED_BUTTONS = registerColored("button", p -> new ButtonBlock(BlockSetType.OAK, 30, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON));
    public static final List<DeferredBlock<Block>> COLORED_SIGNS = registerColoredSigns(false, false);
    public static final List<DeferredBlock<Block>> COLORED_WALL_SIGNS = registerColoredSigns(true, false);
    public static final List<DeferredBlock<Block>> COLORED_HANGING_SIGNS = registerColoredSigns(false, true);
    public static final List<DeferredBlock<Block>> COLORED_WALL_HANGING_SIGNS = registerColoredSigns(true, true);
    public static final List<DeferredBlock<Block>> COLORED_COPPER_BLOCKS = registerColored("copper_block", p -> new WeatheringCopperFullBlock(WeatherState.UNAFFECTED, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK));
    public static final List<DeferredBlock<Block>> COLORED_CHISELED_COPPER = registerColored("chiseled_copper", p -> new WeatheringCopperFullBlock(WeatherState.UNAFFECTED, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK));
    public static final List<DeferredBlock<Block>> COLORED_COPPER_GRATES = registerColored("copper_grate", p -> new WeatheringCopperGrateBlock(WeatherState.UNAFFECTED, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE));
    public static final List<DeferredBlock<Block>> COLORED_CUT_COPPER = registerColored("cut_copper", p -> new WeatheringCopperFullBlock(WeatherState.UNAFFECTED, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK));
    public static final List<DeferredBlock<Block>> COLORED_CUT_COPPER_STAIRS = registerColoredWeatheringStairs();
    public static final List<DeferredBlock<Block>> COLORED_CUT_COPPER_SLABS = registerColored("cut_copper_slab", p -> new WeatheringCopperSlabBlock(WeatherState.UNAFFECTED, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK));
    public static final List<DeferredBlock<Block>> COLORED_COPPER_DOORS = registerColored("copper_door", p -> new WeatheringCopperDoorBlock(BlockSetType.COPPER, WeatherState.UNAFFECTED, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_DOOR));
    public static final List<DeferredBlock<Block>> COLORED_COPPER_TRAPDOORS = registerColored("copper_trapdoor", p -> new WeatheringCopperTrapDoorBlock(BlockSetType.COPPER, WeatherState.UNAFFECTED, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_TRAPDOOR));
    public static final List<DeferredBlock<Block>> COLORED_COPPER_BULBS = registerColored("copper_bulb", p -> new WeatheringCopperBulbBlock(WeatherState.UNAFFECTED, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BULB));
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_BLOCKS = registerColored("copper_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_CHISELED_COPPER = registerColored("chiseled_copper", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_GRATES = registerColored("copper_grate", WaterloggedTransparentBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_CUT_COPPER = registerColored("cut_copper", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_CUT_COPPER_STAIRS = registerColoredStairs("cut_copper_stairs", COLORED_WAXED_CUT_COPPER,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_CUT_COPPER_SLABS = registerColored("cut_copper_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_DOORS = registerColored("copper_door", p -> new DoorBlock(BlockSetType.COPPER, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_DOOR), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_TRAPDOORS = registerColored("copper_trapdoor", p -> new TrapDoorBlock(BlockSetType.COPPER, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_TRAPDOOR), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_BULBS = registerColored("copper_bulb", CopperBulbBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BULB), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_BRICKS = registerColored("bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS));
    public static final List<DeferredBlock<Block>> COLORED_BRICK_STAIRS = registerColoredStairs("brick_stairs", COLORED_BRICKS, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_STAIRS));
    public static final List<DeferredBlock<Block>> COLORED_BRICK_SLABS = registerColored("brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB));
    public static final List<DeferredBlock<Block>> COLORED_BRICK_WALLS = registerColored("brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_WALL));

    private static List<DeferredBlock<Block>> registerColoredWeatheringStairs() {
        List<DeferredBlock<Block>> blocks = new ArrayList<>();
        COLORS.forEach( color -> blocks.add(registerBlock(color.getName() + "_cut_copper_stairs",
                () -> new WeatheringCopperStairBlock(WeatheringCopper.WeatherState.UNAFFECTED, ModBlocks.COLORED_CUT_COPPER.get(COLORS.indexOf(color)).get().defaultBlockState(),
                        BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(color)))));
        return blocks;
    }

    private static List<DeferredBlock<Block>> registerClassic(String key, Supplier<Block> block) {
        List<DeferredBlock<Block>> blocks = new ArrayList<>();
        for (String color : CLASSIC_COLORS) {
            String name = color.concat("_").concat(key);
            DeferredBlock<Block> deferredBlock = registerBlock(name, block);
            blocks.add(deferredBlock);
        }
        return blocks;
    }

    private static List<DeferredBlock<Block>> registerColoredSaplings() {
        List<DeferredBlock<Block>> saplings = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredBlock<Block> block = registerBlock(color.getName() + "_sapling",
                    () -> new SaplingBlock(ModTreeGrower.COLORED_TREES.get(index), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(color)));
            saplings.add(block);
        }
        return saplings;
    }

    private static List<DeferredBlock<Block>> registerColoredStairs(String key, List<DeferredBlock<Block>> baseState, BlockBehaviour.Properties properties, String prefix) {
        List<DeferredBlock<Block>> stairs = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            String name = prefix.concat(color.getName()).concat("_").concat(key);
            DeferredBlock<Block> block = registerBlock(name,
                    () -> new StairBlock(baseState.get(index).get().defaultBlockState(),
                            properties.mapColor(color)));
            stairs.add(block);
        }
        return stairs;
    }

    private static List<DeferredBlock<Block>> registerColoredStairs(String key, List<DeferredBlock<Block>> baseState, BlockBehaviour.Properties properties) {
        return registerColoredStairs(key, baseState, properties, "");
    }

    private static List<DeferredBlock<Block>> registerColoredSigns(boolean wall, boolean hanging) {
        List<DeferredBlock<Block>> signs = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredBlock<Block> block;
            if(!wall && !hanging) // standing sign
                block = BLOCKS.register( color.getName() + "_sign",
                        () -> new StandingSignBlock(ModWoodType.COLORED_WOODS.get(index), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(color)));
            else if(wall && !hanging) // wall sign
                block = BLOCKS.register( color.getName() + "_wall_sign",
                        () -> new WallSignBlock(ModWoodType.COLORED_WOODS.get(index), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(color)));
            else if(!wall) // hanging sign
                block = BLOCKS.register( color.getName() + "_hanging_sign",
                        () -> new CeilingHangingSignBlock(ModWoodType.COLORED_WOODS.get(index), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(color)));
            else // wall hanging sign
                block = BLOCKS.register( color.getName() + "_wall_hanging_sign",
                        () -> new WallHangingSignBlock(ModWoodType.COLORED_WOODS.get(index), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(color)));
            signs.add(block);
        }
        return signs;
    }

    private static <T extends Block> List<DeferredBlock<T>> registerColored(String key, Function<BlockBehaviour.Properties, ? extends T> block, BlockBehaviour.Properties properties, String prefix) {
        List<DeferredBlock<T>> blocks = new ArrayList<>();
        for (DyeColor color : COLORS) {
            String name = prefix.concat(color.getName()).concat("_").concat(key);
            DeferredBlock<T> deferredBlock = BLOCKS.registerBlock(name, block, properties.mapColor(color));
            registerBlockItem(name, deferredBlock);
            blocks.add(deferredBlock);
        }
        return blocks;
    }

    private static <T extends Block> List<DeferredBlock<T>> registerColored(String key, Function<BlockBehaviour.Properties, ? extends T> block, BlockBehaviour.Properties properties) {
        return registerColored(key, block, properties, "");
    }

//    /**
//     * Create a new set of colored blocks.
//     *
//     * @param key the name of the new block
//     * @param clazz the class to be instanced. It must have a constructor with {@linkplain WoodType} and {@linkplain BlockBehaviour.Properties} (like {@linkplain SignBlock})
//     * @param properties the properties of the new block
//     */
//    @SuppressWarnings("unchecked")
//    private static <T extends Block> List<DeferredBlock<T>> registerColored(String key, Class<?> clazz, BlockBehaviour.Properties properties) {
//        List<DeferredBlock<T>> blocks = new ArrayList<>();
//        for (DyeColor color : COLORS) {
//            int index = COLORS.indexOf(color);
//            String name = color.getName().concat("_").concat(key);
//            DeferredBlock<T> deferredBlock = BLOCKS.registerBlock(name,
//                    props -> {
//                        try {
//                            //https://stackoverflow.com/questions/4872978/how-do-i-pass-a-class-as-a-parameter-in-java
//                            //https://stackoverflow.com/questions/15941957/passing-class-type-as-parameter-and-creating-instance-of-it
//                            return (T) clazz.getDeclaredConstructor(WoodType.class, BlockBehaviour.Properties.class).newInstance(ModWoodType.COLORED_WOODS.get(index), props);
//                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }, properties.mapColor(color));
//            if(!SignBlock.class.isAssignableFrom(clazz)) {
//                registerBlockItem(name, deferredBlock);
//            }
//            blocks.add(deferredBlock);
//        }
//        return blocks;
//    }

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
