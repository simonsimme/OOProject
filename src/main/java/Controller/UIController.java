package Controller;

import Main.ChatApplication;
import View.IView;
import backend.Message;
import backend.client_model.Client;
import backend.client_model.ClientObserver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import backend.CommandType;

public class UIController implements ClientObserver {
    private IView view;
    private Client refrence;
    private ChatApplication chatApplication;

    public UIController(IView view,ChatApplication chatApplication ,Client ref) {
        this.view = view;
        this.refrence = ref;
        this.chatApplication = chatApplication;
        this.view.addCreateChannelButtonListener(new CreateChannelButtonListener());
        this.view.addJoinChannelButtonListener(new JoinChannelButtonListener());
    }

    @Override
    public void update(Message message) {
        if(!message.getSender().equals("Server")) {
            System.out.println(message.getSender() + ": " + message.getContent());
            showTextinView(message);
        }

        System.out.println(message.getSender());
    }

    class CreateChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showCreateChannelScreen();
        }
    }


    class JoinChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Message msg = new Message(view.getNickNameFeild(), view.getNickNameFeild(), CommandType.JOIN);
           try {
               refrence.sendMessage(msg);
           } catch (IOException ex) {
               throw new RuntimeException(ex);
           }
            showChatArea();
        }
    }
    /*
    class JoinChannelButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String channelName = view.getChannelName(); // Assume this method gets the channel name from the user
        Command joinChannelCommand = new JoinChannelCommand(channelName, refrence.getClientHandler());
        joinChannelCommand.execute();
        view.appendChatText("Joined channel: " + channelName);
    }
}

     */

    private void showChatArea() {
        view.showChatArea();
        view.addSendButtonListener(new SendButtonListener());
        view.addJoinNewChannelButtonListener(new JoinNewChannelButtonListener());
        view.addLeaveChannelButtonListener(new LeaveChannelButtonListener());
        view.addCreateNewChannelButtonListener(new CreateNewChannelButtonListener());
        nicknameset(view.getNickNameFeild());
    }
    private void showCreateChannelScreen() {
        view.showCreateChannelScreen();
        view.addCreateButtonListener(new CreateButtonListener());
    }

    class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("send button pressed");
            String inputText = view.getInputText();
            try {
                refrence.sendMessage(inputText);
                System.out.println("sent message");
            } catch (IOException ex) {
                System.out.println("ERROR");

                throw new RuntimeException(ex);
            }
            //view.appendChatText("You: " + inputText);
            view.clearInputText();


        }
    }
    class CreateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String channelName = view.getChannelName();
            showChatArea();

            Message msg = new Message(view.getChannelName(), view.getNickNameFeild(), CommandType.JOIN);
            try {
                refrence.setNickName(view.getNickNameFeild());
                refrence.sendMessage(msg);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }





        }
    }
    public void sendPrivateMessage(Message msg)
    {
        view.appendChatText(msg);
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
            view.appendChatText(msg);

        }catch (Exception e)
        {
        e.printStackTrace();
        }
    }

    class JoinNewChannelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Message msg = new Message("Joining new channel...", "INFO", CommandType.MESSAGE);
            view.appendChatText(msg);
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
            Message msg = new Message("Creating new channel...", "INFO", CommandType.MESSAGE);
            view.appendChatText(msg);
        }
    }
}