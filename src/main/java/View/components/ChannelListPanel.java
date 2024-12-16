package View.components;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

/**
 * The ChannelListPanel class represents the panel that displays the list of channels.
 * It contains a JList for displaying channel names and provides methods to update and manage the channel list.
 */
public class ChannelListPanel {
    private JList<String> channelList;
    private DefaultListModel<String> listModel;

    /**
     * Constructs a new ChannelListPanel and initializes the UI components.
     */
    public ChannelListPanel() {
        listModel = new DefaultListModel<>();
        channelList = new JList<>(listModel);
        channelList.setFont(new Font("Arial", Font.PLAIN, 14));
        channelList.setBackground(new Color(60, 63, 65));
        channelList.setForeground(Color.WHITE);
    }

    /**
     * Returns a JScrollPane containing the channel list.
     *
     * @return the JScrollPane containing the channel list
     */
    public JScrollPane getChannelScrollPane() {
        return new JScrollPane(channelList);
    }

    /**
     * Updates the channel list with the provided channels and selects the current channel.
     *
     * @param channels the list of channel names
     * @param currentChannel the name of the current channel
     */
    public void updateChannelList(List<String> channels, String currentChannel) {
        listModel.clear();
        for (String channel : channels) {
            listModel.addElement(channel);
        }
        channelList.setSelectedValue(currentChannel, true);
        channelList.repaint();
    }

    /**
     * Returns the channel list model.
     *
     * @return the DefaultListModel containing the channel names
     */
    public DefaultListModel<String> getChannelList() {
        return listModel;
    }

    /**
     * Adds a listener for the channel list selection.
     *
     * @param listener the ListSelectionListener to add
     */
    public void addChannelListSelectionListener(ListSelectionListener listener) {
        channelList.addListSelectionListener(listener);
    }

    /**
     * Removes a channel from the channel list.
     *
     * @param channelName the name of the channel to remove
     */
    public void removeChannelFromList(String channelName) {
        listModel.removeElement(channelName);
    }
}