package net.theobl.worldofcolor.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.AzaleaBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherRootsBlock;
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
        ModBlocks.COLORED_COPPER_BLOCKS.forEach(block -> blockModels.createTrivialCube(block.get()));
        ModBlocks.COLORED_COPPER_DOORS.forEach(block -> blockModels.createDoor(block.get()));
        ModBlocks.COLORED_COPPER_TRAPDOORS.forEach(block -> blockModels.createOrientableTrapdoor(block.get()));
        ModBlocks.COLORED_COPPER_GRATES.forEach(block -> blockModels.createTrivialCube(block.get()));
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
            blockModels.family(ModBlocks.COLORED_CUT_COPPER.pick(color).get())
                    .generateFor(ModBlockFamilies.COLORED_CUT_COPPER.pick(color))
                    .donateModelTo(ModBlocks.COLORED_CUT_COPPER.pick(color).get(), ModBlocks.COLORED_WAXED_CUT_COPPER.pick(color).get())
                    .donateModelTo(ModBlocks.COLORED_CHISELED_COPPER.pick(color).get(), ModBlocks.COLORED_WAXED_CHISELED_COPPER.pick(color).get())
                    .generateFor(ModBlockFamilies.COLORED_WAXED_CUT_COPPER.pick(color));
            blockModels.createCopperBulb(ModBlocks.COLORED_COPPER_BULBS.pick(color).get());
            blockModels.copyCopperBulbModel(ModBlocks.COLORED_COPPER_BULBS.pick(color).get(), ModBlocks.COLORED_WAXED_COPPER_BULBS.pick(color).get());
            blockModels.createFullAndCarpetBlocks(ModBlocks.CLASSIC_WOOLS.get(index).get(), ModBlocks.CLASSIC_CARPETS.get(index).get());
            blockModels.woodProvider(ModBlocks.COLORED_LOGS.pick(color).get()).logWithHorizontal(ModBlocks.COLORED_LOGS.pick(color).get()).wood(ModBlocks.COLORED_WOODS.pick(color).get());
            blockModels.woodProvider(ModBlocks.COLORED_STRIPPED_LOGS.pick(color).get()).logWithHorizontal(ModBlocks.COLORED_STRIPPED_LOGS.pick(color).get()).wood(ModBlocks.COLORED_STRIPPED_WOODS.pick(color).get());
            blockModels.copyModel(ModBlocks.COLORED_COPPER_BLOCKS.pick(color).get(), ModBlocks.COLORED_WAXED_COPPER_BLOCKS.pick(color).get());
            blockModels.copyDoorModel(ModBlocks.COLORED_COPPER_DOORS.pick(color).get(), ModBlocks.COLORED_WAXED_COPPER_DOORS.pick(color).get());
            blockModels.copyTrapdoorModel(ModBlocks.COLORED_COPPER_TRAPDOORS.pick(color).get(), ModBlocks.COLORED_WAXED_COPPER_TRAPDOORS.pick(color).get());
            blockModels.copyModel(ModBlocks.COLORED_COPPER_GRATES.pick(color).get(), ModBlocks.COLORED_WAXED_COPPER_GRATES.pick(color).get());
            blockModels.createBarsAndItem(ModBlocks.COLORED_COPPER_BARS.pick(color).get(), ModBlocks.COLORED_WAXED_COPPER_BARS.pick(color).get());
            blockModels.createCopperChainItem(ModBlocks.COLORED_COPPER_CHAINS.pick(color).asItem(), ModBlocks.COLORED_WAXED_COPPER_CHAINS.pick(color).asItem());
            blockModels.createCopperChain(ModBlocks.COLORED_COPPER_CHAINS.pick(color).get(), ModBlocks.COLORED_WAXED_COPPER_CHAINS.pick(color).get());
            blockModels.createCopperLantern(ModBlocks.COLORED_COPPER_LANTERNS.pick(color).get(), ModBlocks.COLORED_WAXED_COPPER_LANTERNS.pick(color).get());
            blockModels.createLightningRod(ModBlocks.COLORED_LIGHTNING_RODS.pick(color).get(), ModBlocks.COLORED_WAXED_LIGHTNING_RODS.pick(color).get());
            blockModels.createChest(ModBlocks.COLORED_COPPER_CHESTS.pick(color).get(), ModBlocks.COLORED_COPPER_BLOCKS.pick(color).get(), WorldOfColor.asResource("copper_" + color.getName()), false);
            blockModels.copyModel(ModBlocks.COLORED_COPPER_CHESTS.pick(color).get(), ModBlocks.COLORED_WAXED_COPPER_CHESTS.pick(color).get());
            coloredBlockModels.createCopperGolemStatue(ModBlocks.COLORED_COPPER_GOLEM_STATUES.pick(color).get(), ModBlocks.COLORED_COPPER_BLOCKS.pick(color).get(), color);
            blockModels.copyModel(ModBlocks.COLORED_COPPER_GOLEM_STATUES.pick(color).get(), ModBlocks.COLORED_WAXED_COPPER_GOLEM_STATUES.pick(color).get());
            coloredBlockModels.createCauldrons(color);
            coloredBlockModels.createTrivialBlock(ModBlocks.QUILTED_CONCRETES.pick(color).get(), cube(Blocks.DYED_SHULKER_BOX.pick(color)), ModelTemplates.CUBE_ALL);
            blockModels.createPlantWithDefaultItem(ModBlocks.COLORED_SAPLINGS.pick(color).get(), ModBlocks.POTTED_COLORED_SAPLINGS.get(color).get(), BlockModelGenerators.PlantType.NOT_TINTED);
            ModBlocks.COLORED_POTTED_PLANTS.forEach((plant, pottedPlant) -> {
                BlockModelGenerators.PlantType plantType = BlockModelGenerators.PlantType.NOT_TINTED;
                if(plant.get() == Blocks.FERN) {
                    plantType = BlockModelGenerators.PlantType.TINTED;
                } else if (plant.get() == Blocks.OPEN_EYEBLOSSOM) {
                    plantType = BlockModelGenerators.PlantType.EMISSIVE_NOT_TINTED;
                }
                if(plant.get() == Blocks.BAMBOO || plant.get() == Blocks.MANGROVE_PROPAGULE || plant.get() == Blocks.CACTUS || plant.get() instanceof AzaleaBlock || plant.get() instanceof NetherRootsBlock) {
                    String suffix = plant.get() instanceof AzaleaBlock ? "_bush" : "";
                    coloredBlockModels.createPottedPlant(pottedPlant.get(color).get(), ModBlocks.COLORED_FLOWER_POTS.pick(color).get(), plant.getId().getPath() + suffix);
                } else {
                    coloredBlockModels.createPottedPlant(plant.get(), pottedPlant.get(color).get(), ModBlocks.COLORED_FLOWER_POTS.pick(color).get(), plantType);
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
        blockModels.createFullAndCarpetBlocks(ModBlocks.RGB_WOOL.get(), ModBlocks.RGB_CARPET.get());
        blockModels.createTrivialCube(ModBlocks.RGB_TERRACOTTA.get());
        blockModels.createTrivialCube(ModBlocks.RGB_CONCRETE.get());
        blockModels.createColoredBlockWithRandomRotations(TexturedModel.CUBE, List.of(ModBlocks.RGB_CONCRETE_POWDER.get()));
        blockModels.createColoredBlockWithStateRotations(TexturedModel.GLAZED_TERRACOTTA, List.of(ModBlocks.RGB_GLAZED_TERRACOTTA.get()));
        blockModels.createGlassBlocks(ModBlocks.RGB_STAINED_GLASS.get(), ModBlocks.RGB_STAINED_GLASS_PANE.get());
        blockModels.createCandleAndCandleCake(ModBlocks.RGB_CANDLE.get(), ModBlocks.RGB_CANDLE_CAKE.get());
        coloredBlockModels.createShulkerBox(ModBlocks.RGB_SHULKER_BOX.get());
        coloredBlockModels.createBed(ModBlocks.RGB_BED.get());
        coloredBlockModels.createBanner(ModBlocks.RGB_BANNER.get(), ModBlocks.RGB_WALL_BANNER.get());

        coloredBlockModels.copyBlockModel(Blocks.CAULDRON, ModBlocks.DYED_WATER_CAULDRON.get());

        ModItems.COLORED_BOATS.forEach(item -> itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));
        ModItems.COLORED_CHEST_BOATS.forEach(item -> itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));
        ModItems.COLORED_ITEM_FRAMES.forEach(item -> itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));
        ModItems.COLORED_POTATO_PEELS.forEach(item -> itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));
        itemModels.generateFlatItem(ModItems.RGB_DYE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RGB_HARNESS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateBundleModels(ModItems.RGB_BUNDLE.get());
    }
}
