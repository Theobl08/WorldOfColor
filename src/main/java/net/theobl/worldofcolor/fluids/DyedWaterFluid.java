package net.theobl.worldofcolor.fluids;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.theobl.worldofcolor.block.entity.DyedWaterLiquidBlockEntity;
import org.jspecify.annotations.NullMarked;

import java.util.Map;

@NullMarked
public abstract class DyedWaterFluid extends BaseFlowingFluid {
    protected DyedWaterFluid(Properties properties) {
        super(properties);
    }

    @Override
    protected void spread(ServerLevel level, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!fluidState.isEmpty()) {
            BlockPos belowPos = pos.below();
            BlockState belowState = level.getBlockState(belowPos);
            FluidState belowFluid = belowState.getFluidState();
            if (this.canMaybePassThrough(level, pos, state, Direction.DOWN, belowPos, belowState, belowFluid)) {
                FluidState newBelowFluid = this.getNewLiquid(level, belowPos, belowState);
                Fluid newBelowFluidType = newBelowFluid.getType();
                if (belowFluid.canBeReplacedWith(level, belowPos, newBelowFluidType, Direction.DOWN)
                        && canHoldSpecificFluid(level, belowPos, belowState, newBelowFluidType)) {
                    this.spreadTo(level, belowPos, belowState, Direction.DOWN, newBelowFluid);
                    if (this.sourceNeighborCount(level, pos) >= 3) {
                        this.spreadToSides(level, pos, fluidState, state);
                    }

                    if(level.getBlockEntity(pos) instanceof DyedWaterLiquidBlockEntity blockEntity
                            && level.getBlockEntity(belowPos) instanceof DyedWaterLiquidBlockEntity belowBlockEntity
                            && belowBlockEntity.getColor() == DyedWaterLiquidBlockEntity.NORMAL_WATER_COLOR) {
                        belowBlockEntity.setColor(blockEntity.getColor());
                    }

                    return;
                }
            }

            if (fluidState.isSource() || !this.isWaterHole(level, pos, state, belowPos, belowState)) {
                this.spreadToSides(level, pos, fluidState, state);
            }
        }
    }

    private void spreadToSides(ServerLevel level, BlockPos pos, FluidState fluidState, BlockState state) {
        int neighbor = fluidState.getAmount() - this.getDropOff(level);
        if (fluidState.getValue(FALLING)) {
            neighbor = 7;
        }

        if (neighbor > 0) {
            Map<Direction, FluidState> spreads = this.getSpread(level, pos, state);

            for (Map.Entry<Direction, FluidState> entry : spreads.entrySet()) {
                Direction spread = entry.getKey();
                FluidState newNeighborFluid = entry.getValue();
                BlockPos neighborPos = pos.relative(spread);
                this.spreadTo(level, neighborPos, level.getBlockState(neighborPos), spread, newNeighborFluid);

                if(level.getBlockEntity(pos) instanceof DyedWaterLiquidBlockEntity blockEntity
                        && level.getBlockEntity(neighborPos) instanceof DyedWaterLiquidBlockEntity neighborBlockEntity
                        && neighborBlockEntity.getColor() == DyedWaterLiquidBlockEntity.NORMAL_WATER_COLOR) {
                    neighborBlockEntity.setColor(blockEntity.getColor());
                }
            }
        }
    }

    private static boolean isSameColor(FluidState fluidState, Fluid other, BlockGetter level, BlockPos pos, BlockPos relativePos) {
        return fluidState.getType().isSame(other)
                && level.getBlockEntity(pos) instanceof DyedWaterLiquidBlockEntity blockEntity
                && level.getBlockEntity(relativePos) instanceof DyedWaterLiquidBlockEntity relativeBlockEntity
                && blockEntity.getColor() == relativeBlockEntity.getColor();
    }

    public static class Flowing extends DyedWaterFluid {
        public Flowing(Properties properties) {
            super(properties);
            registerDefaultState(getStateDefinition().any().setValue(LEVEL, 7));
        }

        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Source extends DyedWaterFluid {
        public Source(Properties properties) {
            super(properties);
        }

        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }
}
