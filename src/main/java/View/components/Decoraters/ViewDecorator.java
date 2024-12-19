package View.components.Decoraters;

import View.components.ChatArea;
import View.components.IView;
import View.components.NotificationSystem;
import View.components.TextFormat;
import Model.Messages.UI.DisplayMessage;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.List;

public class ViewDecorator implements IView {
    protected IView decoratedView;

    public ViewDecorator(IView decoratedView) {
        this.decoratedView = decoratedView;
    }

    @Override
    public void startArea() {
        decoratedView.startArea();
    }

    @Override
    public void showChatArea() {
        decoratedView.showChatArea();
    }

    @Override
    public void addCreateChannelButtonListener(ActionListener listener) {
        decoratedView.addCreateChannelButtonListener(listener);
    }

    @Override
    public void addJoinChannelButtonListener(ActionListener listener) {
        decoratedView.addJoinChannelButtonListener(listener);
    }

    @Override
    public void addSendButtonListener(ActionListener listener) {
        decoratedView.addSendButtonListener(listener);
    }

    @Override
    public void addJoinNewChannelButtonListener(ActionListener listener) {
        decoratedView.addJoinNewChannelButtonListener(listener);
    }

    @Override
    public void addLeaveChannelButtonListener(ActionListener listener) {
        decoratedView.addLeaveChannelButtonListener(listener);
    }

    @Override
    public void addCreateNewChannelButtonListener(ActionListener listener) {
        decoratedView.addCreateNewChannelButtonListener(listener);
    }

    @Override
    public String getInputText() {
        return decoratedView.getInputText();
    }



    @Override
    public void appendChatText(DisplayMessage text) {
        decoratedView.appendChatText(text);
    }

    @Override
    public void clearChatText() {
        decoratedView.clearChatText();
    }

    @Override
    public void clearInputText() {
        decoratedView.clearInputText();
    }

    @Override
    public void appendChatText(TextFormat tf) {
        decoratedView.appendChatText(tf);
    }

    @Override
    public String getNickNameFeild() {
        return decoratedView.getNickNameFeild();
    }

    @Override
    public String[] getChannelNameAndPasswordInput(String type) {
        return decoratedView.getChannelNameAndPasswordInput(type);
    }

    @Override
    public void displayErrorMessage(String message) {
        decoratedView.displayErrorMessage(message);
    }
    @Override
    public NotificationSystem getNotificationSystem() {
        return decoratedView.getNotificationSystem();
    }

    @Override
    public void updateChannelList(List<String> channels, String currentChannel) {
        decoratedView.updateChannelList(channels, currentChannel);
    }

    @Override
    public void changeChannel(String channelName) {
        decoratedView.changeChannel(channelName);
    }

    @Override
    public void addChannelListSelectionListener(ListSelectionListener listener) {
        decoratedView.addChannelListSelectionListener(listener);
    }

    @Override
    public void removeChannelFromList(String channelName) {
        decoratedView.removeChannelFromList(channelName);
    }

    @Override
    public DefaultListModel<String> getChannelList() {
        return decoratedView.getChannelList();
    }



    @Override
    public void addWindowExitListener(WindowListener listener) {
        decoratedView.addWindowExitListener(listener);
    }

    @Override
    public void closeWindow() {
        decoratedView.closeWindow();
    }

    @Override
    public void addHelpButtonListener(ActionListener helpButtonListener) {
        decoratedView.addHelpButtonListener(helpButtonListener);
    }
    @Override
    public ChatArea getChatArea() {
        return decoratedView.getChatArea();
    }

    @Override
    public void addHelpChatButtonListener(ActionListener chatHelpButtonListener) {
        decoratedView.addHelpChatButtonListener(chatHelpButtonListener);
    }
}