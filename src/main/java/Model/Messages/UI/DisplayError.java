package Model.Messages.UI;

/**
 * Represents a message to display an error in the user interface.
 */
public class DisplayError extends UIMessage {
    /**
     * The error message to be displayed.
     */
    private final String errorMessage;

    /**
     * Constructs a {@code DisplayError} message with the specified error message.
     *
     * @param errorMessage The error message to be displayed.
     */
    public DisplayError(String errorMessage){
         this.errorMessage = errorMessage;
    }
    /**
     * Retrieves the error message.
     * @return The error message to be displayed.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Accepts a visitor, allowing it to handle the {@code DisplayError} message.
     * @param visitor The {@link UIMessageVisitor} that will handle the message.
     */
    @Override
    public void accept(UIMessageVisitor visitor) {
        visitor.handle(this);
    }
}
