package net.theobl.worldofcolor.datagen;

import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.generators.loaders.CompositeModelBuilder;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;
import net.theobl.worldofcolor.WorldOfColor;

public class VanillaModelTemplates {
    public static final ModelTemplate CAULDRON = ModelTemplates.create(
            "cauldron", TextureSlot.INSIDE, TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE
    );
    public static final ModelTemplate LIGHTNING_ROD = ModelTemplates.create("lightning_rod", TextureSlot.TEXTURE, TextureSlot.PARTICLE);
    public static final ModelTemplate SLIME_BLOCK = ModelTemplates.create("slime_block", TextureSlot.PARTICLE, TextureSlot.TEXTURE);
    public static final ModelTemplate FLOWER_POT = ModelTemplates.create("flower_pot", TextureSlot.PARTICLE, ColoredTextureSlot.FLOWERPOT);

//    public static ModelTemplate cauldronLevelX(DyeColor color, int level) {
//        final float y;
//        if(level == 1)
//            y = 9;
//        else if(level == 2)
//            y = 12;
//        else
//            y = 15;
//
//        return ExtendedModelTemplateBuilder.builder().requiredTextureSlot(TextureSlot.PARTICLE).customLoader(CompositeModelBuilder::new,
//                compositeModelBuilder -> compositeModelBuilder.child("cauldron",
//                                ResourceLocation.fromNamespaceAndPath(WorldOfColor.MODID, "block/" + color.getName() + "_cauldron"))
//                        .inlineChild("content",
//                                ModelTemplates.create(TextureSlot.CONTENT).extend()
//                                        .renderType("translucent")
//                                        .element(elementBuilder -> elementBuilder
//                                                .from(2, 4, 2)
//                                                .to(14, y, 14)
//                                                .face(Direction.UP, faceBuilder -> faceBuilder
//                                                        .texture(TextureSlot.CONTENT).tintindex(0)))
//                                        .build(),
//                                new TextureMapping().put(TextureSlot.CONTENT, TextureMapping.getBlockTexture(Blocks.WATER, "_still"))))
//                .ambientOcclusion(false)
//                .build();
//    }
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
