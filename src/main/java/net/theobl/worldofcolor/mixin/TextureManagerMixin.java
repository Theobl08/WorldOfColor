package net.theobl.worldofcolor.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.Identifier;
import net.theobl.worldofcolor.client.renderer.texture.ModAnimatedTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

//Credits to https://github.com/bernie-g/geckolib/blob/main/common/src/main/java/com/geckolib/mixin/client/TextureManagerMixin.java (MIT Licensed)
@Mixin(TextureManager.class)
public abstract class TextureManagerMixin {
    @Shadow
    protected abstract TextureContents loadContentsSafe(Identifier textureId, ReloadableTexture texture);

    @Shadow
    public abstract void register(Identifier location, AbstractTexture texture);

    @Definition(id = "SimpleTexture", type = SimpleTexture.class)
    @Expression("new SimpleTexture(?)")
    @WrapOperation(method = "getTexture(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/renderer/texture/AbstractTexture;",
            at = @At("MIXINEXTRAS:EXPRESSION"))
    private SimpleTexture worldofcolor$replaceAnimatableTexture(Identifier location, Operation<SimpleTexture> original) {
        ModAnimatedTexture animatableTexture = new ModAnimatedTexture(location);

        TextureContents contents = loadContentsSafe(location, animatableTexture);

        if (animatableTexture.isAnimated()) {
            animatableTexture.apply(contents);
            register(location, animatableTexture);

            return animatableTexture;
        }

        animatableTexture.close();

        return original.call(location);
    }

    @Definition(id = "registerAndLoad", method = "Lnet/minecraft/client/renderer/texture/TextureManager;registerAndLoad(Lnet/minecraft/resources/Identifier;Lnet/minecraft/client/renderer/texture/ReloadableTexture;)V")
    @Expression("this.registerAndLoad(?, ?)")
    @WrapWithCondition(method = "getTexture(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/renderer/texture/AbstractTexture;",
            at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean worldofcolor$skipAnimatableTextureRegistration(TextureManager textureManager, Identifier id, ReloadableTexture texture) {
        return !(texture instanceof ModAnimatedTexture);
    }
}
