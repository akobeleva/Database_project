package GUI;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class AuthorizationWindow extends JFrame {
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton authButton;
    private JLabel label;
    private JPanel mainPanel;
    private JLabel usernameLAbel;
    private JLabel passwordLabel;
    private Map<String, Role> roles = new HashMap<String, Role>(){{
        put("admin", Role.ADMIN);
        put("patient", Role.PATIENT);
        put("polyclinic_registry", Role.POlYCLINIC_REGISTRY);
        put("hospital_registry", Role.HOSPITAL_REGISTRY);
    }};

    public AuthorizationWindow(){
        this.setContentPane(mainPanel);
        authButton.addActionListener(e->{
            authorization();
        });
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void authorization(){
        String username = usernameTextField.getText();
        String password = new String (passwordField.getPassword());
        Role role = null;
        Vector<String> vectorRole = ConnectionManager.select("SELECT role from users where (username = '" + username
                + "') and (password = '" + password + "')", 1);
        if (vectorRole.size() == 0){
            ErrorWindow error = new ErrorWindow();
        }
        else {
            for (Object vectorItem : vectorRole) {
                Vector<String> vec = (Vector<String>) vectorItem;
                role = roles.get(vec.get(0));
                System.out.println(vec.get(0));
            }
            WindowsManager.addMainFrame(new MainWindow(role), "mainWindow");
            WindowsManager.setMainFramesVisible("authWindow", false);
        }
    }
}
