// ViewDecorator.java
package View;

import backend.Messages.*;
import backend.Messages.UI.DisplayMessage;
import backend.Messages.UI.UIMessage;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.List;

public  class ViewDecorator implements IView {
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
    public void appendChatText(String text) {
        decoratedView.appendChatText(text);
    }
    @Override
    public void appendChatText(DisplayMessage text) {
        decoratedView.appendChatText(text);
    }

    @Override
    public void clearInputText() {
        decoratedView.clearInputText();
    }
    @Override
    public void appendChatText(TextFormat ft) {
        decoratedView.appendChatText(ft);
    }

    @Override
    public String getNickNameFeild() {
        return decoratedView.getNickNameFeild();
    }


    @Override
    public void showCreateChannelScreen() {
        decoratedView.showCreateChannelScreen();
    }

    @Override
    public String getPasswordInput() {
        return decoratedView.getPasswordInput();
    }

    @Override
    public String getChannelNameInput() {
        return decoratedView.getChannelNameInput();
    }

    @Override
    public void addCreateButtonListener(ActionListener listener) {
        decoratedView.addCreateButtonListener(listener);
    }

    @Override
    public void addChannelToList(String channelName) {
        decoratedView.addChannelToList(channelName);
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
    public void showHistory(StringBuilder history) {
        decoratedView.showHistory(history);
    }



}