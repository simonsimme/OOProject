package View.components.Factorys;

import View.StandardView;
import View.components.Decoraters.EmojiDecorator;
import View.components.Decoraters.MessageDecorator;
import View.components.Factorys.ViewFactory;
import View.components.IView;

public class StandardViewFactory implements ViewFactory {

    @Override
    public IView createView() {
        return new EmojiDecorator(new MessageDecorator(new StandardView()));
    }
}