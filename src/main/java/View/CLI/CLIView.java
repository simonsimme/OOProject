package View.CLI;

import Model.Client.ClientObserver;
import Model.Messages.UI.DisplayMessage;
import Model.Messages.UI.UIMessage;
import Model.Messages.UI.UIMessageVisitor;

import java.io.PrintStream;

public class CLIView implements ClientObserver {

    private PrintStream ps;
    private UIMessageVisitor visitor;

    public CLIView(PrintStream ps){
        this.ps = ps;
        this.visitor = new CLIVisitor(ps);
    }

    public void displayMessage(String message){
        ps.println(message);
    }
    @Override
    public void update(UIMessage message) {
        //message.accept(visitor);
    }

    @Override
    public void notification(DisplayMessage message) {

    }
}
