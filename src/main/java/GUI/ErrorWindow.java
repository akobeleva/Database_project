package GUI;

import javax.swing.*;

public class ErrorWindow extends JFrame {
    public ErrorWindow() {
        this.setTitle("Ошибка");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 180);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("Имя пользователя или пароль введен не верно!");
        JButton button = new JButton("OK");
        button.addActionListener(e->{
            this.setVisible(false);
        });
        panel.add(label);
        panel.add(button);
        this.add(panel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
