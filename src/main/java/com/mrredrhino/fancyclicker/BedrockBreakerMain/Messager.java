package com.mrredrhino.fancyclicker.BedrockBreakerMain;


import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.UUID;

public class Messager {
    public static void actionBar(String message){
        Minecraft minecraftClient = Minecraft.getInstance();
        ITextComponent text = new StringTextComponent(message);
        minecraftClient.ingameGUI.setOverlayMessage(text,false);
    }

    public static void chat(String message){
        Minecraft minecraftClient = Minecraft.getInstance();
        ITextComponent text = new StringTextComponent(message);
        minecraftClient.ingameGUI.sendChatMessage(ChatType.SYSTEM, text, UUID.randomUUID());
    }
}

