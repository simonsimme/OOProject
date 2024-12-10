package Model.Server.saving;// src/main/java/Model/Server/saving/ChatSaverObserver.java


import Model.Message;
import Model.Server.ChatChannel;


public class ChatSaverObserver implements SaveObserver {
    private final ChatSaver chatSaver;

    public ChatSaverObserver(ChatChannel channel) {
        this.chatSaver = new ChatSaver(channel);
    }

    @Override
    public void update(String channelName, Message message) {
        this.chatSaver.saveMessage(channelName, message);
    }

    public void close() {
        chatSaver.close();
    }
}