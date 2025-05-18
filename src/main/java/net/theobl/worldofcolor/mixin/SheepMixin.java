package net.theobl.worldofcolor.mixin;

import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sheep.class)
public class SheepMixin {
    @Inject(method = "createSheepColor", at = @At(value = "RETURN"), cancellable = true)
    private static void bedrockEditionSheepColor(DyeColor dyeColor, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(dyeColor.getTextureDiffuseColor());
    }
}
