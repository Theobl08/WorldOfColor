package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.theobl.worldofcolor.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static net.minecraft.world.level.block.LayeredCauldronBlock.LEVEL;

@Mixin(LayeredCauldronBlock.class)
public abstract class LayeredCauldronBlockMixin {
    @ModifyExpressionValue(method = "lowerFillLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;"))
    private static BlockState lowerFillLevel(BlockState original, @Local(argsOnly = true) BlockState state) {
        for (DeferredBlock<Block> block : ModBlocks.COLORED_WATER_CAULDRONS) {
            if (state.is(block)) {
                int index = ModBlocks.COLORED_WATER_CAULDRONS.indexOf(block);
                return ModBlocks.COLORED_CAULDRONS.get(index).get().defaultBlockState();
            }
        }
        return original;
    }
    @ModifyExpressionValue(method = "handleEntityOnFireInside", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;"))
    private static BlockState handleEntityOnFireInside(BlockState original, @Local(argsOnly = true) BlockState state) {
        for (DeferredBlock<Block> block : ModBlocks.COLORED_POWDER_SNOW_CAULDRONS) {
            if (state.is(block)) {
                int index = ModBlocks.COLORED_POWDER_SNOW_CAULDRONS.indexOf(block);
                return ModBlocks.COLORED_WATER_CAULDRONS.get(index).get().defaultBlockState().setValue(LEVEL, state.getValue(LEVEL));
            }
        }
        return original;
    }
}
