package GUI.Request;

import DAL.ConnectionManager;

import GUI.Response.ServiceStaffOfSelectedSpecialityResponse;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ServiceStaffOfSelectedSpecialityRequest extends RequestView{
    private JComboBox specComboBox;
    private JComboBox typeComboBox;
    private JComboBox instComboBox;
    private JButton requestButton;
    private JLabel label;
    private JPanel mainPanel;
    private JLabel specLabel;
    private JLabel typeLabel;
    private JLabel institutionLabel;
    private final Map<String, String> specialities = new HashMap<>();
    private final Map<String, String> hospitals = new HashMap<>();
    private final Map<String, String> polyclinics = new HashMap<>();

    public ServiceStaffOfSelectedSpecialityRequest(String viewName) {
        super(viewName);
        this.setContentPane(mainPanel);

        requestButton.addActionListener(e -> requestActionListener());

        setComboBoxItems();

        pack();
    }

    @Override
    public void executeRequest() {
       ServiceStaffOfSelectedSpecialityResponse response = new ServiceStaffOfSelectedSpecialityResponse(getRequestFromForm());
    }

    @Override
    public String getRequestFromForm() {
        String spec = specialities.get(specComboBox.getSelectedItem());
        String request = null;
        if (typeComboBox.getSelectedIndex() == 0) {
            request = "SELECT ss_id, ss.surname || ' ' || ss.name || ' ' || ss.patronymic " +
                    "FROM service_staff ss JOIN ss_of_hospitals USING (ss_id) WHERE hospital_id = "
                    + hospitals.get(instComboBox.getSelectedItem()) + " AND spec_ss_id = " + spec;
        }
        if (typeComboBox.getSelectedIndex() == 1) {
            request = "SELECT ss_id, ss.surname || ' ' || ss.name || ' ' || ss.patronymic " +
                    "FROM service_staff ss JOIN ss_of_polyclinics USING (ss_id) WHERE polyclinic_id = "
                    + polyclinics.get(instComboBox.getSelectedItem()) + " AND spec_ss_id = " + spec;
        }
        if (typeComboBox.getSelectedIndex() == 2) {
            request = "SELECT ss_id, surname || ' ' || name || ' ' || patronymic " +
                    "FROM service_staff WHERE spec_ss_id = " +  spec;
        }
        return request;
    }

    private void setComboBoxItems() {
        Vector vectorItems = ConnectionManager.select("SELECT * from spec_of_service_staff", 2);
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
            instComboBox.removeAllItems();
            if (typeComboBox.getSelectedIndex() == 0) {
                Vector hospitalsItems = ConnectionManager.select("SELECT hospital_id, name from hospitals", 2);
                for (Object vectorItem : hospitalsItems) {
                    Vector<String> vec = (Vector<String>) vectorItem;
                    hospitals.put(vec.get(1), vec.get(0));
                    instComboBox.addItem(vec.get(1));
                }
            }
            if (typeComboBox.getSelectedIndex() == 1) {
                Vector polyclinicsItems = ConnectionManager.select("SELECT polyclinic_id, name from polyclinics", 2);
                for (Object vectorItem : polyclinicsItems) {
                    Vector<String> vec = (Vector<String>) vectorItem;
                    polyclinics.put(vec.get(1), vec.get(0));
                    instComboBox.addItem(vec.get(1));
                }
            }
        });
        typeComboBox.setSelectedIndex(-1);
    }
}
