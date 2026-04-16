package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.theobl.worldofcolor.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxBlockMixin {
    @ModifyVariable(method = "playerWillDestroy", at = @At("STORE"), name = "itemStack")
    private ItemStack rgbShulkerBox(ItemStack itemStack, @Local(name = "blockEntity") BlockEntity blockEntity) {
        if(blockEntity.getBlockState().is(ModBlocks.RGB_SHULKER_BOX.get())) {
            return new ItemStack(ModBlocks.RGB_SHULKER_BOX);
        }
        return itemStack;
    }
}
