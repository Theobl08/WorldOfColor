package net.theobl.worldofcolor.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.theobl.worldofcolor.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class ModChestBoat extends ChestBoat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(ModChestBoat.class, EntityDataSerializers.INT);

    public ModChestBoat(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ModChestBoat(Level pLevel, double pX, double pY, double pZ) {
        this(ModEntityType.COLORED_CHEST_BOAT.get(), pLevel);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_TYPE, ModBoat.Type.WHITE.ordinal());
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putString("Type", this.getModVariant().getSerializedName());
        this.addChestVehicleSaveData(pCompound, this.registryAccess());
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Type", 8)) {
            this.setVariant(ModBoat.Type.byName(pCompound.getString("Type")));
        }
        this.readChestVehicleSaveData(pCompound, this.registryAccess());
    }

    public Item getDropItem() {
        return switch (this.getModVariant()) {
            case WHITE -> ModItems.COLORED_CHEST_BOATS.get(0).get();
            case LIGHT_GRAY -> ModItems.COLORED_CHEST_BOATS.get(1).get();
            case GRAY -> ModItems.COLORED_CHEST_BOATS.get(2).get();
            case BLACK -> ModItems.COLORED_CHEST_BOATS.get(3).get();
            case BROWN -> ModItems.COLORED_CHEST_BOATS.get(4).get();
            case RED -> ModItems.COLORED_CHEST_BOATS.get(5).get();
            case ORANGE -> ModItems.COLORED_CHEST_BOATS.get(6).get();
            case YELLOW -> ModItems.COLORED_CHEST_BOATS.get(7).get();
            case LIME -> ModItems.COLORED_CHEST_BOATS.get(8).get();
            case GREEN -> ModItems.COLORED_CHEST_BOATS.get(9).get();
            case CYAN -> ModItems.COLORED_CHEST_BOATS.get(10).get();
            case LIGHT_BLUE -> ModItems.COLORED_CHEST_BOATS.get(11).get();
            case BLUE -> ModItems.COLORED_CHEST_BOATS.get(12).get();
            case PURPLE -> ModItems.COLORED_CHEST_BOATS.get(13).get();
            case MAGENTA -> ModItems.COLORED_CHEST_BOATS.get(14).get();
            case PINK -> ModItems.COLORED_CHEST_BOATS.get(15).get();
        };
    }

    public void setVariant(ModBoat.Type pVariant) {
        this.entityData.set(DATA_ID_TYPE, pVariant.ordinal());
    }

    @NotNull
    public ModBoat.Type getModVariant() {
        return ModBoat.Type.byId(this.entityData.get(DATA_ID_TYPE));
    }
}
