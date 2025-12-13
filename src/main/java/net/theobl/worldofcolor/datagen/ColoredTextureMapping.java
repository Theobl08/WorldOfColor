package net.theobl.worldofcolor.datagen;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.util.ModUtil;

import static net.minecraft.client.data.models.model.TextureMapping.getBlockTexture;

public class ColoredTextureMapping {
    public static TextureMapping cauldron(Identifier texture, DyeColor color) {
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

    public static TextureMapping flowerPot(Block flowerPot) {
        return new TextureMapping()
                .put(TextureSlot.PARTICLE, getBlockTexture(flowerPot))
                .put(ColoredTextureSlot.FLOWERPOT, getBlockTexture(flowerPot));
    }

    public static TextureMapping cauldronContent(Block particle) {
        return new TextureMapping().put(TextureSlot.CONTENT, TextureMapping.getBlockTexture(Blocks.WATER, "_still"))
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(particle, "_side"));
    }
}
