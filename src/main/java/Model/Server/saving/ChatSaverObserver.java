package Model.Server.saving;// src/main/java/Model/Server/saving/ChatSaverObserver.java


import Model.Message;
import Model.Server.ChatChannel;

/**
 * The {@code ChatSaverObserver} class is responsible for observing messages
 * and saving them to a file using a {@link ChatSaver} instance.
 */
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