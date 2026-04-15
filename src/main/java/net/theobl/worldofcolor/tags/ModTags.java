package net.theobl.worldofcolor.tags;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.theobl.worldofcolor.WorldOfColor;

import java.util.*;

import static net.theobl.worldofcolor.util.ModUtil.*;

public class ModTags {
    public static class Blocks {
        public static final Map<DyeColor, TagKey<Block>> COLORED_LOGS = createColoredBlockTags();

        private static Map<DyeColor, TagKey<Block>> createColoredBlockTags() {
            Map<DyeColor, TagKey<Block>> coloredTag = new LinkedHashMap<>();
            for (DyeColor color : COLORS){
                TagKey<Block> tagKey = createTag(color.getName() + "_logs");
                coloredTag.put(color, tagKey);
            }
            return coloredTag;
        }

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(WorldOfColor.asResource(name));
        }
    }

    public static class Items {
        public static final Map<DyeColor, TagKey<Item>> COLORED_LOGS = createColoredBlockTags();
        public static final TagKey<Item> CAULDRONS = createTag("cauldrons");

        private static Map<DyeColor, TagKey<Item>> createColoredBlockTags() {
            Map<DyeColor, TagKey<Item>> coloredTag = new LinkedHashMap<>();
            for (DyeColor color : COLORS){
                TagKey<Item> tagKey = createTag(color.getName() + "_logs");
                coloredTag.put(color, tagKey);
            }
            return coloredTag;
        }

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(WorldOfColor.asResource(name));
        }
    }
}
