package main;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * @version 1.0 11/09/98
 */
public class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setBackground(new Color(110,163,255));
        } else{
            setForeground(new Color(90,90,90));
            setBackground(new Color(200,200,200));

            setBorder(null);
            //setBackground(UIManager.getColor("Button.background"));
        }
        setText( (value ==null) ? "" : value.toString() );
        return this;
    }
}
