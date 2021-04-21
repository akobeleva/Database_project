package GUI.Row;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.Vector;

public class PolyclinicRowView extends RowView {
    private JPanel mainPanel;
    private JTextField addressTextField;
    private JTextField nameTextField;
    private JTextField phoneTextField;
    private JLabel label;
    private JTextField cabinetsTextField;
    private JLabel nameLabel;
    private JLabel addressLabel;
    private JLabel phoneLabel;
    private JLabel cabinetsLabel;
    private JButton OkButton;

    public PolyclinicRowView(String viewName, Mode mode) {
        super(viewName, mode);
        this.setContentPane(mainPanel);
        OkButton.addActionListener(e -> {
            okActionListener();
        });
        pack();
    }

    @Override
    public void insertRow() {
        if (mode ==Mode.CREATE){
            ConnectionManager.insert("INSERT INTO polyclinics (name, address, phone_number, count_cabinets) VALUES (" + getRowFromForm() + ")");
        }
        else ConnectionManager.executeQuery("UPDATE polyclinics SET " + getRowFromForm() + "WHERE polyclinic_id = " + selectedRow);
    }

    @Override
    public String getRowFromForm() {
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        String phone = phoneTextField.getText();
        String cabinets = cabinetsTextField.getText();
        String row;
        if (mode == Mode.CREATE) {
            row = "'" + name + "'" + ", " + "'" + address + "'" + ", " + "'" + phone + "'" + ", " + cabinets;
        }
        else {
            row = "name = " + "'" + name + "'," + "address = " + "'" + address + "'," + "phone_number = "
                    + "'" + phone + "'" + "," + "count_cabinets = " + cabinets;
        }
        return row;
    }

    @Override
    public void fillFields() {
        Vector vectorRows = ConnectionManager.select("SELECT * from polyclinics WHERE polyclinic_id = " + selectedRow, 5);
        Vector<String> row = (Vector<String>) vectorRows.get(0);
        nameTextField.setText(row.get(1));
        addressTextField.setText(row.get(2));
        phoneTextField.setText(row.get(3));
        cabinetsTextField.setText(row.get(4));
    }
}
