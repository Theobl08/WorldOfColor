package net.theobl.worldofcolor.item.equipement;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.theobl.worldofcolor.WorldOfColor;

public interface ModEquipmentAssets {
    ResourceKey<EquipmentAsset> RGB_HARNESS = ResourceKey.create(EquipmentAssets.ROOT_ID, WorldOfColor.asResource("rgb_harness"));
}
