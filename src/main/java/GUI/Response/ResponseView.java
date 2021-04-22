package GUI.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ResponseView extends JFrame {
    public JPanel mainPanel;
    public JTable table;
    public JButton okButton;
    public JLabel totalCountLabel;
    public JLabel totalCount;
    public DefaultTableModel dtm;

    public ResponseView(String request) {
        this.setTitle("Ответ на запрос");
        this.setSize(800, 600);
        this.setContentPane(mainPanel);

        okButton.addActionListener(e -> this.setVisible(false));
        dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int i, int j){
                return false;
            }
        };

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
