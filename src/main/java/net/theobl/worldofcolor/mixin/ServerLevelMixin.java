package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.theobl.worldofcolor.entity.ModPoiTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
    @ModifyExpressionValue(method = "lambda$findLightningRod$5", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Holder;is(Lnet/minecraft/resources/ResourceKey;)Z"))
    private static boolean test(boolean original, @Local(argsOnly = true) Holder<PoiType> poiType) {
        return original || poiType.is(Objects.requireNonNull(ModPoiTypes.COLORED_LIGHTNING_RODS.getKey()));
    }
}