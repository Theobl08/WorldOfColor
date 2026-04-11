package net.theobl.worldofcolor.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.sounds.ModSoundEvents;

public class ModSoundDefinitionsProvider extends SoundDefinitionsProvider {
    protected ModSoundDefinitionsProvider(PackOutput output) {
        super(output, WorldOfColor.MODID);
    }

    @Override
    public void registerSounds() {
        this.add(ModSoundEvents.POTONE_BREAK, definition().subtitle("subtitles.block.generic.break")
                .with(sound(WorldOfColor.asResource("block/potone/break1")), sound(WorldOfColor.asResource("block/potone/break2")),
                        sound(WorldOfColor.asResource("block/potone/break3")), sound(WorldOfColor.asResource("block/potone/break4"))));
        this.add(ModSoundEvents.POTONE_FALL, definition()
                .with(sound(WorldOfColor.asResource("block/potone/step1")),
                        sound(WorldOfColor.asResource("block/potone/step2")),
                        sound(WorldOfColor.asResource("block/potone/step3")),
                        sound(WorldOfColor.asResource("block/potone/step4")),
                        sound(WorldOfColor.asResource("block/potone/step5")),
                        sound(WorldOfColor.asResource("block/potone/step6"))));
        this.add(ModSoundEvents.POTONE_HIT, definition().subtitle("subtitles.block.generic.hit")
                .with(sound(WorldOfColor.asResource("block/potone/step1")),
                        sound(WorldOfColor.asResource("block/potone/step2")),
                        sound(WorldOfColor.asResource("block/potone/step3")),
                        sound(WorldOfColor.asResource("block/potone/step4")),
                        sound(WorldOfColor.asResource("block/potone/step5")),
                        sound(WorldOfColor.asResource("block/potone/step6"))));
        this.add(ModSoundEvents.POTONE_PLACE, definition().subtitle("subtitles.block.generic.place")
                .with(sound(WorldOfColor.asResource("block/potone/break1")), sound(WorldOfColor.asResource("block/potone/break2")),
                        sound(WorldOfColor.asResource("block/potone/break3")), sound(WorldOfColor.asResource("block/potone/break4"))));
        this.add(ModSoundEvents.POTONE_STEP, definition().subtitle("subtitles.block.generic.footsteps")
                .with(sound(WorldOfColor.asResource("block/potone/step1")),
                        sound(WorldOfColor.asResource("block/potone/step2")),
                        sound(WorldOfColor.asResource("block/potone/step3")),
                        sound(WorldOfColor.asResource("block/potone/step4")),
                        sound(WorldOfColor.asResource("block/potone/step5")),
                        sound(WorldOfColor.asResource("block/potone/step6"))));
    }
}
