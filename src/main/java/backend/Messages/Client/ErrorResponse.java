package backend.Messages.Client;

/**
 * Message to client : Display an error message in the UI
 */
public class ErrorResponse extends ClientMessage {
    /**
     * This string is displayed to the user
     */
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
