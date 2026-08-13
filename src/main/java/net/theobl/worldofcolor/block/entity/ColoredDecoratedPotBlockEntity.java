package net.theobl.worldofcolor.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.state.BlockState;
import net.theobl.worldofcolor.block.ModBlocks;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ColoredDecoratedPotBlockEntity extends DecoratedPotBlockEntity {

    public ColoredDecoratedPotBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntityType.COLORED_DECORATED_POT.get();
    }

    public static ItemStackTemplate createDecoratedPotTemplate(PotDecorations decorations, DyeColor color) {
        return new ItemStackTemplate(ModBlocks.COLORED_DECORATED_POTS.pick(color).asItem(),
                DataComponentPatch.builder().set(DataComponents.POT_DECORATIONS, decorations).build());
    }

    public static ItemStack createDecoratedPotInstance(PotDecorations decorations, DyeColor color) {
        return createDecoratedPotTemplate(decorations, color).create();
    }
}
