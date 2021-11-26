package com.mrredrhino.fancyclicker.BedrockBreakerMain;

import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import static net.minecraft.block.Block.hasEnoughSolidSide;

import java.util.ArrayList;

public class CheckingEnvironment {

    public static BlockPos findNearbyFlatBlockToPlaceRedstoneTorch(ClientWorld world, BlockPos blockPos) {
        if ((hasEnoughSolidSide(world, blockPos.east(), Direction.UP) && (world.getBlockState(blockPos.east().up()).getMaterial().isReplaceable()) || world.getBlockState(blockPos.east().up()).matchesBlock(Blocks.REDSTONE_TORCH))) {
            return blockPos.east();
        } else if ((hasEnoughSolidSide(world, blockPos.west(), Direction.UP) && (world.getBlockState(blockPos.west().up()).getMaterial().isReplaceable()) || world.getBlockState(blockPos.west().up()).matchesBlock(Blocks.REDSTONE_TORCH))) {
            return blockPos.west();
        } else if ((hasEnoughSolidSide(world, blockPos.north(), Direction.UP) && (world.getBlockState(blockPos.north().up()).getMaterial().isReplaceable()) || world.getBlockState(blockPos.north().up()).matchesBlock(Blocks.REDSTONE_TORCH))) {
            return blockPos.north();
        } else if ((hasEnoughSolidSide(world, blockPos.south(), Direction.UP) && (world.getBlockState(blockPos.south().up()).getMaterial().isReplaceable()) || world.getBlockState(blockPos.south().up()).matchesBlock(Blocks.REDSTONE_TORCH))) {
            return blockPos.south();
        }
        return null;
    }

    public static BlockPos findPossibleSlimeBlockPos(ClientWorld world, BlockPos blockPos) {
        if (world.getBlockState(blockPos.east()).getMaterial().isReplaceable() && (world.getBlockState(blockPos.east().up()).getMaterial().isReplaceable())) {
            return blockPos.east();
        } else if (world.getBlockState(blockPos.west()).getMaterial().isReplaceable() && (world.getBlockState(blockPos.west().up()).getMaterial().isReplaceable())) {
            return blockPos.west();
        } else if (world.getBlockState(blockPos.south()).getMaterial().isReplaceable() && (world.getBlockState(blockPos.south().up()).getMaterial().isReplaceable())) {
            return blockPos.south();
        } else if (world.getBlockState(blockPos.north()).getMaterial().isReplaceable() && (world.getBlockState(blockPos.north().up()).getMaterial().isReplaceable())) {
            return blockPos.north();
        }
        return null;
    }

    public static boolean has2BlocksOfPlaceToPlacePiston(ClientWorld world, BlockPos blockPos) {
        if (world.getBlockState(blockPos.up()).getBlockHardness(world, blockPos.up()) == 0) {
            BlockBreaker.breakBlock(blockPos.up());
        }
        return world.getBlockState(blockPos.up()).getMaterial().isReplaceable() && world.getBlockState(blockPos.up().up()).getMaterial().isReplaceable();
    }

    public static ArrayList<BlockPos> findNearbyRedstoneTorch(ClientWorld world, BlockPos pistonBlockPos) {
        ArrayList<BlockPos> list = new ArrayList<>();
        if (world.getBlockState(pistonBlockPos.east()).matchesBlock(Blocks.REDSTONE_TORCH)) {
            list.add(pistonBlockPos.east());
        }
        if (world.getBlockState(pistonBlockPos.west()).matchesBlock(Blocks.REDSTONE_TORCH)) {
            list.add(pistonBlockPos.west());
        }
        if (world.getBlockState(pistonBlockPos.south()).matchesBlock(Blocks.REDSTONE_TORCH)) {
            list.add(pistonBlockPos.south());
        }
        if (world.getBlockState(pistonBlockPos.north()).matchesBlock(Blocks.REDSTONE_TORCH)) {
            list.add(pistonBlockPos.north());
        }
        return list;
    }
}