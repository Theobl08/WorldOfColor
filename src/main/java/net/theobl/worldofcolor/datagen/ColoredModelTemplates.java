package net.theobl.worldofcolor.datagen;

import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.theobl.worldofcolor.WorldOfColor;

public class ColoredModelTemplates {
    public static final ModelTemplate CAULDRON = ModelTemplates.create(
            "cauldron", TextureSlot.INSIDE, TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE
    );
    public static final ModelTemplate SLIME_BLOCK = ModelTemplates.create("slime_block", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate FLOWER_POT = ModelTemplates.create("flower_pot", TextureSlot.PARTICLE, ColoredTextureSlot.FLOWERPOT);
    public static final ModelTemplate DECORATED_POT = ModelTemplates.createItem("decorated_pot", TextureSlot.PARTICLE);
    public static final ModelTemplate ITEM_FRAME = ModelTemplates.create("template_item_frame", TextureSlot.PARTICLE, ColoredTextureSlot.WOOD, TextureSlot.BACK);
    public static final ModelTemplate ITEM_FRAME_MAP = ModelTemplates.create("template_item_frame_map", TextureSlot.PARTICLE, ColoredTextureSlot.WOOD, TextureSlot.BACK);
    public static final ModelTemplate SHULKER_BOX_ITEM = ModelTemplates.createItem(WorldOfColor.MODID + ":shulker_box_item", TextureSlot.TEXTURE);
    public static final ModelTemplate BED_ITEM = ModelTemplates.createItem(WorldOfColor.MODID + ":bed_item", TextureSlot.TEXTURE);
}
