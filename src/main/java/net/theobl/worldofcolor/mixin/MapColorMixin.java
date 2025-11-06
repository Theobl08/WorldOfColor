package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MapColor.class)
public abstract class MapColorMixin {
    @ModifyVariable(method = "<init>", argsOnly = true, ordinal = 1, at = @At("HEAD"), require = 0)
    private static int correctDyeColorMapColor(int original, @Local(argsOnly = true, ordinal = 1) int col) {
        return switch (col) {
            case 16777215 -> 16383998; // white
            case 10066329 -> 10329495; // light gray
            case 5000268 -> 4673362; // gray
            case 1644825 -> 1908001; // black
            case 6704179 -> 8606770; // brown
            case 10040115 -> 11546150; // red
            case 14188339 -> 16351261; // orange
            case 15066419 -> 16701501; // yellow
            case 8375321 -> 8439583; // lime
            case 6717235 -> 6192150; // green
            case 5013401 -> 1481884; // cyan
            case 6724056 -> 3847130; // light blue
            case 3361970 -> 3949738; // blue
            case 8339378 -> 8991416; // purple
            case 11685080 -> 13061821; // magenta
            case 15892389 -> 15961002; // pink
            default -> original; // others
        };
    }
}
