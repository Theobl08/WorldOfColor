package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CopperChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.DataMapHooks;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.util.ModUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Optional;

@Mixin(CopperChestBlock.class)
public abstract class CopperChestBlockMixin {
    @Shadow
    private static Optional<BlockState> unwaxBlock(CopperChestBlock copperChestBlock, BlockState state) {
        throw new AssertionError();
    }

    @ModifyVariable(method = "getLeastOxidizedChestOfConnectedBlocks", at = @At(value = "LOAD"))
    private static Block getLeastColoredChestOfConnectedBlocks(Block leastOxidizedBlock, @Local(argsOnly = true) BlockState state, @Local(name = "connectedState") BlockState connectedState) {
        CopperChestBlock copperChestBlock = (CopperChestBlock) state.getBlock();
        CopperChestBlock connectedCopperChestBlock = (CopperChestBlock) connectedState.getBlock();
        if(copperChestBlock.getState().ordinal() != connectedCopperChestBlock.getState().ordinal())
            return leastOxidizedBlock;
        BlockState updatedBlockState = state;
        BlockState connectedPredictedBlockState = connectedState;
        if (copperChestBlock.isWaxed() != connectedCopperChestBlock.isWaxed()) {
            updatedBlockState = unwaxBlock(copperChestBlock, state).orElse(state);
            connectedPredictedBlockState = unwaxBlock(connectedCopperChestBlock, connectedState).orElse(connectedState);
        }

        if(connectedPredictedBlockState.getBlock() == Blocks.OXIDIZED_COPPER_CHEST || updatedBlockState.getBlock() == Blocks.OXIDIZED_COPPER_CHEST)
            return Blocks.OXIDIZED_COPPER_CHEST;

        if(connectedPredictedBlockState.getBlock() == Blocks.WAXED_OXIDIZED_COPPER_CHEST || updatedBlockState.getBlock() == Blocks.WAXED_OXIDIZED_COPPER_CHEST)
            return Blocks.WAXED_OXIDIZED_COPPER_CHEST;

        for (DyeColor color : ModUtil.COLORS) {
            int index = ModUtil.COLORS.indexOf(color);
            if(updatedBlockState.is(ModBlocks.COLORED_COPPER_CHESTS.get(color))) {
                for (DyeColor color1 : ModUtil.COLORS) {
                    int index1 = ModUtil.COLORS.indexOf(color1);
                    if(connectedPredictedBlockState.is(ModBlocks.COLORED_COPPER_CHESTS.get(color1))) {
                        return index <= index1 ? updatedBlockState.getBlock() : connectedPredictedBlockState.getBlock();
                    }
                }
            }
        }

        for (DyeColor color : ModUtil.COLORS) {
            int index = ModUtil.COLORS.indexOf(color);
            if(updatedBlockState.is(ModBlocks.COLORED_WAXED_COPPER_CHESTS.get(color))) {
                for (DyeColor color1 : ModUtil.COLORS) {
                    int index1 = ModUtil.COLORS.indexOf(color1);
                    if(connectedPredictedBlockState.is(ModBlocks.COLORED_WAXED_COPPER_CHESTS.get(color1))) {
                        return index <= index1 ? updatedBlockState.getBlock() : connectedPredictedBlockState.getBlock();
                    }
                }
            }
        }
        return leastOxidizedBlock;
    }

    @ModifyReturnValue(method = "unwaxBlock", at = @At("RETURN"))
    private static Optional<BlockState> unwaxBlock(Optional<BlockState> original, @Local(argsOnly = true) CopperChestBlock copperChestBlock, @Local(argsOnly = true) BlockState state) {
        return !copperChestBlock.isWaxed()
                ? Optional.of(state)
                : Optional.ofNullable(DataMapHooks.getBlockUnwaxed(state.getBlock())).map(block1 -> block1.withPropertiesOf(state));
    }
}
