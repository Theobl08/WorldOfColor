package net.theobl.worldofcolor.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.entity.ModEntityType;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.tags.ModTags;
import org.jetbrains.annotations.NotNull;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, WorldOfColor.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.worldofcolor", "World of Color");
        for (DeferredHolder<Block, ? extends Block> block : ModBlocks.BLOCKS.getEntries()) {
            if(block.get() instanceof WallSignBlock || block.get() instanceof WallHangingSignBlock)
                continue;
            add(block.get(), capitalizeString(filterBlockLang(block.get())));
        }

        ModItems.COLORED_BOATS.forEach(item -> add(item.asItem(), capitalizeString(filterItemLang(item))));
        ModItems.COLORED_CHEST_BOATS.forEach(item -> add(item.asItem(), filterChestBoatLang(item)));
        ModEntityType.COLORED_BOATS.forEach(boat -> add(boat.get(), capitalizeString(filterEntityTypeLang(boat.get()))));
        ModEntityType.COLORED_CHEST_BOATS.forEach(boat -> add(boat.get(), filterChestBoatLang(boat.get())));

        for (TagKey<Block> tag : ModTags.Blocks.COLORED_LOGS) {
            add(tag, capitalizeString(filterTagLang(tag)));
        }

        for (TagKey<Item> tag : ModTags.Items.COLORED_LOGS) {
            add(tag, capitalizeString(filterTagLang(tag)));
        }

//        for (Field field : BlockTags.class.getDeclaredFields()) {
//            TagKey<Block> tag = null;
//            try {
//                tag = (TagKey<Block>) field.get(null);
//            } catch (IllegalAccessException e) {
//                throw new IllegalStateException(BlockTags.class.getName() + " is missing tag name: " + field.getName());
//            }
//            add(tag, capitalizeString(filterTagLang(tag)));
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

    private static @NotNull String filterBlockLang(@NotNull Block key) {
        return key.getDescriptionId().replace("block.worldofcolor.","").replace("_"," ");
    }

    private static @NotNull String filterTagLang(@NotNull TagKey<?> key) {
        return key.location().getPath().replace("_"," ");
    }

    private static @NotNull String filterItemLang(ItemLike key){
        return key.asItem().getDescriptionId().replace("item.worldofcolor.","").replace("_"," ")
                .replace("block.worldofcolor.","");
    }

    private static String filterChestBoatLang(ItemLike key){
        String type = key.asItem().getDescriptionId()
                .replace("item.worldofcolor.","")
                .replace("chest_boat","")
                .replace("_"," ");

        return capitalizeString(type) + "Boat with Chest";
    }

    private static String filterChestBoatLang(EntityType<?> key){
        String type = key.getDescriptionId()
                .replace("entity.worldofcolor.","")
                .replace("chest_boat","")
                .replace("_"," ");

        return capitalizeString(type) + "Boat with Chest";
    }

    private static String filterEntityTypeLang(EntityType<?> key){
        return key.getDescriptionId().replace("entity.worldofcolor.","").replace("_"," ");
    }
}
