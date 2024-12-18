package View.components.Factorys;

import View.StandardView;
import View.components.Decoraters.EmojiDecorator;
import View.components.Decoraters.MessageDecorator;
import View.components.IView;

public class StandardViewFactory implements ViewFactory {

    //returns a new StandardView object with the MessageDecorator and ErrormessageDecorater decorators
    @Override
    public IView createView() {
        return (new EmojiDecorator(new MessageDecorator(new StandardView())));
    }
}
