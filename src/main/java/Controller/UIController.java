package Controller;

import Model.View.Decoraters.HandleMessageDecorator;
import backend.Messages.UI.*;
import backend.client_model.Client;
import backend.client_model.ClientObserver;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.View.IView;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class UIController implements ClientObserver {
    private IView view;
    private Client refrence;
    private HandleMessageDecorator handleMessageDecorator;

    public UIController(IView view, Client ref) {
        this.view = view;
        this.refrence = ref;
        this.view.addCreateChannelButtonListener(new CreateChannelButtonListener());
        this.view.addJoinChannelButtonListener(new JoinChannelButtonListener());
        this.handleMessageDecorator = new HandleMessageDecorator(view);
    }

    /**
     * Updates the UI based on the received UIMessage.
     * @param message the UIMessage to process.
     */
    @Override
    public void update(UIMessage message) {
       // view.appendChatText(message);
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
                throw new Exception("STOPED");
            }
            String channelName = channelNameAndPassword[0];
            String password = channelNameAndPassword[1];
            System.out.println(password);
            if(channelName.isEmpty()){
                throw new Exception("Channel name cannot be empty");
            }
            refrence.joinChannel(channelName, password); //TODO fix so if wrong pass not continue


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
            throw new Exception("STOPED");
        }
        String channelName = channelNameAndPassword[0];
        String password = channelNameAndPassword[1];
        if(channelName.isEmpty()){
            throw new Exception("Channel name cannot be empty");
        }
        refrence.createChannel(channelName, password);


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
            try {
                createChannel();
            } catch (Exception ex) {
                // Handle the exception and do not proceed to the chat area
                if(ex.getMessage().equals("STOPED")){
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
                if(ex.getMessage().equals("STOPED")){
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
                if (selectedChannel != null && !selectedChannel.equals(refrence.getCurrentChannelName())) {
                    refrence.switchChannel(selectedChannel);
                    view.changeChannel(selectedChannel);
                    UIMessage message = new DisplayMessage("SYSTEM", "Switched to channel: " + selectedChannel);
                    message.accept(handleMessageDecorator);
                    //view.showHistory(refrence.getHistory());
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
                if(ex.getMessage().equals("STOPED")){
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
     * Removes the current channel from the channel list and leaves the channel.
     * If there are no more channels, the start area is displayed.
     */
    class LeaveChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (view.getChannelList().size() == 0) {
                view.startArea();
                view.addCreateChannelButtonListener(new CreateChannelButtonListener());
                view.addJoinChannelButtonListener(new JoinChannelButtonListener());
            } else {
                view.removeChannelFromList(refrence.getCurrentChannelName());
                refrence.leaveChannel();
                String channelName = refrence.getCurrentChannelName();
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
                if(ex.getMessage().equals("STOPED")){
                    return;
                }
                view.displayErrorMessage(ex.getMessage());
                return;
            }
            view.showNotification("Channel created successfully");
        }
    }
}