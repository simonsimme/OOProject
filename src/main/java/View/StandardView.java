// StandardView.java
package View;

import backend.Messages.*;
import backend.Messages.Message;
import backend.Messages.UI.DisplayMessage;
import backend.Messages.UI.UIMessage;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class StandardView implements IView {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextArea inputField;
    private JTextField inputTextField = new JTextField(20);
    private JButton sendButton;
    private JButton createChannelButton;
    private JButton createChannelButtonItem;
    private JButton createButton;
    private JButton joinChannelButton;
    private JButton joinNewChannelButton;
    private JButton leaveChannelButton;
    private JButton createNewChannelButton;
    private JList<String> channelList = new JList<>();
    private HighlightedChannelRenderer channelRenderer;
    DefaultListModel<String> listModel = new DefaultListModel<>();

    private JTextField channelNameField;

    /**
     * Updates the channel list in the view.
     * @param channels the list of channel names.
     * @param currentChannel the name of the current channel.
     */
    public void updateChannelList(List<String> channels, String currentChannel) {
        listModel.clear();
        for (String channel : channels) {
            chatArea.append(channel);
            listModel.addElement(channel);
        }
        channelList.setSelectedValue(currentChannel, true);
        channelRenderer.setCurrentChannel(currentChannel);
        channelList.repaint();
    }

    /**
     * Returns the list model for the channel list.
     * @return the list model for the channel list.
     */
    public DefaultListModel<String> getChannelList() {
        return listModel;
    }

    /**
     * Displays the chat history in the chat area.
     * @param history the chat history to display.
     */
    @Override
    public void showHistory(StringBuilder history) {
        chatArea.setText(history.toString());
    }

    /**
     * Changes the current channel in the view.
     * @param channelName the name of the new channel.
     */
    @Override
    public void changeChannel(String channelName) {
        if (chatArea != null) {
            chatArea.setText(""); // clear chat
            chatArea.append("Switched to channel: " + channelName + "\n");
            channelRenderer.setCurrentChannel(channelName);
            channelList.repaint();
        }
    }

    /**
     * Shows the create channel screen.
     */
    @Override
    public void showCreateChannelScreen() {
        frame.getContentPane().removeAll();
        createChannelScreen();
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Gets the channel name input from the user.
     * @return the channel name input.
     */
    @Override
    public String getChannelNameInput() {
        return JOptionPane.showInputDialog(frame, "Enter Channel Name:", "Channel Name", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Gets the password input from the user.
     * @return the password input.
     */
    @Override
    public String getPasswordInput() {
        return JOptionPane.showInputDialog(frame, "Enter Password:", "Password", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Creates the create channel screen.
     */
    private void createChannelScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(43, 43, 43));

        JPanel createChannelPanel = new JPanel();
        createChannelPanel.setLayout(new GridBagLayout());
        createChannelPanel.setBackground(new Color(43, 43, 43));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel channelNameLabel = new JLabel("Channel Name:");
        channelNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        channelNameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        createChannelPanel.add(channelNameLabel, gbc);

        channelNameField = new JTextField(20);
        channelNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        channelNameField.setBackground(new Color(60, 63, 65));
        channelNameField.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 0;
        createChannelPanel.add(channelNameField, gbc);

        createButton = new JButton("Create");
        createButton.setFont(new Font("Arial", Font.BOLD, 16));
        createButton.setBackground(new Color(60, 63, 65));
        createButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        createChannelPanel.add(createButton, gbc);

        panel.add(createChannelPanel, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * Adds a listener for the create button.
     * @param listener the listener to add.
     */
    @Override
    public void addCreateButtonListener(ActionListener listener) {
        createButton.addActionListener(listener);
    }

    /**
     * Constructs a new StandardView and sets the look and feel.
     */
    public StandardView() {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        channelRenderer = new HighlightedChannelRenderer("None");
        channelList.setCellRenderer(channelRenderer);
        startArea();
    }

    /**
     * Gets the nickname field input.
     * @return the nickname field input.
     */
    @Override
    public String getNickNameFeild() {
        String name = inputTextField.getText();
        if (name.isEmpty() || name.equals("")) {
            name = "Guest";
        }
        return name;
    }

    /**
     * Shows the start area of the application.
     */
    @Override
    public void startArea() {
        if (frame != null) {
            frame.getContentPane().removeAll();
            frame.revalidate();
            frame.repaint();
        } else {
            frame = new JFrame("Chat Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(43, 43, 43));

        JPanel startPanel = new JPanel();
        startPanel.setLayout(new GridBagLayout());
        startPanel.setBackground(new Color(43, 43, 43));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        createChannelButton = new JButton("Create Channel");
        createChannelButton.setFont(new Font("Arial", Font.BOLD, 16));
        createChannelButton.setBackground(new Color(60, 63, 65));
        createChannelButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        startPanel.add(createChannelButton, gbc);

        joinChannelButton = new JButton("Join Channel");
        joinChannelButton.setFont(new Font("Arial", Font.BOLD, 16));
        joinChannelButton.setBackground(new Color(60, 63, 65));
        joinChannelButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        startPanel.add(joinChannelButton, gbc);

        inputTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputTextField.setBackground(new Color(60, 63, 65));
        inputTextField.setForeground(Color.WHITE);
        inputTextField.setToolTipText("Nickname");
        gbc.gridx = 0;
        gbc.gridy = 2;
        startPanel.add(inputTextField, gbc);

        panel.add(startPanel, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * Adds a listener for the create channel button.
     * @param listener the listener to add.
     */
    @Override
    public void addCreateChannelButtonListener(ActionListener listener) {
        createChannelButton.addActionListener(listener);
    }

    /**
     * Adds a listener for the join channel button.
     * @param listener the listener to add.
     */
    @Override
    public void addJoinChannelButtonListener(ActionListener listener) {
        joinChannelButton.addActionListener(listener);
    }

    /**
     * Adds a listener for the create new channel button.
     * @param listener the listener to add.
     */
    @Override
    public void addCreateNewChannelButtonListener(ActionListener listener) {
        createNewChannelButton.addActionListener(listener);
    }

    /**
     * Shows the chat area of the application.
     */
    @Override
    public void showChatArea() {
        frame.getContentPane().removeAll();
        chatArea();
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Creates the chat area.
     */
    private void chatArea() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(43, 43, 43));

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        chatArea.setBackground(new Color(60, 63, 65));
        chatArea.setForeground(Color.WHITE);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputField = new JTextArea(1, 20);
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setBackground(new Color(60, 63, 65));
        inputField.setForeground(Color.WHITE);
        inputField.setLineWrap(true);
        inputField.setWrapStyleWord(true);
        JScrollPane inputScrollPane = new JScrollPane(inputField);
        inputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        inputScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setBackground(new Color(60, 63, 65));
        sendButton.setForeground(Color.WHITE);
        inputPanel.add(inputScrollPane, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && !e.isShiftDown()) {
                    sendButton.doClick();
                    e.consume();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isShiftDown()) {
                    inputField.append("\n");
                    e.consume();
                }
            }
        });

        panel.add(inputPanel, BorderLayout.SOUTH);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setBackground(new Color(43, 43, 43));

        channelList = new JList<>(listModel);
        channelList.setFont(new Font("Arial", Font.PLAIN, 14));
        channelList.setBackground(new Color(60, 63, 65));
        channelList.setForeground(Color.WHITE);
        JScrollPane channelScrollPane = new JScrollPane(channelList);
        sidebar.add(channelScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));
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

        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * Adds a channel to the channel list.
     * @param channelName the name of the channel to add.
     */
    @Override
    public void addChannelToList(String channelName) {
        listModel.addElement(channelName);
    }

    /**
     * Adds a listener for the send button.
     * @param listener the listener to add.
     */
    @Override
    public void addSendButtonListener(ActionListener listener) {
        sendButton.addActionListener(listener);
    }

    /**
     * Adds a listener for the join new channel button.
     * @param listener the listener to add.
     */
    @Override
    public void addJoinNewChannelButtonListener(ActionListener listener) {
        joinNewChannelButton.addActionListener(listener);
    }

    /**
     * Adds a listener for the channel list selection.
     * @param listener the listener to add.
     */
    @Override
    public void addChannelListSelectionListener(ListSelectionListener listener) {
        channelList.addListSelectionListener(listener);
    }

    /**
     * Adds a listener for the leave channel button.
     * @param listener the listener to add.
     */
    @Override
    public void addLeaveChannelButtonListener(ActionListener listener) {
        leaveChannelButton.addActionListener(listener);
    }

    /**
     * Gets the input text from the input field.
     * @return the input text.
     */
    @Override
    public String getInputText() {
        return inputField.getText();
    }

    /**
     * Appends text to the chat area.
     * @param text the text to append.
     */
    @Override
    public void appendChatText(String text) {
        if (chatArea != null) {
            chatArea.append(text + "\n");
        }
    }
    @Override
    public void appendChatText(TextFormat tf) {
        if (chatArea != null) {
            for (int i = 0; i < tf.getText().size(); i++) {
                chatArea.append(tf.getText().get(i));
            }
            chatArea.append("\n");
        }
    }

    /**
     * Appends a message to the chat area.
     * @param text the message to append.
     */
    @Override
    public void appendChatText(DisplayMessage text) {
        if (chatArea != null) {
            chatArea.append(text.getMessage());
        }
    }

    /**
     * Removes a channel from the channel list.
     * @param channelName the name of the channel to remove.
     */
    @Override
    public void removeChannelFromList(String channelName) {
        listModel.removeElement(channelName);
    }

    /**
     * Clears the input text field.
     */
    @Override
    public void clearInputText() {
        inputField.setText("");
    }
}