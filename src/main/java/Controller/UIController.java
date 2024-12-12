package Controller;

import Model.EncryptionLayer;
import View.components.Decoraters.HandleMessageDecorator;
import Model.Messages.UI.*;
import Model.Client.Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import View.components.IView;

import javax.crypto.SecretKey;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * The UIController class is responsible for handling user input and updating the view based on the received messages.
 */
public class UIController{
    private final IView view;
    private final Client reference;
    private UIChannelController channelController;
    private final SecretKey key;

    public UIController(IView view, Client ref, SecretKey key) {
        this.view = view;
        this.reference = ref;
        this.key = key;
        this.view.addCreateChannelButtonListener(new CreateChannelButtonListener());
        this.view.addJoinChannelButtonListener(new JoinChannelButtonListener());

        WindowController windowController = new WindowController(view, reference);
         channelController = new UIChannelController(view, ref);
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
            try {
               inputText = EncryptionLayer.encrypt(inputText, key); //TODO check if this is againg SRP
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
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
            }
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

                reference.switchChannel();
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
            view.clearChatText();
            view.showNotification("Channel created successfully");
        }
    }
}