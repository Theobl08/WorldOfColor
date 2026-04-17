package net.theobl.worldofcolor.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
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
        ModItems.ITEMS.getEntries().forEach(this::lang);
        ModEntityType.ENTITY_TYPES.getEntries().forEach(this::lang);

        ModTags.Blocks.COLORED_LOGS.values().forEach(this::lang);

        ModTags.Items.COLORED_LOGS.values().forEach(this::lang);

        ModBlocks.COLORED_POTATO_PEELS_BLOCK.forEach((color, block) -> addBlock(block, "Block of " + colorLang(color) + " Potato Peels"));
        addBlock(ModBlocks.RGB_CANDLE_CAKE, "Cake with RGB Candle");
    }

    /**
     * A method to capitalize a string and remove anything what's between it.
     * <a href="https://stackoverflow.com/questions/1892765/how-to-capitalize-the-first-character-of-each-word-in-a-string">Source</a>
     * @param string the string you want to capitalize.
     * @return a capitalized string.
     */
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

    private static String colorLang(DyeColor color) {
        return capitalizeString(color.getName().replace("_", ""));
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
                .replace("chest_boat","boat_with_chest")
                .replace("_"," "))
                .replace("Rgb", "RGB")
                .replace(" With "," with ");

        add(descriptionId, value);
    }

    private <T> void lang(Supplier<T> key) {
        lang(key.get());
    }

    // Override the base method to catch the Exception, to be consistent with the other data providers, which doesn't throw on duplicate value (either skip or replace)
    @Override
    public void add(String key, Component value) {
        try {
            super.add(key, value);
        } catch (Exception _) {}
    }
}
