package Model.Messages.UI;

import java.util.List;

/**
 * Message to update the list of channels and set the current channel.
 * This class is used to notify the view about the available channels and the currently active channel.
 */
public class UpdateChannels implements UIMessage {
    /**
     * The list of channel names that are available to the user.
     */
    private final List<String> currentChannelList;

    /**
     * The name of the current channel the user is in.
     */
    private final String currentChannel;

    /**
     * Constructor to create an UpdateChannels message.
     *
     * @param channels A list of available channel names.
     * @param current The name of the current channel the user is in.
     */
    public UpdateChannels(List<String> channels,String current){
        this.currentChannelList = channels;
        this.currentChannel = current;
    }

    /**
     * Gets the list of available channels.
     * @return A list of channel names.
     */
    public List<String> getChannels() {
        return currentChannelList;
    }

    /**
     * Gets the name of the current channel the user is in.
     * @return The name of the current channel.
     */
    public String getCurrentChannel() {
        return currentChannel;
    }

    /**
     * Accepts a visitor to handle this UpdateChannels message.
     * @param visitor The visitor that will handle the message.
     */
    @Override
    public void accept(UIMessageVisitor visitor) {
        visitor.handle(this);
    }
}
