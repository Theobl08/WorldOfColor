package net.theobl.worldofcolor.datagen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.SingleVariant;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.special.CopperGolemStatueSpecialRenderer;
import net.minecraft.client.renderer.special.ShulkerBoxSpecialRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
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
import static net.theobl.worldofcolor.datagen.VanillaModelTemplates.DECORATED_POT;

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

    public static MultiVariant createLayeredCauldron(Identifier contentLevel, Identifier emptyCauldron) {
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

    public void generateDecoratedPotItemModel(Block block, SpecialModelRenderer.Unbaked specialModel, DyeColor color) {
        Item item = block.asItem();
        Identifier identifier = DECORATED_POT.create(ModelLocationUtils.getModelLocation(item),
                TextureMapping.particle(WorldOfColor.asResource("entity/decorated_pot/decorated_pot_side_" + color.getName())),
                blockModels.modelOutput);
        blockModels.itemModelOutput.accept(item, ItemModelUtils.specialModel(identifier, specialModel));
    }

    public void createCauldrons(DyeColor color) {
        int index = ModUtil.COLORS.indexOf(color);
        Block cauldron = ModBlocks.COLORED_CAULDRONS.get(index).get();
        Block lavaCauldron = ModBlocks.COLORED_LAVA_CAULDRONS.get(index).get();
        Block waterCauldron = ModBlocks.COLORED_WATER_CAULDRONS.get(index).get();
        Block powderSnowCauldron = ModBlocks.COLORED_POWDER_SNOW_CAULDRONS.get(index).get();
        Identifier emptyCauldron = CAULDRON.create(cauldron, cauldronEmpty(color), blockModels.modelOutput);
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
                                CAULDRON.extend().parent(Identifier.parse("block/lava_cauldron")).build()
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
                                                                        CAULDRON.extend().parent(Identifier.parse("block/powder_snow_cauldron_level1")).build()
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
                                                                        CAULDRON.extend().parent(Identifier.parse("block/powder_snow_cauldron_level2")).build()
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
                                                                        CAULDRON.extend().parent(Identifier.parse("block/powder_snow_cauldron_full")).build()
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
        Identifier doorBottomLeft = ModelTemplates.DOOR_BOTTOM_LEFT.extend().renderType(renderType).build().create(doorBlock, texturemapping, blockModels.modelOutput);
        Identifier doorBottomLeftOpen = ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.extend().renderType(renderType).build().create(doorBlock, texturemapping, blockModels.modelOutput);
        Identifier doorBottomRight = ModelTemplates.DOOR_BOTTOM_RIGHT.extend().renderType(renderType).build().create(doorBlock, texturemapping, blockModels.modelOutput);
        Identifier doorBottomRightOpen = ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.extend().renderType(renderType).build().create(doorBlock, texturemapping, blockModels.modelOutput);
        Identifier doorTopLeft = ModelTemplates.DOOR_TOP_LEFT.extend().renderType(renderType).build().create(doorBlock, texturemapping, blockModels.modelOutput);
        Identifier doorTopLeftOpen = ModelTemplates.DOOR_TOP_LEFT_OPEN.extend().renderType(renderType).build().create(doorBlock, texturemapping, blockModels.modelOutput);
        Identifier doorTopRight = ModelTemplates.DOOR_TOP_RIGHT.extend().renderType(renderType).build().create(doorBlock, texturemapping, blockModels.modelOutput);
        Identifier doorTopRightOpen = ModelTemplates.DOOR_TOP_RIGHT_OPEN.extend().renderType(renderType).build().create(doorBlock, texturemapping, blockModels.modelOutput);
        blockModels.registerSimpleFlatItemModel(doorBlock.asItem());
        blockModels.blockStateOutput
                .accept(
                        createDoor(
                                doorBlock,
                                plainVariant(doorBottomLeft),
                                plainVariant(doorBottomLeftOpen),
                                plainVariant(doorBottomRight),
                                plainVariant(doorBottomRightOpen),
                                plainVariant(doorTopLeft),
                                plainVariant(doorTopLeftOpen),
                                plainVariant(doorTopRight),
                                plainVariant(doorTopRightOpen)
                        )
                );
    }

    public void createOrientableTrapdoorWithRenderType(Block trapdoorBlock, String renderType) {
        TextureMapping texturemapping = TextureMapping.defaultTexture(trapdoorBlock);
        Identifier top = ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.extend().renderType(renderType).build().create(trapdoorBlock, texturemapping, blockModels.modelOutput);
        Identifier bottom = ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.extend().renderType(renderType).build().create(trapdoorBlock, texturemapping, blockModels.modelOutput);
        Identifier open = ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.extend().renderType(renderType).build().create(trapdoorBlock, texturemapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(createOrientableTrapdoor(trapdoorBlock, plainVariant(top), plainVariant(bottom), plainVariant(open)));
        blockModels.registerSimpleItemModel(trapdoorBlock, bottom);
    }

    public void createCrossBlock(Block block, BlockModelGenerators.PlantType plantType, String renderType) {
        TextureMapping texturemapping = plantType.getTextureMapping(block);
        Identifier identifier = plantType.getCross().extend().renderType(renderType).build().create(block, texturemapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(createSimpleBlock(block, plainVariant(identifier)));
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
                .parent(Identifier.parse("block/potted_" + parent))
                .renderType("cutout")
                .build().create(pottedBlock, textureMapping, blockModels.modelOutput));
        blockModels.blockStateOutput.accept(createSimpleBlock(pottedBlock, multivariant));
    }

    public ModBlockFamilyProvider family(Block block) {
        TexturedModel texturedmodel = TEXTURED_MODELS.getOrDefault(block, TexturedModel.CUBE.get(block));
        return new ModBlockFamilyProvider(texturedmodel.getMapping(), blockModels).fullBlock(block, texturedmodel.getTemplate());
    }

    public void createBarsAndItem(Block weatheringBlock, Block waxedBlock) {
        String renderType = ChunkSectionLayer.CUTOUT.label();
        TextureMapping texturemapping = TextureMapping.bars(weatheringBlock);
        Identifier postEnd = ModelTemplates.BARS_POST_ENDS.extend().renderType(renderType).build().create(weatheringBlock, texturemapping, blockModels.modelOutput);
        Identifier post = ModelTemplates.BARS_POST.extend().renderType(renderType).build().create(weatheringBlock, texturemapping, blockModels.modelOutput);
        Identifier cap = ModelTemplates.BARS_CAP.extend().renderType(renderType).build().create(weatheringBlock, texturemapping, blockModels.modelOutput);
        Identifier capAlt = ModelTemplates.BARS_CAP_ALT.extend().renderType(renderType).build().create(weatheringBlock, texturemapping, blockModels.modelOutput);
        Identifier postSide = ModelTemplates.BARS_POST_SIDE.extend().renderType(renderType).build().create(weatheringBlock, texturemapping, blockModels.modelOutput);
        Identifier postSideAlt = ModelTemplates.BARS_POST_SIDE_ALT.extend().renderType(renderType).build().create(weatheringBlock, texturemapping, blockModels.modelOutput);
        blockModels.createBars(weatheringBlock, postEnd, post, cap, capAlt, postSide, postSideAlt);
        blockModels.createBars(waxedBlock, postEnd, post, cap, capAlt, postSide, postSideAlt);
        blockModels.registerSimpleFlatItemModel(weatheringBlock);
        blockModels.itemModelOutput.copy(weatheringBlock.asItem(), waxedBlock.asItem());
    }

    public void createRedstoneLamp(Block block) {
        MultiVariant off = plainVariant(TexturedModel.CUBE.create(block, blockModels.modelOutput));
        MultiVariant on = plainVariant(blockModels.createSuffixedVariant(block, "_on", ModelTemplates.CUBE_ALL, TextureMapping::cube));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(createBooleanModelDispatch(BlockStateProperties.LIT, on, off)));
    }

    public void createCopperChain(Block block, Block waxed) {
        MultiVariant model = plainVariant(TexturedModel.CHAIN.updateTemplate(template -> template.extend().renderType("cutout_mipped").build()).create(block, blockModels.modelOutput));
        blockModels.createAxisAlignedPillarBlockCustomModel(block, model);
        blockModels.createAxisAlignedPillarBlockCustomModel(waxed, model);
        Identifier identifier = blockModels.createFlatItemModel(block.asItem());
        blockModels.registerSimpleItemModel(block.asItem(), identifier);
        blockModels.registerSimpleItemModel(waxed.asItem(), identifier);
    }

    public void createCopperLantern(Block block, Block waxed) {
        String renderType = "cutout";
        Identifier ground = TexturedModel.LANTERN.updateTemplate(template -> template.extend().renderType(renderType).build()).create(block, blockModels.modelOutput);
        Identifier hanging = TexturedModel.HANGING_LANTERN.updateTemplate(template -> template.extend().renderType(renderType).build()).create(block, blockModels.modelOutput);
        blockModels.registerSimpleFlatItemModel(block.asItem());
        blockModels.itemModelOutput.copy(block.asItem(), waxed.asItem());
        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.dispatch(block)
                                .with(createBooleanModelDispatch(BlockStateProperties.HANGING, plainVariant(hanging), plainVariant(ground)))
                );
        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.dispatch(waxed)
                                .with(createBooleanModelDispatch(BlockStateProperties.HANGING, plainVariant(hanging), plainVariant(ground)))
                );
    }

    public void createCopperGolemStatue(Block statueBlock, Block copperBlock, DyeColor color) {
        MultiVariant blockModel = plainVariant(
                ModelTemplates.PARTICLE_ONLY.create(statueBlock, TextureMapping.particle(TextureMapping.getBlockTexture(copperBlock)), blockModels.modelOutput)
        );
        Identifier itemBase = ModelLocationUtils.decorateItemModelLocation("template_copper_golem_statue");
        Identifier texture = WorldOfColor.asResource("textures/entity/copper_golem/" + color.getName() + "_copper_golem.png");
        blockModels.blockStateOutput.accept(createSimpleBlock(statueBlock, blockModel));
        blockModels.itemModelOutput
                .accept(
                        statueBlock.asItem(),
                        ItemModelUtils.selectBlockItemProperty(
                                CopperGolemStatueBlock.POSE,
                                ItemModelUtils.specialModel(itemBase, new CopperGolemStatueSpecialRenderer.Unbaked(texture, CopperGolemStatueBlock.Pose.STANDING)),
                                Map.of(
                                        CopperGolemStatueBlock.Pose.SITTING,
                                        ItemModelUtils.specialModel(
                                                itemBase, new CopperGolemStatueSpecialRenderer.Unbaked(texture, CopperGolemStatueBlock.Pose.SITTING)
                                        ),
                                        CopperGolemStatueBlock.Pose.STAR,
                                        ItemModelUtils.specialModel(itemBase, new CopperGolemStatueSpecialRenderer.Unbaked(texture, CopperGolemStatueBlock.Pose.STAR)),
                                        CopperGolemStatueBlock.Pose.RUNNING,
                                        ItemModelUtils.specialModel(
                                                itemBase, new CopperGolemStatueSpecialRenderer.Unbaked(texture, CopperGolemStatueBlock.Pose.RUNNING)
                                        )
                                )
                        )
                );
    }

    public void createGlassBlocks(Block glassBlock, Block paneBlock) {
        String translucent = ChunkSectionLayer.TRANSLUCENT.label();
        this.createTrivialCubeWithRenderType(glassBlock, translucent);
        TextureMapping texturemapping = TextureMapping.pane(glassBlock, paneBlock);
        MultiVariant post = plainVariant(ModelTemplates.STAINED_GLASS_PANE_POST.extend().renderType(translucent).build().create(paneBlock, texturemapping, blockModels.modelOutput));
        MultiVariant side = plainVariant(ModelTemplates.STAINED_GLASS_PANE_SIDE.extend().renderType(translucent).build().create(paneBlock, texturemapping, blockModels.modelOutput));
        MultiVariant sideAlt = plainVariant(ModelTemplates.STAINED_GLASS_PANE_SIDE_ALT.extend().renderType(translucent).build().create(paneBlock, texturemapping, blockModels.modelOutput));
        MultiVariant noSide = plainVariant(ModelTemplates.STAINED_GLASS_PANE_NOSIDE.extend().renderType(translucent).build().create(paneBlock, texturemapping, blockModels.modelOutput));
        MultiVariant noSideAlt = plainVariant(ModelTemplates.STAINED_GLASS_PANE_NOSIDE_ALT.extend().renderType(translucent).build().create(paneBlock, texturemapping, blockModels.modelOutput));
        Item paneItem = paneBlock.asItem();
        blockModels.registerSimpleItemModel(paneItem,
                ModelTemplates.FLAT_ITEM
                        .extend()
                        .renderType(translucent)
                        .build()
                        .create(ModelLocationUtils.getModelLocation(paneItem), TextureMapping.layer0(glassBlock), blockModels.modelOutput));
        blockModels.blockStateOutput
                .accept(
                        MultiPartGenerator.multiPart(paneBlock)
                                .with(post)
                                .with(condition().term(BlockStateProperties.NORTH, true), side)
                                .with(condition().term(BlockStateProperties.EAST, true), side.with(Y_ROT_90))
                                .with(condition().term(BlockStateProperties.SOUTH, true), sideAlt)
                                .with(condition().term(BlockStateProperties.WEST, true), sideAlt.with(Y_ROT_90))
                                .with(condition().term(BlockStateProperties.NORTH, false), noSide)
                                .with(condition().term(BlockStateProperties.EAST, false), noSideAlt)
                                .with(condition().term(BlockStateProperties.SOUTH, false), noSideAlt.with(Y_ROT_90))
                                .with(condition().term(BlockStateProperties.WEST, false), noSide.with(Y_ROT_270))
                );
    }
    public void createShulkerBox(Block block) {
        blockModels.createParticleOnlyBlock(block);
        Item item = block.asItem();
//        Identifier baseModel = ModelTemplates.SHULKER_BOX_INVENTORY.create(item, TextureMapping.particle(block), blockModels.modelOutput);
        Identifier baseModel = VanillaModelTemplates.SHULKER_BOX_ITEM.create(item,
                new TextureMapping().put(TextureSlot.TEXTURE, WorldOfColor.asResource("entity/shulker/shulker_rgb")), blockModels.modelOutput);
//        ItemModel.Unbaked itemModel = ItemModelUtils.specialModel(baseModel, new ShulkerBoxSpecialRenderer.Unbaked(WorldOfColor.asResource("shulker_rgb"), 0.0F, Direction.UP));
        ItemModel.Unbaked itemModel = ItemModelUtils.plainModel(baseModel);
        blockModels.itemModelOutput.accept(item, itemModel);
    }

    public void createBed(Block block) {
        MultiVariant multivariant = plainVariant(Identifier.parse("block/bed"));
        blockModels.blockStateOutput.accept(createSimpleBlock(block, multivariant));
        Item item = block.asItem();
        Identifier baseModel = VanillaModelTemplates.BED_ITEM.create(item,
                new TextureMapping().put(TextureSlot.TEXTURE, WorldOfColor.asResource("entity/bed/rgb")), blockModels.modelOutput);
        ItemModel.Unbaked itemModel = ItemModelUtils.plainModel(baseModel);
        blockModels.itemModelOutput.accept(item, itemModel);
    }

    @ParametersAreNonnullByDefault
    public class ModBlockFamilyProvider extends BlockModelGenerators.BlockFamilyProvider {
        private final TextureMapping mapping;
        private final Map<ModelTemplate, Identifier> models = Maps.newHashMap();
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
                Identifier identifier = ModelTemplates.PARTICLE_ONLY.create(signBlock, this.mapping, blockModels.modelOutput);
                blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(signBlock, plainVariant(identifier)));
                blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, plainVariant(identifier)));
                blockModels.registerSimpleFlatItemModel(signBlock.asItem());
                return this;
            }
        }

        public BlockModelGenerators.BlockFamilyProvider slab(Block slabBlock) {
            if (this.fullBlock == null) {
                throw new IllegalStateException("Full block not generated yet");
            } else {
                Identifier bottom = this.getOrCreateModel(ModelTemplates.SLAB_BOTTOM, slabBlock);
                Identifier top = this.getOrCreateModel(ModelTemplates.SLAB_TOP, slabBlock);
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

        public Identifier getOrCreateModel(ModelTemplate modelTemplate, Block block) {
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
