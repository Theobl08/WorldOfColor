package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.util.ModUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LightningBolt.class)
public abstract class LightningBoltMixin {
    @ModifyExpressionValue(method = "powerLightningRod", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private boolean powerColoredLightningRod(boolean original, @Local BlockState blockState) {
        return original || ModUtil.isColoredBlock(blockState, ModBlocks.COLORED_LIGHTNING_RODS);
    }

    @ModifyExpressionValue(method = "clearCopperOnLightningStrike", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    private static boolean clearColoredCopperOnLightningStrike(boolean original, @Local BlockState blockState) {
        return original || ModUtil.isColoredBlock(blockState, ModBlocks.COLORED_LIGHTNING_RODS);
    }
}
