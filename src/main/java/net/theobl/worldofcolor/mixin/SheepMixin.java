package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.color.ColorLerper;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ColorLerper.class)
public class SheepMixin {
    @ModifyReturnValue(method = "getModifiedColor", at = @At(value = "RETURN"))
    private static int bedrockEditionSheepColor(int original, @Local(argsOnly = true) DyeColor dyeColor, @Local(argsOnly = true) float brightness) {
        if(brightness < 1.0F)
            return dyeColor.getTextureDiffuseColor();
        return original;
    }
}
