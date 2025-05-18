package net.theobl.worldofcolor.datagen;

import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.theobl.worldofcolor.block.ModBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ModBlockFamilies {
    private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();
    private static final String RECIPE_GROUP_PREFIX_WOODEN = "wooden";
    private static final String RECIPE_UNLOCKED_BY_HAS_PLANKS = "has_planks";
    public static final List<BlockFamily> COLORED_PLANKS = coloredPlanksFamilyBuilder();
//    public static final BlockFamily WHITE_PLANKS = familyBuilder(ModBlocks.COLORED_PLANKS.getFirst().get())
//            .button(ModBlocks.COLORED_BUTTONS.getFirst().get())
//            .fence(ModBlocks.COLORED_FENCES.getFirst().get())
//            .fenceGate(ModBlocks.COLORED_FENCE_GATES.getFirst().get())
//            .pressurePlate(ModBlocks.COLORED_PRESSURE_PLATES.getFirst().get())
//            //.sign(ModBlocks.COLORED_SIGN.getFirst().get(), ModBlocks.COLORED_WALL_SIGN.getFirst().get())
//            .slab(ModBlocks.COLORED_SLABS.getFirst().get())
//            .stairs(ModBlocks.COLORED_STAIRS.getFirst().get())
//            .door(ModBlocks.COLORED_DOORS.getFirst().get())
//            .trapdoor(ModBlocks.COLORED_TRAPDOORS.getFirst().get())
//            .recipeGroupPrefix(RECIPE_GROUP_PREFIX_WOODEN)
//            .recipeUnlockedBy(RECIPE_UNLOCKED_BY_HAS_PLANKS)
//            .getFamily();

    private static List<BlockFamily> coloredPlanksFamilyBuilder() {
        List<BlockFamily> families = new ArrayList<>();
        for (DeferredBlock<Block> planks : ModBlocks.COLORED_PLANKS) {
            int index = ModBlocks.COLORED_PLANKS.indexOf(planks);
            BlockFamily family = familyBuilder(planks.get())
                    .button(ModBlocks.COLORED_BUTTONS.get(index).get())
                    .fence(ModBlocks.COLORED_FENCES.get(index).get())
                    .fenceGate(ModBlocks.COLORED_FENCE_GATES.get(index).get())
                    .pressurePlate(ModBlocks.COLORED_PRESSURE_PLATES.get(index).get())
                    .sign(ModBlocks.COLORED_SIGNS.get(index).get(), ModBlocks.COLORED_WALL_SIGNS.get(index).get())
                    .slab(ModBlocks.COLORED_SLABS.get(index).get())
                    .stairs(ModBlocks.COLORED_STAIRS.get(index).get())
                    .door(ModBlocks.COLORED_DOORS.get(index).get())
                    .trapdoor(ModBlocks.COLORED_TRAPDOORS.get(index).get())
                    .recipeGroupPrefix(RECIPE_GROUP_PREFIX_WOODEN)
                    .recipeUnlockedBy(RECIPE_UNLOCKED_BY_HAS_PLANKS)
                    .getFamily();
            families.add(family);
        }
        return families;
    }

    private static BlockFamily.Builder familyBuilder(Block baseBlock) {
        BlockFamily.Builder blockfamily$builder = new BlockFamily.Builder(baseBlock);
        BlockFamily blockfamily = MAP.put(baseBlock, blockfamily$builder.getFamily());
        if (blockfamily != null) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(baseBlock));
        } else {
            return blockfamily$builder;
        }
    }

    public static Stream<BlockFamily> getAllFamilies() {
        return MAP.values().stream();
    }
}
