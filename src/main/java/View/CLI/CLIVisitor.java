package View.CLI;

import Model.Messages.UI.DisplayError;
import Model.Messages.UI.DisplayMessage;
import Model.Messages.UI.UIMessageVisitor;
import Model.Messages.UI.UpdateChannels;

import java.io.PrintStream;
import java.util.List;

public class CLIVisitor implements UIMessageVisitor {
    private PrintStream ps;

    public CLIVisitor(PrintStream ps){
        this.ps = ps;
    }
    @Override
    public void handle(DisplayError e) {
        ps.println(e.getErrorMessage());
    }

    @Override
    public void handle(DisplayMessage m) {
        ps.println("(" + m.getTimestamp().toString() + ") " + m.getUserName() + " : " + m.getMessage());
    }

    @Override
    public void handle(UpdateChannels u) {
        ps.println("Channels have been updated, these are the channels: ");
        List<String> channels = u.getChannels();
        for (String channel: channels) {
            ps.println(channel);
        }
    }
}
