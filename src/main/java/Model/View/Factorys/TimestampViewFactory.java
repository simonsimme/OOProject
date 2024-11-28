package Model.View.Factorys;

import Model.View.Decoraters.TimestampDecorator;
import Model.View.IView;
import View.StandardView;

public class TimestampViewFactory implements ViewFactory {
    @Override
    public IView createView() {
        return new TimestampDecorator(new StandardView());
    }
}
