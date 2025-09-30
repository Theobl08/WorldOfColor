package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.theobl.worldofcolor.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(value = CauldronInteraction.class, remap = false)
public interface CauldronInteractionMixin {
    @ModifyVariable(method = "emptyBucket", at = @At(value = "HEAD"), argsOnly = true)
    private static BlockState emptyBucketThatActuallyWorking(BlockState state, @Local(argsOnly = true)BlockPos pos,
                                                             @Local(argsOnly = true)Level level, @Local(argsOnly = true)ItemStack filledStack) {
        if(!level.isClientSide()) {
            Item item = filledStack.getItem();
            //checking if the block is one of our colored cauldron
//            for (DeferredBlock<Block> block : ModBlocks.COLORED_CAULDRONS) {
//                if (level.getBlockState(pos).is(block)) {
//                    int index = ModBlocks.COLORED_CAULDRONS.indexOf(block);
//                    if (item == Items.WATER_BUCKET)
//                        blockState = ModBlocks.COLORED_WATER_CAULDRONS.get(index).get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3);
//                    else if (item == Items.LAVA_BUCKET) {
//                        blockState = ModBlocks.COLORED_LAVA_CAULDRONS.get(index).get().defaultBlockState();
//                    } else
//                        blockState = ModBlocks.COLORED_POWDER_SNOW_CAULDRONS.get(index).get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3);
//                    return blockState;
//                }
//            }
            BlockState blockState = worldOfColor$isColoredCauldron(ModBlocks.COLORED_CAULDRONS, level, pos, item);
            if(blockState != null)
                return blockState;

            blockState = worldOfColor$isColoredCauldron(ModBlocks.COLORED_WATER_CAULDRONS, level, pos, item);
            if(blockState != null)
                return blockState;

            blockState = worldOfColor$isColoredCauldron(ModBlocks.COLORED_LAVA_CAULDRONS, level, pos, item);
            if(blockState != null)
                return blockState;

            blockState = worldOfColor$isColoredCauldron(ModBlocks.COLORED_POWDER_SNOW_CAULDRONS, level, pos, item);
            if(blockState != null)
                return blockState;

        }
        return state;
    }

    @Unique
    private static BlockState worldOfColor$isColoredCauldron(List<DeferredBlock<Block>> checkedBlock, Level level, BlockPos pos, Item item) {
        BlockState blockState = null;
        for (DeferredBlock<Block> block : checkedBlock) {
            if (level.getBlockState(pos).is(block)) {
                int index = checkedBlock.indexOf(block);
                if (item == Items.WATER_BUCKET)
                    blockState = ModBlocks.COLORED_WATER_CAULDRONS.get(index).get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3);
                else if (item == Items.LAVA_BUCKET) {
                    blockState = ModBlocks.COLORED_LAVA_CAULDRONS.get(index).get().defaultBlockState();
                } else
                    blockState = ModBlocks.COLORED_POWDER_SNOW_CAULDRONS.get(index).get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3);
            }
        }
        return blockState;
    }
}
