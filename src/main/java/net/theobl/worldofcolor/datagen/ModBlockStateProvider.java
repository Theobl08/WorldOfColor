package net.theobl.worldofcolor.datagen;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.item.ModItems;

import static net.theobl.worldofcolor.util.ModUtil.*;

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

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("worldofcolor:block/" + deferredBlock.getId().getPath()));
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
        simpleBlockItem(trapdoor.get(), models().trapdoorBottom(name(trapdoor.get()), texture));
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
}
