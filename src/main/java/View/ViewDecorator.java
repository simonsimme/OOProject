// ViewDecorator.java
package View;

import backend.Message;

import java.awt.event.ActionListener;

public abstract class ViewDecorator implements IView {
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
    public void appendChatText(Message text) {
        decoratedView.appendChatText(text);
    }

    @Override
    public void clearInputText() {
        decoratedView.clearInputText();
    }

    @Override
    public String getNickNameFeild() {
        return decoratedView.getNickNameFeild();
    }
    @Override
    public String getChannelName() {
        return decoratedView.getChannelName();
    }

    @Override
    public void showCreateChannelScreen() {
        decoratedView.showCreateChannelScreen();
    }
    @Override
    public void addCreateButtonListener(ActionListener listener) {
        decoratedView.addCreateButtonListener(listener);
    }

    @Override
    public void joinChannel(String channelName) {
        decoratedView.joinChannel(channelName);
    }

}