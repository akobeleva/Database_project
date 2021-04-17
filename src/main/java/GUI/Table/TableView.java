package GUI.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class TableView extends JFrame{
    public JTable table;
    public JPanel mainPanel;
    public JButton addButton;
    public JButton updateButton;
    public JScrollPane scrollPane;
    public JTableHeader tableHeader;
    public String tableName;
    public DefaultTableModel dtm;
    public JPopupMenu popup = new JPopupMenu();
    public Integer selectedRow;

    public TableView(String name){
        tableName = name;
        this.setTitle(tableName);
        this.setSize(1000, 800);
        this.setContentPane(mainPanel);

        addPopupMenu();
        addTableSettings();

        updateButton.addActionListener(e->{
            updateTable();
        });

        addButton.addActionListener(e->{
            createNewRow();
        });

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void addPopupMenu(){
        JMenuItem menuItem = new JMenuItem("Изменить");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewRow();
            }
        });
        popup.add(menuItem);
        menuItem = new JMenuItem("Удалить");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteRow(selectedRow);
                updateTable();
            }
        });
        popup.add(menuItem);
    }

    public void addTableSettings(){
        dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int i, int j){
                return false;
            }
        };
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3){
                    System.out.println("right");
                    popup.show(e.getComponent(), e.getX(), e.getY());
                    int row = table.rowAtPoint(e.getPoint());
                    String id = (String) table.getValueAt(row, 0);
                    System.out.println(id);
                    updateSelectedId(Integer.parseInt(id));
                }
            }
        });
    }

    public void updateSelectedId(Integer newId){
        selectedRow = newId;
    }

    public abstract void updateTable();

    public abstract void createNewRow();

    public abstract void editRow();

    public abstract void deleteRow(Integer id);
}
