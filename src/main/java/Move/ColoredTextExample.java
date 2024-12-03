package Move;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class ColoredTextExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Colored Text Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();

        // Define a style for red text
        Style redStyle = textPane.addStyle("RedStyle", null);
        StyleConstants.setForeground(redStyle, Color.RED);

        // Define a style for blue text
        Style blueStyle = textPane.addStyle("BlueStyle", null);
        StyleConstants.setForeground(blueStyle, Color.BLUE);

        try {
            // Insert text with different styles
            doc.insertString(doc.getLength(), "This is red text. ", redStyle);
            doc.insertString(doc.getLength(), "This is blue text.", blueStyle);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        frame.add(new JScrollPane(textPane));
        frame.setVisible(true);
    }
}