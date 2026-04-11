package net.theobl.worldofcolor.sounds;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.theobl.worldofcolor.WorldOfColor;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =  DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, WorldOfColor.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> POTONE_BREAK = registerSoundEvent("block.potone.break");
    public static final DeferredHolder<SoundEvent, SoundEvent> POTONE_STEP = registerSoundEvent("block.potone.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> POTONE_PLACE = registerSoundEvent("block.potone.place");
    public static final DeferredHolder<SoundEvent, SoundEvent> POTONE_HIT = registerSoundEvent("block.potone.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> POTONE_FALL = registerSoundEvent("block.potone.fall");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String id) {
        return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(WorldOfColor.asResource(id)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
