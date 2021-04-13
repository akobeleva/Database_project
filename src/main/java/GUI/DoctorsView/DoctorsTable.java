package GUI.DoctorsView;

import DAL.ConnectionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.util.Vector;

public class DoctorsTable extends JFrame{
    private JTable table;
    private JPanel mainPanel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JScrollPane scrollPane;
    private JTableHeader tableHeader;

    public DoctorsTable(String name){
        this.setTitle(name);
        this.setSize(800, 600);
        this.setContentPane(mainPanel);

        updateButton.addActionListener(e->{
            updateTable();
        });

        addButton.addActionListener(e->{
            AddDoctor addDoctor = new AddDoctor();
        });

        editButton.addActionListener(e->{

        });

        deleteButton.addActionListener(e->{
            DeleteDoctor deleteDoctor = new DeleteDoctor();
        });
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    private void updateTable(){
        Vector values = ConnectionManager.select("SELECT * FROM doctors", 9);
        Vector header = new Vector();
        header.add("ID");
        header.add("Фамилия");
        header.add("Имя");
        header.add("Отчество");
        header.add("докт. мед.наук");
        header.add("канд. мед.наук");
        header.add("Профессор");
        header.add("Доцент");
        header.add("Стаж");

        DefaultTableModel dtm = (DefaultTableModel)table.getModel();
        dtm.setDataVector(values, header);
        //mainPanel.add(table.getTableHeader());
    }
}
