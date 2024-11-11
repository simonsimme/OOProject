package Controller;

import View.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIController {
    private View view;

    public UIController(View view) {
        this.view = view;
        this.view.addSendButtonListener(new SendButtonListener());
    }

    class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String inputText = view.getInputText();
            view.appendChatText("You: " + inputText);
            view.clearInputText();
        }
    }
}