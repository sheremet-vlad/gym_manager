package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(NewClient.dirPathForClientPath+ClientTable.clientInfo[row][0]+".txt"), StandardCharsets.UTF_8))){
            String line;
            StringBuffer templine = new StringBuffer(""), tempString = new StringBuffer();

            writeInfoInStatisticsFile(reader.readLine());
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
                Form.writeCountOfPeopleInGym(1);
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

    private static void writeInfoInStatisticsFile(String gender){
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(TrainyStatistics.fileNameForStatiscs,true), StandardCharsets.UTF_8))){
            boolean checkForGender;       //true - M, false - Ж
            if (gender.contains("M")) {
                checkForGender = true;
            } else {
                checkForGender = false;
            }

            Calendar calendarTimeNow = Calendar.getInstance();
            Calendar calendarFiveHours = Calendar.getInstance();
            Calendar calendarTwelveHours = Calendar.getInstance();
            SimpleDateFormat format1 = new SimpleDateFormat("HH.mm");
            String dataNow = format1.format(calendarTimeNow.getTime());
            calendarTimeNow.setTime(format1.parse(dataNow));
            calendarFiveHours.setTime(format1.parse("17.00"));
            calendarTwelveHours.setTime(format1.parse("12.00"));

            if (calendarTimeNow.getTime().after(calendarTwelveHours.getTime())){
                if (calendarTimeNow.getTime().after(calendarFiveHours.getTime())){
                    if (checkForGender){
                        writer.write("4");
                    }
                    else {
                        writer.write("5");
                    }
                }
                else {
                    if (checkForGender){
                        writer.write("2");
                    }
                    else {
                        writer.write("3");
                    }
                }
            }
            else {
                if (checkForGender){
                    writer.write("0");
                }
                else {
                    writer.write("1");
                }
            }
            writer.flush();
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

