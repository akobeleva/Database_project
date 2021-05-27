package GUI;

import DAL.ConnectionManager;
import DAL.MyConnection;

import javax.swing.*;
import java.sql.SQLException;

public class StartWindow extends JFrame {
    public StartWindow(){
        this.setTitle("ИСМО");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,300);
        this.setLayout(null);

        JButton nsuConnButton = new JButton("Подключиться к серверу НГУ");
        nsuConnButton.setBounds(50, 50, 280, 50);
        nsuConnButton.addActionListener(e -> {
            try {
                MyConnection conn = new MyConnection("18208_kobeleva", "azkp4yzp",
                        "jdbc:oracle:thin:@84.237.50.81:1521:");
                ConnectionManager connManager = new ConnectionManager(conn);
                WindowsManager.setMainFramesVisible("startWindow", false);
                WindowsManager.addMainFrame(new AuthorizationWindow(), "authWindow");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        JButton localConnButton = new JButton("Подключиться к локальному серверу");
        localConnButton.setBounds(50, 150, 280, 50);
        localConnButton.addActionListener(e-> {
            WindowsManager.setMainFramesVisible("startWindow", false);
            WindowsManager.addMainFrame(new LoggingWindow(), "loggingWindow");
        });
        this.add(nsuConnButton);
        this.add(localConnButton);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
