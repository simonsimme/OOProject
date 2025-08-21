package Model.Server.saving;

import Model.Messages.Message;
import Model.Server.ChatChannel;

/**
 * The {@code ChatSaverObserver} class is responsible for observing messages
 * and saving them to a file using a {@link ChatSaver} instance.
 * This class implements the {@link SaveObserver} interface and reacts to messages
 * by saving them to the appropriate channel's log file.
 */
public class ChatSaverObserver implements SaveObserver {
    private final ChatSaver chatSaver;

    /**
     * Constructs a {@code ChatSaverObserver} for the specified chat channel.
     * The observer will save messages to a log file associated with this channel.
     *
     * @param channel the {@link ChatChannel} whose messages will be saved
     */
    public ChatSaverObserver(ChatChannel channel) {
        this.chatSaver = new ChatSaver(channel);
    }

    /**
     * Updates the observer with a new message and saves it to the associated log file.
     * This method is called when a new message is received by the observer.
     *
     * @param message the {@link Message} that will be saved to the log file
     */
    @Override
    public void update(Message message) {
        this.chatSaver.saveMessage(message);
    }

    /**
     * Closes the {@link ChatSaver} and releases any resources associated with it.
     * This method should be called when the observer is no longer needed.
     */
    public void close() {
        chatSaver.close();
    }
}