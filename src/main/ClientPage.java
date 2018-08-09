package main;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

import static main.ClientTable.clientInfo;
import static main.Form.redreshPersonInfoInTable;

public class ClientPage extends JFrame {
    private int COUNT_COLUMNS = 30;
    public static Object[] newClientInfo;
    private static String lineInfo;

    private JTextField  fieldFirstName = new JTextField(COUNT_COLUMNS),
            fieldSurname = new JTextField(COUNT_COLUMNS),
            fieldSecondName = new JTextField(COUNT_COLUMNS),
            fieldWCartNumber = new JTextField(COUNT_COLUMNS);

    private JFormattedTextField fieldBirthday,fieldPhone;

    private ButtonGroup manOrWoman = new ButtonGroup();

    private JRadioButton    radioMan = new JRadioButton("М"),
            radioWoman = new JRadioButton("Ж");


    private JLabel  labelName = new JLabel("Имя"),
            labelSurname = new JLabel("Фамилия"),
            labelSecondName = new JLabel("Отчество"),
            labelBirthday = new JLabel("Дата рождения"),
            labelTitle = new JLabel("РЕДАКТИРОВАТЬ ДАННЫЕ КЛИЕНТА"),
            line = new JLabel(""),
            line2 = new JLabel(""),
            line3 = new JLabel(""),
            line4 = new JLabel(""),
            labelNotPerfom = new JLabel(""),
            labelHistotyOfSubscription = new JLabel("ИСТОРИЯ АБОНЕМЕНТОВ"),
            labelHistotyOfTreiny = new JLabel("ИСТОРИЯ ТРЕНИРОВОК"),
            labelContactPhone = new JLabel("Телефон"),
            labelCartNumber = new JLabel("Номер карты");

    private JButton button_finish = new JButton("Изменить");
    private static JTable tableHistorySubscripton, tableHistoryTrainy;
    private JScrollPane scrollPane_subscription, scrollPane_trainy;

    public ClientPage(int form_height, int form_width, int index) {
        int lineSpace = 275;
        //шаблон
        setLayout(null);

        //Бордер для линии
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

        //настройка компонентов
        //configurateComponent();

        //настройка маски поля день рождения
        fieldBirthday = new JFormattedTextField();
        try {
            MaskFormatter mfData = new MaskFormatter("##.##.####");
            MaskFormatter mfPhone = new MaskFormatter("80(##)-###-##-##");
            fieldBirthday = new JFormattedTextField(mfData);
            fieldPhone = new JFormattedTextField(mfPhone);
        }
        catch (Exception ee){}

        manOrWoman.add(radioMan);
        manOrWoman.add(radioWoman);


        //добавление компонетов
        add(labelTitle).setBounds(32,10,230,30);
        line.setBorder(border); line2.setBorder(border); line3.setBorder(border); line4.setBorder(border);
        add(line).setBounds(0,40,form_width,1);
        add(line2).setBounds(0,235,lineSpace,1);
        add(line3).setBounds(lineSpace,0,1,form_height);
        add(line4).setBounds(625,0,1,form_height);
        add(labelSurname).setBounds(40,50,70,20);
        add(fieldSurname).setBounds(110,50,130,20);
        add(labelName).setBounds(40,80,70,20);
        add(fieldFirstName).setBounds(110,80,130,20);
        add(labelSecondName).setBounds(40,110,70,20);
        add(fieldSecondName).setBounds(110,110,130,20);
        add(labelBirthday).setBounds(40,140,70,20);
        add(fieldBirthday).setBounds(110,140,70,20);
        add(labelContactPhone).setBounds(40,170,70,20);
        add(fieldPhone).setBounds(110,170,130,20);
        add(labelCartNumber).setBounds(40,200,70,20);
        add(fieldWCartNumber).setBounds(110,200,130,20);
        add(button_finish).setBounds(40,250,200,20);
        add(labelNotPerfom).setBounds(90,300,200,20);
        add(labelHistotyOfSubscription).setBounds(380,10,230,30);
        add(labelHistotyOfTreiny).setBounds(730,10,230,30);

        loadClietInfoToField(index);

        //кнопка зарегистрировать
        buttonRegistrateAction(index);

        //загрука таблиц
        loadHistoriesTable(index);

        add(scrollPane_subscription).setBounds(300,50,300,290);
        add(scrollPane_trainy).setBounds(650,50,300,290);
    }

    private void loadHistoriesTable(int index){
        String[] titleTrainyTablee = new String[] {"Абонемент","Дата посещения"};
        String[] titleSubscriptionTable = new String[] {"Абонемент","Дата действия абонемента"};
        String[][] trainyInfo;
        String[][] subscriptionInfo;
        int indexForStartCopy = 0,countOfSubscription = 0,countOfTrainy = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(NewClient.dirPathForClientPath+clientInfo[index][0]+".txt"), StandardCharsets.UTF_8));
             BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(NewClient.dirPathForClientPath+clientInfo[index][0]+".txt"), StandardCharsets.UTF_8))) {

            reader.readLine();
            String line,subscriptionName;
            StringBuffer tempLine = new StringBuffer("");
            while ((line = reader.readLine()) != null){
                tempLine = new StringBuffer(line);
                countOfSubscription++;
                while ((indexForStartCopy = tempLine.indexOf("_",indexForStartCopy+1)) != -1){
                    countOfTrainy++;
                }
            }
            reader.close();
            trainyInfo = new String[countOfTrainy][2];
            subscriptionInfo = new String[countOfSubscription][2];
            int i = 0, j = 0;
            reader1.readLine();
            while ((line = reader1.readLine()) != null) {
                tempLine = new StringBuffer(line);
                subscriptionName = tempLine.substring(0,tempLine.indexOf("|"));
                indexForStartCopy = tempLine.indexOf("|")+1;
                subscriptionInfo[i][0] = subscriptionName;
                subscriptionInfo[i][1] = tempLine.substring(indexForStartCopy,tempLine.indexOf("|",indexForStartCopy));
                indexForStartCopy = tempLine.lastIndexOf("|")+2;
                while ((tempLine.indexOf(" ",indexForStartCopy)) != -1){
                    trainyInfo[j][0] = subscriptionName;
                    trainyInfo[j][1] = tempLine.substring(indexForStartCopy,tempLine.indexOf(" ",indexForStartCopy));
                    indexForStartCopy = tempLine.indexOf(" ",indexForStartCopy)+1;
                    j++;
                }
                i++;
                if (tempLine.indexOf(">") != -1) {
                    trainyInfo[j][0] = subscriptionName;
                    trainyInfo[j][1] = tempLine.substring(indexForStartCopy, tempLine.indexOf(">"));
                    j++;
                }

                if ((tempLine.lastIndexOf(" ")+22) > tempLine.length()){
                    trainyInfo[j][0] = subscriptionName;
                    trainyInfo[j][1] = tempLine.substring(tempLine.lastIndexOf(" ")+1, tempLine.length());
                    j++;
                }
            }
            reader1.close();
            tableHistorySubscripton = new JTable(subscriptionInfo,titleSubscriptionTable);
            tableHistoryTrainy = new JTable(trainyInfo,titleTrainyTablee);

            tableHistoryTrainy.setFillsViewportHeight(true);
            tableHistorySubscripton.setFillsViewportHeight(true);

            tableHistorySubscripton.getColumnModel().getColumn(0).setPreferredWidth(130);
            tableHistoryTrainy.getColumnModel().getColumn(0).setPreferredWidth(100);

            scrollPane_subscription = new JScrollPane(tableHistorySubscripton);
            scrollPane_trainy = new JScrollPane(tableHistoryTrainy);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void buttonRegistrateAction(int index) {
        button_finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //////////////////////////////////////////////////////////////////////////////////
                    String fileName = ClientTable.clientsFile;
                    Charset charset = StandardCharsets.UTF_8;
                    Path path = Paths.get(fileName);
                    StringBuffer write = new StringBuffer("");
                    String  name = fieldFirstName.getText(),
                            secondName = fieldSecondName.getText(),
                            surname = fieldSurname.getText(),
                            birthday = fieldBirthday.getText(),
                            cartNumber = fieldWCartNumber.getText(),
                            phone = fieldPhone.getText();



                    if (name.equals("") || secondName.equals("") || surname.equals("") || birthday.equals("")
                            || phone.equals("") || cartNumber.equals("")) {
                        labelNotPerfom.setText("Ошибка");
                    }
                    else {
                        labelNotPerfom.setText("");
                        //запись в массив
                        newClientInfo = ClientTable.clientInfo[index];
                        newClientInfo[0] = surname + " " + name + " " + secondName;
                        newClientInfo[1] = cartNumber;
                        newClientInfo[2] = phone;
                        newClientInfo[7] = birthday;
                        newClientInfo[5] = "+";
                        newClientInfo[6] = "-";
                        newClientInfo[8] = "Добавить";
                        newClientInfo[9] = "...";
                        redreshPersonInfoInTable(index,newClientInfo);

                        //запись в файл
                        write.append(surname+" ");
                        write.append(name+" ");
                        write.append(secondName+"|");
                        write.append(birthday+"|");
                        write.append(cartNumber+"|");
                        write.append(phone+"|");
                        Files.write(path, new String(Files.readAllBytes(path), charset).replace(lineInfo, write + "").getBytes(charset));

                        File oldFile = new File(NewClient.dirPathForClientPath+lineInfo.substring(0,lineInfo.indexOf('|'))+".txt");
                        File newFile = new File(NewClient.dirPathForClientPath+write.substring(0,write.indexOf("|"))+".txt");

                        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(oldFile), StandardCharsets.UTF_8));
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile), StandardCharsets.UTF_8));
                        String line;
                        int indexForNewLine = 0;
                        while ((line = reader.readLine()) != null){
                            if (indexForNewLine != 0){
                                writer.write("\n"+line);
                            }
                            else {
                                writer.write(line);
                                indexForNewLine++;
                            }
                        }
                        reader.close();
                        writer.close();

                        Path pathForDelete = Paths.get(oldFile+"");
                        Files.delete(pathForDelete);
                        dispose();
                    }
                }
                catch (NoSuchFileException x) {
                    System.err.format("%s: no such" + " file or directory%n");
                } catch (DirectoryNotEmptyException x) {
                    System.err.format("%s not empty%n");
                } catch (IOException x) {
                    // File permission problems are caught here.
                    System.err.println(x);
                }
                catch (Exception ee){
                    System.out.println("Error");
                }

            }
        });
    }

    private void loadClietInfoToField(int index){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(ClientTable.clientsFile), StandardCharsets.UTF_8));
             BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(NewClient.dirPathForClientPath+ClientTable.clientInfo[index][0]+".txt"), StandardCharsets.UTF_8))){

            String line;
            StringBuffer tempLine = new StringBuffer("");
            while ((line = reader.readLine()) != null){
                if (line.contains(ClientTable.clientInfo[index][0]+"|"+ClientTable.clientInfo[index][7])){
                    tempLine = new StringBuffer(line);
                    StringBuffer fio = new StringBuffer(tempLine.substring(0,tempLine.indexOf("|")));
                    tempLine.delete(0,tempLine.indexOf("|")+1);
                    fieldSurname.setText(fio.substring(0,fio.indexOf(" ")));
                    fio.delete(0,fio.indexOf(" ")+1);
                    fieldFirstName.setText(fio.substring(0,fio.indexOf(" ")));
                    fio.delete(0,fio.indexOf(" ")+1);
                    fieldSecondName.setText(fio+"");
                    fieldBirthday.setText(tempLine.substring(0,tempLine.indexOf("|")));
                    tempLine.delete(0,tempLine.indexOf("|")+1);
                    fieldWCartNumber.setText(tempLine.substring(0,tempLine.indexOf("|")));
                    tempLine.delete(0,tempLine.indexOf("|")+1);
                    fieldPhone.setText(tempLine.substring(0,tempLine.indexOf("|")));
                    //tempLine.delete(0,tempLine.indexOf("|")+1);
                    lineInfo = line;
                    break;
                }
            }
            reader.close();

            String manOrWoman = reader1.readLine();
            reader1.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
