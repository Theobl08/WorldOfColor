package net.theobl.worldofcolor.entity;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Cushion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.theobl.worldofcolor.item.ModItems;
import org.jspecify.annotations.NullMarked;

@NullMarked
public abstract class AbstractCushion extends Cushion {
    public AbstractCushion(EntityType<Cushion> type, Level level) {
        super(type, level);
    }

    protected abstract Holder<Block> getAssociatedWoolBlock();

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(ModItems.RGB_CUSHION.get());
    }

    @Override
    protected void showBreakingParticles() {
        if (this.level() instanceof ServerLevel level) {
            level.sendParticles(
                    new BlockParticleOption(ParticleTypes.BLOCK, getAssociatedWoolBlock().value().defaultBlockState()),
                    this.getX(),
                    this.getY(0.6666666666666666),
                    this.getZ(),
                    10,
                    this.getBbWidth() / 4.0F,
                    this.getBbHeight() / 4.0F,
                    this.getBbWidth() / 4.0F,
                    0.05
            );
        }
    }

    @Override
    protected ItemStack getCushionItemStackWithData() {
        ItemStack itemStack = getPickResult();
        itemStack.set(DataComponents.CUSTOM_NAME, this.getCustomName());
        return itemStack;
    }
}
