package net.theobl.worldofcolor.datagen;

import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.item.equipement.ModEquipmentAssets;
import org.jspecify.annotations.NullMarked;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@NullMarked
public class ModEquipmentAssetProvider extends EquipmentAssetProvider {

    public ModEquipmentAssetProvider(PackOutput output) {
        super(output);
    }

    protected void registerModels(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
        output.accept(
                ModEquipmentAssets.RGB_HARNESS,
                EquipmentClientInfo.builder()
                        .addLayers(EquipmentClientInfo.LayerType.HAPPY_GHAST_BODY, new EquipmentClientInfo.Layer(WorldOfColor.asResource("rgb_harness")))
                        .build()
        );
    }
}
