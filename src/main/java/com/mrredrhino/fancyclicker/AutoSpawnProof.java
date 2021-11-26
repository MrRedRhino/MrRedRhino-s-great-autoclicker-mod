package com.mrredrhino.fancyclicker;

import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.mrredrhino.fancyclicker.fancyclicker.spawnProofHotkey;

public class AutoSpawnProof {
//    int lightEngineCooldown = 0;
    boolean running = false;

    @SubscribeEvent
    public void onModeToggle(InputEvent.KeyInputEvent event) {
        if (spawnProofHotkey.isPressed() && event.getAction() == 1) {
            if (!running) {
                running = true;
            } else {
                running = false;
            }

            System.out.println("PRESSIERT!!!!1!!!1");
        }
    }

    @SubscribeEvent
    public void tick(TickEvent event) {
        Minecraft MC = fancyclicker.MC;
        boolean placed = false;
        if (running) {
            for (int y = 3; y > -3; y--) {
                for (int x = -3; x < 3; x++) {
                    for (int z = -3; z < 3; z++) {
                        BlockPos playerPos = MC.player.getPosition();
                        BlockPos blockPos = new BlockPos(x + playerPos.getX(), y + playerPos.getY(), z + playerPos.getZ());
                        if (MC.world.getBlockState(blockPos).matchesBlock(Blocks.AIR) && MC.world.getBlockState(blockPos.down()).isSolid() && !MC.world.getBlockState(blockPos.down()).matchesBlock(Blocks.COBBLESTONE_SLAB)) { // getBlockState(blockPos.down()).matchesBlock(Blocks.AIR)
                            if (MC.player.getHeldItem(Hand.MAIN_HAND).getItem() == Items.COBBLESTONE_SLAB) {
                                util.sendInteractPacket(blockPos);
                                placed = true;
                            }
                        }
                    } // 15 - MC.world.getLightFor(LightType.BLOCK, blockPos.down()) > 7 &&
                }
                if (placed) {
                    placed = false;
                    return;
                }
            }
        }
    }
}