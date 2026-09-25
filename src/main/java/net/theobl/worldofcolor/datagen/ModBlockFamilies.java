package net.theobl.worldofcolor.datagen;

import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColorCollection;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.theobl.worldofcolor.block.ColoringColorCollection;
import net.theobl.worldofcolor.block.ModBlocks;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class ModBlockFamilies {
    private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();
    private static final String RECIPE_GROUP_PREFIX_WOODEN = "wooden";
    private static final String RECIPE_GROUP_PREFIX_WOOLEN = "woolen";
    private static final String RECIPE_GROUP_PREFIX_CONCRETE = "concrete";
    private static final String RECIPE_UNLOCKED_BY_HAS_PLANKS = "has_planks";
    public static final ColorCollection<BlockFamily> COLORED_PLANKS = createFamily(color ->
            familyBuilder(ModBlocks.COLORED_PLANKS.pick(color))
                    .log(ModBlocks.COLORED_LOGS.pick(color))
                    .strippedLog(ModBlocks.COLORED_STRIPPED_LOGS.pick(color))
                    .button(ModBlocks.COLORED_BUTTONS.pick(color))
                    .fence(ModBlocks.COLORED_FENCES.pick(color))
                    .fenceGate(ModBlocks.COLORED_FENCE_GATES.pick(color))
                    .hangingSign(ModBlocks.COLORED_HANGING_SIGNS.pick(color), ModBlocks.COLORED_WALL_HANGING_SIGNS.pick(color))
                    .pressurePlate(ModBlocks.COLORED_PRESSURE_PLATES.pick(color))
                    .sign(ModBlocks.COLORED_SIGNS.pick(color), ModBlocks.COLORED_WALL_SIGNS.pick(color))
                    .slab(ModBlocks.COLORED_SLABS.pick(color))
                    .stairs(ModBlocks.COLORED_STAIRS.pick(color))
                    .door(ModBlocks.COLORED_DOORS.pick(color))
                    .trapdoor(ModBlocks.COLORED_TRAPDOORS.pick(color))
                    .recipeGroupPrefix(RECIPE_GROUP_PREFIX_WOODEN)
                    .recipeUnlockedBy(RECIPE_UNLOCKED_BY_HAS_PLANKS)
                    .getFamily()
    );
    public static final ColorCollection<BlockFamily> COLORED_BRICKS = createFamily(color ->
            familyBuilder(ModBlocks.COLORED_BRICKS.pick(color))
                    .wall(ModBlocks.COLORED_BRICK_WALLS.pick(color))
                    .stairs(ModBlocks.COLORED_BRICK_STAIRS.pick(color))
                    .slab(ModBlocks.COLORED_BRICK_SLABS.pick(color))
                    .generateStonecutterRecipe()
                    .getFamily()
    );
    public static final ColoringColorCollection<BlockFamily> COLORED_COPPER_BLOCK = ColoringColorCollection.createFamily(
            (prefix, color) -> familyBuilder(ModBlocks.COLORED_COPPER_BLOCKS.waxed().pick(color))
                    .cut(ModBlocks.COLORED_CUT_COPPER.waxed().pick(color))
                    .recipeGroupPrefix("waxed_cut_copper")
                    .dontGenerateModel()
                    .getFamily(),
            (prefix, color) -> familyBuilder(ModBlocks.COLORED_COPPER_BLOCKS.coloring().pick(color))
                    .cut(ModBlocks.COLORED_CUT_COPPER.coloring().pick(color))
                    .dontGenerateModel()
                    .getFamily()
    );
    public static final ColoringColorCollection<BlockFamily> COLORED_CUT_COPPER = ColoringColorCollection.createFamily(
            (prefix, color) -> familyBuilder(ModBlocks.COLORED_CUT_COPPER.waxed().pick(color))
                    .slab(ModBlocks.COLORED_CUT_COPPER_SLABS.waxed().pick(color))
                    .stairs(ModBlocks.COLORED_CUT_COPPER_STAIRS.waxed().pick(color))
                    .chiseled(ModBlocks.COLORED_CHISELED_COPPER.waxed().pick(color))
                    .recipeGroupPrefix("waxed_cut_copper")
                    .dontGenerateModel()
                    .generateStonecutterRecipe()
                    .getFamily(),
            (prefix, color) -> familyBuilder(ModBlocks.COLORED_CUT_COPPER.coloring().pick(color))
                    .slab(ModBlocks.COLORED_CUT_COPPER_SLABS.coloring().pick(color))
                    .stairs(ModBlocks.COLORED_CUT_COPPER_STAIRS.coloring().pick(color))
                    .chiseled(ModBlocks.COLORED_CHISELED_COPPER.coloring().pick(color))
                    .dontGenerateModel()
                    .generateStonecutterRecipe()
                    .getFamily()
    );
    public static final BlockFamily RGB_WOOL = familyBuilder(ModBlocks.RGB_WOOL)
            .carpet(ModBlocks.RGB_CARPET)
            .stairs(ModBlocks.RGB_WOOL_STAIRS)
            .slab(ModBlocks.RGB_WOOL_SLAB)
            .recipeGroupPrefix(RECIPE_GROUP_PREFIX_WOOLEN)
            .getFamily();
    public static final BlockFamily RGB_CONCRETE = familyBuilder(ModBlocks.RGB_CONCRETE)
            .stairs(ModBlocks.RGB_CONCRETE_STAIRS)
            .slab(ModBlocks.RGB_CONCRETE_SLAB)
            .recipeGroupPrefix(RECIPE_GROUP_PREFIX_CONCRETE)
            .generateStonecutterRecipe()
            .getFamily();
    public static final BlockFamily MISSINGNO_WOOL = familyBuilder(ModBlocks.MISSINGNO_WOOL)
            .carpet(ModBlocks.MISSINGNO_CARPET)
            .stairs(ModBlocks.MISSINGNO_WOOL_STAIRS)
            .slab(ModBlocks.MISSINGNO_WOOL_SLAB)
            .recipeGroupPrefix(RECIPE_GROUP_PREFIX_WOOLEN)
            .getFamily();
    public static final BlockFamily MISSINGNO_CONCRETE = familyBuilder(ModBlocks.MISSINGNO_CONCRETE)
            .stairs(ModBlocks.MISSINGNO_CONCRETE_STAIRS)
            .slab(ModBlocks.MISSINGNO_CONCRETE_SLAB)
            .recipeGroupPrefix(RECIPE_GROUP_PREFIX_CONCRETE)
            .generateStonecutterRecipe()
            .getFamily();

    public static ColorCollection<BlockFamily> createFamily(Function<DyeColor, BlockFamily> colorProvider) {
        return ColorCollection.VALUES.map(colorProvider);
    }

    private static Builder familyBuilder(DeferredBlock<Block> baseBlock) {
        return familyBuilder(baseBlock.get());
    }
    private static Builder familyBuilder(Block baseBlock) {
        Builder builder = new Builder(baseBlock);
        BlockFamily blockfamily = MAP.put(baseBlock, builder.getFamily());
        if (blockfamily != null) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(baseBlock));
        } else {
            return builder;
        }
    }

    public static Stream<BlockFamily> getAllFamilies() {
        return MAP.values().stream();
    }

    public static @Nullable BlockFamily getFamily(Block base) {
        return MAP.get(base);
    }

    @NullMarked
    public static class Builder extends BlockFamily.Builder {
        public Builder(Block baseBlock) {
            super(baseBlock);
        }

        public Builder button(DeferredBlock<Block> button) {
            super.button(button.get());
            return this;
        }

        public Builder carpet(DeferredBlock<Block> carpet) {
            super.carpet(carpet.get());
            return this;
        }

        public Builder chiseled(DeferredBlock<Block> chiseled) {
            super.chiseled(chiseled.get());
            return this;
        }

        public Builder cracked(DeferredBlock<Block> cracked) {
            super.cracked(cracked.get());
            return this;
        }

        public Builder tiles(DeferredBlock<Block> tiles) {
            super.tiles(tiles.get());
            return this;
        }

        public Builder pillar(DeferredBlock<Block> pillar) {
            super.pillar(pillar.get());
            return this;
        }

        public Builder cut(DeferredBlock<Block> cut) {
            super.cut(cut.get());
            return this;
        }

        public Builder door(DeferredBlock<Block> door) {
            super.door(door.get());
            return this;
        }

        public Builder fence(DeferredBlock<Block> fence) {
            super.fence(fence.get());
            return this;
        }

        public Builder fenceGate(DeferredBlock<Block> fenceGate) {
            super.fenceGate(fenceGate.get());
            return this;
        }

        public Builder sign(DeferredBlock<Block> sign, DeferredBlock<Block> wallSign) {
            super.sign(sign.get(), wallSign.get());
            return this;
        }

        public Builder hangingSign(DeferredBlock<Block> sign, DeferredBlock<Block> wallSign) {
            super.hangingSign(sign.get(), wallSign.get());
            return this;
        }

        public Builder log(DeferredBlock<Block> log) {
            super.log(log.get());
            return this;
        }

        public Builder strippedLog(DeferredBlock<Block> strippedLog) {
            super.strippedLog(strippedLog.get());
            return this;
        }

        public Builder slab(DeferredBlock<Block> slab) {
            super.slab(slab.get());
            return this;
        }

        public Builder stairs(DeferredBlock<Block> stairs) {
            super.stairs(stairs.get());
            return this;
        }

        public Builder pressurePlate(DeferredBlock<Block> pressurePlate) {
            super.pressurePlate(pressurePlate.get());
            return this;
        }

        public Builder trapdoor(DeferredBlock<Block> trapdoor) {
            super.trapdoor(trapdoor.get());
            return this;
        }

        public Builder wall(DeferredBlock<Block> wall) {
            super.wall(wall.get());
            return this;
        }

        @Override
        public Builder dontGenerateModel() {
            super.dontGenerateModel();
            return this;
        }

        @Override
        public Builder dontGenerateCraftingRecipe() {
            super.dontGenerateCraftingRecipe();
            return this;
        }

        @Override
        public Builder generateStonecutterRecipe() {
            super.generateStonecutterRecipe();
            return this;
        }

        @Override
        public Builder recipeGroupPrefix(String recipeGroupPrefix) {
            super.recipeGroupPrefix(recipeGroupPrefix);
            return this;
        }

        @Override
        public Builder recipeUnlockedBy(String recipeUnlockedBy) {
            super.recipeUnlockedBy(recipeUnlockedBy);
            return this;
        }
    }
}
