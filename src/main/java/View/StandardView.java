package View;

import Model.Messages.UI.DisplayMessage;
import View.components.*;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The StandardView class implements the IView interface and provides the user interface for the application.
 * It manages the main window, chat area, input panel, and channel list panel.
 */
public class StandardView implements IView {
    private final WindowManager windowManager;
    private final NotificationSystem notificationSystem;
    private final ChatArea chatArea;
    private final InputPanel inputPanel;
    private final ChannelListPanel channelListPanel;
    private static int guestUser = 0;

    private final JButton createChannelButton;
    private final JButton joinChannelButton;
    private final JTextField inputTextField;
    private final List<ActionListener> createChannelButtonListeners = new ArrayList<>();
    private final List<ActionListener> joinChannelButtonListeners = new ArrayList<>();
    private JButton joinNewChannelButton;
    private JButton leaveChannelButton;
    private JButton createNewChannelButton;

    /**
     * Constructs a new StandardView and initializes the UI components.
     */
    public StandardView() {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        windowManager = new WindowManager();
        chatArea = new ChatArea();
        inputPanel = new InputPanel();
        channelListPanel = new ChannelListPanel();
        notificationSystem = new NotificationManager(windowManager);

        createChannelButton = new JButton("Create Channel");
        joinChannelButton = new JButton("Join Channel");
        inputTextField = new JTextField(20);

        startArea();
        guestUser++;
        notificationSystem.showNotification("Welcome " + "Guest" + guestUser + "!");
    }

    @Override
    public NotificationSystem getNotificationSystem() {
        return notificationSystem;
    }

    /**
     * Initializes and displays the start area of the application.
     */
    @Override
    public void startArea() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(43, 43, 43));

        JPanel startPanel = new JPanel(new GridBagLayout());
        startPanel.setBackground(new Color(43, 43, 43));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        createChannelButton.setFont(new Font("Arial", Font.BOLD, 16));
        createChannelButton.setBackground(new Color(60, 63, 65));
        createChannelButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        startPanel.add(createChannelButton, gbc);

        joinChannelButton.setFont(new Font("Arial", Font.BOLD, 16));
        joinChannelButton.setBackground(new Color(60, 63, 65));
        joinChannelButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        startPanel.add(joinChannelButton, gbc);

        JLabel inputLabel = new JLabel("Change Your Nickname:");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        startPanel.add(inputLabel, gbc);

        inputTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputTextField.setBackground(new Color(60, 63, 65));
        inputTextField.setForeground(Color.WHITE);
        inputTextField.setToolTipText("Nickname");
        gbc.gridx = 0;
        gbc.gridy = 3;
        startPanel.add(inputTextField, gbc);

        panel.add(startPanel, BorderLayout.CENTER);

        windowManager.setContentPane(panel);
        windowManager.showWindow();

        // Add action listeners
        createChannelButton.addActionListener(e -> {
            for (ActionListener listener : createChannelButtonListeners) {
                listener.actionPerformed(e);
            }
        });

        joinChannelButton.addActionListener(e -> {
            for (ActionListener listener : joinChannelButtonListeners) {
                listener.actionPerformed(e);
            }
        });
    }

    /**
     * Displays the chat area of the application.
     */
    @Override
    public void showChatArea() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(43, 43, 43));

        JScrollPane chatScrollPane = chatArea.getChatScrollPane();
        JPanel inputPanelComponent = inputPanel.getInputPanel();
        JScrollPane channelScrollPane = channelListPanel.getChannelScrollPane();

        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(new Color(43, 43, 43));
        sidebar.add(channelScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        buttonPanel.setBackground(new Color(43, 43, 43));
         joinNewChannelButton = new JButton("+ Join New Channel");
        joinNewChannelButton.setFont(new Font("Arial", Font.BOLD, 14));
        joinNewChannelButton.setBackground(new Color(60, 63, 65));
        joinNewChannelButton.setForeground(Color.WHITE);
         leaveChannelButton = new JButton("Leave Channel");
        leaveChannelButton.setFont(new Font("Arial", Font.BOLD, 14));
        leaveChannelButton.setBackground(new Color(60, 63, 65));
        leaveChannelButton.setForeground(Color.WHITE);
         createNewChannelButton = new JButton("Create New Channel");
        createNewChannelButton.setFont(new Font("Arial", Font.BOLD, 14));
        createNewChannelButton.setBackground(new Color(60, 63, 65));
        createNewChannelButton.setForeground(Color.WHITE);
        buttonPanel.add(joinNewChannelButton);
        buttonPanel.add(leaveChannelButton);
        buttonPanel.add(createNewChannelButton);

        sidebar.add(buttonPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, chatScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);

        panel.add(splitPane, BorderLayout.CENTER);
        panel.add(inputPanelComponent, BorderLayout.SOUTH);

        windowManager.setContentPane(panel);
    }

    /**
     * Clears the chat text area.
     */
    @Override
    public void clearChatText() {
        chatArea.clearChatText();
    }

    /**
     * Updates the channel list with the provided channels and selects the current channel.
     *
     * @param channels      the list of channel names
     * @param currentChannel the name of the current channel
     */
    @Override
    public void updateChannelList(List<String> channels, String currentChannel) {
        channelListPanel.updateChannelList(channels, currentChannel);
    }

    /**
     * Returns the channel list model.
     *
     * @return the DefaultListModel containing the channel names
     */
    @Override
    public DefaultListModel<String> getChannelList() {
        return channelListPanel.getChannelList();
    }


    /**
     * Changes the current channel and clears the chat text.
     *
     * @param channelName the name of the new channel
     */
    @Override
    public void changeChannel(String channelName) {
        chatArea.clearChatText();
    }

    /**
     * Displays an error message in a dialog.
     *
     * @param errorMessage the error message to display
     */
    @Override
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(windowManager.getFrame(), errorMessage, "Message", JOptionPane.ERROR_MESSAGE);
    }



    /**
     * Prompts the user to input a channel name and password.
     *
     * @param type the type of input dialog (e.g., "Join" or "Create")
     * @return an array containing the channel name and password, or null if the input was canceled
     */
    @Override
    public String[] getChannelNameAndPasswordInput(String type) {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JLabel channelNameLabel = new JLabel("Channel Name:");
        JLabel passwordLabel = new JLabel("Password:");
        JTextField channelNameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        panel.add(channelNameLabel);
        panel.add(channelNameField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(windowManager.getFrame(), panel, "Channel " + type, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            return new String[]{channelNameField.getText(), new String(passwordField.getPassword())};
        } else {
            return null;
        }
    }



    /**
     * Returns the text from the nickname input field.
     *
     * @return the nickname text
     */
    @Override
    public String getNickNameFeild() {
        if(inputTextField.getText().isEmpty()){
            return "Guest" + guestUser;
        }
        return inputTextField.getText();}

    /**
     * Adds a listener for the create channel button.
     *
     * @param listener the ActionListener to add
     */
    @Override
    public void addCreateChannelButtonListener(ActionListener listener) {
        createChannelButtonListeners.add(listener);
    }

    /**
     * Adds a listener for the join channel button.
     *
     * @param listener the ActionListener to add
     */
    @Override
    public void addJoinChannelButtonListener(ActionListener listener) {
        joinChannelButtonListeners.add(listener);
    }

    /**
     * Adds a listener for the create new channel button.
     *
     * @param listener the ActionListener to add
     */
    @Override
    public void addCreateNewChannelButtonListener(ActionListener listener) {
        createNewChannelButton.addActionListener(listener);
    }

    /**
     * Adds a listener for the send button in the input panel.
     *
     * @param listener the ActionListener to add
     */
    @Override
    public void addSendButtonListener(ActionListener listener) {
        inputPanel.addSendButtonListener(listener);
    }

    /**
     * Adds a listener for the join new channel button.
     *
     * @param listener the ActionListener to add
     */
    @Override
    public void addJoinNewChannelButtonListener(ActionListener listener) {
        joinNewChannelButton.addActionListener(listener);
    }

    /**
     * Adds a listener for the channel list selection.
     *
     * @param listener the ListSelectionListener to add
     */
    @Override
    public void addChannelListSelectionListener(ListSelectionListener listener) {
        channelListPanel.addChannelListSelectionListener(listener);
    }

    /**
     * Adds a listener for the leave channel button.
     *
     * @param listener the ActionListener to add
     */
    @Override
    public void addLeaveChannelButtonListener(ActionListener listener) {
        leaveChannelButton.addActionListener(listener);
    }

    /**
     * Adds a listener for the window exit event.
     *
     * @param listener the WindowListener to add
     */
    @Override
    public void addWindowExitListener(WindowListener listener) {
        windowManager.addWindowListener(listener);
    }

    /**
     * Closes the application window.
     */
    @Override
    public void closeWindow() {
        windowManager.closeWindow();
    }



    /**
     * Appends a DisplayMessage to the chat area.
     *
     * @param text the DisplayMessage to append
     */
    @Override
    public void appendChatText(DisplayMessage text) {
        chatArea.appendChatText(text);
    }

    /**
     * Appends formatted text to the chat area.
     *
     * @param tf the TextFormat to append
     */
    @Override
    public void appendChatText(TextFormat tf) {
        chatArea.appendChatText(tf);
    }

    /**
     * Removes a channel from the channel list.
     *
     * @param channelName the name of the channel to remove
     */
    @Override
    public void removeChannelFromList(String channelName) {
        channelListPanel.removeChannelFromList(channelName);
    }

    /**
     * Returns the text from the input panel.
     *
     * @return the input text
     */
    @Override
    public String getInputText() {
        return inputPanel.getInputText();
    }

    /**
     * Clears the text in the input panel.
     */
    @Override
    public void clearInputText() {
        inputPanel.clearInputText();
    }
}