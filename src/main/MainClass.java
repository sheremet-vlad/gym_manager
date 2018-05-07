package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;

import static create_gui_form.SwingConsole.run;

public class MainClass {
    public static String fileName = "E:\\temp_file\\clients.txt";
    public DefaultTableModel dm = new DefaultTableModel();
    public static void main(String[] args) {

        //инициализация переменных
        new GlobalVariable();

        //размер экрана
        Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        int heightScreen = sSize.height;
        int widthScreen  = sSize.width;

        //создание главнонй формы
        JFrame main_form = run(new Form(),widthScreen,heightScreen);

    }

}
