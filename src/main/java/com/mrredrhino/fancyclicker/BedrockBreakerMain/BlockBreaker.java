package com.mrredrhino.fancyclicker.BedrockBreakerMain;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class BlockBreaker {
    public static void breakBlock(BlockPos pos) {
        InventoryManager.switchToItem(Items.DIAMOND_PICKAXE);
        Minecraft.getInstance().playerController.clickBlock(pos, Direction.UP);
    }
}