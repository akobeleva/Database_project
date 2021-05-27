package GUI;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class RegistrationWindow extends JFrame {
    private JComboBox typeComboBox;
    private JButton regButton;
    private JLabel label;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel roleLabel;
    private JPasswordField passwordField;
    private JPanel mainPanel;
    private JTextField usernameTextField;
    private JComboBox userComboBox;
    private JLabel userLabel;
    private JButton backButton;
    private Map<String, String> roles = new HashMap<String, String>() {{
        put("Пациент", "patient");
        put("Регистратура поликлиники", "polyclinic_registry");
        put("Регистратура больницы", "hospital_registry");
    }};
    private final Map<String, String> patients = new HashMap<>();
    private final Map<String, String> hospitals = new HashMap<>();
    private final Map<String, String> polyclinics = new HashMap<>();

    public RegistrationWindow() {
        this.setContentPane(mainPanel);
        setComboBoxItems();
        regButton.addActionListener(e -> {
            createUser();
            WindowsManager.setMainFramesVisible("regWindow", false);
            WindowsManager.setMainFramesVisible("mainWindow", true);
        });
        backButton.addActionListener(e->{
            WindowsManager.setMainFramesVisible("regWindow", false);
            WindowsManager.setMainFramesVisible("mainWindow", true);
        });
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void createUser() {
        String username = usernameTextField.getText();
        String password = new String(passwordField.getPassword());
        String role = roles.get(typeComboBox.getSelectedItem());
        ConnectionManager.executeQuery("INSERT INTO users(username, password, role) VALUES ('" + username + "','" + password + "','" + role + "')");
        ConnectionManager.executeQuery("CREATE USER \"" + username + "\" IDENTIFIED BY \"" + password + "\" DEFAULT TABLESPACE " +
                "USERS TEMPORARY TABLESPACE TEMP");
        ConnectionManager.executeQuery("GRANT connect to \"" + username+ "\"");
        ConnectionManager.executeQuery("GRANT " + role + " to \"" + username + "\"");
        String tableName = new String();
        String columnName = new String();
        String id = new String();
        if (typeComboBox.getSelectedIndex() == 0) {
            tableName = "users_patients";
            columnName = "patient_id";
            id = patients.get(userComboBox.getSelectedItem());
        }
        else if (typeComboBox.getSelectedIndex() == 1) {
            tableName = "users_hospitals";
            columnName = "hospital_id";
            id = hospitals.get(userComboBox.getSelectedItem());
        }
        else if (typeComboBox.getSelectedIndex() == 2) {
            tableName = "users_polyclinics";
            columnName = "polyclinic_id";
            id = polyclinics.get(userComboBox.getSelectedItem());
        }
        ConnectionManager.executeQuery("INSERT INTO " + tableName + "(user_id, " + columnName + ") SELECT max(user_id), " +
                id + " FROM users");
    }

    private void setComboBoxItems(){
        typeComboBox.addItem("Пациент");
        typeComboBox.addItem("Регистратура больницы");
        typeComboBox.addItem("Регистратура поликлиники");
        typeComboBox.addItemListener(e -> {
            userComboBox.removeAllItems();
            if (typeComboBox.getSelectedIndex() == 0) {
                Vector hospitalsItems = ConnectionManager.select("SELECT patient_id, p.surname || ' ' || p.name " +
                        "|| ' ' || p.patronymic  from patients p", 2);
                for (Object vectorItem : hospitalsItems) {
                    Vector<String> vec = (Vector<String>) vectorItem;
                    patients.put(vec.get(1), vec.get(0));
                    userComboBox.addItem(vec.get(1));
                }
            }
            else if (typeComboBox.getSelectedIndex() == 1) {
                Vector hospitalsItems = ConnectionManager.select("SELECT hospital_id, name from hospitals", 2);
                for (Object vectorItem : hospitalsItems) {
                    Vector<String> vec = (Vector<String>) vectorItem;
                    hospitals.put(vec.get(1), vec.get(0));
                    userComboBox.addItem(vec.get(1));
                }
            }
            else if (typeComboBox.getSelectedIndex() == 2) {
                Vector polyclinicsItems = ConnectionManager.select("SELECT polyclinic_id, name from polyclinics", 2);
                for (Object vectorItem : polyclinicsItems) {
                    Vector<String> vec = (Vector<String>) vectorItem;
                    polyclinics.put(vec.get(1), vec.get(0));
                    userComboBox.addItem(vec.get(1));
                }
            }
        });
        typeComboBox.setSelectedIndex(-1);
    }
}
