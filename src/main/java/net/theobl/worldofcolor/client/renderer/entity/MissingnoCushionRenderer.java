package net.theobl.worldofcolor.client.renderer.entity;

import net.minecraft.client.renderer.entity.CushionRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.CushionRenderState;
import net.minecraft.world.entity.decoration.Cushion;
import net.theobl.worldofcolor.WorldOfColor;

public class MissingnoCushionRenderer extends CushionRenderer {
    public MissingnoCushionRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void extractRenderState(Cushion cushion, CushionRenderState state, float partialTicks) {
        super.extractRenderState(cushion, state, partialTicks);
        state.texture = WorldOfColor.asResource("textures/entity/cushion/missingno_cushion.png");
    }
}
