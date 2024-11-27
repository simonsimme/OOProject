package backend.Messages.Client;

/**
 * Message to client : error.
 */
public class ErrorResponse extends ClientMessage {
    /**
     * This string represents a short error message that the user should be notified with.
     */
    private String errorMessage;

    /**
     * Constructor for ErrorResponse.
     * @param errorMessage
     */
    public ErrorResponse(String errorMessage){
        this.errorMessage = errorMessage;
    }

    //Getter
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     *  Visitor pattern method.
     * @param visitor
     */
    @Override
    public void accept(ClientMessageVisitor visitor) {
        visitor.handle(this);
    }
}
