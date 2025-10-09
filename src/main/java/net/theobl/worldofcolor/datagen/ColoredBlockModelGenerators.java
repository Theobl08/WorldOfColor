package net.theobl.worldofcolor.datagen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.SingleVariant;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.special.CopperGolemStatueSpecialRenderer;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.blockstate.CompositeBlockStateModelBuilder;
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
import static net.minecraft.client.data.models.model.TextureMapping.getBlockTexture;
import static net.theobl.worldofcolor.datagen.ColoredTextureMapping.cauldronEmpty;
import static net.theobl.worldofcolor.datagen.VanillaModelTemplates.CAULDRON;

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

    public static MultiVariant createLayeredCauldron(ResourceLocation contentLevel, ResourceLocation emptyCauldron) {
        CompositeBlockStateModelBuilder compositeBlockStateModelBuilder = new CompositeBlockStateModelBuilder();
        compositeBlockStateModelBuilder.addPartModel(new SingleVariant.Unbaked(plainModel(contentLevel)));
        compositeBlockStateModelBuilder.addPartModel(new SingleVariant.Unbaked(plainModel(emptyCauldron)));
        return MultiVariant.of(compositeBlockStateModelBuilder);
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
        ResourceLocation emptyCauldron = CAULDRON.create(cauldron, cauldronEmpty(color), blockModels.modelOutput);
        blockModels.registerSimpleFlatItemModel(ModItems.COLORED_CAULDRONS.get(index).get());
        blockModels.blockStateOutput
                .accept(
                        createSimpleBlock(
                                cauldron,
                                plainVariant(
                                        emptyCauldron
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
                                                        createLayeredCauldron(
                                                                        VanillaModelTemplates.cauldronLevelX(1)
                                                                                .createWithSuffix(
                                                                                        waterCauldron,
                                                                                        "_level1",
                                                                                        ColoredTextureMapping.cauldronContent(cauldron),
                                                                                        blockModels.modelOutput
                                                                                ), emptyCauldron
                                                                )
                                                )
                                                .select(
                                                        2,
                                                        createLayeredCauldron(
                                                                        VanillaModelTemplates.cauldronLevelX(2)
                                                                                .createWithSuffix(
                                                                                        waterCauldron,
                                                                                        "_level2",
                                                                                        ColoredTextureMapping.cauldronContent(cauldron),
                                                                                        blockModels.modelOutput
                                                                                ), emptyCauldron
                                                                )
                                                )
                                                .select(
                                                        3,
                                                        createLayeredCauldron(
                                                                        VanillaModelTemplates.cauldronLevelX(3)
                                                                                .createWithSuffix(
                                                                                        waterCauldron,
                                                                                        "_full",
                                                                                        ColoredTextureMapping.cauldronContent(cauldron),
                                                                                        blockModels.modelOutput
                                                                                ), emptyCauldron
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

    public void createCrossBlock(Block block, BlockModelGenerators.PlantType plantType, String renderType) {
        TextureMapping texturemapping = plantType.getTextureMapping(block);
        ResourceLocation resourcelocation = plantType.getCross().extend().renderType(renderType).build().create(block, texturemapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(createSimpleBlock(block, plainVariant(resourcelocation)));
    }

    public void createPlantWithDefaultItem(Block block, Block pottedBlock, BlockModelGenerators.PlantType plantType) {
        blockModels.registerSimpleItemModel(block.asItem(), plantType.createItemModel(blockModels, block));
        this.createCrossBlock(block, plantType, "cutout");
        TextureMapping texturemapping = plantType.getPlantTextureMapping(block);
        MultiVariant multivariant = plainVariant(plantType.getCrossPot().extend().renderType("cutout").build().create(pottedBlock, texturemapping, blockModels.modelOutput));
        blockModels.blockStateOutput.accept(createSimpleBlock(pottedBlock, multivariant));
    }

    public void createPottedPlant(Block block, Block pottedBlock, Block emptyPot, BlockModelGenerators.PlantType plantType) {
        TextureMapping texturemapping = plantType.getPlantTextureMapping(block);
        texturemapping.put(ColoredTextureSlot.FLOWERPOT, getBlockTexture(emptyPot)).put(TextureSlot.PARTICLE, getBlockTexture(emptyPot));
        MultiVariant multivariant = plainVariant(plantType.getCrossPot().extend()
                .requiredTextureSlot(ColoredTextureSlot.FLOWERPOT)
                .requiredTextureSlot(TextureSlot.PARTICLE)
                .renderType("cutout")
                .build().create(pottedBlock, texturemapping, blockModels.modelOutput));
        blockModels.blockStateOutput.accept(createSimpleBlock(pottedBlock, multivariant));
    }

    public void createPottedPlant(Block pottedBlock, Block emptyPot, String parent) {
        TextureMapping textureMapping = ColoredTextureMapping.flowerPot(emptyPot);
        MultiVariant multivariant = plainVariant(VanillaModelTemplates.FLOWER_POT.extend()
                .parent(ResourceLocation.parse("block/potted_" + parent))
                .renderType("cutout")
                .build().create(pottedBlock, textureMapping, blockModels.modelOutput));
        blockModels.blockStateOutput.accept(createSimpleBlock(pottedBlock, multivariant));
    }

    public ModBlockFamilyProvider family(Block block) {
        TexturedModel texturedmodel = TEXTURED_MODELS.getOrDefault(block, TexturedModel.CUBE.get(block));
        return new ModBlockFamilyProvider(texturedmodel.getMapping(), blockModels).fullBlock(block, texturedmodel.getTemplate());
    }

    public void createBarsAndItem(Block weatheringBlock, Block waxedBlock) {
        String renderType = ChunkSectionLayer.CUTOUT_MIPPED.label();
        TextureMapping texturemapping = TextureMapping.bars(weatheringBlock);
        ResourceLocation postEnd = ModelTemplates.BARS_POST_ENDS.extend().renderType(renderType).build().create(weatheringBlock, texturemapping, blockModels.modelOutput);
        ResourceLocation post = ModelTemplates.BARS_POST.extend().renderType(renderType).build().create(weatheringBlock, texturemapping, blockModels.modelOutput);
        ResourceLocation cap = ModelTemplates.BARS_CAP.extend().renderType(renderType).build().create(weatheringBlock, texturemapping, blockModels.modelOutput);
        ResourceLocation capAlt = ModelTemplates.BARS_CAP_ALT.extend().renderType(renderType).build().create(weatheringBlock, texturemapping, blockModels.modelOutput);
        ResourceLocation postSide = ModelTemplates.BARS_POST_SIDE.extend().renderType(renderType).build().create(weatheringBlock, texturemapping, blockModels.modelOutput);
        ResourceLocation postSideAlt = ModelTemplates.BARS_POST_SIDE_ALT.extend().renderType(renderType).build().create(weatheringBlock, texturemapping, blockModels.modelOutput);
        blockModels.createBars(weatheringBlock, postEnd, post, cap, capAlt, postSide, postSideAlt);
        blockModels.createBars(waxedBlock, postEnd, post, cap, capAlt, postSide, postSideAlt);
        blockModels.registerSimpleFlatItemModel(weatheringBlock);
        blockModels.itemModelOutput.copy(weatheringBlock.asItem(), waxedBlock.asItem());
    }

    public void createCopperChain(Block block, Block waxed) {
        MultiVariant multivariant = plainVariant(TexturedModel.CHAIN.updateTemplate(template -> template.extend().renderType("cutout_mipped").build()).create(block, blockModels.modelOutput));
        blockModels.createAxisAlignedPillarBlockCustomModel(block, multivariant);
        blockModels.createAxisAlignedPillarBlockCustomModel(waxed, multivariant);
        ResourceLocation resourcelocation = blockModels.createFlatItemModel(block.asItem());
        blockModels.registerSimpleItemModel(block.asItem(), resourcelocation);
        blockModels.registerSimpleItemModel(waxed.asItem(), resourcelocation);
    }

    public void createCopperLantern(Block block, Block waxed) {
        String renderType = "cutout";
        ResourceLocation lantern = TexturedModel.LANTERN.updateTemplate(template -> template.extend().renderType(renderType).build()).create(block, blockModels.modelOutput);
        ResourceLocation hangingLantern = TexturedModel.HANGING_LANTERN.updateTemplate(template -> template.extend().renderType(renderType).build()).create(block, blockModels.modelOutput);
        blockModels.registerSimpleFlatItemModel(block.asItem());
        blockModels.itemModelOutput.copy(block.asItem(), waxed.asItem());
        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.dispatch(block)
                                .with(createBooleanModelDispatch(BlockStateProperties.HANGING, plainVariant(hangingLantern), plainVariant(lantern)))
                );
        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.dispatch(waxed)
                                .with(createBooleanModelDispatch(BlockStateProperties.HANGING, plainVariant(hangingLantern), plainVariant(lantern)))
                );
    }

    public void createCopperGolemStatue(Block statueBlock, Block copperBlock, DyeColor color) {
        MultiVariant multivariant = plainVariant(
                ModelTemplates.PARTICLE_ONLY.create(statueBlock, TextureMapping.particle(TextureMapping.getBlockTexture(copperBlock)), blockModels.modelOutput)
        );
        ResourceLocation resourcelocation = ModelLocationUtils.decorateItemModelLocation("template_copper_golem_statue");
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(WorldOfColor.MODID, "textures/entity/copper_golem/" + color.getName() + "_copper_golem.png");
        blockModels.blockStateOutput.accept(createSimpleBlock(statueBlock, multivariant));
        blockModels.itemModelOutput
                .accept(
                        statueBlock.asItem(),
                        ItemModelUtils.selectBlockItemProperty(
                                CopperGolemStatueBlock.POSE,
                                ItemModelUtils.specialModel(resourcelocation, new CopperGolemStatueSpecialRenderer.Unbaked(texture, CopperGolemStatueBlock.Pose.STANDING)),
                                Map.of(
                                        CopperGolemStatueBlock.Pose.SITTING,
                                        ItemModelUtils.specialModel(
                                                resourcelocation, new CopperGolemStatueSpecialRenderer.Unbaked(texture, CopperGolemStatueBlock.Pose.SITTING)
                                        ),
                                        CopperGolemStatueBlock.Pose.STAR,
                                        ItemModelUtils.specialModel(resourcelocation, new CopperGolemStatueSpecialRenderer.Unbaked(texture, CopperGolemStatueBlock.Pose.STAR)),
                                        CopperGolemStatueBlock.Pose.RUNNING,
                                        ItemModelUtils.specialModel(
                                                resourcelocation, new CopperGolemStatueSpecialRenderer.Unbaked(texture, CopperGolemStatueBlock.Pose.RUNNING)
                                        )
                                )
                        )
                );
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
