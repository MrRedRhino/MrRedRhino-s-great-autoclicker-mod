package com.mrredrhino.fancyclicker.BedrockBreakerMain;

import com.mrredrhino.fancyclicker.BedrockBreaker;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.world.ClientWorld;

import java.util.ArrayList;

public class BreakingFlowController {
    private static ArrayList<TargetBlock> cachedTargetBlockList = new ArrayList<>();

    public static boolean isWorking() {
        return working;
    }

    private static boolean working = false;

    public static void addBlockPosToList(BlockPos pos) {
        ClientWorld world = Minecraft.getInstance().world;
        if (world.getBlockState(pos).matchesBlock(Blocks.BEDROCK) || BedrockBreaker.breakEverything) {
            String haveEnoughItems = InventoryManager.warningMessage();
            if (haveEnoughItems != null) {
                Messager.actionBar(haveEnoughItems);
                return;
            }

            if (shouldAddNewTargetBlock(pos)){
                TargetBlock targetBlock = new TargetBlock(pos, world);
                cachedTargetBlockList.add(targetBlock);
            }
        } else {
            Messager.actionBar("Block has to be bedrock or breakEverything has to be turned on");
        }
    }

    public static void tick() {
        if (InventoryManager.warningMessage() != null) {
            return;
        }
        Minecraft minecraftClient = Minecraft.getInstance();
        PlayerEntity player = minecraftClient.player;

        if (!"survival".equals(minecraftClient.playerController.getCurrentGameType().getName())) {
            return;
        }

        for (int i = 0; i < cachedTargetBlockList.size(); i++) {
            TargetBlock selectedBlock = cachedTargetBlockList.get(i);

            //玩家切换世界，或离目标方块太远时，删除所有缓存的任务
            if (selectedBlock.getWorld() != Minecraft.getInstance().world ) {
                cachedTargetBlockList = new ArrayList<TargetBlock>();
                break;
            }

            if (blockInPlayerRange(selectedBlock.getBlockPos(), player)) {
                TargetBlock.Status status = cachedTargetBlockList.get(i).tick();
                if (status == TargetBlock.Status.RETRACTING) {
                    continue;
                } else if (status == TargetBlock.Status.FAILED || status == TargetBlock.Status.RETRACTED) {
                    cachedTargetBlockList.remove(i);
                } else {
                    break;
                }

            }
        }
    }

    private static boolean blockInPlayerRange(BlockPos blockPos, PlayerEntity player) {
        double x = player.getPosX();
        double y = player.getPosY();
        double z = player.getPosZ();

        return (blockPos.distanceSq(x, y, z, true) <= (float) 3.4 * (float) 3.4);
    }

    private static boolean shouldAddNewTargetBlock(BlockPos pos){
        for (TargetBlock targetBlock : cachedTargetBlockList) {
            if (targetBlock.getBlockPos().distanceSq(pos.getX(), pos.getY(), pos.getZ(), false) == 0) {
                return false;
            }
        }
        return true;
    }

    public static void switchOnOff(){
        if (working){
            Messager.actionBar("Bedrock miner was turned OFF");
            working = false;

        } else {
            Messager.chat("Bedrock miner was turned ON");
            working = true;
        }
    }
}
