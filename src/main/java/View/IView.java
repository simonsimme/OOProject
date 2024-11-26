package View;

import backend.Messages.*;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.List;

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
    void updateChannelList(List<String> channels, String currentChannel);
    void changeChannel(String channelName);
    void addChannelListSelectionListener(ListSelectionListener listener);
    void addChannelToList(String channelName);
    String getInputText();
    void appendChatText(String text);
    void appendChatText(Message text);
    void removeChannelFromList(String channelName);
    void clearInputText();
    String getNickNameFeild();

    void showCreateChannelScreen();

    String getPasswordInput();

    String getChannelNameInput();
    DefaultListModel<String> getChannelList();
}
