package GUI.Request;

import DAL.ConnectionManager;
import GUI.Response.PatientsOfSelectedHospitalResponse;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class PatientsOfSelectedHospitalRequest extends RequestView{
    private JComboBox hospComboBox;
    private JComboBox depComboBox;
    private JButton requestButton;
    private JLabel label;
    private JLabel hospitalLabel;
    private JLabel depLAbel;
    private JPanel mainPanel;
    private final Map<String, String> hospitals = new HashMap<>();
    private final Map<String, String> departments = new HashMap<>();

    public PatientsOfSelectedHospitalRequest(String viewName) {
        super(viewName);
        this.setContentPane(mainPanel);
        requestButton.addActionListener(e -> requestActionListener());
        setComboBoxItems();
        pack();
    }

    private void setComboBoxItems() {
        Vector hospitalsItems = ConnectionManager.select("SELECT hospital_id, name from hospitals", 2);
        for (Object vectorItem : hospitalsItems) {
            Vector<String> vec = (Vector<String>) vectorItem;
            hospitals.put(vec.get(1), vec.get(0));
            hospComboBox.addItem(vec.get(1));
        }
        depComboBox.setSelectedIndex(-1);
        hospComboBox.addActionListener(e->{
            String hospID = hospitals.get(hospComboBox.getSelectedItem());
            depComboBox.removeAllItems();
            Vector depItems = ConnectionManager.select("SELECT department_id, d.name from departments d JOIN buildings " +
                    "USING(building_id) JOIN hospitals USING(hospital_id) WHERE (hospital_id = " + hospID + ")", 2);
            for (Object vectorItem : depItems) {
                Vector<String> vec = (Vector<String>) vectorItem;
                departments.put(vec.get(1), vec.get(0));
                depComboBox.addItem(vec.get(1));
            }
            depComboBox.setSelectedIndex(-1);
        });
        hospComboBox.setSelectedIndex(-1);
    }

    @Override
    public void executeRequest() {
        PatientsOfSelectedHospitalResponse response = new PatientsOfSelectedHospitalResponse(getRequestFromForm());
    }

    @Override
    public String getRequestFromForm() {
        String hosp = hospitals.get(hospComboBox.getSelectedItem());
        String request = null;
        if (depComboBox.getSelectedIndex() == -1) {
            request = "SELECT p.surname || ' ' || p.name || ' ' || p.patronymic, h.name, d.name, start_date, health_status, temp " +
                    "FROM hospital_card JOIN patients p USING(patient_id) JOIN departments d USING(department_id) JOIN buildings USING(building_id) " +
                    "JOIN hospitals h USING(hospital_id) WHERE hospital_id = " + hosp;
        }
        else {
            request = "SELECT p.surname || ' ' || p.name || ' ' || p.patronymic, h.name, d.name, start_date, health_status, temp " +
                    "FROM hospital_card JOIN patients p USING(patient_id) JOIN departments d USING(department_id) JOIN buildings USING(building_id) " +
                    "JOIN hospitals h USING(hospital_id) WHERE department_id = "
                    + departments.get(depComboBox.getSelectedItem()) + " AND hospital_id = " + hosp;
        }
        return request;
    }
}
