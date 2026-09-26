package net.theobl.worldofcolor;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.BuiltInBlockModels;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.references.BlockItemIds;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.WallBannerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.fluid.FluidTintSource;
import net.neoforged.neoforge.client.fluid.FluidTintSources;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.theobl.worldofcolor.block.DyedWaterLiquidBlock;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.block.entity.DyedWaterCauldronBlockEntity;
import net.theobl.worldofcolor.block.entity.DyedWaterLiquidBlockEntity;
import net.theobl.worldofcolor.block.entity.ModBlockEntityType;
import net.theobl.worldofcolor.client.model.geom.ModModelLayers;
import net.theobl.worldofcolor.client.renderer.ModSpriteId;
import net.theobl.worldofcolor.client.renderer.blockentity.ColoredBannerRenderer;
import net.theobl.worldofcolor.client.renderer.blockentity.ColoredDecoratedPotRenderer;
import net.theobl.worldofcolor.client.renderer.blockentity.DyedWaterCauldronRenderer;
import net.theobl.worldofcolor.client.renderer.entity.MissingnoCushionRenderer;
import net.theobl.worldofcolor.client.renderer.entity.RgbCushionRenderer;
import net.theobl.worldofcolor.client.renderer.gui.GuiColoredBannerResultRenderer;
import net.theobl.worldofcolor.client.renderer.gui.state.GuiColoredBannerResultRenderState;
import net.theobl.worldofcolor.client.renderer.special.ColoredBannerSpecialRenderer;
import net.theobl.worldofcolor.client.renderer.special.ColoredDecoratedPotSpecialRenderer;
import net.theobl.worldofcolor.entity.ModEntityType;
import net.theobl.worldofcolor.fluids.ModFluids;
import net.theobl.worldofcolor.util.ModUtil;
import org.joml.Vector4f;
import org.jspecify.annotations.Nullable;

import java.util.List;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = WorldOfColor.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(value = Dist.CLIENT)
public class WorldOfColorClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
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
        event.registerEntityRenderer(ModEntityType.RGB_CUSHION.get(), RgbCushionRenderer::new);
        event.registerEntityRenderer(ModEntityType.MISSINGNO_CUSHION.get(), MissingnoCushionRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntityType.COLORED_DECORATED_POT.get(), ColoredDecoratedPotRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntityType.RGB_BANNER.get(), ColoredBannerRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntityType.DYED_WATER_CAULDRON.get(), DyedWaterCauldronRenderer::new);
    }

    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        event.addPackFinders(WorldOfColor.asResource("resourcepacks/accurate_stained_glass"), PackType.CLIENT_RESOURCES,
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
        event.register(WorldOfColor.asResource("decorated_pot"), ColoredDecoratedPotSpecialRenderer.Unbaked.MAP_CODEC);
        event.register(WorldOfColor.asResource("rgb_banner"), ColoredBannerSpecialRenderer.Unbaked.MAP_CODEC);
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
        event.register(
                new FluidModel.Unbaked(
                        new Material(WorldOfColor.asResource("block/rgb_water_still")),
                        new Material(WorldOfColor.asResource("block/rgb_water_flow")),
                        new Material(WorldOfColor.asResource("block/rgb_water_overlay")),
                        null,
                        null
                ),
                ModFluids.RGB_WATER,
                ModFluids.FLOWING_RGB_WATER
        );
    }

    @SubscribeEvent
    public static void registerClientExtension(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public void modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
                IClientFluidTypeExtensions.super.modifyFogColor(camera, partialTick, level, renderDistance, darkenWorldAmount, fluidFogColor);
                if(level.getBlockEntity(camera.blockPosition()) instanceof DyedWaterLiquidBlockEntity blockEntity) {
                    DyedItemColor color = blockEntity.getColor();
                    float red = ARGB.red(color.rgb()) / 255F;
                    float green = ARGB.green(color.rgb()) / 255F;
                    float blue = ARGB.blue(color.rgb()) / 255F;
                    float alpha = 1.0F;
                    fluidFogColor.set(red, green, blue, alpha);
                }
            }

            @Override
            public void modifyFogRender(Camera camera, @Nullable FogEnvironment environment, float renderDistance, float partialTick, FogData fogData) {
                fogData.environmentalStart = camera.attributeProbe().getValue(EnvironmentAttributes.WATER_FOG_START_DISTANCE, partialTick);
                fogData.environmentalEnd = camera.attributeProbe().getValue(EnvironmentAttributes.WATER_FOG_END_DISTANCE, partialTick);
                if (camera.entity() instanceof LocalPlayer player) {
                    fogData.environmentalEnd = fogData.environmentalEnd * Math.max(0.25F, player.getWaterVision());
                }

                fogData.skyEnd = fogData.environmentalEnd;
                fogData.cloudEnd = fogData.environmentalEnd;
            }
        }, ModFluids.DYED_WATER_TYPE);
    }

    @SubscribeEvent
    public static void registerPictureInPictureRenderers(RegisterPictureInPictureRenderersEvent event) {
        event.register(GuiColoredBannerResultRenderState.class,
                () -> new GuiColoredBannerResultRenderer(Minecraft.getInstance().getAtlasManager()));
    }
}
