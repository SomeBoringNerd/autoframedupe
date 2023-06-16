package xyz.someboringnerd.autoframedupe.Util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ChatUtil
{
    public static void SendMessage(String message)
    {
        MinecraftClient.getInstance().player.sendMessage(Text.of("<AutoFrameDupe By SomeBoringNerd> " + message));
    }
}
