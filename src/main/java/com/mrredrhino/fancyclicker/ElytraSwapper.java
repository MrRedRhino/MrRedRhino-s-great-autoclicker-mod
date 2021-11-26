package com.mrredrhino.fancyclicker;

import com.mrredrhino.fancyclicker.BedrockBreakerMain.Messager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.CClickWindowPacket;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import static com.mrredrhino.fancyclicker.fancyclicker.elytraHotkey;

public class ElytraSwapper {
    @SubscribeEvent
    public void onInput(InputEvent.KeyInputEvent event) {
        if (fancyclicker.MC.player == null) {
            return;
        }

        PlayerInventory inv = fancyclicker.MC.player.inventory;

        if (elytraHotkey.isPressed() && !inv.getStackInSlot(38).getStack().isEmpty()) {
            if (!inv.getStackInSlot(9).getStack().canEquip(EquipmentSlotType.CHEST, fancyclicker.MC.player)) {
                Messager.actionBar("The item to be swapped has to be in the top-left slot of your inventory");
                return;
            }

            NetworkManager network = fancyclicker.MC.getConnection().getNetworkManager();
            CClickWindowPacket packet0 = new CClickWindowPacket(0, 6, 0, ClickType.PICKUP, inv.getStackInSlot(38), (short) 0);
            CClickWindowPacket packet1 = new CClickWindowPacket(0, 9, 0, ClickType.PICKUP, inv.getStackInSlot(9), (short) 0);
            network.sendPacket(packet0);
            network.sendPacket(packet1);
            network.sendPacket(packet0);
        }
    }
}
