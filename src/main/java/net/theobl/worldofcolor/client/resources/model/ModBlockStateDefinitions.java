package net.theobl.worldofcolor.client.resources.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.util.ModUtil;

import java.util.ArrayList;
import java.util.List;

public class ModBlockStateDefinitions {
    public static final List<StateDefinition<Block, BlockState>> COLORED_ITEM_FRAME_FAKE_DEFINITION = createItemFrameFakeState();
    public static final List<ResourceLocation> COLORED_ITEM_FRAME_LOCATION = createItemFrameLocation();

    private static List<StateDefinition<Block, BlockState>> createItemFrameFakeState() {
        List<StateDefinition<Block, BlockState>> list = new ArrayList<>();
        for(DyeColor color : ModUtil.COLORS)
            list.add(new StateDefinition.Builder<Block, BlockState>(Blocks.AIR).add(BlockStateProperties.MAP).create(Block::defaultBlockState, BlockState::new));
        return list;
    }
    private static List<ResourceLocation> createItemFrameLocation() {
        List<ResourceLocation> list = new ArrayList<>();
        for(DyeColor color : ModUtil.COLORS)
            list.add(WorldOfColor.asResource(color.getName() + "_item_frame"));
        return list;
    }

    public static BlockState getItemFrameFakeState(DyeColor color, boolean map) {
        return COLORED_ITEM_FRAME_FAKE_DEFINITION.get(ModUtil.COLORS.indexOf(color)).any().setValue(BlockStateProperties.MAP, map);
    }
}
