package com.mrredrhino.fancyclicker;

import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Laggomatic {
    private static final Minecraft MC = Minecraft.getInstance();

    @SubscribeEvent
    public void thing(PlayerInteractEvent event) {
        if (MC.player == null || MC.world == null) {
            return;
        }
        if (MC.world.getBlockState(event.getPos()).matchesBlock(Blocks.LEVER)) {
            for (int i = 0; i < 64; i++) {
                util.sendInteractPacket(event.getPos());
            }
        }
    }

}

