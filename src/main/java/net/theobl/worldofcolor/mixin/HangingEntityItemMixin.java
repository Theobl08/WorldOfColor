package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.HangingEntityItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.theobl.worldofcolor.entity.ColoredItemFrame;
import net.theobl.worldofcolor.entity.ModEntityType;
import net.theobl.worldofcolor.util.ModUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HangingEntityItem.class)
public abstract class HangingEntityItemMixin {
    @Final
    @Shadow
    private EntityType<? extends HangingEntity> type;

    @Definition(id = "type", field = "Lnet/minecraft/world/item/HangingEntityItem;type:Lnet/minecraft/world/entity/EntityType;")
    @Definition(id = "GLOW_ITEM_FRAME", field = "Lnet/minecraft/world/entity/EntityType;GLOW_ITEM_FRAME:Lnet/minecraft/world/entity/EntityType;")
    @Expression("this.type != GLOW_ITEM_FRAME")
    @ModifyExpressionValue(method = "useOn", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private boolean checkIfColoredItemFrame(boolean original) {
        for (DyeColor color : ModUtil.COLORS) {
            int index = ModUtil.COLORS.indexOf(color);
            if(this.type == ModEntityType.COLORED_ITEM_FRAMES.get(index).get()) {
                return false; // We want to continue on the "useOn" method, and when this boolean is true, the method is over
            }
        }
        return original;
    }

    @Definition(id = "hangingentity", local = @Local(type = HangingEntity.class))
    @Definition(id = "GlowItemFrame", type = GlowItemFrame.class)
    @Expression("hangingentity = new GlowItemFrame(?, ?, ?)")
    @ModifyVariable(method = "useOn", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER))
    private HangingEntity placeColoredItemFrame(HangingEntity original, @Local(argsOnly = true) UseOnContext context, @Local(ordinal = 1) BlockPos blockPos1, @Local Direction direction) {
        Level level = context.getLevel();
        for (DyeColor color : ModUtil.COLORS) {
            int index = ModUtil.COLORS.indexOf(color);
            if(this.type == ModEntityType.COLORED_ITEM_FRAMES.get(index).get()) {
                return new ColoredItemFrame(level, blockPos1, direction, color);
            }
        }
        return original;
    }
}
