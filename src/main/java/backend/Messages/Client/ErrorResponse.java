package backend.Messages.Client;

public class ErrorResponse extends ClientMessage {
    private String errorMessage;

    public ErrorResponse(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void accept(ClientMessageVisitor visitor) {
        visitor.handle(this);
    }
}
