package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

    /**
     * @version 1.0 11/09/98
     */
    public class JButtonTableExample extends JFrame {

        public JButtonTableExample(){
            super( "JButtonTable Example" );

            DefaultTableModel dm = new DefaultTableModel();
            dm.setDataVector(new Object[][]{{"button 1","foo"},
                            {"button 2","bar"}},
                    new Object[]{"Button","String"});

            JTable table = new JTable(dm);
            table.getColumn("Button").setCellRenderer(new ButtonRenderer());
            table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
            JScrollPane scroll = new JScrollPane(table);
            getContentPane().add( scroll );
            setSize( 400, 100 );
            setVisible(true);
        }

        public static void main(String[] args) {
            JButtonTableExample frame = new JButtonTableExample();
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
        }
    }