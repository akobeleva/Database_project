package GUI.CreateNewRow;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CreateBuilding extends CreateNewRowView{
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
    private Map<String, String> hospitals = new HashMap<>();

    public CreateBuilding(String viewName){
        super(viewName);
        this.setContentPane(mainPanel);

        OkButton.addActionListener(e->{
            okActionListener();
        });

        Vector vectorItems = ConnectionManager.select("SELECT hospital_id, name FROM hospitals", 2);
        for (int i = 0; i < vectorItems.size(); i++){
            Vector<String> vec = (Vector<String>) vectorItems.get(i);
            hospitals.put(vec.get(1), vec.get(0));
            comboBox.addItem(vec.get(1));
        }
        pack();
    }

    @Override
    public void insertRow() {
        ConnectionManager.insert("INSERT INTO buildings (hospital_id, building_number, phone_number, count_departments) VALUES (" +
                getRowFromForm() + ")");
    }

    @Override
    public String getRowFromForm() {
        String hospital_id = hospitals.get(comboBox.getSelectedItem());
        String buildNumber = buildNumberTextField.getText();
        String phoneNumber = phoneTextField.getText();
        String countDep = countDepTextField.getText();
        String row = new String("'" + hospital_id + "'" + ", " + "'" + buildNumber + "'" + ", " + "'" + phoneNumber + "'"
                + ", " + countDep);
        return row;
    }
}
