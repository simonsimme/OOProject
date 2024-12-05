package Controller;

import View.components.Decoraters.HandleMessageDecorator;
import Model.Messages.UI.*;
import Model.Client.Client;
import Model.Client.ClientObserver;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import View.components.IView;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * The UIController class is responsible for handling user input and updating the view based on the received messages.
 */
public class UIController{
    private final IView view;
    private final Client reference;
    private final HandleMessageDecorator handleMessageDecorator;
    private UIChannelController channelController;

    public UIController(IView view, Client ref) {
        this.view = view;
        this.reference = ref;
        this.view.addCreateChannelButtonListener(new CreateChannelButtonListener());
        this.view.addJoinChannelButtonListener(new JoinChannelButtonListener());

        WindowController windowController = new WindowController(view, reference);
         channelController = new UIChannelController(view, ref);
        this.handleMessageDecorator = new HandleMessageDecorator(view);
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
        channelController.setNickName(view.getNickNameFeild());
    }



    class CreateChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                channelController.createChannel();
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
                channelController.joinChannel();
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
                channelController.joinChannel();
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
                channelController.createChannel();
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