package net.theobl.worldofcolor.datagen;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.item.ModItems;

import static net.theobl.worldofcolor.util.ModUtil.COLORS;
import static net.theobl.worldofcolor.util.ModUtil.SHULKER_BOXES;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, WorldOfColor.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.CLASSIC_WOOLS.forEach(this::blockWithItem);

        for (DeferredBlock<Block> carpet : ModBlocks.CLASSIC_CARPETS) {
            int index = ModBlocks.CLASSIC_CARPETS.indexOf(carpet);
            carpetBlockWithItem(carpet, ModBlocks.CLASSIC_WOOLS.get(index));
        }

        ModBlocks.SIMPLE_COLORED_BLOCKS.forEach(this::blockWithItem);
        ModBlocks.COLORED_COPPER_BLOCKS.forEach(this::blockWithItem);
        ModBlocks.COLORED_CHISELED_COPPER.forEach(this::blockWithItem);
        ModBlocks.COLORED_COPPER_GRATES.forEach(block -> blockWithRenderTypeWithItem(block, RenderType.cutout()));
        ModBlocks.COLORED_CUT_COPPER.forEach(this::blockWithItem);
        ModBlocks.COLORED_COPPER_DOORS.forEach(this::simpleDoorBlockWithItem);
        ModBlocks.COLORED_COPPER_TRAPDOORS.forEach(this::simpleTrapdoorBlockWithItem);
        ModBlocks.COLORED_COPPER_BULBS.forEach(this::bulbBlockWithItem);
        ModBlocks.COLORED_LIGHTNING_RODS.forEach(this::lightningRodBlock);

        ModBlocks.COLORED_BRICKS.forEach(this::blockWithItem);
        ModBlocks.COLORED_SLIME_BLOCKS.forEach(this::slimeBlockWithItem);

        for (DeferredBlock<Block> block : ModBlocks.COLORED_CUT_COPPER_STAIRS) {
            int index = ModBlocks.COLORED_CUT_COPPER_STAIRS.indexOf(block);
            stairsBlockWithItem(block, blockTexture(ModBlocks.COLORED_CUT_COPPER.get(index).get()));
        }

        for (DeferredBlock<Block> block : ModBlocks.COLORED_CUT_COPPER_SLABS) {
            int index = ModBlocks.COLORED_CUT_COPPER_SLABS.indexOf(block);
            simpleSlabBlockWithItem(block, blockTexture(ModBlocks.COLORED_CUT_COPPER.get(index).get()));
        }

        for (DeferredBlock<Block> block : ModBlocks.COLORED_CAULDRONS) {
            int index = ModBlocks.COLORED_CAULDRONS.indexOf(block);
            String colorName = COLORS.get(index).getName();
            simpleBlock(block.get(),
                    models().withExistingParent(name(block.get()), "block/cauldron")
                            .texture("particle", modLoc("block/" + colorName + "_cauldron_side"))
                            .texture("top", modLoc("block/" + colorName + "_cauldron_top"))
                            .texture("bottom", modLoc("block/" + colorName + "_cauldron_bottom"))
                            .texture("side", modLoc("block/" + colorName + "_cauldron_side"))
                            .texture("inside", modLoc("block/" + colorName + "_cauldron_inner"))
            );
            itemModels().basicItem(block.asItem());
        }

        for (DeferredBlock<Block> block : ModBlocks.COLORED_WATER_CAULDRONS) {
            String colorName = COLORS.get(ModBlocks.COLORED_WATER_CAULDRONS.indexOf(block)).getName();
            layeredCauldron(block, "water", colorName);
        }

        for (DeferredBlock<Block> block : ModBlocks.COLORED_POWDER_SNOW_CAULDRONS) {
            String colorName = COLORS.get(ModBlocks.COLORED_POWDER_SNOW_CAULDRONS.indexOf(block)).getName();
            layeredCauldron(block, "powder_snow", colorName);
        }

        for (DeferredBlock<Block> block : ModBlocks.COLORED_LAVA_CAULDRONS) {
            int index = ModBlocks.COLORED_LAVA_CAULDRONS.indexOf(block);
            String colorName = COLORS.get(index).getName();
            simpleBlock(block.get(),
                    models().withExistingParent(name(block.get()), "block/lava_cauldron")
                            .texture("particle", modLoc("block/" + colorName + "_cauldron_side"))
                            .texture("top", modLoc("block/" + colorName + "_cauldron_top"))
                            .texture("bottom", modLoc("block/" + colorName + "_cauldron_bottom"))
                            .texture("side", modLoc("block/" + colorName + "_cauldron_side"))
                            .texture("inside", modLoc("block/" + colorName + "_cauldron_inner"))
            );
        }

        for (DyeColor color : COLORS) {
            int index = COLORS.indexOf(color);
            waxedBlockWithItem(ModBlocks.COLORED_WAXED_COPPER_BLOCKS.get(index), ModBlocks.COLORED_COPPER_BLOCKS.get(index));
            waxedBlockWithItem(ModBlocks.COLORED_WAXED_CHISELED_COPPER.get(index), ModBlocks.COLORED_CHISELED_COPPER.get(index));
            waxedBlockWithItem(ModBlocks.COLORED_WAXED_COPPER_GRATES.get(index), ModBlocks.COLORED_COPPER_GRATES.get(index));
            waxedBlockWithItem(ModBlocks.COLORED_WAXED_CUT_COPPER.get(index), ModBlocks.COLORED_CUT_COPPER.get(index));
            waxedStairsBlockWithItem(ModBlocks.COLORED_WAXED_CUT_COPPER_STAIRS.get(index), ModBlocks.COLORED_CUT_COPPER_STAIRS.get(index));
            waxedSlabBlockWithItem(ModBlocks.COLORED_WAXED_CUT_COPPER_SLABS.get(index), ModBlocks.COLORED_CUT_COPPER_SLABS.get(index), blockTexture(ModBlocks.COLORED_CUT_COPPER.get(index).get()));
            waxedDoorBlockWithItem(ModBlocks.COLORED_WAXED_COPPER_DOORS.get(index), ModBlocks.COLORED_COPPER_DOORS.get(index));
            waxedTrapdoorBlockWithItem(ModBlocks.COLORED_WAXED_COPPER_TRAPDOORS.get(index), ModBlocks.COLORED_COPPER_TRAPDOORS.get(index));
            waxedBulbBlockWithItem(ModBlocks.COLORED_WAXED_COPPER_BULBS.get(index), ModBlocks.COLORED_COPPER_BULBS.get(index));

            stairsBlockWithItem(ModBlocks.COLORED_BRICK_STAIRS.get(index), blockTexture(ModBlocks.COLORED_BRICKS.get(index).get()));
            simpleSlabBlockWithItem(ModBlocks.COLORED_BRICK_SLABS.get(index), blockTexture(ModBlocks.COLORED_BRICKS.get(index).get()));
            wallBlock((WallBlock) ModBlocks.COLORED_BRICK_WALLS.get(index).get(), blockTexture(ModBlocks.COLORED_BRICKS.get(index).get()));
            simpleBlockItem(ModBlocks.COLORED_BRICK_WALLS.get(index).get(),
                    models().wallInventory(name(ModBlocks.COLORED_BRICK_WALLS.get(index).get()).concat("_inventory"), blockTexture(ModBlocks.COLORED_BRICKS.get(index).get())));
        }

        for (DeferredBlock<Block> quiltedConcrete : ModBlocks.QUILTED_CONCRETES) {
            int index = ModBlocks.QUILTED_CONCRETES.indexOf(quiltedConcrete);
            simpleBlockWithItem(quiltedConcrete.get(), cubeAll(SHULKER_BOXES.get(index)));
        }

        for (DeferredBlock<Block> glazedConcrete : ModBlocks.GLAZED_CONCRETES) {
            horizontalBlock(glazedConcrete.get(),
                    models().withExistingParent(name(glazedConcrete.get()), mcLoc("block/template_glazed_terracotta"))
                            .texture("pattern", blockTexture(glazedConcrete.get())),0);
            blockItem(glazedConcrete);
        }

        for (DeferredBlock<Block> planks : ModBlocks.COLORED_PLANKS) {
            int index = ModBlocks.COLORED_PLANKS.indexOf(planks);
            blockWithItem(planks);
            stairsBlockWithItem(ModBlocks.COLORED_STAIRS.get(index), blockTexture(planks.get()));
            simpleSlabBlockWithItem(ModBlocks.COLORED_SLABS.get(index), blockTexture(planks.get()));
            fenceBlockWithItem(ModBlocks.COLORED_FENCES.get(index), blockTexture(planks.get()));
            fenceGateBlockWithItem(ModBlocks.COLORED_FENCE_GATES.get(index), blockTexture(planks.get()));
            simpleDoorBlockWithItem(ModBlocks.COLORED_DOORS.get(index));
            simpleTrapdoorBlockWithItem(ModBlocks.COLORED_TRAPDOORS.get(index));
            pressurePlateBlockWithItem(ModBlocks.COLORED_PRESSURE_PLATES.get(index), blockTexture(planks.get()));
            buttonBlockWithItem(ModBlocks.COLORED_BUTTONS.get(index), blockTexture(planks.get()));
            signBlockWithItem(ModBlocks.COLORED_SIGNS.get(index), ModBlocks.COLORED_WALL_SIGNS.get(index), blockTexture(planks.get()));
            hangingSignBlockWithItem(ModBlocks.COLORED_HANGING_SIGNS.get(index), ModBlocks.COLORED_WALL_HANGING_SIGNS.get(index),
                    blockTexture(ModBlocks.COLORED_STRIPPED_LOGS.get(index).get()));

            saplingBlockWithItem(ModBlocks.COLORED_SAPLINGS.get(index));
            blockWithItem(ModBlocks.COLORED_LEAVES.get(index));
            logBlockWithItem(ModBlocks.COLORED_LOGS.get(index));
            logBlockWithItem(ModBlocks.COLORED_STRIPPED_LOGS.get(index));
            woodBlockWithItem(ModBlocks.COLORED_WOODS.get(index), blockTexture(ModBlocks.COLORED_LOGS.get(index).get()));
            woodBlockWithItem(ModBlocks.COLORED_STRIPPED_WOODS.get(index), blockTexture(ModBlocks.COLORED_STRIPPED_LOGS.get(index).get()));
            itemModels().basicItem(ModItems.COLORED_BOATS.get(index).asItem());
            itemModels().basicItem(ModItems.COLORED_CHEST_BOATS.get(index).asItem());
        }
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), rl.getPath() + suffix);
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void saplingBlockWithItem(DeferredBlock<Block> deferredBlock) {
        simpleBlock(deferredBlock.get(),
                models().cross(BuiltInRegistries.BLOCK.getKey(deferredBlock.get()).getPath(), blockTexture(deferredBlock.get())).renderType("cutout"));
        itemModels().withExistingParent(deferredBlock.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0", modLoc("block/" + name(deferredBlock.get())));
    }

    private void blockWithRenderTypeWithItem(DeferredBlock<?> deferredBlock, RenderType renderType) {
        simpleBlockWithItem(deferredBlock.get(), models().cubeAll(name(deferredBlock.get()), blockTexture(deferredBlock.get())).renderType(renderType.name));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("worldofcolor:block/" + deferredBlock.getId().getPath()));
    }

    private void slimeBlockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), models().withExistingParent(name(deferredBlock.get()), "block/slime_block")
                .texture("texture", blockTexture(deferredBlock.get()))
                .texture("particle", blockTexture(deferredBlock.get()))
                .renderType(RenderType.translucent().name));
    }

    private void carpetBlockWithItem(DeferredBlock<?> deferredBlock, DeferredBlock<?> wool) {
        simpleBlockWithItem(deferredBlock.get(), models().carpet(name(deferredBlock.get()), blockTexture(wool.get())));
    }

    private void stairsBlockWithItem(DeferredBlock<?> stairs, ResourceLocation texture) {
        stairsBlock((StairBlock) stairs.get(), texture);
        blockItem(stairs);
    }

    private void simpleSlabBlockWithItem(DeferredBlock<?> slab, ResourceLocation texture) {
        slabBlock((SlabBlock) slab.get(), texture, texture);
        blockItem(slab);
    }

    private void fenceBlockWithItem(DeferredBlock<?> fence, ResourceLocation texture) {
        fenceBlock((FenceBlock) fence.get(), texture);
        simpleBlockItem(fence.get(),
                models().fenceInventory(name(fence.get()) + "_inventory", texture));
    }

    private void fenceGateBlockWithItem(DeferredBlock<?> gate, ResourceLocation texture) {
        fenceGateBlock((FenceGateBlock) gate.get(), texture);
        blockItem(gate);
    }

    private void simpleDoorBlockWithItem(DeferredBlock<?> door) {
        doorBlockWithItem(door, blockTexture(door.get()));
    }

    private void doorBlockWithItem(DeferredBlock<?> door, ResourceLocation texture) {
        doorBlockWithRenderType((DoorBlock) door.get(), extend(texture, "_bottom"), extend(texture, "_top"), RenderType.cutout().name);
        itemModels().basicItem(door.asItem());
    }

    private void simpleTrapdoorBlockWithItem(DeferredBlock<?> trapdoor) {
        trapdoorBlockWithItem(trapdoor, blockTexture(trapdoor.get()));
    }

    private void trapdoorBlockWithItem(DeferredBlock<?> trapdoor, ResourceLocation texture) {
        trapdoorBlockWithRenderType((TrapDoorBlock) trapdoor.get(), texture, true, RenderType.cutout().name);
        simpleBlockItem(trapdoor.get(), models().trapdoorOrientableBottom(name(trapdoor.get()) + "_bottom", texture));
    }

    private void pressurePlateBlockWithItem(DeferredBlock<?> pressurePlate, ResourceLocation texture) {
        pressurePlateBlock((PressurePlateBlock) pressurePlate.get(), texture);
        blockItem(pressurePlate);
    }

    private void buttonBlockWithItem(DeferredBlock<?> button, ResourceLocation texture) {
        buttonBlock((ButtonBlock) button.get(), texture);
        simpleBlockItem(button.get(),
                models().buttonInventory(name(button.get()) + "_inventory", texture));
    }

    private void signBlockWithItem(DeferredBlock<?> standingSign, DeferredBlock<?> wallSign, ResourceLocation texture) {
        signBlock((StandingSignBlock) standingSign.get(), (WallSignBlock) wallSign.get(), texture);
        itemModels().basicItem(standingSign.asItem());
    }

    private void hangingSignBlockWithItem(DeferredBlock<?> ceilingHangingSign, DeferredBlock<?> wallHangingSign, ResourceLocation texture) {
        hangingSignBlock((CeilingHangingSignBlock) ceilingHangingSign.get(), (WallHangingSignBlock) wallHangingSign.get(), texture);
        itemModels().basicItem(ceilingHangingSign.asItem());
    }

    private void logBlockWithItem(DeferredBlock<?> log) {
        logBlock((RotatedPillarBlock) log.get());
        blockItem(log);
    }

    private void woodBlockWithItem(DeferredBlock<?> block, ResourceLocation texture) {
        axisBlock((RotatedPillarBlock) block.get(),
                models().cubeColumn(name(block.get()), texture, texture),
                models().cubeColumn(name(block.get()), texture, texture));
        blockItem(block);
    }

    private void bulbBlockWithItem(DeferredBlock<?> block) {
        getVariantBuilder(block.get())
                .partialState().with(CopperBulbBlock.LIT, false).with(CopperBulbBlock.POWERED, false)
                .modelForState().modelFile(cubeAll(block.get())).addModel()
                .partialState().with(CopperBulbBlock.LIT, false).with(CopperBulbBlock.POWERED, true)
                .modelForState().modelFile(models().cubeAll(name(block.get()) + "_powered", extend(blockTexture(block.get()), "_powered"))).addModel()
                .partialState().with(CopperBulbBlock.LIT, true).with(CopperBulbBlock.POWERED, false)
                .modelForState().modelFile(models().cubeAll(name(block.get()) + "_lit", extend(blockTexture(block.get()), "_lit"))).addModel()
                .partialState().with(CopperBulbBlock.LIT, true).with(CopperBulbBlock.POWERED, true)
                .modelForState().modelFile(models().cubeAll(name(block.get()) + "_lit_powered", extend(blockTexture(block.get()), "_lit_powered"))).addModel();
        blockItem(block);
    }

    private void waxedBulbBlockWithItem(DeferredBlock<?> waxedBlock, DeferredBlock<?> regularBlock) {
        ModelFile bulb = models().getExistingFile(blockTexture(regularBlock.get()));
        ModelFile powered = models().getExistingFile(extend(blockTexture(regularBlock.get()), "_powered"));
        ModelFile lit = models().getExistingFile(extend(blockTexture(regularBlock.get()), "_lit"));
        ModelFile litPowered = models().getExistingFile(extend(blockTexture(regularBlock.get()), "_lit_powered"));
        getVariantBuilder(waxedBlock.get())
                .partialState().with(CopperBulbBlock.LIT, false).with(CopperBulbBlock.POWERED, false)
                .modelForState().modelFile(bulb).addModel()
                .partialState().with(CopperBulbBlock.LIT, false).with(CopperBulbBlock.POWERED, true)
                .modelForState().modelFile(powered).addModel()
                .partialState().with(CopperBulbBlock.LIT, true).with(CopperBulbBlock.POWERED, false)
                .modelForState().modelFile(lit).addModel()
                .partialState().with(CopperBulbBlock.LIT, true).with(CopperBulbBlock.POWERED, true)
                .modelForState().modelFile(litPowered).addModel();
        simpleBlockItem(waxedBlock.get(), bulb);
    }

    private void waxedBlockWithItem(DeferredBlock<Block> waxedBlock, DeferredBlock<Block> regularBlock) {
        ModelFile modelFile = models().getExistingFile(blockTexture(regularBlock.get()));
        simpleBlock(waxedBlock.get(), modelFile);
        simpleBlockItem(waxedBlock.get(), modelFile);
    }

    private void waxedStairsBlockWithItem(DeferredBlock<Block> waxedBlock, DeferredBlock<Block> regularBlock) {
        ModelFile stairs = models().getExistingFile(blockTexture(regularBlock.get()));
        ModelFile stairsInner = models().getExistingFile(extend(blockTexture(regularBlock.get()), "_inner"));
        ModelFile stairsOuter = models().getExistingFile(extend(blockTexture(regularBlock.get()), "_outer"));
        stairsBlock((StairBlock) waxedBlock.get(), stairs, stairsInner, stairsOuter);
        simpleBlockItem(waxedBlock.get(), stairs);
    }

    private void waxedSlabBlockWithItem(DeferredBlock<Block> waxedBlock, DeferredBlock<Block> regularBlock, ResourceLocation doubleSlab) {
        ModelFile bottom = models().getExistingFile(blockTexture(regularBlock.get()));
        ModelFile top = models().getExistingFile(extend(blockTexture(regularBlock.get()), "_top"));
        slabBlock((SlabBlock) waxedBlock.get(), bottom, top, models().getExistingFile(doubleSlab));
        simpleBlockItem(waxedBlock.get(), bottom);
    }

    private void waxedDoorBlockWithItem(DeferredBlock<Block> waxedBlock, DeferredBlock<Block> regularBlock) {
        ModelFile bottomLeft = models().getExistingFile(extend(blockTexture(regularBlock.get()), "_bottom_left"));
        ModelFile bottomLeftOpen = models().getExistingFile(extend(blockTexture(regularBlock.get()), "_bottom_left_open"));
        ModelFile bottomRight = models().getExistingFile(extend(blockTexture(regularBlock.get()), "_bottom_right"));
        ModelFile bottomRightOpen = models().getExistingFile(extend(blockTexture(regularBlock.get()), "_bottom_right_open"));
        ModelFile topLeft = models().getExistingFile(extend(blockTexture(regularBlock.get()), "_top_left"));
        ModelFile topLeftOpen = models().getExistingFile(extend(blockTexture(regularBlock.get()), "_top_left_open"));
        ModelFile topRight = models().getExistingFile(extend(blockTexture(regularBlock.get()), "_top_right"));
        ModelFile topRightOpen = models().getExistingFile(extend(blockTexture(regularBlock.get()), "_top_right_open"));
        ModelFile item = models().getExistingFile(ResourceLocation.fromNamespaceAndPath(WorldOfColor.MODID, "item/" + name(regularBlock.get())));
        doorBlock((DoorBlock) waxedBlock.get(), bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen, topLeft, topLeftOpen, topRight, topRightOpen);
        simpleBlockItem(waxedBlock.get(), item);
    }

    private void waxedTrapdoorBlockWithItem(DeferredBlock<Block> waxedBlock, DeferredBlock<Block> regularBlock) {
        ModelFile bottom = models().getExistingFile(extend(blockTexture(regularBlock.get()), "_bottom"));
        ModelFile top = models().getExistingFile(extend(blockTexture(regularBlock.get()), "_top"));
        ModelFile open = models().getExistingFile(extend(blockTexture(regularBlock.get()), "_open"));
        trapdoorBlock((TrapDoorBlock) waxedBlock.get(), bottom, top, open, true);
        simpleBlockItem(waxedBlock.get(), bottom);
    }

    private void lightningRodBlock(DeferredBlock<Block> lightningRod) {
        ModelFile model = models().withExistingParent(lightningRod.getRegisteredName(), mcLoc("block/lightning_rod"))
                .texture("texture", blockTexture(lightningRod.get()))
                .texture("particle", blockTexture(lightningRod.get()));
        ModelFile modelOn = models().getExistingFile(mcLoc("block/lightning_rod_on"));

        getVariantBuilder(lightningRod.get())
                .partialState().with(LightningRodBlock.FACING, Direction.DOWN).with(LightningRodBlock.POWERED, false)
                .modelForState().rotationX(180).modelFile(model).addModel()
                .partialState().with(LightningRodBlock.FACING, Direction.DOWN).with(LightningRodBlock.POWERED, true)
                .modelForState().rotationX(180).modelFile(modelOn).addModel()

                .partialState().with(LightningRodBlock.FACING, Direction.EAST).with(LightningRodBlock.POWERED, false)
                .modelForState().rotationX(90).rotationY(90).modelFile(model).addModel()
                .partialState().with(LightningRodBlock.FACING, Direction.EAST).with(LightningRodBlock.POWERED, true)
                .modelForState().rotationX(90).rotationY(90).modelFile(modelOn).addModel()

                .partialState().with(LightningRodBlock.FACING, Direction.NORTH).with(LightningRodBlock.POWERED, false)
                .modelForState().rotationX(90).modelFile(model).addModel()
                .partialState().with(LightningRodBlock.FACING, Direction.NORTH).with(LightningRodBlock.POWERED, true)
                .modelForState().rotationX(90).modelFile(modelOn).addModel()

                .partialState().with(LightningRodBlock.FACING, Direction.SOUTH).with(LightningRodBlock.POWERED, false)
                .modelForState().rotationX(90).rotationY(180).modelFile(model).addModel()
                .partialState().with(LightningRodBlock.FACING, Direction.SOUTH).with(LightningRodBlock.POWERED, true)
                .modelForState().rotationX(90).rotationY(180).modelFile(modelOn).addModel()

                .partialState().with(LightningRodBlock.FACING, Direction.UP).with(LightningRodBlock.POWERED, false)
                .modelForState().modelFile(model).addModel()
                .partialState().with(LightningRodBlock.FACING, Direction.UP).with(LightningRodBlock.POWERED, true)
                .modelForState().modelFile(modelOn).addModel()

                .partialState().with(LightningRodBlock.FACING, Direction.WEST).with(LightningRodBlock.POWERED, false)
                .modelForState().rotationX(90).rotationY(270).modelFile(model).addModel()
                .partialState().with(LightningRodBlock.FACING, Direction.WEST).with(LightningRodBlock.POWERED, true)
                .modelForState().rotationX(90).rotationY(270).modelFile(modelOn).addModel();

        blockItem(lightningRod);
    }

    private void layeredCauldron(DeferredBlock<Block> block, String cauldronType, String colorName) {
        ModelFile level1 = models().withExistingParent(name(block.get()) + "_level1", "block/" + cauldronType + "_cauldron_level1")
                .texture("particle", modLoc("block/" + colorName + "_cauldron_side"))
                .texture("top", modLoc("block/" + colorName + "_cauldron_top"))
                .texture("bottom", modLoc("block/" + colorName + "_cauldron_bottom"))
                .texture("side", modLoc("block/" + colorName + "_cauldron_side"))
                .texture("inside", modLoc("block/" + colorName + "_cauldron_inner"));

        ModelFile level2 = models().withExistingParent(name(block.get()) + "_level2", "block/" + cauldronType + "_cauldron_level2")
                .texture("particle", modLoc("block/" + colorName + "_cauldron_side"))
                .texture("top", modLoc("block/" + colorName + "_cauldron_top"))
                .texture("bottom", modLoc("block/" + colorName + "_cauldron_bottom"))
                .texture("side", modLoc("block/" + colorName + "_cauldron_side"))
                .texture("inside", modLoc("block/" + colorName + "_cauldron_inner"));

        ModelFile full = models().withExistingParent(name(block.get()) + "_full", "block/" + cauldronType + "_cauldron_full")
                .texture("particle", modLoc("block/" + colorName + "_cauldron_side"))
                .texture("top", modLoc("block/" + colorName + "_cauldron_top"))
                .texture("bottom", modLoc("block/" + colorName + "_cauldron_bottom"))
                .texture("side", modLoc("block/" + colorName + "_cauldron_side"))
                .texture("inside", modLoc("block/" + colorName + "_cauldron_inner"));

        getVariantBuilder(block.get())
                .partialState().with(LayeredCauldronBlock.LEVEL, 1).modelForState().modelFile(level1).addModel()
                .partialState().with(LayeredCauldronBlock.LEVEL, 2).modelForState().modelFile(level2).addModel()
                .partialState().with(LayeredCauldronBlock.LEVEL, 3).modelForState().modelFile(full).addModel();
    }
}
