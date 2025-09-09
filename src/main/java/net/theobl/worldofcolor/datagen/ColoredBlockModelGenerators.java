package net.theobl.worldofcolor.datagen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredBlock;
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
import static net.minecraft.client.data.models.model.TextureMapping.getBlockTexture;
import static net.theobl.worldofcolor.datagen.ColoredTextureMapping.cauldronEmpty;
import static net.theobl.worldofcolor.datagen.ColoredTextureMapping.lightningRod;
import static net.theobl.worldofcolor.datagen.VanillaModelTemplates.CAULDRON;
import static net.theobl.worldofcolor.datagen.VanillaModelTemplates.LIGHTNING_ROD;

public class ColoredBlockModelGenerators {
    private final BlockModelGenerators blockModels;

    public final Map<BlockFamily.Variant, BiConsumer<ModBlockFamilyProvider, Block>> SHAPE_CONSUMERS = ImmutableMap.<BlockFamily.Variant, BiConsumer<ModBlockFamilyProvider, Block>>builder()
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

    public ColoredBlockModelGenerators(BlockModelGenerators blockModels) {
        this.blockModels = blockModels;
    }

    public void createTrivialCubeWithRenderType(Block block, String renderType) {
        blockModels.createTrivialBlock(block, TexturedModel.CUBE.updateTemplate(template -> template.extend().renderType(renderType).build()));
    }

    public void createTrivialBlockWithRenderType(Block block, TexturedModel.Provider provider, String renderType) {
        blockModels.blockStateOutput.accept(createSimpleBlock(block, plainVariant(provider.updateTemplate(template -> template.extend().renderType(renderType).build()).create(block, blockModels.modelOutput))));
    }

    public void createTrivialBlock(Block block, TextureMapping mapping, ModelTemplate template) {
        blockModels.blockStateOutput.accept(createSimpleBlock(block, plainVariant(template.create(block, mapping, blockModels.modelOutput))));
    }

    public void createCauldrons(DyeColor color) {
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
                                plainVariant(
                                CAULDRON
                                        .create(cauldron, cauldronEmpty(color), blockModels.modelOutput)
                                )
                        )
                );
        blockModels.blockStateOutput
                .accept(
                        createSimpleBlock(
                                lavaCauldron,
                                plainVariant(
                                CAULDRON.extend().parent(ResourceLocation.parse("block/lava_cauldron")).build()
                                        .create(lavaCauldron, ColoredTextureMapping.cauldron(getBlockTexture(Blocks.LAVA, "_still"), color), blockModels.modelOutput)
                                )
                        )
                );
        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.dispatch(waterCauldron)
                                .with(
                                        PropertyDispatch.initial(LayeredCauldronBlock.LEVEL)
                                                .select(
                                                        1,
                                                        plainVariant(
                                                                        VanillaModelTemplates.cauldronLevelX(color, 1)
                                                                                .createWithSuffix(
                                                                                        waterCauldron,
                                                                                        "_level1",
                                                                                        TextureMapping.particle(getBlockTexture(cauldron, "_side")),
                                                                                        blockModels.modelOutput
                                                                                )
                                                                )
                                                )
                                                .select(
                                                        2,
                                                        plainVariant(
                                                                        VanillaModelTemplates.cauldronLevelX(color, 2)
                                                                                .createWithSuffix(
                                                                                        waterCauldron,
                                                                                        "_level2",
                                                                                        TextureMapping.particle(getBlockTexture(cauldron, "_side")),
                                                                                        blockModels.modelOutput
                                                                                )
                                                                )
                                                )
                                                .select(
                                                        3,
                                                        plainVariant(
                                                                        VanillaModelTemplates.cauldronLevelX(color, 3)
                                                                                .createWithSuffix(
                                                                                        waterCauldron,
                                                                                        "_full",
                                                                                        TextureMapping.particle(getBlockTexture(cauldron, "_side")),
                                                                                        blockModels.modelOutput
                                                                                )
                                                                )
                                                )
                                )
                );
        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.dispatch(powderSnowCauldron)
                                .with(
                                        PropertyDispatch.initial(LayeredCauldronBlock.LEVEL)
                                                .select(
                                                        1,
                                                        plainVariant(
                                                                        CAULDRON.extend().parent(ResourceLocation.parse("block/powder_snow_cauldron_level1")).build()
                                                                                .createWithSuffix(
                                                                                        powderSnowCauldron,
                                                                                        "_level1",
                                                                                        ColoredTextureMapping.cauldron(getBlockTexture(Blocks.POWDER_SNOW), color),
                                                                                        blockModels.modelOutput
                                                                                )
                                                                )
                                                )
                                                .select(
                                                        2,
                                                        plainVariant(
                                                                        CAULDRON.extend().parent(ResourceLocation.parse("block/powder_snow_cauldron_level2")).build()
                                                                                .createWithSuffix(
                                                                                        powderSnowCauldron,
                                                                                        "_level2",
                                                                                        ColoredTextureMapping.cauldron(getBlockTexture(Blocks.POWDER_SNOW), color),
                                                                                        blockModels.modelOutput
                                                                                )
                                                                )
                                                )
                                                .select(
                                                        3,
                                                        plainVariant(
                                                                        CAULDRON.extend().parent(ResourceLocation.parse("block/powder_snow_cauldron_full")).build()
                                                                                .createWithSuffix(
                                                                                        powderSnowCauldron,
                                                                                        "_full",
                                                                                        ColoredTextureMapping.cauldron(getBlockTexture(Blocks.POWDER_SNOW), color),
                                                                                        blockModels.modelOutput
                                                                                )
                                                                )
                                                )
                                )
                );
    }

    public void createDoorWithRenderType(Block doorBlock, String renderType) {
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
                                plainVariant(resourcelocation),
                                plainVariant(resourcelocation1),
                                plainVariant(resourcelocation2),
                                plainVariant(resourcelocation3),
                                plainVariant(resourcelocation4),
                                plainVariant(resourcelocation5),
                                plainVariant(resourcelocation6),
                                plainVariant(resourcelocation7)
                        )
                );
    }

    public void createOrientableTrapdoorWithRenderType(Block trapdoorBlock, String renderType) {
        TextureMapping texturemapping = TextureMapping.defaultTexture(trapdoorBlock);
        ResourceLocation resourcelocation = ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.extend().renderType(renderType).build().create(trapdoorBlock, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation1 = ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.extend().renderType(renderType).build().create(trapdoorBlock, texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation2 = ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.extend().renderType(renderType).build().create(trapdoorBlock, texturemapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(createOrientableTrapdoor(trapdoorBlock, plainVariant(resourcelocation), plainVariant(resourcelocation1), plainVariant(resourcelocation2)));
        blockModels.registerSimpleItemModel(trapdoorBlock, resourcelocation1);
    }

    public void createCrossBlockWithDefaultItem(Block block, BlockModelGenerators.PlantType plantType, String renderType) {
        blockModels.registerSimpleItemModel(block.asItem(), plantType.createItemModel(blockModels, block));
        TextureMapping texturemapping = plantType.getTextureMapping(block);
        ResourceLocation resourcelocation = plantType.getCross().extend().renderType(renderType).build().create(block, texturemapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(createSimpleBlock(block, plainVariant(resourcelocation)));
    }

    public void createLightningRods(DeferredBlock<Block> block) {
        ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(Blocks.LIGHTNING_ROD, "_on");
        ResourceLocation resourcelocation1 = LIGHTNING_ROD.create(block.get(), lightningRod(block.get()), blockModels.modelOutput);
        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.dispatch(block.get())
                                .with(createBooleanModelDispatch(BlockStateProperties.POWERED, plainVariant(resourcelocation), plainVariant(resourcelocation1)))
                                .with(ROTATIONS_COLUMN_WITH_FACING)
                );
    }

    public ModBlockFamilyProvider family(Block block) {
        TexturedModel texturedmodel = TEXTURED_MODELS.getOrDefault(block, TexturedModel.CUBE.get(block));
        return new ModBlockFamilyProvider(texturedmodel.getMapping(), blockModels).fullBlock(block, texturedmodel.getTemplate());
    }

    @ParametersAreNonnullByDefault
    @MethodsReturnNonnullByDefault
    public class ModBlockFamilyProvider extends BlockModelGenerators.BlockFamilyProvider {
        private final TextureMapping mapping;
        private final Map<ModelTemplate, ResourceLocation> models = Maps.newHashMap();
        @Nullable
        private BlockFamily family;
        @Nullable
        private Variant fullBlock;
        private final Set<Block> skipGeneratingModelsFor = new HashSet<>();
        private final BlockModelGenerators blockModels;

        public ModBlockFamilyProvider(TextureMapping mapping, BlockModelGenerators blockModels) {
            blockModels.super(mapping);
            this.mapping = mapping;
            this.blockModels = blockModels;
        }

        public ModBlockFamilyProvider fullBlock(Block block, ModelTemplate modelTemplate) {
            this.fullBlock = plainModel(modelTemplate.create(block, this.mapping, blockModels.modelOutput));
            if (FULL_BLOCK_MODEL_CUSTOM_GENERATORS.containsKey(block)) {
                blockModels.blockStateOutput
                        .accept(
                                FULL_BLOCK_MODEL_CUSTOM_GENERATORS
                                        .get(block)
                                        .create(block, (this.fullBlock), this.mapping, blockModels.modelOutput)
                        );
            } else {
                blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, variant(this.fullBlock)));
            }

            return this;
        }

        public BlockModelGenerators.BlockFamilyProvider sign(Block signBlock) {
            if (this.family == null) {
                throw new IllegalStateException("Family not defined");
            } else {
                Block block = this.family.getVariants().get(BlockFamily.Variant.WALL_SIGN);
                ResourceLocation resourcelocation = ModelTemplates.PARTICLE_ONLY.create(signBlock, this.mapping, blockModels.modelOutput);
                blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(signBlock, plainVariant(resourcelocation)));
                blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, plainVariant(resourcelocation)));
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
                        .accept(BlockModelGenerators.createSlab(slabBlock, plainVariant(bottom), plainVariant(top), variant(this.fullBlock)));
                blockModels.registerSimpleItemModel(slabBlock, bottom);
                return this;
            }
        }

        public BlockModelGenerators.BlockFamilyProvider door(Block doorBlock) {
            createDoorWithRenderType(doorBlock, "cutout");
            return this;
        }

        public void trapdoor(Block trapdoorBlock) {
            if (NON_ORIENTABLE_TRAPDOOR.contains(trapdoorBlock)) {
                blockModels.createTrapdoor(trapdoorBlock);
            } else {
                createOrientableTrapdoorWithRenderType(trapdoorBlock, "cutout");
            }
        }

        public ResourceLocation getOrCreateModel(ModelTemplate modelTemplate, Block block) {
            return this.models.computeIfAbsent(modelTemplate, template -> template.create(block, this.mapping, blockModels.modelOutput));
        }

        public BlockModelGenerators.BlockFamilyProvider generateFor(BlockFamily family) {
            this.family = family;
            family.getVariants().forEach((variant, block) -> {
                if (!this.skipGeneratingModelsFor.contains(block)) {
                    BiConsumer<ModBlockFamilyProvider, Block> biconsumer = SHAPE_CONSUMERS.get(variant);
                    if (biconsumer != null) {
                        biconsumer.accept(this, block);
                    }
                }
            });
            return this;
        }
    }
}
