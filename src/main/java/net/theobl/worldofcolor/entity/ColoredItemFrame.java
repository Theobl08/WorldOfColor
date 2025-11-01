package net.theobl.worldofcolor.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.theobl.worldofcolor.item.ModItems;
import net.theobl.worldofcolor.util.ModUtil;

public class ColoredItemFrame extends ItemFrame {
    private final DyeColor color;
    public ColoredItemFrame(EntityType<? extends ItemFrame> entityType, Level level, DyeColor color) {
        super(entityType, level);
        this.color = color;
    }

    public ColoredItemFrame(Level level, BlockPos pos, Direction facingDirection, DyeColor color) {
        super(ModEntityType.COLORED_ITEM_FRAMES.get(ModUtil.COLORS.indexOf(color)).get(), level, pos, facingDirection);
        this.color = color;
    }

    @Override
    protected ItemStack getFrameItemStack() {
        return new ItemStack(ModItems.COLORED_ITEM_FRAMES.get(ModUtil.COLORS.indexOf(this.color)).get());
    }
}
