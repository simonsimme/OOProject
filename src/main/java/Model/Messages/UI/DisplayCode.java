package Model.Messages.UI;

public class DisplayCode extends UIMessage {
    private String code;

    public DisplayCode(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    @Override
    public void accept(UIMessageVisitor visitor) {
        visitor.handle(this);
    }

}
