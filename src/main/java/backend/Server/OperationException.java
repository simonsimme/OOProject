package backend.Server;

/**
 * The {@code OperationException} is a custom runtime exception used to represent
 * errors that occur during server operations, such as creating or joining a channel.
 * This exception can be throw when a specific operation fails due to logical constraints
 * or invalid inputs.
 */
public class OperationException extends RuntimeException {
    /**
     * Constructs a new {@code OperationException} with a specified error message.
     * @param errorMessage the detail message explaining the reason for the exception.
     */
    public OperationException(String errorMessage) {
        super(errorMessage);
    }
}
