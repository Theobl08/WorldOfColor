package net.theobl.worldofcolor.compat.jade;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.DyedItemColor;
import net.theobl.worldofcolor.block.entity.DyedWaterCauldronBlockEntity;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;

@NullMarked
public class DyedWaterCauldronProvider implements StreamServerDataProvider<BlockAccessor, Integer> {
    protected static final DyedWaterCauldronProvider INSTANCE = new DyedWaterCauldronProvider();

    @Override
    public @Nullable Integer streamData(BlockAccessor accessor) {
        DyedWaterCauldronBlockEntity entity = accessor.typedBlockEntity();
        return ARGB.transparent(entity.getWaterColor());
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, Integer> streamCodec() {
        return ByteBufCodecs.INT.cast();
    }

    @Override
    public Identifier getUid() {
        return WorldOfColorJadePlugin.DYED_WATER_CAULDRON;
    }

    public static class Client implements IBlockComponentProvider {
        public static final Client INSTANCE = new Client();

        @Override
        public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
            Integer data = DyedWaterCauldronProvider.INSTANCE.decodeFromData(accessor).orElse(null);
            if(data == null) return;
            new DyedItemColor(data).addToTooltip(Item.TooltipContext.EMPTY, tooltip::add, TooltipFlag.ADVANCED, DataComponentMap.EMPTY);
        }

        @Override
        public Identifier getUid() {
            return WorldOfColorJadePlugin.DYED_WATER_CAULDRON;
        }
    }
}
