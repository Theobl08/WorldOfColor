package net.theobl.worldofcolor.datagen;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;

import static net.theobl.worldofcolor.datagen.ColoredModelTemplates.SLIME_BLOCK;

public final class ColoredTexturedModel {
    public static final TexturedModel.Provider SLIME_BLOCK = TexturedModel.createDefault(TextureMapping::defaultTexture, ColoredModelTemplates.SLIME_BLOCK);
    public static final TexturedModel.Provider FLOWER_POT = TexturedModel.createDefault(ColoredTextureMapping::flowerPot, ColoredModelTemplates.FLOWER_POT);
}
