package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.state.ChestRenderState;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.theobl.worldofcolor.ModMaterial;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.util.ModUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChestRenderer.class)
public abstract class ChestRendererMixin {
    @ModifyReturnValue(method = "getCustomMaterial", at = @At("RETURN"))
    private Material getCustomMaterial(Material original, @Local(argsOnly = true) BlockEntity blockEntity, @Local(argsOnly = true) ChestRenderState renderState) {
        for (DyeColor color : ModUtil.COLORS) {
            int index = ModUtil.COLORS.indexOf(color);
            if(blockEntity.getBlockState().is(ModBlocks.COLORED_COPPER_CHESTS.get(index))
                    || blockEntity.getBlockState().is(ModBlocks.COLORED_WAXED_COPPER_CHESTS.get(index))) {
                return switch (renderState.type) {
                    case SINGLE -> ModMaterial.COPPER_CHEST_LOCATION.get(index);
                    case LEFT -> ModMaterial.COPPER_CHEST_LOCATION_LEFT.get(index);
                    case RIGHT -> ModMaterial.COPPER_CHEST_LOCATION_RIGHT.get(index);
                };
            }
        }
        return original;
    }
}
