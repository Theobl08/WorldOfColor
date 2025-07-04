package net.theobl.worldofcolor;

import com.mojang.logging.LogUtils;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
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
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.block.ModWoodType;
import net.theobl.worldofcolor.entity.ModEntityType;
import net.theobl.worldofcolor.entity.ModPoiTypes;
import net.theobl.worldofcolor.entity.client.ModBoatRenderer;
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
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public WorldOfColor(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        ModBlocks.register(modEventBus);
        ModEntityType.register(modEventBus);
        ModPoiTypes.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ModItems.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        ModCreativeModeTabs.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (WorldOfColor) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::addBlockToBlockEntity);

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
    }

    private void addBlockToBlockEntity(BlockEntityTypeAddBlocksEvent event) {
        ModBlocks.COLORED_SIGNS.forEach(sign -> event.modify(BlockEntityType.SIGN, sign.get()));
        ModBlocks.COLORED_WALL_SIGNS.forEach(sign -> event.modify(BlockEntityType.SIGN, sign.get()));
        ModBlocks.COLORED_HANGING_SIGNS.forEach(sign -> event.modify(BlockEntityType.HANGING_SIGN, sign.get()));
        ModBlocks.COLORED_WALL_HANGING_SIGNS.forEach(sign -> event.modify(BlockEntityType.HANGING_SIGN, sign.get()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            // LOGGER.info("HELLO FROM CLIENT SETUP");
            // LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            for (WoodType type : ModWoodType.COLORED_WOODS)
                Sheets.addWoodType(type);

            EntityRenderers.register(ModEntityType.COLORED_BOAT.get(), pContext -> new ModBoatRenderer(pContext, false));
            EntityRenderers.register(ModEntityType.COLORED_CHEST_BOAT.get(), pContext -> new ModBoatRenderer(pContext, true));
        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            ModModelLayers.COLORED_BOATS.forEach(layer -> event.registerLayerDefinition(layer, BoatModel::createBodyModel));
            ModModelLayers.COLORED_CHEST_BOATS.forEach(layer -> event.registerLayerDefinition(layer, ChestBoatModel::createBodyModel));
        }

        @SubscribeEvent
        public static void addPackFinders(AddPackFindersEvent event) {
            event.addPackFinders(ResourceLocation.fromNamespaceAndPath(MODID, "resourcepacks/accurate_stained_glass"), PackType.CLIENT_RESOURCES,
                    Component.literal("Accurate stained glass color"), PackSource.DEFAULT, false, Pack.Position.TOP);
        }
    }
}
