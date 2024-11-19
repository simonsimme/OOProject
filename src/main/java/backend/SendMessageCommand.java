package backend;

public class SendMessageCommand implements Command {
    private Message message;
    private ClientHandler ch;

    public SendMessageCommand(Message message, ClientHandler ch) {
        this.message = message;
        this.ch = ch;
    }
    @Override
    public void execute() {
        ch.sendMessage(message);
    }
}
