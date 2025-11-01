package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.state.ItemFrameRenderState;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.worldofcolor.client.resources.model.ModBlockStateDefinitions;
import net.theobl.worldofcolor.entity.ModEntityType;
import net.theobl.worldofcolor.util.ModUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemFrameRenderer.class)
public abstract class ItemFrameRendererMixin {
    @ModifyVariable(method = "submit(Lnet/minecraft/client/renderer/entity/state/ItemFrameRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V", at = @At("STORE"))
    private BlockState getColoredItemFrameFakeBlockstate(BlockState original, @Local(argsOnly = true)ItemFrameRenderState renderState) {
        for(DyeColor color : ModUtil.COLORS) {
            if(renderState.entityType == ModEntityType.COLORED_ITEM_FRAMES.get(ModUtil.COLORS.indexOf(color)).get())
                return ModBlockStateDefinitions.getItemFrameFakeState(color, renderState.mapId != null);
        }
        return original;
    }
}
