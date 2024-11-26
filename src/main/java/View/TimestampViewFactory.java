package View;

public class TimestampViewFactory implements ViewFactory {
    @Override
    public IView createView() {
        return new TimestampDecorator(new StandardView());
    }
}
