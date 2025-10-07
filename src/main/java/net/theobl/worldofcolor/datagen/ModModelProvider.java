package net.theobl.worldofcolor.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.AzaleaBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RootsBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.util.ModUtil;

import static net.minecraft.client.data.models.model.TextureMapping.*;
import static net.theobl.worldofcolor.datagen.VanillaModelTemplates.*;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, WorldOfColor.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        ColoredBlockModelGenerators coloredBlockModels = new ColoredBlockModelGenerators(blockModels);
        ModBlockFamilies.getAllFamilies()
                .filter(BlockFamily::shouldGenerateModel)
                .forEach(blockFamily -> coloredBlockModels.family(blockFamily.getBaseBlock()).generateFor(blockFamily));
        ModBlocks.SIMPLE_COLORED_BLOCKS.forEach(block -> blockModels.createTrivialCube(block.get()));
        ModBlocks.GLAZED_CONCRETES.forEach(block -> blockModels.createColoredBlockWithStateRotations(TexturedModel.GLAZED_TERRACOTTA, block.get()));
        ModBlocks.COLORED_LEAVES.forEach(block -> blockModels.createTrivialCube(block.get()));
        ModBlocks.COLORED_COPPER_BLOCKS.forEach(block -> blockModels.createTrivialCube(block.get()));
        ModBlocks.COLORED_COPPER_DOORS.forEach(block -> coloredBlockModels.createDoorWithRenderType(block.get(), "cutout"));
        ModBlocks.COLORED_COPPER_TRAPDOORS.forEach(block -> coloredBlockModels.createOrientableTrapdoorWithRenderType(block.get(), "cutout"));
        ModBlocks.COLORED_COPPER_GRATES.forEach(block -> coloredBlockModels.createTrivialCubeWithRenderType(block.get(), "cutout"));
        ModBlocks.COLORED_SLIME_BLOCKS.forEach(block -> coloredBlockModels.createTrivialBlockWithRenderType(block.get(), TexturedModel.createDefault(TextureMapping::defaultTexture, SLIME_BLOCK), "translucent"));
        ModBlocks.COLORED_FLOWER_POTS.forEach(block -> {
            blockModels.registerSimpleFlatItemModel(block.asItem());
            coloredBlockModels.createTrivialBlock(block.get(), ColoredTextureMapping.flowerPot(block.get()), FLOWER_POT);
        });
        for(DyeColor color : ModUtil.COLORS) {
            int index = ModUtil.COLORS.indexOf(color);
            blockModels.family(ModBlocks.COLORED_CUT_COPPER.get(index).get())
                    .generateFor(ModBlockFamilies.COLORED_CUT_COPPER.get(index))
                    .donateModelTo(ModBlocks.COLORED_CUT_COPPER.get(index).get(), ModBlocks.COLORED_WAXED_CUT_COPPER.get(index).get())
                    .donateModelTo(ModBlocks.COLORED_CHISELED_COPPER.get(index).get(), ModBlocks.COLORED_WAXED_CHISELED_COPPER.get(index).get())
                    .generateFor(ModBlockFamilies.COLORED_WAXED_CUT_COPPER.get(index));
            blockModels.createCopperBulb(ModBlocks.COLORED_COPPER_BULBS.get(index).get());
            blockModels.copyCopperBulbModel(ModBlocks.COLORED_COPPER_BULBS.get(index).get(), ModBlocks.COLORED_WAXED_COPPER_BULBS.get(index).get());
            blockModels.createFullAndCarpetBlocks(ModBlocks.CLASSIC_WOOLS.get(index).get(), ModBlocks.CLASSIC_CARPETS.get(index).get());
            blockModels.woodProvider(ModBlocks.COLORED_LOGS.get(index).get()).logWithHorizontal(ModBlocks.COLORED_LOGS.get(index).get()).wood(ModBlocks.COLORED_WOODS.get(index).get());
            blockModels.woodProvider(ModBlocks.COLORED_STRIPPED_LOGS.get(index).get()).logWithHorizontal(ModBlocks.COLORED_STRIPPED_LOGS.get(index).get()).wood(ModBlocks.COLORED_STRIPPED_WOODS.get(index).get());
            blockModels.createHangingSign(ModBlocks.COLORED_STRIPPED_LOGS.get(index).get(), ModBlocks.COLORED_HANGING_SIGNS.get(index).get(), ModBlocks.COLORED_WALL_HANGING_SIGNS.get(index).get());
            blockModels.copyModel(ModBlocks.COLORED_COPPER_BLOCKS.get(index).get(), ModBlocks.COLORED_WAXED_COPPER_BLOCKS.get(index).get());
            blockModels.copyDoorModel(ModBlocks.COLORED_COPPER_DOORS.get(index).get(), ModBlocks.COLORED_WAXED_COPPER_DOORS.get(index).get());
            blockModels.copyTrapdoorModel(ModBlocks.COLORED_COPPER_TRAPDOORS.get(index).get(), ModBlocks.COLORED_WAXED_COPPER_TRAPDOORS.get(index).get());
            blockModels.copyModel(ModBlocks.COLORED_COPPER_GRATES.get(index).get(), ModBlocks.COLORED_WAXED_COPPER_GRATES.get(index).get());
            coloredBlockModels.createBarsAndItem(ModBlocks.COLORED_COPPER_BARS.get(index).get(), ModBlocks.COLORED_WAXED_COPPER_BARS.get(index).get());
            coloredBlockModels.createCopperChain(ModBlocks.COLORED_COPPER_CHAINS.get(index).get(), ModBlocks.COLORED_WAXED_COPPER_CHAINS.get(index).get());
            coloredBlockModels.createCopperLantern(ModBlocks.COLORED_COPPER_LANTERNS.get(index).get(), ModBlocks.COLORED_WAXED_COPPER_LANTERNS.get(index).get());
            blockModels.createLightningRod(ModBlocks.COLORED_LIGHTNING_RODS.get(index).get(), ModBlocks.COLORED_WAXED_LIGHTNING_RODS.get(index).get());
            coloredBlockModels.createCopperGolemStatue(ModBlocks.COLORED_COPPER_GOLEM_STATUES.get(index).get(), ModBlocks.COLORED_COPPER_BLOCKS.get(index).get(), color);
            blockModels.copyModel(ModBlocks.COLORED_COPPER_GOLEM_STATUES.get(index).get(), ModBlocks.COLORED_WAXED_COPPER_GOLEM_STATUES.get(index).get());
            coloredBlockModels.createCauldrons(color);
            coloredBlockModels.createTrivialBlock(ModBlocks.QUILTED_CONCRETES.get(index).get(), cube(ModUtil.SHULKER_BOXES.get(index)), ModelTemplates.CUBE_ALL);
            coloredBlockModels.createPlantWithDefaultItem(ModBlocks.COLORED_SAPLINGS.get(index).get(), ModBlocks.POTTED_COLORED_SAPLINGS.get(index).get(), BlockModelGenerators.PlantType.NOT_TINTED);
//            coloredBlockModels.createPottedPlant(Blocks.SPRUCE_SAPLING, ModBlocks.COLORED_POTTED_SPRUCE_SAPLING.get(index).get(), ModBlocks.COLORED_FLOWER_POTS.get(index).get(), BlockModelGenerators.PlantType.NOT_TINTED);
            ModBlocks.COLORED_POTTED_PLANTS.forEach((plant, pottedPlant) -> {
                BlockModelGenerators.PlantType plantType = BlockModelGenerators.PlantType.NOT_TINTED;
                if(plant.get() == Blocks.FERN) {
                    plantType = BlockModelGenerators.PlantType.TINTED;
                } else if (plant.get() == Blocks.OPEN_EYEBLOSSOM) {
                    plantType = BlockModelGenerators.PlantType.EMISSIVE_NOT_TINTED;
                }
                if(plant.get() == Blocks.BAMBOO || plant.get() == Blocks.MANGROVE_PROPAGULE || plant.get() == Blocks.CACTUS || plant.get() instanceof AzaleaBlock || plant.get() instanceof RootsBlock) {
                    String suffix = plant.get() instanceof AzaleaBlock ? "_bush" : "";
                    coloredBlockModels.createPottedPlant(pottedPlant.get(index).get(), ModBlocks.COLORED_FLOWER_POTS.get(index).get(), BuiltInRegistries.BLOCK.getKey(plant.get()).getPath() + suffix);
                } else {
                    coloredBlockModels.createPottedPlant(plant.get(), pottedPlant.get(index).get(), ModBlocks.COLORED_FLOWER_POTS.get(index).get(), plantType);
                }
            });
        }

        ModItems.COLORED_BOATS.forEach(item -> itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));
        ModItems.COLORED_CHEST_BOATS.forEach(item -> itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));
    }
}
