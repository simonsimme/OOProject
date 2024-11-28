package Model.View.Factorys;

import Model.View.Decoraters.TimestampDecorator;
import Model.View.IView;
import View.StandardView;

public class StandardViewFactory implements ViewFactory {

    //returns a new StandardView object with the TimestampDecorator and ErrormessageDecorater decorators
    @Override
    public IView createView() {
        return (new TimestampDecorator(new StandardView()));
    }
}
