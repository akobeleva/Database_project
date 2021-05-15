package GUI;

import javax.swing.*;

public class EnterWindow extends JFrame{
    public EnterWindow(){
        this.setTitle("Вход");
        this.setSize(300,300);
        this.setLayout(null);

        JButton authButton = new JButton("Авторизация");
        authButton.setBounds(50, 50, 200, 50);
        authButton.addActionListener(e -> {
            WindowsManager.setMainFramesVisible("enterWindow", false);
            WindowsManager.addMainFrame(new AuthorizationWindow(), "authWindow");
        });

        JButton regButton = new JButton("Регистрация");
        regButton.setBounds(50, 150, 200, 50);
        regButton.addActionListener(e ->{
            WindowsManager.setMainFramesVisible("enterWindow", false);
            WindowsManager.addMainFrame(new RegistrationWindow(), "regWindow");
        });

        JButton backButton = new JButton("Назад");
        backButton.setBounds(0, 225, 50, 30);
        backButton.addActionListener(e->{
            WindowsManager.setMainFramesVisible("enterWindow", false);
            WindowsManager.setMainFramesVisible("startWindow", true);
        });
        this.add(authButton);
        this.add(regButton);
        this.add(backButton);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
