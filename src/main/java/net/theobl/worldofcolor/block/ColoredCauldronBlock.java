package net.theobl.worldofcolor.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.theobl.worldofcolor.util.ModUtil;

public class ColoredCauldronBlock extends CauldronBlock {
    private final DyeColor color;
    public ColoredCauldronBlock(DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    @Override
    public void handlePrecipitation(BlockState state, Level level, BlockPos pos, Biome.Precipitation precipitation) {
        if (shouldHandlePrecipitation(level, precipitation)) {
            if (precipitation == Biome.Precipitation.RAIN) {
                level.setBlockAndUpdate(pos, ModBlocks.COLORED_WATER_CAULDRONS.get(ModUtil.COLORS.indexOf(color)).get().defaultBlockState());
                level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            } else if (precipitation == Biome.Precipitation.SNOW) {
                level.setBlockAndUpdate(pos, ModBlocks.COLORED_POWDER_SNOW_CAULDRONS.get(ModUtil.COLORS.indexOf(color)).get().defaultBlockState());
                level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            }
        }
    }

    @Override
    protected void receiveStalactiteDrip(BlockState state, Level level, BlockPos pos, Fluid fluid) {
        if (fluid == Fluids.WATER) {
            BlockState blockstate = ModBlocks.COLORED_WATER_CAULDRONS.get(ModUtil.COLORS.indexOf(color)).get().defaultBlockState();
            level.setBlockAndUpdate(pos, blockstate);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockstate));
            level.levelEvent(1047, pos, 0);
        } else if (fluid == Fluids.LAVA) {
            BlockState blockstate1 = ModBlocks.COLORED_LAVA_CAULDRONS.get(ModUtil.COLORS.indexOf(color)).get().defaultBlockState();
            level.setBlockAndUpdate(pos, blockstate1);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockstate1));
            level.levelEvent(1046, pos, 0);
        }
    }
}
