package net.theobl.worldofcolor.datagen;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.util.ModUtil;

import static net.minecraft.client.data.models.model.TextureMapping.getBlockTexture;

public class ColoredTextureMapping {
    public static TextureMapping cauldron(ResourceLocation texture, DyeColor color) {
        int index = ModUtil.COLORS.indexOf(color);
        Block cauldron = ModBlocks.COLORED_CAULDRONS.get(index).get();
        return new TextureMapping()
                .put(TextureSlot.PARTICLE, getBlockTexture(cauldron, "_side"))
                .put(TextureSlot.SIDE, getBlockTexture(cauldron, "_side"))
                .put(TextureSlot.TOP, getBlockTexture(cauldron, "_top"))
                .put(TextureSlot.BOTTOM, getBlockTexture(cauldron, "_bottom"))
                .put(TextureSlot.INSIDE, getBlockTexture(cauldron, "_inner"))
                .put(TextureSlot.CONTENT, texture);
    }

    public static TextureMapping cauldronEmpty(DyeColor color) {
        int index = ModUtil.COLORS.indexOf(color);
        Block cauldron = ModBlocks.COLORED_CAULDRONS.get(index).get();
        return new TextureMapping()
                .put(TextureSlot.PARTICLE, getBlockTexture(cauldron, "_side"))
                .put(TextureSlot.SIDE, getBlockTexture(cauldron, "_side"))
                .put(TextureSlot.TOP, getBlockTexture(cauldron, "_top"))
                .put(TextureSlot.BOTTOM, getBlockTexture(cauldron, "_bottom"))
                .put(TextureSlot.INSIDE, getBlockTexture(cauldron, "_inner"));
    }

    public static TextureMapping lightningRod(Block lightningRod) {
        return new TextureMapping()
                .put(TextureSlot.PARTICLE, getBlockTexture(lightningRod))
                .put(TextureSlot.TEXTURE, getBlockTexture(lightningRod));
    }
}
