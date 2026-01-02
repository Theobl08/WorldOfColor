package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.LoomScreen;
import net.minecraft.client.model.object.banner.BannerFlagModel;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.theobl.worldofcolor.client.renderer.gui.state.GuiColoredBannerResultRenderState;
import net.theobl.worldofcolor.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LoomScreen.class)
public class LoomScreenMixin {
    @WrapOperation(method = "renderBg", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;submitBannerPatternRenderState(Lnet/minecraft/client/model/object/banner/BannerFlagModel;Lnet/minecraft/world/item/DyeColor;Lnet/minecraft/world/level/block/entity/BannerPatternLayers;IIII)V"))
    private void submitRGBBannerPatternRenderState(GuiGraphics instance, BannerFlagModel flagModel, DyeColor baseColor, BannerPatternLayers resultBannerPatterns, int x0, int y0, int x1, int y1, Operation<Void> original,
                                                   @Local(ordinal = 0) Slot bannerSlot) {
        if(bannerSlot.hasItem() && bannerSlot.getItem().is(ModItems.RGB_BANNER)) {
            instance.submitPictureInPictureRenderState(new GuiColoredBannerResultRenderState(flagModel, baseColor, resultBannerPatterns, x0, y0, x1, y1, instance.peekScissorStack()));
        }
        else {
            original.call(instance, flagModel, baseColor, resultBannerPatterns, x0, y0, x1, y1);
        }
    }
}
