package View.components.Factorys;

import View.StandardView;
import View.components.Decoraters.EmojiDecorator;
import View.components.Decoraters.MessageDecorator;
import View.components.Decoraters.CodeDecorator;
import View.components.IView;

public class StandardViewFactory implements ViewFactory {

    // Returns a new StandardView object with the EmojiDecorator, MessageDecorator, and CodeMessageDecorator decorators
    @Override
    public IView createView() {
        return new EmojiDecorator(
                new CodeDecorator(
                        new MessageDecorator(
                                new StandardView()
                        )
                )
        );
    }
}
