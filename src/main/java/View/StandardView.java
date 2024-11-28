// StandardView.java
package View;

import Model.View.HighlightedChannelRenderer;
import Model.View.IView;
import Model.View.TextFormat;
import backend.Messages.UI.DisplayMessage;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class StandardView implements IView {
    private JFrame frame;
    private JTextPane chatArea;
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
     * Updates the channel list with the given channels and sets the current channel.
     * @param channels List of channel names.
     * @param currentChannel The name of the current channel.
     */
    public void updateChannelList(List<String> channels, String currentChannel) {
        listModel.clear();
        for (String channel : channels) {
            chatArea.setText(chatArea.getText() + channel);
            listModel.addElement(channel);
        }
        channelList.setSelectedValue(currentChannel, true);
        channelRenderer.setCurrentChannel(currentChannel);
        channelList.repaint();
    }

    /**
     * Returns the channel list model.
     * @return The DefaultListModel containing the channel names.
     */
    public DefaultListModel<String> getChannelList() {
        return listModel;
    }

    /**
     * Displays the chat history in the chat area.
     * @param history The chat history to display.
     */
    @Override
    public void showHistory(StringBuilder history) {
        chatArea.setText(history.toString());
    }

    /**
     * Changes the current channel and updates the chat area.
     * @param channelName The name of the new channel.
     */
    @Override
    public void changeChannel(String channelName) {
        if (chatArea != null) {
            chatArea.setText(""); // clear chat
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
     * Prompts the user to enter a channel name.
     * @return The entered channel name.
     */
    @Override
    public String getChannelNameInput() {
        return JOptionPane.showInputDialog(frame, "Enter Channel Name:", "Channel Name", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Prompts the user to enter a password.
     * @return The entered password.
     */
    @Override
    public String getPasswordInput() {
        return JOptionPane.showInputDialog(frame, "Enter Password:", "Password", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Creates the create channel screen UI.
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
     * Adds an ActionListener to the create button.
     * @param listener The ActionListener to add.
     */
    @Override
    public void addCreateButtonListener(ActionListener listener) {
        createButton.addActionListener(listener);
    }

    /**
     * Constructor for StandardView. Initializes the UI components.
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
     * Returns the nickname entered in the input text field.
     * @return The entered nickname or "Guest" if the field is empty.
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
     * Initializes and displays the start area UI.
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
     * Adds an ActionListener to the create channel button.
     * @param listener The ActionListener to add.
     */
    @Override
    public void addCreateChannelButtonListener(ActionListener listener) {
        createChannelButton.addActionListener(listener);
    }

    /**
     * Adds an ActionListener to the join channel button.
     * @param listener The ActionListener to add.
     */
    @Override
    public void addJoinChannelButtonListener(ActionListener listener) {
        joinChannelButton.addActionListener(listener);
    }

    /**
     * Adds an ActionListener to the create new channel button.
     * @param listener The ActionListener to add.
     */
    @Override
    public void addCreateNewChannelButtonListener(ActionListener listener) {
        createNewChannelButton.addActionListener(listener);
    }

    /**
     * Shows the chat area UI.
     */
    @Override
    public void showChatArea() {
        frame.getContentPane().removeAll();
        chatArea();
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Initializes and displays the chat area UI.
     */
    private void chatArea() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(43, 43, 43));

        chatArea = new JTextPane();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        chatArea.setBackground(new Color(60, 63, 65));
        chatArea.setForeground(Color.WHITE);
        chatArea.setContentType("text/html");
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
     * @param channelName The name of the channel to add.
     */
    @Override
    public void addChannelToList(String channelName) {
        listModel.addElement(channelName);
    }

    /**
     * Adds an ActionListener to the send button.
     * @param listener The ActionListener to add.
     */
    @Override
    public void addSendButtonListener(ActionListener listener) {
        sendButton.addActionListener(listener);
    }

    /**
     * Adds an ActionListener to the join new channel button.
     * @param listener The ActionListener to add.
     */
    @Override
    public void addJoinNewChannelButtonListener(ActionListener listener) {
        joinNewChannelButton.addActionListener(listener);
    }

    /**
     * Adds a ListSelectionListener to the channel list.
     * @param listener The ListSelectionListener to add.
     */
    @Override
    public void addChannelListSelectionListener(ListSelectionListener listener) {
        channelList.addListSelectionListener(listener);
    }

    /**
     * Adds an ActionListener to the leave channel button.
     * @param listener The ActionListener to add.
     */
    @Override
    public void addLeaveChannelButtonListener(ActionListener listener) {
        leaveChannelButton.addActionListener(listener);
    }

    /**
     * Returns the text entered in the input field.
     * @return The entered text.
     */
    @Override
    public String getInputText() {
        return inputField.getText();
    }

    /**
     * Appends text to the chat area.
     * @param text The text to append.
     */
    @Override
    public void appendChatText(String text) {
        if (chatArea != null) {
            try {
                StyledDocument doc = chatArea.getStyledDocument();
                doc.insertString(doc.getLength(), text + "\n", null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Appends formatted text to the chat area.
     * @param tf The TextFormat object containing the text and colors.
     */
    @Override
    public void appendChatText(TextFormat tf) {
        if (chatArea != null) {
            StyledDocument doc = chatArea.getStyledDocument();
            for (int i = 0; i < tf.getText().size(); i++) {
                Style style = chatArea.addStyle("ColorStyle", null);
                StyleConstants.setForeground(style, tf.getColors().get(i));
                try {
                    doc.insertString(doc.getLength(), tf.getText().get(i), style);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
            try {
                doc.insertString(doc.getLength(), "\n", null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Appends a DisplayMessage to the chat area.
     * @param text The DisplayMessage object containing the message.
     */
    @Override
    public void appendChatText(DisplayMessage text) {
        if (chatArea != null) {
            try {
                StyledDocument doc = chatArea.getStyledDocument();
                doc.insertString(doc.getLength(), text.getMessage(), null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Removes a channel from the channel list.
     * @param channelName The name of the channel to remove.
     */
    @Override
    public void removeChannelFromList(String channelName) {
        listModel.removeElement(channelName);
    }

    /**
     * Clears the text in the input field.
     */
    @Override
    public void clearInputText() {
        inputField.setText("");
    }
}