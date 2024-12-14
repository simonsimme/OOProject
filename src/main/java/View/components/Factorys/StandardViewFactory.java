package View.components.Factorys;

import View.components.Decoraters.TimestampDecorator;
import View.components.IView;
import View.StandardView;

public class StandardViewFactory implements ViewFactory {

    //returns a new StandardView object with the TimestampDecorator and ErrormessageDecorater decorators
    @Override
    public IView createView() {
        return (new TimestampDecorator(new StandardView()));
    }
}
