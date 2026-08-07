package net.theobl.worldofcolor.block;

import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.grower.ModTreeGrower;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.sounds.ModSoundType;
import net.theobl.worldofcolor.util.ColorCollectionUtil;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.theobl.worldofcolor.util.ModUtil.*;

public class ModBlocks {
    // Create a Deferred Register to hold Blocks which will all be registered under the "worldofcolor" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(WorldOfColor.MODID);

    public static final List<DeferredBlock<Block>> CLASSIC_WOOLS = registerClassic(
            "wool",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.WOOL.white()).mapColor(MapColor.WOOL)
    );
    public static final List<DeferredBlock<Block>> CLASSIC_CARPETS = registerClassic(
            "carpet",
            CarpetBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.CARPET.white()).mapColor(MapColor.WOOL)
    );
    public static final ColorCollection<DeferredBlock<Block>> SIMPLE_COLORED_BLOCKS = registerColored(
            "block",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.CONCRETE.white())
    );
    public static final ColorCollection<DeferredBlock<Block>> QUILTED_CONCRETES = registerColored(
            "quilted_concrete",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.CONCRETE.white())
    );
    public static final ColorCollection<DeferredBlock<Block>> GLAZED_CONCRETES = registerColored(
            "glazed_concrete",
            GlazedTerracottaBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.GLAZED_TERRACOTTA.white())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_LIGHTNING_RODS = registerColored(
            "lightning_rod",
            p -> new WeatheringLightningRodBlock(WeatherState.UNAFFECTED, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHTNING_ROD.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WAXED_LIGHTNING_RODS = registerColored(
            "waxed_lightning_rod",
            LightningRodBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHTNING_ROD.weathering().unaffected())
    );
    public static final DeferredBlock<Block> LIGHT_GRAY_TULIP = registerFlowerBlock(
            "light_gray_tulip",
            p -> new FlowerBlock(MobEffects.WEAKNESS, 7.0F, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP)
    );
    public static final DeferredBlock<Block> GRAY_TULIP = registerFlowerBlock(
            "gray_tulip",
            p -> new FlowerBlock(MobEffects.WEAKNESS, 7.0F, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP)
    );
    public static final DeferredBlock<Block> BLACK_TULIP = registerFlowerBlock(
            "black_tulip",
            p -> new FlowerBlock(MobEffects.WEAKNESS, 7.0F, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP)
    );
    public static final DeferredBlock<Block> BROWN_TULIP = registerFlowerBlock(
            "brown_tulip",
            p -> new FlowerBlock(MobEffects.WEAKNESS, 7.0F, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP)
    );
    public static final DeferredBlock<Block> YELLOW_TULIP = registerFlowerBlock(
            "yellow_tulip",
            p -> new FlowerBlock(MobEffects.WEAKNESS, 7.0F, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP)
    );
    public static final DeferredBlock<Block> LIME_TULIP = registerFlowerBlock(
            "lime_tulip",
            p -> new FlowerBlock(MobEffects.WEAKNESS, 7.0F, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP)
    );
    public static final DeferredBlock<Block> GREEN_TULIP = registerFlowerBlock(
            "green_tulip",
            p -> new FlowerBlock(MobEffects.WEAKNESS, 7.0F, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP)
    );
    public static final DeferredBlock<Block> CYAN_TULIP = registerFlowerBlock(
            "cyan_tulip",
            p -> new FlowerBlock(MobEffects.WEAKNESS, 7.0F, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP)
    );
    public static final DeferredBlock<Block> LIGHT_BLUE_TULIP = registerFlowerBlock(
            "light_blue_tulip",
            p -> new FlowerBlock(MobEffects.WEAKNESS, 7.0F, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP)
    );
    public static final DeferredBlock<Block> BLUE_TULIP = registerFlowerBlock(
            "blue_tulip",
            p -> new FlowerBlock(MobEffects.WEAKNESS, 7.0F, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP)
    );
    public static final DeferredBlock<Block> PURPLE_TULIP = registerFlowerBlock(
            "purple_tulip",
            p -> new FlowerBlock(MobEffects.WEAKNESS, 7.0F, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP)
    );
    public static final DeferredBlock<Block> MAGENTA_TULIP = registerFlowerBlock(
            "magenta_tulip",
            p -> new FlowerBlock(MobEffects.WEAKNESS, 7.0F, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_SAPLINGS = ColorCollectionUtil.registerBlocks(
            createSimpleColored("sapling"),
            ModBlocks::registerFlowerBlock,
            (color, p) -> new SaplingBlock(ModTreeGrower.COLORED_TREES.pick(color), p),
            color -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(color)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_LEAVES = registerColored(
            "leaves",
            (color, p) -> new UntintedParticleLeavesBlock(0.01F, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, color.getTextureDiffuseColor()), p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_LOGS = registerColored(
            "log",
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WOODS = registerColored(
            "wood",
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_STRIPPED_LOGS = registerColored(
            "stripped_log",
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_STRIPPED_WOODS = registerColored(
            "stripped_wood",
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_PLANKS = registerColored(
            "planks",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_STAIRS = registerColoredStairs("stairs", COLORED_PLANKS, StairBlock::new);
    public static final ColorCollection<DeferredBlock<Block>> COLORED_SLABS = registerColored(
            "slab",
            SlabBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_FENCES = registerColored(
            "fence",
            FenceBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_FENCE_GATES = registerColored(
            "fence_gate",
            p -> new FenceGateBlock(p, SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE),
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_DOORS = registerColored(
            "door",
            p -> new DoorBlock(BlockSetType.OAK, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_TRAPDOORS = registerColored(
            "trapdoor",
            p -> new TrapDoorBlock(BlockSetType.OAK, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_PRESSURE_PLATES = registerColored(
            "pressure_plate",
            p -> new PressurePlateBlock(BlockSetType.OAK, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_BUTTONS = registerColored(
            "button",
            p -> new ButtonBlock(BlockSetType.OAK, 30, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_SIGNS = registerColored(
            "sign",
            (color, p) -> new StandingSignBlock(ModWoodType.COLORED_WOODS.pick(color), p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WALL_SIGNS = registerColored(
            "wall_sign",
            (color, p) -> new WallSignBlock(ModWoodType.COLORED_WOODS.pick(color), p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_HANGING_SIGNS = registerColored(
            "hanging_sign",
            (color, p) -> new CeilingHangingSignBlock(ModWoodType.COLORED_WOODS.pick(color), p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WALL_HANGING_SIGNS = registerColored(
            "wall_hanging_sign",
            (color, p) -> new WallHangingSignBlock(ModWoodType.COLORED_WOODS.pick(color), p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_SHELVES = registerColored(
            "shelf",
            ShelfBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SHELF)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_COPPER_BLOCKS = registerColoredWeathering(
            "copper_block",
            WeatheringCopperFullBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_CHISELED_COPPER = registerColoredWeathering(
            "chiseled_copper",
            WeatheringCopperFullBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_COPPER_GRATES = registerColoredWeathering(
            "copper_grate",
            WeatheringCopperGrateBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_CUT_COPPER = registerColoredWeathering(
            "cut_copper",
            WeatheringCopperFullBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_CUT_COPPER_STAIRS = registerColoredStairs("cut_copper_stairs", COLORED_CUT_COPPER,
            (blockState, p) -> new WeatheringCopperStairBlock(WeatherState.UNAFFECTED, blockState, p)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_CUT_COPPER_SLABS = registerColoredWeathering(
            "cut_copper_slab",
            WeatheringCopperSlabBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_COPPER_DOORS = registerColored(
            "copper_door",
            p -> new WeatheringCopperDoorBlock(BlockSetType.COPPER, WeatherState.UNAFFECTED, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_DOOR.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_COPPER_TRAPDOORS = registerColored(
            "copper_trapdoor",
            p -> new WeatheringCopperTrapDoorBlock(BlockSetType.COPPER, WeatherState.UNAFFECTED, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_TRAPDOOR.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_COPPER_BULBS = registerColoredWeathering(
            "copper_bulb",
            WeatheringCopperBulbBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BULB.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_COPPER_BARS = registerColoredWeathering(
            "copper_bars",
            WeatheringCopperBarsBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BARS.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_COPPER_CHAINS = registerColoredWeathering(
            "copper_chain",
            WeatheringCopperChainBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_CHAIN.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_COPPER_LANTERNS = registerColoredWeathering(
            "copper_lantern",
            WeatheringLanternBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_LANTERN.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_COPPER_CHESTS = registerColored(
            "copper_chest",
            p -> new WeatheringCopperChestBlock(WeatherState.OXIDIZED, SoundEvents.COPPER_CHEST_OPEN, SoundEvents.COPPER_CHEST_CLOSE, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_CHEST.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_COPPER_GOLEM_STATUES = registerColored(
            "copper_golem_statue",
            (color, p) -> new ColoredWeatheringCopperGolemStatueBlock(WeatherState.OXIDIZED, color, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GOLEM_STATUE.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WAXED_COPPER_BLOCKS = registerColored(
            "waxed_copper_block",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WAXED_CHISELED_COPPER = registerColored(
            "waxed_chiseled_copper",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WAXED_COPPER_GRATES = registerColored(
            "waxed_copper_grate",
            WaterloggedTransparentBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WAXED_CUT_COPPER = registerColored(
            "waxed_cut_copper",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WAXED_CUT_COPPER_STAIRS = registerColoredStairs("waxed_cut_copper_stairs", COLORED_WAXED_CUT_COPPER,
            StairBlock::new);
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WAXED_CUT_COPPER_SLABS = registerColored(
            "waxed_cut_copper_slab",
            SlabBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WAXED_COPPER_DOORS = registerColored(
            "waxed_copper_door",
            p -> new DoorBlock(BlockSetType.COPPER, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_DOOR.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WAXED_COPPER_TRAPDOORS = registerColored(
            "waxed_copper_trapdoor",
            p -> new TrapDoorBlock(BlockSetType.COPPER, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_TRAPDOOR.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WAXED_COPPER_BULBS = registerColored(
            "waxed_copper_bulb",
            CopperBulbBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BULB.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WAXED_COPPER_BARS = registerColored(
            "waxed_copper_bars",
            IronBarsBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BARS.waxed().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WAXED_COPPER_CHAINS = registerColored(
            "waxed_copper_chain",
            ChainBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_CHAIN.waxed().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WAXED_COPPER_LANTERNS = registerColored(
            "waxed_copper_lantern",
            LanternBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_LANTERN.waxed().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WAXED_COPPER_CHESTS = registerColored(
            "waxed_copper_chest",
            p -> new CopperChestBlock(WeatherState.OXIDIZED, SoundEvents.COPPER_CHEST_OPEN, SoundEvents.COPPER_CHEST_CLOSE, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_CHEST.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WAXED_COPPER_GOLEM_STATUES = registerColored(
            "waxed_copper_golem_statue",
            (color, p) -> new ColoredCopperGolemStatueBlock(WeatherState.OXIDIZED, color, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GOLEM_STATUE.weathering().unaffected())
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_CAULDRONS = registerColored(
            "cauldron",
            ColoredCauldronBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_WATER_CAULDRONS = registerColored(
            "water_cauldron",
            p -> new LayeredCauldronBlock(Biome.Precipitation.RAIN, CauldronInteractions.WATER, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.WATER_CAULDRON)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_LAVA_CAULDRONS = registerColored(
            "lava_cauldron",
            LavaCauldronBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.LAVA_CAULDRON)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_POWDER_SNOW_CAULDRONS = registerColored(
            "powder_snow_cauldron",
            p -> new LayeredCauldronBlock(Biome.Precipitation.SNOW, CauldronInteractions.POWDER_SNOW, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.POWDER_SNOW_CAULDRON)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_BRICKS = registerColored(
            "bricks",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_BRICK_STAIRS = registerColoredStairs("brick_stairs", COLORED_BRICKS, StairBlock::new);
    public static final ColorCollection<DeferredBlock<Block>> COLORED_BRICK_SLABS = registerColored(
            "brick_slab",
            SlabBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_SLAB)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_BRICK_WALLS = registerColored(
            "brick_wall",
            WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BRICK_WALL)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_SLIME_BLOCKS = registerColored(
            "slime_block",
            ColoredSlimeBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)
    );
    public static final DeferredBlock<Block> POTTED_LIGHT_GRAY_TULIP = registerPottedFlowerBlock(LIGHT_GRAY_TULIP);
    public static final DeferredBlock<Block> POTTED_GRAY_TULIP = registerPottedFlowerBlock(GRAY_TULIP);
    public static final DeferredBlock<Block> POTTED_BLACK_TULIP = registerPottedFlowerBlock(BLACK_TULIP);
    public static final DeferredBlock<Block> POTTED_BROWN_TULIP = registerPottedFlowerBlock(BROWN_TULIP);
    public static final DeferredBlock<Block> POTTED_YELLOW_TULIP = registerPottedFlowerBlock(YELLOW_TULIP);
    public static final DeferredBlock<Block> POTTED_LIME_TULIP = registerPottedFlowerBlock(LIME_TULIP);
    public static final DeferredBlock<Block> POTTED_GREEN_TULIP = registerPottedFlowerBlock(GREEN_TULIP);
    public static final DeferredBlock<Block> POTTED_CYAN_TULIP = registerPottedFlowerBlock(CYAN_TULIP);
    public static final DeferredBlock<Block> POTTED_LIGHT_BLUE_TULIP = registerPottedFlowerBlock(LIGHT_BLUE_TULIP);
    public static final DeferredBlock<Block> POTTED_BLUE_TULIP = registerPottedFlowerBlock(BLUE_TULIP);
    public static final DeferredBlock<Block> POTTED_PURPLE_TULIP = registerPottedFlowerBlock(PURPLE_TULIP);
    public static final DeferredBlock<Block> POTTED_MAGENTA_TULIP = registerPottedFlowerBlock(MAGENTA_TULIP);
    public static final ColorCollection<DeferredBlock<Block>> POTTED_COLORED_SAPLINGS = COLORED_SAPLINGS.map(ModBlocks::registerPottedFlowerBlock);
    public static final ColorCollection<DeferredBlock<FlowerPotBlock>> COLORED_FLOWER_POTS = registerColored(
            "flower_pot",
            p -> new FlowerPotBlock(null, () -> Blocks.AIR, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)
    );
    public static final Map<ResourceKey<Block>, ColorCollection<DeferredBlock<Block>>> COLORED_POTTED_PLANTS = registerColoredPottedPlant();
    public static final ColorCollection<DeferredBlock<ColoredDecoratedPotBlock>> COLORED_DECORATED_POTS = registerColored(
            "decorated_pot",
            ColoredDecoratedPotBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DECORATED_POT)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_REDSTONE_LAMPS = registerColored(
            "redstone_lamp",
            RedstoneLampBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_LAMP)
    );
    public static final DeferredBlock<Block> DYED_WATER_CAULDRON = BLOCKS.registerBlock(
            "dyed_water_cauldron",
            p -> new DyedWaterCauldronBlock(null, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_DYED_WATER_CAULDRONS = registerColored(
            "dyed_water_cauldron",
            DyedWaterCauldronBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON)
    );
    public static final ColorCollection<DeferredBlock<Block>> COLORED_POTATO_PEELS_BLOCK = registerColored(
                "potato_peels_block",
                    Block::new,
                    BlockBehaviour.Properties.of()
                            .instrument(NoteBlockInstrument.BANJO)
                            .strength(2.0F)
                            .sound(ModSoundType.POTONE)
                            .ignitedByLava()
    );
    public static final DeferredBlock<Block> RGB_WOOL = registerBlock(
            "rgb_wool",
            Block::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WOOL.white())
    );
    public static final DeferredBlock<Block> RGB_CARPET = registerBlock(
            "rgb_carpet",
            CarpetBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CARPET.white())
    );
    public static final DeferredBlock<Block> RGB_TERRACOTTA = registerBlock(
            "rgb_terracotta",
            Block::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)
    );
    public static final DeferredBlock<Block> RGB_CONCRETE = registerBlock(
            "rgb_concrete",
            Block::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CONCRETE.white())
    );
    public static final DeferredBlock<Block> RGB_CONCRETE_POWDER = registerBlock(
            "rgb_concrete_powder",
            p -> new ConcretePowderBlock(RGB_CONCRETE.get(), p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CONCRETE_POWDER.white())
    );
    public static final DeferredBlock<Block> RGB_GLAZED_TERRACOTTA = registerBlock(
            "rgb_glazed_terracotta",
            GlazedTerracottaBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.GLAZED_TERRACOTTA.white())
    );
    public static final DeferredBlock<Block> RGB_STAINED_GLASS = registerBlock(
            "rgb_stained_glass",
            TransparentBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STAINED_GLASS.white())
    );
    public static final DeferredBlock<Block> RGB_STAINED_GLASS_PANE = registerBlock(
            "rgb_stained_glass_pane",
            IronBarsBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STAINED_GLASS_PANE.white())
    );
    public static final DeferredBlock<Block> RGB_BED = registerBlock(
            "rgb_bed",
            p -> new BedBlock(DyeColor.RED, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BED.red())
    );
    public static final DeferredBlock<Block> RGB_CANDLE = registerBlock(
            "rgb_candle",
            CandleBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE)
    );
    public static final DeferredBlock<Block> RGB_CANDLE_CAKE = BLOCKS.registerBlock(
            "rgb_candle_cake",
            p -> new CandleCakeBlock(RGB_CANDLE.get(), p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE_CAKE)
    );
    public static final DeferredBlock<Block> RGB_SHULKER_BOX = BLOCKS.registerBlock(
            "rgb_shulker_box",
            p -> new ShulkerBoxBlock(null, p),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.SHULKER_BOX)
    );
    public static final DeferredBlock<Block> RGB_BANNER = BLOCKS.registerBlock(
            "rgb_banner",
            ColoredBannerBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BANNER.white())
    );
    public static final DeferredBlock<Block> RGB_WALL_BANNER = BLOCKS.registerBlock(
            "rgb_wall_banner",
            ColoredWallBannerBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WALL_BANNER.white())
                    .overrideLootTable(RGB_BANNER.get().getLootTable())
                    .overrideDescription(RGB_BANNER.get().getDescriptionId())
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

    private static Map<ResourceKey<Block>, ColorCollection<DeferredBlock<Block>>> registerColoredPottedPlant() {
        Map<ResourceKey<Block>, ColorCollection<DeferredBlock<Block>>> pottedPlants = new HashMap<>();
        for (ResourceKey<Block> plant : POTTABLE_PLANTS) {
            pottedPlants.put(plant, ColorCollectionUtil.registerBlocks(
                    createSimpleColored("potted_" + plant.identifier().getPath()),
                    BLOCKS::registerBlock,
                    (color, p) -> new ColoredFlowerPotBlock(COLORED_FLOWER_POTS.pick(color), DeferredBlock.createBlock(plant), color, p),
                    color -> BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT).mapColor(color).randomTicks()
            ));
        }
        return pottedPlants;
    }

    private static ColorCollection<DeferredBlock<Block>> registerColoredStairs(String key, ColorCollection<DeferredBlock<Block>> baseState, BiFunction<BlockState, BlockBehaviour.Properties, Block> factory) {
        return ColorCollectionUtil.registerBlocks(
                createSimpleColored(key),
                ModBlocks::registerBlock,
                (color, p) -> factory.apply(baseState.pick(color).get().defaultBlockState(), p),
                (color) -> BlockBehaviour.Properties.ofFullCopy(baseState.pick(color).get()).mapColor(color)
        );
    }

    private static <T extends Block> ColorCollection<DeferredBlock<T>> registerColored(String key,  BiFunction<DyeColor, BlockBehaviour.Properties, T> block, BlockBehaviour.Properties properties) {
        boolean shouldAlsoRegisterItem = !(key.contains("cauldron") || key.contains("copper_golem") || key.contains("decorated_pot") || key.contains("sign"));
        return ColorCollectionUtil.registerBlocks(
                createSimpleColored(key),
                shouldAlsoRegisterItem ? ModBlocks::registerBlock : BLOCKS::registerBlock,
                block,
                properties::mapColor
        );
    }

    private static <T extends Block> ColorCollection<DeferredBlock<T>> registerColoredWeathering(String key, BiFunction<WeatherState, BlockBehaviour.Properties, ? extends T> block, BlockBehaviour.Properties properties) {
        return registerColored(key, p -> block.apply(WeatherState.UNAFFECTED, p), properties);
    }

    private static <T extends Block> ColorCollection<DeferredBlock<T>> registerColored(String key, Function<BlockBehaviour.Properties, ? extends T> block, BlockBehaviour.Properties properties) {
        return registerColored(key, (color, p) -> block.apply(p), properties);
    }

    private static String formatName(String key, DyeColor color) {
        String prefix = "";
        if(key.startsWith("waxed_")) {
            prefix = "waxed_";
            key = key.replace(prefix, "");
        }
        else if(key.startsWith("stripped_")) {
            prefix = "stripped_";
            key = key.replace(prefix, "");
        }

        return prefix + color.getName() + "_" + key;
    }

    private static DeferredBlock<Block> registerFlowerBlock(String name, Function<BlockBehaviour.Properties, ? extends Block> block, Supplier<BlockBehaviour.Properties> properties) {
        DeferredBlock<Block> deferredBlock = registerBlock(name, block, properties);
        POTTABLE_PLANTS.add(deferredBlock.getKey());
        return deferredBlock;
    }

    private static DeferredBlock<Block> registerPottedFlowerBlock(DeferredBlock<? extends Block> flower) {
        DeferredBlock<Block> block = BLOCKS.registerBlock(
                "potted_" + name(flower),
                p -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, flower, p),
                p -> p.instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)
        );
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(flower.getId(), block);
        return block;
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, ? extends T> block, Supplier<BlockBehaviour.Properties> properties) {
        DeferredBlock<T> deferredBlock = BLOCKS.registerBlock(name, block, properties);
        ModItems.ITEMS.registerSimpleBlockItem(deferredBlock);
        return deferredBlock;
    }

    private static ColorCollection<String> createSimpleColored(String baseName) {
        return ColorCollection.zipMap(ColorCollection.VALUES, ColorCollection.create(baseName), (color, id) -> formatName(id, color));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
