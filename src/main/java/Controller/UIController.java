package Controller;

import View.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIController {
    private View view;

    public UIController(View view) {
        this.view = view;

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
        }
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