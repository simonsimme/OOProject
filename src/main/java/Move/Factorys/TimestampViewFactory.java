package Move.Factorys;

import Move.Decoraters.TimestampDecorator;
import Move.IView;
import View.StandardView;

public class TimestampViewFactory implements ViewFactory {
    @Override
    public IView createView() {
        return new TimestampDecorator(new StandardView());
    }
}
