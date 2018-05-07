package main;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static main.ClientTable.clientInfo;
import static main.Form.redreshPersonInfoInTable;

public class AddSumscription extends JFrame{
    private JComboBox comboBoxType;
    private JButton buttonAdd = new JButton("Добавить");
    private JLabel label1 = new JLabel("Дата начала");
    private JFormattedTextField field;
    private ArrayList<String> subscriptionInfo = new ArrayList<>();
    private JLabel labelSucsOrNot = new JLabel("");


    public AddSumscription(int clientIndexInTable) {
        setLayout(null);

        try {
            MaskFormatter mfData = new MaskFormatter("##.##.####");
            field = new JFormattedTextField(mfData);
        }
        catch (Exception ee){}

        comboBoxType = new JComboBox(loadTitle());
        add(comboBoxType).setBounds(10,10,200,20);

        add(label1).setBounds(70,45,100,20);
        add(field).setBounds(10,70,200,20);
        add(buttonAdd).setBounds(40,100,125,20);
        add(labelSucsOrNot).setBounds(50,130,100,20);

        actionComponents(clientIndexInTable);

    }

    public String[] loadTitle() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(NewSubscription.fileTypeSubscription));
            String tempLine;
            while ((tempLine = reader.readLine()) != null){
                subscriptionInfo.add(tempLine);
            }
        }
        catch (Exception ee){

        }
        String[] listNameSubscription = new String[subscriptionInfo.size()];
        String line;
        for (int i = 0; i < listNameSubscription.length; i++) {
            line = subscriptionInfo.get(i);
            listNameSubscription[i] = line.substring(0,line.indexOf("|",1));
        }
        return listNameSubscription;
    }

    private void actionComponents(int index){
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String subscriptinInfa;
                    SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
                    Calendar dataConmboBox = Calendar.getInstance();
                    dataConmboBox.setTime(format1.parse(field.getText()));
                    dataConmboBox.add(Calendar.HOUR_OF_DAY,23);
                    //Date dataConmboBox = format1.parse(field.getText());
                    Date dataNow = new Date();

                    //проверка текущего абонемента
                    String templine;
                    boolean checkOnActivSubscription = true;
                    BufferedReader reader = new BufferedReader(new FileReader(NewClient.dirPathForClientPath + clientInfo[index][0] + ".txt"));
                    while ((templine = reader.readLine()) != null){
                        if (!(templine.equals("") || templine.equals("M") || templine.equals("Ж") || templine.charAt(templine.length()-1) == '>')){
                            checkOnActivSubscription = false;
                            break;
                        }
                    }

                    if (dataNow.before(dataConmboBox.getTime())) {
                        String clientName = clientInfo[index][0] + "";
                        //System.out.println(clientName);
                        BufferedWriter writer = new BufferedWriter(new FileWriter(NewClient.dirPathForClientPath + clientName + ".txt", true));
                        dataConmboBox.setTime(format1.parse(field.getText()));
                        subscriptinInfa = defineSubscriptionParametr(comboBoxType.getSelectedItem() + "|",dataConmboBox.getTime());
                        if (checkOnActivSubscription){
                            addToCLientTable(index,new StringBuffer(subscriptinInfa));
                        }
                        writer.write(subscriptinInfa);
                        writer.close();
                        field.setText("");
                        labelSucsOrNot.setText("Добавлено");

                        dispose();
                    }
                    else {
                        labelSucsOrNot.setText("Ошибка");
                    }
                }
                catch (Exception ee){
                    System.out.println("file not found");
                    labelSucsOrNot.setText("Ошибка");
                }
            }
        });
    }

    private static String defineSubscriptionParametr(String nameInComboBox, Date startDate){
        String line;
        StringBuffer tempLine,subscriptionInfo = new StringBuffer("");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(NewSubscription.fileTypeSubscription));

            while ((line = reader.readLine()) != null){
                if (line.contains(nameInComboBox)){
                    String name,dataString,type,count,endAndStartTime;      //it is all about subscription
                    tempLine = new StringBuffer(line);
                    name = tempLine.substring(0,tempLine.indexOf("|", 1))+"";
                    tempLine.delete(0,tempLine.indexOf("|")+1);
                    dataString = tempLine.substring(0,tempLine.indexOf("|", 1))+"";
                    tempLine.delete(0,tempLine.indexOf("|")+1);
                    type = tempLine.substring(0,tempLine.indexOf("|", 1))+"";
                    tempLine.delete(0,tempLine.indexOf("|")+1);
                    endAndStartTime = defineEndDate(startDate,Integer.parseInt(dataString));
                    subscriptionInfo.append("\n"+name+"|"+endAndStartTime+"|");
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


    public static void addToCLientTable(int index, StringBuffer line) {
        boolean check;
        Object[] addSubscription;
        //addSubscription = new Object[clientInfo.length];
        addSubscription = clientInfo[index];
        addSubscription[3] = line.substring(0,line.indexOf("|"));
        line.delete(0,line.indexOf("|",1)+1);
        String endAndStartDate = line.substring(0,line.indexOf("|"));
        line.delete(0,line.indexOf("||",1)+2);
        int countInFile = Integer.parseInt(line.substring(0,line.indexOf("|")));
        addSubscription[4] = endAndStartDate+" ("+countInFile+")";
        redreshPersonInfoInTable(index,addSubscription);
    }
}
