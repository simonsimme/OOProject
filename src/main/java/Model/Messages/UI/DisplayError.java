package Model.Messages.UI;

public class DisplayError extends UIMessage {
    private String errorMessage;

    public DisplayError(String errorMessage){
         this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void accept(UIMessageVisitor visitor) {
        visitor.handle(this);
    }
}
