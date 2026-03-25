package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TerrainParticle.class)
public class TerrainParticleMixin {
    @Definition(id = "tintSource", local = @Local(type = BlockTintSource.class))
    @Expression("tintSource != null")
    @ModifyExpressionValue(method = "<init>(Lnet/minecraft/client/multiplayer/ClientLevel;DDDDDDLnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)V",
            at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean areBreakingParticlesTinted(boolean original, @Local(argsOnly = true) BlockState blockState) {
        if(blockState.getBlock() instanceof LayeredCauldronBlock) {
            return false;
        }
        else {
            return original;
        }
    }
}
