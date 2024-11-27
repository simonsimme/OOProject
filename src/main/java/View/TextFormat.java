package View;

import java.awt.*;
import java.util.List;

public class TextFormat {
    private List<String> text;
    private List<Color> colors;
    public TextFormat(List<String> text, List<Color> colors){
        this.text = text;
        this.colors = colors;
    }
    public List<String> getText(){
        return text;
    }
    public List<Color> getColors(){
        return colors;
    }
}
