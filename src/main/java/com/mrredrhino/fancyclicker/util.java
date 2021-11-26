package com.mrredrhino.fancyclicker;

import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.CPlayerTryUseItemOnBlockPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public class util {
    static void sendInteractPacket(BlockPos pos) {
        NetworkManager network = Minecraft.getInstance().getConnection().getNetworkManager();
        Vector3d hitvec = new Vector3d(pos.getX(), pos.getY(), pos.getZ());
        BlockRayTraceResult result = new BlockRayTraceResult(hitvec, Direction.UP, pos, false);
        network.sendPacket(new CPlayerTryUseItemOnBlockPacket(Hand.MAIN_HAND, result));
    }
}
