package backend.Messages.UI;

public interface UIMessageVisitor {
    void handle(DisplayError e);
    void handle(DisplayMessage m);
    void handle(UpdateChannels u);
}
