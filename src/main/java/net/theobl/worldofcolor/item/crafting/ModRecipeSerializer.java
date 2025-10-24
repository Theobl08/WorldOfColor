package net.theobl.worldofcolor.item.crafting;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;

import java.util.function.Supplier;

public class ModRecipeSerializer {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, WorldOfColor.MODID);

    public static final Supplier<RecipeSerializer<ColoredDecoratedPotRecipe>> COLORED_DECORATED_POT_RECIPE =
            RECIPE_SERIALIZERS.register("colored_decorated_pot", () -> new CustomRecipe.Serializer<>(ColoredDecoratedPotRecipe::new));

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
