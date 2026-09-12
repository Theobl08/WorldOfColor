package net.theobl.worldofcolor.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.theobl.worldofcolor.block.entity.DyedWaterLiquidBlockEntity;
import org.jspecify.annotations.Nullable;

public class DyedWaterLiquidBlock extends LiquidBlock implements EntityBlock {
    public DyedWaterLiquidBlock(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean movedByPiston) {
        super.affectNeighborsAfterRemoval(state, level, pos, movedByPiston);
    }

    @Override
    public ItemStack pickupBlock(@Nullable LivingEntity user, LevelAccessor level, BlockPos pos, BlockState state) {
        if (state.getValue(LEVEL) == 0) {
            ItemStack result = new ItemStack(this.fluid.getBucket());
            if(level.getBlockEntity(pos) instanceof DyedWaterLiquidBlockEntity blockEntity) {
                result.set(DataComponents.DYED_COLOR, blockEntity.getColor());
            }
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), UPDATE_NEIGHBORS | UPDATE_CLIENTS | UPDATE_IMMEDIATE);
            return result;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new DyedWaterLiquidBlockEntity(worldPosition, blockState);
    }
}
