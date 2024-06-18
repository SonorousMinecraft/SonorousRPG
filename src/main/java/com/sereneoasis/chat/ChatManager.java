package com.sereneoasis.chat;

import com.sereneoasis.chat.builders.ChatBuilder;
import com.sereneoasis.config.ConfigFile;
import org.bukkit.configuration.file.FileConfiguration;

public class ChatManager {

    private ChatBuilder chatBuilder;

    public ChatBuilder getChatBuilder() {
        return chatBuilder;
    }

    public ChatManager(){
        ConfigFile chat = new ConfigFile("chat");
        FileConfiguration configuration = chat.getConfig();

        int currentIndex = 0;
        chatBuilder = new ChatBuilder();
        while (configuration.contains(String.valueOf(currentIndex))) {
            ChatTypes type = ChatTypes.getFromString(configuration.get(currentIndex + ".TYPE").toString());

            String text = configuration.get(currentIndex + ".TEXT").toString();

            switch (type) {
                case STATEMENT -> {
                    int next = Integer.valueOf(configuration.get(currentIndex + ".NEXT").toString());
                    chatBuilder.addStatement(text, next);
                }
                case QUESTION -> {
                    int next = Integer.valueOf(configuration.get(currentIndex + ".Y").toString());
                    chatBuilder.addStatement(text, next);
                }
                case TERMINAL -> {
                    chatBuilder.addTerminal(text);
                }
            }
            currentIndex += 1;
        }


    }


}
