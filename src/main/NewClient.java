package main;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;

import static main.ClientTable.tableTitle;
import static main.Form.addTableRow;


public class NewClient extends JFrame {
    private int COUNT_COLUMNS = 30;
    public static Object[] newClientInfo;
    public static String dirPathForClientPath = "E:\\temp_file\\clients\\";

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
                    labelTitle = new JLabel("РЕГИСТРАЦИЯ НОВОГО КЛИЕНТА"),
                    line = new JLabel(""),
                    line2 = new JLabel(""),
                    labelContactPhone = new JLabel("Телефон"),
                    labelNotPerfom = new JLabel(""),
                    labelCartNumber = new JLabel("Номер карты");

    private JButton button_finish = new JButton("Зарегистрировать");

    public NewClient(int form_width) {
        //шаблон
        setLayout(null);

        //Бордер для линии
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

        //настройка компонентов
        configurateComponent();

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
        add(labelTitle).setBounds(42,10,200,30);
        line.setBorder(border); line2.setBorder(border);
        add(line).setBounds(0,40,form_width,1);
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
        add(radioMan).setBounds(110,230,40,20);
        add(radioWoman).setBounds(200,230,40,20);
        add(line2).setBounds(0,255,form_width,1);
        add(button_finish).setBounds(40,270,200,20);
        add(labelNotPerfom).setBounds(90,300,200,20);


        //кнопка зарегистрировать
        buttonRegistrateAction();
    }


    //настройка компонентов
    private void configurateComponent(){
        String fontFamily = "Arial";
        Font fontLabel = new Font(fontFamily,Font.PLAIN,25);
        labelNotPerfom.setFont(fontLabel);

    }


    //кнопка зарегистрировать
    private void buttonRegistrateAction() {
        button_finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ClientTable.clientsFile, true), StandardCharsets.UTF_8))){

                    String  name = fieldFirstName.getText().trim(),
                            secondName = fieldSecondName.getText().trim(),
                            surname = fieldSurname.getText().trim(),
                            birthday = fieldBirthday.getText(),
                            cartNumber = fieldWCartNumber.getText().trim(),
                            phone = fieldPhone.getText();



                    if (name.equals("") || secondName.equals("") || surname.equals("") || birthday.equals("")
                            || phone.equals("") || cartNumber.equals("") || (!radioMan.isSelected() && !radioWoman.isSelected())) {
                        labelNotPerfom.setText("Ошибка");
                    }
                    else {
                        labelNotPerfom.setText("");
                        //запись в массив
                        newClientInfo = new Object[tableTitle.length];
                        newClientInfo[0] = surname + " " + name + " " + secondName;
                        newClientInfo[1] = cartNumber;
                        newClientInfo[2] = phone;
                        newClientInfo[7] = birthday;
                        newClientInfo[3] = "--";
                        newClientInfo[4] = "--";
                        newClientInfo[5] = "+";
                        newClientInfo[6] = "-";
                        newClientInfo[8] = "Добавить";
                        newClientInfo[9] = "Заморозить";
                        newClientInfo[10] = "...";

                        //запись в файл
                        write.append(surname+" ");
                        write.append(name+" ");
                        write.append(secondName+"|");
                        write.append(birthday+"|");
                        write.append(cartNumber+"|");
                        write.append(phone+"|\n");
                        dispose();
                        File clientFile = new File(dirPathForClientPath+surname+" "+name+" "+secondName+".txt");
                        clientFile.createNewFile();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dirPathForClientPath+surname+" "+name+" "+secondName+".txt"), StandardCharsets.UTF_8));
                        if (radioMan.isSelected()){
                            writer.append("M");
                        }
                        else {
                            writer.append("Ж");
                        }
                        addTableRow();
                        writer.close();
                    }
                    write.flush();
                    write.close();
                }
                catch (IOException ee){
                    System.out.println("Error");
                }

            }
        });
    }
}
