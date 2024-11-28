// HighlightedChannelRenderer.java
package Model.View;

import javax.swing.*;
import java.awt.*;

public class HighlightedChannelRenderer extends DefaultListCellRenderer {
    private String currentChannel;

    public HighlightedChannelRenderer(String currentChannel) {
        this.currentChannel = currentChannel;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value.toString().equals(currentChannel)) {
            component.setBackground(Color.YELLOW); // Highlight color
        }
        return component;
    }

    public void setCurrentChannel(String currentChannel) {
        this.currentChannel = currentChannel;
    }
}