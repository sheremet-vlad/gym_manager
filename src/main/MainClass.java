package main;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

import static create_gui_form.SwingConsole.run;

public class MainClass {
    public static void main(String[] args) {
        //размер экрана
        Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        int heightScreen = sSize.height;
        int widthScreen  = sSize.width;

        //создание главнонй формы
        JFrame main_form = run(new Form(),widthScreen,heightScreen);

    }

}
