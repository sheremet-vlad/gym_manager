package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static main.ClientTable.checkSubscriptionOnDate;
import static main.ClientTable.clientInfo;
import static main.Form.peopleInGym;
import static main.Form.redreshPersonInfoInTable;


public class ButtonEditorEndTrainy extends DefaultCellEditor {
    protected JButton button;
    private String    label;
    private boolean   isPushed;
    private int row;

    public ButtonEditorEndTrainy(JCheckBox checkBox) {
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
               /* BufferedWriter writer = new BufferedWriter(new FileWriter(NewClient.dirPathForClientPath+ClientTable.clientInfo[row][0] + ".txt", true));
                Date date = new Date();
                SimpleDateFormat format1 = new SimpleDateFormat("HH.mm");

                writer.write(format1.format(date) + " ");
                writer.close();
                peopleInGym(-1);*/
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
            if (line.charAt(line.length()-1) == '_') {
                Date date = new Date();
                SimpleDateFormat format1 = new SimpleDateFormat("HH.mm");
                int lostcount = Integer.parseInt(templine.substring(templine.indexOf("||")+2,templine.lastIndexOf("|")));
                templine.append(format1.format(date));
                System.out.println(lostcount);
                if (lostcount == 0){
                    templine.append(">");
                }
                Form.peopleInGym(-1);
                Files.write(path, new String(Files.readAllBytes(path), charset).replace(line, templine + "").getBytes(charset));
                if (lostcount == 0){
                    clearSubscriprionInTable(row);
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void clearSubscriprionInTable(int index){
        Object[] temp = ClientTable.clientInfo[index];
        if (!checkOnActiveSubscription(index)) {
            temp[3] = "--";
            temp[4] = "--";
            redreshPersonInfoInTable(index,temp);
        }

    }

    private boolean checkOnActiveSubscription(int index){
        boolean check = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(NewClient.dirPathForClientPath+clientInfo[index][0]+".txt"));
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null){
                if (line.charAt(line.length()-1) != '>'){
                    check = true;
                    AddSumscription.addToCLientTable(index, new StringBuffer(line));
                    break;
                }
            }
        }
        catch (Exception e){}
        return check;
    }

}

