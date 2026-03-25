package net.theobl.worldofcolor.item.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.theobl.worldofcolor.block.entity.ColoredDecoratedPotBlockEntity;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ColoredDecoratedPotRecipe extends CustomRecipe {
    public static final MapCodec<ColoredDecoratedPotRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                            Ingredient.CODEC.fieldOf("back").forGetter(o -> o.backPattern),
                            Ingredient.CODEC.fieldOf("left").forGetter(o -> o.leftPattern),
                            Ingredient.CODEC.fieldOf("right").forGetter(o -> o.rightPattern),
                            Ingredient.CODEC.fieldOf("front").forGetter(o -> o.frontPattern),
                            Ingredient.CODEC.fieldOf("dye").forGetter(o -> o.dye)
                    )
                    .apply(i, ColoredDecoratedPotRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, ColoredDecoratedPotRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC,
            o -> o.backPattern,
            Ingredient.CONTENTS_STREAM_CODEC,
            o -> o.leftPattern,
            Ingredient.CONTENTS_STREAM_CODEC,
            o -> o.rightPattern,
            Ingredient.CONTENTS_STREAM_CODEC,
            o -> o.frontPattern,
            Ingredient.CONTENTS_STREAM_CODEC,
            o -> o.dye,
            ColoredDecoratedPotRecipe::new
    );
    public static final RecipeSerializer<ColoredDecoratedPotRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);
    private final Ingredient backPattern;
    private final Ingredient leftPattern;
    private final Ingredient rightPattern;
    private final Ingredient frontPattern;
    private final Ingredient dye;

    public ColoredDecoratedPotRecipe(Ingredient wallPattern, Ingredient dye) {
        this(wallPattern, wallPattern, wallPattern, wallPattern, dye);
    }

    public ColoredDecoratedPotRecipe(Ingredient backPattern, Ingredient leftPattern, Ingredient rightPattern, Ingredient frontPattern, Ingredient dye) {
        this.backPattern = backPattern;
        this.leftPattern = leftPattern;
        this.rightPattern = rightPattern;
        this.frontPattern = frontPattern;
        this.dye = dye;
    }

    private static ItemStack back(CraftingInput input) {
        return input.getItem(1, 0);
    }

    private static ItemStack left(CraftingInput input) {
        return input.getItem(0, 1);
    }

    private static ItemStack right(CraftingInput input) {
        return input.getItem(2, 1);
    }

    private static ItemStack front(CraftingInput input) {
        return input.getItem(1, 2);
    }

    private static ItemStack dye(CraftingInput input) {
        return input.getItem(1, 1);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return input.width() == 3 && input.height() == 3 && input.ingredientCount() == 5
                ? this.backPattern.test(back(input))
                && this.leftPattern.test(left(input))
                && this.rightPattern.test(right(input))
                && this.frontPattern.test(front(input))
                && dye(input).getItem() instanceof DyeItem
                : false;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        PotDecorations potdecorations = new PotDecorations(
                back(input).getItem(), left(input).getItem(), right(input).getItem(), front(input).getItem()
        );
        DataComponentPatch components = DataComponentPatch.builder().set(DataComponents.POT_DECORATIONS, potdecorations).build();
        return ColoredDecoratedPotBlockEntity.createDecoratedPotTemplate(potdecorations, dye(input).getOrDefault(DataComponents.DYE, DyeColor.WHITE))
                .apply(components);
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return SERIALIZER;
    }
}
