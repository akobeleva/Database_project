package GUI.Request;

import DAL.ConnectionManager;
import GUI.Response.DoctorsOfSelectedSpecialityResponse;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class DoctorsOfSelectedSpecialityRequest extends RequestView {
    private JPanel mainPanel;
    private JLabel label;
    private JComboBox specComboBox;
    private JComboBox typeComboBox;
    private JComboBox institComboBox;
    private JButton requestButton;
    private JLabel specLabel;
    private JLabel typeLabel;
    private JLabel institutionLabel;
    private final Map<String, String> specialities = new HashMap<>();
    private final Map<String, String> hospitals = new HashMap<>();
    private final Map<String, String> polyclinics = new HashMap<>();

    public DoctorsOfSelectedSpecialityRequest(String viewName) {
        super(viewName);
        this.setContentPane(mainPanel);

        requestButton.addActionListener(e -> requestActionListener());

        setComboBoxItems();

        pack();
    }

    @Override
    public void executeRequest() {
        DoctorsOfSelectedSpecialityResponse response = new DoctorsOfSelectedSpecialityResponse(getRequestFromForm());
    }

    @Override
    public String getRequestFromForm() {
        String spec = specialities.get(specComboBox.getSelectedItem());
        String request = null;
        if (typeComboBox.getSelectedIndex() == 0) {
            request = "SELECT doctor_id, d.surname || ' ' || d.name || ' ' || d.patronymic " +
                    "FROM doctors d JOIN doctors_of_hospitals USING (doctor_id) WHERE hospital_id = "
                    + hospitals.get(institComboBox.getSelectedItem()) + " AND speciality_id = " + spec;
        }
        if (typeComboBox.getSelectedIndex() == 1) {
            request = "SELECT doctor_id, d.surname || ' ' || d.name || ' ' || d.patronymic " +
                    "FROM doctors d JOIN doctors_of_polyclinics USING (doctor_id) WHERE polyclinic_id = "
                    + polyclinics.get(institComboBox.getSelectedItem()) + " AND speciality_id = " + spec;
        }
        if (typeComboBox.getSelectedIndex() == 2) {
            request = "SELECT doctor_id, surname || ' ' || name || ' ' || patronymic " +
                    "FROM doctors WHERE speciality_id = " +  spec;
        }
        return request;
    }

    private void setComboBoxItems() {
        Vector vectorItems = ConnectionManager.select("SELECT * from specialities", 2);
        for (Object vectorItem : vectorItems) {
            Vector<String> vec = (Vector<String>) vectorItem;
            specialities.put(vec.get(1), vec.get(0));
            specComboBox.addItem(vec.get(1));
        }
        specComboBox.setSelectedIndex(-1);

        typeComboBox.addItem("Больница");
        typeComboBox.addItem("Поликлиника");
        typeComboBox.addItem("Все учреждения");
        typeComboBox.addItemListener(e -> {
            institComboBox.removeAllItems();
            if (typeComboBox.getSelectedIndex() == 0) {
                Vector hospitalsItems = ConnectionManager.select("SELECT hospital_id, name from hospitals", 2);
                for (Object vectorItem : hospitalsItems) {
                    Vector<String> vec = (Vector<String>) vectorItem;
                    hospitals.put(vec.get(1), vec.get(0));
                    institComboBox.addItem(vec.get(1));
                }
            }
            if (typeComboBox.getSelectedIndex() == 1) {
                Vector polyclinicsItems = ConnectionManager.select("SELECT polyclinic_id, name from polyclinics", 2);
                for (Object vectorItem : polyclinicsItems) {
                    Vector<String> vec = (Vector<String>) vectorItem;
                    polyclinics.put(vec.get(1), vec.get(0));
                    institComboBox.addItem(vec.get(1));
                }
            }
        });
        typeComboBox.setSelectedIndex(-1);
    }
}
