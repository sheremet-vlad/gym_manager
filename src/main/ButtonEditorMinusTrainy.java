package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ButtonEditorMinusTrainy extends DefaultCellEditor {
    protected JButton button;
    private String    label;
    private boolean   isPushed;
    private int row;

    public ButtonEditorMinusTrainy(JCheckBox checkBox) {
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
            actionButtnAddSubscription();
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

    //действие кнопки добавить абонемент
    private void actionButtnAddSubscription(){
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(NewClient.dirPathForClientPath+ClientTable.clientInfo[row][0]+".txt",true), StandardCharsets.UTF_8))){

            Date date = new Date();
            SimpleDateFormat format1 = new SimpleDateFormat("HH.mm");
            String line;
            StringBuffer tempLine = new StringBuffer("");
            writer.write(format1.format(date)+" ");
            writer.close();
            Form.writeCountOfPeopleInGym(-1);

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}

