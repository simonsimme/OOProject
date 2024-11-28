package Model.View.Factorys;

import Model.View.Decoraters.DecryptionDecorator;
import Model.View.IView;
import Model.View.Decoraters.TimestampDecorator;
import View.*;

public class DecoderViewFactory implements ViewFactory {
    public IView createView() {
        return new TimestampDecorator(new DecryptionDecorator(new StandardView()));
    }
}
