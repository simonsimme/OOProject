package View.components.Factorys;

import View.components.Decoraters.DecryptionDecorator;
import View.components.IView;
import View.components.Decoraters.TimestampDecorator;
import View.*;

public class DecoderViewFactory implements ViewFactory {
    public IView createView() {
        return new TimestampDecorator(new DecryptionDecorator(new StandardView()));
    }
}
