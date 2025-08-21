package View.components.Factorys;

import View.StandardView;
import View.components.Decoraters.EmojiDecorator;
import View.components.Decoraters.MessageDecorator;
import View.components.IView;

/**
 * The {@code StandardViewFactory} class implements the {@link ViewFactory} interface and is responsible for
 * creating an instance of {@link IView} with a specific set of decorators applied. This factory creates a
 * {@link StandardView} object, which is then decorated with an {@link EmojiDecorator} and a {@link MessageDecorator}.
 * <p>
 * This follows the Factory Design Pattern, providing an abstract way to create decorated views without exposing the
 * concrete instantiation logic.
 * </p>
 */
public class StandardViewFactory implements ViewFactory {

    /**
     * Creates and returns a new {@link IView} instance with the {@link EmojiDecorator} and {@link MessageDecorator} applied
     * to a {@link StandardView}.
     *
     * @return a decorated {@link IView} instance with emoji and message formatting capabilities.
     */
    @Override
    public IView createView() {
        return new EmojiDecorator(
                        new MessageDecorator(
                                new StandardView()
                        )
        );
    }
}
