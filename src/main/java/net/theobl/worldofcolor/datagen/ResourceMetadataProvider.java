package net.theobl.worldofcolor.datagen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.renderer.texture.MipmapStrategy;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.util.Util;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.theobl.worldofcolor.WorldOfColor;
import net.theobl.worldofcolor.block.ModBlocks;
import net.theobl.worldofcolor.item.ModItems;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

//See https://docs.neoforged.net/docs/resources/metadata/#other-resource-metadata
public class ResourceMetadataProvider implements DataProvider {

    private final PackOutput output;
    private final Map<Path, ResourceMetadata> metadata;

    public ResourceMetadataProvider(PackOutput output) {
        this.output = output;
        this.metadata = new HashMap<>();
    }

    protected void add() {
        this.addSimpleRGB(ModBlocks.RGB_WOOL.getId().withPrefix("block/"));
        this.addSimpleRGB(ModBlocks.RGB_TERRACOTTA.getId().withPrefix("block/"));
        this.addSimpleRGB(ModBlocks.RGB_CONCRETE.getId().withPrefix("block/"));
        this.addSimpleRGB(ModBlocks.RGB_CONCRETE_POWDER.getId().withPrefix("block/"));
        this.addSimpleRGB(ModBlocks.RGB_GLAZED_TERRACOTTA.getId().withPrefix("block/"));
        this.addSimpleRGB(ModBlocks.RGB_STAINED_GLASS.getId().withPrefix("block/"));
        this.addSimpleRGB(ModBlocks.RGB_STAINED_GLASS_PANE.getId().withPrefix("block/").withSuffix("_top"));
        this.addSimpleRGB(WorldOfColor.asResource("entity/shulker/shulker_rgb"));
        this.addSimpleRGB(ModBlocks.RGB_BED.getId().withPrefix("block/").withSuffix("_foot_east"));
        this.addSimpleRGB(ModBlocks.RGB_BED.getId().withPrefix("block/").withSuffix("_foot_south"));
        this.addSimpleRGB(ModBlocks.RGB_BED.getId().withPrefix("block/").withSuffix("_foot_up"));
        this.addSimpleRGB(ModBlocks.RGB_BED.getId().withPrefix("block/").withSuffix("_foot_west"));
        this.addSimpleRGB(ModBlocks.RGB_BED.getId().withPrefix("block/").withSuffix("_head_east"));
        this.addSimpleRGB(ModBlocks.RGB_BED.getId().withPrefix("block/").withSuffix("_head_up"));
        this.addSimpleRGB(ModBlocks.RGB_BED.getId().withPrefix("block/").withSuffix("_head_west"));
        this.addSimpleRGB(ModBlocks.RGB_CANDLE.getId().withPrefix("block/"));
        this.addSimpleRGB(ModBlocks.RGB_CANDLE.getId().withPrefix("item/"));
        this.addSimpleRGB(WorldOfColor.asResource("entity/banner/rgb"));
        this.addSimpleRGB(ModItems.RGB_BUNDLE.getId().withPrefix("item/"));
        this.addSimpleRGB(ModItems.RGB_HARNESS.getId().withPrefix("item/"));
        this.addSimpleRGB(ModItems.RGB_HARNESS.getId().withPrefix("entity/equipment/happy_ghast_body/"));
        this.addSimpleRGB(ModItems.RGB_DYE.getId().withPrefix("item/"));
    }

    private void addSimpleRGB(Identifier identifier) {
        this.textureMetadata(identifier)
                .add(
                        AnimationMetadataSection.TYPE,
                        new AnimationMetadataSection(
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty(),
                                25,
                                true
                        )
                );
    }

    protected ResourceMetadata textureMetadata(Identifier resource) {
        return this.metadata(
                PackOutput.Target.RESOURCE_PACK,
                "textures",
                resource.withSuffix(".png")
        );
    }

    protected ResourceMetadata metadata(PackOutput.Target type, String directory, Identifier resource) {
        return this.metadata.computeIfAbsent(
                this.output.createPathProvider(type, directory).file(resource, "mcmeta"),
                p -> new ResourceMetadata()
        );
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        add();
        Executor executor = Util.backgroundExecutor().forName("serializeMetadata");
        return CompletableFuture.allOf(
                this.metadata.entrySet().stream().map(entry -> CompletableFuture.supplyAsync(() -> {
                            JsonObject result = new JsonObject();
                            entry.getValue().sections().forEach((type, data) -> result.add(type, data.get()));
                            return result;
                        }, executor).thenComposeAsync(json -> DataProvider.saveStable(cache, json, entry.getKey()), executor)
                ).toArray(CompletableFuture[]::new)
        );
    }

    @Override
    public final String getName() {
        return "Resource Metadata";
    }

    public record ResourceMetadata(Map<String, Supplier<JsonElement>> sections) {

        public ResourceMetadata() {
            this(new HashMap<>());
        }

        public <T> ResourceMetadata add(MetadataSectionType<T> type, T value) {
            this.sections.put(type.name(), () -> type.codec().encodeStart(JsonOps.INSTANCE, value).getOrThrow(IllegalArgumentException::new).getAsJsonObject());
            return this;
        }
    }

}
