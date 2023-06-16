package xyz.someboringnerd.autoframedupe.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.someboringnerd.autoframedupe.module;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin
{
    @Inject(method = "tick", at = @At("HEAD"))
    private void OnTick(CallbackInfo ci)
    {
        module.getInstance().onTick();
    }
}
