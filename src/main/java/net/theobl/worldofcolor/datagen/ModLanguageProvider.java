package net.theobl.worldofcolor.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.entity.ModEntityType;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.tags.ModTags;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, WorldOfColor.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.worldofcolor", "World of Color");
        ModBlocks.BLOCKS.getEntries().forEach(this::lang);
        ModItems.ITEMS.getEntries().stream().filter(item -> !(item.get() instanceof BlockItem)).forEach(this::lang);

//        ModItems.COLORED_BOATS.forEach(this::lang);
//        ModItems.COLORED_CHEST_BOATS.forEach(this::lang);
//        ModItems.COLORED_ITEM_FRAMES.forEach(this::lang);
        ModEntityType.COLORED_BOATS.forEach(this::lang);
        ModEntityType.COLORED_CHEST_BOATS.forEach(this::lang);
        ModEntityType.COLORED_ITEM_FRAMES.forEach(this::lang);

        ModTags.Blocks.COLORED_LOGS.forEach(this::lang);

        ModTags.Items.COLORED_LOGS.forEach(this::lang);

//        for (Field field : BlockTags.class.getDeclaredFields()) {
//            TagKey<Block> tag = null;
//            try {
//                tag = (TagKey<Block>) field.get(null);
//            } catch (IllegalAccessException e) {
//                throw new IllegalStateException(BlockTags.class.getName() + " is missing tag name: " + field.getName());
//            }
//            lang(tag);
//        }
    }

    private static @NotNull String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++){
            if(!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '_') {
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    private <T> void lang(T key) {
        String descriptionId = switch (key) {
            case Block block -> block.getDescriptionId();
            case Item item -> item.getDescriptionId();
            case EntityType<?> entityType -> entityType.getDescriptionId();
            case TagKey<?> tagKey -> Tags.getTagTranslationKey(tagKey);
            case null, default -> "";
        };

        String value = capitalizeString(descriptionId.replace("block.worldofcolor.","")
                .replace("item.worldofcolor.","")
                .replace("entity.worldofcolor.","")
                .replace("tag.", "")
                .replace("chest_boat","")
                .replace("_"," "));

        if(descriptionId.contains("chest_boat")) {
            value += "Boat with Chest";
        }
        add(descriptionId, value);
    }

    private <T> void lang(Supplier<T> key) {
        lang(key.get());
    }
}
