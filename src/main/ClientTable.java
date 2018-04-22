package main;

import java.awt.*;
import java.io.*;

public class ClientTable {
    public static String[] tableTitle = {"Клиент","Телефон","Текущий абонемент","Срок действия (ост. посещения)","Посещение (+/-)","Дата рождения","Добавление аб-а","..."};
    public static String[][] clientInfo;

    public static void loadInformation(){
        try {
            BufferedReader countLength = new BufferedReader(new FileReader("E:\\temp_file\\clients.txt"));

            //размер таблицы
            LineNumberReader count = new LineNumberReader(countLength);
            while (count.skip(Long.MAX_VALUE) > 0)
            {
               // Loop just in case the file is > Long.MAX_VALUE or skip() decides to not read the entire file
            }
            clientInfo = new String[count.getLineNumber()][8];
            count.close();
            countLength.close();


            //заполнение таблицы
            BufferedReader reader = new BufferedReader(new FileReader("E:\\temp_file\\clients.txt"));
            String templinel;

            System.out.println(reader.readLine());


        }
        catch (IOException ee){
            ee.printStackTrace();
            System.out.println();
        }
    }
}
