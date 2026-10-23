package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.util.ModUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.function.Consumer;

@Mixin(BannerPatternLayers.class)
public abstract class BannerPatternLayersMixin {
    @Shadow
    @Final
    private List<BannerPatternLayers.Layer> layers;

    @Definition(id = "min", method = "Ljava/lang/Math;min(II)I")
    @Definition(id = "layers", method = "Lnet/minecraft/world/level/block/entity/BannerPatternLayers;layers()Ljava/util/List;")
    @Definition(id = "size", method = "Ljava/util/List;size()I")
    @Expression("min(this.layers().size(), @(6))")
    @ModifyExpressionValue(method = "addToTooltip", at = @At("MIXINEXTRAS:EXPRESSION"))
    private int addToTooltip(int original) {
        if(ModUtil.hasRGBPattern(layers)) {
            return original + 1;
        }
        return original;
    }

    @Definition(id = "accept", method = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V")
    @Definition(id = "description", method = "Lnet/minecraft/world/level/block/entity/BannerPatternLayers$Layer;description()Lnet/minecraft/network/chat/MutableComponent;")
    @Definition(id = "withStyle", method = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;")
    @Expression("?.accept(?.description().withStyle(?))")
    @WrapOperation(method = "addToTooltip", at = @At("MIXINEXTRAS:EXPRESSION"))
    private <T> void test(Consumer<T> instance, T t, Operation<Void> original, @Local(name = "i") int i) {
        Identifier patternId = layers.get(i).pattern().key().identifier();
        if(patternId.getNamespace().equals(WorldOfColor.MODID) && patternId.getPath().equals("rgb")) {
            return;
        }
        original.call(instance, t);
    }
}
