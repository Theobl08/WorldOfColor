package net.theobl.worldofcolor.client.resources.model;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ColorCollection;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModBlockStateDefinitions {
    public static final ColorCollection<StateDefinition<Block, BlockState>> COLORED_ITEM_FRAME_FAKE_DEFINITION = ColorCollection.NAMES
            .map(color -> new StateDefinition.Builder<Block, BlockState>(Blocks.AIR).add(BlockStateProperties.MAP).create(Block::defaultBlockState, BlockState::new));
    public static final ColorCollection<Identifier> COLORED_ITEM_FRAME_LOCATION = ColorCollection.NAMES
            .map(color -> WorldOfColor.asResource(color + "_item_frame"));

    public static BlockState getItemFrameFakeState(DyeColor color, boolean map) {
        return COLORED_ITEM_FRAME_FAKE_DEFINITION.pick(color).any().setValue(BlockStateProperties.MAP, map);
    }
}
