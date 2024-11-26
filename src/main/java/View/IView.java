package View;

import backend.Messages.*;

import java.awt.event.ActionListener;

public interface IView {
    void startArea();
    void showChatArea();
    void addCreateChannelButtonListener(ActionListener listener);
    void addJoinChannelButtonListener(ActionListener listener);
    void addSendButtonListener(ActionListener listener);
    void addJoinNewChannelButtonListener(ActionListener listener);
    void addLeaveChannelButtonListener(ActionListener listener);
    void addCreateNewChannelButtonListener(ActionListener listener);
    void addCreateButtonListener(ActionListener listener);
    String getInputText();
    void appendChatText(String text);
    void appendChatText(Message text);

    void clearInputText();
    String getNickNameFeild();

    String getChannelName();
    void showCreateChannelScreen();

    String getPasswordInput();

    String getChannelNameInput();
}
