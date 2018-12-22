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
import java.util.LinkedList;

import static main.Form.redreshPersonInfoInTable;


public class ButtonEditorPlusTrainy extends DefaultCellEditor {
    private final static String NOT_ACTIVATE = "Не активен";

    private static String endAndStartTime = "";

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
        String fileName = NewClient.dirPathForClientPath+ClientTable.clientInfo[row][0]+".txt";
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))){
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
            if (line.contains(NOT_ACTIVATE)) {
                line = activatesSub(fileName, line);
                int lost = Integer.parseInt(templine.substring(templine.indexOf("||")+2,templine.lastIndexOf("|")));
                addInfoToTable(row, lost);
            }

            templine = new StringBuffer(line);

            Charset charset = StandardCharsets.UTF_8;
            Path path = Paths.get(fileName);
            if (line.charAt(line.length()-1) != '_') {
                if (checkSubscriptionFreezeOnDate(line)) {
                    Date date = new Date();
                    SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yy:HH.mm");
                    int lostcount = Integer.parseInt(templine.substring(templine.indexOf("||")+2,templine.lastIndexOf("|")));
                    if (lostcount != 88){
                        lostcount--;
                    }
                    templine.append(" "+format1.format(date)+"_");
                    templine.replace(templine.indexOf("||")+2,templine.lastIndexOf("|"),lostcount+"");
                    refreshLostCountInTrainy(row,lostcount);
                    Form.refreshDataInPlusButton(row,0);
                    Form.writeCountOfPeopleInGym(1);
                    Files.write(path, new String(Files.readAllBytes(path), charset).replace(line, templine + "").getBytes(charset));
                }
            }
        } catch (Exception e){
            //e.printStackTrace();
        }
    }

    private static void refreshLostCountInTrainy(int index,int lostcount){
        Object[] tempArr = ClientTable.clientInfo[index];
        StringBuffer newCount = new StringBuffer((tempArr[4]+""));
        newCount.replace(newCount.lastIndexOf("(")+1,newCount.lastIndexOf(")"),lostcount+"");
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

    public static boolean checkSubscriptionFreezeOnDate(String line){
        boolean flag = true;
        try {
            if (line.indexOf("(", line.indexOf("-")) != -1) {
                String endAndStartDate = line.substring(line.indexOf("(", line.indexOf("-")) + 1, line.indexOf(")",line.indexOf("-")));
                String startDateString = endAndStartDate.substring(0,endAndStartDate.indexOf("-"));
                String endDate = endAndStartDate.substring(endAndStartDate.indexOf("-")+1,endAndStartDate.length());
                SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yy");

                Calendar dateFromFile= Calendar.getInstance();
                dateFromFile.setTime(format1.parse(endDate));
                dateFromFile.add(Calendar.HOUR_OF_DAY,23);

                Calendar startDate = Calendar.getInstance();
                startDate.setTime(format1.parse(startDateString));
                Calendar temp = Calendar.getInstance();
                temp.setTime(new Date());
                Date date = temp.getTime();

                Date date2 = dateFromFile.getTime();
                Date date3 = startDate.getTime();

                if (date.before(date2) && date.after(date3))
                    flag = false;
            }

        }
        catch (Exception ee){
            ee.printStackTrace();
        }

        return flag;
    }

    private static void addInfoToTable (int index, int lostCount) {
        Object[] tempArr = ClientTable.clientInfo[index];
        StringBuffer newCount = new StringBuffer((tempArr[4]+""));
        tempArr[4] = endAndStartTime + "(" + lostCount + ")";
        redreshPersonInfoInTable(index,tempArr);
    }

    private String activatesSub(String fileName, String subscription) {
        String result = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))){

            String line;
            LinkedList<String> listOfSubs = new LinkedList<>();
            while ((line = reader.readLine()) != null) {
                listOfSubs.add(line);
            }

            int indexOfSubs = 0;
            for (int i = 0; i < listOfSubs.size(); i++) {
                if (listOfSubs.get(i).equals(subscription)) {
                    indexOfSubs = i;
                }
            }
            reader.close();

            SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
            Calendar dataConmboBox = Calendar.getInstance();
            Date dateNow = new Date();
            dataConmboBox.setTime(format1.parse(format1.format(dateNow)));
            dataConmboBox.add(Calendar.HOUR_OF_DAY,23);

            String sub = listOfSubs.get(indexOfSubs);
            String subInfo = sub.substring(0, sub.indexOf("|"));
            sub = defineSubscriptionParametr(subInfo, dataConmboBox.getTime());
            listOfSubs.set(indexOfSubs, sub);

            result = sub;
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8));
            for (int i = 0; i < listOfSubs.size(); i++) {
                writer.write(listOfSubs.get(i));
                if (i != listOfSubs.size()-1) {
                    writer.write("\n");
                }
            }
            writer.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String defineSubscriptionParametr(String nameInComboBox, Date startDate){
        String line;
        StringBuffer tempLine,subscriptionInfo = new StringBuffer("");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(NewSubscription.fileTypeSubscription), StandardCharsets.UTF_8))) {
            while ((line = reader.readLine()) != null){
                if (line.contains(nameInComboBox)){
                    String name,dataString,type,count;      //it is all about subscription
                    tempLine = new StringBuffer(line);
                    name = tempLine.substring(0,tempLine.indexOf("|", 1))+"";
                    tempLine.delete(0,tempLine.indexOf("|")+1);
                    dataString = tempLine.substring(0,tempLine.indexOf("|", 1))+"";
                    tempLine.delete(0,tempLine.indexOf("|")+1);
                    type = tempLine.substring(0,tempLine.indexOf("|", 1))+"";
                    tempLine.delete(0,tempLine.indexOf("|")+1);
                    endAndStartTime = defineEndDate(startDate,Integer.parseInt(dataString));
                    subscriptionInfo.append(name+"|"+endAndStartTime+"|");
                    if (type.equals("lin")){
                        count = tempLine.substring(0,tempLine.indexOf("|", 1))+"";
                        subscriptionInfo.append("|"+count+"|");
                    }
                    else {
                        subscriptionInfo.append("|88|");
                    }

                }
            }
        }
        catch (Exception ee){
            System.out.println("");
        }

        return subscriptionInfo+"";
    }

    public static String defineEndDate(Date startDate, int count){
        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_WEEK, count);
        StringBuffer timeSubscription = new StringBuffer(format1.format(startDate)+"-"+format1.format(calendar.getTime()));
        return timeSubscription+"";
    }
}

