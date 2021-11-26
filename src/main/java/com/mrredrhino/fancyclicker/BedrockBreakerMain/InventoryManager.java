package com.mrredrhino.fancyclicker.BedrockBreakerMain;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.potion.EffectUtils;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.IItemProvider;

public class InventoryManager {
    public static boolean switchToItem(IItemProvider item) {
        Minecraft minecraftClient = Minecraft.getInstance();
        PlayerInventory playerInventory = minecraftClient.player.inventory;

        int i = playerInventory.getSlotFor(new ItemStack(item));

        if ("diamond_pickaxe".equals(item.toString())) {
            i = getEfficientTool(playerInventory);
        }

        if (i != -1) {
            if (PlayerInventory.isHotbar(i)) {
                playerInventory.currentItem = i;
            } else {
                minecraftClient.playerController.pickItem(i);
            }
            minecraftClient.getConnection().sendPacket(new CHeldItemChangePacket(playerInventory.currentItem));
            return true;
        }
        return false;
    }

    private static int getEfficientTool(PlayerInventory playerInventory) {
        for (int i = 0; i < playerInventory.mainInventory.size(); ++i) {
            if (getBlockBreakingSpeed(Blocks.PISTON.getDefaultState(), i) > 45f) {
                return i;
            }
        }
        return -1;
    }

    public static boolean canInstantlyMinePiston() {
        Minecraft minecraftClient = Minecraft.getInstance();
        PlayerInventory playerInventory = minecraftClient.player.inventory;

        for (int i = 0; i < playerInventory.getSizeInventory(); i++) {
            if (getBlockBreakingSpeed(Blocks.PISTON.getDefaultState(), i) > 45f) {
                return true;
            }
        }
        return false;
    }

    private static float getBlockBreakingSpeed(BlockState block, int slot) {
        Minecraft minecraftClient = Minecraft.getInstance();
        PlayerEntity player = minecraftClient.player;
        ItemStack stack = player.inventory.getStackInSlot(slot);

        float f = stack.getDestroySpeed(block);
        if (f > 1.0F) {
            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
            ItemStack itemStack = player.inventory.getStackInSlot(slot);
            if (i > 0 && !itemStack.isEmpty()) {
                f += (float) (i * i + 1);
            }
        }

        if (EffectUtils.hasMiningSpeedup(player)) {
            f *= 1.0F + (float) (EffectUtils.getMiningSpeedup(player) + 1) * 0.2F;
        }

        if (player.isPotionActive(Effects.MINING_FATIGUE)) {
            float k;
            switch (player.getActivePotionEffect(Effects.MINING_FATIGUE).getAmplifier()) {
                case 0:
                    k = 0.3F;
                    break;
                case 1:
                    k = 0.09F;
                    break;
                case 2:
                    k = 0.0027F;
                    break;
                case 3:
                default:
                    k = 8.1E-4F;
            }

            f *= k;
        }

        if (player.areEyesInFluid(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(player)) {
            f /= 5.0F;
        }

        if (!player.isOnGround()) {
            f /= 5.0F;
        }

        return f;
    }

    public static int getInventoryItemCount(IItemProvider item) {

        Minecraft minecraftClient = Minecraft.getInstance();
        PlayerInventory playerInventory = minecraftClient.player.inventory;
        return playerInventory.count(item.asItem());
    }

    public static String warningMessage() {
        Minecraft minecraftClient = Minecraft.getInstance();
        if (!"survival".equals(minecraftClient.playerController.getCurrentGameType().getName())) {
            return "Only works in survival-mode";
        }

        if (InventoryManager.getInventoryItemCount(Blocks.PISTON) < 2) {
            return "You need at least 2 pistons";
        }

        if (InventoryManager.getInventoryItemCount(Blocks.REDSTONE_TORCH) < 1) {
            return "You need at least 1 redstone torch";
        }

        if (InventoryManager.getInventoryItemCount(Blocks.SLIME_BLOCK) < 1){
            return "You need at least 1 slime-block";
        }

        if (!InventoryManager.canInstantlyMinePiston()) {
            return "You have to be able to insta-mine stone";
        }
        return null;
    }
}
