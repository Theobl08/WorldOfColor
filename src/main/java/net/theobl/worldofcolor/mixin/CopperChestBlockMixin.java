package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CopperChestBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.DataMapHooks;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.util.ModUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Optional;

@Mixin(CopperChestBlock.class)
public abstract class CopperChestBlockMixin {
    @Shadow
    private static Optional<BlockState> unwaxBlock(CopperChestBlock block, BlockState state) {
        throw new AssertionError();
    }

    @ModifyVariable(method = "getLeastOxidizedChestOfConnectedBlocks", at = @At(value = "LOAD"))
    private static Block getLeastColoredChestOfConnectedBlocks(Block value, @Local(argsOnly = true) BlockState state, @Local(ordinal = 1) BlockState blockState) {
        CopperChestBlock copperChestBlock = (CopperChestBlock) state.getBlock();
        CopperChestBlock copperChestBlock1 = (CopperChestBlock) blockState.getBlock();
        if(copperChestBlock.getState().ordinal() != copperChestBlock1.getState().ordinal())
            return value;
        BlockState blockstate2 = state;
        BlockState blockstate1 = blockState;
        if (copperChestBlock.isWaxed() != copperChestBlock1.isWaxed()) {
            blockstate2 = unwaxBlock(copperChestBlock, state).orElse(state);
            blockstate1 = unwaxBlock(copperChestBlock1, blockState).orElse(blockState);
        }

        if(blockstate1.getBlock() == Blocks.OXIDIZED_COPPER_CHEST || blockstate2.getBlock() == Blocks.OXIDIZED_COPPER_CHEST)
            return Blocks.OXIDIZED_COPPER_CHEST;

        if(blockstate1.getBlock() == Blocks.WAXED_OXIDIZED_COPPER_CHEST || blockstate2.getBlock() == Blocks.WAXED_OXIDIZED_COPPER_CHEST)
            return Blocks.WAXED_OXIDIZED_COPPER_CHEST;

        for (DyeColor color : ModUtil.COLORS) {
            int index = ModUtil.COLORS.indexOf(color);
            if(blockstate2.is(ModBlocks.COLORED_COPPER_CHESTS.get(index))) {
                for (DyeColor color1 : ModUtil.COLORS) {
                    int index1 = ModUtil.COLORS.indexOf(color1);
                    if(blockstate1.is(ModBlocks.COLORED_COPPER_CHESTS.get(index1))) {
                        return index <= index1 ? blockstate2.getBlock() : blockstate1.getBlock();
                    }
                }
            }
        }

        for (DyeColor color : ModUtil.COLORS) {
            int index = ModUtil.COLORS.indexOf(color);
            if(blockstate2.is(ModBlocks.COLORED_WAXED_COPPER_CHESTS.get(index))) {
                for (DyeColor color1 : ModUtil.COLORS) {
                    int index1 = ModUtil.COLORS.indexOf(color1);
                    if(blockstate1.is(ModBlocks.COLORED_WAXED_COPPER_CHESTS.get(index1))) {
                        return index <= index1 ? blockstate2.getBlock() : blockstate1.getBlock();
                    }
                }
            }
        }
        return value;
    }

    @ModifyReturnValue(method = "unwaxBlock", at = @At("RETURN"))
    private static Optional<BlockState> unwaxBlock(Optional<BlockState> original, @Local(argsOnly = true) CopperChestBlock block, @Local(argsOnly = true) BlockState state) {
        return !block.isWaxed()
                ? Optional.of(state)
                : Optional.ofNullable(DataMapHooks.getBlockUnwaxed(state.getBlock())).map(block1 -> block1.withPropertiesOf(state));
    }
}
