package net.theobl.worldofcolor.sounds;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.common.util.DeferredSoundType;

public class ModSoundType {
    public static final SoundType POTONE = new DeferredSoundType(
            1.0F,
            1.0F,
            ModSoundEvents.POTONE_BREAK,
            ModSoundEvents.POTONE_STEP,
            ModSoundEvents.POTONE_PLACE,
            ModSoundEvents.POTONE_HIT,
            ModSoundEvents.POTONE_FALL
    );
}
