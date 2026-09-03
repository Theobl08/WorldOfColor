package net.theobl.worldofcolor.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.references.BlockItemIds;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.AzaleaBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherRootsBlock;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.client.renderer.special.ColoredDecoratedPotSpecialRenderer;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.List;

import static net.minecraft.client.data.models.model.TextureMapping.*;
import static net.theobl.worldofcolor.datagen.ColoredModelTemplates.*;

public class ModModelProvider extends ModelProvider {
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
        ModBlocks.COLORED_COPPER_BULBS.map(DeferredBlock::get).zipUnwaxedWaxed((unwaxed, _) -> blockModels.createCopperBulb(unwaxed));
        ModBlocks.COLORED_COPPER_BULBS.map(DeferredBlock::get).zipUnwaxedWaxed(blockModels::copyCopperBulbModel);
        ModBlocks.COLORED_COPPER_BLOCKS.map(DeferredBlock::get).zipUnwaxedWaxed((unwaxed, _) -> blockModels.createTrivialCube(unwaxed));
        ModBlocks.COLORED_COPPER_BLOCKS.map(DeferredBlock::get).zipUnwaxedWaxed(blockModels::copyModel);
        ModBlocks.COLORED_COPPER_DOORS.map(DeferredBlock::get).zipUnwaxedWaxed((unwaxed, _) -> blockModels.createDoor(unwaxed));
        ModBlocks.COLORED_COPPER_DOORS.map(DeferredBlock::get).zipUnwaxedWaxed(blockModels::copyDoorModel);
        ModBlocks.COLORED_COPPER_TRAPDOORS.map(DeferredBlock::get).zipUnwaxedWaxed((unwaxed, _) -> blockModels.createOrientableTrapdoor(unwaxed));
        ModBlocks.COLORED_COPPER_TRAPDOORS.map(DeferredBlock::get).zipUnwaxedWaxed(blockModels::copyTrapdoorModel);
        ModBlocks.COLORED_COPPER_GRATES.map(DeferredBlock::get).zipUnwaxedWaxed((unwaxed, _) -> blockModels.createTrivialCube(unwaxed));
        ModBlocks.COLORED_COPPER_GRATES.map(DeferredBlock::get).zipUnwaxedWaxed(blockModels::copyModel);
        ModBlocks.COLORED_LIGHTNING_RODS.map(DeferredBlock::get).zipUnwaxedWaxed(blockModels::createLightningRod);
        ModBlocks.COLORED_COPPER_BARS.map(DeferredBlock::get).zipUnwaxedWaxed(blockModels::createBarsAndItem);
        ModBlocks.COLORED_COPPER_LANTERNS.map(DeferredBlock::get).zipUnwaxedWaxed(blockModels::createCopperLantern);
        ModBlocks.COLORED_COPPER_CHAINS.map(DeferredBlock::get).zipUnwaxedWaxed(blockModels::createCopperChain);
        ModBlocks.COLORED_COPPER_CHAINS.map(DeferredBlock::asItem).zipUnwaxedWaxed(blockModels::createCopperChainItem);
        ModBlocks.COLORED_SLIME_BLOCKS.forEach(block -> blockModels.createTrivialBlock(block.get(), TexturedModel.createDefault(TextureMapping::defaultTexture, SLIME_BLOCK)));
        ModBlocks.COLORED_FLOWER_POTS.forEach(block -> {
            blockModels.registerSimpleFlatItemModel(block.asItem());
            coloredBlockModels.createTrivialBlock(block.get(), ColoredTextureMapping.flowerPot(block.get()), FLOWER_POT);
        });
        ModBlocks.COLORED_DECORATED_POTS.forEach(block -> {
            DyeColor color = block.get().getColor();
            blockModels.createParticleOnlyBlock(block.get(), Blocks.DYED_TERRACOTTA.pick(color));
            coloredBlockModels.generateDecoratedPotItemModel(block.get(), new ColoredDecoratedPotSpecialRenderer.Unbaked(color), color);
        });
        ModBlocks.COLORED_REDSTONE_LAMPS.forEach(block -> coloredBlockModels.createRedstoneLamp(block.get()));
        ModBlocks.COLORED_POTATO_PEELS_BLOCK.forEach(block -> blockModels.createTrivialCube(block.get()));
        for(DyeColor color : ModUtil.COLORS) {
            int index = ModUtil.COLORS.indexOf(color);
            blockModels.family(ModBlocks.COLORED_CUT_COPPER.coloring().pick(color).get())
                    .generateFor(ModBlockFamilies.COLORED_CUT_COPPER.coloring().pick(color))
                    .donateModelTo(ModBlocks.COLORED_CUT_COPPER.coloring().pick(color).get(), ModBlocks.COLORED_CUT_COPPER.waxed().pick(color).get())
                    .donateModelTo(ModBlocks.COLORED_CHISELED_COPPER.coloring().pick(color).get(), ModBlocks.COLORED_CHISELED_COPPER.waxed().pick(color).get())
                    .generateFor(ModBlockFamilies.COLORED_CUT_COPPER.waxed().pick(color));

            blockModels.createFullAndCarpetBlocks(ModBlocks.CLASSIC_WOOLS.get(index).get(), ModBlocks.CLASSIC_CARPETS.get(index).get());
            blockModels.woodProvider(ModBlocks.COLORED_LOGS.pick(color).get()).logWithHorizontal(ModBlocks.COLORED_LOGS.pick(color).get()).wood(ModBlocks.COLORED_WOODS.pick(color).get());
            blockModels.woodProvider(ModBlocks.COLORED_STRIPPED_LOGS.pick(color).get()).logWithHorizontal(ModBlocks.COLORED_STRIPPED_LOGS.pick(color).get()).wood(ModBlocks.COLORED_STRIPPED_WOODS.pick(color).get());

            blockModels.createChest(ModBlocks.COLORED_COPPER_CHESTS.coloring().pick(color).get(), ModBlocks.COLORED_COPPER_BLOCKS.coloring().pick(color).get(), WorldOfColor.asResource("copper_" + color.getName()), false);
            blockModels.copyModel(ModBlocks.COLORED_COPPER_CHESTS.coloring().pick(color).get(), ModBlocks.COLORED_COPPER_CHESTS.waxed().pick(color).get());
            coloredBlockModels.createCopperGolemStatue(ModBlocks.COLORED_COPPER_GOLEM_STATUES.coloring().pick(color).get(), ModBlocks.COLORED_COPPER_BLOCKS.coloring().pick(color).get(), color);
            blockModels.copyModel(ModBlocks.COLORED_COPPER_GOLEM_STATUES.coloring().pick(color).get(), ModBlocks.COLORED_COPPER_GOLEM_STATUES.waxed().pick(color).get());
            coloredBlockModels.createCauldrons(color);
            coloredBlockModels.createTrivialBlock(ModBlocks.QUILTED_CONCRETES.pick(color).get(), cube(Blocks.DYED_SHULKER_BOX.pick(color)), ModelTemplates.CUBE_ALL);
            blockModels.createPlantWithDefaultItem(ModBlocks.COLORED_SAPLINGS.pick(color).get(), ModBlocks.POTTED_COLORED_SAPLINGS.pick(color).get(), BlockModelGenerators.PlantType.NOT_TINTED);
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

            ITEM_FRAME.create(WorldOfColor.asResource(color.getName() + "_item_frame").withPrefix("block/"),
                    new TextureMapping().put(TextureSlot.PARTICLE, getBlockTexture(Blocks.BIRCH_PLANKS))
                            .put(ColoredTextureSlot.WOOD, getBlockTexture(Blocks.BIRCH_PLANKS))
                            .put(TextureSlot.BACK, new Material(WorldOfColor.asResource(color.getName() + "_item_frame").withPrefix("block/"))),
                    blockModels.modelOutput);

            ITEM_FRAME_MAP.create(WorldOfColor.asResource(color.getName() + "_item_frame_map").withPrefix("block/"),
                    new TextureMapping().put(TextureSlot.PARTICLE, getBlockTexture(Blocks.BIRCH_PLANKS))
                            .put(ColoredTextureSlot.WOOD, getBlockTexture(Blocks.BIRCH_PLANKS))
                            .put(TextureSlot.BACK, new Material(WorldOfColor.asResource(color.getName() + "_item_frame").withPrefix("block/"))),
                    blockModels.modelOutput);

            blockModels.createShelf(ModBlocks.COLORED_SHELVES.pick(color).get(), ModBlocks.COLORED_STRIPPED_LOGS.pick(color).get());
            coloredBlockModels.copyBlockModel(ModBlocks.COLORED_CAULDRONS.pick(color).get(), ModBlocks.COLORED_DYED_WATER_CAULDRONS.pick(color).get());
        }
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

        blockModels.createFullAndCarpetBlocks(ModBlocks.RGB_WOOL.get(), ModBlocks.RGB_CARPET.get());
        blockModels.createTrivialCube(ModBlocks.RGB_TERRACOTTA.get());
        blockModels.createTrivialCube(ModBlocks.RGB_CONCRETE.get());
        blockModels.createColoredBlockWithRandomRotations(TexturedModel.CUBE, List.of(ModBlocks.RGB_CONCRETE_POWDER.get()));
        blockModels.createColoredBlockWithStateRotations(TexturedModel.GLAZED_TERRACOTTA, List.of(ModBlocks.RGB_GLAZED_TERRACOTTA.get()));
        blockModels.createGlassBlocks(ModBlocks.RGB_STAINED_GLASS.get(), ModBlocks.RGB_STAINED_GLASS_PANE.get());
        blockModels.createCandleAndCandleCake(ModBlocks.RGB_CANDLE.get(), ModBlocks.RGB_CANDLE_CAKE.get());
        coloredBlockModels.createShulkerBox(ModBlocks.RGB_SHULKER_BOX.get(), WorldOfColor.asResource("entity/shulker/shulker_rgb"));
        coloredBlockModels.createBed(ModBlocks.RGB_BED.get());
        coloredBlockModels.createBanner(ModBlocks.RGB_BANNER.get(), ModBlocks.RGB_WALL_BANNER.get());

        coloredBlockModels.createTrivialBlock(ModBlocks.MISSINGNO.get(), cube(new Material(MissingTextureAtlasSprite.getLocation())), ModelTemplates.CUBE_ALL);
        blockModels.createFullAndCarpetBlocks(ModBlocks.MISSINGNO_WOOL.get(), ModBlocks.MISSINGNO_CARPET.get());
        blockModels.createTrivialCube(ModBlocks.MISSINGNO_TERRACOTTA.get());
        blockModels.createTrivialCube(ModBlocks.MISSINGNO_CONCRETE.get());
        blockModels.createColoredBlockWithRandomRotations(TexturedModel.CUBE, List.of(ModBlocks.MISSINGNO_CONCRETE_POWDER.get()));
        blockModels.createColoredBlockWithStateRotations(TexturedModel.GLAZED_TERRACOTTA, List.of(ModBlocks.MISSINGNO_GLAZED_TERRACOTTA.get()));
        blockModels.createGlassBlocks(ModBlocks.MISSINGNO_STAINED_GLASS.get(), ModBlocks.MISSINGNO_STAINED_GLASS_PANE.get());
        blockModels.createCandleAndCandleCake(ModBlocks.MISSINGNO_CANDLE.get(), ModBlocks.MISSINGNO_CANDLE_CAKE.get());
        coloredBlockModels.createShulkerBox(ModBlocks.MISSINGNO_SHULKER_BOX.get(), WorldOfColor.asResource("entity/shulker/shulker_missingno"));
        coloredBlockModels.createBed(ModBlocks.MISSINGNO_BED.get());

        coloredBlockModels.copyBlockModel(Blocks.CAULDRON, ModBlocks.DYED_WATER_CAULDRON.get());

        ModItems.COLORED_BOATS.forEach(item -> itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));
        ModItems.COLORED_CHEST_BOATS.forEach(item -> itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));
        ModItems.COLORED_ITEM_FRAMES.forEach(item -> itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));
        ModItems.COLORED_POTATO_PEELS.forEach(item -> itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));
        itemModels.generateFlatItem(ModItems.RGB_DYE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RGB_HARNESS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateBundleModels(ModItems.RGB_BUNDLE.get());
        itemModels.generateBundleModels(ModItems.MISSINGNO_BUNDLE.get());
        itemModels.generateFlatItem(ModItems.MISSINGNO_HARNESS.get(), ModelTemplates.FLAT_ITEM);
    }
}
