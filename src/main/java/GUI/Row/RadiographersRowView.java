package GUI.Row;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.Vector;

public class RadiographersRowView extends RowView{
    private JLabel label;
    private JPanel mainPanel;
    private JTextField salaryKfTextField;
    private JTextField vacDaysTextField;
    private JButton OkButton;
    private JLabel idLabel;
    private JLabel doctorIdLabel;
    private JLabel salaryKfLabel;
    private JLabel vacDaysLabel;

    public RadiographersRowView(String viewName, Mode mode) {
        super(viewName, mode);
        this.setContentPane(mainPanel);

        OkButton.addActionListener(e -> okActionListener());

        pack();
    }

    @Override
    public void insertRow() {
        ConnectionManager.executeQuery("UPDATE radiographers SET " + getRowFromForm() + "WHERE doctor_id = " + selectedRow);
    }

    @Override
    public String getRowFromForm() {
        String salaryKf = salaryKfTextField.getText();
        String vacDays = vacDaysTextField.getText();
        String row = "salary_kf = " + salaryKf + "," + "vacation_days = " + vacDays;
        return row;
    }

    @Override
    public void fillFields() {
        Vector vectorRows = ConnectionManager.select("SELECT * from radiographers WHERE doctor_id = " + selectedRow, 3);
        Vector<String> row = (Vector<String>) vectorRows.get(0);
        doctorIdLabel.setText(row.get(0));
        salaryKfTextField.setText(row.get(1));
        vacDaysTextField.setText(row.get(2));
    }
}
