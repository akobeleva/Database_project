package GUI.Request;

import DAL.ConnectionManager;
import GUI.Response.PatientsOfSelectedPolyclinicAndDoctorResponse;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class PatientsOfSelectedPolyclinicAndDoctorRequest extends RequestView {
    private JPanel mainPanel;
    private JLabel label;
    private JComboBox polComboBox;
    private JComboBox specComboBox;
    private JComboBox doctorComboBox;
    private JButton requestButton;
    private JLabel polLabel;
    private JLabel specLabel;
    private JLabel doctorLabel;
    private final Map<String, String> polyclinics = new HashMap<>();
    private final Map<String, String> specialities = new HashMap<>();
    private final Map<String, String> doctors = new HashMap<>();

    public PatientsOfSelectedPolyclinicAndDoctorRequest(String viewName) {
        super(viewName);
        this.setContentPane(mainPanel);
        requestButton.addActionListener(e -> requestActionListener());
        setComboBoxItems();
        pack();
    }

    private void setComboBoxItems() {
        Vector specItems = ConnectionManager.select("SELECT speciality_id, name from specialities", 2);
        for (Object vectorItem : specItems) {
            Vector<String> vec = (Vector<String>) vectorItem;
            specialities.put(vec.get(1), vec.get(0));
            specComboBox.addItem(vec.get(1));
        }
        specComboBox.setSelectedIndex(-1);
        Vector polyclinicsItems = ConnectionManager.select("SELECT polyclinic_id, name from polyclinics", 2);
        for (Object vectorItem : polyclinicsItems) {
            Vector<String> vec = (Vector<String>) vectorItem;
            polyclinics.put(vec.get(1), vec.get(0));
            polComboBox.addItem(vec.get(1));
        }
        polComboBox.setSelectedIndex(-1);
        specComboBox.addActionListener(e -> {
            String polID = polyclinics.get(polComboBox.getSelectedItem());
            String spec = specialities.get(specComboBox.getSelectedItem());
            doctorComboBox.removeAllItems();
            Vector depItems = ConnectionManager.select("SELECT doctor_id, d.surname || ' ' || d.name || ' ' || d.patronymic " +
                    "from doctors d JOIN doctors_of_polyclinics USING(doctor_id) WHERE polyclinic_id = " + polID + " AND speciality_id = " +
                    spec, 2);
            for (Object vectorItem : depItems) {
                Vector<String> vec = (Vector<String>) vectorItem;
                doctors.put(vec.get(1), vec.get(0));
                doctorComboBox.addItem(vec.get(1));
            }
            doctorComboBox.setSelectedIndex(-1);
        });
    }

    @Override
    public void executeRequest() {
        PatientsOfSelectedPolyclinicAndDoctorResponse response = new PatientsOfSelectedPolyclinicAndDoctorResponse(getRequestFromForm());
    }

    @Override
    public String getRequestFromForm() {
        String pol = polyclinics.get(polComboBox.getSelectedItem());
        String doctor = doctors.get(doctorComboBox.getSelectedItem());
        String request = null;
        request = "SELECT p.surname || ' ' || p.name || ' ' || p.patronymic " +
                "FROM polyclinic_card JOIN patients p USING(patient_id) JOIN doctors USING(doctor_id) JOIN doctors_of_polyclinics " +
                "USING(doctor_id) WHERE doctor_id = " + doctor + " AND polyclinic_id = " + pol;
        return request;
}
}
