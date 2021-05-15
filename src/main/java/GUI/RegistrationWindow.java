package GUI;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class RegistrationWindow extends JFrame {
    private JComboBox comboBox;
    private JButton regButton;
    private JLabel label;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel roleLabel;
    private JPasswordField passwordField;
    private JPanel mainPanel;
    private JTextField usernameTextField;
    private Map<String, String> roles = new HashMap<String, String>() {{
        put("Пациент", "patient");
        put("Регистратура поликлиники", "polyclinic_registry");
        put("Регистратура больницы", "hospital_registry");
    }};

    public RegistrationWindow() {
        this.setContentPane(mainPanel);
        comboBox.addItem("Пациент");
        comboBox.addItem("Регистратура поликлиники");
        comboBox.addItem("Регистратура больницы");
        comboBox.setSelectedIndex(-1);
        regButton.addActionListener(e -> {
            createUser();
            WindowsManager.setMainFramesVisible("regWindow", false);
            WindowsManager.setMainFramesVisible("enterWindow", true);
        });
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void createUser() {
        String username = usernameTextField.getText();
        String password = new String(passwordField.getPassword());
        String role = roles.get(comboBox.getSelectedItem());
        ConnectionManager.executeQuery("INSERT INTO users(username, password, role) VALUES ('" + username + "','" + password + "','" + role + "')");
        ConnectionManager.executeQuery("CREATE USER \"" + username + "\" IDENTIFIED BY \"" + password + "\" DEFAULT TABLESPACE " +
                "USERS TEMPORARY TABLESPACE TEMP");
        ConnectionManager.executeQuery("GRANT connect to \"" + username+ "\"");
        ConnectionManager.executeQuery("GRANT " + role + " to \"" + username + "\"");
    }
}
