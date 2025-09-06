package net.theobl.worldofcolor.datagen;

import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;

public class VanillaModelTemplates {
    public static final ModelTemplate CAULDRON = ModelTemplates.create(
            "cauldron", TextureSlot.INSIDE, TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE
    );
    public static final ModelTemplate LIGHTNING_ROD = ModelTemplates.create("lightning_rod", TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    public static final ModelTemplate SLIME_BLOCK = ModelTemplates.create("slime_block", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
}
