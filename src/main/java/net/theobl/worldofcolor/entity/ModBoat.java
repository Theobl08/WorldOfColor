package net.theobl.worldofcolor.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.item.ModItems;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.IntFunction;

public class ModBoat extends Boat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(ModBoat.class, EntityDataSerializers.INT);

    public ModBoat(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ModBoat(Level pLevel, double pX, double pY, double pZ) {
        this(ModEntityType.COLORED_BOAT.get(), pLevel);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_TYPE, Type.WHITE.ordinal());
    }

    public Item getDropItem() {
        return switch (this.getModVariant()) {
            case WHITE -> ModItems.COLORED_BOATS.get(0).get();
            case LIGHT_GRAY -> ModItems.COLORED_BOATS.get(1).get();
            case GRAY -> ModItems.COLORED_BOATS.get(2).get();
            case BLACK -> ModItems.COLORED_BOATS.get(3).get();
            case BROWN -> ModItems.COLORED_BOATS.get(4).get();
            case RED -> ModItems.COLORED_BOATS.get(5).get();
            case ORANGE -> ModItems.COLORED_BOATS.get(6).get();
            case YELLOW -> ModItems.COLORED_BOATS.get(7).get();
            case LIME -> ModItems.COLORED_BOATS.get(8).get();
            case GREEN -> ModItems.COLORED_BOATS.get(9).get();
            case CYAN -> ModItems.COLORED_BOATS.get(10).get();
            case LIGHT_BLUE -> ModItems.COLORED_BOATS.get(11).get();
            case BLUE -> ModItems.COLORED_BOATS.get(12).get();
            case PURPLE -> ModItems.COLORED_BOATS.get(13).get();
            case MAGENTA -> ModItems.COLORED_BOATS.get(14).get();
            case PINK -> ModItems.COLORED_BOATS.get(15).get();
        };
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putString("Type", this.getModVariant().getSerializedName());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Type", 8)) {
            this.setVariant(Type.byName(pCompound.getString("Type")));
        }
    }

    public void setVariant(Type pVariant) {
        this.entityData.set(DATA_ID_TYPE, pVariant.ordinal());
    }

    @NotNull
    public ModBoat.Type getModVariant() {
        return Type.byId(this.entityData.get(DATA_ID_TYPE));
    }

    public static enum Type implements StringRepresentable {
        WHITE(ModBlocks.COLORED_PLANKS.get(0).get(), "white"),
        LIGHT_GRAY(ModBlocks.COLORED_PLANKS.get(1).get(), "light_gray"),
        GRAY(ModBlocks.COLORED_PLANKS.get(2).get(), "gray"),
        BLACK(ModBlocks.COLORED_PLANKS.get(3).get(), "black"),
        BROWN(ModBlocks.COLORED_PLANKS.get(4).get(), "brown"),
        RED(ModBlocks.COLORED_PLANKS.get(5).get(), "red"),
        ORANGE(ModBlocks.COLORED_PLANKS.get(6).get(), "orange"),
        YELLOW(ModBlocks.COLORED_PLANKS.get(7).get(), "yellow"),
        LIME(ModBlocks.COLORED_PLANKS.get(8).get(), "lime"),
        GREEN(ModBlocks.COLORED_PLANKS.get(9).get(), "green"),
        CYAN(ModBlocks.COLORED_PLANKS.get(10).get(), "cyan"),
        LIGHT_BLUE(ModBlocks.COLORED_PLANKS.get(11).get(), "light_blue"),
        BLUE(ModBlocks.COLORED_PLANKS.get(12).get(), "blue"),
        PURPLE(ModBlocks.COLORED_PLANKS.get(13).get(), "purple"),
        MAGENTA(ModBlocks.COLORED_PLANKS.get(14).get(), "magenta"),
        PINK(ModBlocks.COLORED_PLANKS.get(15).get(), "pink");

        private final String name;
        private final Block planks;

        private static final IntFunction<Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

        private Type(Block pPlanks, String pName) {
            this.name = pName;
            this.planks = pPlanks;
        }

        @NotNull
        public String getSerializedName() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }

        public Block getPlanks() {
            return this.planks;
        }

        public String toString() {
            return this.name;
        }

        /**
         * Get a boat type by its enum ordinal
         */
        public static Type byId(int pId) {
            return BY_ID.apply(pId);
        }

        public static Type byName(String pName) {
            Type[] type = values();
            return Arrays.stream(type).filter(t -> t.getName().equals(pName)).findFirst().orElse(type[0]);
        }
    }
}
