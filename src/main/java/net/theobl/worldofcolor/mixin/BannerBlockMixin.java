package net.theobl.worldofcolor.mixin;

import com.google.common.collect.Maps;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.theobl.worldofcolor.block.ColoredBannerBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(BannerBlock.class)
public class BannerBlockMixin {
    @Shadow
    @Final
    private static final Map<DyeColor, Block> BY_COLOR = Maps.newHashMap();

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void test(DyeColor p_49012_, BlockBehaviour.Properties p_49013_, CallbackInfo ci) {
        if(((Block) (Object) this) instanceof ColoredBannerBlock) {
            BY_COLOR.put(DyeColor.WHITE, Blocks.WHITE_BANNER);
        }
    }
}
