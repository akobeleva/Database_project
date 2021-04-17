package GUI;

import DAL.CreateDatabase;
import GUI.Table.TableView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MainWindow extends JFrame{
    private Map<String, String> nameTables = new HashMap<String, String>(){{
            put("Doctors", "GUI.Table.DoctorsTable");
            put("Specialities", "GUI.Table.SpecialitiesTable");
            put("Hospitals", "GUI.Table.HospitalsTable");
            put("Polyclinics", "GUI.Table.PolyclinicsTable");
            put("Buildings", "GUI.Table.BuildingsTable");
            put("Departments", "GUI.Table.DepartmentsTable");
        }
    };
    private JPanel mainPanel;
    private JLabel tablesLabel;
    private JTable listTable;
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
        Vector<String> tables = new Vector(Arrays.asList(nameTables.keySet().toArray()));
        dtm.addColumn("Tables", tables);
        listTable.setModel(dtm);
        listTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    int rowIndex = listTable.rowAtPoint(e.getPoint());
                    String row = tables.get(rowIndex);
                    try {
                        TableView tableView = (TableView)Class.forName(nameTables.get(row)).getConstructor(String.class).newInstance(row);
                    } catch (InstantiationException instantiationException) {
                        instantiationException.printStackTrace();
                    } catch (IllegalAccessException illegalAccessException) {
                        illegalAccessException.printStackTrace();
                    } catch (InvocationTargetException invocationTargetException) {
                        invocationTargetException.printStackTrace();
                    } catch (NoSuchMethodException noSuchMethodException) {
                        noSuchMethodException.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
            }
        });

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
