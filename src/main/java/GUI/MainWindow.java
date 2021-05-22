package GUI;

import DAL.ConnectionManager;
import DAL.CreateDatabase;
import GUI.Table.TableView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
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
            put("Пациенты", "GUI.Table.PatientsTable");
            put("Приемы поликлиник", "GUI.Table.PolyclinicCardTable");
            put("Карты стационарного лечения больниц", "GUI.Table.HospitalCardTable");
        }
    };
    private Map<String, Role> roles = new HashMap<String, Role>(){{
        put("admin", Role.ADMIN);
        put("patient", Role.PATIENT);
        put("polyclinic_registry", Role.POlYCLINIC_REGISTRY);
        put("hospital_registry", Role.HOSPITAL_REGISTRY);
    }};
    private JPanel mainPanel;
    private JLabel tableLabel;
    private JTable listTable;
    private JButton createTablesButton;
    private JButton requestButton;
    private JButton backButton;
    private JButton createUserButton;
    private String userID;
    private Role role;

    public MainWindow(String userID){
        this.setTitle("Информационная система медицинских организаций");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);

        this.setContentPane(mainPanel);

        this.userID = userID;
        Vector<String> vectorRole = ConnectionManager.select("SELECT role from users where (user_id = '" + userID + "')", 1);
        for (Object vectorItem : vectorRole) {
            Vector<String> vec = (Vector<String>) vectorItem;
            role = roles.get(vec.get(0));
        }

        addButtonSettings();
        addTableSettings();

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void addButtonSettings(){
        createTablesButton.addActionListener(e -> {
            CreateDatabase database = new CreateDatabase();
            database.create();
        });
        if (role != Role.ADMIN) createTablesButton.setVisible(false);
        createUserButton.addActionListener(e->{
            WindowsManager.setMainFramesVisible("mainWindow", false);
            WindowsManager.addMainFrame(new RegistrationWindow(),"regWindow");
        });
        if (role != Role.ADMIN) createUserButton.setVisible(false);
        requestButton.addActionListener(e->{
            WindowsManager.setMainFramesVisible("mainWindow", false);
            WindowsManager.addMainFrame(new RequestsWindow(), "requestWindow");
        });
        backButton.addActionListener(e->{
            WindowsManager.setMainFramesVisible("mainWindow", false);
            WindowsManager.setMainFramesVisible("startWindow", true);
            try {
                ConnectionManager.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void addTableSettings(){
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
                            WindowsManager.addTableWindow((TableView)Class.forName(nameTables.get(row)).getConstructor(String.class, String.class, Role.class)
                                    .newInstance(row, userID, role), row);
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
    }
}
