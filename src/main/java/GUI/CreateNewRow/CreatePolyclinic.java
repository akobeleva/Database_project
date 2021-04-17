package GUI.CreateNewRow;

import DAL.ConnectionManager;

import javax.swing.*;

public class CreatePolyclinic extends CreateNewRowView {
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

    public CreatePolyclinic(String viewName) {
        super(viewName);
        this.setContentPane(mainPanel);
        OkButton.addActionListener(e -> {
            okActionListener();
        });
        pack();
    }

    @Override
    public void insertRow() {
        ConnectionManager.insert("INSERT INTO polyclinics (name, address, phone_number, count_cabinets) VALUES (" + getRowFromForm() + ")");

    }

    @Override
    public String getRowFromForm() {
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        String phone = phoneTextField.getText();
        String cabinets = cabinetsTextField.getText();
        String row = new String("'" + name + "'" + ", " + "'" + address + "'" + ", " + "'" + phone + "'" + ", " + cabinets);
        return row;
    }
}
