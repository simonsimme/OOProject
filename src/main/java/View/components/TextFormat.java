package View.components;

import java.awt.*;
import java.util.List;

public class TextFormat {
    private List<String> text;
    private List<Color> colors;
    private List<Font> fonts; // Add this field

    public TextFormat(List<String> text, List<Color> colors) {
        this.text = text;
        this.colors = colors;
    }

    public TextFormat(List<String> text, List<Color> colors, List<Font> fonts) {
        this.text = text;
        this.colors = colors;
        this.fonts = fonts;
    }

    public List<String> getText() {
        return text;
    }

    public List<Color> getColors() {
        return colors;
    }

    public List<Font> getFonts() {
        return fonts;
    }
}
