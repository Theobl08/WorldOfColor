package net.theobl.worldofcolor.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class DyedWaterLiquidBlockEntity extends BlockEntity {
    public static final DyedItemColor NORMAL_WATER_COLOR = new DyedItemColor(OverworldBiomes.NORMAL_WATER_COLOR);
    private DyedItemColor color = NORMAL_WATER_COLOR;
    public DyedWaterLiquidBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntityType.DYED_WATER.get(), worldPosition, blockState);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // The packet uses the CompoundTag returned by #getUpdateTag. An alternative overload of #create exists
        // that allows you to specify a custom update tag, including the ability to omit data the client might not need.
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return saveWithoutMetadata(provider);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.color = input.read("color", DyedItemColor.CODEC).orElse(NORMAL_WATER_COLOR);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.store("color", DyedItemColor.CODEC, color);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if(this.getLevel() != null) {
            this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }

    public DyedItemColor getColor() {
        return color;
    }

    public void setColor(DyedItemColor color) {
        this.color = color;
        this.setChanged();
    }
}
