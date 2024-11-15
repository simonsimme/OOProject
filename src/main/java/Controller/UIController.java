package Controller;

import Main.ChatApplication;
import View.View;
import backend.Message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UIController {
    private View view;
    private ChatApplication chatApplication;

    public UIController(View view, ChatApplication chatApplication) {
        this.view = view;
        this.chatApplication = chatApplication;
        this.view.addCreateChannelButtonListener(new CreateChannelButtonListener());
        this.view.addJoinChannelButtonListener(new JoinChannelButtonListener());
    }

    class CreateChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showChatArea();
        }
    }

    class JoinChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showChatArea();
        }
    }

    private void showChatArea() {
        view.showChatArea();
        view.addSendButtonListener(new SendButtonListener());
        view.addJoinNewChannelButtonListener(new JoinNewChannelButtonListener());
        view.addLeaveChannelButtonListener(new LeaveChannelButtonListener());
        view.addCreateNewChannelButtonListener(new CreateNewChannelButtonListener());
    }

    class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String inputText = view.getInputText();

            view.appendChatText("You: " + inputText);
            view.clearInputText();
            try {
                chatApplication.sendFromClients(inputText);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
    }

    //use this to send message to a view, add in what channel as well
    public void showTextinView(Message msg)
    {
        view.appendChatText(msg.getSender() + ": " + msg.getContent());
    }

    class JoinNewChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.appendChatText("Joining new channel...");
        }
    }

    class LeaveChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.startArea();
            view.addCreateChannelButtonListener(new CreateChannelButtonListener());
            view.addJoinChannelButtonListener(new JoinChannelButtonListener());
        }
    }

    class CreateNewChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.appendChatText("Creating new channel...");
        }
    }
}