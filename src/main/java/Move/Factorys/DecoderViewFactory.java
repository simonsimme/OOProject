package Move.Factorys;

import Move.Decoraters.DecryptionDecorator;
import Move.IView;
import Move.Decoraters.TimestampDecorator;
import View.*;

public class DecoderViewFactory implements ViewFactory {
    public IView createView() {
        return new TimestampDecorator(new DecryptionDecorator(new StandardView()));
    }
}
