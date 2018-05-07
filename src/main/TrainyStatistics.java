package main;

import javax.swing.*;

public class TrainyStatistics extends JFrame {
    public static String dirPathFotStatistics = "E:\\temp_file\\statistics";
    public static String fileNameForStatiscs;

    private JLabel labelMan = new JLabel("М");
    private JLabel labelWoman = new JLabel("Ж");
    private JLabel labelDayMorning = new JLabel("За день до 17:00");
    private JLabel labelDayEvning = new JLabel("За день после 17:00");
    private JLabel labelWeekMorning = new JLabel("За неделю до 17:00");
    private JLabel labelWeekEvning = new JLabel("За неделю после 17:00");
    private JLabel labelMonthMorning = new JLabel("За месяц до 17:00");
    private JLabel labelMonthEvening = new JLabel("За месяц после 17:00");

    private JTextField fieldDayMorningM = new JTextField(10);
    private JTextField fieldDayEveningM = new JTextField(10);
    private JTextField fieldWeekMorningM = new JTextField(10);
    private JTextField fieldWeekEveningM = new JTextField(10);
    private JTextField fieldMonthMorningM = new JTextField(10);
    private JTextField fieldMonthEveningM = new JTextField(10);

    private JTextField fieldDayMorningW = new JTextField(10);
    private JTextField fieldDayEveningW = new JTextField(10);
    private JTextField fieldWeekMorningW = new JTextField(10);
    private JTextField fieldWeekEveningW = new JTextField(10);
    private JTextField fieldMonthMorningW = new JTextField(10);
    private JTextField fieldMonthEveningW = new JTextField(10);

    public TrainyStatistics(){
        setLayout(null);

        int widthLabel = 140;
        int leftSpacLabel = 10;
        int hightLabel = 20;
        int widthField = 40;
        int heightField = 20;
        int leftSpaceFieldM = 165;
        int leftSpaceFieldW = 215;
        add(labelMan).setBounds(180,10,20,20);
        add(labelWoman).setBounds(230,10,20,20);
        add(labelDayMorning).setBounds(leftSpacLabel+30,40,widthLabel,hightLabel);
        add(labelDayEvning).setBounds(leftSpacLabel+10,80,widthLabel,hightLabel);
        add(labelWeekMorning).setBounds(leftSpacLabel+12,120,widthLabel,hightLabel);
        add(labelWeekEvning).setBounds(leftSpacLabel-8,160,widthLabel,hightLabel);
        add(labelMonthMorning).setBounds(leftSpacLabel+23,200,widthLabel,hightLabel);
        add(labelMonthEvening).setBounds(leftSpacLabel+3,240,widthLabel,hightLabel);

        add(fieldDayMorningM).setBounds(leftSpaceFieldM,40,widthField,heightField);
        add(fieldDayMorningW).setBounds(leftSpaceFieldW,40,widthField,heightField);
        add(fieldDayEveningM).setBounds(leftSpaceFieldM,80,widthField,heightField);
        add(fieldDayEveningW).setBounds(leftSpaceFieldW,80,widthField,heightField);
        add(fieldWeekMorningM).setBounds(leftSpaceFieldM,120,widthField,heightField);
        add(fieldWeekMorningW).setBounds(leftSpaceFieldW,120,widthField,heightField);
        add(fieldWeekEveningM).setBounds(leftSpaceFieldM,160,widthField,heightField);
        add(fieldWeekEveningW).setBounds(leftSpaceFieldW,160,widthField,heightField);
        add(fieldMonthMorningM).setBounds(leftSpaceFieldM,200,widthField,heightField);
        add(fieldMonthMorningW).setBounds(leftSpaceFieldW,200,widthField,heightField);
        add(fieldMonthEveningM).setBounds(leftSpaceFieldM,240,widthField,heightField);
        add(fieldMonthEveningW).setBounds(leftSpaceFieldW,240,widthField,heightField);

    }
}
