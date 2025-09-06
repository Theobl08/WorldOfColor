package net.theobl.worldofcolor.datagen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.MethodsReturnNonnullByDefault;
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

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import static net.minecraft.client.data.models.BlockModelGenerators.*;
import static net.minecraft.client.data.models.model.TextureMapping.*;
import static net.theobl.worldofcolor.datagen.VanillaModelTemplates.*;

public class ModModelProvider extends ModelProvider {
    public static final Map<BlockFamily.Variant, BiConsumer<ModBlockFamilyProvider, Block>> SHAPE_CONSUMERS = ImmutableMap.<BlockFamily.Variant, BiConsumer<ModBlockFamilyProvider, Block>>builder()
        .put(BlockFamily.Variant.BUTTON, BlockModelGenerators.BlockFamilyProvider::button)
        .put(BlockFamily.Variant.DOOR, ModBlockFamilyProvider::door)
        .put(BlockFamily.Variant.CHISELED, BlockModelGenerators.BlockFamilyProvider::fullBlockVariant)
        .put(BlockFamily.Variant.CRACKED, BlockModelGenerators.BlockFamilyProvider::fullBlockVariant)
        .put(BlockFamily.Variant.CUSTOM_FENCE, BlockModelGenerators.BlockFamilyProvider::customFence)
        .put(BlockFamily.Variant.FENCE, BlockModelGenerators.BlockFamilyProvider::fence)
        .put(BlockFamily.Variant.CUSTOM_FENCE_GATE, BlockModelGenerators.BlockFamilyProvider::customFenceGate)
        .put(BlockFamily.Variant.FENCE_GATE, BlockModelGenerators.BlockFamilyProvider::fenceGate)
        .put(BlockFamily.Variant.SIGN, BlockModelGenerators.BlockFamilyProvider::sign)
        .put(BlockFamily.Variant.SLAB, BlockModelGenerators.BlockFamilyProvider::slab)
        .put(BlockFamily.Variant.STAIRS, BlockModelGenerators.BlockFamilyProvider::stairs)
        .put(BlockFamily.Variant.PRESSURE_PLATE, BlockModelGenerators.BlockFamilyProvider::pressurePlate)
        .put(BlockFamily.Variant.TRAPDOOR, ModBlockFamilyProvider::trapdoor)
        .put(BlockFamily.Variant.WALL, BlockModelGenerators.BlockFamilyProvider::wall)
        .build();

    public ModModelProvider(PackOutput output) {
        super(output, WorldOfColor.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        ModBlockFamilies.getAllFamilies()
                .filter(BlockFamily::shouldGenerateModel)
                .forEach(blockFamily -> this.family(blockFamily.getBaseBlock(), blockModels).generateFor(blockFamily));
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

    public ModBlockFamilyProvider family(Block block, BlockModelGenerators blockModels) {
        TexturedModel texturedmodel = blockModels.texturedModels.getOrDefault(block, TexturedModel.CUBE.get(block));
        return new ModBlockFamilyProvider(texturedmodel.getMapping(), blockModels).fullBlock(block, texturedmodel.getTemplate());
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
        blockModels.blockStateOutput.accept(createOrientableTrapdoor(trapdoorBlock, resourcelocation, resourcelocation1, resourcelocation2));
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

    @ParametersAreNonnullByDefault
    @MethodsReturnNonnullByDefault
    public class ModBlockFamilyProvider extends BlockModelGenerators.BlockFamilyProvider {
        private final TextureMapping mapping;
        private final Map<ModelTemplate, ResourceLocation> models = Maps.newHashMap();
        @Nullable
        private BlockFamily family;
        @Nullable
        private ResourceLocation fullBlock;
        private final Set<Block> skipGeneratingModelsFor = new HashSet<>();
        private final BlockModelGenerators blockModels;

        public ModBlockFamilyProvider(TextureMapping mapping, BlockModelGenerators blockModels) {
            blockModels.super(mapping);
            this.mapping = mapping;
            this.blockModels = blockModels;
        }

        public ModBlockFamilyProvider fullBlock(Block block, ModelTemplate modelTemplate) {
            this.fullBlock = modelTemplate.create(block, this.mapping, blockModels.modelOutput);
            if (blockModels.fullBlockModelCustomGenerators.containsKey(block)) {
                blockModels.blockStateOutput
                        .accept(
                                blockModels.fullBlockModelCustomGenerators
                                        .get(block)
                                        .create(block, this.fullBlock, this.mapping, blockModels.modelOutput)
                        );
            } else {
                blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, this.fullBlock));
            }

            return this;
        }

        public BlockModelGenerators.BlockFamilyProvider sign(Block signBlock) {
            if (this.family == null) {
                throw new IllegalStateException("Family not defined");
            } else {
                Block block = this.family.getVariants().get(BlockFamily.Variant.WALL_SIGN);
                ResourceLocation resourcelocation = ModelTemplates.PARTICLE_ONLY.create(signBlock, this.mapping, blockModels.modelOutput);
                blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(signBlock, resourcelocation));
                blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, resourcelocation));
                blockModels.registerSimpleFlatItemModel(signBlock.asItem());
                return this;
            }
        }

        public BlockModelGenerators.BlockFamilyProvider slab(Block slabBlock) {
            if (this.fullBlock == null) {
                throw new IllegalStateException("Full block not generated yet");
            } else {
                ResourceLocation bottom = this.getOrCreateModel(ModelTemplates.SLAB_BOTTOM, slabBlock);
                ResourceLocation top = this.getOrCreateModel(ModelTemplates.SLAB_TOP, slabBlock);
                blockModels.blockStateOutput
                        .accept(BlockModelGenerators.createSlab(slabBlock, bottom, top, this.fullBlock));
                blockModels.registerSimpleItemModel(slabBlock, bottom);
                return this;
            }
        }

        public BlockModelGenerators.BlockFamilyProvider door(Block doorBlock) {
            createDoorWithRenderType(doorBlock, blockModels, "cutout");
            return this;
        }

        public void trapdoor(Block trapdoorBlock) {
            if (blockModels.nonOrientableTrapdoor.contains(trapdoorBlock)) {
                blockModels.createTrapdoor(trapdoorBlock);
            } else {
                createOrientableTrapdoorWithRenderType(trapdoorBlock, blockModels, "cutout");
            }
        }

        public ResourceLocation getOrCreateModel(ModelTemplate modelTemplate, Block block) {
            return this.models.computeIfAbsent(modelTemplate, template -> template.create(block, this.mapping, blockModels.modelOutput));
        }

        public BlockModelGenerators.BlockFamilyProvider generateFor(BlockFamily family) {
            this.family = family;
            family.getVariants().forEach((variant, block) -> {
                if (!this.skipGeneratingModelsFor.contains(block)) {
                    BiConsumer<ModBlockFamilyProvider, Block> biconsumer = ModModelProvider.SHAPE_CONSUMERS.get(variant);
                    if (biconsumer != null) {
                        biconsumer.accept(this, block);
                    }
                }
            });
            return this;
        }
    }
}
