package net.theobl.worldofcolor;

import com.google.common.collect.ImmutableSet;
import com.mojang.logging.LogUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.item.component.Compostable;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.world.poi.ExtendPoiTypesEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.RegisterCauldronFluidContentEvent;
import net.neoforged.neoforge.transfer.item.VanillaContainerWrapper;
import net.theobl.worldofcolor.block.DyedWaterCauldronBlock;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.block.entity.ModBlockEntityType;
import net.theobl.worldofcolor.entity.ModEntityType;
import net.theobl.worldofcolor.fluids.ModFluids;
import net.theobl.worldofcolor.item.ModCreativeModeTabs;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.item.crafting.ModRecipeSerializer;
import net.theobl.worldofcolor.sounds.ModSoundEvents;
import net.theobl.worldofcolor.util.ModUtil;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(WorldOfColor.MODID)
public class WorldOfColor {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "worldofcolor";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public WorldOfColor(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        ModBlocks.register(modEventBus);
        ModSoundEvents.register(modEventBus);
        ModBlockEntityType.register(modEventBus);
        ModEntityType.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ModItems.register(modEventBus);
        ModFluids.register(modEventBus);
        ModRecipeSerializer.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        ModCreativeModeTabs.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (WorldOfColor) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::addBlockToBlockEntity);
        modEventBus.addListener(this::extendPoiTypes);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::registerCauldronFluidContents);
        modEventBus.addListener(this::modifyDefaultComponents);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        //modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        event.enqueueWork(ModUtil::setup);
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        //if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) event.accept(EXAMPLE_BLOCK_ITEM);
//        if(event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
//            event.remove(Items.WHITE_TULIP.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//            event.insertAfter(Items.AZURE_BLUET.getDefaultInstance(), Items.WHITE_TULIP.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//        }
    }

    private void addBlockToBlockEntity(BlockEntityTypeAddBlocksEvent event) {
        ModBlocks.COLORED_SIGNS.forEach(sign -> event.modify(BlockEntityTypes.SIGN, sign.get()));
        ModBlocks.COLORED_WALL_SIGNS.forEach(sign -> event.modify(BlockEntityTypes.SIGN, sign.get()));
        ModBlocks.COLORED_HANGING_SIGNS.forEach(sign -> event.modify(BlockEntityTypes.HANGING_SIGN, sign.get()));
        ModBlocks.COLORED_WALL_HANGING_SIGNS.forEach(sign -> event.modify(BlockEntityTypes.HANGING_SIGN, sign.get()));
        ModBlocks.COLORED_SHELVES.forEach(shelf -> event.modify(BlockEntityTypes.SHELF, shelf.get()));
        ModBlocks.COLORED_COPPER_CHESTS.forEach(block -> event.modify(BlockEntityTypes.CHEST, block.get()));
        ModBlocks.COLORED_COPPER_GOLEM_STATUES.forEach(block -> event.modify(BlockEntityTypes.COPPER_GOLEM_STATUE, block.get()));
        event.modify(BlockEntityTypes.SHULKER_BOX, ModBlocks.RGB_SHULKER_BOX.get(), ModBlocks.MISSINGNO_SHULKER_BOX.get());
    }

    private void extendPoiTypes(ExtendPoiTypesEvent event) {
        ModBlocks.COLORED_LIGHTNING_RODS.forEach(block -> event.addBlockToPoi(PoiTypes.LIGHTNING_ROD, block.get()));
        ModBlocks.COLORED_CAULDRONS.forEach(block -> event.addBlockToPoi(PoiTypes.LEATHERWORKER, block.get()));
        ModBlocks.COLORED_LAVA_CAULDRONS.forEach(block -> event.addBlockToPoi(PoiTypes.LEATHERWORKER, block.get()));
        ModBlocks.COLORED_WATER_CAULDRONS.forEach(block -> event.addBlockToPoi(PoiTypes.LEATHERWORKER, block.get()));
        ModBlocks.COLORED_POWDER_SNOW_CAULDRONS.forEach(block -> event.addBlockToPoi(PoiTypes.LEATHERWORKER, block.get()));
        event.addStatesToPoi(PoiTypes.HOME, ModBlocks.RGB_BED.get().getStateDefinition().getPossibleStates()
                .stream().filter(state -> state.getValue(BedBlock.PART) == BedPart.HEAD).collect(ImmutableSet.toImmutableSet()));
//        event.addStatesToPoi(PoiTypes.HOME, ImmutableList.of(ModBlocks.RGB_BED.get())
//                .stream()
//                .flatMap(block -> block.getStateDefinition().getPossibleStates().stream())
//                .filter(state -> state.getValue(BedBlock.PART) == BedPart.HEAD)
//                .collect(ImmutableSet.toImmutableSet()));
    }

    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, ModBlockEntityType.COLORED_DECORATED_POT.get(), (container, side) -> VanillaContainerWrapper.of(container));
    }

    public void registerCauldronFluidContents(RegisterCauldronFluidContentEvent event) {
        event.register(ModBlocks.DYED_WATER_CAULDRON.get(), ModFluids.DYED_WATER.get(), FluidType.BUCKET_VOLUME, DyedWaterCauldronBlock.LEVEL);
    }

    public void modifyDefaultComponents(ModifyDefaultComponentsEvent event) {
        ModBlocks.COLORED_LEAVES.forEach(block ->
                event.modify(block, ((components, context, item) ->
                        components.set(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_LOW)))));
        ModBlocks.COLORED_SAPLINGS.forEach(block ->
                event.modify(block, ((components, context, item) -> {
                    components.set(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_LOW));
                    components.set(DataComponents.COOKING_FUEL, new CookingFuel(ContextIntProviders.COOKING_TIME_DRY_PLANTS, ContextFloatProviders.COOKING_DEFAULT_SPEED_MULTIPLIER));
                })));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    public static Identifier asResource(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }
}
