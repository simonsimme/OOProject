package View;

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

    public View() {
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
            frame.setSize(400, 500);
        }

        // Create the main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create the start area
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new GridLayout(2, 1));
        createChannelButton = new JButton("Create Channel");
        joinChannelButton = new JButton("Join Channel");
        startPanel.add(createChannelButton);
        startPanel.add(joinChannelButton);

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

        // Create the chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        panel.add(chatScrollPane, BorderLayout.CENTER);

        // Create the input area
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        panel.add(inputPanel, BorderLayout.SOUTH);

        // Create the sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(2, 1));
        joinNewChannelButton = new JButton("+ Join New Channel");
        leaveChannelButton = new JButton("Leave Channel");
        sidebar.add(joinNewChannelButton);
        sidebar.add(leaveChannelButton);

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