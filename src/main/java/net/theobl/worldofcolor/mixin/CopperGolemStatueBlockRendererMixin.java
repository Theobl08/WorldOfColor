package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.blockentity.CopperGolemStatueBlockRenderer;
import net.minecraft.client.renderer.blockentity.state.CopperGolemStatueRenderState;
import net.minecraft.resources.Identifier;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ColoredCopperGolemStatueBlock;
import net.theobl.worldofcolor.block.ColoredWeatheringCopperGolemStatueBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CopperGolemStatueBlockRenderer.class)
public abstract class CopperGolemStatueBlockRendererMixin {
    @ModifyArg(method = "submit(Lnet/minecraft/client/renderer/blockentity/state/CopperGolemStatueRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/resources/Identifier;IIILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"),
            index = 3)
    private Identifier submitColoredCopperGolemStatue(Identifier value, @Local(argsOnly = true) CopperGolemStatueRenderState state) {
        if(state.blockState.getBlock() instanceof ColoredWeatheringCopperGolemStatueBlock block)
            return WorldOfColor.asResource("textures/entity/copper_golem/" + block.getColor().getName() + "_copper_golem.png");
        else if(state.blockState.getBlock() instanceof ColoredCopperGolemStatueBlock block)
            return WorldOfColor.asResource("textures/entity/copper_golem/" + block.getColor().getName() + "_copper_golem.png");
        else
            return value;
    }
}
