package com.mrredrhino.fancyclicker;

import com.mrredrhino.fancyclicker.BedrockBreakerMain.BreakingFlowController;
import net.minecraft.block.Blocks;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.mrredrhino.fancyclicker.fancyclicker.bedrockBreakerHotkey;

public class BedrockBreaker {
    public static boolean breakEverything = false;
    boolean LMBIsPressed = false;
    @SubscribeEvent
    public void Tick(TickEvent event) {
        if (BreakingFlowController.isWorking()) {
            BreakingFlowController.tick();
        }
    }

    @SubscribeEvent
    public void MouseInput(InputEvent.KeyInputEvent event) {
        if (bedrockBreakerHotkey.isPressed()) {
            BreakingFlowController.switchOnOff();
        }
    }

    @SubscribeEvent
    public void BreakHandler(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getWorld().getBlockState(event.getPos()).matchesBlock(Blocks.BEDROCK) && BreakingFlowController.isWorking() && LMBIsPressed) {
            BreakingFlowController.addBlockPosToList(event.getPos());
        }
    }

    @SubscribeEvent
    public void MouseInput(InputEvent.MouseInputEvent event) {
        LMBIsPressed = event.getAction() == 1;
    }
}
