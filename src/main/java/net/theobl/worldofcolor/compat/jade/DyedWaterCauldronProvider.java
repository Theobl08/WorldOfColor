package net.theobl.worldofcolor.compat.jade;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.theobl.worldofcolor.block.entity.DyedWaterCauldronBlockEntity;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;

import java.util.Locale;

@NullMarked
public class DyedWaterCauldronProvider implements StreamServerDataProvider<BlockAccessor, String> {
    protected static final DyedWaterCauldronProvider INSTANCE = new DyedWaterCauldronProvider();

    @Override
    public @Nullable String streamData(BlockAccessor accessor) {
        DyedWaterCauldronBlockEntity entity = accessor.typedBlockEntity();
        return Integer.toHexString(ARGB.transparent(entity.getWaterColor())).toUpperCase(Locale.ROOT);
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, String> streamCodec() {
        return ByteBufCodecs.STRING_UTF8.cast();
    }

    @Override
    public Identifier getUid() {
        return WorldOfColorJadePlugin.DYED_WATER_CAULDRON;
    }

    public static class Client implements IBlockComponentProvider {
        public static final Client INSTANCE = new Client();

        @Override
        public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
            String data = DyedWaterCauldronProvider.INSTANCE.decodeFromData(accessor).orElse(null);
            if(data == null) return;
            tooltip.add(Component.literal("Color: #" + data));
        }

        @Override
        public Identifier getUid() {
            return WorldOfColorJadePlugin.DYED_WATER_CAULDRON;
        }
    }
}
