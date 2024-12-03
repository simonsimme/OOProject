package Controller;

import Move.Decoraters.HandleMessageDecorator;
import Model.Messages.UI.*;
import Model.Client.Client;
import Model.Client.ClientObserver;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import Move.IView;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * The UIController class is responsible for handling user input and updating the view based on the received messages.
 */
public class UIController implements ClientObserver {
    private final IView view;
    private final Client reference;
    private final HandleMessageDecorator handleMessageDecorator;

    public UIController(IView view, Client ref) {
        this.view = view;
        this.reference = ref;
        this.view.addCreateChannelButtonListener(new CreateChannelButtonListener());
        this.view.addJoinChannelButtonListener(new JoinChannelButtonListener());
        this.view.addWindowExitListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleWindowClosing();
            }
        });
        this.handleMessageDecorator = new HandleMessageDecorator(view);
    }
    private void handleWindowClosing() {
        int confirm = JOptionPane.showOptionDialog(
                null,
                "Are You Sure to Close the Application?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );

        if (confirm == JOptionPane.YES_OPTION) {
            view.closeWindow();
            reference.disconnect();
            System.exit(0);
        }
    }
    /**
     * Updates the UI based on the received UIMessage.
     * @param message the UIMessage to process.
     */
    @Override
    public void update(UIMessage message) {
        message.accept(handleMessageDecorator);
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
        try{
            String[] channelNameAndPassword = view.getChannelNameAndPasswordInput("Join");
            if (channelNameAndPassword == null) {
                throw new Exception("STOPPED");
            }
            String channelName = channelNameAndPassword[0];
            String password = channelNameAndPassword[1];
            System.out.println(password);
            if(channelName.isEmpty()){
                throw new Exception("Channel name cannot be empty");
            }
            reference.joinChannel(channelName, password); //TODO fix so if wrong pass not continue


        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }


    }

    /**
     * Creates a new channel with the given name and password.
     */
    private void createChannel() {
        try{
        String[] channelNameAndPassword = view.getChannelNameAndPasswordInput("Create");
        if (channelNameAndPassword == null) {
            throw new Exception("STOPPED");
        }
        String channelName = channelNameAndPassword[0];
        String password = channelNameAndPassword[1];
        if(channelName.isEmpty()){
            throw new Exception("Channel name cannot be empty");
        }
        reference.createChannel(channelName, password);


        }catch (Exception e){
            throw new RuntimeException(e.getMessage());

        }
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
        setNickName(view.getNickNameFeild());
    }

    /**
     * Sets the nickname for the client.
     * @param name the nickname to set.
     */
    private void setNickName(String name) {
        reference.setNickName(name);
        //we want to show the user that he is changed his/here name
        view.showNotification("Your nickname is now: " + name);
        System.out.println("clientName is" + name);

    }

    class CreateChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                createChannel();
            } catch (Exception ex) {
                // Handle the exception and do not proceed to the chat area
                if(ex.getMessage().equals("STOPPED")){
                    return;
                }
                view.displayErrorMessage(ex.getMessage());
                return;
            }
            showChatArea();
        }
    }

    /**
     * Listener for the join channel button.
     */
    class JoinChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                joinChannel();
            } catch (Exception ex) {
                if(ex.getMessage().equals("STOPPED")){
                    return;
                }
                view.displayErrorMessage(ex.getMessage());
                return;
            }
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
            reference.sendMessage(inputText);
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
                if (selectedChannel != null && !selectedChannel.equals(reference.getCurrentChannelName())) {
                    reference.switchChannel(selectedChannel);
                    view.changeChannel(selectedChannel);
                    UIMessage message = new DisplayMessage("SYSTEM", "Switched to channel: " + selectedChannel);
                    message.accept(handleMessageDecorator);
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
            try {
                joinChannel();
            } catch (Exception ex) {
                if(ex.getMessage().equals("STOPPED")){
                    return;
                }
                view.displayErrorMessage(ex.getMessage());
                return;
            }
            view.showNotification("Joined channel successfully");
        }
    }

    /**
     * Listener for the leave channel button.
     */
    class LeaveChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            reference.leaveChannel();
            view.removeChannelFromList(reference.getCurrentChannelName());
            if (view.getChannelList().isEmpty()) {
                view.startArea();
                view.addCreateChannelButtonListener(new CreateChannelButtonListener());
                view.addJoinChannelButtonListener(new JoinChannelButtonListener());
            } else {
                view.removeChannelFromList(reference.getCurrentChannelName());
                String channelName = reference.switchChannel();
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
            try {
                createChannel();
            } catch (Exception ex) {
                if(ex.getMessage().equals("STOPPED")){
                    return;
                }
                view.displayErrorMessage(ex.getMessage());
                return;
            }
            view.showNotification("Channel created successfully");
        }
    }
}