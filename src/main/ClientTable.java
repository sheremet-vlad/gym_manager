package main;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ClientTable {
    public static String[] tableTitle = {"Клиент", "Карточка", "Телефон", "Текущий абонемент", "Срок действия (ост. посещения)", "Пришел", "Ушел", "Дата рождения", "Добавление аб-а", "З-ть тек. абон.", "Карта клиента", "В зале"};
    public static Object[][] clientInfo;
    public static String clientsFile = "E:\\temp_file\\clients.txt";

    public static void loadInformation(DefaultTableModel dm) {
        try (BufferedReader countLength = new BufferedReader(new InputStreamReader(new FileInputStream(clientsFile), StandardCharsets.UTF_8));
             BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(clientsFile), StandardCharsets.UTF_8))) {


            //размер таблицы
            LineNumberReader count = new LineNumberReader(countLength);
            while (count.skip(Long.MAX_VALUE) > 0) {
                // Loop just in case the file is > Long.MAX_VALUE or skip() decides to not read the entire file
            }
            clientInfo = new Object[count.getLineNumber()][tableTitle.length];
            count.close();
            countLength.close();


            //заполнение таблицы
            StringBuffer templine;
            String line, name;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                templine = new StringBuffer(line);
                name = templine.substring(0, templine.indexOf("|"));
                clientInfo[index][0] = templine.substring(0, templine.indexOf("|"));
                templine.delete(0, templine.indexOf("|", 1));
                clientInfo[index][7] = templine.substring(1, templine.indexOf("|", 1));
                templine.delete(0, templine.indexOf("|", 1));
                clientInfo[index][1] = templine.substring(1, templine.indexOf("|", 1));
                templine.delete(0, templine.indexOf("|", 1));
                clientInfo[index][2] = templine.substring(1, templine.indexOf("|", 1));

                clientInfo[index][3] = "";
                clientInfo[index][4] = "";
                clientInfo[index][5] = "+";
                clientInfo[index][6] = "-";
                clientInfo[index][8] = "Добавить";
                clientInfo[index][9] = "Заморозить";
                clientInfo[index][10] = "...";
                readSubscriptionInfo(index, name);
                index++;
            }
            reader.close();

        } catch (IOException ee) {
            ee.printStackTrace();
            System.out.println();
        }

        dm.setDataVector(clientInfo, tableTitle);
    }


    //действие кнопки добавить абонемент
    public void actionAddsubscription() {

    }

    private static void readSubscriptionInfo(int index, String fileName) {
        try {
            int countInFile, countLost;
            String endAndStartDate;
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(NewClient.dirPathForClientPath + fileName + ".txt"), StandardCharsets.UTF_8));
            String templine, subsInfo, name;
            StringBuffer line;
            boolean isContaincEndedSubscription = false;
            while ((templine = reader.readLine()) != null) {
                if (templine.charAt(templine.length() - 1) == '_') {
                    clientInfo[index][11] = "В зале";
                }
                if (templine.equals("") || templine.equals("M") || templine.equals("Ж") || templine.charAt(templine.length() - 1) == '>') {
                    clientInfo[index][3] = "--";
                    clientInfo[index][4] = "--";
                } else {
                    line = new StringBuffer(templine);
                    name = line.substring(0, line.indexOf("|"));
                    line.delete(0, line.indexOf("|", 1) + 1);
                    endAndStartDate = line.substring(0, line.indexOf("|"));
                    line.delete(0, line.indexOf("||", 1) + 2);
                    countInFile = Integer.parseInt(line.substring(0, line.indexOf("|")));
                    countLost = countInFile;
                    subsInfo = endAndStartDate + " (" + countLost + ")";
                    if (templine.contains("Не активен") || checkSubscriptionOnDate(endAndStartDate)) {
                        clientInfo[index][3] = name;
                        clientInfo[index][4] = subsInfo;
                        break;
                    } else {
                        clientInfo[index][3] = "--";
                        clientInfo[index][4] = "--";
                        isContaincEndedSubscription = true;
                    }
                }
            }
            reader.close();

            if (isContaincEndedSubscription) {
                writeEndingSubscription(fileName);
            }
        } catch (Exception e) {
        }
    }

    public static boolean checkSubscriptionOnDate(String endAndStartDate) {
        try {
            String endDate;
            endDate = endAndStartDate.substring(endAndStartDate.indexOf("-") + 1, endAndStartDate.length());
            SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yy");
            Calendar dateFromFile = Calendar.getInstance();
            dateFromFile.setTime(format1.parse(endDate));
            Date date = new Date();
            Date date2 = dateFromFile.getTime();
            if (date.after(date2))
                return false;
        } catch (Exception ee) {
            ee.printStackTrace();
        }

        return true;
    }

    public static void writeEndingSubscription(String fileName) {
        try {

            String templine, endAndStartDate;
            StringBuilder subsInfo = new StringBuilder();
            StringBuilder line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(NewClient.dirPathForClientPath + fileName + ".txt"), StandardCharsets.UTF_8));

            while ((templine = reader.readLine()) != null) {
                if (templine.equals("") || templine.equals("M") || templine.equals("Ж")) {
                    subsInfo.append(templine);
                } else if (templine.charAt(templine.length() - 1) == '>') {
                    subsInfo.append("\n").append(templine);
                } else {
                    line = new StringBuilder(templine);
                    line.delete(0, line.indexOf("|", 1) + 1);
                    endAndStartDate = line.substring(0, line.indexOf("|"));
                    if (templine.contains("Не активен") || checkSubscriptionOnDate(endAndStartDate)) {
                        subsInfo.append("\n").append(templine);
                    } else {
                        subsInfo.append("\n").append(templine).append(">");
                    }
                }
            }

            reader.close();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(NewClient.dirPathForClientPath + fileName + ".txt", false), StandardCharsets.UTF_8));
            writer.write(String.valueOf(subsInfo));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
