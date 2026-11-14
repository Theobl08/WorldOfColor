package net.theobl.worldofcolor.datagen;

import net.minecraft.client.color.item.Dye;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.references.BlockItemIds;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.AzaleaBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ColorCollection;
import net.minecraft.world.level.block.NetherRootsBlock;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.client.renderer.special.ColoredDecoratedPotSpecialRenderer;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.util.ColorCollectionUtil;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.List;

public class ModModelProvider extends ModelProvider {
    ///Pre-1.21 uncraftable {@link net.minecraft.world.item.alchemy.Potion} color
    private static final int EMPTY_COLOR = 16253176;
    private static final ColorCollection<Identifier> COLORED_COPPER_CHESTS_TEXTURES = ColorCollection.NAMES
            .map(color -> WorldOfColor.asResource("copper_" + color));
    public ModModelProvider(PackOutput output) {
        super(output, WorldOfColor.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        ColoredBlockModelGenerators coloredBlockModels = new ColoredBlockModelGenerators(blockModels);
        ModBlockFamilies.getAllFamilies()
                .filter(BlockFamily::shouldGenerateModel)
                .forEach(blockFamily -> blockModels.family(blockFamily.getBaseBlock()).generateFor(blockFamily));
        ModBlocks.SIMPLE_COLORED_BLOCKS.forEach(block -> blockModels.createTrivialCube(block.get()));
        blockModels.createColoredBlockWithStateRotations(TexturedModel.GLAZED_TERRACOTTA, ModBlocks.GLAZED_CONCRETES.asList().stream().map(DeferredHolder::get).toList());
        ModBlocks.COLORED_LEAVES.forEach(block -> blockModels.createTrivialCube(block.get()));
        ModBlocks.COLORED_COPPER_BULBS.coloring().map(DeferredBlock::get).forEach((blockModels::createCopperBulb));
        ModBlocks.COLORED_COPPER_BULBS.map(DeferredBlock::get).zipUnwaxedWaxed(blockModels::copyCopperBulbModel);
        ModBlocks.COLORED_COPPER_BLOCKS.coloring().map(DeferredBlock::get).forEach(blockModels::createTrivialCube);
        ModBlocks.COLORED_COPPER_BLOCKS.zipUnwaxedWaxed(coloredBlockModels::copyModel);
        ModBlocks.COLORED_COPPER_DOORS.coloring().map(DeferredBlock::get).forEach(blockModels::createDoor);
        ModBlocks.COLORED_COPPER_DOORS.map(DeferredBlock::get).zipUnwaxedWaxed(blockModels::copyDoorModel);
        ModBlocks.COLORED_COPPER_TRAPDOORS.coloring().map(DeferredBlock::get).forEach(blockModels::createOrientableTrapdoor);
        ModBlocks.COLORED_COPPER_TRAPDOORS.map(DeferredBlock::get).zipUnwaxedWaxed(blockModels::copyTrapdoorModel);
        ModBlocks.COLORED_COPPER_GRATES.coloring().map(DeferredBlock::get).forEach(blockModels::createTrivialCube);
        ModBlocks.COLORED_COPPER_GRATES.zipUnwaxedWaxed(coloredBlockModels::copyModel);
        ModBlocks.COLORED_LIGHTNING_RODS.map(DeferredBlock::get).zipUnwaxedWaxed(blockModels::createLightningRod);
        ModBlocks.COLORED_COPPER_BARS.map(DeferredBlock::get).zipUnwaxedWaxed(blockModels::createBarsAndItem);
        ModBlocks.COLORED_COPPER_LANTERNS.map(DeferredBlock::get).zipUnwaxedWaxed(blockModels::createCopperLantern);
        ModBlocks.COLORED_COPPER_CHAINS.map(DeferredBlock::get).zipUnwaxedWaxed(blockModels::createCopperChain);
        ModBlocks.COLORED_COPPER_CHAINS.map(DeferredBlock::asItem).zipUnwaxedWaxed(blockModels::createCopperChainItem);
        ModBlocks.COLORED_SLIME_BLOCKS.forEach(block -> blockModels.createTrivialBlock(block.get(), ColoredTexturedModel.SLIME_BLOCK));
        ModBlocks.COLORED_FLOWER_POTS.forEach(block -> {
            blockModels.registerSimpleFlatItemModel(block.asItem());
            blockModels.createTrivialBlock(block.get(), ColoredTexturedModel.FLOWER_POT);
        });
        ModBlocks.COLORED_DECORATED_POTS.forEach(block -> {
            DyeColor color = block.get().getColor();
            blockModels.createParticleOnlyBlock(block.get(), Blocks.DYED_TERRACOTTA.pick(color));
            coloredBlockModels.generateDecoratedPotItemModel(block.get(), new ColoredDecoratedPotSpecialRenderer.Unbaked(color), color);
        });
        ModBlocks.COLORED_REDSTONE_LAMPS.forEach(block -> coloredBlockModels.createRedstoneLamp(block.get()));
        ModBlocks.COLORED_POTATO_PEELS_BLOCK.forEach(block -> blockModels.createTrivialCube(block.get()));
        coloredBlockModels.createGlassBlocks(ModBlocks.HARDENED_GLASS.get(), ModBlocks.HARDENED_GLASS_PANE.get(), Blocks.GLASS_PANE);
        ColorCollectionUtil.zipApply(ModBlocks.HARDENED_STAINED_GLASS.map(Holder::value), ModBlocks.HARDENED_STAINED_GLASS_PANE.map(Holder::value), Blocks.STAINED_GLASS_PANE, coloredBlockModels::createGlassBlocks);
        ModUtil.zipApply(ModBlocks.CLASSIC_WOOLS, ModBlocks.CLASSIC_CARPETS, coloredBlockModels::createFullAndCarpetBlocks);
        ColorCollectionUtil.zipApply(ModBlocks.COLORED_COPPER_CHESTS.coloring(), ModBlocks.COLORED_COPPER_BLOCKS.coloring(), COLORED_COPPER_CHESTS_TEXTURES,
                (block, particle, texture) -> coloredBlockModels.createChest(block, particle, texture, false));
        ModBlocks.COLORED_COPPER_CHESTS.zipUnwaxedWaxed(coloredBlockModels::copyModel);
        ColorCollectionUtil.zipApply(ModBlocks.COLORED_COPPER_GOLEM_STATUES.coloring(), ModBlocks.COLORED_COPPER_BLOCKS.coloring(), ColorCollection.VALUES, coloredBlockModels::createCopperGolemStatue);
        ModBlocks.COLORED_COPPER_GOLEM_STATUES.zipUnwaxedWaxed(coloredBlockModels::copyModel);
        DyeColor.VALUES.forEach(coloredBlockModels::createCauldrons);
        ColorCollection.zipApply(ModBlocks.QUILTED_CONCRETES.map(Holder::value), Blocks.DYED_SHULKER_BOX, coloredBlockModels::createTrivialCube);
        ColorCollection.zipApply(ModBlocks.COLORED_SAPLINGS, ModBlocks.POTTED_COLORED_SAPLINGS,
                (standAlone, potted) -> blockModels.createPlantWithDefaultItem(standAlone.get(), potted.get(), BlockModelGenerators.PlantType.NOT_TINTED));
        for(DyeColor color : ModUtil.COLORS) {
            blockModels.family(ModBlocks.COLORED_CUT_COPPER.coloring().pick(color).get())
                    .generateFor(ModBlockFamilies.COLORED_CUT_COPPER.coloring().pick(color))
                    .donateModelTo(ModBlocks.COLORED_CUT_COPPER.coloring().pick(color).get(), ModBlocks.COLORED_CUT_COPPER.waxed().pick(color).get())
                    .donateModelTo(ModBlocks.COLORED_CHISELED_COPPER.coloring().pick(color).get(), ModBlocks.COLORED_CHISELED_COPPER.waxed().pick(color).get())
                    .generateFor(ModBlockFamilies.COLORED_CUT_COPPER.waxed().pick(color));
            blockModels.woodProvider(ModBlocks.COLORED_LOGS.pick(color).get()).logWithHorizontal(ModBlocks.COLORED_LOGS.pick(color).get()).wood(ModBlocks.COLORED_WOODS.pick(color).get());
            blockModels.woodProvider(ModBlocks.COLORED_STRIPPED_LOGS.pick(color).get()).logWithHorizontal(ModBlocks.COLORED_STRIPPED_LOGS.pick(color).get()).wood(ModBlocks.COLORED_STRIPPED_WOODS.pick(color).get());
            ModBlocks.COLORED_POTTED_PLANTS.forEach((plant, pottedPlant) -> {
                BlockModelGenerators.PlantType plantType = BlockModelGenerators.PlantType.NOT_TINTED;
                if(plant == BlockItemIds.FERN.block()) {
                    plantType = BlockModelGenerators.PlantType.TINTED;
                } else if (plant == BlockItemIds.OPEN_EYEBLOSSOM.block()) {
                    plantType = BlockModelGenerators.PlantType.EMISSIVE_NOT_TINTED;
                }
                if(plant == BlockItemIds.BAMBOO.block() || plant == BlockItemIds.MANGROVE_PROPAGULE.block() || plant == BlockItemIds.CACTUS.block() || ModUtil.getBlock(plant) instanceof AzaleaBlock || ModUtil.getBlock(plant) instanceof NetherRootsBlock) {
                    String suffix = ModUtil.getBlock(plant) instanceof AzaleaBlock ? "_bush" : "";
                    coloredBlockModels.createPottedPlant(pottedPlant.pick(color).get(), ModBlocks.COLORED_FLOWER_POTS.pick(color).get(), plant.identifier().getPath() + suffix);
                } else {
                    coloredBlockModels.createPottedPlant(ModUtil.getBlock(plant), pottedPlant.pick(color).get(), ModBlocks.COLORED_FLOWER_POTS.pick(color).get(), plantType);
                }
            });
        }
        ColorCollection.zipApply(ModBlocks.COLORED_SHELVES, ModBlocks.COLORED_STRIPPED_LOGS, coloredBlockModels::createShelf);
        ColorCollection.zipApply(ModBlocks.COLORED_CAULDRONS.map(Holder::value), ModBlocks.COLORED_DYED_WATER_CAULDRONS.map(Holder::value), coloredBlockModels::copyBlockModel);
        blockModels.createPlantWithDefaultItem(ModBlocks.LIGHT_GRAY_TULIP.get(), ModBlocks.POTTED_LIGHT_GRAY_TULIP.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModBlocks.GRAY_TULIP.get(), ModBlocks.POTTED_GRAY_TULIP.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModBlocks.BLACK_TULIP.get(), ModBlocks.POTTED_BLACK_TULIP.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModBlocks.BROWN_TULIP.get(), ModBlocks.POTTED_BROWN_TULIP.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModBlocks.YELLOW_TULIP.get(), ModBlocks.POTTED_YELLOW_TULIP.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModBlocks.LIME_TULIP.get(), ModBlocks.POTTED_LIME_TULIP.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModBlocks.GREEN_TULIP.get(), ModBlocks.POTTED_GREEN_TULIP.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModBlocks.CYAN_TULIP.get(), ModBlocks.POTTED_CYAN_TULIP.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModBlocks.LIGHT_BLUE_TULIP.get(), ModBlocks.POTTED_LIGHT_BLUE_TULIP.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModBlocks.BLUE_TULIP.get(), ModBlocks.POTTED_BLUE_TULIP.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModBlocks.PURPLE_TULIP.get(), ModBlocks.POTTED_PURPLE_TULIP.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(ModBlocks.MAGENTA_TULIP.get(), ModBlocks.POTTED_MAGENTA_TULIP.get(), BlockModelGenerators.PlantType.NOT_TINTED);

        blockModels.createTrivialCube(ModBlocks.RGB_TERRACOTTA.get());
        blockModels.createColoredBlockWithRandomRotations(TexturedModel.CUBE, List.of(ModBlocks.RGB_CONCRETE_POWDER.get()));
        blockModels.createColoredBlockWithStateRotations(TexturedModel.GLAZED_TERRACOTTA, List.of(ModBlocks.RGB_GLAZED_TERRACOTTA.get()));
        blockModels.createGlassBlocks(ModBlocks.RGB_STAINED_GLASS.get(), ModBlocks.RGB_STAINED_GLASS_PANE.get());
        blockModels.createCandleAndCandleCake(ModBlocks.RGB_CANDLE.get(), ModBlocks.RGB_CANDLE_CAKE.get());
        coloredBlockModels.createShulkerBox(ModBlocks.RGB_SHULKER_BOX.get(), WorldOfColor.asResource("shulker_rgb"));
        coloredBlockModels.createBed(ModBlocks.RGB_BED.get());
        coloredBlockModels.createBanner(ModBlocks.RGB_BANNER.get(), ModBlocks.RGB_WALL_BANNER.get());

        blockModels.createTrivialBlock(ModBlocks.MISSINGNO.get(), TexturedModel.CUBE.updateTexture(mapping -> mapping.put(TextureSlot.ALL, new Material(MissingTextureAtlasSprite.getLocation()))));
        blockModels.createTrivialCube(ModBlocks.MISSINGNO_TERRACOTTA.get());
        blockModels.createColoredBlockWithRandomRotations(TexturedModel.CUBE, List.of(ModBlocks.MISSINGNO_CONCRETE_POWDER.get()));
        blockModels.createColoredBlockWithStateRotations(TexturedModel.GLAZED_TERRACOTTA, List.of(ModBlocks.MISSINGNO_GLAZED_TERRACOTTA.get()));
        blockModels.createGlassBlocks(ModBlocks.MISSINGNO_STAINED_GLASS.get(), ModBlocks.MISSINGNO_STAINED_GLASS_PANE.get());
        blockModels.createCandleAndCandleCake(ModBlocks.MISSINGNO_CANDLE.get(), ModBlocks.MISSINGNO_CANDLE_CAKE.get());
        coloredBlockModels.createShulkerBox(ModBlocks.MISSINGNO_SHULKER_BOX.get(), WorldOfColor.asResource("shulker_missingno"));
        coloredBlockModels.createBed(ModBlocks.MISSINGNO_BED.get());

        blockModels.createAirLikeBlock(ModBlocks.DYED_WATER.get(), new Material(Identifier.withDefaultNamespace("block/water_still")));
        coloredBlockModels.copyBlockModel(Blocks.CAULDRON, ModBlocks.DYED_WATER_CAULDRON.get());

        blockModels.createAirLikeBlock(ModBlocks.RGB_WATER.get(), new Material(Identifier.withDefaultNamespace("block/water_still"))); // Create the fluid dummy block model et blockstate files

        ModItems.COLORED_BOATS.forEach(item -> itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));
        ModItems.COLORED_CHEST_BOATS.forEach(item -> itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));
        ModItems.COLORED_ITEM_FRAMES.forEach(coloredBlockModels::createItemFrame);
        ModItems.COLORED_POTATO_PEELS.forEach(item -> itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));
        itemModels.generateFlatItem(ModItems.RGB_CUSHION.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RGB_DYE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RGB_HARNESS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateBundleModels(ModItems.RGB_BUNDLE.get());
        itemModels.generateFlatItem(ModItems.MISSINGNO_CUSHION.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateBundleModels(ModItems.MISSINGNO_BUNDLE.get());
        itemModels.generateFlatItem(ModItems.MISSINGNO_HARNESS.get(), ModelTemplates.FLAT_ITEM);
        Identifier model = itemModels.generateLayeredItem(
                ModItems.DYED_WATER_BOTTLE.get(),
                new Material(Identifier.withDefaultNamespace("item/potion_overlay")),
                TextureMapping.getItemTexture(Items.POTION)
        );
        itemModels.itemModelOutput.accept(ModItems.DYED_WATER_BOTTLE.get(), ItemModelUtils.tintedModel(model, new Dye(ARGB.opaque(EMPTY_COLOR))));
        itemModels.itemModelOutput.accept(ModItems.DYED_WATER_BUCKET.get(), ItemModelUtils.tintedModel(
                itemModels.generateLayeredItem(
                        ModItems.DYED_WATER_BUCKET.get(),
                        TextureMapping.getItemTexture(Items.BUCKET),
                        TextureMapping.getItemTexture(ModItems.DYED_WATER_BUCKET.get(), "_overlay")),
                ItemModelGenerators.BLANK_LAYER,
                new Dye(ARGB.opaque(OverworldBiomes.NORMAL_WATER_COLOR))));
        itemModels.generateFlatItem(ModItems.RGB_WATER_BUCKET.get(), ModelTemplates.FLAT_ITEM);
    }
}
