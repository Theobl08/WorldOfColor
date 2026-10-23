package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.LoomScreen;
import net.minecraft.client.model.object.banner.BannerFlagModel;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.theobl.worldofcolor.client.renderer.gui.state.GuiColoredBannerResultRenderState;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.util.ModUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LoomScreen.class)
public class LoomScreenMixin {
    @WrapOperation(method = "extractBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;bannerPattern(Lnet/minecraft/client/model/object/banner/BannerFlagModel;Lnet/minecraft/world/item/DyeColor;Lnet/minecraft/world/level/block/entity/BannerPatternLayers;IIII)V"))
    private void submitRGBBannerPatternRenderState(GuiGraphicsExtractor instance, BannerFlagModel flag, DyeColor baseColor, BannerPatternLayers resultBannerPatterns, int x0, int y0, int x1, int y1, Operation<Void> original,
                                                   @Local(name = "bannerSlot") Slot bannerSlot) {
        if(bannerSlot.hasItem() && bannerSlot.getItem().is(ModItems.RGB_BANNER)) {
            instance.submitPictureInPictureRenderState(new GuiColoredBannerResultRenderState(flag, baseColor, resultBannerPatterns, x0, y0, x1, y1, instance.peekScissorStack()));
        }
        else {
            original.call(instance, flag, baseColor, resultBannerPatterns, x0, y0, x1, y1);
        }
    }
    @Definition(id = "patterns", local = @Local(type = BannerPatternLayers.class, name = "patterns"))
    @Definition(id = "layers", method = "Lnet/minecraft/world/level/block/entity/BannerPatternLayers;layers()Ljava/util/List;")
    @Definition(id = "size", method = "Ljava/util/List;size()I")
    @Expression("patterns.layers().size() >= @(6)")
    @ModifyExpressionValue(method = "containerChanged", at = @At("MIXINEXTRAS:EXPRESSION"))
    private int rgbPatternDoesntCount(int original, @Local(name = "patterns") BannerPatternLayers patterns) {
        if(ModUtil.hasRGBPattern(patterns.layers())) {
            return original + 1;
        }
        return original;
    }
}
