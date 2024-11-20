package backend.Messages;

public class ErrorResponse extends Message{
    private String errorMessage;

    public ErrorResponse(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
