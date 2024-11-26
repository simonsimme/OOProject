package Controller;

import Main.ChatApplication;


import backend.Messages.UI.*;

import backend.client_model.Client;
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
    private MessageVisitorUI messageVisitorUI;


    public UIController(IView view,ChatApplication chatApplication ,Client ref) {
        this.view = view;
        this.refrence = ref;
        this.chatApplication = chatApplication;
        this.view.addCreateChannelButtonListener(new CreateChannelButtonListener());
        this.view.addJoinChannelButtonListener(new JoinChannelButtonListener());
        messageVisitorUI = new MessageVisitorUI(view);

    }

    @Override

    public void update(UIMessage message) {
        message.accept(messageVisitorUI);

    }




    class CreateChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            createChannel();
            showChatArea();
        }
    }

        class JoinChannelButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                joinChannel();
                showChatArea();
            }
        }
        private void joinChannel() {
            String channelName = view.getChannelNameInput();
            String password = view.getPasswordInput();
            refrence.joinChannel(channelName, password);
            view.addChannelToList(channelName);
        }
        private void createChannel() {
            String channelName = view.getChannelNameInput();
            String password = view.getPasswordInput();
            refrence.createChannel(channelName, password);
            view.addChannelToList(channelName);
        }

        private void showChatArea() {
            view.showChatArea();
            view.addSendButtonListener(new SendButtonListener());
            view.addJoinNewChannelButtonListener(new JoinNewChannelButtonListener());
            view.addLeaveChannelButtonListener(new LeaveChannelButtonListener());
            view.addCreateNewChannelButtonListener(new CreateNewChannelButtonListener());
            view.addChannelListSelectionListener(new ChannelListSelectionListener());
            nicknameset(view.getNickNameFeild());
        }

        class SendButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = view.getInputText();
                refrence.sendMessage(inputText);
                view.clearInputText();

            }
        }

    class ChannelListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                String selectedChannel = ((JList<String>) e.getSource()).getSelectedValue();
                System.out.println(refrence.getCurrentChannelName());
                if (selectedChannel != null && !selectedChannel.equals(refrence.getCurrentChannelName())) {
                    refrence.switchChannel(selectedChannel);
                    view.changeChannel(selectedChannel);
                }
            }
        }
    }



        private void nicknameset(String name) {
            refrence.setNickName(name);
        }


        //use this to send message to a view, add in what channel as well
        public void showTextinView(DisplayMessage msg) {
            try {
                view.appendChatText(msg.getTimestamp().getHour() + "." + msg.getTimestamp().getMinute() + "  " + msg.getMessage() + ": " + msg.getMessage());

            } catch (Exception e) {
                System.out.println("clients not found");
            }

        }


        class JoinNewChannelButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                joinChannel();
                view.appendChatText("Joining new channel...");

                // exempel på hur vi kan skicka request till servern
                // Hur ska detta fungera? Ska vi låta user skicka join request till Channel
                // eller är de invite only?
            }

        }


        class LeaveChannelButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                //try {
                refrence.leaveChannel();
                view.removeChannelFromList(refrence.getCurrentChannelName());
                if (view.getChannelList().size() == 0) {
                    view.startArea();
                    view.addCreateChannelButtonListener(new CreateChannelButtonListener());
                    view.addJoinChannelButtonListener(new JoinChannelButtonListener());
                }else {
                    view.removeChannelFromList(refrence.getCurrentChannelName());
                    String name = refrence.getChannelNames().get(0);
                    refrence.switchChannel(name);
                    view.changeChannel(name);
                }
                //} catch (IOException ex) {         -Exceptions catchas i client nu, DisplayError skickas via notify() vid errors
                //    view.appendChatText("Failed to leave channel... Try again later");
                //    throw new RuntimeException(ex);
                //}
            }
        }


        class CreateNewChannelButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                createChannel();
                view.appendChatText("Creating new channel...");
            }
        }
    }
