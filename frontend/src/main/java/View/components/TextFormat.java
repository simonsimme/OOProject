package View.components;

import java.awt.*;
import java.util.List;

/**
 * The {@code TextFormat} class represents a formatted text structure that includes text,
 * colors, and fonts for styling purposes.
 */
public class TextFormat {
    /**
     * The list of text segments to be formatted.
     */
    private final List<String> text;
    /**
     * The list of colors corresponding to each text segment.
     */
    private final List<Color> colors;
    /**
     * The list of fonts corresponding to each text segment.
     */
    private List<Font> fonts; // Add this field

    /**
     * Constructs a {@code TextFormat} object with specified text and colors.
     *
     * @param text   the list of text segments to be formatted.
     * @param colors the list of colors corresponding to each text segment.
     */
    public TextFormat(List<String> text, List<Color> colors) {
        this.text = text;
        this.colors = colors;
    }

    /**
     * Constructs a {@code TextFormat} object with specified text, colors, and fonts.
     *
     * @param text   the list of text segments to be formatted.
     * @param colors the list of colors corresponding to each text segment.
     * @param fonts  the list of fonts corresponding to each text segment.
     */
    public TextFormat(List<String> text, List<Color> colors, List<Font> fonts) {
        this.text = text;
        this.colors = colors;
        this.fonts = fonts;
    }

    /**
     * Returns the list of text segments.
     *
     * @return the list of text segments.
     */
    public List<String> getText() {
        return text;
    }

    /**
     * Returns the list of colors corresponding to the text segments.
     *
     * @return the list of colors.
     */
    public List<Color> getColors() {
        return colors;
    }

    /**
     * Returns the list of fonts corresponding to the text segments.
     *
     * @return the list of fonts.
     */
    public List<Font> getFonts() {
        return fonts;
    }
}
