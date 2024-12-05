package View.components.Factorys;

import View.components.Decoraters.TimestampDecorator;
import View.components.IView;
import View.StandardView;

public class TimestampViewFactory implements ViewFactory {
    @Override
    public IView createView() {
        return new TimestampDecorator(new StandardView());
    }
}
