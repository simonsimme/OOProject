// BasicView.java
package View;

import backend.Messages.*;
import backend.Messages.Message;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
    private JList<String> channelList;
    DefaultListModel<String> listModel = new DefaultListModel<>();

        private JTextField channelNameField;

        @Override
        public String getChannelName() {
            return channelNameField.getText();
        }

        @Override
        public void showCreateChannelScreen() {
            frame.getContentPane().removeAll();
            createChannelScreen();
            frame.revalidate();
            frame.repaint();
        }

    @Override
    public String getPasswordInput() {
        return null;
    }

    @Override
    public String getChannelNameInput() {
        return null;
    }

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
    @Override
    public void addCreateButtonListener(ActionListener listener) {
        createButton.addActionListener(listener);
    }

    public StandardView() {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        startArea();
    }

    @Override
    public String getNickNameFeild() {
        String name = inputTextField.getText();
        if (name.isEmpty() || name.equals("")) {
            name = "Guest";
        }
        return name;
    }

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

    @Override
    public void addCreateChannelButtonListener(ActionListener listener) {
        createChannelButton.addActionListener(listener);
    }

    @Override
    public void addJoinChannelButtonListener(ActionListener listener) {
        joinChannelButton.addActionListener(listener);
    }

    @Override
    public void addCreateNewChannelButtonListener(ActionListener listener) {
        createNewChannelButton.addActionListener(listener);
    }

    @Override
    public void showChatArea() {
        frame.getContentPane().removeAll();
        chatArea();
        frame.revalidate();
        frame.repaint();
    }

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
    public void joinChannel(String channelName) {
        listModel.addElement(channelName);
    }

    @Override
    public void addSendButtonListener(ActionListener listener) {
        sendButton.addActionListener(listener);
    }

    @Override
    public void addJoinNewChannelButtonListener(ActionListener listener) {
        joinNewChannelButton.addActionListener(listener);
    }

    @Override
    public void addLeaveChannelButtonListener(ActionListener listener) {
        leaveChannelButton.addActionListener(listener);
    }

    @Override
    public String getInputText() {
        return inputField.getText();
    }


    @Override
    public void appendChatText(String text) {
        if (chatArea != null) {
            chatArea.append(text + "\n");
        }
    }

    @Override
    public void appendChatText(Message text) {

    }

    @Override
    public void clearInputText() {
        inputField.setText("");
    }
}