package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static main.Form.redreshPersonInfoInTable;


public class ButtonEditorPlusTrainy extends DefaultCellEditor {
    protected JButton button;
    private String    label;
    private boolean   isPushed;
    private int row;

    public ButtonEditorPlusTrainy(JCheckBox checkBox) {
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
        try {
            String line;
            StringBuffer templine = new StringBuffer(""), tempString = new StringBuffer();
            BufferedReader reader = new BufferedReader(new FileReader(NewClient.dirPathForClientPath+ClientTable.clientInfo[row][0]+".txt"));
            reader.readLine();
            while ((line = reader.readLine()) != null){
                if (line.charAt(line.length()-1) !=  '>'){
                    templine = new StringBuffer(line);
                    break;
                }
            }
            line = templine+"";
            String fileName = NewClient.dirPathForClientPath+ClientTable.clientInfo[row][0]+".txt";
            Charset charset = StandardCharsets.UTF_8;
            Path path = Paths.get(fileName);
            if (line.charAt(line.length()-1) != '_') {
                Date date = new Date();
                SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yy:HH.mm");
                int lostcount = Integer.parseInt(templine.substring(templine.indexOf("||")+2,templine.lastIndexOf("|")));
                if (lostcount != 88){
                    lostcount--;
                }
                templine.append(" "+format1.format(date)+"_");
                templine.replace(templine.indexOf("||")+2,templine.lastIndexOf("|"),lostcount+"");
                refreshLostCountInTrainy(row,lostcount);
                Form.peopleInGym(1);
                Files.write(path, new String(Files.readAllBytes(path), charset).replace(line, templine + "").getBytes(charset));
            }
        } catch (Exception e){
            //e.printStackTrace();
        }
    }

    private static void refreshLostCountInTrainy(int index,int lostcount){
        Object[] tempArr = ClientTable.clientInfo[index];
        StringBuffer newCount = new StringBuffer((tempArr[4]+""));
        newCount.replace(newCount.indexOf("(")+1,newCount.indexOf(")"),lostcount+"");
        tempArr[4] = newCount+"";
        redreshPersonInfoInTable(index,tempArr);
    }
}

