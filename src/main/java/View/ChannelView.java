// ChannelView.java
package View;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChannelView extends JPanel {
    private JTextArea chatArea;
    private JTextArea inputField;
    private JButton sendButton;

    public ChannelView() {
        setLayout(new BorderLayout());
        setBackground(new Color(43, 43, 43));

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

        add(chatScrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }

    public void appendChatText(String text) {
        chatArea.append(text + "\n");
    }

    public void clearInputText() {
        inputField.setText("");
    }

    public String getInputText() {
        return inputField.getText();
    }

    public void addSendButtonListener(ActionListener listener) {
        sendButton.addActionListener(listener);
    }
}