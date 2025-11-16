package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.blockentity.ShulkerBoxRenderer;
import net.minecraft.client.renderer.blockentity.state.ShulkerBoxRenderState;
import net.minecraft.client.resources.model.Material;
import net.theobl.worldofcolor.ModMaterial;
import net.theobl.worldofcolor.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ShulkerBoxRenderer.class)
public abstract class ShulkerBoxRendererMixin {
    @ModifyVariable(method = "submit(Lnet/minecraft/client/renderer/blockentity/state/ShulkerBoxRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V",
            at = @At(value = "STORE"))
    private Material rgbMaterial(Material original, @Local(argsOnly = true)ShulkerBoxRenderState renderState) {
        if(renderState.blockState.is(ModBlocks.RGB_SHULKER_BOX.get())) {
            return ModMaterial.RGB_SHULKER_LOCATION;
        }
        return original;
    }
}
