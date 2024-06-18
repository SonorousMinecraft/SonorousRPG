package com.sereneoasis.chat;

import com.sereneoasis.chat.builders.ChatBuilder;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class ChatMaster {

    private Set<ChatBuilder>chatBuilders= new HashSet<>();

    private ChatBuilder current;

    public ChatMaster(Player player){
        current = new ChatBuilder();
        chatBuilders.add(current);
        current.openChat(player);
    }

    public ChatMaster(Player player, ChatBuilder chatBuilder){
        current = chatBuilder;
        chatBuilders.add(current);
        current.openChat(player);
    }

    public void next(Player player, int next){
        current.next(player, next);
    }
}
