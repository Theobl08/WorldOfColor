package net.theobl.worldofcolor.block;

import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.grower.ModTreeGrower;
import net.theobl.worldofcolor.item.ModItems;
import org.apache.commons.lang3.function.TriFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.theobl.worldofcolor.util.ModUtil.*;

public class ModBlocks {
    // Create a Deferred Register to hold Blocks which will all be registered under the "worldofcolor" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(WorldOfColor.MODID);

    public static final List<DeferredBlock<Block>> CLASSIC_WOOLS = registerClassic("wool", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL).mapColor(MapColor.WOOL));
    public static final List<DeferredBlock<Block>> CLASSIC_CARPETS = registerClassic("carpet", CarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CARPET).mapColor(MapColor.WOOL));
    public static final List<DeferredBlock<Block>> SIMPLE_COLORED_BLOCKS = registerColored("block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final List<DeferredBlock<Block>> QUILTED_CONCRETES = registerColored("quilted_concrete", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final List<DeferredBlock<Block>> GLAZED_CONCRETES = registerColored("glazed_concrete", GlazedTerracottaBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_GLAZED_TERRACOTTA));
    public static final List<DeferredBlock<Block>> COLORED_LIGHTNING_RODS = registerColored("lightning_rod", p -> new WeatheringLightningRodBlock(WeatherState.UNAFFECTED, p), BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHTNING_ROD));
    public static final List<DeferredBlock<Block>> COLORED_WAXED_LIGHTNING_RODS = registerColored("lightning_rod", LightningRodBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHTNING_ROD), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_SAPLINGS = registerColoredSaplings();
    public static final List<DeferredBlock<Block>> COLORED_LEAVES = registerColoredLeaves();
    public static final List<DeferredBlock<Block>> COLORED_LOGS = registerColored("log", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG));
    public static final List<DeferredBlock<Block>> COLORED_WOODS = registerColored("wood", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD));
    public static final List<DeferredBlock<Block>> COLORED_STRIPPED_LOGS = registerColored("log", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG), "stripped_");
    public static final List<DeferredBlock<Block>> COLORED_STRIPPED_WOODS = registerColored("wood", RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD), "stripped_");
    public static final List<DeferredBlock<Block>> COLORED_PLANKS = registerColored("planks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final List<DeferredBlock<Block>> COLORED_STAIRS = registerColoredStairs("stairs", COLORED_PLANKS, StairBlock::new);
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
    public static final List<DeferredBlock<Block>> COLORED_SIGNS = registerColoredSigns("sign", StandingSignBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN));
    public static final List<DeferredBlock<Block>> COLORED_WALL_SIGNS = registerColoredSigns("wall_sign", WallSignBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN));
    public static final List<DeferredBlock<Block>> COLORED_HANGING_SIGNS = registerColoredSigns("hanging_sign", CeilingHangingSignBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN));
    public static final List<DeferredBlock<Block>> COLORED_WALL_HANGING_SIGNS = registerColoredSigns("wall_hanging_sign", WallHangingSignBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN));
    public static final List<DeferredBlock<Block>> COLORED_COPPER_BLOCKS = registerColoredWeathering("copper_block", WeatheringCopperFullBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK));
    public static final List<DeferredBlock<Block>> COLORED_CHISELED_COPPER = registerColoredWeathering("chiseled_copper", WeatheringCopperFullBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK));
    public static final List<DeferredBlock<Block>> COLORED_COPPER_GRATES = registerColoredWeathering("copper_grate", WeatheringCopperGrateBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE));
    public static final List<DeferredBlock<Block>> COLORED_CUT_COPPER = registerColoredWeathering("cut_copper", WeatheringCopperFullBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK));
    public static final List<DeferredBlock<Block>> COLORED_CUT_COPPER_STAIRS = registerColoredStairs("cut_copper_stairs", COLORED_CUT_COPPER,
            (blockState, p) -> new WeatheringCopperStairBlock(WeatherState.UNAFFECTED, blockState, p));
    public static final List<DeferredBlock<Block>> COLORED_CUT_COPPER_SLABS = registerColoredWeathering("cut_copper_slab", WeatheringCopperSlabBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK));
    public static final List<DeferredBlock<Block>> COLORED_COPPER_DOORS = registerColored("copper_door", p -> new WeatheringCopperDoorBlock(BlockSetType.COPPER, WeatherState.UNAFFECTED, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_DOOR));
    public static final List<DeferredBlock<Block>> COLORED_COPPER_TRAPDOORS = registerColored("copper_trapdoor", p -> new WeatheringCopperTrapDoorBlock(BlockSetType.COPPER, WeatherState.UNAFFECTED, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_TRAPDOOR));
    public static final List<DeferredBlock<Block>> COLORED_COPPER_BULBS = registerColoredWeathering("copper_bulb", WeatheringCopperBulbBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BULB));
    public static final List<DeferredBlock<Block>> COLORED_COPPER_BARS = registerColoredWeathering("copper_bars", WeatheringCopperBarsBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BARS.unaffected()));
    public static final List<DeferredBlock<Block>> COLORED_COPPER_CHAINS = registerColoredWeathering("copper_chain", WeatheringCopperChainBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_CHAIN.unaffected()));
    public static final List<DeferredBlock<Block>> COLORED_COPPER_LANTERNS = registerColoredWeathering("copper_lantern", WeatheringLanternBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_LANTERN.unaffected()));
    public static final List<DeferredBlock<Block>> COLORED_COPPER_CHESTS = registerColored("copper_chest",
            p -> new WeatheringCopperChestBlock(WeatherState.OXIDIZED, SoundEvents.COPPER_CHEST_OPEN, SoundEvents.COPPER_CHEST_CLOSE, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_CHEST));
    public static final List<DeferredBlock<Block>> COLORED_COPPER_GOLEM_STATUES = registerColoredCopperGolemStatues(ColoredWeatheringCopperGolemStatueBlock::new, "");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_BLOCKS = registerColored("copper_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_CHISELED_COPPER = registerColored("chiseled_copper", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_GRATES = registerColored("copper_grate", WaterloggedTransparentBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_CUT_COPPER = registerColored("cut_copper", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_CUT_COPPER_STAIRS = registerColoredStairs("cut_copper_stairs", COLORED_WAXED_CUT_COPPER,
            StairBlock::new, "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_CUT_COPPER_SLABS = registerColored("cut_copper_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_DOORS = registerColored("copper_door", p -> new DoorBlock(BlockSetType.COPPER, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_DOOR), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_TRAPDOORS = registerColored("copper_trapdoor", p -> new TrapDoorBlock(BlockSetType.COPPER, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_TRAPDOOR), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_BULBS = registerColored("copper_bulb", CopperBulbBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BULB), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_BARS = registerColored("copper_bars", IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BARS.waxed()), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_CHAINS = registerColored("copper_chain", ChainBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_CHAIN.waxed()), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_LANTERNS = registerColored("copper_lantern", LanternBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_LANTERN.waxed()), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_CHESTS = registerColored("copper_chest",
            p -> new CopperChestBlock(WeatherState.OXIDIZED, SoundEvents.COPPER_CHEST_OPEN, SoundEvents.COPPER_CHEST_CLOSE, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_CHEST), "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_WAXED_COPPER_GOLEM_STATUES = registerColoredCopperGolemStatues(ColoredCopperGolemStatueBlock::new, "waxed_");
    public static final List<DeferredBlock<Block>> COLORED_CAULDRONS = registerColored(
            "cauldron",
            ColoredCauldronBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON)
    );
    public static final List<DeferredBlock<Block>> COLORED_WATER_CAULDRONS = registerColored("water_cauldron", p -> new LayeredCauldronBlock(Biome.Precipitation.RAIN, CauldronInteraction.WATER, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.WATER_CAULDRON), false);
    public static final List<DeferredBlock<Block>> COLORED_LAVA_CAULDRONS = registerColored("lava_cauldron", LavaCauldronBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LAVA_CAULDRON), false);
    public static final List<DeferredBlock<Block>> COLORED_POWDER_SNOW_CAULDRONS = registerColored("powder_snow_cauldron", p -> new LayeredCauldronBlock(Biome.Precipitation.SNOW, CauldronInteraction.POWDER_SNOW, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.POWDER_SNOW_CAULDRON), false);
    public static final List<DeferredBlock<Block>> COLORED_BRICKS = registerColored("bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS));
    public static final List<DeferredBlock<Block>> COLORED_BRICK_STAIRS = registerColoredStairs("brick_stairs", COLORED_BRICKS, StairBlock::new);
    public static final List<DeferredBlock<Block>> COLORED_BRICK_SLABS = registerColored("brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB));
    public static final List<DeferredBlock<Block>> COLORED_BRICK_WALLS = registerColored("brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_WALL));
    public static final List<DeferredBlock<Block>> COLORED_SLIME_BLOCKS = registerColored(
            "slime_block",
            ColoredSlimeBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)
    );
    public static final List<DeferredBlock<Block>> POTTED_COLORED_SAPLINGS = registerPottedColoredSaplings();
    public static final List<DeferredBlock<FlowerPotBlock>> COLORED_FLOWER_POTS = registerColored("flower_pot", p -> new FlowerPotBlock(null, () -> Blocks.AIR, p), BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final Map<Supplier<Block>, List<DeferredBlock<Block>>> COLORED_POTTED_PLANTS = registerColoredPottedPlant();
    public static final List<DeferredBlock<ColoredDecoratedPotBlock>> COLORED_DECORATED_POTS = registerColored(
            "decorated_pot",
            ColoredDecoratedPotBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT)
    );
    public static final List<DeferredBlock<Block>> COLORED_REDSTONE_LAMPS = registerColored(
            "redstone_lamp",
            RedstoneLampBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_LAMP)
    );

    private static List<DeferredBlock<Block>> registerClassic(String key, Function<BlockBehaviour.Properties, Block> block, BlockBehaviour.Properties properties) {
        List<DeferredBlock<Block>> blocks = new ArrayList<>();
        for (String color : CLASSIC_COLORS) {
            String name = color.concat("_").concat(key);
            DeferredBlock<Block> deferredBlock = registerBlock(name, block, () -> properties.mapColor(CLASSIC_COLORS_MAP_COLOR.get(CLASSIC_COLORS.indexOf(color))));
            blocks.add(deferredBlock);
        }
        return blocks;
    }

    private static List<DeferredBlock<Block>> registerColoredSaplings() {
        List<DeferredBlock<Block>> saplings = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredBlock<Block> block = registerBlock(color.getName() + "_sapling",
                    p -> new SaplingBlock(ModTreeGrower.COLORED_TREES.get(index), p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(color));
            saplings.add(block);
            POTTABLE_PLANTS.add(block);
        }
        return saplings;
    }

    private static List<DeferredBlock<Block>> registerPottedColoredSaplings() {
        List<DeferredBlock<Block>> saplings = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredBlock<Block> block = BLOCKS.registerBlock("potted_" + COLORED_SAPLINGS.get(index).getId().getPath(),
                    p -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, COLORED_SAPLINGS.get(index), p),
                    () -> BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT));
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(COLORED_SAPLINGS.get(index).getId(), block);
            saplings.add(block);
        }
        return saplings;
    }

    private static Map<Supplier<Block>, List<DeferredBlock<Block>>> registerColoredPottedPlant() {
        Map<Supplier<Block>, List<DeferredBlock<Block>>> pottedPlants = new HashMap<>();
        for (Supplier<Block> plant : POTTABLE_PLANTS) {
            List<DeferredBlock<Block>> blockList = new ArrayList<>();
            for (DyeColor color : COLORS) {
                int index = COLORS.indexOf(color);
                DeferredBlock<Block> block = BLOCKS.registerBlock(color.getName() + "_potted_" + name(plant),
                        p -> new ColoredFlowerPotBlock(COLORED_FLOWER_POTS.get(index), plant, color, p),
                        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT).mapColor(color).randomTicks());
                blockList.add(block);
            }
            pottedPlants.put(plant, blockList);
        }
        return pottedPlants;
    }

    private static List<DeferredBlock<Block>> registerColoredLeaves() {
        List<DeferredBlock<Block>> leaves = new ArrayList<>();
        for (DyeColor color : COLORS) {
            DeferredBlock<Block> block = registerBlock(color.getName() + "_leaves",
                    p -> new UntintedParticleLeavesBlock(0.01F, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, color.getTextureDiffuseColor()), p),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(color));
            leaves.add(block);
        }
        return leaves;
    }

    private static List<DeferredBlock<Block>> registerColoredStairs(String key, List<DeferredBlock<Block>> baseState, BiFunction<BlockState, BlockBehaviour.Properties, Block> factory, String prefix) {
        List<DeferredBlock<Block>> stairs = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            String name = prefix.concat(color.getName()).concat("_").concat(key);
            DeferredBlock<Block> block = registerBlock(name,
                    p -> factory.apply(baseState.get(index).get().defaultBlockState(), p),
                    () -> BlockBehaviour.Properties.ofFullCopy(baseState.get(index).get()).mapColor(color));
            stairs.add(block);
        }
        return stairs;
    }

    private static List<DeferredBlock<Block>> registerColoredStairs(String key, List<DeferredBlock<Block>> baseState, BiFunction<BlockState, BlockBehaviour.Properties, Block> factory) {
        return registerColoredStairs(key, baseState, factory, "");
    }

    private static List<DeferredBlock<Block>> registerColoredSigns(String key, BiFunction<WoodType, BlockBehaviour.Properties, Block> block, BlockBehaviour.Properties properties) {
        List<DeferredBlock<Block>> signs = new ArrayList<>();
        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            DeferredBlock<Block> deferredBlock = BLOCKS.registerBlock(color.getName() + "_" + key, p -> block.apply(ModWoodType.COLORED_WOODS.get(index), p), () -> properties.mapColor(color));
            signs.add(deferredBlock);
        }
        return signs;
    }

    private static List<DeferredBlock<Block>> registerColoredCopperGolemStatues(TriFunction<WeatherState, DyeColor, BlockBehaviour.Properties, Block> func, String prefix) {
        List<DeferredBlock<Block>> blocks = new ArrayList<>();
        for (DyeColor color : COLORS) {
            String name = prefix + color.getName() + "_copper_golem_statue";
            DeferredBlock<Block> deferredBlock = BLOCKS.registerBlock(name,
                    p -> func.apply(WeatherState.OXIDIZED, color, p),
                    () -> BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GOLEM_STATUE).mapColor(color));
            ModItems.ITEMS.registerSimpleBlockItem(deferredBlock, p -> new Item.Properties()
                            .component(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(CopperGolemStatueBlock.POSE, CopperGolemStatueBlock.Pose.STANDING)));
            blocks.add(deferredBlock);
        }
        return blocks;
    }

    private static <T extends Block> List<DeferredBlock<T>> registerColored(String key, BiFunction<DyeColor, BlockBehaviour.Properties, ? extends T> block, BlockBehaviour.Properties properties) {
        List<DeferredBlock<T>> blocks = new ArrayList<>();
        for (DyeColor color : COLORS) {
            String name = color.getName() + "_" + key;
            DeferredBlock<T> deferredBlock = BLOCKS.registerBlock(name, p -> block.apply(color, p), () -> properties.mapColor(color));
            if(key.contains("slime_block"))
                ModItems.ITEMS.registerSimpleBlockItem(deferredBlock);
            blocks.add(deferredBlock);
        }
        return blocks;
    }

    private static <T extends Block> List<DeferredBlock<T>> registerColoredWeathering(String key, BiFunction<WeatherState, BlockBehaviour.Properties, ? extends T> block, BlockBehaviour.Properties properties) {
        return registerColored(key, p -> block.apply(WeatherState.UNAFFECTED, p), properties);
    }

    private static <T extends Block> List<DeferredBlock<T>> registerColored(String key, Function<BlockBehaviour.Properties, ? extends T> block, BlockBehaviour.Properties properties, String prefix, boolean hasItem) {
        List<DeferredBlock<T>> blocks = new ArrayList<>();
        for (DyeColor color : COLORS) {
            String name = prefix.concat(color.getName()).concat("_").concat(key);
            DeferredBlock<T> deferredBlock = BLOCKS.registerBlock(name, block, () -> properties.mapColor(color));
            if(hasItem)
                registerBlockItem(name, deferredBlock);
            blocks.add(deferredBlock);
        }
        return blocks;
    }

    private static <T extends Block> List<DeferredBlock<T>> registerColored(String key, Function<BlockBehaviour.Properties, ? extends T> block, BlockBehaviour.Properties properties) {
        return registerColored(key, block, properties, "");
    }

    private static <T extends Block> List<DeferredBlock<T>> registerColored(String key, Function<BlockBehaviour.Properties, ? extends T> block, BlockBehaviour.Properties properties, String prefix) {
        return registerColored(key, block, properties, prefix, true);
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends Block> List<DeferredBlock<T>> registerColored(String key, Function<BlockBehaviour.Properties, ? extends T> block, BlockBehaviour.Properties properties, boolean hasItem) {
        return registerColored(key, block, properties, "", hasItem);
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, ? extends T> block, BlockBehaviour.Properties properties) {
        DeferredBlock<T> deferredBlock = BLOCKS.registerBlock(name, block, () -> properties);
        registerBlockItem(name, deferredBlock);
        return deferredBlock;
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, ? extends T> block, Supplier<BlockBehaviour.Properties> properties) {
        DeferredBlock<T> deferredBlock = BLOCKS.registerBlock(name, block, properties);
        registerBlockItem(name, deferredBlock);
        return deferredBlock;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.registerSimpleBlockItem(name, block);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
