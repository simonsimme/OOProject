package Controller;

import Main.ChatApplication;
import View.ErrorMessageDecorator;
import backend.Messages.UI.*;
import backend.client_model.Client;
import backend.client_model.ClientChannel;
import backend.client_model.ClientObserver;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import View.IView;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class UIController implements ClientObserver {
    private IView view;
    private Client refrence;
    private ChatApplication chatApplication;
    private ErrorMessageDecorator errorMessageDecorator;

    public UIController(IView view, ChatApplication chatApplication, Client ref) {
        this.view = view;
        this.refrence = ref;
        this.chatApplication = chatApplication;
        this.view.addCreateChannelButtonListener(new CreateChannelButtonListener());
        this.view.addJoinChannelButtonListener(new JoinChannelButtonListener());
        this.errorMessageDecorator = new ErrorMessageDecorator(view);
    }

    /**
     * Updates the UI based on the received UIMessage.
     * @param message the UIMessage to process.
     */
    @Override
    public void update(UIMessage message) {
       // view.appendChatText(message);
        message.accept(errorMessageDecorator);
    }

    /**
     * Loads the chat history into the view.
     * @param history the chat history to load.
     */
    @Override
    public void loadHistory(StringBuilder history) {
        view.showHistory(history);
    }

    /**
     * Joins a channel with the given name and password.
     */
    private void joinChannel() {
        String channelName = view.getChannelNameInput();
        String password = view.getPasswordInput();
        refrence.joinChannel(channelName, password);
        view.addChannelToList(channelName);
    }

    /**
     * Creates a new channel with the given name and password.
     */
    private void createChannel() {
        String channelName = view.getChannelNameInput();
        String password = view.getPasswordInput();
        refrence.createChannel(channelName, password);
        view.addChannelToList(channelName);
    }

    /**
     * Displays the chat area and sets up listeners for various actions.
     */
    private void showChatArea() {
        view.showChatArea();
        view.addSendButtonListener(new SendButtonListener());
        view.addJoinNewChannelButtonListener(new JoinNewChannelButtonListener());
        view.addLeaveChannelButtonListener(new LeaveChannelButtonListener());
        view.addCreateNewChannelButtonListener(new CreateNewChannelButtonListener());
        view.addChannelListSelectionListener(new ChannelListSelectionListener());
        nicknameset(view.getNickNameFeild());
    }

    /**
     * Sets the nickname for the client.
     * @param name the nickname to set.
     */
    private void nicknameset(String name) {
        refrence.setNickName(name);
    }

    /**
     * Displays a message in the chat view.
     * @param msg the message to display.
     */
    public void showTextinView(DisplayMessage msg) {
        try {
            view.appendChatText(msg.getTimestamp().getHour() + "." + msg.getTimestamp().getMinute() + "  " + msg.getMessage() + ": " + msg.getMessage());
        } catch (Exception e) {
            System.out.println("clients not found");
        }
    }

    /**
     * Listener for the create channel button.
     */
    class CreateChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            createChannel();
            showChatArea();
        }
    }

    /**
     * Listener for the join channel button.
     */
    class JoinChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            joinChannel();
            showChatArea();
        }
    }

    /**
     * Listener for the send button.
     */
    class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String inputText = view.getInputText();
            refrence.sendMessage(inputText);
            view.clearInputText();
        }
    }

    /**
     * Listener for the channel list selection.
     */
    class ChannelListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                String selectedChannel = ((JList<String>) e.getSource()).getSelectedValue();
                System.out.println(refrence.getCurrentChannelName());
                if (selectedChannel != null && !selectedChannel.equals(refrence.getCurrentChannelName())) {
                    refrence.switchChannel(selectedChannel);
                    view.changeChannel(selectedChannel);
                    view.showHistory(refrence.getHistory());
                }
            }
        }
    }

    /**
     * Listener for the join new channel button.
     */
    class JoinNewChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            joinChannel();
            view.appendChatText("Joining new channel...");
        }
    }

    /**
     * Listener for the leave channel button.
     */
    class LeaveChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refrence.leaveChannel();
            view.removeChannelFromList(refrence.getCurrentChannelName());
            if (view.getChannelList().size() == 0) {
                view.startArea();
                view.addCreateChannelButtonListener(new CreateChannelButtonListener());
                view.addJoinChannelButtonListener(new JoinChannelButtonListener());
            } else {
                view.removeChannelFromList(refrence.getCurrentChannelName());
                String channelName = refrence.switchChannel();
                view.changeChannel(channelName);
            }
        }
    }

    /**
     * Listener for the create new channel button.
     */
    class CreateNewChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            createChannel();
            view.appendChatText("Creating new channel...");
        }
    }
}