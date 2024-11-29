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
    private static JFrame frame;
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
    private JTextField passwordField;

    /**
     * Updates the channel list with the given channels and sets the current channel.
     * @param channels List of channel names.
     * @param currentChannel The name of the current channel.
     */
    public void updateChannelList(List<String> channels, String currentChannel) {
        if(chatArea == null){
            return;
        }
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
     * Displays an error message in a dialog.
     * @param errorMessage The error message to display.
     */

    @Override
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(frame, errorMessage, "Message", JOptionPane.ERROR_MESSAGE);
    }
    /**
     * Displays an notification of info to the user.
     * @param message notification message.
     */
    @Override
    public void showNotification(String message) {
        JWindow window = new JWindow(frame);
        window.setLayout(new BorderLayout());
        window.setSize(250, 100);
        window.setBackground(new Color(0, 0, 0, 0)); // Set the background to be transparent

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(45, 45, 45));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.setColor(Color.GRAY);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        int fontSize = 20; // Starting font size
        Font font = new Font("Arial", Font.BOLD, fontSize);
        label.setFont(font);
        while (label.getPreferredSize().width > window.getWidth() - 20 && fontSize > 10) {
            fontSize--;
            font = new Font("Arial", Font.BOLD, fontSize);
            label.setFont(font);
        }
        label.setForeground(Color.WHITE);
        panel.add(label, BorderLayout.CENTER);

        window.add(panel);
        window.setAlwaysOnTop(true);

        int x = frame.getX() + frame.getWidth() - window.getWidth() - 10;
        int y = frame.getY() + frame.getHeight() - window.getHeight() - 10;
        window.setLocation(x, y);

        window.setVisible(true);

        Timer timer = new Timer(3000, e -> window.dispose());
        timer.setRepeats(false);
        timer.start();
    }





    /**
     * Prompts the user to enter a channel name and password.
     * @return An array containing the entered channel name and password.
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

        int result = JOptionPane.showConfirmDialog(frame, panel, "Channel " + type, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            return new String[]{channelNameField.getText(), new String(passwordField.getPassword())};
        } else {
            return null;
        }
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
        showNotification("Welcome to the Chat Application!");

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

        JLabel inputLabel = new JLabel("Enter your nickname:");
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