package net.theobl.worldofcolor.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class DyedWaterCauldronBlockEntity extends BlockEntity {
    /**
     * Mirror {@link net.minecraft.data.worldgen.biome.OverworldBiomes#NORMAL_WATER_COLOR}
     */
    private static final int NORMAL_WATER_COLOR = 4159204;
    private int waterColor;
    public DyedWaterCauldronBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityType.DYED_WATER_CAULDRON.get(), pos, blockState);
        waterColor = ARGB.opaque(NORMAL_WATER_COLOR);
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
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("CustomColor", this.waterColor);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.waterColor = input.getIntOr("CustomColor", ARGB.opaque(NORMAL_WATER_COLOR));
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if(this.getLevel() != null) {
            this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }

    public int getWaterColor() {
        return waterColor;
    }

    public void setWaterColor(DyeColor dyeColor) {
        this.waterColor = dyeColor.getTextureDiffuseColor();
        this.setChanged();
    }

    // Formula taken from https://minecraft.fandom.com/wiki/Dye#Dyeing_armor and DyedItemColor#applyDyes
    public void applyDye(DyeColor dyeColor) {
        int redTotal = 0;
        int greenTotal = 0;
        int blueTotal = 0;
        int intensityTotal = 0;
        int colorCount = 0;

        // Retrieve the current water color
        int currentColor = this.waterColor;
        int red = ARGB.red(currentColor);
        int green = ARGB.green(currentColor);
        int blue = ARGB.blue(currentColor);
        intensityTotal += Math.max(red, Math.max(green, blue));
        redTotal += red;
        greenTotal += green;
        blueTotal += blue;
        colorCount++;

        // Apply the new DyeColor's color
        int color = dyeColor.getTextureDiffuseColor();
        red = ARGB.red(color);
        green = ARGB.green(color);
        blue = ARGB.blue(color);
        intensityTotal += Math.max(red, Math.max(green, blue));
        redTotal += red;
        greenTotal += green;
        blueTotal += blue;
        colorCount++;

        // Apply the formula
        red = redTotal / colorCount;
        green = greenTotal / colorCount;
        blue = blueTotal / colorCount;
        float averageIntensity = (float)intensityTotal / colorCount;
        float resultIntensity = Math.max(red, Math.max(green, blue));
        red = (int)(red * averageIntensity / resultIntensity);
        green = (int)(green * averageIntensity / resultIntensity);
        blue = (int)(blue * averageIntensity / resultIntensity);
        int rgb = ARGB.color(0, red, green, blue);

        // Update water color
        this.waterColor = ARGB.opaque(rgb);
        this.setChanged();
    }
}
