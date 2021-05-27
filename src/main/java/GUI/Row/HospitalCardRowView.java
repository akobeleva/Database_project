package GUI.Row;

import DAL.ConnectionManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class HospitalCardRowView extends RowView{
    private JComboBox hospComboBox;
    private JComboBox patComboBox;
    private JTextField startDayTextField;
    private JTextField startMonthTextField;
    private JTextField startYearTextField;
    private JTextField endDayTextField;
    private JTextField endMonthTextField;
    private JTextField endYearTextField;
    private JTextField statusTextField;
    private JTextField tempTextField;
    private JButton OkButton;
    private JLabel label;
    private JPanel mainPanel;
    private JLabel hospLabel;
    private JLabel patLabel;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JLabel statusLabel;
    private JLabel tempLabel;
    private JComboBox depComboBox;
    private JLabel depLabel;
    private final Map<String, String> hospitals = new HashMap<>();
    private final Map<String, String> departments = new HashMap<>();
    private final Map<String, String> patients = new HashMap<>();

    public HospitalCardRowView(String viewName, Mode mode) {
        super(viewName, mode);
        this.setContentPane(mainPanel);

        OkButton.addActionListener(e-> okActionListener());
        setComboBoxItems();
        pack();
    }

    @Override
    public void insertRow() {
        if (mode == Mode.CREATE) {
            ConnectionManager.insert("INSERT INTO hospital_card(patient_id, department_id, start_date, end_date, health_status, temp)"
                    + " VALUES (" + getRowFromForm() + ")");
        }
    }

    @Override
    public String getRowFromForm() {
        String department = departments.get(depComboBox.getSelectedItem());
        String patient = patients.get(patComboBox.getSelectedItem());
        String startDay = startDayTextField.getText();
        String startMonth = startMonthTextField.getText();
        String startYear = startYearTextField.getText();
        String endDay = endDayTextField.getText();
        String endMonth = endMonthTextField.getText();
        String endYear = endYearTextField.getText();
        String status = statusTextField.getText();
        String temp = tempTextField.getText();
        String row = patient + "," + department + "," + "TO_DATE('" + startDay +"/" + startMonth + "/" + startYear + "', 'DD/MM/YYYY'),"
                + "TO_DATE('" + endDay +"/" + endMonth + "/" + endYear + "', 'DD/MM/YYYY'),'" + status + "'," + temp;
        return row;
    }

    @Override
    public void fillFields() {

    }

    private void setComboBoxItems(){
        Vector hospitalsItems = ConnectionManager.select("SELECT hospital_id, name from hospitals", 2);
        for (Object vectorItem : hospitalsItems) {
            Vector<String> vec = (Vector<String>) vectorItem;
            hospitals.put(vec.get(1), vec.get(0));
            hospComboBox.addItem(vec.get(1));
        }
        hospComboBox.setSelectedIndex(-1);

        Vector patientsItems = ConnectionManager.select("SELECT patient_id, p.surname || ' ' || p.name || ' ' || p.patronymic from patients p", 2);
        for (Object vectorItem : patientsItems) {
            Vector<String> vec = (Vector<String>) vectorItem;
            patients.put(vec.get(1), vec.get(0));
            patComboBox.addItem(vec.get(1));
        }
        patComboBox.setSelectedIndex(-1);
        hospComboBox.addItemListener(e -> {
            depComboBox.removeAllItems();
            String hosp_id = hospitals.get(hospComboBox.getSelectedItem());
            Vector departmentsItems = ConnectionManager.select("SELECT department_id, d.name " +
                    "from departments d JOIN buildings USING (building_id) JOIN hospitals USING(hospital_id) " +
                    "WHERE (hospital_id = " + hosp_id + ")", 2);
            for (Object doctorItem : departmentsItems) {
                Vector<String> vec = (Vector<String>) doctorItem;
                departments.put(vec.get(1), vec.get(0));
                depComboBox.addItem(vec.get(1));
            }
            depComboBox.setSelectedIndex(-1);
        });
    }
}
