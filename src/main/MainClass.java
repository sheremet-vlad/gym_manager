package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static create_gui_form.SwingConsole.run;

public class MainClass {
    public static String fileName = "E:\\temp_file\\clients.txt";
    public DefaultTableModel dm = new DefaultTableModel();
    public static void main(String[] args) {

        //инициализация переменных
        new GlobalVariable();
        //ExitAction.peopleInGym = new HashMap<>();

        //размер экрана
        Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        int heightScreen = sSize.height;
        int widthScreen  = sSize.width;

        TrainyStatistics.defineStatisticsFile();

        //создание главнонй формы
        JFrame main_form = run(new Form(),widthScreen,heightScreen);

    }

}
