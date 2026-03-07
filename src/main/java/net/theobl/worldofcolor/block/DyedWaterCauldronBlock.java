package net.theobl.worldofcolor.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.theobl.worldofcolor.block.entity.DyedWaterCauldronBlockEntity;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class DyedWaterCauldronBlock extends AbstractCauldronBlock implements EntityBlock {
    public static final MapCodec<DyedWaterCauldronBlock> CODEC = simpleCodec(DyedWaterCauldronBlock::new);
    public static final int MIN_FILL_LEVEL = 1;
    public static final int MAX_FILL_LEVEL = 3;
    public static final IntegerProperty LEVEL = LayeredCauldronBlock.LEVEL;
    private static final int BASE_CONTENT_HEIGHT = 6;
    private static final double HEIGHT_PER_LEVEL = 3.0;
    private static final VoxelShape[] FILLED_SHAPES = Util.make(
            () -> Block.boxes(2, level -> Shapes.or(AbstractCauldronBlock.SHAPE, Block.column(12.0, 4.0, getPixelContentHeight(level + 1))))
    );

    public DyedWaterCauldronBlock(Properties properties) {
        super(properties, ColoredCauldronInteraction.DYED_WATER);
    }

    @Override
    protected MapCodec<DyedWaterCauldronBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.getValue(LEVEL) == MAX_FILL_LEVEL;
    }

    @Override
    protected double getContentHeight(BlockState state) {
        return getPixelContentHeight(state.getValue(LEVEL)) / 16.0;
    }

    private static double getPixelContentHeight(int level) {
        return BASE_CONTENT_HEIGHT + level * HEIGHT_PER_LEVEL;
    }

    @Override
    protected VoxelShape getEntityInsideCollisionShape(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
        return FILLED_SHAPES[state.getValue(LEVEL) - 1];
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        if (level instanceof ServerLevel serverlevel) {
            BlockPos blockpos = pos.immutable();
            effectApplier.runBefore(InsideBlockEffectType.EXTINGUISH, e -> {
                if (e.isOnFire() && e.mayInteract(serverlevel, blockpos)) {
                    lowerFillLevel(state, level, blockpos);
                }
            });
        }

        effectApplier.apply(InsideBlockEffectType.EXTINGUISH);
    }

    public static void lowerFillLevel(BlockState state, Level level, BlockPos pos) {
        int newLevel = state.getValue(LEVEL) - 1;
        BlockState newState = newLevel == 0 ? Blocks.CAULDRON.defaultBlockState() : state.setValue(LEVEL, newLevel);
        level.setBlockAndUpdate(pos, newState);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(newState));
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
        return state.getValue(LEVEL);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    public Item asItem() {
        return Items.CAULDRON;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DyedWaterCauldronBlockEntity(pos, state);
    }
}
