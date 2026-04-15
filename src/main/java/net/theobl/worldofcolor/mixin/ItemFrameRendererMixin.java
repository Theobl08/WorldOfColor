package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.state.ItemFrameRenderState;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.worldofcolor.client.resources.model.ModBlockStateDefinitions;
import net.theobl.worldofcolor.entity.ModEntityType;
import net.theobl.worldofcolor.util.ModUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemFrameRenderer.class)
public abstract class ItemFrameRendererMixin {
    @Shadow
    @Final
    private BlockModelResolver blockModelResolver;

    @Definition(id = "updateForItemFrame", method = "Lnet/minecraft/client/renderer/block/BlockModelResolver;updateForItemFrame(Lnet/minecraft/client/renderer/block/BlockModelRenderState;ZZ)V")
    @Definition(id = "blockModelResolver", field = "Lnet/minecraft/client/renderer/entity/ItemFrameRenderer;blockModelResolver:Lnet/minecraft/client/renderer/block/BlockModelResolver;")
    @Expression("this.blockModelResolver.updateForItemFrame(?, ?, ?)")
    @WrapOperation(method = "extractRenderState(Lnet/minecraft/world/entity/decoration/ItemFrame;Lnet/minecraft/client/renderer/entity/state/ItemFrameRenderState;F)V",
            at = @At("MIXINEXTRAS:EXPRESSION"))
    private void getColoredItemFrameFakeBlockstate(BlockModelResolver instance, BlockModelRenderState renderState, boolean isGlowFrame, boolean map, Operation<Void> original, @Local(argsOnly = true) ItemFrame entity, @Local(argsOnly = true) ItemFrameRenderState state) {
        for(DyeColor color : ModUtil.COLORS) {
            if(entity.is(ModEntityType.COLORED_ITEM_FRAMES.get(color).get())) {
                BlockState fakeState = ModBlockStateDefinitions.getItemFrameFakeState(color, state.mapId != null);
                this.blockModelResolver.update(renderState, fakeState, ItemFrameRenderer.BLOCK_DISPLAY_CONTEXT);
                return;
            }
        }
        original.call(instance, renderState, isGlowFrame, map);
    }
}
