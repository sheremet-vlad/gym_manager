package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static create_gui_form.SwingConsole.run;
import static create_gui_form.SwingConsole.run1;
import static main.ClientTable.*;
import static main.NewClient.newClientInfo;

public class Form extends JFrame {
    private JButton buttonStatistics = new JButton("Статистика посещений");
    private JButton button_new_client = new JButton("Новый клиент");
    private JTextField field_find_client = new JTextField(40);
    private JLabel label_find = new JLabel("Поиск");
    private JButton button_find = new JButton("Найти");
    private JButton buttonNewSubscription = new JButton("Абонементы");
    private JScrollPane scrollpan_clients, scrollpan_clientsBirthday;
    public static JTable tableClints;
    private int heightFirstLine;
    private static JTextField fieldNowInGym = new JTextField(20);
    private JLabel labelNowInGym = new JLabel("Сейчас в зале");
    private static JTable tableBirthdayToday;
    private JLabel labelBirthdayToday = new JLabel("День рождения сегодня");

    public static DefaultTableModel dm = new DefaultTableModel();
    private static int countInGym = 0;

    public static final String PEOPLE_IN_GYM_FILE = "E:\\temp_file\\peopleInGym.txt";


    public Form(int i){
        //для вызова нестатического метода
    }

    //добавление компонентов на форму
    public Form() {
        //настройка размеров экрана
        setupComponentSize();

        //настройка шрифтов
        configurateComponent();

        //настройка макета
        setLayout(null);

        //добавление компонентов
        add(button_new_client).setBounds(20,20,230, heightFirstLine);
        add(label_find).setBounds(290,20,100,heightFirstLine);
        add(field_find_client).setBounds(380,20,560,heightFirstLine+1);
        add(button_find).setBounds(1000,20,230,heightFirstLine);

        //устанавливаем кнопку найти кноппкой по умолчанию
        JRootPane rootPane = SwingUtilities.getRootPane(button_find);
        rootPane.setDefaultButton(button_find);


        //действия компонентов
        componentAction();
        int size = 0;

        //this.setFocusable(true);

        //размер экрана
        Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();

        //add(scrollPane);
        loadClientTable();
        configurTable();
        readCountOfPeopleInGym();

        //загрузка таблицы клиентов
        add(scrollpan_clients).setBounds(20,90,screenSize.width-40,500 + size);

        add(labelNowInGym).setBounds(20,670 + size,200,20);
        add(fieldNowInGym).setBounds(210,670 + size,40,27);

        add(labelBirthdayToday).setBounds(450,625 + size,400,30);
        loadTableBirthday();
        add(scrollpan_clientsBirthday).setBounds(380,660 + size,450,70);

        add(buttonStatistics).setBounds(1000,625 + size,230,heightFirstLine);
        add(buttonNewSubscription).setBounds(1000,700 + size,230,heightFirstLine);
    }

    //установка размеров компонентов
    private void setupComponentSize(){
        heightFirstLine = 40;
    }


    //настройка компонентов
    private void configurateComponent() {
        String FONT_FAMILY = "Arial";
        Font font_button = new Font(FONT_FAMILY, Font.PLAIN,18);
        Font font_label = new Font(FONT_FAMILY, Font.PLAIN, 28);
        Font font_table = new Font(FONT_FAMILY, Font.PLAIN, 13);
        button_new_client.setFont(font_button);
        label_find.setFont(font_label);
        field_find_client.setFont(font_button);
        button_find.setFont(font_button);
        buttonNewSubscription.setFont(font_button);
        labelNowInGym.setFont(font_label);
        fieldNowInGym.setFont(font_label);
        labelBirthdayToday.setFont(font_label);
        buttonStatistics.setFont(font_button);
    }


    public void loadTableBirthday(){
        String[][] infa = BirthdayTable.loadBirthdayTable();
        String[] title = {"ФИО","Номер карты","Телефон"};
        tableBirthdayToday = new JTable(infa,title);
        tableBirthdayToday.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableBirthdayToday.getColumnModel().getColumn(1).setPreferredWidth(80);
        tableBirthdayToday.getColumnModel().getColumn(2).setPreferredWidth(110);

        scrollpan_clientsBirthday = new JScrollPane(tableBirthdayToday);
        tableBirthdayToday.setFillsViewportHeight(true);
    }


    //дествия компонетов
    public void componentAction() {
        button_new_client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run1(new NewClient(305), 305,375);
            }
        });

        buttonNewSubscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run1(new NewSubscription(), 240,300);
            }
        });

        buttonStatistics.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run1(new TrainyStatistics(),440,440);
            }
        });

        button_find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = field_find_client.getText();
                for (int i = 0; i < ClientTable.clientInfo.length; i++) {
                    if (text.equals(ClientTable.clientInfo[i][1]) || (ClientTable.clientInfo[i][0]+"").contains(text)){
                        tableClints.setRowSelectionInterval(i,i);
                        tableClints.scrollRectToVisible(tableClints.getCellRect(i,0,true));
                        break;
                    }
                }
            }
        });


        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    //загрузка таблицы клиентов
    public void loadClientTable(){
        loadInformation(dm);
        tableClints = new JTable(dm);
        tableClints.getColumn("Добавление аб-а").setCellRenderer(new ButtonRenderer());
        tableClints.getColumn("Добавление аб-а").setCellEditor(new ButtonEditorAddSubscription(new JCheckBox()));
        tableClints.getColumn("Карта клиента").setCellRenderer(new ButtonRenderer());
        tableClints.getColumn("Карта клиента").setCellEditor(new ButtonEditorChangeInfo(new JCheckBox()));
        tableClints.getColumn("Пришел").setCellRenderer(new ButtonRenderer());
        tableClints.getColumn("Пришел").setCellEditor(new ButtonEditorPlusTrainy(new JCheckBox()));
        tableClints.getColumn("Ушел").setCellRenderer(new ButtonRenderer());
        tableClints.getColumn("Ушел").setCellEditor(new ButtonEditorEndTrainy(new JCheckBox()));
        tableClints.getColumn("З-ть тек. абон.").setCellRenderer(new ButtonRenderer());
        tableClints.getColumn("З-ть тек. абон.").setCellEditor(new ButtonEditorFreeze(new JCheckBox()));


        JScrollPane scroll = new JScrollPane(tableClints);
        getContentPane().add(scroll);
        tableClints.getColumnModel().getColumn(0).setPreferredWidth(300);
        tableClints.getColumnModel().getColumn(1).setPreferredWidth(60);
        tableClints.getColumnModel().getColumn(2).setPreferredWidth(120);
        tableClints.getColumnModel().getColumn(3).setPreferredWidth(180);
        tableClints.getColumnModel().getColumn(4).setPreferredWidth(300);
        tableClints.getColumnModel().getColumn(5).setPreferredWidth(50);
        tableClints.getColumnModel().getColumn(6).setPreferredWidth(50);
        tableClints.getColumnModel().getColumn(7).setPreferredWidth(100);
        tableClints.getColumnModel().getColumn(8).setPreferredWidth(90);
        tableClints.getColumnModel().getColumn(9).setPreferredWidth(90);
        tableClints.getColumnModel().getColumn(10).setPreferredWidth(40);
        tableClints.getColumnModel().getColumn(11).setPreferredWidth(30);

        scrollpan_clients = new JScrollPane(tableClints);
        tableClints.setFillsViewportHeight(true);

        /*sorter = new TableRowSorter<>(tableClints.getModel());
        tableClints.setRowSorter(sorter);*/
    }

    public static void addTableRow(){
        dm.addRow(newClientInfo);
        Object[][] temparray = Arrays.copyOf(clientInfo,clientInfo.length+1);
        temparray[clientInfo.length] = newClientInfo;
        clientInfo = Arrays.copyOf(clientInfo,temparray.length);
        clientInfo = temparray;
        newClientInfo = null;
        temparray = null;
    }


    public static void redreshPersonInfoInTable(int index, Object[] tempArray){
        dm.insertRow(index,tempArray);
        dm.removeRow(index+1);
        newClientInfo = null;
    }

    public static void refreshDataInPlusButton(int index, int flag){
        Object[] tempArray = clientInfo[index];
        if (flag == 0) {
            tempArray[11] = "в зале";
        } else {
            tempArray[11] = "";
        }

        dm.insertRow(index,tempArray);
        dm.removeRow(index+1);
    }

    public static void refreshValueInTable(String value,int index, int number) {
        dm.setValueAt(value, index, number);
    }

    public static void readCountOfPeopleInGym(){
        int nowInGym = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(PEOPLE_IN_GYM_FILE), StandardCharsets.UTF_8))) {
            nowInGym = Integer.parseInt(reader.readLine());
            fieldNowInGym.setText(nowInGym+"");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        countInGym = nowInGym;

    }


    public static void writeCountOfPeopleInGym(int i){
        countInGym += i;
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PEOPLE_IN_GYM_FILE), StandardCharsets.UTF_8))) {
            writer.write(Integer.toString(countInGym));
            fieldNowInGym.setText(countInGym+"");
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void configurTable() {

    }
}
