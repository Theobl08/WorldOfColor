package net.theobl.worldofcolor;

import com.mojang.logging.LogUtils;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.world.poi.ExtendPoiTypesEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.theobl.worldofcolor.block.ColoredCauldronInteraction;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.block.ModWoodType;
import net.theobl.worldofcolor.entity.ModEntityType;
import net.theobl.worldofcolor.entity.client.ModModelLayers;
import net.theobl.worldofcolor.item.ModCreativeModeTabs;
import net.theobl.worldofcolor.item.ModItems;
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
        ModEntityType.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ModItems.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        ModCreativeModeTabs.register(modEventBus);
        ColoredCauldronInteraction.bootStrap();

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (WorldOfColor) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::addBlockToBlockEntity);
        modEventBus.addListener(this::extendPoiTypes);
        modEventBus.addListener(this::registerBlockColor);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        //modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        ModUtil.setup();
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
        ModBlocks.COLORED_SIGNS.forEach(sign -> event.modify(BlockEntityType.SIGN, sign.get()));
        ModBlocks.COLORED_WALL_SIGNS.forEach(sign -> event.modify(BlockEntityType.SIGN, sign.get()));
        ModBlocks.COLORED_HANGING_SIGNS.forEach(sign -> event.modify(BlockEntityType.HANGING_SIGN, sign.get()));
        ModBlocks.COLORED_WALL_HANGING_SIGNS.forEach(sign -> event.modify(BlockEntityType.HANGING_SIGN, sign.get()));
    }

    private void extendPoiTypes(ExtendPoiTypesEvent event) {
        ModBlocks.COLORED_LIGHTNING_RODS.forEach(block -> event.addBlockToPoi(PoiTypes.LIGHTNING_ROD, block.get()));
    }

    private void registerBlockColor(RegisterColorHandlersEvent.Block event) {
        ModBlocks.COLORED_WATER_CAULDRONS.forEach(block ->
                event.register((state, tintGetter, pos, i) ->
                        tintGetter != null && pos != null ? BiomeColors.getAverageWaterColor(tintGetter, pos) : -1, block.get()));
        ModBlocks.COLORED_POTTED_PLANTS.get(ModUtil.FERN).forEach(block ->
                event.register(
                        (state, level, pos, tintIndex) -> level != null && pos != null
                                ? BiomeColors.getAverageGrassColor(level, pos)
                                : GrassColor.getDefaultColor(),
                        block.get()
                )
        );
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            // LOGGER.info("HELLO FROM CLIENT SETUP");
            // LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            for (WoodType type : ModWoodType.COLORED_WOODS)
                Sheets.addWoodType(type);

            for (DyeColor color : ModUtil.COLORS) {
                int index = ModUtil.COLORS.indexOf(color);
                EntityRenderers.register(ModEntityType.COLORED_BOATS.get(index).get(), context -> new BoatRenderer(context, ModModelLayers.COLORED_BOATS.get(index)));
                EntityRenderers.register(ModEntityType.COLORED_CHEST_BOATS.get(index).get(), context -> new BoatRenderer(context, ModModelLayers.COLORED_CHEST_BOATS.get(index)));
            }
        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            ModModelLayers.COLORED_BOATS.forEach(layer -> event.registerLayerDefinition(layer, BoatModel::createBoatModel));
            ModModelLayers.COLORED_CHEST_BOATS.forEach(layer -> event.registerLayerDefinition(layer, BoatModel::createChestBoatModel));
        }

        @SubscribeEvent
        public static void addPackFinders(AddPackFindersEvent event) {
            event.addPackFinders(ResourceLocation.fromNamespaceAndPath(MODID, "resourcepacks/accurate_stained_glass"), PackType.CLIENT_RESOURCES,
                    Component.literal("Accurate stained glass color"), PackSource.DEFAULT, false, Pack.Position.TOP);
        }
    }
}
