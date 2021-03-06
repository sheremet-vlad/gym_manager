package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static create_gui_form.SwingConsole.run1;

public class ButtonEditorFreeze extends DefaultCellEditor {

    protected JButton button;
    private String    label;
    private boolean   isPushed;
    private static int row;

    public ButtonEditorFreeze(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(Color.RED);
        } else{
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value ==null) ? "" : value.toString();
        button.setText( label );
        isPushed = true;
        this.row = row;
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed)  {
           actionButtonFreeze();
        }
        isPushed = false;
        return new String( label ) ;
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

    private static void actionButtonFreeze() {
        run1(new FormFreeze(row),300,240);
    }
}
