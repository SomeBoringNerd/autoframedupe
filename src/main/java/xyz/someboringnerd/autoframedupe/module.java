package xyz.someboringnerd.autoframedupe;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;
import xyz.someboringnerd.autoframedupe.Util.ChatUtil;

public class module
{

    ItemFrameEntity[] frames = new ItemFrameEntity[2];

    public static boolean active = false;

    int tick = 0, current = 0;

    public module(){
        instance = this;
    }

    private static module instance;

    public static module getInstance(){
        return instance;
    }

    public void onTick()
    {
        if(!active) return;

        if(frames[0] == null || !frames[0].isAlive())
        {
            getNearbyItemFrames();
        }
        assert MinecraftClient.getInstance().player != null;
        if(tick >= 5)
        {
            if(current == frames.length) current = 0;
            if (frames[current] == null) current = 0;

            else if(frames[current].isAlive())
            {
                if (frames[current].getHeldItemStack().getItem() != Items.AIR)
                {
                    MinecraftClient.getInstance().player.networkHandler.sendPacket(PlayerInteractEntityC2SPacket.attack(frames[current], false));
                } else {
                    MinecraftClient.getInstance().player.networkHandler.sendPacket(PlayerInteractEntityC2SPacket.interact(frames[current], false, Hand.MAIN_HAND));
                }
                current++;
                tick = 0;
            }else{
                active = false;
                active = true;
            }
        }

        tick++;
    }

    private void getNearbyItemFrames()
    {
        int i = 0;
        for (Entity e : MinecraftClient.getInstance().world.getEntities())
        {
            if(e instanceof ItemFrameEntity && e.distanceTo(MinecraftClient.getInstance().player) <= 4)
            {
                ItemFrameEntity entity = (ItemFrameEntity) e;

                if(entity.getHeldItemStack().getItem() == Items.AIR && i < 2)
                {
                    frames[i] = entity;
                    i++;
                }
            }

        }
        if(i == 0)
        {
            ChatUtil.SendMessage("Couldn't find item frames in range");
            active = false;
        }
        else
        {
            ChatUtil.SendMessage(("Found " + i + " item frames"));
        }
    }
}
