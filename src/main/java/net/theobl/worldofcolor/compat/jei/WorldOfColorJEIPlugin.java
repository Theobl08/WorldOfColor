package net.theobl.worldofcolor.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.item.ModItems;

@JeiPlugin
public class WorldOfColorJEIPlugin implements IModPlugin {
    @Override
    public Identifier getPluginUid() {
        return WorldOfColor.asResource("jei_plugin");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerFromDataComponentTypes(ModItems.DYED_WATER_BUCKET.get(), DataComponents.DYED_COLOR);
        registration.registerFromDataComponentTypes(ModItems.DYED_WATER_BOTTLE.get(), DataComponents.DYED_COLOR);
    }
}
