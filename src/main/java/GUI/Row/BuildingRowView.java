package GUI.Row;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class BuildingRowView extends RowView {
    private JLabel label;
    private JComboBox comboBox;
    private JLabel hospitalLabel;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JTextField buildNumberTextField;
    private JLabel buildNumberLabel;
    private JTextField countDepTextField;
    private JLabel countDepLabel;
    private JButton OkButton;
    private JPanel mainPanel;
    private final Map<String, String> hospitals = new HashMap<>();

    public BuildingRowView(String viewName, Mode mode){
        super(viewName, mode);
        this.setContentPane(mainPanel);

        OkButton.addActionListener(e-> okActionListener());

        Vector vectorItems = ConnectionManager.select("SELECT hospital_id, name FROM hospitals", 2);
        for (Object vectorItem : vectorItems) {
            Vector<String> vec = (Vector<String>) vectorItem;
            hospitals.put(vec.get(1), vec.get(0));
            comboBox.addItem(vec.get(1));
        }
        comboBox.setSelectedIndex(-1);
        pack();
    }

    @Override
    public void insertRow() {
        if (mode == Mode.CREATE){
            ConnectionManager.insert("INSERT INTO buildings (hospital_id, building_number, phone_number, count_departments) VALUES (" +
                    getRowFromForm() + ")");
        }
        else ConnectionManager.executeQuery("UPDATE buildings SET " + getRowFromForm() + "WHERE building_id = " + selectedRow);
    }

    @Override
    public String getRowFromForm() {
        String hospital_id = hospitals.get(comboBox.getSelectedItem());
        String buildNumber = buildNumberTextField.getText();
        String phoneNumber = phoneTextField.getText();
        String countDep = countDepTextField.getText();
        String row;
        if (mode == Mode.CREATE) {
            row = "'" + hospital_id + "'" + ", " + "'" + buildNumber + "'" + ", " + "'" + phoneNumber + "'" + ", " + countDep;
        }
        else {
            row  = "hospital_id = " + "'" + hospital_id + "'," + "building_number = " + "'" + buildNumber + "'," + "phone_number = "
                    + "'" + phoneNumber + "'," + "count_departments = " + countDep;
        }
        return row;
    }

    @Override
    public void fillFields(){
        Vector vectorRows = ConnectionManager.select("SELECT * from buildings WHERE building_id = " + selectedRow, 5);
        Vector<String> row = (Vector<String>) vectorRows.get(0);
        comboBox.setSelectedItem(row.get(1));
        buildNumberTextField.setText(row.get(2));
        phoneTextField.setText(row.get(3));
        countDepTextField.setText(row.get(4));
    }
}
