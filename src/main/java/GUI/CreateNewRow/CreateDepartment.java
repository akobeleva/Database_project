package GUI.CreateNewRow;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CreateDepartment extends CreateNewRowView{
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

    public CreateDepartment(String viewName) {
        super(viewName);
        this.setContentPane(mainPanel);

        OkButton.addActionListener(e->{
            okActionListener();
        });
        pack();
    }

    @Override
    public void insertRow() {
        ConnectionManager.insert("INSERT INTO departments (name, building_id, phone_number, wards, beds, free_wards, free_beds) VALUES ("
                + getRowFromForm() + ")");
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
        String row = new String("'" + name + "'" + ", " + "'" + buildId + "'" + ", " + "'" + phoneNumber + "'"
                + ", " + wards + ", " + beds + ", " + freeWards + ", " + freeBeds);
        return row;
    }
}
