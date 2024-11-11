package View;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private JButton createChannelButton;
    private JButton joinChannelButton;
    private JButton joinNewChannelButton;
    private JButton leaveChannelButton;
    private JButton createNewChannelButton;
    private JList<String> channelList;

    public View() {
        // Set the FlatLaf look and feel
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        startArea();
    }

    public void startArea() {
        // Clear the frame content
        if (frame != null) {
            frame.getContentPane().removeAll();
            frame.revalidate();
            frame.repaint();
        } else {
            // Create the main frame
            frame = new JFrame("Chat Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null); // Center the frame
        }

        // Create the main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(43, 43, 43));

        // Create the start area
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

        panel.add(startPanel, BorderLayout.CENTER);

        // Add the panel to the frame
        frame.add(panel);

        // Display the frame
        frame.setVisible(true);
    }

    public void addCreateChannelButtonListener(ActionListener listener) {
        createChannelButton.addActionListener(listener);
    }

    public void addJoinChannelButtonListener(ActionListener listener) {
        joinChannelButton.addActionListener(listener);
    }

    public void addCreateNewChannelButtonListener(ActionListener listener) {
        createNewChannelButton.addActionListener(listener);
    }

    public void showChatArea() {
        frame.getContentPane().removeAll();
        chatArea();
        frame.revalidate();
        frame.repaint();
    }

    private void chatArea() {
        // Create the main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(43, 43, 43));

        // Create the chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        chatArea.setBackground(new Color(60, 63, 65));
        chatArea.setForeground(Color.WHITE);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        panel.add(chatScrollPane, BorderLayout.CENTER);

        // Create the input area
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setBackground(new Color(60, 63, 65));
        inputField.setForeground(Color.WHITE);
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setBackground(new Color(60, 63, 65));
        sendButton.setForeground(Color.WHITE);
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        panel.add(inputPanel, BorderLayout.SOUTH);

        // Create the sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setBackground(new Color(43, 43, 43));

        // Add the channel list
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Channel 1");
        listModel.addElement("Channel 2");
        channelList = new JList<>(listModel);
        channelList.setFont(new Font("Arial", Font.PLAIN, 14));
        channelList.setBackground(new Color(60, 63, 65));
        channelList.setForeground(Color.WHITE);
        JScrollPane channelScrollPane = new JScrollPane(channelList);
        sidebar.add(channelScrollPane, BorderLayout.CENTER);

        // Add the buttons to the sidebar
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

        panel.add(sidebar, BorderLayout.WEST);

        // Add the panel to the frame
        frame.add(panel);

        // Display the frame
        frame.setVisible(true);
    }

    public void addSendButtonListener(ActionListener listener) {
        sendButton.addActionListener(listener);
    }

    public void addJoinNewChannelButtonListener(ActionListener listener) {
        joinNewChannelButton.addActionListener(listener);
    }

    public void addLeaveChannelButtonListener(ActionListener listener) {
        leaveChannelButton.addActionListener(listener);
    }

    public String getInputText() {
        return inputField.getText();
    }

    public void appendChatText(String text) {
        chatArea.append(text + "\n");
    }

    public void clearInputText() {
        inputField.setText("");
    }
}