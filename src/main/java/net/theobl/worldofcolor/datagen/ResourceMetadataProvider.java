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
        // Add metadata here.
        ModBlocks.COLORED_LEAVES.forEach(block ->
                this.textureMetadata(block.getId().withPrefix("block/"))
                        // Can chain multiple `add` calls.
                        .add(
                                // The metadata section to add.
                                TextureMetadataSection.TYPE,
                                // The value of the metadata section.
                                new TextureMetadataSection(
                                        TextureMetadataSection.DEFAULT_BLUR,
                                        TextureMetadataSection.DEFAULT_CLAMP,
                                        MipmapStrategy.DARK_CUTOUT,
                                        TextureMetadataSection.DEFAULT_ALPHA_CUTOFF_BIAS
                                )
                        )
        );
        this.addSimpleFlower(ModBlocks.LIGHT_GRAY_TULIP.getId());
        this.addSimpleFlower(ModBlocks.GRAY_TULIP.getId());
        this.addSimpleFlower(ModBlocks.BLACK_TULIP.getId());
        this.addSimpleFlower(ModBlocks.BROWN_TULIP.getId());
        this.addSimpleFlower(ModBlocks.YELLOW_TULIP.getId());
        this.addSimpleFlower(ModBlocks.LIME_TULIP.getId());
        this.addSimpleFlower(ModBlocks.GREEN_TULIP.getId());
        this.addSimpleFlower(ModBlocks.CYAN_TULIP.getId());
        this.addSimpleFlower(ModBlocks.LIGHT_BLUE_TULIP.getId());
        this.addSimpleFlower(ModBlocks.BLUE_TULIP.getId());
        this.addSimpleFlower(ModBlocks.PURPLE_TULIP.getId());
        this.addSimpleFlower(ModBlocks.MAGENTA_TULIP.getId());
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

    private void addSimpleFlower(Identifier identifier) {
        this.textureMetadata(identifier)
                .add(
                        TextureMetadataSection.TYPE,
                        new TextureMetadataSection(
                                TextureMetadataSection.DEFAULT_BLUR,
                                TextureMetadataSection.DEFAULT_CLAMP,
                                MipmapStrategy.STRICT_CUTOUT,
                                TextureMetadataSection.DEFAULT_ALPHA_CUTOFF_BIAS
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
