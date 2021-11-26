package com.mrredrhino.fancyclicker.BedrockBreakerMain;

import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemOnBlockPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public class BlockPlacer {
    public static void simpleBlockPlacement(BlockPos pos, IItemProvider item) {
        Minecraft minecraftClient = Minecraft.getInstance();

        InventoryManager.switchToItem(item);
        BlockRayTraceResult hitResult = new BlockRayTraceResult(new Vector3d(pos.getX(), pos.getY(), pos.getZ()), Direction.UP, pos, false);
//        minecraftClient.interactionManager.interactBlock(minecraftClient.player, minecraftClient.world, Hand.MAIN_HAND, hitResult);
        placeBlockWithoutInteractingBlock(minecraftClient, hitResult);
    }

    public static void pistonPlacement(BlockPos pos, Direction direction) {
        Minecraft minecraftClient = Minecraft.getInstance();
        double x = pos.getX();

        PlayerEntity player = minecraftClient.player;
        float pitch;
        switch (direction) {
            case UP:
                pitch = 90f;
                break;
            case DOWN:
                pitch = -90f;
                break;
            default:
                pitch = 90f;
                break;
        }

        minecraftClient.getConnection().sendPacket(new CPlayerPacket.RotationPacket(player.getYaw(1.0f), pitch, player.isOnGround()));

        Vector3d vec3d = new Vector3d(x, pos.getY(), pos.getZ());

        InventoryManager.switchToItem(Blocks.PISTON);
        BlockRayTraceResult hitResult = new BlockRayTraceResult(vec3d, Direction.UP, pos, false);
//        minecraftClient.interactionManager.interactBlock(minecraftClient.player, minecraftClient.world, Hand.MAIN_HAND, hitResult);
        placeBlockWithoutInteractingBlock(minecraftClient, hitResult);
    }

    private static void placeBlockWithoutInteractingBlock(Minecraft minecraftClient, BlockRayTraceResult hitResult) {
        ClientPlayerEntity player = minecraftClient.player;
        ItemStack itemStack = player.getHeldItem(Hand.MAIN_HAND);

        minecraftClient.getConnection().sendPacket(new CPlayerTryUseItemOnBlockPacket(Hand.MAIN_HAND, hitResult));

        if (!itemStack.isEmpty() && !player.getCooldownTracker().hasCooldown(itemStack.getItem())) {
            ItemUseContext itemUsageContext = new ItemUseContext(player, Hand.MAIN_HAND, hitResult);
            itemStack.onItemUse(itemUsageContext);

        }
    }
}
