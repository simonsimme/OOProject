package View.components.Factorys;

import View.components.IView;

/**
 * The {@code ViewFactory} interface defines a contract for creating {@link IView} objects.
 * <p>
 * Implementing classes should provide the logic to create and return a specific {@link IView} instance.
 * This follows the Factory Design Pattern, allowing for the creation of different types of views
 * without exposing the instantiation details.
 * </p>
 */
public interface ViewFactory {

    /**
     * Creates and returns a new {@link IView} instance.
     * <p>
     * The specific implementation of this method will determine the type and configuration
     * of the {@link IView} object created.
     * </p>
     *
     * @return a new instance of {@link IView}.
     */
    IView createView();
}
