package main;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ClientTable {
    public static String[] tableTitle = {"Клиент","Карточка","Телефон","Текущий абонемент","Срок действия (ост. посещения)","Пришел","Ушел","Дата рождения","Добавление аб-а","..."};
    public static Object[][] clientInfo;
    public static String clientsFile = "E:\\temp_file\\clients.txt";

    public static void loadInformation(DefaultTableModel dm){
        try {
            BufferedReader countLength = new BufferedReader(new FileReader(clientsFile));

            //размер таблицы
            LineNumberReader count = new LineNumberReader(countLength);
            while (count.skip(Long.MAX_VALUE) > 0)
            {
               // Loop just in case the file is > Long.MAX_VALUE or skip() decides to not read the entire file
            }
            clientInfo = new Object[count.getLineNumber()][tableTitle.length];
            count.close();
            countLength.close();


            //заполнение таблицы
            BufferedReader reader = new BufferedReader(new FileReader(clientsFile));
            StringBuffer templine;
            String line,name;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                templine = new StringBuffer(line);
                name = templine.substring(0,templine.indexOf("|"));
                clientInfo[index][0] = templine.substring(0,templine.indexOf("|"));
                templine.delete(0,templine.indexOf("|",1));
                clientInfo[index][7] = templine.substring(1,templine.indexOf("|",1));
                templine.delete(0,templine.indexOf("|",1));
                clientInfo[index][1] = templine.substring(1,templine.indexOf("|",1));
                templine.delete(0,templine.indexOf("|",1));
                clientInfo[index][2] = templine.substring(1,templine.indexOf("|",1));

                clientInfo[index][3] = "";
                clientInfo[index][4] = "";
                clientInfo[index][5] = "+";
                clientInfo[index][6] = "-";
                clientInfo[index][8] = "Добавить";
                clientInfo[index][9] = "...";
                readSubscriptionInfo(index,name);
                index++;
            }
            reader.close();

        }
        catch (IOException ee){
            ee.printStackTrace();
            System.out.println();
        }

        dm.setDataVector(clientInfo,tableTitle);
    }


    //действие кнопки добавить абонемент
    public void actionAddsubscription() {

    }

    private static void readSubscriptionInfo(int index, String fileName){
        try{
            int countInFile, countLost;
            String endAndStartDate;
            BufferedReader reader = new BufferedReader(new FileReader( NewClient.dirPathForClientPath+fileName+".txt"));
            String templine,subsInfo,name;
            StringBuffer line;
            while ((templine = reader.readLine()) != null){
                if (templine.equals("") || templine.equals("M") || templine.equals("Ж") || templine.charAt(templine.length()-1) == '>'){
                    clientInfo[index][3] = "--";
                    clientInfo[index][4] = "--";
                }
                else {
                    line = new StringBuffer(templine);
                    name = line.substring(0,line.indexOf("|"));
                    line.delete(0,line.indexOf("|",1)+1);
                    endAndStartDate = line.substring(0,line.indexOf("|"));
                    line.delete(0,line.indexOf("||",1)+2);
                    countInFile = Integer.parseInt(line.substring(0,line.indexOf("|")));
                    countLost = countInFile;
                    subsInfo = endAndStartDate+" ("+countLost+")";
                    if (checkSubscriptionOnDate(endAndStartDate)) {
                        clientInfo[index][3] = name;
                        clientInfo[index][4] = subsInfo;
                    }
                    else {
                        clientInfo[index][3] = "--";
                        clientInfo[index][4] = "--";
                        writeEndingSubscription(fileName);
                    }
                    break;
                }
            }
            reader.close();
        }
        catch (Exception e){}
    }

    public static boolean checkSubscriptionOnDate(String endAndStartDate){
        try {
            String endDate;
            endDate = endAndStartDate.substring(endAndStartDate.indexOf("-")+1,endAndStartDate.length());
            SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yy");
            Calendar dateFromFile= Calendar.getInstance();
            dateFromFile.setTime(format1.parse(endDate));
            Date date = new Date();
            Date date2 = dateFromFile.getTime();
            if (date.after(date2))
                return false;
        }
        catch (Exception ee){
            ee.printStackTrace();
        }

        return true;
    }

    public static void writeEndingSubscription(String fileName){
        try {
            int countInFile, countLost;
            String endAndStartDate;
            BufferedReader reader = new BufferedReader(new FileReader( NewClient.dirPathForClientPath+fileName+".txt"));
            String templine,subsInfo,name;
            StringBuffer line;
            while ((templine = reader.readLine()) != null) {
                if (templine.equals("") || templine.equals("M") || templine.equals("Ж") || templine.charAt(templine.length() - 1) == '>') {
                    subsInfo = templine;
                    break;
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(NewClient.dirPathForClientPath+fileName+".txt",true));
            writer.write(">");
                /*else {
                    line = new StringBuffer(templine);
                    name = line.substring(0,line.indexOf("|"));
                    line.delete(0,line.indexOf("|",1)+1);
                    endAndStartDate = line.substring(0,line.indexOf("|"));
                    line.delete(0,line.indexOf("||",1)+2);
                    countInFile = Integer.parseInt(line.substring(0,line.indexOf("|")));
                    countLost = countInFile;
                    subsInfo = endAndStartDate+" ("+countLost+")";
                    if (checkSubscriptionOnDate(endAndStartDate)) {

                    }
                    else {

                    }*/
                writer.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
