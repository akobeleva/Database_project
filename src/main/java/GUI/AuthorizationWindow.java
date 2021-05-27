package GUI;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
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
    private JButton backButton;

    public AuthorizationWindow() {
        this.setTitle("Авторизация");
        this.setContentPane(mainPanel);
        this.setSize(350, 200);
        authButton.addActionListener(e -> {
            authorization();
        });
        backButton.addActionListener(e -> {
            WindowsManager.setMainFramesVisible("authWindow", false);
            WindowsManager.setMainFramesVisible("startWindow", true);
            WindowsManager.deleteMainFrame("authWindow");
        });
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void authorization() {
        String username = usernameTextField.getText();
        String password = new String(passwordField.getPassword());
        String userID = null;
        Role role = null;
        Vector<String> vectorID = ConnectionManager.select("SELECT user_id from users where (username = '" + username
                + "') and (password = '" + password + "')", 1);
        if (vectorID.size() == 0) {
            ErrorWindow error = new ErrorWindow();
        } else {
            for (Object vectorItem : vectorID) {
                Vector<String> vec = (Vector<String>) vectorItem;
                userID = vec.get(0);
            }
            WindowsManager.addMainFrame(new MainWindow(userID), "mainWindow");
            WindowsManager.setMainFramesVisible("authWindow", false);
        }
    }
}
