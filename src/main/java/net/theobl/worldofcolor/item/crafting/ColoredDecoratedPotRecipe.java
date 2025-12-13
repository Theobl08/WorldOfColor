package net.theobl.worldofcolor.item.crafting;

import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.theobl.worldofcolor.block.entity.ColoredDecoratedPotBlockEntity;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ColoredDecoratedPotRecipe extends CustomRecipe {
    public ColoredDecoratedPotRecipe(CraftingBookCategory category) {
        super(category);
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
                ? back(input).is(ItemTags.DECORATED_POT_INGREDIENTS)
                && left(input).is(ItemTags.DECORATED_POT_INGREDIENTS)
                && right(input).is(ItemTags.DECORATED_POT_INGREDIENTS)
                && front(input).is(ItemTags.DECORATED_POT_INGREDIENTS)
                && dye(input).getItem() instanceof DyeItem
                : false;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        PotDecorations potdecorations = new PotDecorations(
                back(input).getItem(), left(input).getItem(), right(input).getItem(), front(input).getItem()
        );
        return ColoredDecoratedPotBlockEntity.createDecoratedPotItem(potdecorations, ((DyeItem) dye(input).getItem()).getDyeColor());
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializer.COLORED_DECORATED_POT_RECIPE.get();
    }
}
