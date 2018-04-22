package main;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import static create_gui_form.SwingConsole.run;
import static create_gui_form.SwingConsole.run1;
import static main.ClientTable.clientInfo;
import static main.ClientTable.loadInformation;
import static main.ClientTable.tableTitle;

public class Form extends JFrame {
    private JButton button_new_client = new JButton("Новый клиент");
    private JTextField field_find_client = new JTextField(40);
    private JLabel label_find = new JLabel("Поиск");
    private JButton button_find = new JButton("Найти");
    private JScrollPane scrollpan_clients;
    public JTable tableClints;
    private int heightFirstLine;

    //добавление компонентов на форму
    public Form() {
        //настройка размеров экрана
        setupComponentSize();

        //настройка шрифтов
        configurateComponent();

        //настройка макета
        setLayout(null);

        //добавление компонентов
        add(button_new_client).setBounds(20,20,200, heightFirstLine);
        add(label_find).setBounds(420,20,100,heightFirstLine);
        add(field_find_client).setBounds(510,20,710,heightFirstLine+1);


        //действия компонентов
        componentAction();

        //размер экрана
        Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();

        //add(scrollPane);
        loadClientTable();

        //загрузка таблицы клиентов
        add(scrollpan_clients).setBounds(20,90,screenSize.width-40,600);
    }

    //установка размеров компонентов
    private void setupComponentSize(){
        heightFirstLine = 40;
    }



    //настройка компонентов
    private void configurateComponent() {
        String FONT_FAMILY = "Arial";
        Font font_button = new Font(FONT_FAMILY, Font.PLAIN,20);
        Font font_label = new Font(FONT_FAMILY, Font.PLAIN, 30);
        button_new_client.setFont(font_button);
        label_find.setFont(font_label);
        field_find_client.setFont(font_button);
        button_find.setFont(font_button);
    }


    //дествия компонетов
    public void componentAction() {
        button_new_client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame form_new_client = run1(new NewClient(600), 600,600);
            }
        });
    }

    //загрузка таблицы клиентов
    public void loadClientTable(){

        loadInformation();

        tableClints = new JTable(clientInfo,tableTitle);

        //устанавливаем ширирину ячеек
        tableClints.getColumnModel().getColumn(0).setPreferredWidth(450);
        tableClints.getColumnModel().getColumn(1).setPreferredWidth(250);
        tableClints.getColumnModel().getColumn(2).setPreferredWidth(200);
        tableClints.getColumnModel().getColumn(3).setPreferredWidth(400);
        tableClints.getColumnModel().getColumn(4).setPreferredWidth(150);
        tableClints.getColumnModel().getColumn(5).setPreferredWidth(150);
        tableClints.getColumnModel().getColumn(6).setPreferredWidth(150);
        tableClints.getColumnModel().getColumn(7).setPreferredWidth(50);

        scrollpan_clients = new JScrollPane(tableClints);
        tableClints.setFillsViewportHeight(true);

    }
}


/* public Form() {
        //настройка шрифтов
        configurateComponent();

        //настройка макета
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.NONE;
        c.weighty = 0.5;

        //кнопка новый клиент
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(20, 20, 0, 0);
        c.ipadx = 20;
        c.ipady = 5;
        c.weightx = 0.2;
        c.gridheight = 1;
        c.gridwidth  = 1;
        gbl.setConstraints(button_new_client, c);
        add(button_new_client);

        //надпись найти
        c.gridx = 1;
        c.anchor = GridBagConstraints.NORTHEAST;
        c.insets = new Insets(18,40,0,-20);
        gbl.setConstraints(label_find, c);
        add(label_find);

        //текстовое поле поиск
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 0.1;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(20,0,0,0);
        c.ipady = 12;
        gbl.setConstraints(field_find_client, c);
        add(field_find_client);

        //кнопка найти
        c.gridx = 3;
        c.weightx = 1;
        c.ipady = 5;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(20,0,0,0);
        gbl.setConstraints(button_find, c);
        add(button_find);

        //действия компонентов
        componentAction();

        //размер экрана
        Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();

        //загрузка таблицы клиентов
        //setLayout(null);
        String[][] clientInfo = new String[0][0];
        tableClints = new JTable(clientInfo,tableTitle);
        TableColumn column = null;
        for (int i = 0; i < tableTitle.length; i++) {
            column = tableClints.getColumnModel().getColumn(i);
            if (i == 2) {
                column.setPreferredWidth(100); //third column is bigger
            } else {
                column.setPreferredWidth(400);
            }
        }

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        JScrollPane scrollPane = new JScrollPane(tableClints);
        gbl.setConstraints(scrollPane, c);
        add(scrollPane).setBounds(20,70,screenSize.width,500);
        //add(scrollPane);
        loadClientTable();
    }
*/
