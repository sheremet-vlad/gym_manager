package main;

import filter.DigitFilter;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class NewSubscription extends JFrame {
    public static String fileTypeSubscription = "E:\\temp_file\\subscriptions.txt";

    private ButtonGroup
                        createOrChange= new ButtonGroup(),
                        limOtUnlim = new ButtonGroup();
    private JComboBox comboboxChange;

    private JTextField
            fieldDate = new JTextField(),
            fieldName = new JTextField("Название абонемнта",40),
            fieldCount = new JTextField();

    private JRadioButton
                        radioCreate = new JRadioButton("Создать"),
                        radioChangee = new JRadioButton("Изменить"),
                        radioLin = new JRadioButton("На посещения"),
                        radioUnlim = new JRadioButton("Безлимитный");

    private JButton buttonPrfom = new JButton("Выполнить");

    private JLabel labelData = new JLabel("Длительность, дн.");
    private JLabel logLabel = new JLabel("");

    public NewSubscription(){
        setLayout(null);

        PlainDocument doc = (PlainDocument) fieldDate.getDocument();
        doc.setDocumentFilter(new DigitFilter());

        PlainDocument doc1 = (PlainDocument) fieldCount.getDocument();
        doc1.setDocumentFilter(new DigitFilter());

        createOrChange.add(radioChangee);
        createOrChange.add(radioCreate);
        add(radioCreate).setBounds(10,10,100,20);
        add(radioChangee).setBounds(110,10,100,20);

        add(fieldName).setBounds(10,70,200,20);
        add(labelData).setBounds(10,100,120,20);
        add(fieldDate).setBounds(125,100,85,20);

        limOtUnlim.add(radioLin);
        limOtUnlim.add(radioUnlim);
        add(radioLin).setBounds(10,130,115,20);
        add(radioUnlim).setBounds(10,150,150,20);

        add(fieldCount).setBounds(125,130,85,20);

        comboboxChange = new JComboBox(readTypeSubscription());
        add(comboboxChange).setBounds(10,40,200,20);
        comboboxChange.setEditable(false);

        add(buttonPrfom).setBounds(55,180,100,20);

        add(logLabel).setBounds(55,210,100,20);

        actionComponents();
        configereComponents();
    }

    public void configereComponents(){
        comboboxChange.setEditable(false);
    }

    private void actionComponents() {
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (radioChangee.isSelected()){
                    comboboxChange.setVisible(true);
                }
                else {
                    comboboxChange.setVisible(false);
                }
            }
        });

        buttonPrfom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioCreate.isSelected()){
                    writNewSubscription();
                }
                else if (radioChangee.isSelected()) {
                    changeSubcription();
                }
            }
        });


        radioChangee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboboxChange = new JComboBox(readTypeSubscription());
                add(comboboxChange).setBounds(10,40,200,20);
                comboboxChange.setEditable(true);
                comboboxChange.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileTypeSubscription), StandardCharsets.UTF_8))){

                            String tempLine,typeSubscription,choosedSubscription = comboboxChange.getSelectedItem()+"|";
                            StringBuffer line;
                            while ((tempLine = reader.readLine()) != null){
                                if (tempLine.contains(choosedSubscription)) {
                                    line = new StringBuffer(tempLine);
                                    fieldName.setText(line.substring(0, line.indexOf("|", 1)));
                                    line.delete(0, line.indexOf("|", 1));
                                    fieldDate.setText(line.substring(1, line.indexOf("|", 1)));
                                    line.delete(0, line.indexOf("|", 1));
                                    typeSubscription = line.substring(0, line.indexOf("|", 1));
                                    if (typeSubscription.contains("lin")) {
                                        radioLin.setSelected(true);
                                        line.delete(0, line.indexOf("|", 1));
                                        fieldCount.setText(line.substring(1, line.indexOf("|", 1)));
                                    } else {
                                        radioUnlim.setSelected(true);
                                        fieldCount.setText("");
                                    }
                                    logLabel.setText("");
                                }
                            }
                        }
                        catch (Exception eeeee){
                        }
                    }
                });
            }
        });
    }

    public String[] readTypeSubscription() {
        ArrayList<String> tempArrayNameSubscription = new ArrayList<>();
        String[] arrayNameSubscription;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileTypeSubscription), StandardCharsets.UTF_8))){

            String tempLine,typeSubscription;
            StringBuffer line;
            while ((tempLine = reader.readLine()) != null){
                line = new StringBuffer(tempLine);
                tempArrayNameSubscription.add(line.substring(0,line.indexOf("|",1)));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        arrayNameSubscription = new String[tempArrayNameSubscription.size()];
        for (int i = 0; i < arrayNameSubscription.length; i++) {
            arrayNameSubscription[i] = tempArrayNameSubscription.get(i);
        }
        return arrayNameSubscription;
    }

    public void writNewSubscription(){
        if (!fieldName.getText().equals("") && !fieldDate.getText().equals("") && ((!fieldCount.getText().equals("") && radioLin.isSelected()) || radioUnlim.isSelected())) {
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(fileTypeSubscription, true), StandardCharsets.UTF_8))) {

                if (radioLin.isSelected()) {
                    writer.write(fieldName.getText() + "|" + fieldDate.getText() + "|lin|" + fieldCount.getText() + "|\n");
                } else {
                    writer.write(fieldName.getText() + "|" + fieldDate.getText() + "|unlim|\n");
                }
                writer.close();
                clearField();
                logLabel.setText("");

            } catch (Exception e) {
                System.out.println("not found file");
            }
        } else {
            logLabel.setText("Ошибка");
        }
    }

    public void changeSubcription() {
        if (!fieldName.getText().equals("") && !fieldDate.getText().equals("") && ((!fieldCount.getText().equals("") && radioLin.isSelected()) || radioUnlim.isSelected())) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileTypeSubscription), StandardCharsets.UTF_8))) {
                ArrayList<String> wtiteInfoTempFile = new ArrayList<>();
                String line, changedLine = comboboxChange.getSelectedItem() + "|";
                while ((line = reader.readLine()) != null) {
                    if (line.contains(changedLine)) {
                        if (radioLin.isSelected()) {
                            wtiteInfoTempFile.add(fieldName.getText() + "|" + fieldDate.getText() + "|lin|" + fieldCount.getText() + "|\n");
                        } else {
                            wtiteInfoTempFile.add(fieldName.getText() + "|" + fieldDate.getText() + "|unlim|\n");
                        }
                    } else {
                        wtiteInfoTempFile.add(line + "\n");
                    }
                }
                reader.close();

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileTypeSubscription), StandardCharsets.UTF_8));
                for (int i = 0; i < wtiteInfoTempFile.size(); i++) {
                    writer.write(wtiteInfoTempFile.get(i));
                }
                writer.close();
                clearField();
                logLabel.setText("Выполнено");

            } catch (Exception ee) {
            }
        }
        else {
            logLabel.setText("Ошибка");
        }

    }

    public void clearField(){
        fieldName.setText("Название абонемента");
        fieldDate.setText("");
        fieldCount.setText("");
        radioLin.setSelected(false);
        radioUnlim.setSelected(false);

    }
}
