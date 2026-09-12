package net.theobl.worldofcolor;

import com.google.common.collect.ImmutableSet;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.BuiltInBlockModels;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.references.BlockItemIds;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.WallBannerBlock;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.fluid.FluidTintSource;
import net.neoforged.neoforge.client.fluid.FluidTintSources;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.world.poi.ExtendPoiTypesEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.RegisterCauldronFluidContentEvent;
import net.neoforged.neoforge.transfer.item.VanillaContainerWrapper;
import net.theobl.worldofcolor.block.DyedWaterCauldronBlock;
import net.theobl.worldofcolor.block.DyedWaterLiquidBlock;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.block.entity.DyedWaterCauldronBlockEntity;
import net.theobl.worldofcolor.block.entity.DyedWaterLiquidBlockEntity;
import net.theobl.worldofcolor.block.entity.ModBlockEntityType;
import net.theobl.worldofcolor.client.renderer.ModSpriteId;
import net.theobl.worldofcolor.client.renderer.blockentity.ColoredBannerRenderer;
import net.theobl.worldofcolor.client.renderer.blockentity.ColoredDecoratedPotRenderer;
import net.theobl.worldofcolor.client.renderer.blockentity.DyedWaterCauldronRenderer;
import net.theobl.worldofcolor.client.renderer.gui.GuiColoredBannerResultRenderer;
import net.theobl.worldofcolor.client.renderer.gui.state.GuiColoredBannerResultRenderState;
import net.theobl.worldofcolor.client.renderer.special.ColoredBannerSpecialRenderer;
import net.theobl.worldofcolor.client.renderer.special.ColoredDecoratedPotSpecialRenderer;
import net.theobl.worldofcolor.entity.ModEntityType;
import net.theobl.worldofcolor.client.model.geom.ModModelLayers;
import net.theobl.worldofcolor.fluids.ModFluids;
import net.theobl.worldofcolor.item.ModCreativeModeTabs;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.item.crafting.ModRecipeSerializer;
import net.theobl.worldofcolor.sounds.ModSoundEvents;
import net.theobl.worldofcolor.util.ModUtil;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;

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

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    public static Identifier asResource(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            // LOGGER.info("HELLO FROM CLIENT SETUP");
            // LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

            for (DyeColor color : ModUtil.COLORS) {
                EntityRenderers.register(ModEntityType.COLORED_BOATS.pick(color).get(), context -> new BoatRenderer(context, ModModelLayers.COLORED_BOATS.pick(color)));
                EntityRenderers.register(ModEntityType.COLORED_CHEST_BOATS.pick(color).get(), context -> new BoatRenderer(context, ModModelLayers.COLORED_CHEST_BOATS.pick(color)));
                EntityRenderers.register(ModEntityType.COLORED_ITEM_FRAMES.pick(color).get(), ItemFrameRenderer::new);
            }

            ModSpriteId.bootstrap();
        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            ModModelLayers.COLORED_BOATS.forEach(layer -> event.registerLayerDefinition(layer, BoatModel::createBoatModel));
            ModModelLayers.COLORED_CHEST_BOATS.forEach(layer -> event.registerLayerDefinition(layer, BoatModel::createChestBoatModel));
        }

        @SubscribeEvent
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntityType.COLORED_DECORATED_POT.get(), ColoredDecoratedPotRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntityType.RGB_BANNER.get(), ColoredBannerRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntityType.DYED_WATER_CAULDRON.get(), DyedWaterCauldronRenderer::new);
        }

        @SubscribeEvent
        public static void addPackFinders(AddPackFindersEvent event) {
            event.addPackFinders(Identifier.fromNamespaceAndPath(MODID, "resourcepacks/accurate_stained_glass"), PackType.CLIENT_RESOURCES,
                    Component.literal("Accurate stained glass color"), PackSource.DEFAULT, false, Pack.Position.TOP);
        }

        @SubscribeEvent
        public static void registerBlockColor(RegisterColorHandlersEvent.BlockTintSources event) {
            ModBlocks.COLORED_WATER_CAULDRONS.forEach(block ->
                    event.register(List.of(BlockTintSources.water()), block.get()));
            ModBlocks.COLORED_POTTED_PLANTS.get(BlockItemIds.FERN.block()).forEach(block ->
                    event.register(List.of(BlockTintSources.grass()), block.get())
            );
        }

        @SubscribeEvent
        public static void registerSpecialModelRenderer(RegisterSpecialModelRendererEvent event) {
            event.register(asResource("decorated_pot"), ColoredDecoratedPotSpecialRenderer.Unbaked.MAP_CODEC);
            event.register(asResource("rgb_banner"), ColoredBannerSpecialRenderer.Unbaked.MAP_CODEC);
        }

        @SubscribeEvent
        public static void registerSpecialBlockModelRenderer(RegisterBlockModelsEvent event) {
            ModBlocks.COLORED_DECORATED_POTS.forEach(block ->
                            event.register(BuiltInBlockModels.specialModelWithPropertyDispatch(
                                    DecoratedPotBlock.HORIZONTAL_FACING, facing ->
                                            BuiltInBlockModels.special(new ColoredDecoratedPotSpecialRenderer.Unbaked(block.get().getColor()),
                                                    DecoratedPotRenderer.modelTransformation(facing))
                            ), block.get())

            );

            event.register(BuiltInBlockModels.specialModelWithPropertyDispatch(
                            BannerBlock.ROTATION,
                            rotation -> BuiltInBlockModels.special(
                                    new ColoredBannerSpecialRenderer.Unbaked(BannerBlock.AttachmentType.GROUND),
                                    BannerRenderer.TRANSFORMATIONS.freeTransformations(rotation)
                            )
                    ), ModBlocks.RGB_BANNER.get());

            event.register(BuiltInBlockModels.specialModelWithPropertyDispatch(
                            WallBannerBlock.FACING,
                            facing -> BuiltInBlockModels.special(
                                    new ColoredBannerSpecialRenderer.Unbaked(BannerBlock.AttachmentType.WALL), BannerRenderer.TRANSFORMATIONS.wallTransformation(facing)
                            )
                    ), ModBlocks.RGB_WALL_BANNER.get());
        }

        @SubscribeEvent
        public static void registerFluidModels(RegisterFluidModelsEvent event) {
            event.register(
                    new FluidModel.Unbaked(
                            new Material(Identifier.withDefaultNamespace("block/water_still")),
                            new Material(Identifier.withDefaultNamespace("block/water_flow")),
                            new Material(Identifier.withDefaultNamespace("block/water_overlay")),
                            new FluidTintSource() {
                                @Override
                                public int color(FluidState state) {
                                    if(Minecraft.getInstance().hitResult instanceof BlockHitResult hitResult) {
                                        if(Minecraft.getInstance().level != null && Minecraft.getInstance().level.getBlockEntity(hitResult.getBlockPos()) instanceof DyedWaterCauldronBlockEntity blockEntity) {
                                            return ARGB.opaque(blockEntity.getWaterColor());
                                        }
                                    }
                                    return FluidTintSources.water().color(state);
                                }

                                @Override
                                public int colorInWorld(FluidState fluidState, BlockState blockState, BlockAndTintGetter level, BlockPos pos) {
                                    if (level.getBlockEntity(pos) instanceof DyedWaterLiquidBlockEntity blockEntity) {
                                        return ARGB.opaque(blockEntity.getColor().rgb());
                                    }
                                    return FluidTintSource.super.colorInWorld(fluidState, blockState, level, pos);
                                }
                            }),
                    ModFluids.DYED_WATER,
                    ModFluids.FLOWING_DYED_WATER
            );
        }

        @SubscribeEvent
        public static void registerPictureInPictureRenderers(RegisterPictureInPictureRenderersEvent event) {
            event.register(GuiColoredBannerResultRenderState.class,
                    () -> new GuiColoredBannerResultRenderer(Minecraft.getInstance().getAtlasManager()));
        }
    }
}
