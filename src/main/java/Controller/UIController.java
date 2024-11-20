package Controller;

import Main.ChatApplication;
import View.View;
import backend.Message;
import backend.client_model.Client;
import backend.client_model.ClientObserver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UIController implements ClientObserver {
    private View view;
    private Client refrence;
    private ChatApplication chatApplication;

    public UIController(View view,ChatApplication chatApplication ,Client ref) {
        this.view = view;
        this.refrence = ref;
        this.chatApplication = chatApplication;
        this.view.addCreateChannelButtonListener(new CreateChannelButtonListener());
        this.view.addJoinChannelButtonListener(new JoinChannelButtonListener());
    }

    @Override
    public void update(Message message) {
        showTextinView(message);
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
        nicknameset(view.getNickNameFeild());
    }

    class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String inputText = view.getInputText();
            try {
                refrence.sendMessage(inputText);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            //view.appendChatText("You: " + inputText);
            view.clearInputText();


        }
    }
    private void nicknameset(String name)
    {
        refrence.setNickName(name);

    }

    //use this to send message to a view, add in what channel as well
    public void showTextinView(Message msg)
    {
        try
        {
            view.appendChatText(msg.getTimestamp().getHour() +"." +msg.getTimestamp().getMinute() + "  " +msg.getSender() + ": " + msg.getContent());

        }catch (Exception e)
        {
        System.out.println("clients not found");
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