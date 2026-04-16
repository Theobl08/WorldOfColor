package net.theobl.worldofcolor.datagen;

import com.mojang.math.Transformation;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.special.CopperGolemStatueSpecialRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.client.renderer.special.ColoredBannerSpecialRenderer;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.util.ModUtil;
import org.joml.Vector3f;

import java.util.Map;

import static net.minecraft.client.data.models.BlockModelGenerators.*;
import static net.minecraft.client.data.models.model.TextureMapping.getBlockTexture;
import static net.theobl.worldofcolor.datagen.ColoredTextureMapping.cauldronEmpty;
import static net.theobl.worldofcolor.datagen.ColoredModelTemplates.CAULDRON;
import static net.theobl.worldofcolor.datagen.ColoredModelTemplates.DECORATED_POT;

public class ColoredBlockModelGenerators {
    private final BlockModelGenerators blockModels;

    public ColoredBlockModelGenerators(BlockModelGenerators blockModels) {
        this.blockModels = blockModels;
    }

    public void copyLayeredCauldronModel(Block sourceBlock, Block targetBlock) {
        Identifier level1 = ModelLocationUtils.getModelLocation(sourceBlock, "_level1");
        Identifier level2 = ModelLocationUtils.getModelLocation(sourceBlock, "_level2");
        Identifier full = ModelLocationUtils.getModelLocation(sourceBlock, "_full");
        blockModels.blockStateOutput
                .accept(
                        MultiVariantGenerator.dispatch(targetBlock)
                                .with(
                                        PropertyDispatch.initial(LayeredCauldronBlock.LEVEL)
                                                .select(1, plainVariant(level1))
                                                .select(2, plainVariant(level2))
                                                .select(3, plainVariant(full))
                                )
                );
    }

    public void copyBlockModel(Block sourceBlock, Block targetBlock) {
        MultiVariant multivariant = plainVariant(ModelLocationUtils.getModelLocation(sourceBlock));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(targetBlock, multivariant));
    }

    public void createTrivialBlock(Block block, TextureMapping mapping, ModelTemplate template) {
        blockModels.blockStateOutput.accept(createSimpleBlock(block, plainVariant(template.create(block, mapping, blockModels.modelOutput))));
    }

    public void generateDecoratedPotItemModel(Block block, SpecialModelRenderer.Unbaked<?> specialModel, DyeColor color) {
        Item item = block.asItem();
        Identifier identifier = DECORATED_POT.create(ModelLocationUtils.getModelLocation(item),
                TextureMapping.particle(new Material(WorldOfColor.asResource("entity/decorated_pot/decorated_pot_side_" + color.getName()))),
                blockModels.modelOutput);
        blockModels.itemModelOutput.accept(item, ItemModelUtils.specialModel(identifier, specialModel));
    }

    public void createCauldrons(DyeColor color) {
        int index = ModUtil.COLORS.indexOf(color);
        Block cauldron = ModBlocks.COLORED_CAULDRONS.get(color).get();
        Block lavaCauldron = ModBlocks.COLORED_LAVA_CAULDRONS.get(color).get();
        Block waterCauldron = ModBlocks.COLORED_WATER_CAULDRONS.get(color).get();
        Block powderSnowCauldron = ModBlocks.COLORED_POWDER_SNOW_CAULDRONS.get(color).get();
        Identifier emptyCauldron = CAULDRON.create(cauldron, cauldronEmpty(color), blockModels.modelOutput);
        Material water = TextureMapping.getBlockTexture(Blocks.WATER, "_still");

        blockModels.registerSimpleFlatItemModel(ModItems.COLORED_CAULDRONS.get(color).get());
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
                                                        plainVariant(
                                                                        ModelTemplates.CAULDRON_LEVEL1
                                                                                .createWithSuffix(
                                                                                        waterCauldron,
                                                                                        "_level1",
                                                                                        ColoredTextureMapping.cauldron(water, color),
                                                                                        blockModels.modelOutput
                                                                                )
                                                                )
                                                )
                                                .select(
                                                        2,
                                                        plainVariant(
                                                                        ModelTemplates.CAULDRON_LEVEL2
                                                                                .createWithSuffix(
                                                                                        waterCauldron,
                                                                                        "_level2",
                                                                                        ColoredTextureMapping.cauldron(water, color),
                                                                                        blockModels.modelOutput
                                                                                )
                                                                )
                                                )
                                                .select(
                                                        3,
                                                        plainVariant(
                                                                        ModelTemplates.CAULDRON_FULL
                                                                                .createWithSuffix(
                                                                                        waterCauldron,
                                                                                        "_full",
                                                                                        ColoredTextureMapping.cauldron(water, color),
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

    public void createPottedPlant(Block block, Block pottedBlock, Block emptyPot, BlockModelGenerators.PlantType plantType) {
        TextureMapping texturemapping = plantType.getPlantTextureMapping(block);
        texturemapping.put(ColoredTextureSlot.FLOWERPOT, getBlockTexture(emptyPot)).put(TextureSlot.PARTICLE, getBlockTexture(emptyPot));
        MultiVariant multivariant = plainVariant(plantType.getCrossPot().extend()
                .requiredTextureSlot(ColoredTextureSlot.FLOWERPOT)
                .requiredTextureSlot(TextureSlot.PARTICLE)
                .build().create(pottedBlock, texturemapping, blockModels.modelOutput));
        blockModels.blockStateOutput.accept(createSimpleBlock(pottedBlock, multivariant));
    }

    public void createPottedPlant(Block pottedBlock, Block emptyPot, String parent) {
        TextureMapping textureMapping = ColoredTextureMapping.flowerPot(emptyPot);
        MultiVariant multivariant = plainVariant(ColoredModelTemplates.FLOWER_POT.extend()
                .parent(Identifier.parse("block/potted_" + parent))
                .build().create(pottedBlock, textureMapping, blockModels.modelOutput));
        blockModels.blockStateOutput.accept(createSimpleBlock(pottedBlock, multivariant));
    }

    public void createBanner(Block block, Block wallBlock) {
        MultiVariant multivariant = plainVariant(Identifier.parse("banner").withPrefix("block/"));
        Identifier identifier = ModelLocationUtils.decorateItemModelLocation("template_banner");
        blockModels.blockStateOutput.accept(createSimpleBlock(block, multivariant));
        blockModels.blockStateOutput.accept(createSimpleBlock(wallBlock, multivariant));
        Item item = block.asItem();
        blockModels.itemModelOutput.accept(
                item,
                ItemModelUtils.specialModel(
                        identifier,
                        BannerRenderer.TRANSFORMATIONS.freeTransformations(0),
                        new ColoredBannerSpecialRenderer.Unbaked(BannerBlock.AttachmentType.GROUND)));
    }

    public void createRedstoneLamp(Block block) {
        MultiVariant off = plainVariant(TexturedModel.CUBE.create(block, blockModels.modelOutput));
        MultiVariant on = plainVariant(blockModels.createSuffixedVariant(block, "_on", ModelTemplates.CUBE_ALL, TextureMapping::cube));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(createBooleanModelDispatch(BlockStateProperties.LIT, on, off)));
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
                                new Transformation(new Vector3f(0.5F, 1.5F, 0.5F), null, new Vector3f(1.0F, -1.0F, -1.0F), null),
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
        blockModels.createTrivialBlock(glassBlock, TexturedModel.CUBE.updateTexture(TextureMapping::forceAllTranslucent));
        TextureMapping texturemapping = TextureMapping.pane(glassBlock, paneBlock);
        MultiVariant post = plainVariant(ModelTemplates.STAINED_GLASS_PANE_POST.create(paneBlock, texturemapping, blockModels.modelOutput));
        MultiVariant side = plainVariant(ModelTemplates.STAINED_GLASS_PANE_SIDE.create(paneBlock, texturemapping, blockModels.modelOutput));
        MultiVariant sideAlt = plainVariant(ModelTemplates.STAINED_GLASS_PANE_SIDE_ALT.create(paneBlock, texturemapping, blockModels.modelOutput));
        MultiVariant noSide = plainVariant(ModelTemplates.STAINED_GLASS_PANE_NOSIDE.create(paneBlock, texturemapping, blockModels.modelOutput));
        MultiVariant noSideAlt = plainVariant(ModelTemplates.STAINED_GLASS_PANE_NOSIDE_ALT.create(paneBlock, texturemapping, blockModels.modelOutput));
        Item paneItem = paneBlock.asItem();
        blockModels.registerSimpleItemModel(paneItem,
                ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(paneItem), TextureMapping.layer0(glassBlock), blockModels.modelOutput));
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
        Identifier baseModel = ColoredModelTemplates.SHULKER_BOX_ITEM.create(item,
                new TextureMapping().put(TextureSlot.TEXTURE, new Material(WorldOfColor.asResource("entity/shulker/shulker_rgb"))), blockModels.modelOutput);
//        ItemModel.Unbaked itemModel = ItemModelUtils.specialModel(baseModel, new ShulkerBoxSpecialRenderer.Unbaked(WorldOfColor.asResource("shulker_rgb"), 0.0F, Direction.UP));
        ItemModel.Unbaked itemModel = ItemModelUtils.plainModel(baseModel);
        blockModels.itemModelOutput.accept(item, itemModel);
    }

    public void createBed(Block block) {
        MultiVariant multivariant = plainVariant(Identifier.parse("block/bed"));
        blockModels.blockStateOutput.accept(createSimpleBlock(block, multivariant));
        Item item = block.asItem();
        Identifier baseModel = ColoredModelTemplates.BED_ITEM.create(item,
                new TextureMapping().put(TextureSlot.TEXTURE, new Material(WorldOfColor.asResource("entity/bed/rgb"))), blockModels.modelOutput);
        ItemModel.Unbaked itemModel = ItemModelUtils.plainModel(baseModel);
        blockModels.itemModelOutput.accept(item, itemModel);
    }
}
