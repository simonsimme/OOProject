package Model.Server.saving;// src/main/java/Model/Server/saving/ChatSaverObserver.java


import Model.Server.ChatChannel;
import Model.Server.saving.ChatSaver;
import Model.Server.saving.SaveObserver;

public class ChatSaverObserver implements SaveObserver {
    private final ChatSaver chatSaver;

    public ChatSaverObserver(ChatChannel channel) {
        this.chatSaver = new ChatSaver(channel);
    }

    @Override
    public void update(String userName, String channelName, String message) {
        this.chatSaver.saveMessage(userName, channelName, message);
    }

    public void close() {
        chatSaver.close();
    }
}