package View.components;

import Model.Messages.UI.DisplayMessage;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
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
    void updateChannelList(List<String> channels, String currentChannel);
    void changeChannel(String channelName);
    void addChannelListSelectionListener(ListSelectionListener listener);
    String getInputText();
    void clearChatText();
    void appendChatText(DisplayMessage text);
    void appendChatText(TextFormat ft);
    String[] getChannelNameAndPasswordInput(String type);
    void removeChannelFromList(String channelName);
    void clearInputText();
    String getNickNameFeild();
    void displayErrorMessage(String message);
    DefaultListModel<String> getChannelList();
    NotificationSystem getNotificationSystem();
    void addWindowExitListener(WindowListener listener);
    void closeWindow();

    void addHelpButtonListener(ActionListener helpButtonListener);
    ChatArea getChatArea();
    void addHelpChatButtonListener(ActionListener chatHelpButtonListener);
}