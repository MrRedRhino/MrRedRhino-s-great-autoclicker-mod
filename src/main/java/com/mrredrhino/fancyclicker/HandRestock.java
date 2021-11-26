package com.mrredrhino.fancyclicker;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.CClickWindowPacket;
import net.minecraft.network.play.client.CCloseWindowPacket;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HandRestock {
    private static final Minecraft MC = Minecraft.getInstance();

    @SubscribeEvent
    public void OnHandRestockNeeded(PlayerInteractEvent event) {
        int bestSlotId = 0;
        int bestSlotCount = 0;
        if (event.getItemStack().getCount() < 5) {  // rechtsklick auf gefundenen slot (wenn < 60 linksklick), linksclick auf selectedSlot
            for (int i = 0; i < MC.player.inventory.mainInventory.size(); i++) {
                if (MC.player.inventory.mainInventory.get(i) == event.getItemStack()) {
                    if (event.getItemStack().getCount() > bestSlotCount) {
                        bestSlotId = i;
                        bestSlotCount = event.getItemStack().getCount();
                    }
                }
            }
            if (bestSlotCount < 60) {
                System.out.println("HELLO");
                NetworkManager network = Minecraft.getInstance().getConnection().getNetworkManager();
                MC.player.inventory.dropAllItems();
                CClickWindowPacket packet = new CClickWindowPacket(0, 9, 0, ClickType.PICKUP, MC.player.inventory.mainInventory.get(9), (short) 0);
                network.sendPacket(packet);
                packet = new CClickWindowPacket(0, 10, 0, ClickType.SWAP, MC.player.inventory.mainInventory.get(20), (short) 0);
                network.sendPacket(packet);
                CCloseWindowPacket packet2 = new CCloseWindowPacket(0);
                network.sendPacket(packet2);
//                CClickWindowPacket packet = new CClickWindowPacket(0, bestSlotId, 0, ClickType.PICKUP_ALL, MC.player.inventory.mainInventory.get(bestSlotId), (short) 0);
//                NetworkManager network = Minecraft.getInstance().getConnection().getNetworkManager();
//                network.sendPacket(packet);
//
//                packet = new CClickWindowPacket(0, MC.player.inventory.currentItem, 0, ClickType.PICKUP_ALL, MC.player.inventory.mainInventory.get(MC.player.inventory.currentItem), (short) 0);
//                network.sendPacket(packet);
            } else {  // DO RIGHTCLICK

            }

            System.out.println("ITEM FOUND");
            System.out.println(bestSlotId);
            System.out.println(bestSlotCount);
        }
    }
}
//