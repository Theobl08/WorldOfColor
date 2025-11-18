package net.theobl.worldofcolor.datagen;

import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.core.Direction;
import net.theobl.worldofcolor.WorldOfColor;

public class VanillaModelTemplates {
    public static final ModelTemplate CAULDRON = ModelTemplates.create(
            "cauldron", TextureSlot.INSIDE, TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE
    );
    public static final ModelTemplate SLIME_BLOCK = ModelTemplates.create("slime_block", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate FLOWER_POT = ModelTemplates.create("flower_pot", TextureSlot.PARTICLE, ColoredTextureSlot.FLOWERPOT);
    public static final ModelTemplate DECORATED_POT = ModelTemplates.createItem("decorated_pot", TextureSlot.PARTICLE);
    public static final ModelTemplate ITEM_FRAME = ModelTemplates.create("template_item_frame", TextureSlot.PARTICLE, ColoredTextureSlot.WOOD, TextureSlot.BACK);
    public static final ModelTemplate ITEM_FRAME_MAP = ModelTemplates.create("template_item_frame_map", TextureSlot.PARTICLE, ColoredTextureSlot.WOOD, TextureSlot.BACK);
    public static final ModelTemplate SHULKER_BOX_ITEM = ModelTemplates.createItem(WorldOfColor.MODID + ":shulker_box_item", TextureSlot.TEXTURE);

    public static ModelTemplate cauldronLevelX(int level) {
        final float y;
        if(level == 1)
            y = 9;
        else if(level == 2)
            y = 12;
        else
            y = 15;

        return ModelTemplates.create(TextureSlot.CONTENT, TextureSlot.PARTICLE).extend()
                .renderType("translucent").ambientOcclusion(false)
                .element(elementBuilder -> elementBuilder
                        .from(2, 4, 2)
                        .to(14, y, 14)
                        .face(Direction.UP, faceBuilder -> faceBuilder
                                .texture(TextureSlot.CONTENT).tintindex(0))).build();
    }
}
