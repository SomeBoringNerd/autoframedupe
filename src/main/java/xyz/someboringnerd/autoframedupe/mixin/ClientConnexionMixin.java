package xyz.someboringnerd.autoframedupe.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.someboringnerd.autoframedupe.module;

@Mixin(ClientConnection.class)
public class ClientConnexionMixin
{

    // @TODO : replace that with event
    @Inject(method = "send(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void send(Packet<?> packet, CallbackInfo ci)
    {
        if (packet instanceof ChatMessageC2SPacket)
        {
            String message = (((ChatMessageC2SPacket) packet).chatMessage());

            if(message.startsWith("!dupe")){
                module.active = !module.active;
                ci.cancel();
            }
        }
    }
}