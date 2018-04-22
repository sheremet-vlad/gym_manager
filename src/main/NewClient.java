package main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class NewClient extends JFrame {
    private int COUNT_COLUMNS = 30;

    private JTextField  fieldFirstName = new JTextField(COUNT_COLUMNS),
                        fieldSurname = new JTextField(COUNT_COLUMNS),
                        fieldSecondName = new JTextField(COUNT_COLUMNS),
                        fieldBirthday = new JTextField(COUNT_COLUMNS),
                        fieldPhone = new JTextField(COUNT_COLUMNS),
                        fieldWCartNumber = new JTextField(COUNT_COLUMNS);
    private JLabel  labelName = new JLabel("Имя"),
                    labelSurname = new JLabel("Фамилия"),
                    labelSecondName = new JLabel("Отчество"),
                    labelBirthday = new JLabel("Дата рождения"),
                    labelTitle = new JLabel("РЕГИСТРАЦИЯ НОВОГО КЛИЕНТА"),
                    line = new JLabel(""),
                    line2 = new JLabel(""),
                    labelContactPhone = new JLabel("Телефон"),
                    labelCartNumber = new JLabel("Номер карты");

    private JButton button_finish = new JButton("Зарегистрировать");

    public NewClient(int form_width) {
        //шаблон
        setLayout(null);

        //Бордер для линии
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

        //настройка компонентов
        configurateComponent();

        //добавление компонетов
        add(labelTitle).setBounds(50,10,500,50);
        line.setBorder(border); line2.setBorder(border);
        add(line).setBounds(0,70,form_width,1);
        add(labelSurname).setBounds(90,90,200,40);
        add(fieldSurname).setBounds(220,90,200,40);
        add(labelName).setBounds(150,160,200,40);
        add(fieldFirstName).setBounds(220,160,200,40);
        add(labelSecondName).setBounds(90,230,200,40);
        add(fieldSecondName).setBounds(220,230,200,40);
        add(labelBirthday).setBounds(20,300,200,40);
        add(fieldBirthday).setBounds(220,300,200,40);
        add(labelContactPhone).setBounds(90,370,200,40);
        add(fieldPhone).setBounds(220,370,200,40);
        add(labelCartNumber).setBounds(30,440,200,40);
        add(fieldWCartNumber).setBounds(220,440,200,40);
        add(line2).setBounds(0,500,form_width,1);
        add(button_finish).setBounds(220,510,200,40);

        //кнопка зарегистрировать
        buttonRegistrateAction();
    }


    //настройка компонентов
    private void configurateComponent(){
        String fontFamily = "Arial";
        Font fontField = new Font(fontFamily,Font.PLAIN,20);
        Font fontLabel = new Font(fontFamily,Font.PLAIN,25);
        Font fontTitle = new Font(fontFamily,Font.PLAIN,30);
        Font fontButton = new Font(fontFamily,Font.PLAIN,20);

        labelTitle.setFont(fontTitle);
        line.setBackground(Color.BLACK);

        fieldFirstName.setFont(fontField);
        fieldSecondName.setFont(fontField);
        fieldSurname.setFont(fontField);
        fieldBirthday.setFont(fontField);
        fieldWCartNumber.setFont(fontField);
        fieldPhone.setFont(fontField);

        labelCartNumber.setFont(fontLabel);
        labelContactPhone.setFont(fontLabel);
        labelName.setFont(fontLabel);
        labelBirthday.setFont(fontLabel);
        labelSecondName.setFont(fontLabel);
        labelSurname.setFont(fontLabel);

        button_finish.setFont(fontButton);
        button_finish.setBackground(Color.GRAY);
    }


    //кнопка зарегистрировать
    private void buttonRegistrateAction() {
        button_finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileWriter write = new FileWriter("E:\\temp_file\\clients.txt",true);
                    String  name = fieldFirstName.getText(),
                            secondName = fieldSecondName.getText(),
                            surname = fieldSurname.getText(),
                            birthday = fieldBirthday.getText(),
                            work = fieldWCartNumber.getText(),
                            phone = fieldPhone.getText();



                    if (name.equals("") && secondName.equals("") && surname.equals("") && birthday.equals("")
                            && phone.equals("") && work.equals("")) {
                        button_finish.setBackground(Color.RED);
                        try {
                            Thread.sleep(1000);
                        }
                        catch (Exception eee){}
                        button_finish.setBackground(Color.GRAY);
                    }
                    else {
                        write.append(name+" | ");
                        write.append(secondName+" | ");
                        write.append(surname+" | ");
                        write.append(birthday+" | ");
                        write.append(work+" | ");
                        write.append(phone+" |\n");
                    }

                    write.flush();

                    fieldFirstName.setText("");
                    fieldSecondName.setText("");
                    fieldSurname.setText("");
                    fieldBirthday.setText("");
                    fieldWCartNumber.setText("");
                    fieldPhone.setText("");

                }
                catch (IOException ee){
                    System.out.println("Error");
                }

            }
        });
    }
}
