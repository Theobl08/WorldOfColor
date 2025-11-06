package net.theobl.worldofcolor.tags;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.theobl.worldofcolor.WorldOfColor;

import java.util.ArrayList;
import java.util.List;

import static net.theobl.worldofcolor.util.ModUtil.*;

public class ModTags {
    public static class Blocks {
        public static final List<TagKey<Block>> COLORED_LOGS = createColoredBlockTags();

        private static List<TagKey<Block>> createColoredBlockTags() {
            List<TagKey<Block>> coloredTag = new ArrayList<>();
            for (DyeColor color : COLORS){
                TagKey<Block> tagKey = createTag(color.getName() + "_logs");
                coloredTag.add(tagKey);
            }
            return coloredTag;
        }

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(WorldOfColor.asResource(name));
        }
    }

    public static class Items {
        public static final List<TagKey<Item>> COLORED_LOGS = createColoredBlockTags();
        public static final TagKey<Item> CAULDRONS = createTag("cauldrons");

        private static List<TagKey<Item>> createColoredBlockTags() {
            List<TagKey<Item>> coloredTag = new ArrayList<>();
            for (DyeColor color : COLORS){
                TagKey<Item> tagKey = createTag(color.getName() + "_logs");
                coloredTag.add(tagKey);
            }
            return coloredTag;
        }

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(WorldOfColor.asResource(name));
        }
    }
}
