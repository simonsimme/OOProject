package Model.Messages.Client;

/**
 * Message to client : Represents an error response.
 * This message contains an error message that the client should be notified of.
 */
public class ErrorResponse implements ClientMessage {
    /**
     * This string represents a short error message that the user should be notified with.
     */
    private final String errorMessage;

    /**
     * Constructor for creating an ErrorResponse with a specific error message.
     * @param errorMessage the error message to notify the user with.
     */
    public ErrorResponse(String errorMessage){
        this.errorMessage = errorMessage;
    }

    /**
     * Gets the error message.
     * @return the error message that was provided to the client.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Accepts a visitor to handle this ErrorResponse message.
     * This method follows the visitor design pattern and allows a specific action to be
     * performed on this message.
     *
     * @param visitor the visitor that will handle the ErrorResponse.
     */
    @Override
    public void accept(ClientMessageVisitor visitor) {
        visitor.handle(this);
    }



}