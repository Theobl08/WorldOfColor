package net.theobl.worldofcolor.client.resources.model;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModBlockStateDefinitions {
    public static final Map<DyeColor, StateDefinition<Block, BlockState>> COLORED_ITEM_FRAME_FAKE_DEFINITION = createItemFrameFakeState();
    public static final Map<DyeColor, Identifier> COLORED_ITEM_FRAME_LOCATION = createItemFrameLocation();

    private static Map<DyeColor, StateDefinition<Block, BlockState>> createItemFrameFakeState() {
        Map<DyeColor, StateDefinition<Block, BlockState>> list = new LinkedHashMap<>();
        for(DyeColor color : ModUtil.COLORS)
            list.put(color, new StateDefinition.Builder<Block, BlockState>(Blocks.AIR).add(BlockStateProperties.MAP).create(Block::defaultBlockState, BlockState::new));
        return list;
    }
    private static Map<DyeColor, Identifier> createItemFrameLocation() {
        Map<DyeColor, Identifier> list = new LinkedHashMap<>();
        for(DyeColor color : ModUtil.COLORS)
            list.put(color, WorldOfColor.asResource(color.getName() + "_item_frame"));
        return list;
    }

    public static BlockState getItemFrameFakeState(DyeColor color, boolean map) {
        return COLORED_ITEM_FRAME_FAKE_DEFINITION.get(color).any().setValue(BlockStateProperties.MAP, map);
    }
}
