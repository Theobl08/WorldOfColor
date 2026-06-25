package net.theobl.worldofcolor.tags;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColorCollection;
import net.theobl.worldofcolor.WorldOfColor;

public class ModTags {
    public static class Blocks {
        public static final ColorCollection<TagKey<Block>> COLORED_LOGS = ColorCollection.NAMES
                .map(color -> createTag(color + "_logs"));

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(WorldOfColor.asResource(name));
        }
    }

    public static class Items {
        public static final ColorCollection<TagKey<Item>> COLORED_LOGS = ColorCollection.NAMES
                .map(color -> createTag(color + "_logs"));
        /**
         * Block tag equivalent is {@link BlockTags#CAULDRONS}
         */
        public static final TagKey<Item> CAULDRONS = createTag("cauldrons");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(WorldOfColor.asResource(name));
        }
    }
}
