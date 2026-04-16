package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.blockentity.ShulkerBoxRenderer;
import net.minecraft.client.renderer.blockentity.state.ShulkerBoxRenderState;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.theobl.worldofcolor.client.renderer.ModSpriteId;
import net.theobl.worldofcolor.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ShulkerBoxRenderer.class)
public abstract class ShulkerBoxRendererMixin {
    @ModifyVariable(method = "submit(Lnet/minecraft/client/renderer/blockentity/state/ShulkerBoxRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
            at = @At(value = "STORE"), name = "sprite")
    private SpriteId rgbSprite(SpriteId sprite, @Local(argsOnly = true)ShulkerBoxRenderState state) {
        if(state.blockState.is(ModBlocks.RGB_SHULKER_BOX.get())) {
            return ModSpriteId.RGB_SHULKER_LOCATION;
        }
        return sprite;
    }
}
