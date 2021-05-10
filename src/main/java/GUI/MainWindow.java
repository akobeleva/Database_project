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
            put("Врачи", "GUI.Table.DoctorsTable");
            put("Специальности", "GUI.Table.SpecialitiesTable");
            put("Больницы", "GUI.Table.HospitalsTable");
            put("Поликлиники", "GUI.Table.PolyclinicsTable");
            put("Корпусы", "GUI.Table.BuildingsTable");
            put("Отделения", "GUI.Table.DepartmentsTable");
            put("Врачи больниц", "GUI.Table.DoctorsOfHospitalsTable");
            put("Врачи поликлиник", "GUI.Table.DoctorsOfPolyclinicsTable");
            put("Хирурги", "GUI.Table.SurgeonsTable");
            put("Рентгенологи", "GUI.Table.RadiographersTable");
            put("Специльности обслуживающего персонала", "GUI.Table.SpecialitiesOfServiceStaffTable");
            put("Обслуживающий персонал", "GUI.Table.ServiceStaffTable");
            put("Обслуживающий персонал больниц", "GUI.Table.ServiceStaffOfHospitalsTable");
            put("Обслуживающий персонал поликлиник", "GUI.Table.ServiceStaffOfPolyclinicsTable");
        }
    };
    private JPanel mainPanel;
    private JLabel tablesLabel;
    private JTable listTable;
    private JButton createTablesButton;
    private JButton requestButton;
    private JButton backButton;

    public MainWindow(){
        this.setTitle("Информационная система медицинских организаций");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);

        this.setContentPane(mainPanel);

        createTablesButton.addActionListener(e -> {
            CreateDatabase database = new CreateDatabase();
            database.create();
        });

        requestButton.addActionListener(e->{
            WindowsManager.setMainFramesVisible("mainWindow", false);
            WindowsManager.addMainFrame(new RequestsWindow(), "requestWindow");
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
                        WindowsManager.setMainFramesVisible("mainWindow", false);
                        if (WindowsManager.isTableWindowExists(row)){
                            WindowsManager.setTableWindowVisible(row, true);
                            System.out.println("exist");
                        }
                        else {
                            WindowsManager.addTableWindow((TableView)Class.forName(nameTables.get(row)).getConstructor(String.class).newInstance(row), row);
                            System.out.println("not exist");
                        }
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
        backButton.addActionListener(e->{
            WindowsManager.setMainFramesVisible("mainWindow", false);
            WindowsManager.setMainFramesVisible("startWindow", true);
        });
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
