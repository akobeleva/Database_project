package GUI.CreateNewRow;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CreateHospital extends CreateNewRowView {
    private JTextField nameTextField;
    private JTextField addressTextField;
    private JComboBox comboBox;
    private JTextField phoneTextField;
    private JLabel label;
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JLabel addressLabel;
    private JLabel mainDoctorLabel;
    private JLabel phineLabel;
    private JButton OkButton;
    private Map<String, String> doctors = new HashMap<>();

    public CreateHospital(String viewName) {
        super(viewName);
        this.setContentPane(mainPanel);
        OkButton.addActionListener(e -> {
            okActionListener();
        });

        Vector vectorItems = ConnectionManager.select("SELECT doctor_id, d.surname || ' ' || d.name || ' ' || d.patronymic from doctors d", 2);
        for (int i = 0; i < vectorItems.size(); i++) {
            Vector<String> vec = (Vector<String>) vectorItems.get(i);
            doctors.put(vec.get(1), vec.get(0));
            comboBox.addItem(vec.get(1));
        }
        pack();
    }

    @Override
    public void insertRow() {
        ConnectionManager.insert("INSERT INTO hospitals (name, address, main_doctor_id, phone_number) VALUES (" + getRowFromForm() + ")");
    }

    @Override
    public String getRowFromForm() {
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        String mainDoctor = doctors.get(comboBox.getSelectedItem());
        String phone = phoneTextField.getText();
        String row = new String("'" + name + "'" + ", " + "'" + address + "'" + ", " + "'" + mainDoctor + "'" + ", " + phone);
        return row;
    }
}
