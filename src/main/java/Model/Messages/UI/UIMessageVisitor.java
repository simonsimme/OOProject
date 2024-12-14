package Model.Messages.UI;

public interface UIMessageVisitor {
    void handle(DisplayError e);
    void handle(DisplayMessage m);
    void handle(UpdateChannels u);
    void handle(UIChannelHistory historyMessage);
    void handle(UpdateUserMessage u);
}
