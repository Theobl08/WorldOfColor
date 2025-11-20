package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.client.renderer.blockentity.state.BedRenderState;
import net.minecraft.client.resources.model.Material;
import net.theobl.worldofcolor.ModMaterial;
import net.theobl.worldofcolor.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BedRenderer.class)
public abstract class BedRendererMixin {
    @ModifyVariable(method = "submit(Lnet/minecraft/client/renderer/blockentity/state/BedRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V",
            at = @At("STORE"))
    private Material rgbBedMaterial(Material original, @Local(argsOnly = true)BedRenderState renderState) {
        if(renderState.blockState.is(ModBlocks.RGB_BED.get())) {
            return ModMaterial.RGB_BED_TEXTURE;
        }
        return original;
    }
}
