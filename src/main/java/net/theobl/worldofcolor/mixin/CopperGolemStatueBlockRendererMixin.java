package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.CopperGolemStatueBlockRenderer;
import net.minecraft.client.renderer.blockentity.state.CopperGolemStatueRenderState;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ColoredCopperGolemStatueBlock;
import net.theobl.worldofcolor.block.ColoredWeatheringCopperGolemStatueBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CopperGolemStatueBlockRenderer.class)
public abstract class CopperGolemStatueBlockRendererMixin {
    @ModifyVariable(method = "submit(Lnet/minecraft/client/renderer/blockentity/state/CopperGolemStatueRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V",
            at = @At("STORE"))
    private RenderType submitColoredCopperGolemStatue(RenderType value, @Local(argsOnly = true) CopperGolemStatueRenderState renderState) {
        if(renderState.blockState.getBlock() instanceof ColoredWeatheringCopperGolemStatueBlock block)
            return RenderType.entityCutoutNoCull(WorldOfColor.asResource("textures/entity/copper_golem/" + block.getColor().getName() + "_copper_golem.png"));
        else if(renderState.blockState.getBlock() instanceof ColoredCopperGolemStatueBlock block)
            return RenderType.entityCutoutNoCull(WorldOfColor.asResource("textures/entity/copper_golem/" + block.getColor().getName() + "_copper_golem.png"));
        else
            return value;
    }
}
