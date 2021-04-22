package GUI.Row;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class HospitalRowView extends RowView {
    private JTextField nameTextField;
    private JTextField addressTextField;
    private JComboBox comboBox;
    private JTextField phoneTextField;
    private JLabel label;
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JLabel addressLabel;
    private JLabel mainDoctorLabel;
    private JLabel phoneLabel;
    private JButton OkButton;
    private final Map<String, String> doctors = new HashMap<>();

    public HospitalRowView(String viewName, Mode mode) {
        super(viewName, mode);
        this.setContentPane(mainPanel);
        OkButton.addActionListener(e -> okActionListener());

        Vector vectorItems = ConnectionManager.select("SELECT doctor_id, d.surname || ' ' || d.name || ' ' || d.patronymic from doctors d", 2);
        for (int i = 0; i < vectorItems.size(); i++) {
            Vector<String> vec = (Vector<String>) vectorItems.get(i);
            doctors.put(vec.get(1), vec.get(0));
            comboBox.addItem(vec.get(1));
        }
        comboBox.setSelectedIndex(-1);

        pack();
    }

    @Override
    public void insertRow() {
        if (mode == Mode.CREATE){
            ConnectionManager.insert("INSERT INTO hospitals (name, address, main_doctor_id, phone_number) VALUES ("
                    + getRowFromForm() + ")");
        }
        else ConnectionManager.executeQuery("UPDATE hospitals SET " + getRowFromForm() + "WHERE hospital_id = " + selectedRow);
    }

    @Override
    public String getRowFromForm() {
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        String mainDoctor = doctors.get(comboBox.getSelectedItem());
        String phone = phoneTextField.getText();
        String row;
        if (mode == Mode.CREATE){
            row = "'" + name + "'" + ", " + "'" + address + "'" + ", " + "'" + mainDoctor + "'" + ", " + phone;
        }
        else {
            row = "name = " + "'" + name + "'," + "address = " + "'" + address + "'," + "main_doctor_id = "
                    + mainDoctor +"," + "phone_number = " +  "'" + phone + "'";
        }
        return row;
    }

    @Override
    public void fillFields() {
        Vector vectorRows = ConnectionManager.select("SELECT * from hospitals WHERE hospital_id = " + selectedRow, 5);
        Vector<String> row = (Vector<String>) vectorRows.get(0);
        nameTextField.setText(row.get(1));
        addressTextField.setText(row.get(2));
        comboBox.setSelectedItem(row.get(3));
        phoneTextField.setText(row.get(4));
    }
}
