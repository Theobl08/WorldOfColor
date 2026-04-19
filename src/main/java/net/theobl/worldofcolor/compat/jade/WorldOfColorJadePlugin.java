package net.theobl.worldofcolor.compat.jade;

import net.minecraft.resources.Identifier;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.DyedWaterCauldronBlock;
import net.theobl.worldofcolor.block.entity.DyedWaterCauldronBlockEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class WorldOfColorJadePlugin implements IWailaPlugin {
    protected static Identifier DYED_WATER_CAULDRON = WorldOfColor.asResource("dyed_water_cauldron");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(DyedWaterCauldronProvider.INSTANCE, DyedWaterCauldronBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(DyedWaterCauldronProvider.Client.INSTANCE, DyedWaterCauldronBlock.class);
    }
}
