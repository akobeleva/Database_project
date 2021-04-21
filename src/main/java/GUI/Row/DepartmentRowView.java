package GUI.Row;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.Vector;

public class DepartmentRowView extends RowView {
    private JTextField nameTextField;
    private JLabel label;
    private JLabel nameLabel;
    private JLabel idBuildLabel;
    private JLabel phoneLabel;
    private JLabel wardsLabel;
    private JLabel bedsLabel;
    private JLabel freeWardsLabel;
    private JLabel freeBedsLabel;
    private JTextField phoneTextField;
    private JTextField wardsTextField;
    private JTextField bedsTextField;
    private JTextField freeWardsTextField;
    private JTextField freeBedsTextField;
    private JButton OkButton;
    private JPanel mainPanel;
    private JTextField idBuildTextField;

    public DepartmentRowView(String viewName, Mode mode) {
        super(viewName, mode);
        this.setContentPane(mainPanel);

        OkButton.addActionListener(e->{
            okActionListener();
        });
        pack();
    }

    @Override
    public void insertRow() {
        if (mode == Mode.CREATE){
            ConnectionManager.insert("INSERT INTO departments (name, building_id, phone_number, wards, beds, free_wards, free_beds) VALUES ("
                    + getRowFromForm() + ")");
        }
        else ConnectionManager.executeQuery("UPDATE departments SET " + getRowFromForm() + "WHERE department_id = " + selectedRow);
    }

    @Override
    public String getRowFromForm() {
        String name = nameTextField.getText();
        String buildId = idBuildTextField.getText();
        String phoneNumber = phoneTextField.getText();
        String wards = wardsTextField.getText();
        String beds = bedsTextField.getText();
        String freeWards = freeWardsTextField.getText();
        String freeBeds = freeBedsTextField.getText();
        String row;
        if (mode == Mode.CREATE) {
            row = "'" + name + "'" + ", " + "'" + buildId + "'" + ", " + "'" + phoneNumber + "'" + ", " + wards + ", "
                    + beds + ", " + freeWards + ", " + freeBeds;
        }
        else {
            row = "name = " + "'" + name + "'," + "building_id = " + "'" + buildId + "'," + "phone_number = "
                    + "'" + phoneNumber + "'," + "wards = " + wards + "," + "beds = " + beds + "," + "free_wards = " + freeWards
                    + "," + "free_beds = " + freeBeds;
        }
        return row;
    }

    @Override
    public void fillFields() {
        Vector vectorRows = ConnectionManager.select("SELECT * from departments WHERE department_id = " + selectedRow, 8);
        Vector<String> row = (Vector<String>) vectorRows.get(0);
        nameTextField.setText(row.get(1));
        idBuildTextField.setText(row.get(2));
        phoneTextField.setText(row.get(3));
        wardsTextField.setText(row.get(4));
        bedsTextField.setText(row.get(5));
        freeWardsTextField.setText(row.get(6));
        freeBedsTextField.setText(row.get(7));
    }
}
