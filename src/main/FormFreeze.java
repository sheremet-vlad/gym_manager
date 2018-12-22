package main;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.ClientTable.clientInfo;
import static main.Form.redreshPersonInfoInTable;

public class FormFreeze extends JFrame {
    private static final String REGEX = "[^\\d]+";

    private JLabel labelStart = new JLabel("С какого числа:");
    private JLabel labelCount = new JLabel("Количество дней:");
    private JLabel labelLog = new JLabel("");

    private JFormattedTextField fieldStart;
    private JTextField fieldCount = new JTextField(10);

    private JButton buttonFreeze = new JButton("Заморозить");

    private int index;
    private String activeSubscription = "";
    private List<String> listOfLines = new LinkedList<>();
    private int indexOfActiveSub;

    public FormFreeze(int index) {
        this.index = index;

        setLayout(null);

        try {
            MaskFormatter mfStart = new MaskFormatter("##.##.####");
            fieldStart = new JFormattedTextField(mfStart);
        }
        catch (ParseException e) {

        }

        add(labelStart).setBounds(33,20,140,30);
        add(fieldStart).setBounds(150,20,100,30);

        add(labelCount).setBounds(20,60,140,30);
        add(fieldCount).setBounds(150,60,100,30);

        add(buttonFreeze).setBounds(45,111,180,30);

        add(labelLog).setBounds(20,150,200,30);

        configureComponents();
        actionComponent();
    }

    private void configureComponents() {
        Font buttonFont = new Font("Arial", Font.PLAIN, 18);
        Font labelFont = new Font("Arial", Font.PLAIN, 16);

        labelCount.setFont(labelFont);
        labelStart.setFont(labelFont);
        buttonFreeze.setFont(buttonFont);
    }

    private void actionComponent() {
        buttonFreeze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkFields()) {
                    try {
                        int daysToFreeze = Integer.parseInt(fieldCount.getText());
                        System.out.println(daysToFreeze);
                        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yy");

                        String nameOfSub = activeSubscription.substring(0,activeSubscription.indexOf("|"));
                        int tempIndex = activeSubscription.indexOf("-");
                        String startDateString = activeSubscription.substring(activeSubscription.indexOf("|") + 1,tempIndex);
                        String endDateString = activeSubscription.substring(tempIndex + 1,activeSubscription.indexOf("|",tempIndex));
                        String trainyInfo = activeSubscription.substring(activeSubscription.indexOf("|",tempIndex));

                        Calendar dateEndOfSub = Calendar.getInstance();
                        dateEndOfSub.setTime(format1.parse(endDateString));

                        Calendar dataEndOfFreeze = Calendar.getInstance();
                        dataEndOfFreeze.setTime(format1.parse(fieldStart.getText()));
                        dataEndOfFreeze.add(Calendar.DAY_OF_WEEK, daysToFreeze);

                        dateEndOfSub.add(Calendar.DAY_OF_WEEK, daysToFreeze);
                        SimpleDateFormat formatToWrite = new SimpleDateFormat("dd.MM.yy");
                        StringBuilder newSub = new StringBuilder(nameOfSub);
                        newSub.append("|");
                        newSub.append(startDateString);
                        newSub.append("-");
                        newSub.append(format1.format(dateEndOfSub.getTime()));
                        newSub.append("(");
                        newSub.append(fieldStart.getText());
                        newSub.append("-");
                        newSub.append(formatToWrite.format(dataEndOfFreeze.getTime()));
                        newSub.append(")");
                        newSub.append(trainyInfo);
                        listOfLines.set(indexOfActiveSub,newSub+"");
                        writeToFile();
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                }

            }
        });
    }

    private void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(NewClient.dirPathForClientPath + clientInfo[index][0] + ".txt", false), StandardCharsets.UTF_8))) {
            for (int i = 0; i < listOfLines.size(); i++) {
                if (i != listOfLines.size() - 1) {
                    writer.write(listOfLines.get(i)+"\n");
                }
                else {
                    writer.write(listOfLines.get(i)+"");
                }
            }
            refreshLostCountInTrainy(index, listOfLines.get(index));
        }
        catch (Exception e) {

        }
    }

    private boolean checkFields() {
        boolean flag = false;
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(fieldCount.getText());

        getActiveSubscription(index);
        if (activeSubscription.equals("")){
            labelLog.setText("Нет активного абонемента");
        } else if (matcher.find()) {
            labelLog.setText("Не цифры в поле \"количество дней\"");
        } else if (fieldCount.getText().equals("")) {
            labelLog.setText("Пустое поле \"Количество дней\"");
        } else if (!fieldStart.getText().contains("2")){
            labelLog.setText("Неправильно заполнена дата начала");
        } else if (activeSubscription.indexOf("(", activeSubscription.indexOf("-")) != -1){
            labelLog.setText("Абонент уже был заморожен");
        } else if (activeSubscription.contains("Не активен")) {
            labelLog.setText("Абонемент не активен");
        } else {
            flag = true;
            labelLog.setText("Выполнено");
        }

        return flag;
    }

    private void getActiveSubscription(int index){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(NewClient.dirPathForClientPath+clientInfo[index][0]+".txt"), StandardCharsets.UTF_8))) {
            boolean flag = true;
            String line;
            listOfLines.add(reader.readLine());
            int i = 1;
            while((line =reader.readLine())!=null) {
                listOfLines.add(line);
                if (line.charAt(line.length() - 1) != '>' && flag) {
                    activeSubscription = line;
                    indexOfActiveSub = i;
                    flag = false;
                }
                i++;
            }
        }
        catch (Exception e){}
    }

    private static void refreshLostCountInTrainy(int index, String line){
        Object[] tempArr = ClientTable.clientInfo[index];
        StringBuffer newCount = new StringBuffer((tempArr[4]+""));
        tempArr[4] = line.substring(line.lastIndexOf("(") + 1,line.lastIndexOf(")"));
        redreshPersonInfoInTable(index,tempArr);
    }

}
