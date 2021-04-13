package GUI;

import DAL.CreateDatabase;
import GUI.DoctorsView.DoctorsTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Vector;

public class MainWindow extends JFrame{
    private String [] nameTables = {"Doctors", "Hospitals", "Specialities"};
    private JPanel mainPanel;
    private JLabel tablesLabel;
    private JTable table;
    private JButton createTablesButton;

    public MainWindow(){
        this.setTitle("Информационная система медицинских организаций");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);

        this.setContentPane(mainPanel);

        createTablesButton.addActionListener(e -> {
            CreateDatabase database = new CreateDatabase();
            database.create();
        });

        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int i, int j){
                return false;
            }
        };
        Vector tables = new Vector(Arrays.asList(nameTables));
        dtm.addColumn("Tables", tables);
        table.setModel(dtm);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    int row = table.rowAtPoint(e.getPoint());
                    DoctorsTable doctorsTable = new DoctorsTable(nameTables[row]);
                }
            }
        });

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
