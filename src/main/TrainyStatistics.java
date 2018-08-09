package main;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TrainyStatistics extends JFrame {
    public static String dirPathFotStatistics = "E:\\temp_file\\statistics\\";
    public static String fileNameForStatiscs;

    private JLabel labelMan = new JLabel("Мужчины"),
                    labelWoman = new JLabel("Женщины"),
                    labelBoth = new JLabel("Все");

    private JTable tableManStatistics,tableWomanStatistics,tableBothStatistics;
    private JScrollPane scrollPaneMan,scrollPaneWoman,scrollPaneBoth;


    public TrainyStatistics(){
        setLayout(null);

        defineStatistics();

        add(labelMan).setBounds(180,10,150,20);
        add(scrollPaneMan).setBounds(10,30,400,87);
        add(labelWoman).setBounds(177,140,150,20);
        add(scrollPaneWoman).setBounds(10,160,400,87);
        add(labelBoth).setBounds(196,270,150,20);
        add(scrollPaneBoth).setBounds(10,290,400,87);
    }

    public static void defineStatisticsFile(){
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format1 = new SimpleDateFormat("MM.yyyy");
            SimpleDateFormat format2 = new SimpleDateFormat("E.dd.MM.yyyy");

            String monthToday = format1.format(calendar.getTime());
            calendar.setTime(format1.parse(monthToday));

            String fileName = dirPathFotStatistics+monthToday+".txt";
            File fileStatistics = new File(fileName);
            if (!fileStatistics.exists()){
                fileStatistics.createNewFile();
            }

            Calendar calendarForDayToday = Calendar.getInstance();
            String dateToday = format2.format(calendarForDayToday.getTime());

            //проверяем нужно ли записывать новую строку
            String line,tempLine = "";
            Boolean checkForNewLineInFile = true;
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
            while ((line = reader.readLine()) != null){
                tempLine = line;
            }
            if (tempLine.contains(dateToday)){
                checkForNewLineInFile = false;
            }

            if (checkForNewLineInFile) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
                writer.write("\n" + dateToday + ":");
                writer.close();
            }
            fileNameForStatiscs = fileName;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void defineStatistics(){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileNameForStatiscs), StandardCharsets.UTF_8))){

            int[][] statistcsMan = new int[4][4];
            int[][] statiscsWoman = new int[4][4];
            int[][] statiscsBoth = new int[4][4];

            String startStringToWeekStatistics = "",line;

            reader.readLine();
            while ((line = reader.readLine()) != null){
                if (line.contains("пн")){
                    startStringToWeekStatistics = line;
                }
            }

            reader.close();
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(fileNameForStatiscs), StandardCharsets.UTF_8));

            StringBuffer tempString = new StringBuffer("");
            int indexForStartCop = 0,value;
            boolean checkForWeek = false;

            reader1.readLine();
            while ((line = reader1.readLine()) != null){
                tempString = new StringBuffer(line);
                indexForStartCop = tempString.indexOf(":")+1;
                if (line.equals(startStringToWeekStatistics) || checkForWeek){
                    while (indexForStartCop != tempString.length()){
                        value = Integer.parseInt(tempString.charAt(indexForStartCop)+"");
                        indexForStartCop++;
                        switch (value){
                            case 0: {
                                statistcsMan[0][2] = statistcsMan[0][2]+1;
                                statistcsMan[0][3] = statistcsMan[0][3]+1;
                                statistcsMan[3][2] = statistcsMan[3][2]+1;
                                statistcsMan[3][3] = statistcsMan[3][3]+1;
                            }
                            break;

                            case 1: {
                                statiscsWoman[0][2] = statiscsWoman[0][2]+1;
                                statiscsWoman[0][3] = statiscsWoman[0][3]+1;
                                statiscsWoman[3][2] = statiscsWoman[3][2]+1;
                                statiscsWoman[3][3] = statiscsWoman[3][3]+1;
                            }
                            break;

                            case 2: {
                                statistcsMan[1][2] = statistcsMan[1][2]+1;
                                statistcsMan[1][3] = statistcsMan[1][3]+1;
                                statistcsMan[3][2] = statistcsMan[3][2]+1;
                                statistcsMan[3][3] = statistcsMan[3][3]+1;
                            }
                            break;

                            case 3: {
                                statiscsWoman[1][2] = statiscsWoman[1][2]+1;
                                statiscsWoman[1][3] = statiscsWoman[1][3]+1;
                                statiscsWoman[3][2] = statiscsWoman[3][2]+1;
                                statiscsWoman[3][3] = statiscsWoman[3][3]+1;
                            }
                            break;

                            case 4: {
                                statistcsMan[2][2] = statistcsMan[2][2]+1;
                                statistcsMan[2][3] = statistcsMan[2][3]+1;
                                statistcsMan[3][2] = statistcsMan[3][2]+1;
                                statistcsMan[3][3] = statistcsMan[3][3]+1;
                            }
                            break;

                            case 5: {
                                statiscsWoman[2][2]= statiscsWoman[2][2]+1;
                                statiscsWoman[2][3]= statiscsWoman[2][3]+1;
                                statiscsWoman[3][2] = statiscsWoman[3][2]+1;
                                statiscsWoman[3][3] = statiscsWoman[3][3]+1;
                            }
                            break;
                        }
                    }
                    checkForWeek = true;
                }
                else {
                    while (indexForStartCop != tempString.length()){
                        value = Integer.parseInt(tempString.charAt(indexForStartCop)+"");
                        indexForStartCop++;
                        System.out.print(value);
                        switch (value){
                            case 0: {
                                statistcsMan[0][3] = statistcsMan[0][3]+1;
                                statistcsMan[3][3] = statistcsMan[3][3]+1;
                            }
                            break;

                            case 1: {
                                statiscsWoman[0][3] = statiscsWoman[0][3]+1;
                                statiscsWoman[3][3] = statiscsWoman[3][3]+1;
                            }
                            break;

                            case 2: {
                                statistcsMan[1][3] = statistcsMan[1][3]+1;
                                statistcsMan[3][3] = statistcsMan[3][3]+1;
                            }
                            break;

                            case 3: {
                                statiscsWoman[1][3] = statiscsWoman[1][3]+1;
                                statiscsWoman[3][3] = statiscsWoman[3][3]+1;
                            }
                            break;

                            case 4: {
                                statistcsMan[2][3] = statistcsMan[2][3]+1;
                                statistcsMan[3][3] = statistcsMan[3][3]+1;
                            }
                            break;

                            case 5: {
                                statiscsWoman[2][3]= statiscsWoman[2][3]+1;
                                statiscsWoman[3][3] = statiscsWoman[3][3]+1;
                            }
                            break;
                        }
                    }
                }

            }

            //добавление статистики за день
            indexForStartCop = tempString.indexOf(":")+1;
            while (indexForStartCop != tempString.length()){
                value = Integer.parseInt(tempString.charAt(indexForStartCop)+"");
                indexForStartCop++;
                switch (value){
                    case 0: {
                        statistcsMan[0][1] = statistcsMan[0][1]+1;
                        statistcsMan[3][1] = statistcsMan[3][1]+1;
                    }
                    break;

                    case 1: {
                        statiscsWoman[0][1] = statiscsWoman[0][1]+1;
                        statiscsWoman[3][1] = statiscsWoman[3][1]+1;
                    }
                    break;

                    case 2: {
                        statistcsMan[1][1] = statistcsMan[1][1]+1;
                        statistcsMan[3][1] = statistcsMan[3][1]+1;
                    }
                    break;

                    case 3: {
                        statiscsWoman[1][1] = statiscsWoman[1][1]+1;
                        statiscsWoman[3][1] = statiscsWoman[3][1]+1;
                    }
                    break;

                    case 4: {
                        statistcsMan[2][1] = statistcsMan[2][1]+1;
                        statistcsMan[3][1] = statistcsMan[3][1]+1;
                    }
                    break;

                    case 5: {
                        statiscsWoman[2][1] = statiscsWoman[2][1]+1;
                        statiscsWoman[3][1] = statiscsWoman[3][1]+1;
                    }
                    break;
                }
            }

            String[][] womanStatiscsToTable = new String[4][4];
            String[][] manStatiscsToTable = new String[4][4];
            String[][] bothStatisticsToTable = new String[4][4];
            String[] topTableTitle = new String[] {"","Сутки", "Неделя", "Месяц"};
            String[] leftTablTitle = new String[] {"Утро", "День","Вечер","Всего"};
            //запись статистики в массивы для таблиц

            for (int i = 0; i < 4; i++) {
                for (int j = 1; j < 4; j++) {
                    womanStatiscsToTable[i][j] = statiscsWoman[i][j]+"";
                    manStatiscsToTable[i][j] = statistcsMan[i][j]+"";
                    bothStatisticsToTable[i][j] = statiscsWoman[i][j]+statistcsMan[i][j]+"";
                }
                womanStatiscsToTable[i][0] = leftTablTitle[i];
                manStatiscsToTable[i][0] = leftTablTitle[i];
                bothStatisticsToTable[i][0] = leftTablTitle[i];
            }

            tableManStatistics = new JTable(manStatiscsToTable,topTableTitle);
            tableWomanStatistics = new JTable(womanStatiscsToTable,topTableTitle);
            tableBothStatistics = new JTable(bothStatisticsToTable,topTableTitle);

            tableBothStatistics.setFillsViewportHeight(true);
            tableWomanStatistics.setFillsViewportHeight(true);
            tableManStatistics.setFillsViewportHeight(true);

            scrollPaneMan = new JScrollPane(tableManStatistics);
            scrollPaneWoman = new JScrollPane(tableWomanStatistics);
            scrollPaneBoth = new JScrollPane(tableBothStatistics);

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

}
