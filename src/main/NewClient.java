package main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class NewClient extends JFrame {
    private int COUNT_COLUMNS = 30;

    private JTextField  firstName = new JTextField(COUNT_COLUMNS),
                        surname = new JTextField(COUNT_COLUMNS),
                        secondName = new JTextField(COUNT_COLUMNS),
                        birthday = new JTextField(COUNT_COLUMNS),
                        phone = new JTextField(COUNT_COLUMNS),
                        work = new JTextField(COUNT_COLUMNS);
    private JLabel  labelName = new JLabel("Имя"),
                    labelSurname = new JLabel("Фамилия"),
                    labelSecondName = new JLabel("Отчество"),
                    labelBirthday = new JLabel("Дата рождения"),
                    labelTitle = new JLabel("РЕГИСТРАЦИЯ НОВГО КЛИЕНТА"),
                    line = new JLabel(""),
                    line2 = new JLabel(""),
                    labelContactPhone = new JLabel("Телефон"),
                    labelWork = new JLabel("Место Раб./Уч.");

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
        add(surname).setBounds(220,90,200,40);
        add(labelName).setBounds(150,160,200,40);
        add(firstName).setBounds(220,160,200,40);
        add(labelSecondName).setBounds(90,230,200,40);
        add(secondName).setBounds(220,230,200,40);
        add(labelBirthday).setBounds(20,300,200,40);
        add(birthday).setBounds(220,300,200,40);
        add(labelContactPhone).setBounds(90,370,200,40);
        add(phone).setBounds(220,370,200,40);
        add(labelWork).setBounds(30,440,200,40);
        add(work).setBounds(220,440,200,40);
        add(line2).setBounds(0,500,form_width,1);
        add(button_finish).setBounds(220,510,200,40);
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

        firstName.setFont(fontField);
        secondName.setFont(fontField);
        surname.setFont(fontField);
        birthday.setFont(fontField);
        work.setFont(fontField);
        phone.setFont(fontField);

        labelWork.setFont(fontLabel);
        labelContactPhone.setFont(fontLabel);
        labelName.setFont(fontLabel);
        labelBirthday.setFont(fontLabel);
        labelSecondName.setFont(fontLabel);
        labelSurname.setFont(fontLabel);

        button_finish.setFont(fontButton);
    }


}
