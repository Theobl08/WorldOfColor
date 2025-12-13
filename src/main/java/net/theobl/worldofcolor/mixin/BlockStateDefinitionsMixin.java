package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.resources.model.BlockStateDefinitions;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.theobl.worldofcolor.client.resources.model.ModBlockStateDefinitions;
import net.theobl.worldofcolor.util.ModUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.function.Function;

@Mixin(BlockStateDefinitions.class)
public abstract class BlockStateDefinitionsMixin {
    @Inject(method = "definitionLocationToBlockStateMapper", at = @At("RETURN"))
    private static void addColoredItemFrames(CallbackInfoReturnable<Function<Identifier, StateDefinition<Block, BlockState>>> cir, @Local Map<Identifier, StateDefinition<Block, BlockState>> map) {
        for(DyeColor color : ModUtil.COLORS) {
            int index = ModUtil.COLORS.indexOf(color);
            map.put(ModBlockStateDefinitions.COLORED_ITEM_FRAME_LOCATION.get(index), ModBlockStateDefinitions.COLORED_ITEM_FRAME_FAKE_DEFINITION.get(index));
        }
    }
}
