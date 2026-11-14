package net.theobl.worldofcolor.datagen;

import com.mojang.math.Transformation;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.ShulkerBoxRenderer;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.special.CopperGolemStatueSpecialRenderer;
import net.minecraft.client.renderer.special.ShulkerBoxSpecialRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.client.renderer.special.ColoredBannerSpecialRenderer;
import net.theobl.worldofcolor.item.ModItems;
import org.joml.Vector3f;

import java.util.Map;

import static net.minecraft.client.data.models.model.TextureMapping.getBlockTexture;

public class ColoredBlockModelGenerators extends BlockModelGenerators {

    public ColoredBlockModelGenerators(BlockModelGenerators blockModels) {
        super(blockModels.blockStateOutput, blockModels.itemModelOutput, blockModels.modelOutput);
    }

    public void copyLayeredCauldronModel(Block sourceBlock, Block targetBlock) {
        Identifier level1 = ModelLocationUtils.getModelLocation(sourceBlock, "_level1");
        Identifier level2 = ModelLocationUtils.getModelLocation(sourceBlock, "_level2");
        Identifier full = ModelLocationUtils.getModelLocation(sourceBlock, "_full");
        this.blockStateOutput
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
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(targetBlock, multivariant));
    }

    public void createTrivialCube(Block block, Block appearance) {
        this.createTrivialBlock(block, TextureMapping.cube(appearance), ModelTemplates.CUBE_ALL);
    }

    public void createTrivialBlock(Block block, TextureMapping mapping, ModelTemplate template) {
        this.blockStateOutput.accept(createSimpleBlock(block, plainVariant(template.create(block, mapping, this.modelOutput))));
    }

    public void generateDecoratedPotItemModel(Block block, SpecialModelRenderer.Unbaked<?> specialModel, DyeColor color) {
        Item item = block.asItem();
        Identifier identifier = ColoredModelTemplates.DECORATED_POT.create(ModelLocationUtils.getModelLocation(item),
                TextureMapping.particle(getBlockTexture(Blocks.DYED_TERRACOTTA.pick(color))),
                this.modelOutput);
        this.itemModelOutput.accept(item, ItemModelUtils.specialModel(identifier, specialModel));
    }

    public void createCauldrons(DyeColor color) {
        Block cauldron = ModBlocks.COLORED_CAULDRONS.pick(color).get();
        Block lavaCauldron = ModBlocks.COLORED_LAVA_CAULDRONS.pick(color).get();
        Block waterCauldron = ModBlocks.COLORED_WATER_CAULDRONS.pick(color).get();
        Block powderSnowCauldron = ModBlocks.COLORED_POWDER_SNOW_CAULDRONS.pick(color).get();
        Identifier emptyCauldron = ColoredModelTemplates.CAULDRON.create(cauldron, ColoredTextureMapping.cauldronEmpty(color), this.modelOutput);
        Material water = TextureMapping.getBlockTexture(Blocks.WATER, "_still");

        this.registerSimpleFlatItemModel(ModItems.COLORED_CAULDRONS.pick(color).get());
        this.blockStateOutput.accept(createSimpleBlock(cauldron, plainVariant(emptyCauldron)));
        this.blockStateOutput
                .accept(
                        createSimpleBlock(
                                lavaCauldron,
                                plainVariant(
                                        ColoredModelTemplates.CAULDRON.extend().parent(Identifier.parse("block/lava_cauldron")).build()
                                                .create(lavaCauldron, ColoredTextureMapping.cauldron(getBlockTexture(Blocks.LAVA, "_still"), color), this.modelOutput)
                                )
                        )
                );
        this.blockStateOutput
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
                                                                                this.modelOutput
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
                                                                                this.modelOutput
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
                                                                                this.modelOutput
                                                                        )
                                                        )
                                                )
                                )
                );
        this.blockStateOutput
                .accept(
                        MultiVariantGenerator.dispatch(powderSnowCauldron)
                                .with(
                                        PropertyDispatch.initial(LayeredCauldronBlock.LEVEL)
                                                .select(
                                                        1,
                                                        plainVariant(
                                                                ColoredModelTemplates.CAULDRON.extend().parent(Identifier.parse("block/powder_snow_cauldron_level1")).build()
                                                                        .createWithSuffix(
                                                                                powderSnowCauldron,
                                                                                "_level1",
                                                                                ColoredTextureMapping.cauldron(getBlockTexture(Blocks.POWDER_SNOW), color),
                                                                                this.modelOutput
                                                                        )
                                                        )
                                                )
                                                .select(
                                                        2,
                                                        plainVariant(
                                                                ColoredModelTemplates.CAULDRON.extend().parent(Identifier.parse("block/powder_snow_cauldron_level2")).build()
                                                                                .createWithSuffix(
                                                                                        powderSnowCauldron,
                                                                                        "_level2",
                                                                                        ColoredTextureMapping.cauldron(getBlockTexture(Blocks.POWDER_SNOW), color),
                                                                                        this.modelOutput
                                                                                )
                                                                )
                                                )
                                                .select(
                                                        3,
                                                        plainVariant(
                                                                ColoredModelTemplates.CAULDRON.extend().parent(Identifier.parse("block/powder_snow_cauldron_full")).build()
                                                                        .createWithSuffix(
                                                                                powderSnowCauldron,
                                                                                "_full",
                                                                                ColoredTextureMapping.cauldron(getBlockTexture(Blocks.POWDER_SNOW), color),
                                                                                this.modelOutput
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
                .build().create(pottedBlock, texturemapping, this.modelOutput));
        this.blockStateOutput.accept(createSimpleBlock(pottedBlock, multivariant));
    }

    public void createPottedPlant(Block pottedBlock, Block emptyPot, String parent) {
        TextureMapping textureMapping = ColoredTextureMapping.flowerPot(emptyPot);
        MultiVariant multivariant = plainVariant(ColoredModelTemplates.FLOWER_POT.extend()
                .parent(Identifier.parse("block/potted_" + parent))
                .build().create(pottedBlock, textureMapping, this.modelOutput));
        this.blockStateOutput.accept(createSimpleBlock(pottedBlock, multivariant));
    }

    public void createBanner(Block block, Block wallBlock) {
        MultiVariant multivariant = plainVariant(Identifier.parse("banner").withPrefix("block/"));
        Identifier identifier = ModelLocationUtils.decorateItemModelLocation("template_banner");
        this.blockStateOutput.accept(createSimpleBlock(block, multivariant));
        this.blockStateOutput.accept(createSimpleBlock(wallBlock, multivariant));
        Item item = block.asItem();
        this.itemModelOutput.accept(
                item,
                ItemModelUtils.specialModel(
                        identifier,
                        BannerRenderer.TRANSFORMATIONS.freeTransformations(0),
                        new ColoredBannerSpecialRenderer.Unbaked(BannerBlock.AttachmentType.GROUND)));
    }

    public void createRedstoneLamp(Block block) {
        MultiVariant off = plainVariant(TexturedModel.CUBE.create(block, this.modelOutput));
        MultiVariant on = plainVariant(this.createSuffixedVariant(block, "_on", ModelTemplates.CUBE_ALL, TextureMapping::cube));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(createBooleanModelDispatch(BlockStateProperties.LIT, on, off)));
    }

    public void createCopperGolemStatue(Block block, Block particle, DyeColor color) {
        MultiVariant blockModel = plainVariant(
                ModelTemplates.PARTICLE_ONLY.create(block, TextureMapping.particle(TextureMapping.getBlockTexture(particle)), this.modelOutput)
        );
        Identifier itemBase = ModelLocationUtils.decorateItemModelLocation("template_copper_golem_statue");
        Identifier texture = WorldOfColor.asResource("textures/entity/copper_golem/" + color.getName() + "_copper_golem.png");
        this.blockStateOutput.accept(createSimpleBlock(block, blockModel));
        this.itemModelOutput
                .accept(
                        block.asItem(),
                        ItemModelUtils.selectBlockItemProperty(
                                new Transformation(new Vector3f(0.5F, 1.5F, 0.5F), null, new Vector3f(1.0F, -1.0F, -1.0F), null),
                                CopperGolemStatueBlock.POSE,
                                ItemModelUtils.specialModel(itemBase, new CopperGolemStatueSpecialRenderer.Unbaked(texture, CopperGolemStatueBlock.Pose.STANDING)),
                                Map.of(
                                        CopperGolemStatueBlock.Pose.SITTING,
                                        ItemModelUtils.specialModel(itemBase, new CopperGolemStatueSpecialRenderer.Unbaked(texture, CopperGolemStatueBlock.Pose.SITTING)),
                                        CopperGolemStatueBlock.Pose.STAR,
                                        ItemModelUtils.specialModel(itemBase, new CopperGolemStatueSpecialRenderer.Unbaked(texture, CopperGolemStatueBlock.Pose.STAR)),
                                        CopperGolemStatueBlock.Pose.RUNNING,
                                        ItemModelUtils.specialModel(itemBase, new CopperGolemStatueSpecialRenderer.Unbaked(texture, CopperGolemStatueBlock.Pose.RUNNING))
                                )
                        )
                );
    }

    public void createCopperGolemStatue(DeferredBlock<Block> block, DeferredBlock<Block> particle, DyeColor color) {
        createCopperGolemStatue(block.get(), particle.get(), color);
    }

    public void createGlassBlocks(Block block, Block pane, Block edge) {
        this.createTrivialBlock(block, TexturedModel.CUBE.updateTexture(TextureMapping::forceAllTranslucent));
        TextureMapping texturemapping = TextureMapping.pane(block, edge).forceAllTranslucent();
        MultiVariant post = plainVariant(ModelTemplates.STAINED_GLASS_PANE_POST.create(pane, texturemapping, this.modelOutput));
        MultiVariant side = plainVariant(ModelTemplates.STAINED_GLASS_PANE_SIDE.create(pane, texturemapping, this.modelOutput));
        MultiVariant sideAlt = plainVariant(ModelTemplates.STAINED_GLASS_PANE_SIDE_ALT.create(pane, texturemapping, this.modelOutput));
        MultiVariant noSide = plainVariant(ModelTemplates.STAINED_GLASS_PANE_NOSIDE.create(pane, texturemapping, this.modelOutput));
        MultiVariant noSideAlt = plainVariant(ModelTemplates.STAINED_GLASS_PANE_NOSIDE_ALT.create(pane, texturemapping, this.modelOutput));
        Item paneItem = pane.asItem();
        this.registerSimpleItemModel(paneItem, this.createFlatItemModelWithBlockTexture(paneItem, block));
        this.blockStateOutput
                .accept(
                        MultiPartGenerator.multiPart(pane)
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
    public void createShulkerBox(Block block, Identifier texture) {
        this.createParticleOnlyBlock(block);
        Item item = block.asItem();
        Identifier baseModel = ModelTemplates.SHULKER_BOX_INVENTORY.create(item, TextureMapping.particle(block), this.modelOutput);
        Transformation transformation = ShulkerBoxRenderer.modelTransform(Direction.UP);
        ItemModel.Unbaked itemModel = ItemModelUtils.specialModel(baseModel, transformation, new ShulkerBoxSpecialRenderer.Unbaked(texture, 0.0F));
        this.itemModelOutput.accept(item, itemModel);
    }

    public void createBed(Block block) {
        Identifier head = ModelTemplates.BED_HEAD.createWithSuffix(block, "_" + BedPart.HEAD, TextureMapping.bed(block, BedPart.HEAD), this.modelOutput);
        Identifier foot = ModelTemplates.BED_FOOT.createWithSuffix(block, "_" + BedPart.FOOT, TextureMapping.bed(block, BedPart.FOOT), this.modelOutput);
        MultiVariant blockModelHead = plainVariant(head);
        MultiVariant blockModelFoot = plainVariant(foot);
        this.blockStateOutput.accept(BlockModelGenerators.createBed(block, blockModelHead, blockModelFoot));
        Transformation footTransformation = new Transformation(new Vector3f(0.0F, 0.0F, 1.0F), null, null, null);
        ItemModel.Unbaked itemModelHead = ItemModelUtils.plainModel(head);
        ItemModel.Unbaked itemModelFoot = ItemModelUtils.plainModel(foot, footTransformation);
        this.itemModelOutput.accept(block.asItem(), ItemModelUtils.composite(itemModelHead, itemModelFoot));
    }

    public void createItemFrame(DeferredItem<Item> itemFrame) {
        ColoredModelTemplates.ITEM_FRAME.create(itemFrame.getId().withPrefix("block/"),
                new TextureMapping()
                        .put(TextureSlot.PARTICLE, getBlockTexture(Blocks.BIRCH_PLANKS))
                        .put(ColoredTextureSlot.WOOD, getBlockTexture(Blocks.BIRCH_PLANKS))
                        .put(TextureSlot.BACK, new Material(itemFrame.getId().withPrefix("block/"))),
                this.modelOutput);
        ColoredModelTemplates.ITEM_FRAME_MAP.create(itemFrame.getId().withSuffix("_map").withPrefix("block/"),
                new TextureMapping()
                        .put(TextureSlot.PARTICLE, getBlockTexture(Blocks.BIRCH_PLANKS))
                        .put(ColoredTextureSlot.WOOD, getBlockTexture(Blocks.BIRCH_PLANKS))
                        .put(TextureSlot.BACK, new Material(itemFrame.getId().withPrefix("block/"))),
                this.modelOutput);
        this.registerSimpleFlatItemModel(itemFrame.get());
    }

    //DeferredHolders variants
    public void createFullAndCarpetBlocks(DeferredBlock<Block> block, DeferredBlock<Block> carpet) {
        super.createFullAndCarpetBlocks(block.get(), carpet.get());
    }

    public void copyModel(DeferredBlock<Block> donor, DeferredBlock<Block> acceptor) {
        super.copyModel(donor.get(), acceptor.get());
    }

    public void createShelf(DeferredBlock<Block> block, DeferredBlock<Block> particle) {
        super.createShelf(block.get(), particle.get());
    }

    public void createChest(DeferredBlock<Block> block, DeferredBlock<Block> particle, Identifier texture, boolean hasGiftVariant) {
        super.createChest(block.get(), particle.get(), texture, hasGiftVariant);
    }
}
