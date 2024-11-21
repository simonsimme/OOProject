package View;

public class DecoderViewFactory implements ViewFactory {
    public IView createView() {
        return new  TimestampDecorator(new DecryptionDecorator(new StandardView()));
    }
}
