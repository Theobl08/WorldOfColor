package net.theobl.worldofcolor.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.*;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.util.ModUtil;

import static net.minecraft.client.data.models.BlockModelGenerators.*;
import static net.minecraft.client.data.models.model.TextureMapping.*;
import static net.theobl.worldofcolor.datagen.VanillaModelTemplates.*;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, WorldOfColor.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        ModBlockFamilies.getAllFamilies()
                .filter(BlockFamily::shouldGenerateModel)
                .forEach(blockFamily -> blockModels.family(blockFamily.getBaseBlock()).generateFor(blockFamily));
        ModBlocks.SIMPLE_COLORED_BLOCKS.forEach(block -> blockModels.createTrivialCube(block.get()));
        ModBlocks.GLAZED_CONCRETES.forEach(block -> {
            ResourceLocation resourcelocation = TexturedModel.GLAZED_TERRACOTTA.create(block.get(), blockModels.modelOutput);
            blockModels.blockStateOutput
                    .accept(
                            MultiVariantGenerator.multiVariant(block.get(), Variant.variant().with(VariantProperties.MODEL, resourcelocation))
                                    .with(createHorizontalFacingDispatchAlt())
                    );
        });
        ModBlocks.COLORED_LIGHTNING_RODS.forEach(block -> {
            int index = ModBlocks.COLORED_LIGHTNING_RODS.indexOf(block);
            ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(Blocks.LIGHTNING_ROD, "_on");
            ResourceLocation resourcelocation1 = ModelLocationUtils.getModelLocation(block.get());
            blockModels.blockStateOutput
                    .accept(
                            MultiVariantGenerator.multiVariant(block.get(), Variant.variant().with(VariantProperties.MODEL, LIGHTNING_ROD.create(block.get(), lightningRod(ModUtil.COLORS.get(index)), blockModels.modelOutput)))
                                    .with(blockModels.createColumnWithFacing())
                                    .with(createBooleanModelDispatch(BlockStateProperties.POWERED, resourcelocation, resourcelocation1))
                    );
        });
        ModBlocks.COLORED_SAPLINGS.forEach(block -> createCrossBlockWithDefaultItem(block.get(), BlockModelGenerators.PlantType.NOT_TINTED, blockModels, "cutout"));
        ModBlocks.COLORED_LEAVES.forEach(block -> blockModels.createTrivialCube(block.get()));
        ModBlocks.COLORED_COPPER_BLOCKS.forEach(block -> blockModels.createTrivialCube(block.get()));
        ModBlocks.COLORED_COPPER_DOORS.forEach(block -> createDoorWithRenderType(block.get(), blockModels, "cutout"));
        ModBlocks.COLORED_COPPER_TRAPDOORS.forEach(block -> createOrientableTrapdoorWithRenderType(block.get(), blockModels, "cutout"));
        ModBlocks.COLORED_COPPER_GRATES.forEach(block -> blockModels.createTrivialBlock(block.get(), TexturedModel.CUBE.updateTemplate(template -> template.extend().renderType("cutout").build())));
        ModBlocks.COLORED_SLIME_BLOCKS.forEach(block ->
                blockModels.blockStateOutput
                        .accept(
                                createSimpleBlock(
                                        block.get(),
                                        SLIME_BLOCK.extend().renderType("translucent").build()
                                                .create(block.get(), defaultTexture(block.get()), blockModels.modelOutput)
                                )
                        )
        );
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
            createCauldrons(blockModels, color);
            blockModels.blockStateOutput
                    .accept(
                            createSimpleBlock(
                                    ModBlocks.QUILTED_CONCRETES.get(index).get(),
                                    ModelTemplates.CUBE_ALL
                                            .create(ModBlocks.QUILTED_CONCRETES.get(index).get(), cube(ModUtil.SHULKER_BOXES.get(index)), blockModels.modelOutput)
                            )
                    );
        }

        ModItems.COLORED_BOATS.forEach(item -> itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));
        ModItems.COLORED_CHEST_BOATS.forEach(item -> itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));
    }

    public void createCauldrons(BlockModelGenerators blockModels, DyeColor color) {
        int index = ModUtil.COLORS.indexOf(color);
        Block cauldron = ModBlocks.COLORED_CAULDRONS.get(index).get();
        Block lavaCauldron = ModBlocks.COLORED_LAVA_CAULDRONS.get(index).get();
        Block waterCauldron = ModBlocks.COLORED_WATER_CAULDRONS.get(index).get();
        Block powderSnowCauldron = ModBlocks.COLORED_POWDER_SNOW_CAULDRONS.get(index).get();
        blockModels.registerSimpleFlatItemModel(ModItems.COLORED_CAULDRONS.get(index).get());
        blockModels.blockStateOutput
                .accept(
                        createSimpleBlock(
                                cauldron,
                                CAULDRON
                                        .create(cauldron, cauldronEmpty(color), blockModels.modelOutput)
                        )
                );
        blockModels.blockStateOutput
                .accept(
                        createSimpleBlock(
                                lavaCauldron,
                                CAULDRON.extend().parent(ResourceLocation.parse("block/lava_cauldron")).build()
                                        .create(lavaCauldron, cauldron(getBlockTexture(Blocks.LAVA, "_still"), color), blockModels.modelOutput)
                        )
                );
        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.multiVariant(waterCauldron)
                                .with(
                                        PropertyDispatch.property(LayeredCauldronBlock.LEVEL)
                                                .select(
                                                        1,
                                                        Variant.variant()
                                                                .with(
                                                                        VariantProperties.MODEL,
                                                                        CAULDRON.extend().parent(ResourceLocation.parse("block/water_cauldron_level1")).build()
                                                                                .createWithSuffix(
                                                                                        waterCauldron,
                                                                                        "_level1",
                                                                                        cauldronEmpty(color),
                                                                                        blockModels.modelOutput
                                                                                )
                                                                )
                                                )
                                                .select(
                                                        2,
                                                        Variant.variant()
                                                                .with(
                                                                        VariantProperties.MODEL,
                                                                        CAULDRON.extend().parent(ResourceLocation.parse("block/water_cauldron_level2")).build()
                                                                                .createWithSuffix(
                                                                                        waterCauldron,
                                                                                        "_level2",
                                                                                        cauldronEmpty(color),
                                                                                        blockModels.modelOutput
                                                                                )
                                                                )
                                                )
                                                .select(
                                                        3,
                                                        Variant.variant()
                                                                .with(
                                                                        VariantProperties.MODEL,
                                                                        CAULDRON.extend().parent(ResourceLocation.parse("block/water_cauldron_full")).build()
                                                                                .createWithSuffix(
                                                                                        waterCauldron,
                                                                                        "_full",
                                                                                        cauldronEmpty(color),
                                                                                        blockModels.modelOutput
                                                                                )
                                                                )
                                                )
                                )
                );
        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.multiVariant(powderSnowCauldron)
                                .with(
                                        PropertyDispatch.property(LayeredCauldronBlock.LEVEL)
                                                .select(
                                                        1,
                                                        Variant.variant()
                                                                .with(
                                                                        VariantProperties.MODEL,
                                                                        CAULDRON.extend().parent(ResourceLocation.parse("block/powder_snow_cauldron_level1")).build()
                                                                                .createWithSuffix(
                                                                                        powderSnowCauldron,
                                                                                        "_level1",
                                                                                        cauldron(getBlockTexture(Blocks.POWDER_SNOW), color),
                                                                                        blockModels.modelOutput
                                                                                )
                                                                )
                                                )
                                                .select(
                                                        2,
                                                        Variant.variant()
                                                                .with(
                                                                        VariantProperties.MODEL,
                                                                        CAULDRON.extend().parent(ResourceLocation.parse("block/powder_snow_cauldron_level2")).build()
                                                                                .createWithSuffix(
                                                                                        powderSnowCauldron,
                                                                                        "_level2",
                                                                                        cauldron(getBlockTexture(Blocks.POWDER_SNOW), color),
                                                                                        blockModels.modelOutput
                                                                                )
                                                                )
                                                )
                                                .select(
                                                        3,
                                                        Variant.variant()
                                                                .with(
                                                                        VariantProperties.MODEL,
                                                                        CAULDRON.extend().parent(ResourceLocation.parse("block/powder_snow_cauldron_full")).build()
                                                                                .createWithSuffix(
                                                                                        powderSnowCauldron,
                                                                                        "_full",
                                                                                        cauldron(getBlockTexture(Blocks.POWDER_SNOW), color),
                                                                                        blockModels.modelOutput
                                                                                )
                                                                )
                                                )
                                )
                );
    }

    public void createDoorWithRenderType(Block doorBlock, BlockModelGenerators blockModels, String renderType) {
        TextureMapping texturemapping = TextureMapping.door(doorBlock);
        ResourceLocation resourcelocation = ModelTemplates.DOOR_BOTTOM_LEFT.extend().renderType(renderType).build().create(doorBlock, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation1 = ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.extend().renderType(renderType).build().create(doorBlock, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation2 = ModelTemplates.DOOR_BOTTOM_RIGHT.extend().renderType(renderType).build().create(doorBlock, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation3 = ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.extend().renderType(renderType).build().create(doorBlock, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation4 = ModelTemplates.DOOR_TOP_LEFT.extend().renderType(renderType).build().create(doorBlock, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation5 = ModelTemplates.DOOR_TOP_LEFT_OPEN.extend().renderType(renderType).build().create(doorBlock, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation6 = ModelTemplates.DOOR_TOP_RIGHT.extend().renderType(renderType).build().create(doorBlock, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation7 = ModelTemplates.DOOR_TOP_RIGHT_OPEN.extend().renderType(renderType).build().create(doorBlock, texturemapping, blockModels.modelOutput);
        blockModels.registerSimpleFlatItemModel(doorBlock.asItem());
        blockModels.blockStateOutput
                .accept(
                        createDoor(
                                doorBlock,
                                resourcelocation,
                                resourcelocation1,
                                resourcelocation2,
                                resourcelocation3,
                                resourcelocation4,
                                resourcelocation5,
                                resourcelocation6,
                                resourcelocation7
                        )
                );
    }

    public void createOrientableTrapdoorWithRenderType(Block trapdoorBlock, BlockModelGenerators blockModels, String renderType) {
        TextureMapping texturemapping = TextureMapping.defaultTexture(trapdoorBlock);
        ResourceLocation resourcelocation = ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.extend().renderType(renderType).build().create(trapdoorBlock, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation1 = ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.extend().renderType(renderType).build().create(trapdoorBlock, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation2 = ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.extend().renderType(renderType).build().create(trapdoorBlock, texturemapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(createTrapdoor(trapdoorBlock, resourcelocation, resourcelocation1, resourcelocation2));
        blockModels.registerSimpleItemModel(trapdoorBlock, resourcelocation1);
    }

    public void createCrossBlockWithDefaultItem(Block block, BlockModelGenerators.PlantType plantType, BlockModelGenerators blockModels, String renderType) {
        blockModels.registerSimpleItemModel(block.asItem(), plantType.createItemModel(blockModels, block));
        TextureMapping texturemapping = plantType.getTextureMapping(block);
        ResourceLocation resourcelocation = plantType.getCross().extend().renderType(renderType).build().create(block, texturemapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(createSimpleBlock(block, resourcelocation));
    }

    public static TextureMapping cauldron(ResourceLocation texture, DyeColor color) {
        int index = ModUtil.COLORS.indexOf(color);
        Block cauldron = ModBlocks.COLORED_CAULDRONS.get(index).get();
        return new TextureMapping()
                .put(TextureSlot.PARTICLE, getBlockTexture(cauldron, "_side"))
                .put(TextureSlot.SIDE, getBlockTexture(cauldron, "_side"))
                .put(TextureSlot.TOP, getBlockTexture(cauldron, "_top"))
                .put(TextureSlot.BOTTOM, getBlockTexture(cauldron, "_bottom"))
                .put(TextureSlot.INSIDE, getBlockTexture(cauldron, "_inner"))
                .put(TextureSlot.CONTENT, texture);
    }

    public static TextureMapping cauldronEmpty(DyeColor color) {
        int index = ModUtil.COLORS.indexOf(color);
        Block cauldron = ModBlocks.COLORED_CAULDRONS.get(index).get();
        return new TextureMapping()
                .put(TextureSlot.PARTICLE, getBlockTexture(cauldron, "_side"))
                .put(TextureSlot.SIDE, getBlockTexture(cauldron, "_side"))
                .put(TextureSlot.TOP, getBlockTexture(cauldron, "_top"))
                .put(TextureSlot.BOTTOM, getBlockTexture(cauldron, "_bottom"))
                .put(TextureSlot.INSIDE, getBlockTexture(cauldron, "_inner"));
    }

    public static TextureMapping lightningRod(DyeColor color) {
        int index = ModUtil.COLORS.indexOf(color);
        Block lightningRod = ModBlocks.COLORED_LIGHTNING_RODS.get(index).get();
        return new TextureMapping()
                .put(TextureSlot.PARTICLE, getBlockTexture(lightningRod))
                .put(TextureSlot.TEXTURE, getBlockTexture(lightningRod));
    }
}
