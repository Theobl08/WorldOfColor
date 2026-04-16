package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.state.ChestRenderState;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.theobl.worldofcolor.client.renderer.ModSpriteId;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.util.ModUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChestRenderer.class)
public abstract class ChestRendererMixin {
    @ModifyReturnValue(method = "getCustomSprite", at = @At("RETURN"))
    private SpriteId getCustomSprite(SpriteId original, @Local(argsOnly = true) BlockEntity blockEntity, @Local(argsOnly = true) ChestRenderState renderState) {
        for (DyeColor color : ModUtil.COLORS) {
            if(blockEntity.getBlockState().is(ModBlocks.COLORED_COPPER_CHESTS.get(color))
                    || blockEntity.getBlockState().is(ModBlocks.COLORED_WAXED_COPPER_CHESTS.get(color))) {
                return switch (renderState.type) {
                    case SINGLE -> ModSpriteId.COPPER_CHEST_LOCATION.get(color);
                    case LEFT -> ModSpriteId.COPPER_CHEST_LOCATION_LEFT.get(color);
                    case RIGHT -> ModSpriteId.COPPER_CHEST_LOCATION_RIGHT.get(color);
                };
            }
        }
        return original;
    }
}
