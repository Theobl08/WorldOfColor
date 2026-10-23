package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.inventory.LoomMenu;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.theobl.worldofcolor.util.ModUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LoomMenu.class)
public class LoomMenuMixin {
    @Definition(id = "patterns", local = @Local(type = BannerPatternLayers.class, name = "patterns"))
    @Definition(id = "layers", method = "Lnet/minecraft/world/level/block/entity/BannerPatternLayers;layers()Ljava/util/List;")
    @Definition(id = "size", method = "Ljava/util/List;size()I")
    @Expression("patterns.layers().size() >= @(6)")
    @ModifyExpressionValue(method = "slotsChanged", at = @At("MIXINEXTRAS:EXPRESSION"))
    private int rgbPatternDoesntCount(int original, @Local(name = "patterns") BannerPatternLayers patterns) {
        if(ModUtil.hasRGBPattern(patterns.layers())) {
            return original + 1;
        }
        return original;
    }
}
