package net.theobl.worldofcolor.mixin;

import com.google.common.collect.BiMap;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.world.item.DyeColor;
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

@Mixin(value = CauldronInteractions.class, remap = false)
public abstract class CauldronInteractionsMixin {
    @ModifyVariable(method = "emptyBucket", at = @At(value = "HEAD"), argsOnly = true)
    private static BlockState emptyBucketThatActuallyWorking(BlockState newState, @Local(argsOnly = true)BlockPos pos,
                                                             @Local(argsOnly = true)Level level, @Local(argsOnly = true)ItemStack itemInHand) {
        if(!level.isClientSide()) {
            Item item = itemInHand.getItem();
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

            blockState = worldOfColor$isColoredCauldron(ModBlocks.COLORED_DYED_WATER_CAULDRONS, level, pos, item);
            if(blockState != null)
                return blockState;

        }
        return newState;
    }

    @Unique
    private static BlockState worldOfColor$isColoredCauldron(BiMap<DyeColor, DeferredBlock<Block>> checkedBlock, Level level, BlockPos pos, Item item) {
        BlockState blockState = null;
        for (DeferredBlock<Block> block : checkedBlock.values()) {
            if (level.getBlockState(pos).is(block)) {
                DyeColor color = checkedBlock.inverse().get(block);
                if (item == Items.WATER_BUCKET)
                    blockState = ModBlocks.COLORED_WATER_CAULDRONS.get(color).get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3);
                else if (item == Items.LAVA_BUCKET) {
                    blockState = ModBlocks.COLORED_LAVA_CAULDRONS.get(color).get().defaultBlockState();
                } else
                    blockState = ModBlocks.COLORED_POWDER_SNOW_CAULDRONS.get(color).get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3);
            }
        }
        return blockState;
    }
}
